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

      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="预约时间"
          prop="yuyueshijian"
      >
        <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="ruleForm.yuyueshijian"
            type="datetime"
            placeholder="预约时间"
            style="width: 100%;"
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
        // 移除图片相关属性
        zuowei: false,
        qiandaozhuangtai: false,
        qiantuizhuangtai: false,
        yuyueshijian: false,
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
        // 移除图片字段
        zuowei: "",
        qiandaozhuangtai: "未签到",
        qiantuizhuangtai: "未签退",
        yuyueshijian: "",
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
        // 移除图片校验规则
        zuowei: [
          // { validator: this.$validate.isIntNumer, trigger: 'blur' },
        ],
        qiandaozhuangtai: [],
        qiantuizhuangtai: [],
        yuyueshijian: [
          { required: true, message: "预约时间不能为空", trigger: "blur" },
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
    //this.bg();
    let type = this.$route.query.type ? this.$route.query.type : "";
    this.init(type);
    this.baseUrl = this.$config.baseUrl;
  },
  methods: {
    getMakeZero(s) {
      return s < 10 ? "0" + s : s;
    },
    // 下载
    download(file) {
      window.open(`${file}`);
    },
    // 初始化
    init(type) {
      this.type = type;
      if (type == "cross") {
        var obj = JSON.parse(localStorage.getItem("crossObj"));
        // console.log(obj);
        for (var o in obj) {
          if (o == 'id'){
            this.ruleForm.zixishiid = obj[o];
            this.ro.zixishiid = true;
            // console.log(obj[o]);
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
          // 移除图片相关初始化逻辑
          if (o == "zuowei") {
            // console.log(obj[o]);
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
          if (o == "yuyueshijian") {
            this.ruleForm.yuyueshijian = obj[o];
            this.ro.yuyueshijian = true;
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
        // this.ruleForm.zuowei = 0
      }
      // 获取用户信息
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

    // 多级联动参数
    // 多级联动参数
    info(id) {
      this.$http
          .get("yuyuexinxi/detail/${id}", { emulateJSON: true })
          .then((res) => {
            if (res.data.code == 0) {
              this.ruleForm = res.data.data;
            }
          });
    },
    // 提交
    onSubmit() {
      var obj = JSON.parse(localStorage.getItem("crossObj"));
      var table = localStorage.getItem("crossTable");
      // obj.zuowei = obj.zuowei - this.ruleForm.zuowei
      // if(obj.zuowei<0){
      //   this.$message.error("座位不足");
      //   return
      // }

      //this.$http.post(table+`/update`, obj).then(res => {});
      //更新跨表属性
      var crossuserid;
      var crossrefid;
      var crossoptnum;
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          if (this.type == "cross") {
            var statusColumnName = localStorage.getItem("statusColumnName");
            var statusColumnValue = localStorage.getItem("statusColumnValue");
            if (statusColumnName && statusColumnName != "") {
              var obj = JSON.parse(localStorage.getItem("crossObj"));
              //  console.log(obj);
              if (!statusColumnName.startsWith("[")) {
                for (var o in obj) {
                  if (o == statusColumnName) {
                    obj[o] = statusColumnValue;
                    //  console.log(obj[o]);
                  }
                }
                var table = localStorage.getItem("crossTable");
                //  console.log(table)
                //  this.$http.post(table+'/update', obj).then(res => {});
              } else {
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
                    // 跨表计算
                    var obj = JSON.parse(localStorage.getItem("crossObj"));
                    var table = localStorage.getItem("crossTable");

                    // obj.zuowei = parseFloat(obj.zuowei) - parseFloat(this.ruleForm.zuowei)
                    // console.log('ss')
                    console.log(obj);
                    // this.$http.post(table+`/update`,obj).then(res => {});
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
                        });
                  }
                });
          } else {
            var obj = JSON.parse(localStorage.getItem("crossObj"));
            var table = localStorage.getItem("crossTable");

            // obj.zuowei = parseFloat(obj.zuowei) - parseFloat(this.ruleForm.zuowei)
            // console.log('sssss' + table)
            // console.log(obj);
            // this.$http.post(table+`/update`,obj).then(res => {});
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
            });
          }
        }
      });
    },
    // 获取uuid
    getUUID() {
      return new Date().getTime();
    },
    // 返回
    back() {
      this.$router.go(-1);
    },
    // 移除图片上传方法
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