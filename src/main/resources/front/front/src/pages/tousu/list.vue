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

    <!-- 列表容器 -->
    <div class="list-preview" :style='{"width":"100%","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"20px auto","position":"relative","background":"#fff","boxSizing":"border-box","padding":"0 10px"}'>

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
            label="投诉编号"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.tousudanhao }}
          </template>
        </el-table-column>
        <el-table-column
            prop="tousurenxuehao"
            label="投诉人学号"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.tousurenxuehao }}
          </template>
        </el-table-column>
        <el-table-column
            prop="zixishi"
            label="自习室"
            min-width="100"
            align="center"
        >
          <template #default="{ row }">
            {{ row.zixishi || "-" }}
          </template>
        </el-table-column>
        <el-table-column
            prop="zuoweihao"
            label="座位号"
            min-width="100"
            align="center"
        >
          <template #default="{ row }">
            {{ row.zuoweihao || "-" }}
          </template>
        </el-table-column>
        <el-table-column
            prop="tousuleixing"
            label="投诉类型"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            <el-tag :type="getTypeTagType(row.tousuleixing)">{{ row.tousuleixing }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="tousuReason"
            label="投诉详情"
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
            prop="chulizhuangtai"
            label="状态"
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
            prop="tousuTime"
            label="创建时间"
            min-width="160"
            align="center"
        >
          <template #default="{ row }">
            {{ row.tousuTime || "-" }}
          </template>
        </el-table-column>
        <!-- 操作列 -->
        <el-table-column
            label="操作"
            min-width="100"
            align="center"
        >
          <template #default="{ row }">
            <div style="display: flex; gap: 6px; justify-content: center; align-items: center; flex-wrap: wrap;">
              <el-button
                  type="danger"
                  size="mini"
                  icon="el-icon-circle-close"
                  @click="cancelComplaint(row)"
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

      complaintList: [], // 数据由API拉取填充

      // 分页相关
      pageIndex: 1,
      pageSize: 10,
      total: 0,
      dataListLoading: false,
      layouts: ["total", "prev", "pager", "next", "sizes", "jumper"],

      tousuleixingOptions: "大声喧哗,乱吃乱喝,随地大小便,其他".split(","),
      chulizhuangtaiOptions: "待处理,处理中,已结案,已驳回".split(",")
    };
  },
  created() {
    this.getComplaintList();
  },
  methods: {
    // 投诉类型 => 标签样式
    getTypeTagType(type) {
      const typeMap = {
        "大声喧哗": "warning",
        "乱吃乱喝": "danger",
        "随地大小便": "danger",
        "设备故障": "success",
        "服务态度": "primary",
        "占座违规": "info",
        "其他": "info"
      };
      return typeMap[type] || "info";
    },
    // 状态 => 标签样式
    getStateTagType(state) {
      const stateMap = {
        "待处理": "info",
        "处理中": "warning",
        "已结案": "success",
        "已驳回": "danger"
      };
      return stateMap[state] || "info";
    },
    // 跳转添加页
    toAddComplaint() {
      this.$router.push('/index/tousuAdd');
    },
    // 拉取当前用户投诉数据
    getComplaintList() {
      const userId = localStorage.getItem("username");
      if (!userId) {
        this.$message.error("请先登录");
        this.complaintList = [];
        return;
      }
      this.dataListLoading = true;
      this.$http.get('/study_room/complaint/student/list')
          .then(res => {
            if (res.data.code === 0) {
              // 后端返回 complaint 对象数组，对应 complaint 表字段
              // 映射字段到前端使用的格式
              this.complaintList = (res.data.data || []).map(item => ({
                tousudanhao: item.complaintNo || item.complaint_no,
                tousurenxuehao: item.userId || item.user_id,
                zixishi: item.roomId || item.room_id,
                zuoweihao: item.seatId || item.seat_id,
                tousuleixing: this.categoryDisplay(item.category),
                tousuReason: item.detail || "",
                chulizhuangtai: this.statusDisplay(item.status),
                tousuTime: this.formatDateTime(item.createdAt || item.created_at)
              }));
              this.total = this.complaintList.length;
            } else {
              this.$message.error(res.data.msg || "获取投诉失败");
              this.complaintList = [];
            }
            this.dataListLoading = false;
          }).catch(() => {
        this.dataListLoading = false;
        this.$message.error("网络错误，获取投诉失败");
        this.complaintList = [];
      });
    },
    // 分类数字转文本
    categoryDisplay(cat) {
      switch(Number(cat)) {
        case 1: return "乱吃乱喝";
        case 2: return "大声喧哗";
        case 3: return "随地大小便";
        case 4: return "其他";
        default: return "其他";
      }
    },
    // 状态数字转文本
    statusDisplay(status) {
      switch(Number(status)) {
        case 0: return "待处理";
        case 1: return "处理中";
        case 2: return "已结案";
        case 3: return "已驳回";
        default: return "待处理";
      }
    },
    // 时间格式化
    formatDateTime(dt) {
      if (!dt) return "-";
      let d = new Date(dt);
      return `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`;
    },
    // 搜索（可扩展：根据 searchForm 过滤 complaintList）
    search() {
      // 可加过滤，目前简单直接显示所有
      this.pageIndex = 1;
      this.dataListLoading = true;
      setTimeout(() => {
        this.dataListLoading = false;
      }, 500);
    },
    // 分页大小改变
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
    },
    // 当前页改变
    currentChangeHandle(val) {
      this.pageIndex = val;
    },
    // 取消投诉（模拟删除前端数据，可后接接口）
    cancelComplaint(row) {
      this.$confirm('确定要取消该投诉吗？取消后记录将被删除', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.complaintList = this.complaintList.filter(item => item.tousudanhao !== row.tousudanhao);
        this.total = this.complaintList.length;
        this.$message.success(`已取消投诉单号${row.tousudanhao}的投诉`);
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

.complaint-header {
  .el-button {
    &:hover {
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
    }
  }
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