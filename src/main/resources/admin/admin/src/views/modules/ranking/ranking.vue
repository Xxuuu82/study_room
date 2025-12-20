<template>
  <div style="width: 100%; min-height: 100vh; padding: 0; box-sizing: border-box; background: #fff;">
    <!-- 列表容器 -->
    <div class="list-preview" :style='{"width":"100%","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"0 auto","position":"relative","background":"#fff","boxSizing":"border-box","padding":"0"}'>
      <!-- 查询表单 -->
      <div class="search-form-container" :style='{"width":"100%","padding":"20px","background":"#f8f9fa","borderBottom":"1px solid #e8e8e8"}'>
        <el-form
            class="center-form-pv"
            :style="{ width: '100%', margin: '0 auto', boxSizing: 'border-box' }"
            :inline="true"
            :model="searchForm"
        >
          <el-row :style="{ flexDirection: 'row', display: 'flex', flexWrap: 'wrap', justifyContent: 'flex-start', alignItems: 'center', gap: '20px' }">
            <div
                :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
            >
              <label
                  :style="{
                  margin: '0 0 8px 0',
                  color: '#333',
                  textAlign: 'left',
                  display: 'inline-block',
                  width: '100%',
                  lineHeight: '1',
                  fontSize: '14px',
                  fontWeight: '600',
                  height: 'auto',
                }"
                  class="item-label"
              >学号</label>
              <el-input
                  v-model="searchForm.studentId"
                  placeholder="请输入学号"
                  clearable
                  style="width: 100%;"
              ></el-input>
            </div>
            <div
                :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
            >
              <label
                  :style="{
                  margin: '0 0 8px 0',
                  color: '#333',
                  textAlign: 'left',
                  display: 'inline-block',
                  width: '100%',
                  lineHeight: '1',
                  fontSize: '14px',
                  fontWeight: '600',
                  height: 'auto',
                }"
                  class="item-label"
              >学生姓名</label>
              <el-input
                  v-model="searchForm.name"
                  placeholder="请输入学生姓名"
                  clearable
                  style="width: 100%;"
              ></el-input>
            </div>
            <div :style="{ margin: 'auto 0 0', minWidth: '120px' }">
              <el-button
                  :style="{
                  border: 'none',
                  cursor: 'pointer',
                  padding: '0 24px',
                  boxShadow: '0px 2px 4px 0px rgba(0,0,0,0.1)',
                  outline: 'none',
                  margin: '28px 0 0',
                  color: '#fff',
                  borderRadius: '4px',
                  background: '#409EFF',
                  width: '100%',
                  fontSize: '14px',
                  height: '40px',
                  transition: 'all 0.3s',
                }"
                  type="primary"
                  @click="search()"
              >查询</el-button>
            </div>
            <div :style="{ margin: 'auto 0 0', minWidth: '120px' }">
              <el-button
                  :style="{
                  border: 'none',
                  cursor: 'pointer',
                  padding: '0 24px',
                  boxShadow: '0px 2px 4px 0px rgba(0,0,0,0.1)',
                  outline: 'none',
                  margin: '28px 0 0',
                  color: '#fff',
                  borderRadius: '4px',
                  background: '#93C7B3',
                  width: '100%',
                  fontSize: '14px',
                  fontWeight: '600',
                  height: '40px',
                  transition: 'all 0.3s',
                }"
                  type="success"
                  @click="addOrUpdateHandler()"
              >新增</el-button>
            </div>
            <div :style="{ margin: 'auto 0 0', minWidth: '120px' }">
              <el-button
                  :style="{
                  border: 'none',
                  cursor: 'pointer',
                  padding: '0 24px',
                  boxShadow: '0px 2px 4px 0px rgba(0,0,0,0.1)',
                  outline: 'none',
                  margin: '28px 0 0',
                  color: '#fff',
                  borderRadius: '4px',
                  background: '#F56C6C',
                  width: '100%',
                  fontSize: '14px',
                  height: '40px',
                  transition: 'all 0.3s',
                }"
                  :disabled="dataListSelections.length <= 0"
                  type="danger"
                  @click="deleteHandler()"
              >删除</el-button>
            </div>
          </el-row>
        </el-form>
      </div>

      <!-- 表格区域 -->
      <div class="table-container" :style='{"padding":"20px"}'>
        <!-- 数据表格 -->
        <el-table
            class="custom-table"
            :stripe="true"
            :style="{
            padding: '0',
            borderColor: '#eee',
            borderRadius: '0',
            borderWidth: '1px',
            background: '#fff',
            width: '100%',
            margin: '0',
            borderStyle: 'solid',
            height: 'fit-content',
            boxSizing: 'border-box',
          }"
            :data="dataList"
            v-loading="dataListLoading"
            @selection-change="selectionChangeHandler"
            border
        >
          <el-table-column
              :resizable="true"
              type="selection"
              align="center"
              width="50"
          ></el-table-column>
          <el-table-column
              label="排名"
              width="80"
              align="center"
              header-align="center"
              class-name="index-column"
          >
            <template #default="{ row }">
              <span class="index-badge">{{ row.ranking }}</span>
            </template>
          </el-table-column>

          <!-- 学生姓名 -->
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="name"
              label="学生姓名"
              min-width="100"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <div class="name-text" :title="row.name">
                {{ row.name || "-" }}
              </div>
            </template>
          </el-table-column>

          <!-- 学号：适配数据库原生字段名 student_id -->
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="student_id"
              label="学号"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <div class="id-number" :title="row.student_id">
                {{ row.student_id || "-" }}
              </div>
            </template>
          </el-table-column>

          <!-- 自习时长：适配数据库原生字段名 study_duration_min -->
          <el-table-column
              :resizable="true"
              prop="study_duration_min"
              label="自习时长"
              width="150"
              align="center"
              header-align="center"
              sortable="custom"
          >
            <template #default="{ row }">
              <el-tooltip :content="`${row.study_duration_min || 0}分钟`" placement="top">
                <div class="duration-badge">
                  {{ formatTime(row.study_duration_min) }}
                </div>
              </el-tooltip>
            </template>
          </el-table-column>

          <!-- 班级：恢复为原来的 className 格式 -->
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="className"
              label="班级"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <el-tag type="info" effect="dark" class="class-tag">
                {{ row.className || "-" }}
              </el-tag>
            </template>
          </el-table-column>

          <!-- 手机 -->
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="phone"
              label="手机号"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <div class="phone-number">
                {{ row.phone || "-" }}
              </div>
            </template>
          </el-table-column>

          <!-- 操作列：恢复原始修改按钮逻辑（传student_id） -->
          <el-table-column
              label="操作"
              width="150"
              align="center"
              header-align="center"
              fixed="right"
          >
            <template slot-scope="scope">
              <div style="display: flex; gap: 6px; justifyContent: center; alignItems: center; flexWrap: wrap;">
                <el-button
                    type="primary"
                    size="mini"
                    @click="showDetail(scope.row)"
                    style="border-radius: 4px; padding: 5px 12px; height: 30px; line-height: 20px; transition: all 0.2s; border-color: #409EFF; background: #409EFF; color: #fff; border: none;"
                >详情</el-button>
                <el-button
                    type="warning"
                    size="mini"
                    @click="addOrUpdateHandler(scope.row.student_id)"
                    style="border-radius: 4px; padding: 5px 12px; height: 30px; line-height: 20px; transition: all 0.2s; border-color: #E6A23C; background: #E6A23C; color: #fff; border: none;"
                >修改</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 详情弹窗：保持当前样式 -->
    <el-dialog
        title="详情信息"
        :visible.sync="detailDialogVisible"
        width="50%"
        :close-on-click-modal="false"
    >
      <div v-if="currentDetailData" style="padding: 20px;">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="排名">{{ currentDetailData.ranking || "未上榜" }}</el-descriptions-item>
          <el-descriptions-item label="学生姓名">{{ currentDetailData.name || "-" }}</el-descriptions-item>
          <el-descriptions-item label="学号">{{ currentDetailData.student_id || "-" }}</el-descriptions-item>
          <el-descriptions-item label="班级">{{ currentDetailData.className || "-" }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ currentDetailData.phone || "-" }}</el-descriptions-item>
          <el-descriptions-item label="自习时长">{{ formatTime(currentDetailData.study_duration_min) }}</el-descriptions-item>
        </el-descriptions>
      </div>
      <div v-else style="padding: 20px; text-align: center; color: #999;">暂无该学生的详情数据</div>
      <!-- 完全对齐修改弹窗的按钮样式 -->
      <template slot="footer">
        <div style="text-align: center; width: 100%;">
          <el-button
            @click="detailDialogVisible = false"
            plain
            style="min-width: 80px; height: 32px; padding: 0 15px; margin: 0 5px; cursor: pointer; font-size: 14px; border-radius: 4px;"
          >
            关 闭
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 添加/修改页面：恢复原始父组件传递方式 -->
    <add-or-update
        v-if="addOrUpdateFlag"
        :parent="this"
        ref="addOrUpdate"
    ></add-or-update>

    <!-- 加载状态 -->
    <el-skeleton :rows="6" animated v-if="dataListLoading" />
  </div>
