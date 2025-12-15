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


      <el-form-item
          :style="{
        width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="预约开始时间"
          prop="yuyue_start"

      >
        <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="ruleForm.yuyue_start"
            type="datetime"
            placeholder="选择开始时间"
            style="width: 100%;"
            @change="checkTime"
            :picker-options="pickerOptions"
        >
        </el-date-picker>
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
          label="预约结束时间"
          prop="yuyue_end"
      >
        <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="ruleForm.yuyue_end"
            type="datetime"
            placeholder="选择结束时间"
            style="width: 100%;"
            @change="checkTime"
            :picker-options="pickerOptions"
        >
        </el-date-picker>
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
        // qiandaozhuangtai: false,
        // qiantuizhuangtai: false,
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
        // qiandaozhuangtai: "未签到",

        // qiantuizhuangtai: "未签退",
        yuyue_start: "",
        yuyue_end: "",
        beizhu: "",
        xuehao: "",
        xingming: "",
        shouji: "",
      },
      // ********************************** 修改：时间选择器配置 **********************************
      pickerOptions: {
        // 限制时间在 8:00:00 - 22:00:00 之间
        selectableRange: '08:00:00 - 22:00:00',

        // 限制日期为今天和明天
        disabledDate(time) {
          const today = new Date();

          // 获取今天的开始时间 (00:00:00)
          const todayStart = new Date(today.getFullYear(), today.getMonth(), today.getDate(), 0, 0, 0);

          // 获取后天的开始时间 (后天 00:00:00)
          const tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
          const dayAfterTomorrowStart = new Date(tomorrow.getFullYear(), tomorrow.getMonth(), tomorrow.getDate() + 1, 0, 0, 0);

          // 禁用过去的日期（早于今天 00:00:00）
          const isPast = time.getTime() < todayStart.getTime();

          // 禁用后天及以后的日期（从后天 00:00:00 开始禁用）
          const isFuture = time.getTime() >= dayAfterTomorrowStart.getTime();

          return isPast || isFuture;
        }
      },
      // ***************************************************************************************
      qiandaozhuangtaiOptions: [],
      qiantuizhuangtaiOptions: [],
      rules: {
        yuyuedanhao: [],

        // 修复1：添加名称必填校验
        mingcheng: [
          { required: true, message: "图书馆/自习室名称不能为空", trigger: "blur" }
        ],
        zuowei: [],
        qiandaozhuangtai: [],
        qiantuizhuangtai: [],
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
          { required: true, message: "姓名不能为空", trigger: "blur"
          }
        ],
        shouji: [{ validator: this.$validate.isMobile, trigger: "blur" }],
        sfsh: [],
        shhf: [],
      },
    };
    // 修复4：修改 data 结束位置的引用，防止影响逻辑
  },
  computed: {},
  created() {
    let type = this.$route.query.type ? this.$route.query.type : "";
    this.init(type);
    this.baseUrl = this.$config.baseUrl;
  },
  methods: {
    getMakeZero(s) {
      return s < 10 ?
          "0" + s : s;
    },
    download(file) {
      window.open(`${file}`);
    },

    // ****************************** 统一的 checkTime 校验方法 ******************************
    checkTime() {
      console.log("当前选择的开始时间：", this.ruleForm.yuyue_start);
      console.log("当前选择的结束时间：", this.ruleForm.yuyue_end);

      const START_HOUR = 8;
      const END_HOUR = 22;

      if (this.ruleForm.yuyue_start && this.ruleForm.yuyue_end) {
        const start = new Date(this.ruleForm.yuyue_start).getTime();
        const end = new Date(this.ruleForm.yuyue_end).getTime();

        // 1. 结束时间不能早于或等于开始时间
        if (end <= start) {
          this.$message.error("结束时间必须晚于开始时间");
          this.ruleForm.yuyue_end = "";
          return;
        }

        // 2. 校验时间范围（8:00 到 22:00）
        const startDate = new Date(this.ruleForm.yuyue_start);
        const endDate = new Date(this.ruleForm.yuyue_end);

        // 校验开始时间不能早于 8 点
        if (startDate.getHours() < START_HOUR) {
          this.$message.error("预约开始时间不能早于 " + this.getMakeZero(START_HOUR) + ":00");
          this.ruleForm.yuyue_start = "";
          return;
        }

        // 校验结束时间不能晚于 22 点
        if (endDate.getHours() > END_HOUR || (endDate.getHours() === END_HOUR && (endDate.getMinutes() > 0 || endDate.getSeconds() > 0))) {
          this.$message.error("预约结束时间不能晚于 " + this.getMakeZero(END_HOUR) + ":00");
          this.ruleForm.yuyue_end = "";
          return;
        }

        // 3. 校验日期范围（今天和明天）
        const today = new Date();
        const todayStart = new Date(today.getFullYear(), today.getMonth(), today.getDate(), 0, 0, 0);
        const dayAfterTomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 2, 0, 0, 0); // 后天的开始时间

        // 确保开始日期在今天和明天之间
        if (startDate.getTime() < todayStart.getTime() || startDate.getTime() >= dayAfterTomorrow.getTime()) {
          this.$message.error("预约日期仅允许选择今天和明天");
          this.ruleForm.yuyue_start = "";
          this.ruleForm.yuyue_end = "";
          return;
        }
        // 确保结束日期也在今天和明天之间
        if (endDate.getTime() >= dayAfterTomorrow.getTime()) {
          this.$message.error("预约日期仅允许选择今天和明天");
          this.ruleForm.yuyue_start = "";
          this.ruleForm.yuyue_end = "";
          return;
        }

        // 4. 校验预约不能跨天
        const startDay = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()).getTime();
        const endDay = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate()).getTime();

        if(startDay !== endDay) {
          this.$message.error("预约不能跨越日期");
          this.ruleForm.yuyue_end = "";
          return;
        }


      } else if (!this.ruleForm.yuyue_start) {
        this.$message.warning("请选择预约开始时间");
      } else if (!this.ruleForm.yuyue_end) {
        this.$message.warning("请选择预约结束时间");
      }
    },
    // ******************************************************************************

    init(type) {
      this.type = type;
      if (type == "cross") {
        var obj = JSON.parse(localStorage.getItem("crossObj") || "{}");
        for (var o in obj) {
          if (o == 'id'){
            this.ruleForm.zixishiid = obj[o];
            this.ro.zixishiid = true;
            // 修复2：根据自习室ID查询名称并自动赋值
            this.$http.get('zixishi/detail/' + obj[o]).then(res => {
              if (res.data.code == 0) {
                this.ruleForm.mingcheng = res.data.data.mingcheng;
                this.ro.mingcheng = true; // 赋值后设为只读
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
          // if (o == "qiandaozhuangtai") {
          //   this.ruleForm.qiandaozhuangtai = obj[o];
          //   this.ro.qiandaozhuangtai = true;
          //   continue;
          // }
          // if (o == "qiantuizhuangtai") {
          //   this.ruleForm.qiantuizhuangtai = obj[o];
          //   this.ro.qiantuizhuangtai = true;
          //   continue;
          // }
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
      // 初始化状态选项
      // this.qiandaozhuangtaiOptions = "已签到,未签到".split(",");
      // this.qiantuizhuangtaiOptions = "已签退,未签退".split(",");
    },
    // 修复3：完整的提交逻辑（含名称字段正确传递）
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
          // 2. 构造参数：保留名称字段，兼容后端字段格式
          const submitData = {
            ...this.ruleForm,
            // 兼容后端驼峰字段
            yuyueStart: this.ruleForm.yuyue_start,
            yuyueEnd: this.ruleForm.yuyue_end,

            // 保留原下划线字段（避免后端兼容问题）
            yuyue_start: this.ruleForm.yuyue_start,
            yuyue_end: this.ruleForm.yuyue_end
          };

          // 3. 打印最终提交参数（调试用）
          console.log("最终提交的参数：", submitData);

          // 4. 发送请求：强制设置JSON请求头
          this.$http.post("yuyuexinxi/submit", submitData, {

            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            },
            emulateJSON: false // 关键：关闭表单模拟，发送纯JSON
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
  },
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
}

.el-date-editor.el-input {
  width: 100%;
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

::v-deep .el-button:hover {
  opacity: 0.9;
}

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