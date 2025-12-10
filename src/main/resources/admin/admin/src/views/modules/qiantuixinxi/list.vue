<template>
  <div class="main-content">
    <!-- 列表页 -->

    <template v-if="showFlag">
      <el-form
        class="center-form-pv"
        :style="{ width: '250px', padding: '0 6px', margin: '0 0 20px' }"
        :inline="true"
        :model="searchForm"
      >
        <el-row :style="{ flexDirection: 'column', display: 'flex' }">
          <div
            :style="{ margin: '0', flexDirection: 'column', display: 'flex' }"
          >
            <label
              :style="{
                margin: '0',
                color: '#000',
                textAlign: 'center',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
              class="item-label"
              >名称</label
            >
            <el-input
              v-model="searchForm.mingcheng"
              placeholder="名称"
              clearable
            ></el-input>
          </div>
          <div
            :style="{ margin: '0', flexDirection: 'column', display: 'flex' }"
          ></div>
          <el-button
            :style="{
              border: '1px solid #97C9D6',
              cursor: 'pointer',
              padding: '0 24px',
              boxShadow: '0px 2px 2px 0px #78ABC3',
              outline: 'none',
              margin: '10px 0 0',
              color: '#78ABC3',
              borderRadius: '30px',
              background: '#fff',
              width: 'auto',
              fontSize: '14px',
              height: '40px',
            }"
            type="success"
            @click="search()"
            >查询</el-button
          >
        </el-row>

        <el-row
          :style="{
            margin: '20px 0',
            flexDirection: 'column',
            display: 'flex',
          }"
        >
          <el-button
            :style="{
              border: '1px solid #97C9D6',
              cursor: 'pointer',
              padding: '0',
              boxShadow: '0px 2px 2px 0px #78ABC3',
              margin: '0 0 10px',
              outline: 'none',
              color: '#78ABC3',
              borderRadius: '30px',
              background: '#fff',
              width: '100%',
              fontSize: '14px',
              height: '40px',
            }"
            v-if="isAuth('qiantuixinxi', '新增')"
            type="success"
            @click="addOrUpdateHandler()"
            >新增</el-button
          >
          <el-button
            :style="{
              border: '1px solid #97C9D6',
              cursor: 'pointer',
              padding: '0',
              boxShadow: '0px 2px 2px 0px #78ABC3',
              margin: '0 0 10px',
              outline: 'none',
              color: '#78ABC3',
              borderRadius: '30px',
              background: '#fff',
              width: '100%',
              fontSize: '14px',
              height: '40px',
            }"
            v-if="isAuth('qiantuixinxi', '删除')"
            :disabled="dataListSelections.length <= 0"
            type="danger"
            @click="deleteHandler()"
            >删除</el-button
          >
        </el-row>
        <el-button
          :style="{
            border: '1px solid #97C9D6',
            cursor: 'pointer',
            padding: '0',
            boxShadow: '0px 2px 2px 0px #78ABC3',
            margin: '0 0 10px',
            outline: 'none',
            color: '#78ABC3',
            borderRadius: '30px',
            background: '#fff',
            width: '100%',
            fontSize: '14px',
            height: '40px',
          }"
          v-if="isAuth('qiantuixinxi', '分析')"
          @click="fenxi()"
          >数据分析</el-button
        >
        <el-button
          v-if="isAnalysisMode"
          @click="exitAnalysis"
          style="margin-left: 10px"
          type="primary"
        >
          返回普通模式
        </el-button>
      </el-form>

      <!-- <div> -->
      <el-table
        :data="dataList"
        class="custom-table"
        :row-class-name="tableRowClassName"
        @row-mouseenter="handleRowHover"
        @row-mouseleave="handleRowLeave"
        stripe
        border
      >
        <!-- 公共列始终显示 -->
        <el-table-column
          type="selection"
          align="center"
          width="50"
          class-name="selection-column"
        ></el-table-column>

        <el-table-column
          type="index"
          label="索引"
          width="80"
          align="center"
          class-name="index-column"
        >
          <template #default="{ $index }">
            <span class="index-badge">{{ $index + 1 }}</span>
          </template>
        </el-table-column>

        <!-- 动态列 -->
        <template v-if="!isAnalysisMode">
          <el-table-column
            prop="mingcheng"
            label="名称"
            min-width="120"
            header-align="center"
          >
            <template #default="{ row }">
              <div class="text-ellipsis" :title="row.mingcheng">
                {{ row.mingcheng || "-" }}
              </div>
            </template>
          </el-table-column>

          <el-table-column
            prop="qiantuishijian"
            label="签退时间"
            width="180"
            align="center"
          >
            <template #default="{ row }">
              <el-tooltip :content="row.qiantuishijian">
                <span>{{ row.qiantuishijian }}</span>
              </el-tooltip>
            </template>
          </el-table-column>

          <el-table-column
            prop="qiantuibeizhu"
            label="签退备注"
            min-width="150"
          >
            <template #default="{ row }">
              <div class="remark-box">
                {{ row.qiantuibeizhu || "无备注" }}
              </div>
            </template>
          </el-table-column>

          <el-table-column
            prop="sfsh"
            label="回复状态"
            width="120"
            align="center"
          >
            <template #default="{ row }">
              <el-tag :type="row.sfsh ? 'success' : 'warning'" effect="light">
                {{ row.sfsh || "待回复" }}
              </el-tag>
            </template>
          </el-table-column>
        </template>

        <!-- 分析模式专属列 -->
        <template>
          <el-table-column
            prop="zixishichang"
            label="自习时长"
            width="120"
            align="center"
          >
            <template #default="{ row }">
              <el-tooltip :content="`${row.zixishichang}秒`">
                <div class="duration-badge">
                  {{ formatTime(row.zixishichang) }}
                </div>
              </el-tooltip>
            </template>
          </el-table-column>

          <el-table-column
            prop="xuehao"
            label="学号"
            width="150"
            align="center"
          >
            <template #default="{ row }">
              <span class="id-number">{{ row.xuehao }}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="shouji"
            label="手机"
            width="140"
            align="center"
          >
            <template #default="{ row }">
              <div class="phone-number">
                {{ row.shouji | phoneFilter }}
              </div>
            </template>
          </el-table-column>

          <el-table-column prop="banji" label="班级" width="120" align="center">
            <template #default="{ row }">
              <el-tag type="info" effect="dark" class="class-tag">
                {{ row.banji }}
              </el-tag>
            </template>
          </el-table-column>
        </template>

        <!-- 公共操作列 -->
        <el-table-column
          label="操作"
          width="260"
          fixed="right"
          class-name="action-column"
        >
          <template #default="{ row }">
            <div class="action-btns">
              <el-button
                v-if="isAuth('qiantuixinxi', '查看')"
                size="mini"
                icon="el-icon-view"
                class="detail-btn"
                @click.stop="addOrUpdateHandler(row.id, 'info')"
              />

              <el-button
                v-if="isAuth('qiantuixinxi', '修改')"
                type="primary"
                size="mini"
                icon="el-icon-edit"
                class="edit-btn"
                @click.stop="addOrUpdateHandler(row.id)"
              />

              <el-button
                v-if="isAuth('qiantuixinxi', '删除')"
                type="danger"
                size="mini"
                icon="el-icon-delete"
                class="delete-btn"
                @click.stop="deleteHandler(row.id)"
              />
            </div>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        background
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageSize"
        :layout="layouts.join()"
        :total="totalPage"
        prev-text="<"
        next-text=">"
        :hide-on-single-page="true"
        :style="{
          width: '100%',
          padding: '0',
          margin: '20px 0 0',
          whiteSpace: 'nowrap',
          color: '#333',
          fontWeight: '500',
        }"
      ></el-pagination>
      <!-- </div> -->
    </template>

    <!-- 添加/修改页面  将父组件的search方法传递给子组件-->
    <add-or-update
      v-if="addOrUpdateFlag"
      :parent="this"
      ref="addOrUpdate"
    ></add-or-update>

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
    <div
      ref="chartContainer"
      v-show="isAnalysisMode"
      style="width: 100%; height: 500px; margin-top: 20px"
    ></div>

    <!-- 加载状态 -->
    <el-skeleton :rows="6" animated v-if="dataListLoading" />
  </div>
