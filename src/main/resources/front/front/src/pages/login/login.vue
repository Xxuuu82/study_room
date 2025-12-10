<template>
  <div>
    <div
      class="container"
      :style="{
        minHeight: '100vh',
        alignItems: 'center',
        background:
          'url(login_bg.jpg)',
        display: 'flex',
        width: '100%',
        backgroundSize: '100% 100%',
        backgroundPosition: 'center center',
        backgroundRepeat: 'no-repeat',
        justifyContent: 'center',
      }"
    >
      <el-form
        ref="loginForm"
        :model="loginForm"
        :style="{
          padding: '40px 20px 20px',
          boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.302)',
          margin: '0',
          borderRadius: '10px',
          left: '15%',
          background: '#fff',
          width: '400px',
          position: 'absolute',
          height: 'auto',
        }"
        :rules="rules"
      >
        <div
          v-if="false"
          :style="{
            margin: '0 0 10px 0',
            color: 'rgba(64, 158, 255, 1)',
            textAlign: 'center',
            width: '100%',
            lineHeight: '44px',
            fontSize: '20px',
            textShadow: '4px 4px 2px rgba(64, 158, 255, .5)',
          }"
        >
          USER / LOGIN
        </div>
        <div
          v-if="true"
          :style="{
            width: '100%',
            margin: '0 0 10px 0',
            lineHeight: '44px',
            fontSize: '20px',
            color: '#174AD7',
            textAlign: 'center',
          }"
        >
          智慧自习室预约系统 登录
        </div>
        <el-form-item
          v-if="loginType == 1"
          class="list-item"
          :style="{ width: '80%', margin: '0 auto 10px' }"
          prop="username"
        >
          <div
            v-if="false"
            :style="{
              width: '64px',
              lineHeight: '44px',
              fontSize: '14px',
              color: 'rgba(64, 158, 255, 1)',
            }"
          >
            账号：
          </div>
          <input
            :style="{
              border: '0px solid rgba(64, 158, 255, 1)',
              padding: '0 10px',
              color: '#000',
              outlineOffset: '4px',
              background: '#F9F9F9',
              width: '100%',
              fontSize: '14px',
              height: '44px',
            }"
            v-model="loginForm.username"
            placeholder="请输入账户"
          />
        </el-form-item>
        <el-form-item
          v-if="loginType == 1"
          class="list-item"
          :style="{ width: '80%', margin: '0 auto 10px' }"
          prop="password"
        >
          <div
            v-if="false"
            :style="{
              width: '64px',
              lineHeight: '44px',
              fontSize: '14px',
              color: 'rgba(64, 158, 255, 1)',
            }"
          >
            密码：
          </div>
          <input
            :style="{
              border: '0px solid rgba(64, 158, 255, 1)',
              padding: '0 10px',
              color: '#000',
              outlineOffset: '4px',
              background: '#F9F9F9',
              width: '100%',
              fontSize: '14px',
              height: '44px',
            }"
            v-model="loginForm.password"
            placeholder="请输入密码"
            type="password"
          />
        </el-form-item>
        <el-form-item
          v-if="roles.length > 1"
          class="list-type"
          :style="{ width: '90%', textAlign: 'center', margin: '20px auto' }"
          prop="role"
        >
          <el-radio
            v-model="loginForm.tableName"
            :label="item.tableName"
            v-for="(item, index) in roles"
            :key="index"
            @change.native="getCurrentRow(item)"
            >{{ item.roleName }}</el-radio
          >
        </el-form-item>
        <el-form-item :style="{ width: '100%', margin: '20px auto' }">
          <el-button
            v-if="loginType == 1"
            :style="{
              border: '0',
              cursor: 'pointer',
              padding: '0 24px',
              margin: '0 5px',
              outline: 'none',
              color: '#fff',
              borderRadius: '4px',
              background: '#1746D4',
              width: '100%',
              fontSize: '14px',
              height: '44px',
            }"
            @click="submitForm('loginForm')"
            >登录</el-button
          >
          <el-button
            v-if="loginType == 1"
            :style="{
              border: '0',
              cursor: 'pointer',
              padding: '0 24px',
              margin: '0 5px',
              outline: 'none',
              color: '#fff',
              borderRadius: '4px',
              background: 'rgba(64, 158, 255, 1)',
              display: 'none',
              width: 'auto',
              fontSize: '14px',
              height: '44px',
            }"
            @click="resetForm('loginForm')"
            >重置</el-button
          >
          <el-upload
            v-if="loginType == 2"
            :action="baseUrl + 'file/upload'"
            :show-file-list="false"
            :on-success="faceLogin"
          >
            <el-button
              :style="{
                border: '0',
                cursor: 'pointer',
                padding: '0 24px',
                margin: '0 5px',
                outline: 'none',
                color: '#fff',
                borderRadius: '4px',
                background: '#1746D4',
                width: '100%',
                fontSize: '14px',
                height: '44px',
              }"
              >人脸识别登录</el-button
            >
          </el-upload>
        </el-form-item>
        <el-form-item
          v-if="loginType == 1"
          class="list-item"
          :style="{ width: '80%', margin: '0 auto 10px' }"
          prop="code"
        >
          <div v-if="false">验证码：</div>
          <div
            :style="{ display: 'flex', width: '100%', alignItems: 'center' }"
          >
            <input
              :style="{
                border: '0px solid #ddd',
                padding: '0 10px',
                color: '#000',
                outlineOffset: '4px',
                background: '#F9F9F9',
                width: '60%',
                fontSize: '14px',
                height: '44px',
              }"
              v-model="loginForm.code"
              placeholder="请输入验证码"
            />
            <div
              @click="randomString"
              :style="{
                width: '35%',
                cursor: 'pointer',
                marginLeft: '5%',
                display: 'flex',
                justifyContent: 'space-around',
                alignItems: 'center',
                height: '44px',
              }"
            >
              <span
                v-for="(code, index) in codes"
                :key="index"
                :style="{
                  color: code.color,
                  transform: code.rotate,
                  fontSize: code.size,
                  fontWeight: 'bold',
                }"
              >
                {{ code.num }}
              </span>
            </div>
          </div>
        </el-form-item>
        <div
          :style="{
            width: '100%',
            margin: '20px auto',
            flexWrap: 'wrap',
            justifyContent: 'space-around',
            display: 'flex',
          }"
        >
          <router-link
            :style="{
              cursor: 'pointer',
              margin: '5px 0',
              color: '#000',
              textAlign: 'center',
              background: '#EFECEC',
              width: '32%',
              fontSize: '14px',
              textDecoration: 'none',
              order: '2',
            }"
            :to="{
              path: '/register',
              query: { role: item.tableName, pageFlag: 'register' },
            }"
            v-if="item.hasFrontRegister == '是'"
            v-for="(item, index) in roles"
            :key="index"
            >注册{{ item.roleName.replace("注册", "") }}</router-link
          >
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  //数据集合
  data() {
    return {
      baseUrl: this.$config.baseUrl,
      loginType: 1,
      roleMenus: [
        {
          backMenu: [
            {
              child: [
                {
                  appFrontIcon: "cuIcon-discover",
                  buttons: ["新增", "查看", "修改", "删除"],
                  menu: "学生",
                  menuJump: "列表",
                  tableName: "xuesheng",
                },
              ],
              menu: "学生管理",
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-goodsnew",
                  buttons: ["新增", "查看", "修改", "删除"],
                  menu: "自习室",
                  menuJump: "列表",
                  tableName: "zixishi",
                },
              ],
              menu: "自习室管理",
            },
            
            {
              child: [
                {
                  appFrontIcon: "cuIcon-present",
                  buttons: ["查看", "修改", "删除", "审核"],
                  menu: "预约信息",
                  menuJump: "列表",
                  tableName: "yuyuexinxi",
                },
              ],
              menu: "预约信息管理",
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-send",
                  buttons: ["查看", "修改", "删除", "审核"],
                  menu: "取消预约",
                  menuJump: "列表",
                  tableName: "quxiaoyuyue",
                },
              ],
              menu: "取消预约管理",
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-qrcode",
                  buttons: ["查看", "修改", "删除", "审核"],
                  menu: "签到信息",
                  menuJump: "列表",
                  tableName: "qiandaoxinxi",
                },
              ],
              menu: "签到信息管理",
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-list",
                  buttons: ["查看", "修改"],
                  menu: "轮播图管理",
                  tableName: "config",
                },
                {
                  appFrontIcon: "cuIcon-news",
                  buttons: ["新增", "查看", "修改", "删除"],
                  menu: "公告资讯",
                  tableName: "news",
                },
              ],
              menu: "系统管理",
            },
          ],
          frontMenu: [
            {
              child: [
                {
                  appFrontIcon: "cuIcon-discover",
                  buttons: ["查看", "在线预约"],
                  menu: "自习室列表",
                  menuJump: "列表",
                  tableName: "zixishi",
                },
              ],
              menu: "自习室模块",
            },
            
          ],
          hasBackLogin: "是",
          hasBackRegister: "否",
          hasFrontLogin: "否",
          hasFrontRegister: "否",
          roleName: "管理员",
          tableName: "users",
        },
        {
          backMenu: [
            {
              child: [
                {
                  appFrontIcon: "cuIcon-present",
                  buttons: ["查看", "取消预约", "签到"],
                  menu: "预约信息",
                  menuJump: "列表",
                  tableName: "yuyuexinxi",
                },
              ],
              menu: "预约信息管理",
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-send",
                  buttons: ["查看"],
                  menu: "取消预约",
                  menuJump: "列表",
                  tableName: "quxiaoyuyue",
                },
              ],
              menu: "取消预约管理",
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-qrcode",
                  buttons: ["查看"],
                  menu: "签到信息",
                  menuJump: "列表",
                  tableName: "qiandaoxinxi",
                },
              ],
              menu: "签到信息管理",
            },
          ],
          frontMenu: [
            {
              child: [
                {
                  appFrontIcon: "cuIcon-discover",
                  buttons: ["查看", "在线预约"],
                  menu: "自习室列表",
                  menuJump: "列表",
                  tableName: "zixishi",
                },
              ],
              menu: "自习室模块",
            },
            
          ],
          hasBackLogin: "是",
          hasBackRegister: "否",
          hasFrontLogin: "是",
          hasFrontRegister: "是",
          roleName: "学生",
          tableName: "xuesheng",
        },
      ],
      loginForm: {
        username: "",
        password: "",
        tableName: "",
        code: "",
      },
      role: "",
      roles: [],
      rules: {
        username: [{ required: true, message: "请输入账户", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
      codes: [
        {
          num: 1,
          color: "#000",
          rotate: "10deg",
          size: "16px",
        },
        {
          num: 2,
          color: "#000",
          rotate: "10deg",
          size: "16px",
        },
        {
          num: 3,
          color: "#000",
          rotate: "10deg",
          size: "16px",
        },
        {
          num: 4,
          color: "#000",
          rotate: "10deg",
          size: "16px",
        },
      ],
    };
  },
  created() {
	this.randomString();
    for (let item in this.roleMenus) {
      if (this.roleMenus[item].hasFrontLogin == "是") {
        this.roles.push(this.roleMenus[item]);
      }
    }
  },
  mounted() {},
  //方法集合
  methods: {
    randomString() {
    const len = 4;
    const chars = [
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
      'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
      'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
      'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
      'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
      '3', '4', '5', '6', '7', '8', '9'
    ];
    const colors = ['#FF0000', '#00FF00', '#0000FF', '#FFA500', '#800080'];
    const sizes = ['18px', '20px', '22px'];
    
    let newCodes = [];
    let codeStr = '';
    
    for (let i = 0; i < len; i++) {
      const charIndex = Math.floor(Math.random() * chars.length);
      const colorIndex = Math.floor(Math.random() * colors.length);
      const sizeIndex = Math.floor(Math.random() * sizes.length);
      const rotate = `${Math.floor(Math.random() * 30 - 15)}deg`;
      
      newCodes.push({
        num: chars[charIndex],
        color: colors[colorIndex],
        rotate: rotate,
        size: sizes[sizeIndex]
      });
      
      codeStr += chars[charIndex];
    }
    
    this.codes = newCodes;
    this.generatedCode = codeStr.toLowerCase(); // 存储生成的验证码（小写）
  },
    getCurrentRow(row) {
      this.role = row.roleName;
    },
    submitForm(formName) {
		const inputCode = this.loginForm.code.toLowerCase();
  if (inputCode !== this.generatedCode) {
    this.$message.error('验证码输入错误');
    this.randomString(); // 重新生成验证码
    return;
  }
      if (this.roles.length != 1) {
        if (!this.role) {
          this.$message.error("请选择登录用户类型");
          return false;
        }
      } else {
        this.role = this.roles[0].roleName;
        this.loginForm.tableName = this.roles[0].tableName;
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http
            .get(`${this.loginForm.tableName}/login`, {
              params: this.loginForm,
            })
            .then((res) => {
              if (res.data.code === 0) {
                localStorage.setItem("Token", res.data.token);
                localStorage.setItem("UserTableName", this.loginForm.tableName);
                localStorage.setItem("username", this.loginForm.username);
                localStorage.setItem("adminName", this.loginForm.username);
                localStorage.setItem("sessionTable", this.loginForm.tableName);
                localStorage.setItem("role", this.role);
                localStorage.setItem(
                  "keyPath",
                  this.$config.indexNav.length + 2
                );
                this.$router.push("/index/center");
                this.$message({
                  message: "登录成功",
                  type: "success",
                  duration: 1500,
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
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.container {
  position: relative;
  background: url(http://codegen.caihongy.cn/20221017/0d970ac476e8407a92e0eee7a8e88625.png);

  .el-form-item {
    & >>> .el-form-item__content {
      width: 100%;
    }
  }

  .list-item >>> .el-input .el-input__inner {
    border: 0px solid rgba(64, 158, 255, 1);
    padding: 0 10px;
    color: #000;
    background: #f9f9f9;
    width: 100%;
    font-size: 14px;
    outline-offset: 4px;
    height: 44px;
  }

  .list-code >>> .el-input .el-input__inner {
    border: 0px solid rgba(64, 158, 255, 1);
    padding: 0 10px;
    outline: none;
    color: #000;
    background: #f9f9f9;
    display: inline-block;
    vertical-align: middle;
    width: calc(100% - 80px);
    font-size: 14px;
    height: 44px;
  }

  .list-type >>> .el-radio__input .el-radio__inner {
    background: rgba(53, 53, 53, 0);
    border-color: #666666;
  }
  .list-type >>> .el-radio__input.is-checked .el-radio__inner {
    background: #174ad7;
    border-color: #174ad7;
  }
  .list-type >>> .el-radio__label {
    color: #666666;
    font-size: 14px;
  }
  .list-type >>> .el-radio__input.is-checked + .el-radio__label {
    color: #174ad7;
    font-size: 14px;
  }
}
</style>
