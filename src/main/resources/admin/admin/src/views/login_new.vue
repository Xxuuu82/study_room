<template>
  <div>
    <div
        class="container"
        :style="{
        minHeight: '100vh',
        display: 'flex',
        width: '100%',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundAttachment: 'fixed',
        backgroundSize: 'cover',
        backgroundPosition: 'center center',
        backgroundRepeat: 'no-repeat'
      }"
    >
      <!-- 明显可见的测试标题（便于验证页面是否被替换） -->
      <el-form
          :style="{
          padding: '40px 20px 40px',
          boxShadow: '0px 4px 10px 0px #78ABC3',
          margin: '0',
          outline: '4px solid #78ABC3',
          borderRadius: '0',
          outlineOffset: '30px',
          background: '#fff',
          width: '460px',
          height: 'auto'
        }"
      >
        <div
            class="title-container"
            :style="{
            margin: '0 0 20px 0',
            color: '#000',
            textAlign: 'center',
            width: '100%',
            lineHeight: '40px',
            fontSize: '22px',
            textShadow: '2px 2px 2px rgba(120, 171, 195, 0.3)',
            fontWeight: 'bold'
          }"
        >
          <!-- 这行文字用于验证：如果你看不到 “这是 login_new.vue（已替换）” 说明替换没有生效 -->
          这是 login_new.vue（已替换） — 智慧自习室预约系统 登录
        </div>

        <!-- 角色选择：放在用户名上方 -->
        <div
            v-if="roles.length > 0"
            :style="{
            width: '80%',
            margin: '0 auto 18px',
            display: 'flex',
            justifyContent: 'flex-start',
            gap: '18px',
            alignItems: 'center'
          }"
        >
          <el-radio
              v-for="item in roles"
              :key="item.roleName"
              v-model="rulesForm.role"
              :label="item.roleName"
          >{{ item.roleName }}</el-radio
          >
        </div>

        <!-- 用户名 -->
        <div
            class="list-item"
            :style="{
            width: '80%',
            margin: '0 auto 14px',
            display: 'flex'
          }"
        >
          <input
              placeholder="请输入用户名"
              name="username"
              type="text"
              v-model="rulesForm.username"
              :style="inputStyle"
          />
        </div>

        <!-- 密码 -->
        <div
            class="list-item"
            :style="{
            width: '80%',
            margin: '0 auto 14px',
            display: 'flex'
          }"
        >
          <input
              placeholder="请输入密码"
              name="password"
              type="password"
              v-model="rulesForm.password"
              :style="inputStyle"
          />
        </div>

        <!-- 验证码（放在密码与登录之间） -->
        <div
            v-if="roles.length > 1"
            class="list-item"
            :style="{
            width: '80%',
            margin: '0 auto 18px',
            alignItems: 'center',
            display: 'flex',
            gap: '12px'
          }"
        >
          <div style="width: 100%;">
            <div style="margin-bottom:6px;color:#333;font-size:13px;">验证码</div>
            <input
                placeholder="请输入验证码"
                v-model="rulesForm.code"
                @keyup.enter="login"
                :style="{
                border: '0px solid #ddd',
                padding: '0 10px',
                boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.1)',
                color: '#333',
                width: '100%',
                fontSize: '14px',
                height: '44px'
              }"
            />
          </div>

          <!-- 验证码展示 -->
          <div
              :style="{
              width: '140px',
              cursor: 'pointer',
              display: 'flex',
              justifyContent: 'space-around',
              alignItems: 'center'
            }"
              @click="getRandCode()"
              title="点击刷新验证码"
          >
            <span
                v-for="(item, index) in codes"
                :key="index"
                :style="{
                color: item.color,
                transform: item.rotate,
                fontSize: item.size,
                fontWeight: 'bold',
                display: 'inline-block',
                userSelect: 'none'
              }"
            >
              {{ item.num }}
            </span>
          </div>
        </div>

        <!-- 登录按钮 -->
        <div
            :style="{
            width: '80%',
            margin: '0 auto',
            display: 'flex',
            justifyContent: 'center'
          }"
        >
          <el-button
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
              height: '48px'
            }"
              type="primary"
              @click="login"
          >
            登录
          </el-button>
        </div>

        <!-- 注册学生 按钮（放在最下面） -->
        <div
            :style="{
            width: '80%',
            margin: '18px auto 0',
            display: 'flex',
            justifyContent: 'center',
            gap: '12px'
          }"
        >
          <el-button
              type="text"
              :style="{
              color: '#1746D4',
              padding: '0',
              fontSize: '14px'
            }"
              @click="goRegister"
          >
            注册学生
          </el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import menu from "@/utils/menu";
