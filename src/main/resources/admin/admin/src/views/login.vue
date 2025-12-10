<template>
  <div>
    <div
      class="container"
      :style="{
        minHeight: '100vh',
        backgroundAttachment: 'fixed',
        alignItems: 'center',
        background:
          'url(login_bg.jpg)',
        display: 'flex',
        width: '100%',
        backgroundSize: 'cover',
        backgroundPosition: 'center center',
        backgroundRepeat: 'no-repeat',
        justifyContent: 'center',
      }"
    >
      <el-form
        :style="{
          padding: '40px 20px 80px',
          boxShadow: '0px 4px 10px 0px #78ABC3',
          margin: '0',
          outline: '4px solid #78ABC3',
          borderRadius: '0',
          outlineOffset: '30px',
          background: '#fff',
          width: '500px',
          height: 'auto',
        }"
      >
        <div
          v-if="true"
          :style="{
            margin: '0 0 10px 0',
            color: '#000',
            textAlign: 'center',
            width: '100%',
            lineHeight: '60px',
            fontSize: '26px',
            textShadow: '4px 4px 2px rgba(120, 171, 195, 0.3)',
            fontWeight: 'bold',
          }"
          class="title-container"
        >
        智慧自习室预约系统 登录
        </div>
        <div
          v-if="loginType == 1"
          class="list-item"
          :style="{
            width: '80%',
            margin: '0 auto 20px',
            alignItems: 'center',
            flexWrap: 'wrap',
            display: 'flex',
          }"
        >
          <div
            v-if="false"
            class="lable"
            :style="{
              width: '64px',
              lineHeight: '44px',
              fontSize: '14px',
              color: 'rgba(64, 158, 255, 1)',
            }"
          >
            用户名
          </div>
          <input
            :style="{
              border: '0px solid rgba(64, 158, 255, 1)',
              padding: '0 10px',
              boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.3020)',
              color: '#758659',
              width: '100%',
              fontSize: '14px',
              height: '44px',
            }"
            placeholder="请输入用户名"
            name="username"
            type="text"
            v-model="rulesForm.username"
          />
        </div>
        <div
          v-if="loginType == 1"
          class="list-item"
          :style="{
            width: '80%',
            margin: '0 auto 20px',
            alignItems: 'center',
            flexWrap: 'wrap',
            display: 'flex',
          }"
        >
          <div
            v-if="false"
            class="lable"
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
              boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.3020)',
              color: '#758659',
              width: '100%',
              fontSize: '14px',
              height: '44px',
            }"
            placeholder="请输入密码"
            name="password"
            type="password"
            v-model="rulesForm.password"
          />
        </div>
        <div
          :style="{ width: '80%', margin: '20px auto' }"
          v-if="roles.length > 1"
          prop="loginInRole"
          class="list-type"
        >
          <el-radio
            v-for="item in roles"
            v-bind:key="item.roleName"
            v-model="rulesForm.role"
            :label="item.roleName"
            >{{ item.roleName }}</el-radio
          >
        </div>
        <div
          :style="{
            width: '80%',
            margin: '20px auto',
            alignItems: 'center',
            flexWrap: 'wrap',
            justifyContent: 'center',
            display: 'flex',
          }"
        >
          <el-button
            v-if="loginType == 1"
            :style="{
              border: '0',
              cursor: 'pointer',
              padding: '0 24px',
              margin: '0',
              outline: 'none',
              color: '#fff',
              borderRadius: '0',
              background: '#78ABC3',
              width: '100%',
              fontSize: '18px',
              height: '60px',
            }"
            type="primary"
            @click="login()"
            class="loginInBt"
            >登录</el-button
          >
        </div>
        <div v-if="loginType==1" class="list-item" :style='{"width":"80%","margin":"0 auto 20px","alignItems":"center","flexWrap":"wrap","display":"flex"}'>
          <div class="lable" :style='{"width":"100%","margin":"0 0 10px 0","fontSize":"14px","color":"#333"}'>验证码</div>
          <div :style='{"display":"flex","width":"100%","alignItems":"center"}'>
            <input 
              :style='{"border":"0px solid #ddd","padding":"0 10px","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.1)","color":"#333","width":"60%","fontSize":"14px","height":"44px"}' 
              placeholder="请输入验证码" 
              v-model="rulesForm.code"
              @keyup.enter="login"
            >
            <!-- 验证码展示区域，点击可刷新 -->
            <div 
              :style='{"width":"35%","marginLeft":"5%","cursor":"pointer","display":"flex","justifyContent":"space-between"}' 
              @click="getRandCode()"
            >
              <span 
                v-for="(item, index) in codes" 
                :key="index"
                :style='{
                  "color": item.color,
                  "transform": item.rotate,
                  "fontSize": item.size,
                  "fontWeight": "bold",
                  "display": "inline-block"
                }'
              >
                {{ item.num }}
              </span>
            </div>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>
