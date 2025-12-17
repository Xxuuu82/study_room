<template>
  <div>
    <div class="container" :style="{
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        background: '#f3f7fa',
        width: '100%'
      }">
      <el-form class="rgs-form" v-if="pageFlag === 'register'"
               :style="{
                 padding: '20px',
                 boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.302)',
                 borderRadius: '10px',
                 background: '#fff',
                 width: '420px',
                 height: 'auto'
               }"
               ref="registerForm"
               :model="registerForm"
               :rules="rules"
               label-position="top"
      >
        <!-- 标题 -->
        <div :style="{
            width: '100%',
            margin: '0 0 16px 0',
            lineHeight: '44px',
            fontSize: '20px',
            color: '#000',
            textAlign: 'center',
            fontWeight: '700'
          }">智慧自习室预约系统 注册</div>

        <!-- 学号 -->
        <el-form-item prop="xuehao" label="学号">
          <el-input v-model="registerForm.xuehao" placeholder="请输入学号"></el-input>
        </el-form-item>

        <!-- 密码 -->
        <el-form-item prop="mima" label="密码">
          <el-input v-model="registerForm.mima" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <!-- 确认密码 -->
        <el-form-item prop="mima2" label="确认密码">
          <el-input v-model="registerForm.mima2" type="password" placeholder="请再次输入密码"></el-input>
        </el-form-item>

        <!-- 姓名 -->
        <el-form-item prop="xingming" label="姓名">
          <el-input v-model="registerForm.xingming" placeholder="请输入姓名"></el-input>
        </el-form-item>

        <!-- 性别 -->
        <el-form-item prop="xingbie" label="性别">
          <el-select v-model="registerForm.xingbie" placeholder="请选择性别">
            <el-option v-for="(item, index) in xueshengxingbieOptions" :key="index" :label="item" :value="item" />
          </el-select>
        </el-form-item>

        <!-- 头像上传（复用项目已有 file-upload 组件） -->
        <el-form-item prop="touxiang" label="头像">
          <file-upload
              tip="点击上传头像"
              action="file/upload"
              :limit="1"
              :multiple="false"
              :fileUrls="registerForm.touxiang ? registerForm.touxiang : ''"
              @change="xueshengtouxiangUploadChange"
          ></file-upload>
        </el-form-item>

        <!-- 手机 -->
        <el-form-item prop="shouji" label="手机">
          <el-input v-model="registerForm.shouji" placeholder="请输入手机"></el-input>
        </el-form-item>

        <!-- 班级 -->
        <el-form-item prop="banji" label="班级">
          <el-input v-model="registerForm.banji" placeholder="请输入班级"></el-input>
        </el-form-item>

        <!-- 按钮组 -->
        <div style="width:100%; display:flex; flex-direction:column; align-items:center; gap:12px; margin-top:12px;">
          <el-button type="primary" :style="submitBtnStyle" @click="submitForm('registerForm')">注册</el-button>
          <el-button type="default" :style="resetBtnStyle" @click="resetForm('registerForm')">重置</el-button>
          <router-link :to="{ path: '/login' }" style="color:#999; margin-top:8px; display:block;">已有账户登录</router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pageFlag: '',
      tableName: '',
      registerForm: {
        xuehao: '',
        mima: '',
        mima2: '',
        xingming: '',
        xingbie: '',
        touxiang: '',
        shouji: '',
        banji: ''
      },
      xueshengxingbieOptions: ['男', '女'],
      rules: {
        xuehao: [
          { required: true, message: '请输入学号', trigger: 'blur' }
        ],
        mima: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
        ],
        mima2: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: (rule, value, callback) => {
              if (value !== this.registerForm.mima) {
                callback(new Error('两次密码输入不一致'));
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ],
        xingming: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        shouji: [
          { required: true, validator: (rule, value, callback) => {
              if (!value) return callback(new Error('请输入手机号'));
              const mobileReg = /^1[3-9]\d{9}$/;
              if (!mobileReg.test(value)) return callback(new Error('请输入正确的手机号码'));
              callback();
            }, trigger: 'blur' }
        ]
      },
      submitBtnStyle: {
        border: '0',
        cursor: 'pointer',
        padding: '0 10px',
        outline: 'none',
        color: '#fff',
        borderRadius: '8px',
        background: '#78ABC3',
        display: 'block',
        width: '80%',
        fontSize: '16px',
        height: '44px'
      },
      resetBtnStyle: {
        border: '0',
        cursor: 'pointer',
        padding: '0 10px',
        outline: 'none',
        color: '#fff',
        borderRadius: '8px',
        background: '#B0C9CF',
        display: 'block',
        width: '80%',
        fontSize: '16px',
        height: '44px'
      }
    };
  },
  created() {
    this.pageFlag = this.$route.query.pageFlag || 'register';
    this.tableName = this.$route.query.role || 'xuesheng';
    // keep rules in data; already defined
  },
  methods: {
    getUUID() {
      return new Date().getTime();
    },
    xueshengtouxiangUploadChange(fileUrls) {
      // file-upload 组件可能返回完整 URL，我们保存数据库相对路径（去掉 baseUrl）
      if (!fileUrls) {
        this.registerForm.touxiang = '';
        return;
      }
      this.registerForm.touxiang = fileUrls.replace(new RegExp(this.$config.baseUrl, 'g'), '');
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // 两次密码一致校验（表单验证中也有，但此处再做一次保险）
          if (this.registerForm.mima !== this.registerForm.mima2) {
            this.$message.error('两次密码输入不一致');
            return;
          }

          const url = `${this.tableName}/register`;

          // 关键：使用 emulateJSON:true 强制以 application/x-www-form-urlencoded 发送，避免服务器 415
          this.$http.post(url, this.registerForm, { emulateJSON: true })
              .then(res => {
                if (res.data && res.data.code === 0) {
                  this.$message({
                    message: '注册成功',
                    type: 'success',
                    duration: 1500,
                    onClose: () => {
                      this.$router.push('/login');
                    }
                  });
                } else {
                  // 后端可能返回 401/415/其他，统一展示后端 msg
                  this.$message.error(res.data?.msg || '注册失败');
                }
              })
              .catch(err => {
                // 如果后端返回 415 等错误，给出清晰提示
                console.error('注册请求失败：', err);
                const status = (err && err.status) || (err && err.response && err.response.status) || null;
                if (status === 415) {
                  this.$message.error('请求格式不被后端接受 (415)。已尝试以表单格式提交，请检查后端register接口是否允许 POST 表单或是否需要登录。');
                } else if (status === 401) {
                  this.$message.error('需要先登录或权限校验未通过（401）');
                } else {
                  this.$message.error('网络请求失败，请检查后端服务或网络连接');
                }
              });
        } else {
          // 表单验证未通过，会显示对应校验信息
          return false;
        }
      });
    },
    resetForm(formName) {
      if (this.$refs[formName]) this.$refs[formName].resetFields();
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.container {
  .el-date-editor.el-input {
    width: 100%;
  }

  .rgs-form .el-input >>> .el-input__inner {
    border: 0;
    border-radius: 8px;
    padding: 0 10px;
    outline: none;
    color: #000;
    background: #F9F9F9;
    width: 100%;
    font-size: 14px;
    height: 44px;
  }

  .rgs-form .el-select >>> .el-input__inner {
    border: 0;
    border-radius: 8px;
    padding: 0 10px;
    outline: none;
    color: #000;
    background: #F9F9F9;
    width: 100%;
    font-size: 14px;
    height: 44px;
  }

  .rgs-form .el-date-editor >>> .el-input__inner {
    border: 0;
    border-radius: 8px;
    padding: 0 10px 0 30px;
    outline: none;
    color: #000;
    background: #F9F9F9;
    width: 100%;
    font-size: 14px;
    height: 44px;
  }

  .rgs-form >>> .el-upload--picture-card {
    background: transparent;
    border: 0;
    border-radius: 0;
    width: auto;
    height: auto;
    line-height: initial;
    vertical-align: middle;
  }

  /* required 标识：Element UI 在必填时会给 el-form-item 加 is-required 类，下面让标签显示红星 */
  .el-form-item.is-required .el-form-item__label::after {
    content: " *";
    color: #f56c6c;
    margin-left: 4px;
  }

  .rgs-form >>> .upload .upload-img {
    border: 1px dashed #888;
    cursor: pointer;
    border-radius: 8px;
    color: #888;
    width: 100px;
    font-size: 32px;
    line-height: 100px;
    text-align: center;
    height: 100px;
  }

  .rgs-form >>> .el-upload-list .el-upload-list__item {
    border: 1px dashed #888;
    cursor: pointer;
    border-radius: 8px;
    color: #888;
    width: 100px;
    font-size: 32px;
    line-height: 100px;
    text-align: center;
    height: 100px;
  }

  .rgs-form >>> .el-upload .el-icon-plus {
    border: 1px dashed #888;
    cursor: pointer;
    border-radius: 8px;
    color: #888;
    width: 100px;
    font-size: 32px;
    line-height: 100px;
    text-align: center;
    height: 100px;
  }
}
</style>