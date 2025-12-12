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
          label="名称"
          prop="mingcheng"
      >
        <el-input
            v-model="ruleForm.mingcheng"
            placeholder="名称"
            clearable
        ></el-input>
      </el-form-item>

      <!-- 移除图片相关表单元素 -->

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
          label="签到状态"
          prop="qiandaozhuangtai"
      >
        <el-select
            v-model="ruleForm.qiandaozhuangtai"
            placeholder="请选择签到状态"
            disabled
        >
          <el-option
              v-for="(item, index) in qiandaozhuangtaiOptions"
              :key="index"
              :label="item"
              :value="item"
          >
          </el-option>
        </el-select>
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
          label="签退状态"
          prop="qiantuizhuangtai"
      >
        <el-select
            v-model="ruleForm.qiantuizhuangtai"
            placeholder="请选择签退状态"
            disabled
        >
          <el-option
              v-for="(item, index) in qiantuizhuangtaiOptions"
              :key="index"
              :label="item"
              :value="item"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <!-- 新增：预约开始时间 -->
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
        >
        </el-date-picker>
      </el-form-item>

      <!-- 新增：预约结束时间 -->
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

      <!-- 简化风格的按钮区域 -->
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
        qiandaozhuangtai: false,
        qiantuizhuangtai: false,
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
        zixishiid:"",
        yuyuedanhao: this.getUUID(),
        mingcheng: "",
        zuowei: "",
        qiandaozhuangtai: "未签到",
        qiantuizhuangtai: "未签退",
        yuyue_start: "",
        yuyue_end: "",
        beizhu: "",
        xuehao: "",
        xingming: "",
        shouji: "",
      },
      qiandaozhuangtaiOptions: [],
      qiantuizhuangtaiOptions: [],
      rules: {
        yuyuedanhao: [],
        mingcheng: [],
        zuowei: [],
        qiandaozhuangtai: [],
        qiantuizhuangtai: [],
        yuyue_start: [
          { required: true, message: "预约开始时间不能为空", trigger: "blur" },
        ],
        yuyue_end: [
          { required: true, message: "预约结束时间不能为空", trigger: "blur" },
        ],
        beizhu: [],
        xuehao: [],
        xingming: [],
        shouji: [{ validator: this.$validate.isMobile, trigger: "blur" }],
        sfsh: [],
        shhf: [],
      },
    };
  },
  computed: {},
  created() {
    let type = this.$route.query.type ? this.$route.query.type : "";
    this.init(type);
    this.baseUrl = this.$config.baseUrl;
  },
  methods: {
    getMakeZero(s) {
      return s < 10 ? "0" + s : s;
    },
    download(file) {
      window.open(`${file}`);
    },
    // 新增：时间校验（结束时间不早于开始时间）
    checkTime() {
      if (this.ruleForm.yuyue_start && this.ruleForm.yuyue_end) {
        const start = new Date(this.ruleForm.yuyue_start).getTime();
        const end = new Date(this.ruleForm.yuyue_end).getTime();
        if (end < start) {
          this.$message.error("结束时间不能早于开始时间");
          this.ruleForm.yuyue_end = "";
        }
      }
    },
    init(type) {
      this.type = type;
      if (type == "cross") {
        var obj = JSON.parse(localStorage.getItem("crossObj"));
        for (var o in obj) {
          if (o == 'id'){
            this.ruleForm.zixishiid = obj[o];
            this.ro.zixishiid = true;
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
          if (o == "qiandaozhuangtai") {
            this.ruleForm.qiandaozhuangtai = obj[o];
            this.ro.qiandaozhuangtai = true;
            continue;
          }
          if (o == "qiantuizhuangtai") {
            this.ruleForm.qiantuizhuangtai = obj[o];
            this.ro.qiantuizhuangtai = true;
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
      this.$http
          .get(this.userTableName + "/session", { emulateJSON: true })
          .then((res) => {
            if (res.data.code == 0) {
              var json = res.data.data;
              if ((json.xuehao != "" && json.xuehao) || json.xuehao == 0) {
                this.ruleForm.xuehao = json.xuehao;
              }
              if ((json.xingming != "" && json.xingming) || json.xingming == 0) {
                this.ruleForm.xingming = json.xingming;
              }
              if ((json.shouji != "" && json.shouji) || json.shouji == 0) {
                this.ruleForm.shouji = json.shouji;
              }
            }
          });
      this.qiandaozhuangtaiOptions = "已签到,未签到".split(",");
      this.qiantuizhuangtaiOptions = "已签退,未签退".split(",");
    },
    info(id) {
      this.$http
          .get("yuyuexinxi/detail/${id}", { emulateJSON: true })
          .then((res) => {
            if (res.data.code == 0) {
              this.ruleForm = res.data.data;
            }
          });
    },
    onSubmit() {
      var obj = JSON.parse(localStorage.getItem("crossObj"));
      var table = localStorage.getItem("crossTable");
      // 关键修复：提前声明变量，避免未定义报错
      let crossuserid = null;
      let crossrefid = null;
      let crossoptnum = null;

      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          if (this.type == "cross") {
            var statusColumnName = localStorage.getItem("statusColumnName");
            var statusColumnValue = localStorage.getItem("statusColumnValue");
            if (statusColumnName && statusColumnName != "") {
              var obj = JSON.parse(localStorage.getItem("crossObj"));
              if (!statusColumnName.startsWith("[")) {
                for (var o in obj) {
                  if (o == statusColumnName) {
                    obj[o] = statusColumnValue;
                  }
                }
                var table = localStorage.getItem("crossTable");
              } else {
                // 赋值到提前声明的变量中
                crossuserid = Number(localStorage.getItem("userid"));
                crossrefid = obj["id"];
                crossoptnum = localStorage.getItem("statusColumnName");
                crossoptnum = crossoptnum.replace(/\[/, "").replace(/\]/, "");
              }
            }
          }
          if (crossrefid && crossuserid) {
            this.ruleForm.crossuserid = crossuserid;
            this.ruleForm.crossrefid = crossrefid;
            var params = {
              page: 1,
              limit: 10,
              crossuserid: crossuserid,
              crossrefid: crossrefid,
            };
            this.$http
                .get("yuyuexinxi/list", {
                  params: params,
                })
                .then((res) => {
                  if (res.data.data.total >= crossoptnum) {
                    this.$message({
                      message: localStorage.getItem("tips"),
                      type: "success",
                      duration: 1500,
                    });
                    return false;
                  } else {
                    var obj = JSON.parse(localStorage.getItem("crossObj"));
                    var table = localStorage.getItem("crossTable");
                    this.$http
                        .post("yuyuexinxi/add", this.ruleForm)
                        .then((res) => {
                          if (res.data.code == 0) {
                            this.$message({
                              message: "操作成功",
                              type: "success",
                              duration: 1500,
                              onClose: () => {
                                this.$router.go(-1);
                              },
                            });
                          } else {
                            this.$message({
                              message: res.data.msg,
                              type: "error",
                              duration: 1500,
                            });
                          }
                        })
                        // 新增：捕获请求异常
                        .catch(err => {
                          this.$message.error("提交失败：" + err.message);
                        });
                  }
                })
                // 新增：捕获列表请求异常
                .catch(err => {
                  this.$message.error("查询数据失败：" + err.message);
                });
          } else {
            var obj = JSON.parse(localStorage.getItem("crossObj"));
            var table = localStorage.getItem("crossTable");
            this.$http.post("yuyuexinxi/add", this.ruleForm).then((res) => {
              if (res.data.code == 0) {
                this.$message({
                  message: "操作成功",
                  type: "success",
                  duration: 1500,
                  onClose: () => {
                    this.$router.go(-1);
                  },
                });
              } else {
                this.$message({
                  message: res.data.msg,
                  type: "error",
                  duration: 1500,
                });
              }
            })
                // 新增：捕获提交请求异常
                .catch(err => {
                  this.$message.error("提交失败：" + err.message);
                });
          }
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
// 响应式基础样式
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

// 统一输入框样式
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

// 按钮hover效果
::v-deep .el-button:hover {
  opacity: 0.9;
}

// 移除图片相关样式
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