<template>
  <div>
    <div
        class="container"
        :style="{
        minHeight: '100vh',
        alignItems: 'center',
        background: '#f5f7fa',
        display: 'flex',
        width: '100%',
        justifyContent: 'center',
        padding: '20px'
      }"
    >
      <el-form
          ref="loginForm"
          :model="loginForm"
          :style="{
          padding: '40px 20px 20px',
          boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.302)',
          borderRadius: '10px',
          background: '#fff',
          width: '400px',
          height: 'auto',
          margin: '0 auto'
        }"
          :rules="rules"
      >
        <!-- 标题：黑体加粗 -->
        <div
            v-if="true"
            :style="{
            width: '100%',
            margin: '0 0 20px 0',
            lineHeight: '44px',
            fontSize: '20px',
            color: '#000',
            textAlign: 'center',
            fontFamily: 'SimHei, sans-serif',
            fontWeight: 'bold'
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
          <input
              :style="{
              border: '1px solid #ddd',
              padding: '0 10px',
              color: '#000',
              background: '#F9F9F9',
              width: '100%',
              fontSize: '14px',
              height: '44px',
              borderRadius: '4px',
              outline: 'none'
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
          <input
              :style="{
              border: '1px solid #ddd',
              padding: '0 10px',
              color: '#000',
              background: '#F9F9F9',
              width: '100%',
              fontSize: '14px',
              height: '44px',
              borderRadius: '4px',
              outline: 'none'
            }"
              v-model="loginForm.password"
              placeholder="请输入密码"
              type="password"
          />
        </el-form-item>
        <el-form-item
            v-if="loginType == 1"
            class="list-item"
            :style="{ width: '80%', margin: '0 auto 10px' }"
            prop="code"
        >
          <div
              :style="{ display: 'flex', width: '100%', alignItems: 'center' }"
          >
            <input
                :style="{
                border: '1px solid #ddd',
                padding: '0 10px',
                color: '#000',
                background: '#F9F9F9',
                width: '60%',
                fontSize: '14px',
                height: '44px',
                borderRadius: '4px',
                outline: 'none'
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
                background: '#f9f9f9',
                borderRadius: '4px',
                border: '1px solid #ddd'
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
        <el-form-item :style="{ width: '80%', margin: '20px auto' }">
          <!-- 登录按钮：改为图片中的浅蓝色 -->
          <el-button
              v-if="loginType == 1"
              :style="{
              border: '0',
              cursor: 'pointer',
              padding: '0 24px',
              margin: '0 5px',
              outline: 'none',
              color: '#fff',
              borderRadius: '8px',
              background: '#78ABC3', /* 图片中的浅蓝色 */
              width: '100%',
              fontSize: '16px',
              height: '48px',
              fontWeight: '500'
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
        <!-- 注册学生按钮：美化 -->
        <div
            :style="{
            width: '100%',
            margin: '20px auto',
            display: 'flex',
            justifyContent: 'center'
          }"
        >
          <router-link
              :style="{
              cursor: 'pointer',
              color: '#78ABC3',
              textAlign: 'center',
              background: 'rgba(120, 171, 195, 0.1)',
              padding: '12px 24px',
              borderRadius: '8px',
              fontSize: '14px',
              textDecoration: 'none',
              fontWeight: '500',
              transition: 'all 0.2s'
            }"
              :to="{
              path: '/register',
              query: { role: 'xuesheng', pageFlag: 'register' },
            }"
              @mouseenter="style={background: 'rgba(120, 171, 195, 0.2)'}"
              @mouseleave="style={background: 'rgba(120, 171, 195, 0.1)'}"
          >注册学生</router-link
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
      baseUrl: this.$config.baseUrl || '/study_room/',
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
          hasFrontLogin: "是", // 关键修改：管理员显示在登录角色列表
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
        tableName: "xuesheng", // 默认选择学生
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
      generatedCode: ""
    };
  },
  created() {
    this.randomString();
    for (let item in this.roleMenus) {
      if (this.roleMenus[item].hasFrontLogin == "是") {
        this.roles.push(this.roleMenus[item]);
      }
    }
    // 设置默认角色
    if (this.roles.length > 0) {
      this.loginForm.tableName = this.roles[0].tableName;
      this.role = this.roles[0].roleName;
    }

    // 调试日志
    console.log('登录页面初始化完成');
    console.log('baseUrl配置:', this.$config?.baseUrl);
    console.log('$http对象是否存在:', !!this.$http);
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
      this.generatedCode = codeStr.toLowerCase();
    },
    getCurrentRow(row) {
      this.role = row.roleName;
    },
    submitForm(formName) {
      console.log('开始登录提交...');

      // 验证码校验
      const inputCode = this.loginForm.code.toLowerCase();
      if (inputCode !== this.generatedCode) {
        this.$message.error('验证码输入错误');
        this.randomString();
        return;
      }

      // 角色选择校验
      if (this.roles.length != 1) {
        if (!this.role) {
          this.$message.error("请选择登录用户类型");
          return false;
        }
      } else {
        this.role = this.roles[0].roleName;
        this.loginForm.tableName = this.roles[0].tableName;
      }

      // 表单验证
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('表单验证通过，开始发送登录请求');
          console.log('登录参数:', this.loginForm);

          // 关键修复：移除有问题的代码行
          // 注释掉或删除导致错误的代码：
          // console.log('完整请求地址:', this.$http.defaults.baseURL ? this.$http.defaults.baseURL + requestUrl : requestUrl);

          // 构建安全的请求URL
          const baseUrl = this.$config?.baseUrl || '/study_room/';
          const requestUrl = `${baseUrl}${this.loginForm.tableName}/login`;

          console.log('登录请求URL:', requestUrl);
          console.log('请求方法: POST');

          // 发送登录请求
          this.$http
              .post(requestUrl, null, {
                params: {
                  username: this.loginForm.username,
                  password: this.loginForm.password,
                  tableName: this.loginForm.tableName
                },
              })
              .then((res) => {
                console.log('登录响应:', res.data);
                if (res.data && res.data.code === 0) {
                  // 存储登录信息
                  localStorage.setItem("Token", res.data.token);
                  localStorage.setItem("UserTableName", this.loginForm.tableName);
                  localStorage.setItem("username", this.loginForm.username);
                  localStorage.setItem("adminName", this.loginForm.username);
                  localStorage.setItem("sessionTable", this.loginForm.tableName);
                  localStorage.setItem("role", this.role);

                  this.$message({
                    message: "登录成功",
                    type: "success",
                    duration: 1500,
                  });

                  // 登录成功后跳转
                  setTimeout(() => {
                    this.$router.push("/index/center");
                  }, 1000);
                } else {
                  this.$message.error(res.data.msg || "登录失败");
                }
              })
              .catch((error) => {
                console.error('登录请求失败:', error);

                // 更详细的错误提示
                if (error.response) {
                  this.$message.error(`服务器错误: ${error.response.status}`);
                } else if (error.request) {
                  this.$message.error("无法连接到服务器，请检查后端是否运行");
                } else {
                  this.$message.error("请求配置错误");
                }
              });
        } else {
          console.log('表单验证失败');
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

  .el-form-item {
    & >>> .el-form-item__content {
      width: 100%;
    }
  }

  .list-item >>> .el-input .el-input__inner {
    border: 1px solid #ddd;
    padding: 0 10px;
    color: #000;
    background: #f9f9f9;
    width: 100%;
    font-size: 14px;
    height: 44px;
    border-radius: 4px;
  }

  .list-code >>> .el-input .el-input__inner {
    border: 1px solid #ddd;
    padding: 0 10px;
    color: #000;
    background: #f9f9f9;
    display: inline-block;
    vertical-align: middle;
    width: calc(100% - 80px);
    font-size: 14px;
    height: 44px;
    border-radius: 4px;
  }

  .list-type >>> .el-radio__input .el-radio__inner {
    background: rgba(53, 53, 53, 0);
    border-color: #666666;
  }
  .list-type >>> .el-radio__input.is-checked .el-radio__inner {
    background: #78ABC3;
    border-color: #78ABC3;
  }
  .list-type >>> .el-radio__label {
    color: #666666;
    font-size: 14px;
  }
  .list-type >>> .el-radio__input.is-checked + .el-radio__label {
    color: #78ABC3;
    font-size: 14px;
  }
}
</style>