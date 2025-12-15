<template>
  <div style="width: 100%; min-height: 100vh; padding: 0 10px; boxSizing: border-box;">
    <!-- 固定的黑名单状态Header -->
    <div style="
      position: sticky;
      top: 0;
      z-index: 100;
      width: 100%;
      padding: 15px 20px;
      margin: 10px auto 20px;
      border-radius: 27px;
      background: #f8f9fa;
      box-shadow: 0px 4px 10px 0px rgba(0,0,0,0.1);
      box-sizing: border-box;
    " class="blacklist-header">
      <div style="display: flex; align-items: center; justify-content: space-between;">
        <div style="font-size: 16px; font-weight: 600; color: #333;">
          当前黑名单状态：
          <el-tag :type="isInBlacklist ? 'danger' : 'success'" size="medium">
            {{ isInBlacklist ? '已列入黑名单' : '正常状态' }}
          </el-tag>
        </div>
        <div v-if="isInBlacklist" style="font-size: 14px; color: #e64340;">
          解禁剩余时间：{{ banEndTime }}
        </div>
      </div>
    </div>

    <!-- 面包屑 -->
    <div :style='{"width":"100%","padding":"20px","margin":"10px auto","borderRadius":"27px","background":"#bfd2fc","boxSizing":"border-box"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'Ξ'" :style='{"fontSize":"14px","lineHeight":"1"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item>违规记录</el-breadcrumb-item>
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
            >违规单号</label
            >
            <el-input
                v-model="searchForm.weiguidanhao"
                placeholder="违规单号"
                clearable
                style="width: 100%;"
            ></el-input>
          </div>
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
              class="select"
              label="违规类型"
              prop="weiguileixing"
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
            >违规类型</label
            >
            <el-select
                clearable
                v-model="searchForm.weiguileixing"
                placeholder="请选择违规类型"
                style="width: 100%;"
            >
              <el-option
                  v-for="(item, index) in weiguileixingOptions"
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
          <!-- 新增申诉状态查询条件 -->
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
              class="select"
              label="申诉状态"
              prop="shensuStatus"
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
            >申诉状态</label
            >
            <el-select
                clearable
                v-model="searchForm.shensuStatus"
                placeholder="请选择申诉状态"
                style="width: 100%;"
            >
              <el-option
                  v-for="(item, index) in shensuStatusOptions"
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

      <!-- 违规记录表格 -->
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
          :data="violationList"
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
            prop="weiguidanhao"
            label="违规单号"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.weiguidanhao }}
          </template>
        </el-table-column>
        <el-table-column
            prop="weiguileixing"
            label="违规类型"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            <el-tag :type="getViolationTagType(row.weiguileixing)">
              {{ row.weiguileixing }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="weiguiTime"
            label="违规时间"
            min-width="160"
            align="center"
        >
          <template #default="{ row }">
            {{ row.weiguiTime || "-" }}
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
            <el-tag :type="row.chulizhuangtai === '已处理' ? 'success' : 'warning'">
              {{ row.chulizhuangtai }}
            </el-tag>
          </template>
        </el-table-column>
        <!-- 新增申诉状态列 -->
        <el-table-column
            prop="shensuStatus"
            label="申诉状态"
            min-width="100"
            align="center"
        >
          <template #default="{ row }">
            <el-tag :type="row.shensuStatus === '已申诉' ? 'info' : 'default'">
              {{ row.shensuStatus || '未申诉' }}
            </el-tag>
          </template>
        </el-table-column>
        <!-- 新增申诉处理状态列 -->
        <el-table-column
            prop="shensuHandleStatus"
            label="申诉处理状态"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            <el-tag :type="getShensuHandleTagType(row.shensuHandleStatus)">
              {{ row.shensuHandleStatus || '未处理' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="weiguiReason"
            label="违规原因"
            min-width="180"
            align="center"
        >
          <template #default="{ row }">
            <div class="remark-box" :title="row.weiguiReason">
              {{ row.weiguiReason || "无" }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
            prop="punishment"
            label="处罚措施"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.punishment || "-" }}
          </template>
        </el-table-column>
        <!-- 新增申诉处理结果列 -->
        <el-table-column
            prop="shensuResult"
            label="申诉处理结果"
            min-width="180"
            align="center"
        >
          <template #default="{ row }">
            <div class="remark-box" :title="row.shensuResult">
              {{ row.shensuResult || "暂无" }}
            </div>
          </template>
        </el-table-column>
        <!-- 操作列 -->
        <el-table-column
            label="操作"
            min-width="180"
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
              >详情</el-button
              >
              <!-- 新增申诉按钮，仅未申诉状态显示 -->
              <el-button
                  type="warning"
                  size="mini"
                  icon="el-icon-circle-check"
                  @click="toShensu(row)"
                  v-if="row.shensuStatus !== '已申诉'"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #E6A23C; background: #E6A23C; color: #fff;"
              >申诉</el-button
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
      // 黑名单状态
      isInBlacklist: true, // 模拟用户在黑名单中
      banEndTime: "2天8小时30分钟", // 模拟解禁时间

      // 搜索表单
      searchForm: {
        weiguidanhao: '',
        weiguileixing: '',
        chulizhuangtai: '',
        shensuStatus: '' // 新增申诉状态查询条件
      },

      // 违规记录列表（模拟数据，新增申诉相关字段）
      violationList: [
        {
          weiguidanhao: "WG20251215001",
          weiguileixing: "迟到签到",
          weiguiTime: "2025-12-10 09:30:25",
          relatedOrder: "YY20251210001",
          chulizhuangtai: "已处理",
          weiguiReason: "预约签到时间超过规定时间30分钟",
          punishment: "警告一次",
          shensuStatus: "未申诉",
          shensuHandleStatus: "未处理",
          shensuResult: ""
        },
        {
          weiguidanhao: "WG20251215002",
          weiguileixing: "未签退",
          weiguiTime: "2025-12-08 18:00:00",
          relatedOrder: "YY20251208002",
          chulizhuangtai: "已处理",
          weiguiReason: "预约结束后未按时签退，占用座位资源",
          punishment: "限制预约1天",
          shensuStatus: "已申诉",
          shensuHandleStatus: "处理中",
          shensuResult: ""
        },
        {
          weiguidanhao: "WG20251215003",
          weiguileixing: "多次取消",
          weiguiTime: "2025-12-05 10:15:40",
          relatedOrder: "YY20251205003",
          chulizhuangtai: "处理中",
          weiguiReason: "单日取消预约超过3次，影响资源分配",
          punishment: "列入黑名单3天",
          shensuStatus: "已申诉",
          shensuHandleStatus: "已处理",
          shensuResult: "申诉不成立，维持原处罚决定，因用户单日取消预约达5次，严重影响资源分配"
        }
      ],

      // 分页相关
      pageIndex: 1,
      pageSize: 10,
      total: 3, // 模拟总数
      dataListLoading: false,
      layouts: ["total", "prev", "pager", "next", "sizes", "jumper"],

      // 下拉选项
      weiguileixingOptions: "迟到签到,未签退,多次取消,占座超时,违规占位".split(","),
      chulizhuangtaiOptions: "已处理,处理中,未处理".split(","),
      shensuStatusOptions: "未申诉,已申诉".split(",") // 新增申诉状态选项
    };
  },
  methods: {
    // 获取违规类型标签样式
    getViolationTagType(type) {
      const typeMap = {
        "迟到签到": "warning",
        "未签退": "danger",
        "多次取消": "info",
        "占座超时": "danger",
        "违规占位": "danger"
      };
      return typeMap[type] || "info";
    },

    // 新增：获取申诉处理状态标签样式
    getShensuHandleTagType(status) {
      const statusMap = {
        "未处理": "default",
        "处理中": "warning",
        "已处理": "success"
      };
      return statusMap[status] || "default";
    },

    // 搜索
    search() {
      this.pageIndex = 1;
      // 这里可后续对接接口
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

    // 查看详情：跳转到违规详情页
    toDetail(row) {
      // 将当前违规记录存入localStorage，供详情页读取
      localStorage.setItem("currentWeiguiObj", JSON.stringify(row));
      // 跳转到违规详情页（对应router.js中的/index/weiguiDetail路由）
      this.$router.push('/index/weiguiDetail');
    },

    // 新增：跳转到申诉页面
    toShensu(row) {
      // 将当前违规记录存入localStorage，供申诉页读取
      localStorage.setItem("currentWeiguiObj", JSON.stringify(row));
      // 跳转到申诉页（对应router.js中的/index/weiguiShensu路由）
      this.$router.push('/index/weiguiShensu');
      // 提示用户跳转成功
      this.$message.success(`正在跳转到【${row.weiguidanhao}】的申诉页面`);
    }
  }
};
</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
}

// 黑名单header样式
.blacklist-header {
  .el-tag {
    margin-left: 8px;
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
  cursor: pointer; /* 添加鼠标指针，提示可点击 */
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
  .blacklist-header {
    flex-direction: column;
    gap: 10px;
    align-items: flex-start !important;
  }
}
</style>