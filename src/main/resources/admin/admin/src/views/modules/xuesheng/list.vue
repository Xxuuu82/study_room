<template>
  <div style="width: 100%; min-height: 100vh; padding: 0; box-sizing: border-box; background: #fff;">
    <!-- 列表容器 -->
    <div class="list-preview" :style='{"width":"100%","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"0 auto","position":"relative","background":"#fff","boxSizing":"border-box","padding":"0"}'>
      <!-- 查询表单 - 顶格显示 -->
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
                  v-model="searchForm.xuehao"
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
              >姓名</label>
              <el-input
                  v-model="searchForm.xingming"
                  placeholder="请输入姓名"
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
              >班级</label>
              <el-input
                  v-model="searchForm.banji"
                  placeholder="请输入班级"
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
          </el-row>
        </el-form>
      </div>

      <!-- 表格区域 -->
      <div class="table-container" :style='{"padding":"20px"}'>
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
            border
        >
          <el-table-column
              type="index"
              label="索引"
              width="80"
              align="center"
              header-align="center"
              class-name="index-column"
          >
            <template #default="{ $index }">
              <span class="index-badge">{{ $index + 1 + (pageIndex - 1) * pageSize }}</span>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="xuehao"
              label="学号"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <span style="color: #333; font-weight: 500;">{{ scope.row.xuehao || '-' }}</span>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="xingming"
              label="姓名"
              min-width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.xingming || '-' }}
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="xingbie"
              label="性别"
              width="80"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <el-tag :type="scope.row.xingbie === '男' ? 'primary' : 'danger'" style="borderRadius: 12px; padding: 2px 10px;">
                {{ scope.row.xingbie || '-' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="touxiang"
              label="头像"
              width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <div v-if="scope.row.touxiang" style="display: inline-block;">
                <img
                    v-if="scope.row.touxiang.substring(0, 4) == 'http'"
                    :src="scope.row.touxiang.split(',')[0]"
                    width="40"
                    height="40"
                    style="objectFit: cover; borderRadius: 50%; border: 1px solid #f0f0f0;"
                />
                <img
                    v-else
                    :src="$base.url + scope.row.touxiang.split(',')[0]"
                    width="40"
                    height="40"
                    style="objectFit: cover; borderRadius: 50%; border: 1px solid #f0f0f0;"
                />
              </div>
              <div v-else style="color: #999; font-size: 12px;">无头像</div>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="shouji"
              label="手机"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.shouji || '-' }}
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="banji"
              label="班级"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <span style="color: #409EFF; font-weight: 500;">{{ scope.row.banji || '-' }}</span>
            </template>
          </el-table-column>
          <!-- 操作列 -->
          <el-table-column
              label="操作"
              width="160"
              align="center"
              header-align="center"
              fixed="right"
          >
            <template slot-scope="scope">
              <div style="display: flex; gap: 6px; justifyContent: center; alignItems: center; flexWrap: wrap;">
                <!-- 详情按钮 -->
                <el-button
                    type="primary"
                    size="mini"
                    @click="addOrUpdateHandler(scope.row.id,'info')"
                    style="borderRadius: 4px; padding: 5px 12px; height: 30px; lineHeight: 20px; transition: all 0.2s; borderColor: #409EFF; background: #409EFF; color: #fff; border: none;"
                >详情</el-button>
                <!-- 修改按钮 -->
                <el-button
                    type="warning"
                    size="mini"
                    @click="editHandler(scope.row.id)"
                    style="borderRadius: 4px; padding: 5px 12px; height: 30px; lineHeight: 20px; transition: all 0.2s; borderColor: #E6A23C; background: #E6A23C; color: #fff; border: none;"
                >修改</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination-container" :style='{"marginTop":"20px","paddingTop":"20px","borderTop":"1px solid #eee"}'>
          <el-pagination
              @size-change="sizeChangeHandle"
              @current-change="currentChangeHandle"
              :current-page="pageIndex"
              background
              :page-sizes="[10, 20, 30, 50]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalPage"
              :hide-on-single-page="false"
              :style="{
              width: '100%',
              margin: '0 auto',
              whiteSpace: 'nowrap',
              color: '#333',
              fontWeight: '500',
              textAlign: 'center',
              boxSizing: 'border-box',
            }"
          ></el-pagination>
        </div>
      </div>
    </div>

    <!-- 详情对话框 - 居中模态框 -->
    <el-dialog
        title="学生详情信息"
        :visible.sync="detailDialogVisible"
        width="50%"
        :close-on-click-modal="false"
        center
    >
      <div v-if="currentDetailData" style="padding: 20px;">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="学号">
            <span style="font-weight: 600; color: #333;">{{ currentDetailData.xuehao || "-" }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="姓名">
            {{ currentDetailData.xingming || "-" }}
          </el-descriptions-item>
          <el-descriptions-item label="性别">
            <el-tag :type="currentDetailData.xingbie === '男' ? 'primary' : 'danger'">
              {{ currentDetailData.xingbie || "-" }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="头像" :span="2">
            <div v-if="currentDetailData.touxiang" style="margin-top: 10px;">
              <img
                  v-if="currentDetailData.touxiang.substring(0, 4) == 'http'"
                  :src="currentDetailData.touxiang.split(',')[0]"
                  style="width: 80px; height: 80px; objectFit: cover; borderRadius: 50%; border: 1px solid #f0f0f0;"
              />
              <img
                  v-else
                  :src="$base.url + currentDetailData.touxiang.split(',')[0]"
                  style="width: 80px; height: 80px; objectFit: cover; borderRadius: 50%; border: 1px solid #f0f0f0;"
              />
            </div>
            <div v-else style="color: #999; font-size: 12px;">无头像</div>
          </el-descriptions-item>
          <el-descriptions-item label="手机">
            {{ currentDetailData.shouji || "-" }}
          </el-descriptions-item>
          <el-descriptions-item label="班级">
            <span style="color: #409EFF; font-weight: 500;">{{ currentDetailData.banji || "-" }}</span>
          </el-descriptions-item>
          <el-descriptions-item v-if="currentDetailData.addtime" label="创建时间" :span="2">
            {{ currentDetailData.addtime }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailDialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>

    <!-- 修改对话框 - 居中模态框 -->
    <el-dialog
        title="修改学生信息"
        :visible.sync="editDialogVisible"
        width="50%"
        :close-on-click-modal="false"
        center
    >
      <div v-if="currentEditData" style="padding: 20px;">
        <el-form
          ref="editForm"
          :model="currentEditData"
          :rules="editRules"
          label-width="100px"
          label-position="left"
        >
          <el-form-item label="学号" prop="xuehao">
            <el-input
              v-model="currentEditData.xuehao"
              placeholder="请输入学号"
              :disabled="true"
            ></el-input>
          </el-form-item>

          <el-form-item label="姓名" prop="xingming">
            <el-input
              v-model="currentEditData.xingming"
              placeholder="请输入姓名"
            ></el-input>
          </el-form-item>

          <el-form-item label="性别" prop="xingbie">
            <el-radio-group v-model="currentEditData.xingbie">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="手机" prop="shouji">
            <el-input
              v-model="currentEditData.shouji"
              placeholder="请输入手机号"
            ></el-input>
          </el-form-item>

          <el-form-item label="班级" prop="banji">
            <el-input
              v-model="currentEditData.banji"
              placeholder="请输入班级"
            ></el-input>
          </el-form-item>

          <el-form-item label="头像" prop="touxiang">
            <el-upload
              class="avatar-uploader"
              action="#"
              :show-file-list="false"
              :on-change="handleAvatarChange"
              :auto-upload="false"
            >
              <img v-if="currentEditData.touxiang" :src="getImageUrl(currentEditData.touxiang)" class="avatar" style="width: 100px; height: 100px; border-radius: 50%; object-fit: cover; border: 1px solid #f0f0f0;">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">保 存</el-button>
      </span>
    </el-dialog>

    <!-- 添加/修改页面（原有的完整页面，保留备用） -->
    <add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>
  </div>
</template>

<script>
import axios from 'axios'
import AddOrUpdate from "./add-or-update";
export default {
  data() {
    return {
      searchForm: {
        xuehao: "",
        xingming: "",
        banji: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      sfshVisiable: false,
      shForm: {},
      chartVisiable: false,
      chartVisiable1: false,
      chartVisiable2: false,
      chartVisiable3: false,
      chartVisiable4: false,
      chartVisiable5: false,
      addOrUpdateFlag: false,

      // 详情模态框相关数据
      detailDialogVisible: false, // 控制详情模态框显示/隐藏
      currentDetailData: null, // 当前查看的学生详情数据

      // 修改模态框相关数据
      editDialogVisible: false, // 控制修改模态框显示/隐藏
      currentEditData: null, // 当前修改的学生数据

      // 表单验证规则
      editRules: {
        xuehao: [
          { required: true, message: '请输入学号', trigger: 'blur' }
        ],
        xingming: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        shouji: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
        ],
        banji: [
          { required: true, message: '请输入班级', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.init();
    this.getDataList();
  },
  mounted() {
  },
  filters: {
    htmlfilter: function (val) {
      return val.replace(/<^>]*>/g).replace(/undefined/g,'');
    }
  },
  components: {
    AddOrUpdate,
  },
  methods: {
    init() {
    },
    search() {
      this.pageIndex = 1;
      this.getDataList();
    },

    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: 'id',
        order: 'desc',
      }
      if(this.searchForm.xuehao!='' && this.searchForm.xuehao!=undefined){
        params['xuehao'] = '%' + this.searchForm.xuehao + '%'
      }
      if(this.searchForm.xingming!='' && this.searchForm.xingming!=undefined){
        params['xingming'] = '%' + this.searchForm.xingming + '%'
      }
      if(this.searchForm.banji!='' && this.searchForm.banji!=undefined){
        params['banji'] = '%' + this.searchForm.banji + '%'
      }
      this.$http({
        url: "xuesheng/page",
        method: "get",
        params: params
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.total;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },

    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },

    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },

    // 多选
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },

    // 详情处理
    addOrUpdateHandler(id, type) {
      if (type === 'info') {
        // 查看详情，显示居中模态框
        this.showDetail(id);
      } else {
        // 修改，显示修改模态框
        this.editHandler(id);
      }
    },

    // 显示详情模态框
    showDetail(id) {
      this.dataListLoading = true;
      this.$http({
        url: `xuesheng/info/${id}`,
        method: "get",
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.currentDetailData = data.data;
          this.detailDialogVisible = true;
        } else {
          this.$message.error(data.msg);
        }
        this.dataListLoading = false;
      }).catch((error) => {
        this.dataListLoading = false;
        console.error("获取详情失败:", error);
        this.$message.error("获取详情失败");
      });
    },

    // 显示修改模态框
    editHandler(id) {
      this.dataListLoading = true;
      this.$http({
        url: `xuesheng/info/${id}`,
        method: "get",
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.currentEditData = { ...data.data };
          this.editDialogVisible = true;
        } else {
          this.$message.error(data.msg);
        }
        this.dataListLoading = false;
      }).catch((error) => {
        this.dataListLoading = false;
        console.error("获取修改数据失败:", error);
        this.$message.error("获取修改数据失败");
      });
    },

    // 处理头像上传
    handleAvatarChange(file) {
      // 这里处理头像上传逻辑
      const reader = new FileReader();
      reader.onload = (e) => {
        this.currentEditData.touxiang = e.target.result;
      };
      reader.readAsDataURL(file.raw);
    },

    // 获取图片URL
    getImageUrl(image) {
      if (!image) return '';
      if (image.substring(0, 4) === 'http') {
        return image.split(',')[0];
      } else {
        return this.$base.url + image.split(',')[0];
      }
    },

    // 保存修改
    saveEdit() {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确定要保存修改吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$http({
              url: "xuesheng/update",
              method: "post",
              data: this.currentEditData
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '修改成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.editDialogVisible = false;
                    this.getDataList(); // 刷新列表
                  }
                });
              } else {
                this.$message.error(data.msg);
              }
            }).catch((error) => {
              console.error("保存失败:", error);
              this.$message.error("保存失败");
            });
          });
        } else {
          this.$message.error('请填写完整信息');
          return false;
        }
      });
    },

    // 下载
    download(file){
      window.open(`${file}`)
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
    .el-input, .el-select {
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
        background: #66b1ff !important;
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
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

    .cell {
      font-weight: 600;
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
}

.pagination-container {
  .el-pagination {
    .el-pagination__total {
      margin: 0 10px 0 0;
      color: #666;
      font-weight: 400;
      font-size: 14px;
    }

    .btn-prev,
    .btn-next,
    .number {
      border: 1px solid #d8dce5;
      border-radius: 4px;
      font-size: 14px;
      min-width: 32px;
      height: 32px;
      line-height: 30px;
    }

    .number.active {
      background: #409EFF;
      border-color: #409EFF;
      color: #fff;
    }

    .number:hover:not(.active) {
      border-color: #409EFF;
      color: #409EFF;
    }

    .el-pagination__jump {
      font-size: 14px;
    }

    .el-input__inner {
      height: 32px;
      line-height: 32px;
    }
  }
}

/* 对话框通用样式 */
.el-dialog {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);

  .el-dialog__header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 20px;

    .el-dialog__title {
      color: white;
      font-weight: 600;
      font-size: 18px;
    }

    .el-dialog__headerbtn {
      .el-dialog__close {
        color: white;
        font-size: 20px;

        &:hover {
          color: #f0f0f0;
        }
      }
    }
  }

  .el-dialog__body {
    padding: 0;

    .el-descriptions {
      .el-descriptions__header {
        display: none;
      }

      .el-descriptions__body {
        background: #f9fafc;

        .el-descriptions__table {
          width: 100%;

          .el-descriptions__label {
            background: #f0f2f5;
            color: #606266;
            font-weight: 500;
            width: 120px;
            padding: 12px 15px;
            border-right: 1px solid #e8e8e8;
          }

          .el-descriptions__content {
            background: white;
            color: #303133;
            padding: 12px 15px;

            .el-tag {
              border-radius: 12px;
              padding: 4px 10px;
              font-weight: 500;
            }
          }

          tr:last-child {
            td {
              border-bottom: none;
            }
          }
        }
      }
    }
  }

  .el-dialog__footer {
    padding: 15px 20px;
    background: #f8f9fa;
    border-top: 1px solid #e8e8e8;

    .dialog-footer {
      display: flex;
      justify-content: flex-end;

      .el-button {
        min-width: 100px;
        border-radius: 4px;
        padding: 10px 20px;

        &:hover {
          transform: translateY(-1px);
          box-shadow: 0 2px 8px rgba(64, 158, 255, 0.3);
        }
      }
    }
  }
}

