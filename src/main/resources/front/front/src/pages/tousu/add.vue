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
    <!-- 新增面包屑导航 -->
    <div :style="{
      marginBottom: '20px',
      paddingBottom: '10px',
      borderBottom: '1px solid #e4e7ed'
    }">
      <el-breadcrumb :separator="'/'" :style='{"fontSize":"14px","lineHeight":"1","color":"#333"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item @click="$router.push('/index/tousu')">投诉中心</el-breadcrumb-item>
        <el-breadcrumb-item>{{ type === 'edit' ? '编辑投诉' : '新增投诉' }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-form
        class="add-update-preview"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
    >
      <!-- 投诉单号 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="投诉单号"
          prop="tousudanhao"
      >
        <el-input
            v-model="ruleForm.tousudanhao"
            placeholder="投诉单号"
            readonly
        ></el-input>
      </el-form-item>

      <!-- 关联预约单号 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="关联预约单号"
          prop="relatedOrder"
      >
        <el-input
            v-model="ruleForm.relatedOrder"
            placeholder="请输入关联的预约单号（选填）"
            clearable
        ></el-input>
      </el-form-item>

      <!-- 投诉类型（多选框） -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="投诉类型"
          prop="tousuleixing"
      >
        <el-checkbox-group
            v-model="ruleForm.tousuleixing"
            style="width: 100%;"
        >
          <el-checkbox
              v-for="(item, index) in tousuleixingOptions"
              :key="index"
              :label="item"
              :style="{
                display: 'inline-block',
                margin: '0 15px 10px 0',
                fontSize: '14px',
                color: '#666'
              }"
          >
            {{ item }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <!-- 投诉人学号 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="投诉人学号"
          prop="xuehao"
      >
        <el-input
            v-model="ruleForm.xuehao"
            placeholder="投诉人学号"
            clearable
            :readonly="ro.xuehao"
        ></el-input>
      </el-form-item>

      <!-- 投诉人姓名 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="投诉人姓名"
          prop="xingming"
      >
        <el-input
            v-model="ruleForm.xingming"
            placeholder="投诉人姓名"
            clearable
            :readonly="ro.xingming"
        ></el-input>
      </el-form-item>

      <!-- 投诉人手机 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="投诉人手机"
          prop="shouji"
      >
        <el-input
            v-model="ruleForm.shouji"
            placeholder="投诉人手机"
            clearable
            :readonly="ro.shouji"
        ></el-input>
      </el-form-item>

      <!-- 被投诉人学号（非必填） -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="被投诉人学号"
          prop="beitousuXuehao"
      >
        <el-input
            v-model="ruleForm.beitousuXuehao"
            placeholder="请输入被投诉人学号（选填）"
            clearable
        ></el-input>
      </el-form-item>

      <!-- 投诉时间 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="投诉时间"
          prop="tousuTime"
      >
        <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="ruleForm.tousuTime"
            type="datetime"
            placeholder="选择投诉时间"
            style="width: 100%;"
            default-time="12:00:00"
        >
        </el-date-picker>
      </el-form-item>

      <!-- 投诉原因 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="投诉原因"
          prop="tousuReason"
      >
        <el-input
            v-model="ruleForm.tousuReason"
            type="textarea"
            placeholder="请详细描述投诉原因，至少10个字"
            clearable
            :rows="5"
            style="width: 100%;"
        ></el-input>
      </el-form-item>

      <!-- 投诉诉求 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="投诉诉求"
          prop="tousuSuqiu"
      >
        <el-input
            v-model="ruleForm.tousuSuqiu"
            type="textarea"
            placeholder="请说明您的投诉诉求（如：要求道歉、整改等）"
            clearable
            :rows="3"
            style="width: 100%;"
        ></el-input>
      </el-form-item>

      <!-- 提交/返回按钮 -->
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
            transition: 'all 0.2s ease'
          }"
            type="primary"
            @click="onSubmit"
        >{{ type === 'edit' ? '更新投诉' : '提交投诉' }}</el-button
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
            transition: 'all 0.2s ease'
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
      // 只读字段配置
      ro: {
        tousudanhao: false,
        relatedOrder: false,
        tousuleixing: false,
        xuehao: false,
        xingming: false,
        shouji: false,
        beitousuXuehao: false,
        tousuTime: false,
        tousuReason: false,
        tousuSuqiu: false,
      },
      type: "",
      userTableName: localStorage.getItem("UserTableName"),
      // 表单数据
      ruleForm: {
        tousudanhao: this.getUUID(), // 自动生成投诉单号
        relatedOrder: "", // 关联预约单号
        tousuleixing: [], // 投诉类型（改为数组，支持多选）
        xuehao: "", // 投诉人学号
        xingming: "", // 投诉人姓名
        shouji: "", // 投诉人手机
        beitousuXuehao: "", // 被投诉人学号（非必填）
        tousuTime: "", // 投诉时间
        tousuReason: "", // 投诉原因
        tousuSuqiu: "", // 投诉诉求
        chulizhuangtai: "处理中", // 默认处理状态
        feedback: "" // 处理反馈（初始为空）
      },
      // 投诉类型选项（更新为指定的4种类型）
      tousuleixingOptions: ["教室乱吃乱喝", "未签到", "大声喧哗", "随地大小便"],
      // 表单校验规则
      rules: {
        tousudanhao: [],
        relatedOrder: [],
        // 投诉类型必填（至少选择一项）
        tousuleixing: [
          {
            required: true,
            message: "请至少选择一项投诉类型",
            trigger: "change",
            validator: (rule, value, callback) => {
              if (value.length === 0) {
                callback(new Error('请至少选择一项投诉类型'));
              } else {
                callback();
              }
            }
          }
        ],
        // 投诉人学号必填
        xuehao: [
          { required: true, message: "投诉人学号不能为空", trigger: "blur" }
        ],
        // 投诉人姓名必填
        xingming: [
          { required: true, message: "投诉人姓名不能为空", trigger: "blur" }
        ],
        // 手机号校验（内置实现，避免依赖外部）
        shouji: [{
          validator: this.validateMobile,
          trigger: "blur"
        }],
        // 被投诉人学号非必填
        beitousuXuehao: [],
        // 投诉时间必填
        tousuTime: [
          { required: true, message: "请选择投诉时间", trigger: "change" },
        ],
        // 投诉原因必填
        tousuReason: [
          { required: true, message: "请详细描述投诉原因", trigger: "blur" },
          { min: 10, message: "投诉原因至少填写10个字", trigger: "blur" }
        ],
        // 投诉诉求必填
        tousuSuqiu: [
          { required: true, message: "请说明您的投诉诉求", trigger: "blur" }
        ]
      },
    };
  },
  computed: {},
  created() {
    let type = this.$route.query.type ? this.$route.query.type : "";
    this.init(type);
    this.baseUrl = this.$config.baseUrl;
    // 默认填充当前时间为投诉时间
    this.ruleForm.tousuTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
  },
  methods: {
    getMakeZero(s) {
      return s < 10 ? "0" + s : s;
    },
    // 手机号校验方法（内置实现）
    validateMobile(rule, value, callback) {
      if (!value) {
        callback(new Error('请输入手机号'));
      } else {
        const reg = /^1[3-9]\d{9}$/;
        if (!reg.test(value)) {
          callback(new Error('请输入正确的手机号'));
        } else {
          callback();
        }
      }
    },
    // 初始化方法
    init(type) {
      this.type = type;
      // 如果是编辑模式，从路由参数获取数据（扩展用）
      if (type === "edit") {
        // 可扩展：从localStorage或路由参数获取编辑数据
        let editObj = JSON.parse(localStorage.getItem("editComplaintObj") || "{}");
        if (Object.keys(editObj).length > 0) {
          for (let o in editObj) {
            // 兼容编辑模式下投诉类型的格式（字符串转数组）
            if (o === "tousuleixing" && typeof editObj[o] === 'string') {
              this.ruleForm[o] = editObj[o].split(',');
            } else {
              this.ruleForm[o] = editObj[o];
            }
            // 编辑模式下投诉单号设为只读
            if (o === "tousudanhao") {
              this.ro.tousudanhao = true;
            }
          }
        } else {
          this.$message.warning('未获取到编辑数据，将创建新投诉');
        }
      }

      // 初始化用户信息（自动填充当前登录用户信息）
      // 模拟用户信息（接口未实现时兜底）
      if (!this.userTableName) {
        this.ruleForm.xuehao = "20230001";
        this.ruleForm.xingming = "张三";
        this.ruleForm.shouji = "13800138000";
        this.ro.xuehao = true;
        this.ro.xingming = true;
        this.ro.shouji = true;
        return;
      }

      this.$http
          .get(this.userTableName + "/session", { emulateJSON: true })
          .then((res) => {
            if (res.data.code === 0) {
              let userInfo = res.data.data || {};
              // 自动填充用户信息
              if (userInfo.xuehao) {
                this.ruleForm.xuehao = userInfo.xuehao;
                this.ro.xuehao = true; // 学号设为只读
              }
              if (userInfo.xingming) {
                this.ruleForm.xingming = userInfo.xingming;
                this.ro.xingming = true; // 姓名设为只读
              }
              if (userInfo.shouji) {
                this.ruleForm.shouji = userInfo.shouji;
                this.ro.shouji = true; // 手机号设为只读
              }
            }
          })
          .catch(err => {
            console.error("获取用户信息失败：", err);
            this.$message.warning("无法自动获取用户信息，请手动填写");
          });
    },
    // 提交/更新投诉
    onSubmit() {
      // 表单校验
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          // 构造提交数据（将多选的投诉类型转为逗号分隔的字符串）
          const submitData = {
            ...this.ruleForm,
            tousuleixing: this.ruleForm.tousuleixing.join(','), // 数组转字符串
            // 兼容后端字段格式
            tousu_time: this.ruleForm.tousuTime,
            tousu_reason: this.ruleForm.tousuReason,
            tousu_suqiu: this.ruleForm.tousuSuqiu,
            beitousu_xuehao: this.ruleForm.beitousuXuehao
          };

          console.log("提交的投诉数据：", submitData);

          // 模拟提交请求（实际项目替换为真实接口）
          setTimeout(() => {
            this.$message({
              message: this.type === 'edit' ? "投诉更新成功" : "投诉提交成功，等待管理员处理",
              type: "success",
              duration: 2000,
              onClose: () => {
                this.$router.push({
                  path: '/index/tousu',
                  replace: true // 替换历史记录，避免回退循环
                });
              },
            });
          }, 1000);

          // 真实接口提交代码（注释掉，供实际使用）
          /*
          const apiUrl = this.type === 'edit' ? "tousuinfo/update" : "tousuinfo/submit";
          this.$http.post(apiUrl, submitData, {
            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            },
            emulateJSON: false
          })
              .then((res) => {
                console.log("投诉提交结果：", res.data);
                if (res.data.code === 0 || res.data.msg.includes("成功")) {
                  this.$message({
                    message: this.type === 'edit' ? "投诉更新成功" : "投诉提交成功，等待管理员处理",
                    type: "success",
                    duration: 2000,
                    onClose: () => {
                      this.$router.push('/index/tousu'); // 跳转到投诉列表
                    },
                  });
                } else {
                  this.$message.error(res.data.msg || (this.type === 'edit' ? "投诉更新失败" : "投诉提交失败"));
                }
              })
              .catch(err => {
                console.error("投诉提交失败：", err);
                this.$message.error("网络异常：" + (err.message || (this.type === 'edit' ? "投诉更新失败" : "投诉提交失败")));
              });
          */
        }
      });
    },
    // 生成唯一投诉单号（时间戳）
    getUUID() {
      return "TS" + new Date().getTime();
    },
    // 返回投诉列表（优化跳转逻辑）
    back() {
      this.$router.push({
        path: '/index/tousu',
        replace: true
      });
    },
  },
};
</script>