import menu from "@/utils/menu";
export default {
  data() {
    return {
      baseUrl: this.$base.url,
      loginType: 1,
      rulesForm: {
        correctCode: "",
        username: "",
        password: "",
        role: "",
        code: "",
      },
      menus: [],
      roles: [],
      tableName: "",
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
  mounted() {
    let menus = menu.list();
    this.menus = menus;
    this.getRandCode();
    for (let i = 0; i < this.menus.length; i++) {
      if (this.menus[i].hasBackLogin == "是") {
        this.roles.push(this.menus[i]);
      }
    }
  },
  // created() {
  //   this.getRandCode()
  // },
  destroyed() {},
  methods: {
    //注册
    register(tableName) {
      this.$storage.set("loginTable", tableName);
      this.$storage.set("pageFlag", "register");
      this.$router.push({ path: "/register" });
    },
    // 登陆
    login() {
      if (!this.rulesForm.username) {
        this.$message.error("请输入用户名");
        return;
      }
      if (!this.rulesForm.password) {
        this.$message.error("请输入密码");
        return;
      }
      if (this.roles.length > 1) {
        if (!this.rulesForm.role) {
          this.$message.error("请选择角色");
          return;
        }
        if (!this.rulesForm.code) {
          this.$message.error("请输入验证码");
          return;
        }
        const inputCode = this.rulesForm.code.trim().toLowerCase(); // 忽略大小写和空格
        if (inputCode !== this.correctCode) {
          this.$message.error("验证码错误");
          this.getRandCode(); // 刷新验证码
          this.rulesForm.code = ""; // 清空输入框
          return;
        }
        let menus = this.menus;
        for (let i = 0; i < menus.length; i++) {
          if (menus[i].roleName == this.rulesForm.role) {
            this.tableName = menus[i].tableName;
          }
        }
      } else {
        this.tableName = this.roles[0].tableName;
        this.rulesForm.role = this.roles[0].roleName;
      }

      this.$http({
        url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`,
        method: "post",
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$storage.set("Token", data.token);
          this.$storage.set("role", this.rulesForm.role);
          this.$storage.set("sessionTable", this.tableName);
          this.$storage.set("adminName", this.rulesForm.username);
          this.$router.replace({ path: "/index/" });
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    getRandCode(len = 4) {
      let chars = [
        "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", 
        "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", 
        "X", "Y", "Z", "2", "3", "4", "5", "6", "7", "8", "9"
      ]; // 去除了容易混淆的字符
      let colors = ["#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", "#FFEEAD"];
      let sizes = ['18px', '20px', '22px'];
      
      let codeStr = ''; // 记录正确的验证码
      this.codes = [];
      for (let i = 0; i < len; i++) {
        let charIndex = Math.floor(Math.random() * chars.length);
        let char = chars[charIndex];
        codeStr += char; // 拼接正确验证码

        this.codes.push({
          num: char,
          color: colors[Math.floor(Math.random() * colors.length)],
          rotate: `rotate(${Math.random() * 30 - 15}deg)`, // -15°到+15°旋转
          size: sizes[Math.floor(Math.random() * sizes.length)]
        });
      }
      this.correctCode = codeStr.toLowerCase(); // 存储为小写用于校验
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  position: relative;
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  background: 'url(login_bg.jpg)';

  .list-item >>> .el-input .el-input__inner {
    border: 0px solid rgba(64, 158, 255, 1);
    padding: 0 10px;
    box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
    color: #758659;
    width: 100%;
    font-size: 14px;
    height: 44px;
  }

  .list-code >>> .el-input .el-input__inner {
    border: 0px solid rgba(64, 158, 255, 1);
    padding: 0 10px;
    box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
    outline: none;
    color: #758659;
    width: calc(100% - 20px);
    font-size: 14px;
    height: 44px;
  }
  .list-code span {
    transition: all 0.3s ease;
  }
  .list-type >>> .el-radio__input .el-radio__inner {
    background: rgba(53, 53, 53, 0);
    border-color: #666666;
  }
  .list-type >>> .el-radio__input.is-checked .el-radio__inner {
    background: #758659;
    border-color: #758659;
  }
  .list-type >>> .el-radio__label {
    color: #666666;
    font-size: 14px;
  }
  .list-type >>> .el-radio__input.is-checked + .el-radio__label {
    color: #758659;
    font-size: 14px;
  }
}
</style>
