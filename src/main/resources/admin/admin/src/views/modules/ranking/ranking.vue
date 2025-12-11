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
                  v-if="isAuth('qiantuixinxi', '新增')"
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
                  v-if="isAuth('qiantuixinxi', '删除')"
                  :disabled="dataListSelections.length <= 0"
                  type="danger"
                  @click="deleteHandler()"
              >删除</el-button>
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
                  background: '#E6A23C',
                  width: '100%',
                  fontSize: '14px',
                  height: '40px',
                  transition: 'all 0.3s',
                }"
                  v-if="isAuth('qiantuixinxi', '分析')"
                  type="warning"
                  @click="fenxi()"
              >数据分析</el-button>
            </div>
          </el-row>
        </el-form>
      </div>

      <!-- 表格区域 -->
      <div class="table-container" :style='{"padding":"20px"}'>
        <!-- 分析模式图表 -->
        <div
            ref="chartContainer"
            v-show="isAnalysisMode"
            style="width: 100%; height: 500px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);"
        ></div>

        <!-- 分析模式返回按钮 -->
        <div v-if="isAnalysisMode" :style='{"marginBottom":"20px","display":"flex","justifyContent":"flex-end"}'>
          <el-button
              @click="exitAnalysis"
              type="primary"
              style="borderRadius: 4px; padding: 0 24px; height: 40px; lineHeight: 40px;"
          >
            返回普通模式
          </el-button>
        </div>

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

          <!-- 自习时长 -->
          <el-table-column
              prop="zixishichang"
              label="自习时长"
              width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <el-tooltip :content="`${row.zixishichang}秒`" placement="top">
                <div class="duration-badge">
                  {{ formatTime(row.zixishichang) }}
                </div>
              </el-tooltip>
            </template>
          </el-table-column>

          <!-- 学号 -->
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="xuehao"
              label="学号"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <div class="id-number" :title="row.xuehao">
                {{ row.xuehao || "-" }}
              </div>
            </template>
          </el-table-column>

          <!-- 手机 -->
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="shouji"
              label="手机"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <div class="phone-number">
                {{ row.shouji || "-" }}
              </div>
            </template>
          </el-table-column>

          <!-- 班级 -->
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="banji"
              label="班级"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template #default="{ row }">
              <el-tag type="info" effect="dark" class="class-tag">
                {{ row.banji || "-" }}
              </el-tag>
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
                    v-if="isAuth('qiantuixinxi', '修改')"
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

    <!-- 详情对话框 -->
    <el-dialog
        title="详情信息"
        :visible.sync="detailDialogVisible"
        width="50%"
        :close-on-click-modal="false"
    >
      <div v-if="currentDetailData" style="padding: 20px;">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="学号">
            {{ currentDetailData.xuehao || "-" }}
          </el-descriptions-item>
          <el-descriptions-item label="手机">
            {{ currentDetailData.shouji || "-" }}
          </el-descriptions-item>
          <el-descriptions-item label="班级">
            {{ currentDetailData.banji || "-" }}
          </el-descriptions-item>
          <el-descriptions-item label="自习时长">
            {{ formatTime(currentDetailData.zixishichang) }}
          </el-descriptions-item>
          <el-descriptions-item v-if="currentDetailData.mingcheng" label="名称" :span="2">
            {{ currentDetailData.mingcheng }}
          </el-descriptions-item>
          <el-descriptions-item v-if="currentDetailData.qiantuishijian" label="签退时间" :span="2">
            {{ currentDetailData.qiantuishijian }}
          </el-descriptions-item>
          <el-descriptions-item v-if="currentDetailData.qiantuibeizhu" label="签退备注" :span="2">
            {{ currentDetailData.qiantuibeizhu }}
          </el-descriptions-item>
          <el-descriptions-item v-if="currentDetailData.sfsh" label="回复状态" :span="2">
            <el-tag :type="currentDetailData.sfsh ? 'success' : 'warning'" effect="light">
              {{ currentDetailData.sfsh || "待回复" }}
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailDialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>

    <!-- 添加/修改页面  将父组件的search方法传递给子组件-->
    <add-or-update
        v-if="addOrUpdateFlag"
        :parent="this"
        ref="addOrUpdate"
    ></add-or-update>

    <!-- 回复对话框 -->
    <el-dialog title="回复" :visible.sync="sfshVisiable" width="50%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="内容">
          <el-input type="textarea" :rows="8" v-model="shForm.shhf"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="shDialog">取 消</el-button>
        <el-button type="primary" @click="shHandler">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 加载状态 -->
    <el-skeleton :rows="6" animated v-if="dataListLoading" />
  </div>