</template>

<script>
import axios from "axios";
import AddOrUpdate from "./add-or-update";
import * as echarts from "echarts";
export default {
  data() {
    return {
      searchForm: {
        key: "",
      },
      myChart: null,
      isAnalysisMode: false,
      form: {},
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
      layouts: ["total", "prev", "pager", "next", "sizes", "jumper"],
    };
  },
  created() {
    this.init();
    this.getDataList();
    this.contentStyleChange();
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
    contentStyleChange() {
      this.contentPageStyleChange();
    },
    // 分页
    contentPageStyleChange() {
      let arr = [];
    },

    init() {},
    search() {
      this.pageIndex = 1;
      this.getDataList();
    },

    // 获取数据列表
    getDataList() {
      const url = this.isAnalysisMode
        ? "qiantuixinxi/fenxi"
        : "qiantuixinxi/page";
      this.dataListLoading = true;
      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: "id",
        order: "desc",
      };
      if (
        this.searchForm.mingcheng != "" &&
        this.searchForm.mingcheng != undefined
      ) {
        params["mingcheng"] = "%" + this.searchForm.mingcheng + "%";
      }
      if (
        this.searchForm.qiantuishijianstart != "" &&
        this.searchForm.qiantuishijianstart != undefined
      ) {
        params["qiantuishijianstart"] = this.searchForm.qiantuishijianstart;
      }
      if (
        this.searchForm.qiantuishijianend != "" &&
        this.searchForm.qiantuishijianend != undefined
      ) {
        params["qiantuishijianend"] = this.searchForm.qiantuishijianend;
      }
      this.$http({
        url: url, // 动态接口地址
        method: "get",
        params: this.isAnalysisMode ? {} : params, // 分析模式无需分页参数
      }).then(({ data }) => {
        if (data && data.code === 0) {
          // 分析模式数据处理
          if (this.isAnalysisMode) {
            this.dataList = data.data.map((item) => ({
              xuehao: item.xuehao,
              zixishichang: item.zixishichang,
              shouji: item.shouji,
              banji: item.banji,
            }));
          } else {
            // 普通模式保持原处理
            this.dataList = data.data.list;
            this.totalPage = data.data.total;
          }
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
      const h = Math.floor(seconds / 60);
      const m = Math.floor(seconds % 60);
      console.log(seconds);
      return `${h}小时${m}分钟`;
    },
    exitAnalysis() {
      this.isAnalysisMode = false;
      this.pageIndex = 1;
      this.pageSize = 10; // 恢复默认分页
      //   this.getDataList(); // 重新获取原数据
      if (this.myChart) {
        this.myChart.dispose();
        this.myChart = null;
      }
      this.getDataList(); // 重新加载原始数据
    },
    fenxi() {
      this.isAnalysisMode = true; // 进入分析模式
      this.pageIndex = 1;
      this.pageSize = 9999;
      this.getDataList();
      //   this.initChart();
      this.dataListLoading = true;
      this.searchForm = {
        mingcheng: undefined,
        qiantuishijianstart: undefined,
        qiantuishijianend: undefined,
      };
      this.$http({
        url: "qiantuixinxi/fenxi", // 确保这个路径对应后端Controller
        method: "get",
      })
        .then(({ data }) => {
          if (data && data.code === 0) {
            this.initChart(data.data); // 初始化图表
            this.dataList = data.data; // 原始数据备份（可选）
          } else {
            this.$message.error(data.msg || "数据获取失败");
          }
          this.dataListLoading = false;
        })
        .catch((err) => {
          this.dataListLoading = false;
          console.error("请求失败：", err);
        });
      console.log(data);
    },
    initChart(rawData) {
      if (this.myChart) {
        this.myChart.dispose();
      }
      const chartDom = this.$refs.chartContainer;
      const myChart = echarts.init(chartDom);

      // 处理数据格式
      const xAxisData = [];
      const seriesData = [];

      rawData.forEach((item) => {
        xAxisData.push(item.xuehao);
        seriesData.push(item.zixishichang / 60); // 分钟转小时
      });

      const option = {
        // 整个图表区域背景
        backgroundColor: "#f5f5f5",

        // 绘图区域背景（坐标系内）
        grid: {
          backgroundColor: "#fff",
          borderColor: "#e6e6e6",
          borderWidth: 1,
        },
        title: {
          text: "学生自习时长分布",
          left: "center",
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
          axisLabel: {
            rotate: 30,
            interval: 0,
          },
        },
        yAxis: {
          type: "value",
          name: "时长（小时）",
        },
        series: [
          {
            data: seriesData,
            type: "line",
            smooth: true,
            lineStyle: {
              color: "#2C6BED", // 深蓝色（可自定义）
              width: 3, // 加粗线条（默认1）
              type: "solid", // 可选：solid（实线）| dashed（虚线）
            },
            itemStyle: {
              color: "#2C6BED", // 数据点颜色
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
                    color: "rgba(44,107,237,0.8)", // 渐变开始颜色
                  },
                  {
                    offset: 1,
                    color: "rgba(44,107,237,0.1)", // 渐变结束颜色
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
            end: 50,
          },
        ],
      };

      myChart.setOption(option);

      // 窗口resize监听
      window.addEventListener("resize", () => myChart.resize());
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
  },
};
</script>
<style lang="scss" scoped>
.custom-table {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;

  // 表头样式
  .el-table__header-wrapper {
    th {
      background: #f8f9fa;
      color: #495057;
      font-weight: 600;
      font-size: 14px;
      border-bottom: 2px solid #e9ecef !important;

      &.selection-column .cell {
        display: flex;
        justify-content: center;
      }
    }
  }

  // 表格主体
  .el-table__body-wrapper {
    .el-table__row {
      transition: all 0.3s ease;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        td {
          background: #f1f3f5;
        }
      }

      td {
        border-color: #e9ecef !important;
        padding: 12px 0;
      }
    }
  }

  // 索引列
  .index-column {
    .index-badge {
      display: inline-block;
      width: 24px;
      height: 24px;
      line-height: 24px;
      border-radius: 50%;
      background: #93c7b3;
      color: white;
      font-weight: 500;
    }
  }

  // 操作列
  .action-column {
    .cell {
      padding: 0 8px !important;
    }

    .action-btns {
      display: flex;
      justify-content: space-around;

      .el-button {
        margin: 0 4px;
        padding: 6px;
        border-radius: 50%;
        transition: all 0.3s ease;

        &:hover {
          transform: scale(1.1);
          box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        i {
          font-size: 16px;
        }
      }
    }
  }

  // 特殊元素样式
  .text-ellipsis {
    max-width: 200px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .remark-box {
    padding: 4px 8px;
    background: #f8f9fa;
    border-radius: 4px;
    font-size: 13px;
  }

  .duration-badge {
    padding: 4px 8px;
    background: #e3f2fd;
    border-radius: 12px;
    color: #2196f3;
    font-weight: 500;
  }

  .class-tag {
    font-weight: 500;
    letter-spacing: 0.5px;
  }

  .phone-number {
    font-family: "Roboto Mono", monospace;
    letter-spacing: 1px;
  }
}

// 全局按钮颜色覆盖
.detail-btn {
  background-color: #93c7b3 !important;
  border-color: #93c7b3 !important;
}

.edit-btn {
  background-color: #67c23a !important;
  border-color: #67c23a !important;
}

.delete-btn {
  background-color: #f56c6c !important;
  border-color: #f56c6c !important;
}
.center-form-pv {
  .el-date-editor.el-input {
    width: auto;
  }
}

.el-input {
  width: auto;
}

// form
.center-form-pv .el-input >>> .el-input__inner {
  border: 1px solid #78abc3;
  border-radius: 0;
  padding: 0 12px;
  box-shadow: 0px 2px 2px 0px #78abc3;
  outline: none;
  color: #000;
  width: 100%;
  font-size: 14px;
  height: 40px;
}

.center-form-pv .el-select >>> .el-input__inner {
  border: 1px solid #78abc3;
  border-radius: 0;
  padding: 0 10px;
  box-shadow: 0px 2px 2px 0px #78abc3;
  outline: none;
  color: #000;
  width: 100%;
  font-size: 14px;
  height: 40px;
}

.center-form-pv .el-date-editor >>> .el-input__inner {
  border: 1px solid #78abc3;
  border-radius: 0;
  padding: 0 10px 0 30px;
  box-shadow: 0px 2px 2px 0px #78abc3;
  outline: none;
  color: #000;
  width: 100%;
  font-size: 14px;
  height: 40px;
}

// table
.el-table >>> .el-table__header-wrapper thead {
  color: #999;
  font-weight: 500;
  width: 100%;
}

.el-table >>> .el-table__header-wrapper thead tr {
  background: #93c7b3;
}

.el-table >>> .el-table__header-wrapper thead tr th {
  padding: 12px 0;
  color: #fff;
  background: transparent;
  border-color: #eee;
  border-width: 0 1px 1px 0;
  border-style: solid;
  text-align: center;
}

.el-table >>> .el-table__header-wrapper thead tr th .cell {
  padding: 0 10px;
  word-wrap: normal;
  word-break: break-all;
  white-space: normal;
  font-weight: bold;
  display: inline-block;
  vertical-align: middle;
  width: 100%;
  line-height: 24px;
  position: relative;
  text-overflow: ellipsis;
}

.el-table >>> .el-table__body-wrapper tbody {
  width: 100%;
}

.el-table >>> .el-table__body-wrapper tbody tr {
  background: #fff;
}

.el-table >>> .el-table__body-wrapper tbody tr td {
  padding: 12px 0;
  color: #999;
  background: #fff;
  border-color: #6f6f97;
  border-width: 0 1px 1px 0;
  border-style: solid;
  text-align: center;
}

.el-table >>> .el-table__body-wrapper tbody tr:hover td {
  padding: 12px 0;
  color: #000;
  background: rgba(147, 199, 179, 0.5);
  border-color: #6f6f97;
  border-width: 0 1px 1px 0;
  border-style: solid;
}

.el-table >>> .el-table__body-wrapper tbody tr td {
  padding: 12px 0;
  color: #999;
  background: #fff;
  border-color: #6f6f97;
  border-width: 0 1px 1px 0;
  border-style: solid;
  text-align: center;
}

.el-table >>> .el-table__body-wrapper tbody tr td .cell {
  padding: 0 10px;
  overflow: hidden;
  word-break: break-all;
  white-space: normal;
  line-height: 24px;
  text-overflow: ellipsis;
}

// pagination
.main-content .el-pagination >>> .el-pagination__total {
  margin: 0 10px 0 0;
  color: #666;
  font-weight: 400;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.main-content .el-pagination >>> .btn-prev {
  border: none;
  border-radius: 2px;
  padding: 0;
  margin: 0 5px;
  color: #666;
  background: #f4f4f5;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  min-width: 35px;
  height: 28px;
}

.main-content .el-pagination >>> .btn-next {
  border: none;
  border-radius: 2px;
  padding: 0;
  margin: 0 5px;
  color: #666;
  background: #f4f4f5;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  min-width: 35px;
  height: 28px;
}

.main-content .el-pagination >>> .btn-prev:disabled {
  border: none;
  cursor: not-allowed;
  border-radius: 2px;
  padding: 0;
  margin: 0 5px;
  color: #c0c4cc;
  background: #f4f4f5;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.main-content .el-pagination >>> .btn-next:disabled {
  border: none;
  cursor: not-allowed;
  border-radius: 2px;
  padding: 0;
  margin: 0 5px;
  color: #c0c4cc;
  background: #f4f4f5;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.main-content .el-pagination >>> .el-pager {
  padding: 0;
  margin: 0;
  display: inline-block;
  vertical-align: top;
}

.main-content .el-pagination >>> .el-pager .number {
  cursor: pointer;
  padding: 0 4px;
  margin: 0 5px;
  color: #666;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  border-radius: 2px;
  background: #f4f4f5;
  text-align: center;
  min-width: 30px;
  height: 28px;
}

.main-content .el-pagination >>> .el-pager .number:hover {
  cursor: pointer;
  padding: 0 4px;
  margin: 0 5px;
  color: #fff;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  border-radius: 2px;
  background: #93c7b3;
  text-align: center;
  min-width: 30px;
  height: 28px;
}

.main-content .el-pagination >>> .el-pager .number.active {
  cursor: default;
  padding: 0 4px;
  margin: 0 5px;
  color: #fff;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  border-radius: 2px;
  background: #93c7b3;
  text-align: center;
  min-width: 30px;
  height: 28px;
}

.main-content .el-pagination >>> .el-pagination__sizes {
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.main-content .el-pagination >>> .el-pagination__sizes .el-input {
  margin: 0 5px;
  width: 100px;
  position: relative;
}

.main-content
  .el-pagination
  >>> .el-pagination__sizes
  .el-input
  .el-input__inner {
  border: 1px solid #dcdfe6;
  cursor: pointer;
  padding: 0 25px 0 8px;
  color: #606266;
  display: inline-block;
  font-size: 13px;
  line-height: 28px;
  border-radius: 3px;
  outline: 0;
  background: #fff;
  width: 100%;
  text-align: center;
  height: 28px;
}

.main-content
  .el-pagination
  >>> .el-pagination__sizes
  .el-input
  span.el-input__suffix {
  top: 0;
  position: absolute;
  right: 0;
  height: 100%;
}

.main-content
  .el-pagination
  >>> .el-pagination__sizes
  .el-input
  .el-input__suffix
  .el-select__caret {
  cursor: pointer;
  color: #c0c4cc;
  width: 25px;
  font-size: 14px;
  line-height: 28px;
  text-align: center;
}

.main-content .el-pagination >>> .el-pagination__jump {
  margin: 0 0 0 24px;
  color: #606266;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.main-content .el-pagination >>> .el-pagination__jump .el-input {
  border-radius: 3px;
  padding: 0 2px;
  margin: 0 2px;
  display: inline-block;
  width: 50px;
  font-size: 14px;
  line-height: 18px;
  position: relative;
  text-align: center;
  height: 28px;
}

.main-content
  .el-pagination
  >>> .el-pagination__jump
  .el-input
  .el-input__inner {
  border: 1px solid #dcdfe6;
  cursor: pointer;
  padding: 0 3px;
  color: #606266;
  display: inline-block;
  font-size: 14px;
  line-height: 28px;
  border-radius: 3px;
  outline: 0;
  background: #fff;
  width: 100%;
  text-align: center;
  height: 28px;
}
</style>
