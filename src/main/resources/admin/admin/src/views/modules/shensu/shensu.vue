<template>
  <div class="shensu-container">
    <!-- 申诉处理卡片 -->
    <el-card class="info-card" shadow="hover">
      <div class="card-header">
        <h2>申诉处理</h2>
        <el-tag type="info" size="small">待处理申诉请及时处理</el-tag>
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
          <el-form-item label="处理状态">
            <el-select
              v-model="searchForm.chuliZhuangtai"
              placeholder="请选择状态"
              clearable
              style="width: 150px"
            >
              <el-option label="全部" value="" />
              <el-option label="待处理" value="0" />
              <el-option label="已确认" value="1" />
              <el-option label="已驳回" value="2" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 申诉列表表格 -->
      <div class="table-container">
        <el-table
          :data="tableData"
          border
          stripe
          v-loading="loading"
          style="width: 100%"
          :empty-text="loading ? '加载中...' : '暂无申诉记录'"
        >
          <!-- 申诉ID -->
          <el-table-column prop="id" label="申诉ID" width="100" >
            <template slot-scope="scope">
              <span class="id-text">{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <!-- 申诉单号 -->
          <el-table-column prop="shensuDanhao" label="申诉单号" width="200" />
          <!-- 学号 -->
          <el-table-column prop="xuehao" label="学号" width="180" />
          <!-- 姓名 -->
          <el-table-column prop="xingming" label="姓名" width="180" />
          <!-- 关联违规记录ID -->
          <el-table-column prop="weiguiRecordId" label="关联违规ID" width="150" />
          <!-- 关联违规详情 -->
          <el-table-column label="关联违规详情" min-width="200">
            <template slot-scope="scope">
              <el-button
                type="text"
                @click="viewWeiguiDetail(scope.row.weiguiRecordId)"
                style="color: #409eff;"
              >
                查看详情
              </el-button>
            </template>
          </el-table-column>
          <!-- 申诉内容 -->
          <el-table-column prop="shensuNeirong" label="申诉内容" min-width="300" />
          <!-- 申诉时间 -->
          <el-table-column label="申诉时间" width="210">
            <template slot-scope="scope">
              {{ formatDate(scope.row.shensuShijian) }}
            </template>
          </el-table-column>
          <!-- 处理状态 -->
          <el-table-column label="处理状态" width="120">
            <template slot-scope="scope">
              <el-tag :type="scope.row.chuliZhuangtai === 0 ? 'warning' : (scope.row.chuliZhuangtai === 1 ? 'success' : 'danger')">
                {{ getStatusName(scope.row.chuliZhuangtai) }}
              </el-tag>
            </template>
          </el-table-column>
          <!-- 操作列：修改为并列显示确认/驳回按钮 -->
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <div class="operate-btn-group">
                <el-button
                  type="success"
                  size="small"
                  icon="el-icon-check"
                  @click="handleShenSu(scope.row, 1)"
                  v-if="scope.row.chuliZhuangtai === 0"
                >
                  确认
                </el-button>
                <el-button
                  type="danger"
                  size="small"
                  icon="el-icon-close"
                  @click="handleShenSu(scope.row, 2)"
                  v-if="scope.row.chuliZhuangtai === 0"
                >
                  驳回
                </el-button>
                <span v-else>已处理</span>
              </div>
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

    <!-- 处理申诉弹窗（修复模板闭合错误） -->
    <el-dialog
      title="处理申诉"
      :visible.sync="handleDialogVisible"
      width="40%"
      center
      :close-on-click-modal="false"
    >
      <el-form
        ref="handleFormRef"
        :model="handleForm"
        label-width="100px"
      >
        <el-form-item label="申诉ID">
          <el-input v-model="handleForm.id" disabled />
        </el-form-item>
        <el-form-item label="处理结果" prop="chuliZhuangtai">
          <el-select v-model="handleForm.chuliZhuangtai" disabled>
            <el-option label="确认（撤销违规）" value="1" />
            <el-option label="驳回" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理备注" prop="chuliBeizhu">
          <el-input
            v-model="handleForm.chuliBeizhu"
            type="textarea"
            placeholder="输入处理备注（选填）"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitHandle">确定</el-button>
      </div>
    </el-dialog>

    <!-- 违规详情弹窗 -->
    <el-dialog
      title="关联违规记录详情"
      :visible.sync="weiguiDetailVisible"
      width="50%"
      center
    >
      <el-table :data="weiguiDetailData" border v-loading="detailLoading">
        <el-table-column prop="id" label="违规ID" />
        <el-table-column prop="xuehao" label="学号" />
        <el-table-column prop="xingming" label="姓名" />
        <el-table-column prop="weiguiLeixing" label="违规类型">
          <template slot-scope="scope">
            {{ getLeixingName(scope.row.weiguiLeixing) }}
          </template>
        </el-table-column>
        <el-table-column prop="weiguiShijian" label="违规时间" />
        <el-table-column prop="zixishiMingcheng" label="自习室" />
        <el-table-column prop="weiguiBeizhu" label="违规详情" />
      </el-table>
      <div slot="footer">
        <el-button @click="weiguiDetailVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 完全复用黑名单的http导入逻辑
import http from '@/utils/http'
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'ShenSu',
  data() {
    return {
      tableData: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      searchForm: {
        xuehao: "",
        chuliZhuangtai: ""
      },
      handleDialogVisible: false,
      handleForm: {
        id: '',
        chuliZhuangtai: '',
        chuliBeizhu: ''
      },
      weiguiDetailVisible: false,
      weiguiDetailData: [],
      detailLoading: false
    };
  },
  mounted() {
    this.getShenSuList();
  },
  methods: {
    // 复用违规记录页面的日期格式化逻辑（解决NaN问题）
    formatDate(dateData) {
      if (!dateData) return "-";
      try {
        // 情况1：处理数组格式（如[2025,12,15,10,20]）
        if (Array.isArray(dateData)) {
          const [year, month, day, hour = 0, minute = 0, second = 0] = dateData;
          return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')} ${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}:${String(second).padStart(2, '0')}`;
        }

        // 情况2：标准字符串格式（yyyy-MM-dd HH:mm:ss）
        const standardReg = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
        if (typeof dateData === 'string' && standardReg.test(dateData)) {
          return dateData;
        }

        // 情况3：纯数字字符串（如20251215102000）
        const cleaned = typeof dateData === 'string' ? dateData.replace(/[^\d]/g, "") : '';
        if (cleaned.length === 14) {
          return `${cleaned.slice(0,4)}-${cleaned.slice(4,6)}-${cleaned.slice(6,8)} ${cleaned.slice(8,10)}:${cleaned.slice(10,12)}:${cleaned.slice(12,14)}`;
        }

        // 情况4：兜底显示
        return String(dateData);
      } catch (e) {
        return String(dateData) || "-";
      }
    },

    // 处理状态名称映射
    getStatusName(status) {
      const statusMap = {
        0: '待处理',
        1: '已确认',
        2: '已驳回'
      };
      return statusMap[status] || '未知';
    },

    // 违规类型映射
    getLeixingName(type) {
      if (!type) return '未知';
      const typeMap = {
        chifan: '教室乱吃乱喝',
        weiqiandao: '未签到',
        xuanhua: '大声喧哗',
        suibiaobian: '随地大小便',
        other: '其他'
      };
      return typeMap[type] || type || '未知';
    },

    // 核心：请求路径适配http.js的baseURL
    async getShenSuList() {
      this.loading = true;
      try {
        const response = await http.get("/api/shensu/list", {
          params: {
            page: this.pageNum,
            size: this.pageSize,
            xuehao: this.searchForm.xuehao,
            chuliZhuangtai: this.searchForm.chuliZhuangtai
          }
        });
        const res = response.data;
        this.tableData = res.data?.records || res.data || [];
        this.total = res.data?.total || res.total || 0;
        console.log("申诉数据：", this.tableData);
      } catch (error) {
        console.error("获取申诉失败：", error);
        Message.error("获取申诉列表失败，请检查后端服务！");
        this.tableData = [];
        this.total = 0;
      } finally {
        this.loading = false;
      }
    },

    // 查看违规详情
    async viewWeiguiDetail(weiguiRecordId) {
      if (!weiguiRecordId) {
        Message.warning("无关联的违规记录ID");
        return;
      }
      this.detailLoading = true;
      this.weiguiDetailVisible = true;
      try {
        const response = await http.get("/api/weigui/detail", {
          params: { id: weiguiRecordId }
        });
        const res = response.data;
        this.weiguiDetailData = [res.data] || [];
      } catch (error) {
        console.error("查询违规详情失败：", error);
        Message.error("查询违规详情失败！");
        this.weiguiDetailData = [];
      } finally {
        this.detailLoading = false;
      }
    },

    // 打开处理弹窗
    handleShenSu(row, status) {
      this.handleForm = {
        id: row.id,
        chuliZhuangtai: status + '',
        chuliBeizhu: ''
      };
      this.handleDialogVisible = true;
    },

    // 提交处理
    async submitHandle() {
      try {
        await MessageBox.confirm(
          this.handleForm.chuliZhuangtai === '1' ? '确认后该违规记录将被撤销，是否继续？' : '确定驳回该申诉吗？',
          "提示",
          { type: "warning" }
        );
        const response = await http.post("/api/shensu/handle", {
          id: this.handleForm.id,
          chuliZhuangtai: parseInt(this.handleForm.chuliZhuangtai),
          chuliRen: "admin",
          chuliBeizhu: this.handleForm.chuliBeizhu.trim()
        });
        const res = response.data;
        if (res.code === 0 || !res.code) {
          Message.success("申诉处理成功");
          this.handleDialogVisible = false;
          this.getShenSuList();
        } else {
          Message.error(res.msg || "处理失败");
        }
      } catch (error) {
        if (error !== "cancel") {
          Message.error("解除失败，请稍后重试");
        }
      }
    },

    // 分页/搜索逻辑
    handleSearch() {
      this.pageNum = 1;
      this.getShenSuList();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.pageNum = 1;
      this.getShenSuList();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getShenSuList();
    }
  }
};
</script>

<style lang="scss" scoped>
.shensu-container {
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

      .id-text {
        color: #409eff;
        font-weight: 500;
      }
      /* 新增：操作按钮组样式，确保并列显示 */
      .operate-btn-group {
        display: flex;
        gap: 8px;
        align-items: center;
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
}

.el-table {
  --el-table-header-text-color: #666;
  --el-table-row-hover-bg-color: #f8f9fa;
  .el-table__header-wrapper th {
    background-color: #f5f7fa;
    font-weight: 500;
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

.dialog-footer {
  text-align: center;
}
</style>