</template>

<script>
import AddOrUpdate from "./add-or-update";
import { isAuth } from "../../../utils/utils";
export default {
  data() {
    return {
      searchForm: {
        studentId: "",
        name: ""
      },
      dataList: [],
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateFlag: false,
      detailDialogVisible: false,
      currentDetailData: null,
      // 移除多余的currentRowData（恢复原始逻辑）
    };
  },
  created() {
    this.getDataList();
  },
  components: {
    AddOrUpdate,
  },
  methods: {
    isAuth,

    search() {
      this.getDataList();
    },

    // 获取数据列表：保留你原有逻辑
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: "/ranking/list",
        method: "get",
      }).then(({ data }) => {
        console.log("后端返回完整数据：", data);
        console.log("后端返回列表数据：", data.rows || data.data);

        if (data && data.code === 0) {
          let list = data.rows || data.data || [];
          if (list.length > 0) {
            if (this.searchForm.studentId) {
              list = list.filter(item => item.student_id && item.student_id.includes(this.searchForm.studentId));
            }
            if (this.searchForm.name) {
              list = list.filter(item => item.name && item.name.includes(this.searchForm.name));
            }
          }
          this.dataList = list;
        } else {
          this.dataList = [];
          this.$message.error(data?.msg || "获取数据失败");
        }
        this.dataListLoading = false;
      }).catch((error) => {
        this.dataListLoading = false;
        console.error("请求错误:", error);
        this.$message.error("请求失败");
      });
    },

    // 多选：保留你原有逻辑
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },

    // 新增/修改：恢复原始逻辑（传student_id）
    addOrUpdateHandler(studentId) {
      this.addOrUpdateFlag = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(studentId);
      });
    },

    // 详情弹窗：保持当前正确逻辑
    showDetail(row) {
      this.detailDialogVisible = true;
      this.currentDetailData = row;
    },

    // 删除：保留你原有逻辑
    deleteHandler() {
      if (this.dataListSelections.length === 0) {
        this.$message.warning("请选择要删除的数据");
        return;
      }
      const studentIds = this.dataListSelections.map(item => item.student_id).filter(Boolean);
      this.$confirm(`确定删除选中数据?`, "提示", {
        type: "warning",
      }).then(() => {
        this.$http({
          url: "/ranking/delete",
          method: "post",
          data: studentIds,
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message.success("删除成功");
            this.getDataList();
          } else {
            this.$message.error(data?.msg || "删除失败");
          }
        }).catch((err) => {
          this.$message.error("删除失败");
          console.error(err);
        });
      });
    },

    // 格式化时长：保留你原有逻辑
    formatTime(minutes) {
      const num = Number(minutes);
      if (!num || num <= 0) return "0分钟";
      const hours = Math.floor(num / 60);
      const mins = num % 60;
      let result = "";
      if (hours > 0) result += `${hours}小时`;
      if (mins > 0 || hours === 0) result += `${mins}分钟`;
      return result;
    },
  }
};
</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

