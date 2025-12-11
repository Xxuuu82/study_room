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
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          class="input"
          v-else-if="ruleForm.yuyuedanhao"
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
          class="upload"
          v-if="type != 'info' && !ro.tupian"
          label="图片"
          prop="tupian"
        >
          <file-upload
            tip="点击上传图片"
            action="file/upload"
            :limit="3"
            :multiple="true"
            :fileUrls="ruleForm.tupian ? ruleForm.tupian : ''"
            @change="tupianUploadChange"
          ></file-upload>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          class="upload"
          v-else-if="ruleForm.tupian"
          label="图片"
          prop="tupian"
        >
          <img
            v-if="ruleForm.tupian.substring(0, 4) == 'http'"
            class="upload-img"
            style="margin-right: 20px"
            v-bind:key="index"
            :src="ruleForm.tupian.split(',')[0]"
            width="100"
            height="100"
          />
          <img
            v-else
            class="upload-img"
            style="margin-right: 20px"
            v-bind:key="index"
            v-for="(item, index) in ruleForm.tupian.split(',')"
            :src="$base.url + item"
            width="100"
            height="100"
          />
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          class="input"
          v-if="type != 'info'"
          label="座位"
          prop="zuowei"
        >
          <el-input
            v-model="ruleForm.zuowei"
            placeholder="座位"
            clearable
            :readonly="ro.zuowei"
          ></el-input>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          v-else
          class="input"
          label="座位"
          prop="zuowei"
        >
          <el-input
            v-model="ruleForm.zuowei"
            placeholder="座位"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          class="select"
          v-if="type != 'info'"
          label="签到状态"
          prop="qiandaozhuangtai"
        >
          <el-select
            :disabled="ro.qiandaozhuangtai"
            v-model="ruleForm.qiandaozhuangtai"
            placeholder="请选择签到状态"
          >
            <el-option
              v-for="(item, index) in qiandaozhuangtaiOptions"
              v-bind:key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          v-else
          class="input"
          label="签到状态"
          prop="qiandaozhuangtai"
        >
          <el-input
            v-model="ruleForm.qiandaozhuangtai"
            placeholder="签到状态"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          class="select"
          v-if="type != 'info'"
          label="签退状态"
          prop="qiantuizhuangtai"
        >
          <el-select
            :disabled="ro.qiantuizhuangtai"
            v-model="ruleForm.qiantuizhuangtai"
            placeholder="请选择签退状态"
          >
            <el-option
              v-for="(item, index) in qiantuizhuangtaiOptions"
              v-bind:key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          v-else
          class="input"
          label="签退状态"
          prop="qiantuizhuangtai"
        >
          <el-input
            v-model="ruleForm.qiantuizhuangtai"
            placeholder="签退状态"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          class="date"
          v-if="type != 'info'"
          label="预约时间"
          prop="yuyueshijian"
        >
          <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="ruleForm.yuyueshijian"
            type="datetime"
            :readonly="ro.yuyueshijian"
            placeholder="预约时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          class="input"
          v-else-if="ruleForm.yuyueshijian"
          label="预约时间"
          prop="yuyueshijian"
        >
          <el-input
            v-model="ruleForm.yuyueshijian"
            placeholder="预约时间"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          class="input"
          v-if="type != 'info'"
          label="备注"
          prop="beizhu"
        >
          <el-input
            v-model="ruleForm.beizhu"
            placeholder="备注"
            clearable
            :readonly="ro.beizhu"
          ></el-input>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          v-else
          class="input"
          label="备注"
          prop="beizhu"
        >
          <el-input
            v-model="ruleForm.beizhu"
            placeholder="备注"
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
          label="姓名"
          prop="xingming"
        >
          <el-input
            v-model="ruleForm.xingming"
            placeholder="姓名"
            clearable
            :readonly="ro.xingming"
          ></el-input>
        </el-form-item>
        <el-form-item
          :style="{ width: '50%', margin: '0 0 20px 0' }"
          v-else
          class="input"
          label="姓名"
          prop="xingming"
        >
          <el-input
            v-model="ruleForm.xingming"
            placeholder="姓名"
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

      ro: {
        yuyuedanhao: false,
        mingcheng: false,
        tupian: false,
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

      ruleForm: {
        yuyuedanhao: this.getUUID(),
        mingcheng: "",
        tupian: "",
        zuowei: "1",
        qiandaozhuangtai: "未签到",
        qiantuizhuangtai: "未签退",
        yuyueshijian: "",
        beizhu: "",
        xuehao: "",
        xingming: "",
        shouji: "",
        shhf: "",
      },

      qiandaozhuangtaiOptions: [],
      qiantuizhuangtaiOptions: [],
      rules: {
        yuyuedanhao: [],
        mingcheng: [],
        tupian: [],
        zuowei: [{ validator: validateIntNumber, trigger: "blur" }],
        qiandaozhuangtai: [],
        qiantuizhuangtai: [],
        yuyueshijian: [
          { required: true, message: "预约时间不能为空", trigger: "blur" },
        ],
        beizhu: [],
        xuehao: [],
        xingming: [],
        shouji: [{ validator: validateMobile, trigger: "blur" }],
        sfsh: [],
        shhf: [],
      },
    };
  },
  props: ["parent"],
  computed: {},
  created() {},
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
          if (o == "tupian") {
            this.ruleForm.tupian = obj[o];
            this.ro.tupian = true;
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

        // this.ruleForm.zuowei='1'

        this.ruleForm.qiandaozhuangtai = "未签到";
        this.ruleForm.qiantuizhuangtai = "未签到";
      }

      // 获取用户信息
      this.$http({
        url: `${this.$storage.get("sessionTable")}/session`,
        method: "get",
      }).then(({ data }) => {
        if (data && data.code === 0) {
          var json = data.data;
          if (this.$storage.get("role") != "管理员") {
            this.ro.zuowei = true;
          }
          if (this.$storage.get("role") != "管理员") {
            this.ro.qiandaozhuangtai = true;
			this.ro.qiantuizhuangtai = true;
          }
          if (
            ((json.xuehao != "" && json.xuehao) || json.xuehao == 0) &&
            this.$storage.get("role") != "管理员"
          ) {
            this.ruleForm.xuehao = json.xuehao;
            this.ro.xuehao = true;
          }
          if (
            ((json.xingming != "" && json.xingming) || json.xingming == 0) &&
            this.$storage.get("role") != "管理员"
          ) {
            this.ruleForm.xingming = json.xingming;
            this.ro.xingming = true;
          }
          if (
            ((json.shouji != "" && json.shouji) || json.shouji == 0) &&
            this.$storage.get("role") != "管理员"
          ) {
            this.ruleForm.shouji = json.shouji;
            this.ro.shouji = true;
          }
        } else {
          this.$message.error(data.msg);
        }
      });

      this.qiandaozhuangtaiOptions = "已签到,未签到".split(",");
	  this.qiantuizhuangtaiOptions = "已签退,未签退".split(",");
    },
    // 多级联动参数

    info(id) {
      this.$http({
        url: `yuyuexinxi/info/${id}`,
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

    // 提交
    onSubmit() {
      if (this.ruleForm.tupian != null) {
        this.ruleForm.tupian = this.ruleForm.tupian.replace(
          new RegExp(this.$base.url, "g"),
          ""
        );
      }

      var objcross = this.$storage.getObj("crossObj");
      var table = this.$storage.getObj("crossTable");
      if (objcross != null) {
        objcross.zuowei = objcross.zuowei - this.ruleForm.zuowei;
        if (objcross.zuowei < 0) {
          this.$message.error("座位不足");
          return;
        }
      }

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
            this.$http({
              url: `${table}/update`,
              method: "post",
              data: obj,
            }).then(({ data }) => {});
            this.$http({
              url: `${table}/update`,
              method: "post",
              data: objcross,
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
            this.$http({
              url: "yuyuexinxi/page",
              method: "get",
              params: params,
            }).then(({ data }) => {
              if (data && data.code === 0) {
                if (data.data.total >= crossoptnum) {
                  this.$message.error(this.$storage.get("tips"));
                  return false;
                } else {
                  this.$http({
                    url: `yuyuexinxi/${!this.ruleForm.id ? "save" : "update"}`,
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
                          this.parent.yuyuexinxiCrossAddOrUpdateFlag = false;
                          this.parent.search();
                          this.parent.contentStyleChange();
                        },
                      });
                      this.$http({
                        url: `${table}/update`,
                        method: "post",
                        data: objcross,
                      }).then(({ data }) => {});
                    } else {
                      this.$message.error(data.msg);
                    }
                  });
                }
              } else {
              }
            });
          } else {
            this.$http({
              url: `yuyuexinxi/${!this.ruleForm.id ? "save" : "update"}`,
              method: "post",
              data: this.ruleForm,
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$http({
                  url: `${table}/update`,
                  method: "post",
                  data: objcross,
                }).then(({ data }) => {});
                this.$message({
                  message: "操作成功",
                  type: "success",
                  duration: 1500,
                  onClose: () => {
                    this.parent.showFlag = true;
                    this.parent.addOrUpdateFlag = false;
                    this.parent.yuyuexinxiCrossAddOrUpdateFlag = false;
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
      this.parent.yuyuexinxiCrossAddOrUpdateFlag = false;
      this.parent.contentStyleChange();
    },
    tupianUploadChange(fileUrls) {
      this.ruleForm.tupian = fileUrls;
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
