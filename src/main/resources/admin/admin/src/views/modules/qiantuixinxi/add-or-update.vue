<template>
    <div class="addEdit-block" style="width: 100%">
      <el-form
        :style="{
          padding: '30px',
          boxShadow: '0 0px 0px rgba(64, 158, 255, .3)',
          borderRadius: '6px',
          flexWrap: 'wrap',
          background: '#fff',
          display: 'flex',
          width: '100%',
        }"
        class="add-update-preview"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="100px"
      >
        <template>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            v-if="type != 'info'"
            label="名称"
            prop="mingcheng"
          >
            <el-input
              v-model="ruleForm.mingcheng"
              placeholder="名称"
              clearable
              :readonly="ro.mingcheng"
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            v-else
            class="input"
            label="名称"
            prop="mingcheng"
          >
            <el-input
              v-model="ruleForm.mingcheng"
              placeholder="名称"
              readonly
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="date"
            v-if="type != 'info'"
            label="签退时间"
            prop="qiantuishijian"
          >
            <el-date-picker
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="ruleForm.qiantuishijian"
              type="datetime"
              :readonly="ro.qiantuishijian"
              placeholder="签退时间"
            ></el-date-picker>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            v-else-if="ruleForm.qiantuishijian"
            label="签退时间"
            prop="qiantuishijian"
          >
            <el-input
              v-model="ruleForm.qiantuishijian"
              placeholder="签退时间"
              readonly
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="upload"
            v-else-if="ruleForm.renlianshibie"
            label="人脸识别"
            prop="renlianshibie"
          >
            <img
              v-if="ruleForm.renlianshibie.substring(0, 4) == 'http'"
              class="upload-img"
              style="margin-right: 20px"
              v-bind:key="index"
              :src="ruleForm.renlianshibie.split(',')[0]"
              width="100"
              height="100"
            />
            <img
              v-else
              class="upload-img"
              style="margin-right: 20px"
              v-bind:key="index"
              v-for="(item, index) in ruleForm.renlianshibie.split(',')"
              :src="$base.url + item"
              width="100"
              height="100"
            />
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            v-if="type != 'info'"
            label="签退备注"
            prop="qiantuibeizhu"
          >
            <el-input
              v-model="ruleForm.qiantuibeizhu"
              placeholder="签到备注"
              clearable
              :readonly="ro.qiantuibeizhu"
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            v-else
            class="input"
            label="签退备注"
            prop="qiantuibeizhu"
          >
            <el-input
              v-model="ruleForm.qiantuibeizhu"
              placeholder="签退备注"
              readonly
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            v-if="type != 'info'"
            label="学号"
            prop="xuehao"
          >
            <el-input
              v-model="ruleForm.xuehao"
              placeholder="学号"
              clearable
              :readonly="ro.xuehao"
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            v-else
            class="input"
            label="学号"
            prop="xuehao"
          >
            <el-input
              v-model="ruleForm.xuehao"
              placeholder="学号"
              readonly
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            v-if="type != 'info'"
            label="手机"
            prop="shouji"
          >
            <el-input
              v-model="ruleForm.shouji"
              placeholder="手机"
              clearable
              :readonly="ro.shouji"
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            v-else
            class="input"
            label="手机"
            prop="shouji"
          >
            <el-input
              v-model="ruleForm.shouji"
              placeholder="手机"
              readonly
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            v-if="type != 'info'"
            label="班级"
            prop="banji"
          >
            <el-input
              v-model="ruleForm.banji"
              placeholder="班级"
              clearable
              :readonly="ro.banji"
            ></el-input>
          </el-form-item>
          <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            v-else
            class="input"
            label="班级"
            prop="banji"
          >
            <el-input
              v-model="ruleForm.banji"
              placeholder="班级"
              readonly
            ></el-input>
          </el-form-item>
        </template>
        <el-form-item
          :style="{ width: '100%', padding: '0', margin: '0' }"
          class="btn"
        >
          <el-button
            :style="{
              border: '0',
              cursor: 'pointer',
              padding: '0',
              margin: '0 20px 0 0',
              outline: 'none',
              color: 'rgba(255, 255, 255, 1)',
              borderRadius: '30px',
              background: '#93C7B3',
              width: '128px',
              lineHeight: '40px',
              fontSize: '14px',
              height: '40px',
            }"
            v-show="!faceMatchFlag"
            v-if="type != 'info'"
            type="primary"
            class="btn-success"
            @click="faceMatchHandle"
            >签退</el-button
          >
          <el-button
            :style="{
              border: '0',
              cursor: 'pointer',
              padding: '0',
              margin: '0 20px 0 0',
              outline: 'none',
              color: 'rgba(255, 255, 255, 1)',
              borderRadius: '30px',
              background: '#93C7B3',
              width: '128px',
              lineHeight: '40px',
              fontSize: '14px',
              height: '40px',
            }"
            v-show="faceMatchFlag"
            v-if="type != 'info'"
            type="primary"
            class="btn-success"
            @click="onSubmit"
            >提交</el-button
          >
          <el-button
            :style="{
              border: '0px solid rgba(64, 158, 255, 1)',
              cursor: 'pointer',
              padding: '0',
              margin: '0',
              outline: 'none',
              color: '#fff',
              borderRadius: '30px',
              background: '#93C7B3',
              width: '128px',
              lineHeight: '40px',
              fontSize: '14px',
              height: '40px',
            }"
            v-if="type != 'info'"
            class="btn-close"
            @click="back()"
            >取消</el-button
          >
          <el-button
            :style="{
              border: '0px solid rgba(64, 158, 255, 1)',
              cursor: 'pointer',
              padding: '0',
              margin: '0',
              outline: 'none',
              color: '#fff',
              borderRadius: '30px',
              background: '#93C7B3',
              width: '128px',
              lineHeight: '40px',
              fontSize: '14px',
              height: '40px',
            }"
            v-if="type == 'info'"
            class="btn-close"
            @click="back()"
            >返回</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
  // 数字，邮件，手机，url，身份证校验
  import {
    isNumber,
    isIntNumer,
    isEmail,
    isPhone,
    isMobile,
    isURL,
    checkIdCard,
  } from "@/utils/validate";
  export default {
    data() {
      let self = this;
      var validateIdCard = (rule, value, callback) => {
        if (!value) {
          callback();
        } else if (!checkIdCard(value)) {
          callback(new Error("请输入正确的身份证号码"));
        } else {
          callback();
        }
      };
      var validateUrl = (rule, value, callback) => {
        if (!value) {
          callback();
        } else if (!isURL(value)) {
          callback(new Error("请输入正确的URL地址"));
        } else {
          callback();
        }
      };
      var validateMobile = (rule, value, callback) => {
        if (!value) {
          callback();
        } else if (!isMobile(value)) {
          callback(new Error("请输入正确的手机号码"));
        } else {
          callback();
        }
      };
      var validatePhone = (rule, value, callback) => {
        if (!value) {
          callback();
        } else if (!isPhone(value)) {
          callback(new Error("请输入正确的电话号码"));
        } else {
          callback();
        }
      };
      var validateEmail = (rule, value, callback) => {
        if (!value) {
          callback();
        } else if (!isEmail(value)) {
          callback(new Error("请输入正确的邮箱地址"));
        } else {
          callback();
        }
      };
      var validateNumber = (rule, value, callback) => {
        if (!value) {
          callback();
        } else if (!isNumber(value)) {
          callback(new Error("请输入数字"));
        } else {
          callback();
        }
      };
      var validateIntNumber = (rule, value, callback) => {
        if (!value) {
          callback();
        } else if (!isIntNumer(value)) {
          callback(new Error("请输入整数"));
        } else {
          callback();
        }
      };
      return {
        id: "",
        type: "",
  
        userface: "",
        faceMatchFlag: false,
  
        ro: {
          mingcheng: false,
          qiantuishijian: false,
          qiantuibeizhu: false,
          zuowei: false,
          zixishiid: false,
          xuehao: false,
          shouji: false,
          banji: false,
          shhf: false,
        },
  
        ruleForm: {
          mingcheng: "",
          qiantuishijian: "",
          qiantuibeizhu: "",
          zuowei: "",
          zixishiid: "",
          xuehao: "",
          shouji: "",
          banji: "",
          shhf: "",
        },
  
        rules: {
          mingcheng: [],
          qiantuishijian: [],
          qiantuibeizhu: [],
          zuowei: [],
          zixishiid: [],
          xuehao: [],
          shouji: [{ validator: validateMobile, trigger: "blur" }],
          banji: [],
          shhf: [],
        },
      };
    },
    props: ["parent"],
    computed: {},
    created() {
      this.ruleForm.qiantuishijian = this.getCurDateTime();
    },
    methods: {
      // 下载
      download(file) {
        window.open(`${file}`);
      },
      // 初始化
      init(id, type) {
        if (id) {
          this.id = id;
          this.type = type;
        }
        if (this.type == "info" || this.type == "else") {
          this.info(id);
        } else if (this.type == "logistics") {
          this.logistics = false;
          this.info(id);
        } else if (this.type == "cross") {
          var obj = this.$storage.getObj("crossObj");
          for (var o in obj) {
            if (o == "mingcheng") {
              this.ruleForm.mingcheng = obj[o];
              this.ro.mingcheng = true;
              continue;
            }
            if (o == "qiantuishijian") {
              this.ruleForm.qiantuishijian = obj[o];
              this.ro.qiantuishijian = true;
              continue;
            }
            if (o == "renlianshibie") {
              this.ruleForm.renlianshibie = obj[o];
              this.ro.renlianshibie = true;
              continue;
            }
            if (o == "qiantuibeizhu") {
              this.ruleForm.qiantuibeizhu = obj[o];
              this.ro.qiantuibeizhu = true;
              continue;
            }
            if (o == "zixishiid") {
              this.ruleForm.zixishiid = obj[o];
              this.ro.zixishiid = true;
              continue;
            }
            if (o == "zuowei") {
              this.ruleForm.zuowei = obj[o];
              this.ro.zuowei = true;
              continue;
            }
            if (o == "xuehao") {
              this.ruleForm.xuehao = obj[o];
              this.ro.xuehao = true;
              continue;
            }
            if (o == "shouji") {
              this.ruleForm.shouji = obj[o];
              this.ro.shouji = true;
              continue;
            }
            if (o == "banji") {
              this.ruleForm.banji = obj[o];
              this.ro.banji = true;
              continue;
            }
          }
        }
  
        // 获取用户信息
        this.$http({
          url: `${this.$storage.get("sessionTable")}/session`,
          method: "get",
        }).then(({ data }) => {
          if (data && data.code === 0) {
            if (this.$storage.get("role") == "管理员") {
              this.faceMatchFlag = true;
            }
            this.userface = data.data.touxiang;
  
            var json = data.data;
            if (
              ((json.xuehao != "" && json.xuehao) || json.xuehao == 0) &&
              this.$storage.get("role") != "管理员"
            ) {
              this.ruleForm.xuehao = json.xuehao;
              this.ro.xuehao = true;
            }
            if (
              ((json.shouji != "" && json.shouji) || json.shouji == 0) &&
              this.$storage.get("role") != "管理员"
            ) {
              this.ruleForm.shouji = json.shouji;
              this.ro.shouji = true;
            }
            if (
              ((json.banji != "" && json.banji) || json.banji == 0) &&
              this.$storage.get("role") != "管理员"
            ) {
              this.ruleForm.banji = json.banji;
              this.ro.banji = true;
            }
          } else {
            this.$message.error(data.msg);
          }
        });
      },
      // 多级联动参数
  
      info(id) {
        this.$http({
          url: `qiantuixinxi/info/${id}`,
          method: "get",
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.ruleForm = data.data;
            //解决前台上传图片后台不显示的问题
            let reg = new RegExp("../../../upload", "g"); //g代表全部
          } else {
            this.$message.error(data.msg);
          }
        });
      },
  
      faceMatchHandle() {
        this.faceMatchFlag = true;
        this.$message({
          message: "签退成功",
          type: "success",
          duration: 1500,
        });
        
      },
  
      // 提交
      onSubmit() {
        var objcross = this.$storage.getObj("crossObj");
        console.log(objcross);
        //更新跨表属性
        var crossuserid;
        var crossrefid;
        var crossoptnum;
        if (this.type == "cross") {
          var statusColumnName = this.$storage.get("statusColumnName");
          var statusColumnValue = this.$storage.get("statusColumnValue");
          if (statusColumnName != "") {
            var obj = this.$storage.getObj("crossObj");
            if (statusColumnName && !statusColumnName.startsWith("[")) {
              for (var o in obj) {
                if (o == statusColumnName) {
                  obj[o] = statusColumnValue;
                }
              }
              var table = this.$storage.get("crossTable");
              console.log('ss');
              this.$http({
                url: `${table}/update`,
                method: "post",
                data: obj,
              }).then(({ data }) => {});
            } else {
              crossuserid = this.$storage.get("userid");
              crossrefid = obj["id"];
              crossoptnum = this.$storage.get("statusColumnName");
              crossoptnum = crossoptnum.replace(/\[/, "").replace(/\]/, "");
            }
          }
        }
        this.$refs["ruleForm"].validate((valid) => {
          if (valid) {
            if (crossrefid && crossuserid) {
              this.ruleForm.crossuserid = crossuserid;
              this.ruleForm.crossrefid = crossrefid;
              let params = {
                page: 1,
                limit: 10,
                crossuserid: this.ruleForm.crossuserid,
                crossrefid: this.ruleForm.crossrefid,
              };
              console.log('sss');
              this.$http({
                url: "qiantuixinxi/page",
                method: "get",
                params: params,
              }).then(({ data }) => {
                if (data && data.code === 0) {
                  if (data.data.total >= crossoptnum) {
                    this.$message.error(this.$storage.get("tips"));
                    return false;
                  } else {
                    console.log('ssss');
                    this.$http({
                      url: `qiantuixinxi/${
                        !this.ruleForm.id ? "save" : "update"
                      }`,
                      method: "post",
                      data: this.ruleForm,
                    }).then(({ data }) => {
                      if (data && data.code === 0) {
                        this.$message({
                          message: "操作成功",
                          type: "success",
                          duration: 1500,
                          onClose: () => {
                            this.parent.showFlag = true;
                            this.parent.addOrUpdateFlag = false;
                            this.parent.qiantuixinxiCrossAddOrUpdateFlag = false;
                            this.parent.search();
                            this.parent.contentStyleChange();
                          },
                        });
                      } else {
                        this.$message.error(data.msg);
                      }
                    });
                  }
                } else {
                }
              });
            } else {
              console.log('sssss');
              this.$http({
                url: `qiantuixinxi/${!this.ruleForm.id ? "save" : "update"}`,
                method: "post",
                data: this.ruleForm,
              }).then(({ data }) => {
                if (data && data.code === 0) {
                  this.$message({
                    message: "操作成功",
                    type: "success",
                    duration: 1500,
                    onClose: () => {
                      this.parent.showFlag = true;
                      this.parent.addOrUpdateFlag = false;
                      this.parent.qiantuixinxiCrossAddOrUpdateFlag = false;
                      this.parent.search();
                      this.parent.contentStyleChange();
                    },
                  });
                } else {
                  this.$message.error(data.msg);
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
        this.parent.showFlag = true;
        this.parent.addOrUpdateFlag = false;
        this.parent.qiantuixinxiCrossAddOrUpdateFlag = false;
        this.parent.contentStyleChange();
      },
      renlianshibieUploadChange(fileUrls) {
        this.ruleForm.renlianshibie = fileUrls;
      },
    },
  };
  </script>
  <style lang="scss" scoped>
  .amap-wrapper {
    width: 100%;
    height: 500px;
  }
  
  .search-box {
    position: absolute;
  }
  
  .el-date-editor.el-input {
    width: auto;
  }
  
  .add-update-preview .el-form-item >>> .el-form-item__label {
    padding: 0 10px 0 0;
    color: #666;
    font-weight: 500;
    width: 100px;
    font-size: 14px;
    line-height: 40px;
    text-align: right;
  }
  
  .add-update-preview .el-form-item >>> .el-form-item__content {
    margin-left: 100px;
  }
  
  .add-update-preview .el-input >>> .el-input__inner {
    border: 2px solid #797979;
    border-radius: 0;
    padding: 0 12px;
    box-shadow: 0 0 0px rgba(64, 158, 255, 0.5);
    outline: none;
    color: #000;
    width: 400px;
    font-size: 14px;
    height: 40px;
  }
  
  .add-update-preview .el-select >>> .el-input__inner {
    border: 2px solid #797979;
    border-radius: 0;
    padding: 0 10px;
    box-shadow: 0 0 0px rgba(64, 158, 255, 0.5);
    outline: none;
    color: #000;
    width: 200px;
    font-size: 14px;
    height: 40px;
  }
  
  .add-update-preview .el-date-editor >>> .el-input__inner {
    border: 2px solid #797979;
    border-radius: 0;
    padding: 0 10px 0 30px;
    box-shadow: 0 0 0px rgba(64, 158, 255, 0.5);
    outline: none;
    color: #000;
    width: 200px;
    font-size: 14px;
    height: 40px;
  }
  
  .add-update-preview >>> .el-upload--picture-card {
    background: transparent;
    border: 0;
    border-radius: 0;
    width: auto;
    height: auto;
    line-height: initial;
    vertical-align: middle;
  }
  
  .add-update-preview >>> .upload .upload-img {
    border: 1px dashed #797979;
    cursor: pointer;
    border-radius: 6px;
    color: #797979;
    width: 90px;
    font-size: 32px;
    line-height: 90px;
    text-align: center;
    height: 90px;
  }
  
  .add-update-preview >>> .el-upload-list .el-upload-list__item {
    border: 1px dashed #797979;
    cursor: pointer;
    border-radius: 6px;
    color: #797979;
    width: 90px;
    font-size: 32px;
    line-height: 90px;
    text-align: center;
    height: 90px;
  }
  
  .add-update-preview >>> .el-upload .el-icon-plus {
    border: 1px dashed #797979;
    cursor: pointer;
    border-radius: 6px;
    color: #797979;
    width: 90px;
    font-size: 32px;
    line-height: 90px;
    text-align: center;
    height: 90px;
  }
  
  .add-update-preview .el-textarea >>> .el-textarea__inner {
    border: 2px solid #797979;
    border-radius: 0;
    padding: 12px;
    box-shadow: 0 0 0px rgba(64, 158, 255, 0.5);
    outline: none;
    color: #000;
    width: 400px;
    font-size: 14px;
    height: 120px;
  }
  </style>
  