<style lang="scss" scoped>
// 修复样式语法错误：移除非法标识，统一使用 ::v-deep 深度选择器
// 移动端适配
@media (max-width: 768px) {
  .add-update-preview {
    ::v-deep .el-form-item__label {
      width: 100% !important;
      text-align: left !important;
      line-height: 24px !important;
      padding: 0 0 8px 0 !important;
    }

    ::v-deep .el-form-item__content {
      margin-left: 0 !important;
    }
  }
}

// 日期选择器宽度
::v-deep .el-date-editor.el-input {
  width: 100%;
}

// 表单标签样式
.add-update-preview {
  ::v-deep .el-form-item__label {
    padding: 0 10px 0 0;
    color: #666;
    font-weight: 500;
    width: 120px;
    font-size: 14px;
    line-height: 40px;
    text-align: right;
  }

  ::v-deep .el-form-item__content {
    margin-left: 120px;
  }

  // 输入框样式
  ::v-deep .el-input__inner,
  ::v-deep .el-select__inner,
  ::v-deep .el-date-editor .el-input__inner {
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

  // 文本域样式
  ::v-deep .el-textarea__inner {
    border: 2px solid #2e61e1;
    border-radius: 4px;
    padding: 12px;
    outline: none;
    color: #000;
    background: #bfd2fc;
    width: 100%;
    font-size: 14px;
    min-height: 100px;
    box-sizing: border-box;
  }

  // 复选框样式优化
  ::v-deep .el-checkbox-group {
    .el-checkbox {
      margin-bottom: 8px;
    }

    .el-checkbox__label {
      font-size: 14px;
      color: #666;
    }

    .el-checkbox__inner {
      border-radius: 4px;
      width: 16px;
      height: 16px;
    }
  }
}

// 按钮hover效果
::v-deep .el-button {
  &:hover {
    opacity: 0.9;
    transform: translateY(-1px); // 增加上移动效
  }
}

// 面包屑样式优化
::v-deep .el-breadcrumb {
  .el-breadcrumb__separator {
    margin: 0 8px;
    color: #666;
  }

  .el-breadcrumb__inner {
    color: #333;
    cursor: pointer;
    &:hover {
      color: #2e61e1;
    }
  }

  .el-breadcrumb__inner a {
    color: #2e61e1;
    text-decoration: none;
  }
}
</style>