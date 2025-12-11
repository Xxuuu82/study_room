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
              >预约单号</label>
              <el-input
                  v-model="searchForm.yuyuedanhao"
                  placeholder="请输入预约单号"
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
                  height: '40px',
                  transition: 'all 0.3s',
                }"
                  v-if="isAuth('quxiaoyuyue', '新增')"
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
                  v-if="isAuth('quxiaoyuyue', '删除')"
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
            v-if="isAuth('quxiaoyuyue', '查看')"
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
              prop="yuyuedanhao"
              label="预约单号"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.yuyuedanhao }}
            </template>
          </el-table-column>
          <el-table-column
              prop="mingcheng"
              label="名称"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <div class="text-ellipsis" :title="row.mingcheng">
                {{ row.mingcheng || "-" }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="tupian"
              width="120"
              label="图片"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <div v-if="scope.row.tupian" style="display: inline-block;">
                <img
                    v-if="scope.row.tupian.substring(0, 4) == 'http'"
                    :src="scope.row.tupian.split(',')[0]"
                    width="60"
                    height="60"
                    style="objectFit: cover; borderRadius: 4px; border: 1px solid #f0f0f0;"
                />
                <img
                    v-else
                    :src="$base.url + scope.row.tupian.split(',')[0]"
                    width="60"
                    height="60"
                    style="objectFit: cover; borderRadius: 4px; border: 1px solid #f0f0f0;"
                />
              </div>
              <div v-else style="color: #999; font-size: 12px;">无图片</div>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="yuyueshijian"
              label="预约时间"
              min-width="140"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.yuyueshijian }}
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="quxiaoshijian"
              label="取消时间"
              min-width="140"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.quxiaoshijian }}
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="xuehao"
              label="学号"
              min-width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.xuehao }}
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
              {{ scope.row.xingming }}
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
              {{ scope.row.shouji }}
            </template>
          </el-table-column>
          <!-- 操作列 -->
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
                    @click="addOrUpdateHandler(scope.row.id, 'info')"
                    style="borderRadius: 4px; padding: 5px 12px; height: 30px; lineHeight: 20px; transition: all 0.2s; borderColor: #409EFF; background: #409EFF; color: #fff; border: none;"
                >详情</el-button>
                <el-button
                    v-if="isAuth('quxiaoyuyue', '修改')"
                    type="warning"
                    size="mini"
                    @click="addOrUpdateHandler(scope.row.id)"
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
              :page-sizes="[5, 10, 20, 30, 50]"
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

    <!-- 添加/修改页面  将父组件的search方法传递给子组件-->
    <add-or-update
        v-if="addOrUpdateFlag"
        :parent="this"
        ref="addOrUpdate"
    ></add-or-update>
  </div>
</template>

<script>
import axios from "axios";
import AddOrUpdate from "./add-or-update";
import { isAuth } from "../../../../utils/utils";
export default {
  data() {
    return {
      searchForm: {
        yuyuedanhao: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 5,
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
    };
  },
  created() {
    this.init();
    this.getDataList();
  },
  mounted() {},
  filters: {
    htmlfilter: function (val) {
      return val.replace(/<[^>]*>/g).replace(/undefined/g, "");
    },
  },
  components: {
    AddOrUpdate,
  },
  methods: {
    isAuth,

    init() {
      this.sfshOptions = "是,否,待审核".split(",");
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
        sort: "id",
        order: "desc",
      };
      if (
          this.searchForm.yuyuedanhao !== "" &&
          this.searchForm.yuyuedanhao !== undefined
      ) {
        params["yuyuedanhao"] = "%" + this.searchForm.yuyuedanhao + "%";
      }
      this.$http({
        url: "quxiaoyuyue/page",
        method: "get",
        params: params,
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
    // 添加/修改
    addOrUpdateHandler(id, type) {
      this.showFlag = false;
      this.addOrUpdateFlag = true;
      this.crossAddOrUpdateFlag = false;
      if (type != "info") {
        type = "else";
      }
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id, type);
      });
    },

    // 删除
    deleteHandler(id) {
      var ids = id
          ? [Number(id)]
          : this.dataListSelections.map((item) => {
            return Number(item.id);
          });
      this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http({
          url: "quxiaoyuyue/delete",
          method: "post",
          data: ids,
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.search();
              },
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
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

    .el-select .el-input__inner {
      padding: 0 30px 0 12px;
    }

    .el-button {
      &:hover {
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
      }

      &:nth-child(2):hover {
        background: #7db4a0 !important;
      }

      &:nth-child(3):hover {
        background: #d95454 !important;
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

  .text-ellipsis {
    max-width: 150px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin: 0 auto;
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

    .text-ellipsis {
      max-width: 100px;
    }
  }
}
</style>