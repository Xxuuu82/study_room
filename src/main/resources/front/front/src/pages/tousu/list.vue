<template>
  <div style="width: 100%; min-height: 100vh; padding: 0 10px; boxSizing: border-box;">
    <!-- 固定的投诉入口Header -->
    <div style="
      position: sticky;
      top: 0;
      z-index: 100;
      width: 100%;
      padding: 20px;
      margin: 10px auto 20px;
      border-radius: 27px;
      background: #f0f8fb;
      box-shadow: 0px 4px 10px 0px rgba(0,0,0,0.1);
      box-sizing: border-box;
      display: flex;
      justify-content: space-between;
      align-items: center;
    " class="complaint-header">
      <div style="font-size: 18px; font-weight: 600; color: #333;">
        投诉中心
      </div>
      <el-button
          type="primary"
          icon="el-icon-circle-plus"
          size="medium"
          @click="toAddComplaint"
          style="
            border: 1px solid #409EFF;
            cursor: pointer;
            padding: 0 24px;
            box-shadow: 0px 2px 8px 0px rgba(64, 158, 255, 0.3);
            outline: none;
            color: #fff;
            border-radius: 30px;
            background: #409EFF;
            font-size: 14px;
            height: 44px;
            font-weight: 500;
          "
      >
        进行投诉
      </el-button>
    </div>

    <!-- 面包屑 -->
    <div :style='{"width":"100%","padding":"20px","margin":"10px auto","borderRadius":"27px","background":"#bfd2fc","boxSizing":"border-box"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'Ξ'" :style='{"fontSize":"14px","lineHeight":"1"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item>投诉中心</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 列表容器 -->
    <div class="list-preview" :style='{"width":"100%","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"20px auto","position":"relative","background":"#fff","boxSizing":"border-box","padding":"0 10px"}'>
      <!-- 查询表单 -->
      <el-form
          class="center-form-pv"
          :style="{ width: '100%', padding: '20px 6px', margin: '0 auto', background: 'rgba(0,0,0,0.102)', boxSizing: 'border-box' }"
          :inline="true"
          :model="searchForm"
      >
        <el-row :style="{ flexDirection: 'row', display: 'flex', flexWrap: 'wrap', justifyContent: 'flex-start', gap: '20px' }">
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >投诉单号</label
            >
            <el-input
                v-model="searchForm.tousudanhao"
                placeholder="投诉单号"
                clearable
                style="width: 100%;"
            ></el-input>
          </div>
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
              class="select"
              label="投诉类型"
              prop="tousuleixing"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >投诉类型</label
            >
            <el-select
                clearable
                v-model="searchForm.tousuleixing"
                placeholder="请选择投诉类型"
                style="width: 100%;"
            >
              <el-option
                  v-for="(item, index) in tousuleixingOptions"
                  v-bind:key="index"
                  :label="item"
                  :value="item"
              ></el-option>
            </el-select>
          </div>
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
              class="select"
              label="处理状态"
              prop="chulizhuangtai"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >处理状态</label
            >
            <el-select
                clearable
                v-model="searchForm.chulizhuangtai"
                placeholder="请选择处理状态"
                style="width: 100%;"
            >
              <el-option
                  v-for="(item, index) in chulizhuangtaiOptions"
                  v-bind:key="index"
                  :label="item"
                  :value="item"
              ></el-option>
            </el-select>
          </div>
          <div :style="{ margin: 'auto 0 0', minWidth: '120px' }">
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
                width: '100%',
                fontSize: '14px',
                height: '40px',
              }"
                type="success"
                @click="search()"
            >查询</el-button
            >
          </div>
        </el-row>
      </el-form>

      <!-- 投诉记录表格 -->
      <el-table
          class="custom-table"
          :stripe="false"
          :style="{
          padding: '0',
          boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.3020)',
          borderColor: '#eee',
          borderRadius: '8px',
          borderWidth: '1px 0 0 1px',
          background: '#fff',
          width: '100%',
          margin: '20px auto',
          borderStyle: 'solid',
          height: 'fit-content',
          boxSizing: 'border-box',
          overflow: 'visible',
        }"
          :data="complaintList"
          v-loading="dataListLoading"
          border
      >
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
        <el-table-column
            prop="tousudanhao"
            label="投诉单号"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.tousudanhao }}
          </template>
        </el-table-column>
        <el-table-column
            prop="tousuleixing"
            label="投诉类型"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            <el-tag type="info">{{ row.tousuleixing }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="tousuTime"
            label="投诉时间"
            min-width="160"
            align="center"
        >
          <template #default="{ row }">
            {{ row.tousuTime || "-" }}
          </template>
        </el-table-column>
        <el-table-column
            prop="relatedOrder"
            label="关联预约单号"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.relatedOrder || "-" }}
          </template>
        </el-table-column>
        <el-table-column
            prop="chulizhuangtai"
            label="处理状态"
            min-width="100"
            align="center"
        >
          <template #default="{ row }">
            <el-tag :type="getStateTagType(row.chulizhuangtai)">
              {{ row.chulizhuangtai }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="tousuReason"
            label="投诉原因"
            min-width="180"
            align="center"
        >
          <template #default="{ row }">
            <div class="remark-box" :title="row.tousuReason">
              {{ row.tousuReason || "无" }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
            prop="feedback"
            label="处理反馈"
            min-width="180"
            align="center"
        >
          <template #default="{ row }">
            <div class="remark-box" :title="row.feedback">
              {{ row.feedback || "暂未反馈" }}
            </div>
          </template>
        </el-table-column>
        <!-- 操作列 -->
        <el-table-column
            label="操作"
            min-width="200"
            align="center"
        >
          <template #default="{ row }">
            <div style="display: flex; gap: 6px; justify-content: center; align-items: center; flex-wrap: wrap;">
              <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-view"
                  @click="toDetail(row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #409EFF; background: #409EFF; color: #fff;"
              >查看详情</el-button
              >
              <el-button
                  type="danger"
                  size="mini"
                  icon="el-icon-circle-close"
                  @click="cancelComplaint(row)"
                  v-if="row.chulizhuangtai === '处理中'"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #F56C6C; background: #F56C6C; color: #fff;"
              >取消投诉</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          background
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          :layout="layouts.join()"
          :total="total"
          prev-text="<"
          next-text=">"
          :hide-on-single-page="true"
          :style="{
          width: '100%',
          padding: '20px 0',
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
</template>

<script>
export default {
  data() {
    return {
      // 搜索表单
      searchForm: {
        tousudanhao: '',
        tousuleixing: '',
        chulizhuangtai: ''
      },

      // 投诉记录列表（模拟数据）
      complaintList: [
        {
          tousudanhao: "TS20251215001",
          tousuleixing: "占座违规",
          tousuTime: "2025-12-10 10:20:35",
          relatedOrder: "YY20251210001",
          chulizhuangtai: "处理中",
          tousuReason: "用户占用预约座位超过2小时未使用，且拒绝配合调整",
          feedback: "暂未反馈"
        },
        {
          tousudanhao: "TS20251215002",
          tousuleixing: "设备故障",
          tousuTime: "2025-12-08 14:15:20",
          relatedOrder: "YY20251208002",
          chulizhuangtai: "投诉成功",
          tousuReason: "预约的自习室座位电源故障，无法正常使用，管理员未及时处理",
          feedback: "已安排维修人员修复电源，补偿用户1天免费预约时长"
        },
        {
          tousudanhao: "TS20251215003",
          tousuleixing: "服务态度",
          tousuTime: "2025-12-05 09:30:10",
          relatedOrder: "YY20251205003",
          chulizhuangtai: "投诉失败",
          tousuReason: "管理员态度恶劣，拒绝协助解决座位调整问题",
          feedback: "经核实，管理员已按规定流程处理，不存在态度恶劣情况，投诉不成立"
        }
      ],

      // 分页相关
      pageIndex: 1,
      pageSize: 10,
      total: 3, // 模拟总数
      dataListLoading: false,
      layouts: ["total", "prev", "pager", "next", "sizes", "jumper"],

      // 下拉选项
      tousuleixingOptions: "占座违规,设备故障,服务态度,预约问题,其他问题".split(","),
      chulizhuangtaiOptions: "处理中,投诉成功,投诉失败".split(",")
    };
  },
  methods: {
    // 获取处理状态标签样式
    getStateTagType(state) {
      const stateMap = {
        "处理中": "warning",
        "投诉成功": "success",
        "投诉失败": "danger"
      };
      return stateMap[state] || "info";
    },

    // 跳转到添加投诉页面
    toAddComplaint() {
      // 跳转到投诉新增页（对应router.js中的/index/tousuAdd路由）
      this.$router.push('/index/tousuAdd');
    },

    // 搜索
    search() {
      this.pageIndex = 1;
      // 模拟加载效果
      this.dataListLoading = true;
      setTimeout(() => {
        this.dataListLoading = false;
      }, 500);
    },

    // 分页大小改变
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      // 后续对接接口
    },

    // 当前页改变
    currentChangeHandle(val) {
      this.pageIndex = val;
      // 后续对接接口
    },

    // 查看详情
    toDetail(row) {
      // 存储当前投诉记录到localStorage，供详情页读取
      localStorage.setItem("currentTousuObj", JSON.stringify(row));
      // 跳转到投诉详情页（对应router.js中的/index/tousuDetail路由）
      this.$router.push('/index/tousuDetail');
    },

    // 取消投诉
    cancelComplaint(row) {
      this.$confirm('确定要取消该投诉吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 模拟取消投诉逻辑
        this.$message.success(`已取消投诉单号${row.tousudanhao}的投诉`);
        // 更新列表数据（将状态改为已取消）
        const targetIndex = this.complaintList.findIndex(item => item.tousudanhao === row.tousudanhao);
        if (targetIndex > -1) {
          this.complaintList[targetIndex].chulizhuangtai = "已取消";
        }
      }).catch(() => {
        this.$message.info('已取消操作');
      });
    }
  }
};
</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
}

