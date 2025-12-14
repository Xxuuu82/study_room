<template>
  <div class="blacklist-container">
    <!-- 黑名单管理卡片 -->
    <el-card class="info-card" shadow="hover">
      <div class="card-header">
        <h2>黑名单管理</h2>
        <el-tag type="danger" size="small">违规累计≥3次自动拉黑</el-tag>
      </div>

      <!-- 搜索栏 -->
      <div class="search-form">
        <el-form :model="searchForm" inline @submit.prevent="handleSearch">
          <el-form-item label="学号">
            <el-input
              v-model="searchForm.xuehao"
              placeholder="请输入学号搜索"
              clearable
              style="width: 200px"
            />
          </el-form-item>
          <el-form-item label="状态">
            <el-select
              v-model="searchForm.shifoujiechu"
              placeholder="请选择状态"
              clearable
              style="width: 150px"
            >
              <el-option label="全部" value="" />
              <el-option label="拉黑中" value="0" />
              <el-option label="已解除" value="1" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 黑名单列表表格 -->
      <div class="table-container">
        <el-table
          :data="tableData"
          border
          stripe
          v-loading="loading"
          style="width: 100%"
          :empty-text="loading ? '加载中...' : '暂无黑名单数据'"
        >
          <!-- 学号（匹配后端xuehao字段） -->
          <el-table-column prop="xuehao" label="学号" width="120" />
          <!-- 姓名（匹配后端xingming字段） -->
          <el-table-column prop="xingming" label="姓名" width="120" />
          <!-- 累计违规次数（匹配后端驼峰字段totalWeiguiCount） -->
          <el-table-column label="累计违规次数" width="120">
            <template slot-scope="scope">
              <span class="count-text">{{ scope.row.totalWeiguiCount || 0 }}次</span>
            </template>
          </el-table-column>
          <!-- 违规明细（匹配后端驼峰字段） -->
          <el-table-column label="违规明细" min-width="200">
            <template slot-scope="scope">
              <div class="detail-item">教室乱吃乱喝：{{ scope.row.weiguiChifanCount || 0 }}次</div>
              <div class="detail-item">未签到：{{ scope.row.weiguiWeiqiandaoCount || 0 }}次</div>
              <div class="detail-item">大声喧哗：{{ scope.row.weiguiXuanhuaCount || 0 }}次</div>
              <div class="detail-item">随地大小便：{{ scope.row.weiguiSuibiaobianCount || 0 }}次</div>
              <div v-if="scope.row.otherWeiguiDesc" class="detail-item">其他违规：{{ scope.row.otherWeiguiDesc }}</div>
            </template>
          </el-table-column>
          <!-- 拉黑原因（匹配后端heimingdanYuanyin字段） -->
          <el-table-column prop="heimingdanYuanyin" label="拉黑原因" min-width="200" />
          <!-- 加入时间（格式化时间戳） -->
          <el-table-column label="加入时间" width="200">
            <template slot-scope="scope">
              {{ formatDate(scope.row.heimingdanStartTime) }}
            </template>
          </el-table-column>
          <!-- 解除时间（格式化时间戳） -->
          <el-table-column label="解除时间" width="200">
            <template slot-scope="scope">
              <span :class="scope.row.shifoujiechu === 0 ? 'plan-text' : 'success-text'">
                {{ formatDate(scope.row.heimingdanEndTime) }}
                <span v-if="scope.row.shifoujiechu === 0">（计划）</span>
              </span>
            </template>
          </el-table-column>
          <!-- 状态 -->
          <el-table-column label="状态" width="100">
            <template slot-scope="scope">
              <el-tag :type="scope.row.shifoujiechu === 0 ? 'danger' : 'success'">
                {{ scope.row.shifoujiechu === 0 ? '拉黑中' : '已解除' }}
              </el-tag>
            </template>
          </el-table-column>
          <!-- 操作 -->
          <el-table-column label="操作" width="120">
            <template slot-scope="scope">
              <el-button
                type="success"
                size="small"
                @click="jiechuBlackList(scope.row)"
                :disabled="scope.row.shifoujiechu === 1"
              >
                解除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="margin-top: 20px; text-align: right"
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import http from '@/utils/http'
import { Message, MessageBox } from 'element-ui'

