<template>
  <div class="password-wrap">
    <el-form
      :style='{"padding":"40px 80px","boxShadow":"0 0px 0px rgba(64, 158, 255, .3)","borderRadius":"12px","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%","flexDirection":"column","gap":"15px"}'
      class="add-update-preview"
      ref="ruleForm"
      :rules="rules"
      :model="ruleForm"
      label-width="100px"
    >
      <!-- 标题区域 -->
      <div class="form-title" :style="{marginBottom: '10px', fontSize: '16px', fontWeight: '600', color: '#333', marginLeft: '30px'}">
        修改密码
      </div>

      <!-- 原密码 -->
      <el-form-item :style='{"width":"100%","margin":"0 0 15px 30px"}' label="原密码" prop="password">
        <el-input
          v-model="ruleForm.password"
          show-password
          placeholder="请输入当前登录密码"
          :style="{width: '800px', borderRadius: '6px'}"
        >
          <template #prefix>
            <i class="el-icon-lock" style="color: #909399"></i>
          </template>
        </el-input>
      </el-form-item>

      <!-- 新密码（增加强度提示） -->
      <el-form-item :style='{"width":"100%","margin":"0 0 15px 30px"}' label="新密码" prop="newpassword">
        <el-input
          v-model="ruleForm.newpassword"
          show-password
          placeholder="6-16位，建议包含字母+数字组合"
          :style="{width: '800px', borderRadius: '6px'}"
          @input="checkPwdStrength"
        >
          <template #prefix>
            <i class="el-icon-key" style="color: #909399"></i>
          </template>
        </el-input>
        <!-- 密码强度提示 -->
        <div class="pwd-strength" :style="{marginTop: '8px', display: ruleForm.newpassword ? 'flex' : 'none', alignItems: 'center'}">
          <span
            class="strength-item"
            :style="{
              display: 'inline-block',
              width: '60px',
              height: '6px',
              marginRight: '4px',
              borderRadius: '3px',
              backgroundColor: strength < 1 ? '#eee' : strength < 2 ? '#ff4d4f' : strength === 2 ? '#faad14' : '#52c41a'
            }"
            v-for="i in 3"
            :key="i"
          ></span>
          <span :style="{marginLeft: '10px', fontSize: '12px', color: '#999'}">
            {{ strengthText }}
          </span>
        </div>
      </el-form-item>

      <!-- 确认密码 -->
      <el-form-item :style='{"width":"100%","margin":"0 0 20px 30px"}' label="确认密码" prop="repassword">
        <el-input
          v-model="ruleForm.repassword"
          show-password
          placeholder="请再次输入新密码"
          :style="{width: '800px', borderRadius: '6px'}"
        >
          <template #prefix>
            <i class="el-icon-check" style="color: #909399"></i>
          </template>
        </el-input>
      </el-form-item>

      <!-- 安全提示 -->
      <div class="safe-tips" :style="{width: '100%', margin: '0 0 25px 130px', fontSize: '12px', color: '#666', lineHeight: '1.8'}">
        <p style="margin: 0 0 8px 0;"><i class="el-icon-info" style="color: #409eff; margin-right: 5px;"></i>安全提示：</p>
        <ul style="margin: 0; padding-left: 20px; line-height: 1.8;">
          <li>密码长度建议6-16位，避免使用纯数字/纯字母</li>
          <li>请勿使用与用户名、手机号等相关的简单密码</li>
          <li>修改密码后下次登录需使用新密码</li>
        </ul>
      </div>

      <!-- 操作按钮（优化位置和间距） -->
      <el-form-item :style='{"width":"100%","padding":"0","margin":"10px 0 0 30px"}'>
        <div :style="{marginLeft: '100px', display: 'flex', alignItems: 'center', gap: '20px', marginTop: '10px'}">
          <el-button
            :style='{"border":"0","cursor":"pointer","padding":"0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"30px","background":"#93C7B3","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px","transition":"background 0.3s"}'
            type="primary"
            @click="onUpdateHandler"
            @mouseenter="(e) => e.target.style.background = '#7bb8a2'"
            @mouseleave="(e) => e.target.style.background = '#93C7B3'"
          >
            确 定
          </el-button>
          <el-button
            :style='{"border":"1px solid #dcdfe6","cursor":"pointer","padding":"0","outline":"none","color":"#666","borderRadius":"30px","background":"#fff","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px","transition":"border-color 0.3s"}'
            @click="resetForm"
            @mouseenter="(e) => e.target.style.borderColor = '#93C7B3'"
            @mouseleave="(e) => e.target.style.borderColor = '#dcdfe6'"
          >
            重置
          </el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    // 新增：新密码格式校验
    const validateNewPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error("新密码不能为空"));
      } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/.test(value)) {
        callback(new Error("新密码需6-16位，包含字母和数字"));
      } else {
        callback();
      }
    };
    // 新增：确认密码校验
    const validateRePwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error("确认密码不能为空"));
      } else if (value !== this.ruleForm.newpassword) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };

    return {
      dialogVisible: false,
      ruleForm: {},
      user: {},
      strength: 0, // 密码强度
      strengthText: "", // 强度文本
      rules: {
        password: [
          { required: true, message: "原密码不能为空", trigger: "blur" }
        ],
        newpassword: [
          { required: true, validator: validateNewPwd, trigger: "blur" }
        ],
        repassword: [
          { required: true, validator: validateRePwd, trigger: "blur" }
        ]
      }
    };
  },
  mounted() {
    this.$http({
      url: `${this.$storage.get("sessionTable")}/session`,
      method: "get"
    }).then(({ data }) => {
      if (data && data.code === 0) {
        this.user = data.data;
      } else {
        this.$message.error(data.msg);
      }
    });
  },
  methods: {
    onLogout() {
      this.$storage.remove("Token");
      this.$router.replace({ name: "login" });
    },
    // 新增：重置表单
    resetForm() {
      this.$refs.ruleForm.resetFields();
      this.strength = 0;
      this.strengthText = "";
    },
    // 新增：检查密码强度
    checkPwdStrength() {
      const pwd = this.ruleForm.newpassword;
      let strength = 0;
      if (pwd.length >= 8) strength++; // 长度≥8加1分
      if (/[A-Za-z]/.test(pwd) && /\d/.test(pwd)) strength++; // 字母+数字加1分
      if (/[!@#$%^&*]/.test(pwd)) strength++; // 特殊字符加1分
      this.strength = strength;
      // 强度文本
      if (strength < 2) {
        this.strengthText = "弱（建议增加长度/特殊字符）";
      } else if (strength === 2) {
        this.strengthText = "中（可添加特殊字符提升强度）";
      } else {
        this.strengthText = "强";
      }
    },
    // 修改密码
    onUpdateHandler() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          // 优化：简化原密码获取逻辑
          const originPwd = this.user.mima || this.user.password || "";
          if (!originPwd) {
            this.$message.error("未获取到原密码，请刷新页面重试");
            return;
          }
          if (this.ruleForm.password !== originPwd) {
            this.$message.error("原密码错误");
            return;
          }
          if (this.ruleForm.newpassword !== this.ruleForm.repassword) {
            this.$message.error("两次输入的密码不一致");
            return;
          }
          this.user.password = this.ruleForm.newpassword;
          this.user.mima = this.ruleForm.newpassword;
          this.$http({
            url: `${this.$storage.get("sessionTable")}/update`,
            method: "post",
            data: this.user
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "修改密码成功,下次登录系统生效",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.resetForm(); // 成功后重置表单
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          }).catch(err => {
            this.$message.error("网络异常，请稍后重试");
          });
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
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

// 优化输入框样式（统一圆角，保持原有交互）
.add-update-preview .el-input >>> .el-input__inner {
  border: 2px solid #797979;
  border-radius: 6px !important;
  padding: 0 12px;
  box-shadow: 0 0 0px rgba(64, 158, 255, .5);
  outline: none;
  color: #000;
  width: 400px;
  font-size: 14px;
  height: 40px;
  transition: border-color 0.3s;
  &:focus {
    border-color: #93C7B3;
  }
}

.add-update-preview .el-select >>> .el-input__inner {
  border: 2px solid #797979;
  border-radius: 6px !important;
  padding: 0 10px;
  box-shadow: 0 0 0px rgba(64, 158, 255, .5);
  outline: none;
  color: #000;
  width: 200px;
  font-size: 14px;
  height: 40px;
}

.add-update-preview .el-date-editor >>> .el-input__inner {
  border: 2px solid #797979;
  border-radius: 6px !important;
  padding: 0 10px 0 30px;
  box-shadow: 0 0 0px rgba(64, 158, 255, .5);
  outline: none;
  color: #000;
  width: 200px;
  font-size: 14px;
  height: 40px;
}

.add-update-preview >>> .el-upload--picture-card {
  background: transparent;
  border: 0;
  border-radius: 6px;
  width: auto;
  height: auto;
  line-height: initial;
  vertical-align: middle;
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
  border-radius: 6px !important;
  padding: 12px;
  box-shadow: 0 0 0px rgba(64, 158, 255, .5);
  outline: none;
  color: #000;
  width: 400px;
  font-size: 14px;
  height: 120px;
}

// 整体样式统一
.password-wrap {
  padding: 20px 0;
}

.pwd-strength {
  margin-bottom: 5px;
}

.safe-tips li {
  margin-bottom: 4px;
}
</style>