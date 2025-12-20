<template>
  <div class="violation-container">
    <!-- 违规记录管理卡片 -->
    <el-card class="info-card" shadow="hover">
      <div class="card-header">
        <h2>违规记录管理</h2>
        <el-tag type="warning" size="small">单次违规记录，累计≥3次自动拉黑</el-tag>
      </div>

      <!-- 搜索栏 -->
      <div class="search-form">
        <el-form :model="searchForm" inline>
          <el-form-item label="学号">
            <el-input
              v-model="searchForm.xuehao"
              placeholder="请输入学号搜索"
              clearable
              style="width: 200px"
            />
          </el-form-item>
          <el-form-item label="违规类型">
            <el-select
              v-model="searchForm.weiguiLeixing"
              placeholder="请选择违规类型"
              clearable
              style="width: 150px"
            >
              <el-option label="全部" value="" />
              <el-option label="教室乱吃乱喝" value="chifan" />
              <el-option label="未签到" value="weiqiandao" />
              <el-option label="大声喧哗" value="xuanhua" />
              <el-option label="随地大小便" value="suibiaobian" />
              <el-option label="其他" value="other" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="openAddDialog">新增违规记录</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 违规记录列表表格：新增ID列 -->
      <div class="table-container">
        <el-table
          :data="tableData"
          border
          stripe
          v-loading="loading"
          style="width: 100%"
          :empty-text="loading ? '加载中...' : '暂无违规记录数据'"
        >
          <!-- 新增：数据库主键ID列 -->
          <el-table-column prop="id" label="记录ID" width="100" align="center">
            <template slot-scope="scope">
              <span class="id-text">{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <!-- 学号 -->
          <el-table-column prop="xuehao" label="学号" width="180" />
          <!-- 姓名 -->
          <el-table-column prop="xingming" label="姓名" width="180" />
          <!-- 违规类型 -->
          <el-table-column label="违规类型" width="200">
            <template slot-scope="scope">
              <span class="type-text">{{ getLeixingName(scope.row.weiguiLeixing) }}</span>
            </template>
          </el-table-column>
          <!-- 违规时间 -->
          <el-table-column label="违规时间" width="210">
            <template slot-scope="scope">
              {{ formatDate(scope.row.weiguiShijian) }}
            </template>
          </el-table-column>
          <!-- 违规自习室 -->
          <el-table-column prop="zixishiMingcheng" label="违规自习室" width="180" />
          <!-- 违规详情 -->
          <el-table-column label="违规详情" min-width="350">
            <template slot-scope="scope">
              {{ scope.row.weiguiBeizhu || '-' }}
            </template>
          </el-table-column>
          <!-- 操作 -->
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button
                type="danger"
                size="small"
                @click="deleteViolation(scope.row)"
              >
                删除
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

    <!-- 新增违规记录弹窗 -->
    <el-dialog
      title="新增违规记录"
      :visible.sync="addDialogVisible"
      width="50%"
      center
      :close-on-click-modal="false"
    >
      <el-form
        ref="addFormRef"
        :model="addForm"
        label-width="120px"
        :rules="addRules"
      >
        <el-form-item label="学号" prop="xuehao">
          <el-input v-model="addForm.xuehao" placeholder="请输入学生学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="xingming">
          <el-input v-model="addForm.xingming" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="违规类型" prop="weiguiLeixing">
          <el-select v-model="addForm.weiguiLeixing" placeholder="选择违规类型">
            <el-option label="教室乱吃乱喝" value="chifan" />
            <el-option label="未签到" value="weiqiandao" />
            <el-option label="大声喧哗" value="xuanhua" />
            <el-option label="随地大小便" value="suibiaobian" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
        <el-form-item label="违规时间" prop="weiguiShijian">
          <el-date-picker
            v-model="addForm.weiguiShijian"
            type="datetime"
            placeholder="选择违规时间"
            style="width: 100%;"
            value-format="yyyy-MM-dd HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="违规自习室" prop="zixishiMingcheng">
          <el-input v-model="addForm.zixishiMingcheng" placeholder="输入违规自习室名称" />
        </el-form-item>
        <el-form-item label="违规详情" prop="weiguiBeizhu">
          <el-input
            v-model="addForm.weiguiBeizhu"
            type="textarea"
            placeholder="输入违规详情（如：2025-10-20 14:00在大自习室21号座位未签到）"
            rows="3"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAddForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import http from '@/utils/http'
import storage from '@/utils/storage'
import router from '@/router/router-static'
import { Message, MessageBox } from 'element-ui'

// 请求参数编码
http.interceptors.request.use(config => {
  if (config.params) {
    for (const key in config.params) {
      if (typeof config.params[key] === 'string') {
        config.params[key] = encodeURIComponent(config.params[key])
      }
    }
  }
  config.headers['Content-Type'] = 'application/json;charset=utf-8'
  return config
})

// 响应解码
http.interceptors.response.use(
  response => {
    let data = response.data
    if (typeof data === 'string') {
      try {
        data = decodeURIComponent(escape(data))
        data = JSON.parse(data)
      } catch (e) {}
    }
    response.data = data
    return response
  },
  error => {
    if (error.response?.data && typeof error.response.data === 'string') {
      try {
        error.response.data = decodeURIComponent(escape(error.response.data))
        error.response.data = JSON.parse(error.response.data)
      } catch (e) {}
    }
    return Promise.reject(error)
  }
)

export default {
  name: 'WeiguiRecord',
  data() {
    return {
      tableData: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      searchForm: {
        xuehao: "",
        weiguiLeixing: ""
      },
      addDialogVisible: false,
      // 新增表单：纯驼峰字段（和后端保持一致）
      addForm: {
        xuehao: '',
        xingming: '',
        weiguiLeixing: '',
        weiguiShijian: '',
        zixishiMingcheng: '',
        weiguiBeizhu: ''
      },
      addRules: {
        xuehao: [{ required: true, message: '请输入学号', trigger: 'blur' }],
        xingming: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        weiguiLeixing: [{ required: true, message: '请选择违规类型', trigger: 'change' }],
        weiguiShijian: [{ required: true, message: '请选择违规时间', trigger: 'change' }],
        zixishiMingcheng: [{ required: true, message: '请输入违规自习室', trigger: 'blur' }],
        weiguiBeizhu: [{ required: true, message: '请输入违规详情', trigger: 'blur' }]
      }
    };
  },
  mounted() {
    this.getViolationList();
  },
  methods: {
    // 违规类型中文转换
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

    // 日期格式化（兼容数组、字符串等所有格式）
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

    // 获取违规记录列表（适配后端Map返回格式）
    async getViolationList() {
      this.loading = true;
      try {
        const token = storage.get('Token');
        if (!token) {
          Message.warning("请先登录系统");
          router.push({ name: 'login' });
          this.loading = false;
          return;
        }

        const params = {
          page: this.pageNum,
          size: this.pageSize,
          xuehao: this.searchForm.xuehao?.trim() || '',
          weiguiLeixing: this.searchForm.weiguiLeixing || ''
        };

        const response = await http.get("/api/weigui/list", {
          params,
          headers: {
            'Token': token,
            'Accept': 'application/json;charset=utf-8'
          }
        });

        const res = response.data;
        // 适配后端Map返回格式
        if (res.code === 0) {
          this.tableData = res.data || []; // 列表数据（包含id字段）
          this.total = res.total || 0;     // 总条数
        } else {
          this.tableData = [];
          this.total = 0;
        }
      } catch (error) {
        console.error("获取违规记录失败：", error);
        Message.error("获取列表失败：" + (error.message || "网络异常"));
        this.tableData = [];
        this.total = 0;
      } finally {
        this.loading = false;
      }
    },

    // 提交新增表单（保持原JSON提交逻辑）
    async submitAddForm() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (valid) {
          try {
            const token = storage.get('Token');
            if (!token) {
              Message.warning("请先登录系统");
              router.push({ name: 'login' });
              return;
            }

            // 提交数据：保持原字段，后端用Map接收
            const submitData = {
              xuehao: this.addForm.xuehao.trim(),
              xingming: this.addForm.xingming.trim(),
              zixishiMingcheng: this.addForm.zixishiMingcheng.trim(),
              weiguiShijian: this.addForm.weiguiShijian,
              weiguiLeixing: this.addForm.weiguiLeixing,
              weiguiBeizhu: this.addForm.weiguiBeizhu.trim(),
              isValid: 1 // 有效标记：申诉通过后会被改为0
            };

            // 执行新增请求
            const response = await http.post("/api/weigui/add", submitData, {
              headers: {
                'Token': token,
                'Content-Type': 'application/json;charset=utf-8'
              }
            });

            if (response.data.code === 0) {
              // 新增成功：刷新列表 + 关闭弹窗 + 重置表单
              setTimeout(async () => {
                await this.getViolationList();
                Message.success("新增违规记录成功");
                this.addDialogVisible = false;
                this.$refs.addFormRef.resetFields();
              }, 500);
            } else {
              Message.error("新增失败：" + (response.data.msg || "未知错误"));
            }
          } catch (error) {
            console.error("新增请求异常：", error);
            // 详细错误提示（方便排查）
            let errorMsg = "新增失败：";
            if (error.response) {
              errorMsg += `状态码${error.response.status} - ${JSON.stringify(error.response.data)}`;
            } else {
              errorMsg += error.message || "网络异常";
            }
            Message.error(errorMsg);
          }
        } else {
          Message.warning("请填写完整必填信息");
        }
      });
    },

    // 删除违规记录
    async deleteViolation(row) {
      try {
        await MessageBox.confirm(
          `确定要删除【记录ID：${row.id} | ${row.xingming || row.xuehao}】的这条违规记录吗？`,
          "提示",
          { type: "warning" }
        );

        const token = storage.get('Token');
        const response = await http.post("/api/weigui/delete", {}, {
          params: { id: row.id },
          headers: { 'Token': token }
        });

        if (response.data.code === 0) {
          setTimeout(async () => {
            await this.getViolationList();
            Message.success("删除违规记录成功");
          }, 500);
        } else {
          Message.error("删除失败：" + (response.data.msg || "未知错误"));
        }
      } catch (error) {
        if (error !== "cancel" && !error.message?.includes("cancel")) {
          console.error("删除请求异常：", error);
          Message.error("删除失败：" + (error.message || "网络异常"));
        }
      }
    },

    // 打开新增弹窗（重置表单）
    openAddDialog() {
      this.addForm = {
        xuehao: '',
        xingming: '',
        weiguiLeixing: '',
        weiguiShijian: '',
        zixishiMingcheng: '',
        weiguiBeizhu: ''
      };
      this.$nextTick(() => {
        this.$refs.addFormRef?.resetFields();
      });
      this.addDialogVisible = true;
    },

    // 搜索
    handleSearch() {
      this.pageNum = 1;
      this.getViolationList();
    },

    // 分页：每页条数变化
    handleSizeChange(val) {
      this.pageSize = val;
      this.pageNum = 1;
      this.getViolationList();
    },

    // 分页：当前页变化
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getViolationList();
    }
  }
};
</script>

<style lang="scss" scoped>
.violation-container {
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

      .type-text {
        color: #e6a23c;
        font-weight: 600;
      }
      // 新增ID文本样式
      .id-text {
        color: #409eff;
        font-weight: 500;
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

.el-dialog__body {
  padding: 20px;
}

.dialog-footer {
  text-align: center;
}
</style>