/* 修改对话框特定样式 */
.el-form {
  padding: 20px;

  .el-form-item {
    margin-bottom: 20px;

    .el-form-item__label {
      color: #606266;
      font-weight: 500;
    }

    .el-input, .el-textarea, .el-select {
      width: 100%;
    }
  }
}

/* 头像上传样式 */
.avatar-uploader {
  .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;

    &:hover {
      border-color: #409EFF;
    }
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
    border-radius: 50%;
  }
}

/* 按钮悬停效果 */
.el-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

/* 小屏幕适配 */
@media (max-width: 768px) {
  .search-form-container {
    padding: 15px !important;

    .el-row {
      gap: 15px !important;
    }

    > div {
      min-width: 100% !important;
      flex: 1 1 100% !important;
    }
  }

  .el-table-column--label {
    min-width: 80px !important;
  }

  .el-table-column__content {
    font-size: 12px;
  }

  .el-button {
    font-size: 12px;
    padding: 4px 8px !important;
    height: 26px !important;
    line-height: 18px !important;
  }

  .custom-table {
    .index-column .index-badge {
      width: 20px;
      height: 20px;
      line-height: 20px;
      font-size: 10px;
    }

    .el-tag {
      padding: 1px 6px !important;
      font-size: 11px;
    }
  }

  /* 移动端对话框适配 */
  .el-dialog {
    width: 90% !important;
    margin-top: 8vh !important;

    .el-dialog__body {
      padding: 15px !important;

      .el-descriptions {
        .el-descriptions__table {
          .el-descriptions__label {
            width: 90px;
            padding: 10px 12px;
            font-size: 13px;
          }

          .el-descriptions__content {
            padding: 10px 12px;
            font-size: 14px;
          }
        }
      }
    }
  }

  /* 移动端表单适配 */
  .el-form {
    padding: 10px !important;

    .el-form-item {
      margin-bottom: 15px;

      .el-form-item__label {
        font-size: 13px;
      }
    }
  }
}
</style>