</template>

<script>
import axios from "axios";
import AddOrUpdate from "./add-or-update";
import * as echarts from "echarts";
import { isAuth } from "../../../utils/utils";
export default {
  data() {
    return {
      searchForm: {
        xuehao: ""
      },
      myChart: null,
      isAnalysisMode: false,
      form: {},
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
      detailDialogVisible: false,
      currentDetailData: null,
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

    // 获取数据列表 - 修复搜索功能
    getDataList() {
      this.dataListLoading = true;
      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: "id",
        order: "desc",
      };

      // 修复搜索参数：根据实际需求选择合适的方式
      if (
          this.searchForm.xuehao !== "" &&
          this.searchForm.xuehao !== undefined
      ) {
        // 方式1：精确匹配（推荐先试这个）
        params["xuehao"] = this.searchForm.xuehao;

        // 如果需要模糊匹配，可以使用以下方式之一：
        // 方式2：模糊匹配
        // params["xuehao"] = `%${this.searchForm.xuehao}%`;

        // 方式3：后端框架特定的模糊查询格式
        // params["xuehao[like]"] = `%${this.searchForm.xuehao}%`;

        console.log("搜索参数:", params); // 调试用，查看发送的参数
      }

      this.$http({
        url: "qiantuixinxi/page",
        method: "get",
        params: params,
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.total;
          console.log("搜索结果:", this.dataList); // 调试用，查看返回的数据

          if (this.dataList.length === 0 && this.searchForm.xuehao) {
            this.$message.info("未找到相关记录");
          }
        } else {
          this.dataList = [];
          this.totalPage = 0;
          this.$message.error(data.msg || "搜索失败");
        }
        this.dataListLoading = false;
      }).catch((error) => {
        this.dataListLoading = false;
        console.error("请求错误:", error);
        this.$message.error("搜索请求失败");
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
      if (type === 'info') {
        // 查看详情，显示对话框
        this.showDetail(id);
      } else {
        // 修改或新增，显示完整的添加/修改页面
        this.showFlag = false;
        this.addOrUpdateFlag = true;
        this.crossAddOrUpdateFlag = false;
        if (type != "info") {
          type = "else";
        }
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, type);
        });
      }
    },

    // 显示详情对话框
    showDetail(id) {
      this.dataListLoading = true;
      this.$http({
        url: `qiantuixinxi/info/${id}`,
        method: "get",
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.currentDetailData = data.data;
          this.detailDialogVisible = true;
        } else {
          this.$message.error(data.msg);
        }
        this.dataListLoading = false;
      });
    },

    // 审核窗口
    shDialog(row) {
      this.sfshVisiable = !this.sfshVisiable;
      if (row) {
        this.shForm = {
          mingcheng: row.mingcheng,
          qiantuishijian: row.qiantuishijian,
          zixishichang: row.zixishichang,
          renlianshibie: row.renlianshibie,
          qiantuibeizhu: row.qiantuibeizhu,
          xuehao: row.xuehao,
          shouji: row.shouji,
          banji: row.banji,
          shhf: row.shhf,
          id: row.id,
        };
      }
    },
    // 审核
    shHandler() {
      this.$confirm(`确定操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http({
          url: "qiantuixinxi/update",
          method: "post",
          data: this.shForm,
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
                this.shDialog();
              },
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
    // 下载
    download(file) {
      window.open(`${file}`);
    },

    formatTime(seconds) {
      if (!seconds) return "0分钟";
      const hours = Math.floor(seconds / 3600);
      const minutes = Math.floor((seconds % 3600) / 60);
      if (hours > 0) {
        return `${hours}小时${minutes}分钟`;
      } else {
        return `${minutes}分钟`;
      }
    },

    exitAnalysis() {
      this.isAnalysisMode = false;
      this.pageIndex = 1;
      this.pageSize = 5;
      this.searchForm.xuehao = "";
      if (this.myChart) {
        this.myChart.dispose();
        this.myChart = null;
      }
      this.getDataList();
    },

    fenxi() {
      this.isAnalysisMode = true;
      this.dataListLoading = true;

      this.$http({
        url: "qiantuixinxi/fenxi",
        method: "get",
      })
      .then(({ data }) => {
        if (data && data.code === 0) {
          // 分析模式下显示所有数据
          this.dataList = data.data.map(item => ({
            ...item,
            zixishichang: item.zixishichang || 0
          }));
          this.totalPage = this.dataList.length;
          this.pageSize = this.dataList.length;
          this.initChart(data.data);
        } else {
          this.$message.error(data.msg || "数据获取失败");
        }
        this.dataListLoading = false;
      })
      .catch((err) => {
        this.dataListLoading = false;
        console.error("请求失败：", err);
        this.$message.error("分析数据请求失败");
      });
    },

    initChart(rawData) {
      if (this.myChart) {
        this.myChart.dispose();
      }
      const chartDom = this.$refs.chartContainer;
      this.myChart = echarts.init(chartDom);

      // 处理数据格式
      const xAxisData = [];
      const seriesData = [];

      rawData.forEach((item) => {
        xAxisData.push(item.xuehao || '未知学号');
        seriesData.push(item.zixishichang / 3600); // 秒转小时
      });

      const option = {
        backgroundColor: "#f5f5f5",
        grid: {
          backgroundColor: "#fff",
          borderColor: "#e6e6e6",
          borderWidth: 1,
          left: '10%',
          right: '10%',
          bottom: '15%'
        },
        title: {
          text: "学生自习时长分析",
          left: "center",
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: "axis",
          formatter: ({ [0]: item }) => {
            const hours = Math.floor(item.value);
            const minutes = Math.round((item.value - hours) * 60);
            return `${item.name}<br>${hours}小时${minutes}分钟`;
          },
        },
        xAxis: {
          type: "category",
          data: xAxisData,
          name: '学号',
          axisLabel: {
            rotate: 45,
            interval: 0,
            fontSize: 12
          },
        },
        yAxis: {
          type: "value",
          name: "自习时长（小时）",
        },
        series: [
          {
            data: seriesData,
            type: "line",
            smooth: true,
            lineStyle: {
              color: "#2C6BED",
              width: 3,
              type: "solid",
            },
            itemStyle: {
              color: "#2C6BED",
              borderWidth: 2,
            },
            areaStyle: {
              color: {
                type: "linear",
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 0,
                    color: "rgba(44,107,237,0.8)",
                  },
                  {
                    offset: 1,
                    color: "rgba(44,107,237,0.1)",
                  },
                ],
              },
            },
            emphasis: {
              lineStyle: {
                shadowColor: "rgba(0, 0, 0, 0.5)",
                shadowBlur: 10,
                shadowOffsetY: 5,
              },
            },
          },
        ],
        dataZoom: [
          {
            type: "slider",
            start: 0,
            end: 100,
            bottom: '5%'
          },
        ],
      };

      this.myChart.setOption(option);

      // 窗口resize监听
      window.addEventListener("resize", () => this.myChart.resize());
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
          url: "qiantuixinxi/delete",
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
        background: #337ecc !important;
      }

      &:nth-child(3):hover {
        background: #7db4a0 !important;
      }

      &:nth-child(4):hover {
        background: #d95454 !important;
      }

      &:nth-child(5):hover {
        background: #d69b23 !important;
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

  .duration-badge {
    padding: 4px 8px;
    background: #e3f2fd;
    border-radius: 12px;
    color: #2196f3;
    font-weight: 500;
    display: inline-block;
    min-width: 80px;
  }

  .id-number {
    font-family: 'Courier New', monospace;
    font-weight: 500;
    max-width: 120px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin: 0 auto;
  }

  .phone-number {
    font-family: 'Courier New', monospace;
    letter-spacing: 1px;
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

    .duration-badge {
      padding: 2px 4px;
      font-size: 11px;
      min-width: 60px;
    }

    .id-number, .phone-number {
      max-width: 80px;
      font-size: 12px;
    }

    .class-tag {
      font-size: 11px;
    }
  }
}
</style>