export default {
  name: "LoginNew",
  data() {
    return {
      baseUrl: this.$base ? this.$base.url : "",
      loginType: 1,
      rulesForm: {
        correctCode: "",
        username: "",
        password: "",
        role: "",
        code: ""
      },
      menus: [],
      roles: [],
      tableName: "",
      codes: [],
      correctCode: "",
      inputStyle: {
        border: "0px solid rgba(64, 158, 255, 1)",
        padding: "0 10px",
        boxShadow: "0px 4px 10px 0px rgba(0,0,0,0.1)",
        color: "#758659",
        width: "100%",
        fontSize: "14px",
        height: "44px"
      }
    };
  },
  mounted() {
    // 关键排查点：打印确认该文件确实被加载
    console.log("DEBUG: login_new.vue 已加载（mounted） - 文件替换生效检测");
    let menus = menu.list();
    this.menus = menus || [];
    this.getRandCode();
    for (let i = 0; i < this.menus.length; i++) {
      if (this.menus[i].hasBackLogin == "是") {
        this.roles.push(this.menus[i]);
      }
    }
    if (this.roles.length === 1) {
      this.rulesForm.role = this.roles[0].roleName;
      this.tableName = this.roles[0].tableName;
    }
  },
  methods: {
    goRegister() {
      this.$router.push({ path: "/register", query: { pageFlag: "register", role: "xuesheng" } });
    },
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
        const inputCode = this.rulesForm.code.trim().toLowerCase();
        if (inputCode !== this.correctCode) {
          this.$message.error("验证码错误");
          this.getRandCode();
          this.rulesForm.code = "";
          return;
        }
        for (let i = 0; i < this.menus.length; i++) {
          if (this.menus[i].roleName == this.rulesForm.role) {
            this.tableName = this.menus[i].tableName;
            break;
          }
        }
      } else {
        if (this.roles.length === 1) {
          this.tableName = this.roles[0].tableName;
          this.rulesForm.role = this.roles[0].roleName;
        }
      }

      this.$http({
        url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`,
        method: "post"
      })
          .then(({ data }) => {
            if (data && data.code === 0) {
              this.$storage.set("Token", data.token);
              this.$storage.set("role", this.rulesForm.role);
              this.$storage.set("sessionTable", this.tableName);
              this.$storage.set("adminName", this.rulesForm.username);
              this.$router.replace({ path: "/index/" });
            } else {
              this.$message.error(data.msg || "登录失败");
            }
          })
          .catch(err => {
            this.$message.error("请求失败");
            console.error(err);
          });
    },
    getRandCode(len = 4) {
      const chars = [
        "A","B","C","D","E","F","G","H","J","K",
        "M","N","P","Q","R","S","T","U","V","W",
        "X","Y","Z","2","3","4","5","6","7","8","9"
      ];
      const colors = ["#FF6B6B","#4ECDC4","#45B7D1","#96CEB4","#FFEEAD"];
      const sizes = ["16px","18px","20px"];

      let codeStr = "";
      this.codes = [];
      for (let i = 0; i < len; i++) {
        const char = chars[Math.floor(Math.random() * chars.length)];
        codeStr += char;
        this.codes.push({
          num: char,
          color: colors[Math.floor(Math.random() * colors.length)],
          rotate: `rotate(${Math.random() * 30 - 15}deg)`,
          size: sizes[Math.floor(Math.random() * sizes.length)]
        });
      }
      this.correctCode = codeStr.toLowerCase();
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  position: relative;
  /* 删除背景图片，使用纯色背景 */
  background: #f3f7fa;

  .title-container {
    letter-spacing: 1px;
  }

  /* 保持表单内输入样式一致 */
  .list-item input {
    border: 0;
    padding: 0 10px;
    box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.1);
    color: #758659;
    width: 100%;
    font-size: 14px;
    height: 44px;
    outline: none;
    background: #fff;
  }
}
</style>