// 投诉入口header样式
.complaint-header {
  .el-button {
    &:hover {
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
    }
  }
}

// 复用原页面样式
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__separator {
  margin: 0 9px;
  color: #000;
  font-weight: 500;
}
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__inner {
  color: #000;
  display: inline-block;
  cursor: pointer; /* 首页面包屑添加鼠标指针 */
}
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__inner a {
  color: #000;
  text-decoration: none;
}

.custom-table {
  border-radius: 8px;
  overflow: visible !important;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;

  .el-table__header-wrapper {
    th {
      background: #f8f9fa;
      color: #495057;
      font-weight: 600;
      font-size: 14px;
      border-bottom: 2px solid #e9ecef !important;
      padding: 12px 0;
    }
  }

  .el-table__body-wrapper {
    overflow: visible !important;
    .el-table__row {
      transition: all 0.3s ease;
      &:hover {
        background: #f1f3f5;
      }
      td {
        border-color: #e9ecef !important;
        padding: 12px 5px;
      }
    }
  }

  .index-column .index-badge {
    display: inline-block;
    width: 24px;
    height: 24px;
    line-height: 24px;
    border-radius: 50%;
    background: #93c7b3;
    color: white;
    font-weight: 500;
  }

  .text-ellipsis {
    max-width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .remark-box {
    padding: 4px 8px;
    background: #f8f9fa;
    border-radius: 4px;
    font-size: 13px;
    max-width: 200px;
    margin: 0 auto;
  }
}

.center-form-pv {
  .el-input, .el-select {
    width: 100%;
  }
  .el-input__inner {
    border: 1px solid #78abc3;
    border-radius: 4px;
    padding: 0 12px;
    box-shadow: 0px 2px 2px 0px #78abc3;
    outline: none;
    color: #000;
    font-size: 14px;
    height: 40px;
  }
  .el-select .el-input__inner {
    border: 1px solid #78abc3;
    border-radius: 4px;
    padding: 0 10px;
    box-shadow: 0px 2px 2px 0px #78abc3;
    outline: none;
    color: #000;
    font-size: 14px;
    height: 40px;
  }
}

.el-pagination >>> {
  .el-pagination__total {
    margin: 0 10px 0 0;
    color: #666;
    font-weight: 400;
  }
  .el-pager .number {
    cursor: pointer;
    padding: 0 4px;
    margin: 0 5px;
    color: #666;
    background: #f4f4f5;
    min-width: 30px;
    height: 28px;
    line-height: 28px;
    border-radius: 2px;
  }
  .el-pager .number:hover, .el-pager .number.active {
    background: #2e61e1;
    color: #fff;
  }
}

.el-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

// 响应式适配
@media (max-width: 768px) {
  .el-table-column--label {
    min-width: 80px !important;
  }
  .el-table-column__content {
    font-size: 12px;
  }
  .el-button {
    font-size: 12px;
    padding: 0 8px !important;
  }
  .complaint-header {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start !important;
  }
}
</style>