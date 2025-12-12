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
            <div
                :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
                class="select"
                label="签到状态"
                prop="qiandaozhuangtai"
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
              >签到状态</label>
              <el-select
                  @change="qiandaozhuangtaiChange"
                  clearable
                  v-model="searchForm.qiandaozhuangtai"
                  placeholder="请选择签到状态"
                  style="width: 100%;"
              >
                <el-option
                    v-for="(item, index) in qiandaozhuangtaiOptions"
                    v-bind:key="index"
                    :label="item"
                    :value="item"
                ></el-option>
              </el-select>
            </div>
            <div
                :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
                class="select"
                label="签退状态"
                prop="qiantuizhuangtai"
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
              >签退状态</label>
              <el-select
                  @change="qiantuizhuangtaiChange"
                  clearable
                  v-model="searchForm.qiantuizhuangtai"
                  placeholder="请选择签退状态"
                  style="width: 100%;"
              >
                <el-option
                    v-for="(item, index) in qiantuizhuangtaiOptions"
                    v-bind:key="index"
                    :label="item"
                    :value="item"
                ></el-option>
              </el-select>
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
              prop="zuowei"
              label="座位"
              width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.zuowei }}
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="qiandaozhuangtai"
              label="签到状态"
              width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <el-tag :type="scope.row.qiandaozhuangtai === '已签到' ? 'success' : 'danger'" style="borderRadius: 12px; padding: 2px 10px;">
                {{ scope.row.qiandaozhuangtai }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="qiantuizhuangtai"
              label="签退状态"
              width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <el-tag :type="scope.row.qiantuizhuangtai === '已签退' ? 'success' : 'danger'" style="borderRadius: 12px; padding: 2px 10px;">
                {{ scope.row.qiantuizhuangtai }}
              </el-tag>
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
              prop="beizhu"
              label="备注"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <div class="remark-box">{{ row.qiantuibeizhu || "无备注" }}</div>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="shhf"
              label="回复"
              width="100"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <el-tag :type="row.sfsh ? 'success' : 'warning'" style="borderRadius: 12px; padding: 2px 10px;">
                {{ row.sfsh || "待回复" }}
              </el-tag>
            </template>
          </el-table-column>
          <!-- 操作列 -->
          <el-table-column
              label="操作"
              width="240"
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
                    v-if="isAuth('yuyuexinxi', '取消预约')"
                    type="warning"
                    size="mini"
                    @click="
                    quxiaoyuyueCrossAddOrUpdateHandler(
                      scope.row,
                      'cross',
                      '是',
                      '[1]',
                      '请勿重复操作！'
                    )
                  "
                    style="borderRadius: 4px; padding: 5px 12px; height: 30px; lineHeight: 20px; transition: all 0.2s; borderColor: #E6A23C; background: #E6A23C; color: #fff; border: none;"
                >取消预约</el-button>
                <el-button
                    v-if="isAuth('yuyuexinxi', '签到')"
                    type="success"
                    size="mini"
                    @click="
                    qiandaoxinxiCrossAddOrUpdateHandler(
                      scope.row,
                      'cross',
                      '是',
                      'qiandaozhuangtai',
                      '请勿重复签到！',
                      '已签到,未签到'.split(',')[0]
                    )
                  "
                    style="borderRadius: 4px; padding: 5px 12px; height: 30px; lineHeight: 20px; transition: all 0.2s; borderColor: #67C23A; background: #67C23A; color: #fff; border: none;"
                >签到</el-button>
                <el-button
                    v-if="isAuth('yuyuexinxi', '签退')"
                    size="mini"
                    @click="
                    qiantuixinxiCrossAddOrUpdateHandler(
                      scope.row,
                      'cross',
                      '是',
                      'qiantuizhuangtai',
                      '请勿重复签退！',
                      '已签退,未签退'.split(',')[0]
                    )
                  "
                    style="borderRadius: 4px; padding: 5px 12px; height: 30px; lineHeight: 20px; transition: all 0.2s; borderColor: #93C7B3; background: #93C7B3; color: #fff; border: none;"
                >签退</el-button>
                <el-button
                    v-if="isAuth('yuyuexinxi', '修改')"
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

    <quxiaoyuyue-cross-add-or-update
        v-if="quxiaoyuyueCrossAddOrUpdateFlag"
        :parent="this"
        ref="quxiaoyuyueCrossaddOrUpdate"
    ></quxiaoyuyue-cross-add-or-update>
    <qiandaoxinxi-cross-add-or-update
        v-if="qiandaoxinxiCrossAddOrUpdateFlag"
        :parent="this"
        ref="qiandaoxinxiCrossaddOrUpdate"
    ></qiandaoxinxi-cross-add-or-update>
    <qiantuixinxi-cross-add-or-update
        v-if="qiantuixinxiCrossAddOrUpdateFlag"
        :parent="this"
        ref="qiantuixinxiCrossaddOrUpdate"
    ></qiantuixinxi-cross-add-or-update>
  </div>
</template>

<script>
import axios from "axios";
import AddOrUpdate from "./add-or-update";
import quxiaoyuyueCrossAddOrUpdate from "../quxiaoyuyue/add-or-update";
import qiantuixinxiCrossAddOrUpdate from "../../qiantuixinxi/add-or-update";
import qiandaoxinxiCrossAddOrUpdate from "@/views/modules/qiandaoxinxi/add-or-update";

import { isAuth } from "../../../../utils/utils";
export default {
  data() {
    return {
      searchForm: {
        yuyuedanhao: "",
        qiandaozhuangtai: "",
        qiantuizhuangtai: ""
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
      quxiaoyuyueCrossAddOrUpdateFlag: false,
      qiandaoxinxiCrossAddOrUpdateFlag: false,
      qiantuixinxiCrossAddOrUpdateFlag: false,
      qiandaozhuangtaiOptions: [],
      qiantuizhuangtaiOptions: [],
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
    quxiaoyuyueCrossAddOrUpdate,
    qiandaoxinxiCrossAddOrUpdate,
    qiantuixinxiCrossAddOrUpdate,
  },
  methods: {
    isAuth,

    init() {
      this.qiandaozhuangtaiOptions = "已签到,未签到".split(",");
      this.qiantuizhuangtaiOptions = "已签退,未签退".split(",");
    },

    qiandaozhuangtaiChange() {
      // 签到状态选择变化处理
    },

    qiantuizhuangtaiChange() {
      // 签退状态选择变化处理
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
      if (
          this.searchForm.qiandaozhuangtai !== "" &&
          this.searchForm.qiandaozhuangtai !== undefined
      ) {
        params["qiandaozhuangtai"] = this.searchForm.qiandaozhuangtai;
      }
      if (
          this.searchForm.qiantuizhuangtai !== "" &&
          this.searchForm.qiantuizhuangtai !== undefined
      ) {
        params["qiantuizhuangtai"] = this.searchForm.qiantuizhuangtai;
      }
      this.$http({
        url: "yuyuexinxi/page",
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

    quxiaoyuyueCrossAddOrUpdateHandler(
        row,
        type,
        crossOptAudit,
        statusColumnName,
        tips,
        statusColumnValue
    ) {
      this.showFlag = false;
      this.addOrUpdateFlag = false;
      this.quxiaoyuyueCrossAddOrUpdateFlag = true;
      this.$storage.set("crossObj", row);
      this.$storage.set("crossTable", "yuyuexinxi");
      this.$storage.set("statusColumnName", statusColumnName);
      this.$storage.set("statusColumnValue", statusColumnValue);
      this.$storage.set("tips", tips);
      if (statusColumnName !== "" && !statusColumnName.startsWith("[")) {
        var obj = this.$storage.getObj("crossObj");
        for (var o in obj) {
          if (o === statusColumnName && obj[o] === statusColumnValue) {
            this.$message({
              message: tips,
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              },
            });
            this.showFlag = true;
            this.quxiaoyuyueCrossAddOrUpdateFlag = false;
            return;
          }
        }
      }
      this.$nextTick(() => {
        this.$refs.quxiaoyuyueCrossaddOrUpdate.init(row.id, type);
      });
    },
    qiandaoxinxiCrossAddOrUpdateHandler(
        row,
        type,
        crossOptAudit,
        statusColumnName,
        tips,
        statusColumnValue
    ) {
      this.showFlag = false;
      this.addOrUpdateFlag = false;
      this.qiandaoxinxiCrossAddOrUpdateFlag = true;
      this.$storage.set("crossObj", row);
      this.$storage.set("crossTable", "yuyuexinxi");
      this.$storage.set("statusColumnName", statusColumnName);
      this.$storage.set("statusColumnValue", statusColumnValue);
      this.$storage.set("tips", tips);
      if (statusColumnName !== "" && !statusColumnName.startsWith("[")) {
        var obj = this.$storage.getObj("crossObj");
        for (var o in obj) {
          if (o === statusColumnName && obj[o] === statusColumnValue) {
            this.$message({
              message: tips,
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              },
            });
            this.showFlag = true;
            this.qiandaoxinxiCrossAddOrUpdateFlag = false;
            return;
          }
        }
      }
      this.$nextTick(() => {
        this.$refs.qiandaoxinxiCrossaddOrUpdate.init(row.id, type);
      });
    },
    qiantuixinxiCrossAddOrUpdateHandler(
        row,
        type,
        crossOptAudit,
        statusColumnName,
        tips,
        statusColumnValue
    ) {
      this.showFlag = false;
      this.addOrUpdateFlag = false;
      this.qiantuixinxiCrossAddOrUpdateFlag = true;
      this.$storage.set("crossObj", row);
      this.$storage.set("crossTable", "yuyuexinxi");
      this.$storage.set("statusColumnName", statusColumnName);
      this.$storage.set("statusColumnValue", statusColumnValue);
      this.$storage.set("tips", tips);
      if (statusColumnName !== "" && !statusColumnName.startsWith("[")) {
        var obj = this.$storage.getObj("crossObj");
        for (var o in obj) {
          if (o === statusColumnName && obj[o] === statusColumnValue) {
            this.$message({
              message: tips,
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              },
            });
            this.showFlag = true;
            this.qiantuixinxiCrossAddOrUpdateFlag = false;
            return;
          }
        }
      }
      this.$nextTick(() => {
        this.$refs.qiantuixinxiCrossaddOrUpdate.init(row.id, type);
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

  .text-ellipsis {
    max-width: 150px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin: 0 auto;
  }

  .remark-box {
    padding: 4px 8px;
    background: #f8f9fa;
    border-radius: 4px;
    font-size: 13px;
    max-width: 200px;
    margin: 0 auto;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
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

    .remark-box {
      max-width: 100px;
      font-size: 11px;
    }
  }
}
</style>