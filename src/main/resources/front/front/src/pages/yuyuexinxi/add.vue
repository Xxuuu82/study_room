<template>
  <div
      :style="{
      width: '100%',
      maxWidth: '1000px',
      padding: '20px',
      boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.302)',
      margin: '10px auto',
      position: 'relative',
      background: '#fff',
      borderRadius: '8px',
      boxSizing: 'border-box'
    }"
  >
    <el-form
        class="add-update-preview"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
    >
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="预约单号"
          prop="yuyuedanhao"
      >
        <el-input
            v-model="ruleForm.yuyuedanhao"
            placeholder="预约单号"
            readonly
        ></el-input>
      </el-form-item>

      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="自习室名称"
          prop="mingcheng"
      >
        <el-input
            v-model="ruleForm.mingcheng"
            placeholder="请输入自习室名称"
            clearable
            :readonly="ro.mingcheng"
        ></el-input>
      </el-form-item>

      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="座位"
          prop="zuowei"
      >
        <el-input
            v-model="ruleForm.zuowei"
            placeholder="座位"
            clearable
            readonly
        ></el-input>
      </el-form-item>

      <!-- 重构时间选择区域：改为时间段选择 -->
      <el-form-item
          :style="{
        width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="预约时间段"
          prop="timeRange"
      >
        <div style="display: flex; gap: 10px; align-items: center;">
          <!-- 日期选择：新增cell-class-name属性 -->
          <el-date-picker
              v-model="selectedDate"
              type="date"
              placeholder="选择日期"
              style="width: 40%;"
              :disabled-date="disabledDate"
              @change="generateTimeOptions"
              :cell-class-name="setCellClassName"
          ></el-date-picker>

          <!-- 开始时间选择（下拉框展示30分钟粒度的可选时间） -->
          <el-select
              v-model="ruleForm.yuyue_start"
              placeholder="选择开始时间"
              style="width: 30%;"
              @change="handleStartTimeChange"
              filterable
          >
            <el-option
                v-for="time in availableStartTimeOptions"
                :key="time.value"
                :label="time.label"
                :value="time.value"
            ></el-option>
          </el-select>

          <!-- 结束时间选择（下拉框展示30分钟粒度的可选时间） -->
          <el-select
              v-model="ruleForm.yuyue_end"
              placeholder="选择结束时间"
              style="width: 30%;"
              @change="checkTimeRange"
              filterable
          >
            <el-option
                v-for="time in availableEndTimeOptions"
                :key="time.value"
                :label="time.label"
                :value="time.value"
            ></el-option>
          </el-select>
        </div>
      </el-form-item>

      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="备注"
          prop="beizhu"
      >
        <el-input
            v-model="ruleForm.beizhu"
            placeholder="备注"
            clearable
        ></el-input>
      </el-form-item>

      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="学号"
          prop="xuehao"
      >
        <el-input
            v-model="ruleForm.xuehao"
            placeholder="学号"
            clearable
        ></el-input>
      </el-form-item>

      <el-form-item
          :style="{
    width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="姓名"
          prop="xingming"
      >
        <el-input
            v-model="ruleForm.xingming"
            placeholder="姓名"
            clearable
        ></el-input>
      </el-form-item>

      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
    margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="手机"
          prop="shouji"
      >
        <el-input
            v-model="ruleForm.shouji"
            placeholder="手机"
            clearable
        ></el-input>
      </el-form-item>

      <el-form-item :style="{
        padding: '20px 0 0',
        margin: '0',
        textAlign: 'center',
        width: '100%'
      }">
        <el-button
            :style="{
    margin: '0 10px 10px',
            padding: '0 24px',
            height: '40px',
            borderRadius: '4px',
            fontSize: '14px',
            background: '#2e61e1',
            color: '#fff',
            border: 'none',
            transition: 'opacity 0.2s'
          }"
            type="primary"
            @click="onSubmit"
        >提交</el-button
        >
        <el-button
            :style="{
    margin: '0 10px 10px',
            padding: '0 24px',
            height: '40px',
            borderRadius: '4px',
            fontSize: '14px',
            background: '#f5f7fa',
            color: '#666',
            border: '1px solid #e4e7ed',
            transition: 'opacity 0.2s'
          }"
            @click="back()"
        >返回</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      baseUrl: "",
      ro: {
        zixishiid: false,
        yuyuedanhao: false,
        mingcheng: false,
        zuowei: false,
        yuyue_start: false,
        yuyue_end: false,
        beizhu: false,
        xuehao: false,
        xingming: false,
        shouji: false,
        sfsh: false,
        shhf: false,
      },
      type: "",
      userTableName: localStorage.getItem("UserTableName"),
      ruleForm: {
        zixishiid: "",
        yuyuedanhao: this.getUUID(),
        mingcheng: "",
        zuowei: "",
        yuyue_start: "",
        yuyue_end: "",
        beizhu: "",
        xuehao: "",
        xingming: "",
        shouji: "",
      },
      // 新增：日期和时间选择相关
      selectedDate: "", // 选中的日期 (可以是 Date 对象或 yyyy-MM-dd 字符串）
      availableStartTimeOptions: [], // 可选开始时间列表
      availableEndTimeOptions: [], // 可选结束时间列表
      // 时间配置常量
      TIME_CONFIG: {
        startHour: 8,    // 开始小时
        endHour: 22,     // 结束小时
        stepMinutes: 30  // 时间步长（分钟）
      },
      qiandaozhuangtaiOptions: [],
      qiantuizhuangtaiOptions: [],
      rules: {
        yuyuedanhao: [],
        mingcheng: [
          { required: true, message: "图书馆/自习室名称不能为空", trigger: "blur" }
        ],
        zuowei: [],
        yuyue_start: [
          { required: true, message: "预约开始时间不能为空", trigger: "change" },
        ],
        yuyue_end: [
          { required: true, message: "预约结束时间不能为空", trigger: "change" },
        ],
        beizhu: [],
        xuehao: [
          { required: true, message: "学号不能为空", trigger: "blur" }
        ],
        xingming: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        shouji: [{ validator: this.$validate.isMobile, trigger: "blur" }],
      },
    };
  },
  computed: {},
  created() {
    let type = this.$route.query.type ? this.$route.query.type : "";
    this.init(type);
    this.baseUrl = this.$config.baseUrl;
    // 初始化默认选中今天（字符串形式）
    const today = new Date();
    this.selectedDate = this.formatDate(today);
    // 生成默认时间选项
    this.generateTimeOptions();
  },
  methods: {
    getMakeZero(s) {
      return s < 10 ? "0" + s : s;
    },
    download(file) {
      window.open(`${file}`);
    },

    // 格式化日期为 yyyy-MM-dd
    formatDate(date) {
      const year = date.getFullYear();
      const month = this.getMakeZero(date.getMonth() + 1);
      const day = this.getMakeZero(date.getDate());
      return `${year}-${month}-${day}`;
    },

    // 获取 selectedDate 的字符串 yyyy-MM-dd（兼容 Date 或 字符串）
    getSelectedDateStr() {
      if (!this.selectedDate) return "";
      if (typeof this.selectedDate === "string") {
        return this.selectedDate.split(" ")[0];
      }
      return this.formatDate(this.selectedDate);
    },

    // 将 "yyyy-MM-dd HH:mm:ss" 解析为 Date 对象（避免直接 new Date 字符串解析差异）
    parseDateTimeString(dateTimeStr) {
      if (!dateTimeStr || typeof dateTimeStr !== 'string') return null;
      const parts = dateTimeStr.trim().split(' ');
      if (parts.length < 2) return null;
      const datePart = parts[0];
      const timePart = parts[1];
      const [y, m, d] = datePart.split('-').map(Number);
      const [hh, mm, ss] = timePart.split(':').map(Number);
      if (![y, m, d].every(n => !isNaN(n))) return null;
      return new Date(y, (m || 1) - 1, d || 1, hh || 0, mm || 0, ss || 0);
    },

    // 严格限制日期为今明两天，其余日期禁用
    disabledDate(time) {
      const today = new Date();
      const todayTime = new Date(today.getFullYear(), today.getMonth(), today.getDate()).getTime();
      const tomorrowTime = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1).getTime();
      const currentTime = new Date(time.getFullYear(), time.getMonth(), time.getDate()).getTime();

      // 仅允许今天和明天，其余日期返回true（禁用）
      return currentTime !== todayTime && currentTime !== tomorrowTime;
    },

    // 新增：给非今明两天的日期添加自定义类名
    setCellClassName({ date }) {
      const today = new Date();
      const todayTime = new Date(today.getFullYear(), today.getMonth(), today.getDate()).getTime();
      const tomorrowTime = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1).getTime();
      const currentTime = new Date(date.getFullYear(), date.getMonth(), date.getDate()).getTime();

      // 非今明两天的日期添加自定义类
      if (currentTime !== todayTime && currentTime !== tomorrowTime) {
        return 'disabled-date-custom';
      }
      return '';
    },

    // 生成可选时间选项（30分钟粒度）
    generateTimeOptions() {
      const selectedDateStr = this.getSelectedDateStr();
      if (!selectedDateStr) {
        this.availableStartTimeOptions = [];
        this.availableEndTimeOptions = [];
        this.ruleForm.yuyue_start = "";
        this.ruleForm.yuyue_end = "";
        return;
      }

      const { startHour, endHour, stepMinutes } = this.TIME_CONFIG;
      const now = new Date();
      const isToday = this.formatDate(now) === selectedDateStr;

      // 生成基础时间列表（8:00 - 22:00，30分钟步长）
      const timeList = [];
      for (let hour = startHour; hour <= endHour; hour++) {
        for (let minute = 0; minute < 60; minute += stepMinutes) {
          if (hour === endHour && minute > 0) break;

          const timeStr = `${this.getMakeZero(hour)}:${this.getMakeZero(minute)}:00`;
          const fullTimeStr = `${selectedDateStr} ${timeStr}`;
          const timeObj = this.parseDateTimeString(fullTimeStr);
          if (!timeObj) continue;

          // 当天：必须晚于当前系统时间（严格大于 now）
          if (isToday && timeObj.getTime() <= now.getTime()) continue;

          timeList.push({
            label: timeStr,
            value: fullTimeStr
          });
        }
      }

      this.availableStartTimeOptions = [...timeList];
      this.availableEndTimeOptions = [...timeList];

      this.ruleForm.yuyue_start = "";
      this.ruleForm.yuyue_end = "";
    },

    // 开始时间变化时，正确更新结束时间可选范围
    handleStartTimeChange() {
      if (!this.ruleForm.yuyue_start) {
        this.availableEndTimeOptions = [...this.availableStartTimeOptions];
        this.ruleForm.yuyue_end = "";
        return;
      }

      const startDateObj = this.parseDateTimeString(this.ruleForm.yuyue_start);
      if (!startDateObj) {
        this.$message.error("开始时间解析失败，请重新选择");
        this.availableEndTimeOptions = [];
        this.ruleForm.yuyue_end = "";
        return;
      }
      const startTimeTs = startDateObj.getTime();

      this.availableEndTimeOptions = this.availableStartTimeOptions.filter(item => {
        const t = this.parseDateTimeString(item.value);
        return t && t.getTime() > startTimeTs;
      });

      this.ruleForm.yuyue_end = "";

      if (this.availableEndTimeOptions.length === 0) {
        this.$message.warning("当前没有可选的结束时间，请重新选择开始时间");
      }
    },

    // 检查时间范围合法性
    checkTimeRange() {
      if (!this.ruleForm.yuyue_start || !this.ruleForm.yuyue_end) return;

      const startDateObj = this.parseDateTimeString(this.ruleForm.yuyue_start);
      const endDateObj = this.parseDateTimeString(this.ruleForm.yuyue_end);
      if (!startDateObj || !endDateObj) {
        this.$message.error("时间解析失败，请重新选择时间");
        return;
      }

      const start = startDateObj.getTime();
      const end = endDateObj.getTime();

      if (end <= start) {
        this.$message.error("结束时间必须晚于开始时间");
        this.ruleForm.yuyue_end = "";
      } else {
        const startDay = this.formatDate(startDateObj);
        const endDay = this.formatDate(endDateObj);
        if (startDay !== endDay) {
          this.$message.error("预约不能跨越日期");
          this.ruleForm.yuyue_end = "";
        }
      }
    },

    // 简化的时间校验（保留核心逻辑） - 兜底
    checkTime() {
      if (this.ruleForm.yuyue_start && this.ruleForm.yuyue_end) {
        this.checkTimeRange();
      }
    },

    init(type) {
      this.type = type;
      if (type == "cross") {
        var obj = JSON.parse(localStorage.getItem("crossObj") || "{}");
        for (var o in obj) {
          if (o == 'id') {
            this.ruleForm.zixishiid = obj[o];
            this.ro.zixishiid = true;
            this.$http.get('zixishi/detail/' + obj[o]).then(res => {
              if (res.data.code == 0) {
                this.ruleForm.mingcheng = res.data.data.mingcheng;
                this.ro.mingcheng = true;
              }
            }).catch(err => {
              console.error("查询自习室名称失败：", err);
              this.$message.warning("无法自动获取自习室名称，请手动输入");
            });
            continue;
          }
          if (o == "yuyuedanhao") {
            this.ruleForm.yuyuedanhao = obj[o];
            this.ro.yuyuedanhao = true;
            continue;
          }
          if (o == "mingcheng") {
            this.ruleForm.mingcheng = obj[o];
            this.ro.mingcheng = true;
            continue;
          }
          if (o == "zuowei") {
            this.ruleForm.zuowei = obj[o];
            this.ro.zuowei = true;
            continue;
          }
          if (o == "beizhu") {
            this.ruleForm.beizhu = obj[o];
            this.ro.beizhu = true;
            continue;
          }
          if (o == "xuehao") {
            this.ruleForm.xuehao = obj[o];
            this.ro.xuehao = true;
            continue;
          }
          if (o == "xingming") {
            this.ruleForm.xingming = obj[o];
            this.ro.xingming = true;
            continue;
          }
          if (o == "shouji") {
            this.ruleForm.shouji = obj[o];
            this.ro.shouji = true;
            continue;
          }
        }
      }
      // 初始化用户信息
      this.$http
          .get(this.userTableName + "/session", { emulateJSON: true })
          .then((res) => {
            if (res.data.code == 0) {
              var json = res.data.data || {};
              if (json.xuehao || json.xuehao === 0) {
                this.ruleForm.xuehao = json.xuehao;
              }
              if (json.xingming || json.xingming === 0) {
                this.ruleForm.xingming = json.xingming;
              }
              if (json.shouji || json.shouji === 0) {
                this.ruleForm.shouji = json.shouji;
              }
            }
          })
          .catch(err => {
            console.error("获取用户信息失败：", err);
          });
    },

    onSubmit() {
      // 1. 前端基础校验
      if (!this.ruleForm.yuyue_start) {
        this.$message.error("请选择预约开始时间");
        return;
      }
      if (!this.ruleForm.yuyue_end) {
        this.$message.error("请选择预约结束时间");
        return;
      }
      if (!this.ruleForm.xuehao) {
        this.$message.error("学号不能为空");
        return;
      }
      if (!this.ruleForm.xingming) {
        this.$message.error("姓名不能为空");
        return;
      }
      if (!this.ruleForm.mingcheng) {
        this.$message.error("图书馆/自习室名称不能为空");
        return;
      }

      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          const submitData = {
            ...this.ruleForm,
            yuyueStart: this.ruleForm.yuyue_start,
            yuyueEnd: this.ruleForm.yuyue_end,
            yuyue_start: this.ruleForm.yuyue_start,
            yuyue_end: this.ruleForm.yuyue_end
          };

          console.log("最终提交的参数：", submitData);

          this.$http.post("yuyuexinxi/submit", submitData, {
            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            },
            emulateJSON: false
          })
              .then((res) => {
                console.log("后端返回结果：", res.data);
                if (res.data.code === 0 || res.data.msg === "预约成功") {
                  this.$message({
                    message: "预约提交成功",
                    type: "success",
                    duration: 1500,
                    onClose: () => {
                      this.$router.go(-1);
                    },
                  });
                } else {
                  this.$message.error(res.data.msg || "提交失败");
                }
              })
              .catch(err => {
                console.error("请求失败：", err);
                this.$message.error("网络异常：" + (err.message || "提交失败"));
              });
        }
      });
    },

    getUUID() {
      return new Date().getTime();
    },

    back() {
      this.$router.go(-1);
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@media (max-width: 768px) {
  .add-update-preview .el-form-item >>> .el-form-item__label {
    width: 100% !important;
    text-align: left !important;
    line-height: 24px !important;
    padding: 0 0 8px 0 !important;
  }

  .add-update-preview .el-form-item >>> .el-form-item__content {
    margin-left: 0 !important;
  }

  /* 移动端时间选择区域适配 */
  .el-form-item .el-form-item__content > div {
    flex-direction: column;
  }

  .el-form-item .el-form-item__content > div .el-date-picker,
  .el-form-item .el-form-item__content > div .el-select {
    width: 100% !important;
    margin-bottom: 10px;
  }
}

.el-date-editor.el-input,
.el-select {
  width: 100%;
}


/* 终极强制样式：直接匹配日期选择器的所有非今明日期 */
::v-deep .el-date-table td:not(.current):not(.today) .cell {
  background: #f5f5f5 !important;
  color: #c0c4cc !important;
  text-decoration: line-through !important;
  text-decoration-color: #A0A0A0 !important;
  text-decoration-thickness: 1px !important;
  pointer-events: none !important;
  border-radius: 4px !important;
}

/* 兼容Element Plus */
::v-deep .el-date-table__cell:not(.is-today):not(.is-selected) .el-date-table__cell-content {
  background: #f5f5f5 !important;
  color: #c0c4cc !important;
  text-decoration: line-through !important;
  text-decoration-color: #A0A0A0 !important;
  pointer-events: none !important;
}

.add-update-preview .el-form-item >>> .el-form-item__label {
  padding: 0 10px 0 0;
  color: #666;
  font-weight: 500;
  width: 120px;
  font-size: 14px;
  line-height: 40px;
  text-align: right;
}

.add-update-preview .el-form-item >>> .el-form-item__content {
  margin-left: 120px;
}

.add-update-preview .el-input >>> .el-input__inner,
.add-update-preview .el-select >>> .el-input__inner,
.add-update-preview .el-date-editor >>> .el-input__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 0 12px;
  outline: none;
  color: #000;
  background: #bfd2fc;
  width: 100%;
  font-size: 14px;
  height: 40px;
  box-sizing: border-box;
}

/* 优化下拉选择框样式 */
::v-deep .el-select-dropdown__item {
  padding: 8px 16px;
  font-size: 14px;
}

::v-deep .el-select-dropdown__item.selected {
  color: #2e61e1;
  background-color: #f0f5ff;
}

::v-deep .el-button:hover {
  opacity: 0.9;
}

/* 核心：自定义禁用日期样式（强制生效） */
::v-deep .disabled-date-custom {
  pointer-events: none !important;
}

::v-deep .disabled-date-custom .cell {
  background: #f5f5f5 !important;
  color: #c0c4cc !important;
  text-decoration: line-through !important;
  text-decoration-color: #A0A0A0 !important;
  text-decoration-thickness: 1px !important;
  border-radius: 4px !important;
}

/* 保留原有 textarea 样式 */
.add-update-preview .el-textarea >>> .el-textarea__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 12px;
  outline: none;
  color: #000;
  background: #bfd2fc;
  width: 100%;
  font-size: 14px;
  height: 120px;
}
</style>