<template>
  <div>
    <div class="container" :style='{
			minHeight: "100vh",
			display: "flex",
			justifyContent: "center",
			alignItems: "center",
			background: "#f3f7fa",
			width: "100%"
		}'>
      <el-form class='rgs-form' v-if="pageFlag=='register'" :style='{
				padding: "20px",
				boxShadow: "0px 4px 10px 0px rgba(0,0,0,0.302)",
				borderRadius: "10px",
				background: "#fff",
				width: "400px",
				height: "auto"
			}' ref="registerForm" :model="registerForm" :rules="rules">
        <!-- 标题改为黑色+黑体加粗 -->
        <div v-if="true" :style='{
					width: "100%",
					margin: "0 0 10px 0",
					lineHeight: "44px",
					fontSize: "20px",
					color: "#000", /* 修改为黑色字体 */
					textAlign: "center",
					fontFamily: "SimHei,黑体",
					fontWeight: "bold"
				}'>智慧自习室预约系统 注册</div>

        <el-form-item :style='{
					width: "80%",
					padding: "0",
					margin: "0 auto 15px",
					height: "auto"
				}' v-if="tableName=='xuesheng'" prop="xuehao">
          <el-input v-model="registerForm.xuehao" placeholder="请输入学号" />
        </el-form-item>

        <el-form-item :style='{
					width: "80%",
					padding: "0",
					margin: "0 auto 15px",
					height: "auto"
				}' v-if="tableName=='xuesheng'" prop="mima">
          <el-input v-model="registerForm.mima" type="password" placeholder="请输入密码" />
        </el-form-item>

        <el-form-item :style='{
					width: "80%",
					padding: "0",
					margin: "0 auto 15px",
					height: "auto"
				}' v-if="tableName=='xuesheng'">
          <el-input v-model="registerForm.mima2" type="password" placeholder="请再次输入密码" />
        </el-form-item>

        <el-form-item :style='{
					width: "80%",
					padding: "0",
					margin: "0 auto 15px",
					height: "auto"
				}' v-if="tableName=='xuesheng'" prop="xingming">
          <el-input v-model="registerForm.xingming" placeholder="请输入姓名" />
        </el-form-item>

        <el-form-item :style='{
					width: "80%",
					padding: "0",
					margin: "0 auto 15px",
					height: "auto"
				}' v-if="tableName=='xuesheng'" prop="xingbie">
          <el-select v-model="registerForm.xingbie" placeholder="请选择性别">
            <el-option
                v-for="(item,index) in xueshengxingbieOptions"
                :key="index"
                :label="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item :style='{
					width: "80%",
					padding: "0",
					margin: "0 auto 15px",
					height: "auto"
				}' v-if="tableName=='xuesheng'" prop="touxiang">
          <file-upload
              tip="点击上传头像"
              action="file/upload"
              :limit="1"
              :multiple="true"
              :fileUrls="registerForm.touxiang?registerForm.touxiang:''"
              @change="xueshengtouxiangUploadChange"
          ></file-upload>
        </el-form-item>

        <el-form-item :style='{
					width: "80%",
					padding: "0",
					margin: "0 auto 15px",
					height: "auto"
				}' v-if="tableName=='xuesheng'" prop="shouji">
          <el-input v-model="registerForm.shouji" placeholder="请输入手机" />
        </el-form-item>

        <el-form-item :style='{
					width: "80%",
					padding: "0",
					margin: "0 auto 15px",
					height: "auto"
				}' v-if="tableName=='xuesheng'" prop="banji">
          <el-input v-model="registerForm.banji" placeholder="请输入班级" />
        </el-form-item>

        <!-- 注册按钮（与登录按钮同色） -->
        <el-button :style='{
					border: "0",
					cursor: "pointer",
					padding: "0 10px",
					margin: "20px auto 5px",
					outline: "none",
					color: "#fff",
					borderRadius: "8px",
					background: "#78ABC3",
					display: "block",
					width: "80%",
					fontSize: "16px",
					height: "44px"
				}' type="primary" @click="submitForm('registerForm')">注册</el-button>

        <!-- 重置按钮（与登录按钮同色） -->
        <el-button :style='{
					border: "0",
					cursor: "pointer",
					padding: "0 10px",
					margin: "0 auto 5px",
					outline: "none",
					color: "#fff",
					borderRadius: "8px",
					background: "#78ABC3",
					display: "block",
					width: "80%",
					fontSize: "16px",
					height: "44px"
				}' @click="resetForm('registerForm')">重置</el-button>

        <router-link :style='{
					cursor: "pointer",
					padding: "0 10%",
					color: "rgba(159, 159, 159, 1)",
					textAlign: "center",
					display: "inline-block",
					width: "100%",
					lineHeight: "1",
					fontSize: "12px",
					textDecoration: "none",
					marginTop: "10px"
				}' to="/login">已有账户登录</router-link>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pageFlag : '',
      tableName: '',
      registerForm: {
        xingbie: '',
      },
      rules: {},
      xueshengxingbieOptions: [],
    }
  },
  mounted() {
  },
  created() {
    this.pageFlag = this.$route.query.pageFlag;
    this.tableName = this.$route.query.role;
    if ('xuesheng' == this.tableName) {
      this.rules.xuehao = [{ required: true, message: '请输入学号', trigger: 'blur' }];
    }
    if ('xuesheng' == this.tableName) {
      this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }];
    }
    if ('xuesheng' == this.tableName) {
      this.rules.xingming = [{ required: true, message: '请输入姓名', trigger: 'blur' }];
    }
    this.xueshengxingbieOptions = "男,女".split(',');
    if ('xuesheng' == this.tableName) {
      this.rules.shouji = [{ required: true, validator: this.$validate.isMobile, trigger: 'blur' }];
    }
  },
  methods: {
    getUUID () {
      return new Date().getTime();
    },
    xueshengtouxiangUploadChange(fileUrls) {
      this.registerForm.touxiang = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const url = `${this.tableName}/register`;
          if(`xuesheng` === this.tableName && this.registerForm.mima !== this.registerForm.mima2) {
            this.$message.error(`两次密码输入不一致`);
            return;
          }
          this.$http.post(url, this.registerForm).then(res => {
            if (res.data.code === 0) {
              this.$message({
                message: '注册成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.$router.push('/login');
                }
              });
            } else {
              this.$message.error(res.data.msg);
            }
          });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
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