export default {
  data() {
    return {
      tableData: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      searchForm: {
        xuehao: "",
        shifoujiechu: ""
      }
    };
  },
  mounted() {
    this.getBlackList();
  },
  methods: {
    // 日期格式化：适配后端返回的时间戳（毫秒）
    formatDate(timestamp) {
      if (!timestamp) return "-";
      try {
        const date = new Date(timestamp);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      } catch (e) {
        return "-";
      }
    },

    // 获取黑名单列表（核心修改：适配现有http.js的返回格式）
    async getBlackList() {
      this.loading = true;
      try {
        // 1. 发送请求（路径不变，适配现有http.js的baseURL）
        const response = await http.get("/api/heimingdan/list", {
          params: {
            page: this.pageNum,
            size: this.pageSize,
            xuehao: this.searchForm.xuehao,
            shifoujiechu: this.searchForm.shifoujiechu
          }
        });
        // 2. 核心修改：现有http.js返回的是axios完整响应对象，需先取response.data
        const res = response.data;
        // 3. 解析后端返回的data（res.data才是真正的列表数据）
        this.tableData = res.data?.records || [];
        this.total = res.data?.total || 0;

        // 调试用：打印数据，方便你确认是否获取到（可选）
        console.log("黑名单数据：", this.tableData);
      } catch (error) {
        console.error("获取黑名单失败：", error);
        Message.error("获取黑名单列表失败，请检查后端服务！");
        this.tableData = [];
        this.total = 0;
      } finally {
        this.loading = false;
      }
    },

    // 解除黑名单（同样适配现有http.js的返回格式）
    async jiechuBlackList(row) {
      try {
        await MessageBox.confirm(
          `确定要解除【${row.xingming || row.xuehao}】的黑名单吗？`,
          "提示",
          { type: "warning" }
        );
        const response = await http.post("/api/heimingdan/jiechu", {
          xuehao: row.xuehao
        });
        const res = response.data;
        if (res.code === 0) {
          Message.success("解除黑名单成功");
          this.getBlackList(); // 刷新列表
        } else {
          Message.error(res.msg || "解除失败");
        }
      } catch (error) {
        if (error !== "cancel") {
          Message.error("解除失败，请稍后重试");
        }
      }
    },

    // 搜索事件
    handleSearch() {
      this.pageNum = 1;
      this.getBlackList();
    },

    // 分页-切换每页条数
    handleSizeChange(val) {
      this.pageSize = val;
      this.getBlackList();
    },

    // 分页-切换页码
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getBlackList();
    }
  }
};
</script>

<style lang="scss" scoped>
.blacklist-container {
  padding: 20px;
  background-color: #f5f7fa;

  .info-card {
    border-radius: 12px;
    overflow: hidden;

    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 16px 20px;
      border-bottom: 1px solid #eee;
      background-color: #fff;

      h2 {
        margin: 0;
        font-size: 18px;
        font-weight: 600;
        color: #333;
      }
    }

    .search-form {
      padding: 20px;
      background-color: #fff;
      border-bottom: 1px solid #eee;
    }

    .table-container {
      padding: 20px;
      background-color: #fff;

      .count-text {
        color: #e6a23c;
        font-weight: 600;
      }

      .detail-item {
        line-height: 1.8;
        font-size: 13px;
        color: #666;
      }

      .plan-text {
        color: #999;
      }

      .success-text {
        color: #67c23a;
      }
    }
  }
}

.el-form-item {
  margin-bottom: 15px;

  .el-form-item__label {
    color: #666;
    font-weight: 500;
  }

  .el-input__inner,
  .el-select__wrapper {
    border-radius: 6px;
    border-color: #dcdfe6;
    transition: all 0.3s;

    &:focus {
      border-color: #409eff;
      box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
    }
  }
}

.el-table {
  --el-table-header-text-color: #666;
  --el-table-row-hover-bg-color: #f8f9fa;

  .el-table__header-wrapper {
    th {
      background-color: #f5f7fa;
      font-weight: 500;
    }
  }
}

.el-pagination {
  .el-pagination__sizes {
    margin-right: 10px;
  }

  .el-pager li {
    border-radius: 4px;
    margin: 0 2px;
  }

  .el-pager li.active {
    background-color: #409eff;
    color: #fff;
  }
}
</style>