.list-preview {
  border-radius: 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.search-form-container {
  .center-form-pv {
    .el-input {
      width: 100%;
    }
    .el-input__inner {
      border: 1px solid #dcdfe6;
      border-radius: 4px;
      padding: 0 12px;
      outline: none;
      color: #606266;
      font-size: 14px;
      height: 40px;
      line-height: 40px;
      transition: border-color 0.3s;

      &:hover {
        border-color: #c0c4cc;
      }
      &:focus {
        border-color: #409EFF;
        box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.1);
      }
    }

    .el-button {
      &:hover {
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
      }
    }
  }
}

.custom-table {
  .el-table__header-wrapper {
    th {
      background: #f8f9fa !important;
      color: #495057 !important;
      font-weight: 600 !important;
      font-size: 14px !important;
      border-bottom: 2px solid #e9ecef !important;
      padding: 12px 0 !important;
    }
  }

  .el-table__body-wrapper {
    .el-table__row {
      transition: all 0.3s ease;
      &:hover {
        background: #f5f7fa !important;
      }
      td {
        border-color: #e9ecef !important;
        padding: 10px 0 !important;
        font-size: 14px !important;
      }
    }
  }

  .index-column .index-badge {
    display: inline-block;
    width: 24px;
    height: 24px;
    line-height: 24px;
    border-radius: 50%;
    background: #409EFF;
    color: white;
    font-weight: 500;
    font-size: 12px;
  }

  .duration-badge {
    padding: 4px 8px;
    background: #e3f2fd;
    border-radius: 12px;
    color: #2196f3;
    font-weight: 500;
    display: inline-block;
    min-width: 80px;
  }

  .name-text, .id-number, .phone-number {
    font-weight: 500;
    max-width: 120px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin: 0 auto;
  }

  .class-tag {
    font-weight: 500;
    letter-spacing: 0.5px;
  }
}
</style>