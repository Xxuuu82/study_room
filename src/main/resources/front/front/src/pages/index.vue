<template>
  <div class="main-containers">
    <!-- 顶部导航栏：保留原有渐变背景 -->
    <div class="top-container" :style='{"boxShadow":"0 1px 6px rgba(0, 0, 0, .08)","padding":"0 20px","alignItems":"center","display":"flex","justifyContent":"space-between","overflow":"hidden","top":"0","left":"0","background":"linear-gradient(90deg, #845EC2, #D65DB1, #FF6F91, #FF9671, #FFC75F)","width":"100%","position":"fixed","height":"64px","zIndex":"1002"}'>
      <img v-if='false' :style='{"width":"44px","objectFit":"cover","borderRadius":"100%","display":"block","height":"44px"}' src='http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg'>
      <div v-if="true" :style='{"fontSize":"20px","color":"#fff","textAlign":"center","flex":"9","fontWeight":"500"}'>智慧自习室预约系统 </div>
      <div>
        <div v-if="false" :style='{"color":"#fff","fontSize":"14px","display":"inline-block"}'>0753-1234567</div>
        <div v-if="isLogin" :style='{"color":"#fff","fontSize":"14px","display":"inline-block","marginRight":"10px","textShadow":"0 1px 2px rgba(0,0,0,0.2)"}'>{{username}}</div>
        <!-- 登录/注册按钮优化对比度 -->
        <el-button v-if="!isLogin" @click="toLogin()" :style='{"padding":"0 12px","margin":"0 0 0 10px","color":"#845EC2","borderRadius":"4px","background":"#fff","display":"inline-block","fontSize":"14px","lineHeight":"32px","height":"32px","border":"none","boxShadow":"0 1px 3px rgba(0,0,0,0.1)"}'>登录/注册</el-button>
        <!-- 退出按钮优化透明度和样式 -->
        <el-button v-if="isLogin" @click="logout" :style='{"padding":"0 12px","margin":"0 0 0 10px","color":"#fff","borderRadius":"4px","background":"rgba(255,255,255,0.2)","flex":"1","display":"inline-block","fontSize":"14px","lineHeight":"32px","height":"32px","border":"none","textShadow":"0 1px 2px rgba(0,0,0,0.2)"}'>退出</el-button>
      </div>
    </div>

    <div class="body-containers" :style='"horizontal" == "vertical" ? {"minHeight":"100vh","padding":"64px 0 0","margin":"0 0 0 210px","position":"relative","background":"rgba(64, 158, 0, .3)","display":"block"} : {"minHeight":"100vh","padding":"64px 0 0","margin":"0","position":"relative","background":"rgba(0,0,0,0.102)"}'>
      <!-- 菜单导航栏：保持原有背景，仅修改交互色为渐变主色 -->
      <div class="menu-preview" :style='{"padding":"0 20px","borderColor":"#eee","background":"#f8f9fa","borderWidth":"0 0 1px 0","width":"100%","borderStyle":"solid","height":"auto"}'>
        <el-menu class="el-menu-horizontal-demo" :style='{"border":0,"padding":"10px 0","listStyle":"none","margin":"0","background":"#f8f9fa","display":"flex","position":"relative","justifyContent":"center"}' :default-active="activeIndex" :unique-opened="true" mode="horizontal" :router="true" @select="handleSelect">
          <el-image v-if="false" :style='{"width":"44px","margin":"8px 10px 8px 0","objectFit":"cover","borderRadius":"100%","float":"left","height":"44px"}' src="http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg" fit="cover"></el-image>
          <!-- 菜单项样式保持，仅修改交互颜色 -->
          <el-menu-item v-for="(menu, index) in menuList" :index="index + ''" :key="index" :route="menu.url">
            <i v-if="true" :style='{"padding":"0 8px 0 0","margin":"0","color":"inherit","width":"14px","lineHeight":"40px","fontSize":"14px","height":"40px"}' :class="iconArr[index]"></i>
            <span :style='{"padding":"0 8px","lineHeight":"40px","fontSize":"14px","color":"inherit","height":"40px"}'>{{menu.name}}</span>
          </el-menu-item>
          <!-- 个人中心菜单项 -->
          <el-menu-item :index="menuList.length + 1 + ''" v-if="isLogin && notAdmin" :route="'/index/center'">
            <i v-if="true" :style='{"padding":"0 8px 0 0","margin":"0","color":"inherit","width":"14px","lineHeight":"40px","fontSize":"14px","height":"40px"}' class="el-icon-user"></i>
            <span :style='{"padding":"0 8px","lineHeight":"40px","fontSize":"14px","color":"inherit","height":"40px"}'>个人中心</span>
          </el-menu-item>
        </el-menu>
      </div>

      <!-- 首页轮播图区域 - 变窄 + 渐变背景 -->
      <div class="banner-preview" v-if="$route.path === '/index/home'" :style='{
        "width":"100%",
        "padding":"20px 80px",
        "height":"auto"
      }'>
        <div :style='{
          "width":"100%",
          "max-width":"1200px",
          "margin":"0 auto",
          "borderRadius":"15px",
          "overflow":"hidden",
          "background":"linear-gradient(120deg, #f5efff, #fff9e6)",
          "boxShadow":"0 4px 15px rgba(132, 94, 194, 0.08)"
        }'>
          <el-carousel :style='{"borderRadius":"15px","width":"100%","margin":"0 auto"}' trigger="click" indicator-position="inside" arrow="always" type="default" direction="horizontal" height="400px" :autoplay="true" :interval="3000" :loop="true">
            <el-carousel-item :style='{"width":"100%","overflow":"hidden","height":"100%"}' v-for="item in carouselList" :key="item.id">
              <el-image :style='{"objectFit":"cover","width":"100%","height":"100%"}' :src="baseUrl + item.value" fit="cover"></el-image>
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>

      <router-view></router-view>

      <div class="bottom-preview" :style='{"padding":"20px 0","alignItems":"center","background":"#eee","flexDirection":"column","display":"flex","width":"100%","justifyContent":"center"}'>
        <img :style='{"width":"0","objectFit":"cover","borderRadius":"100%","display":"block","height":"0"}' src="http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg" >
        <div :style='{"margin":"10px 0 0","fontSize":"14px","lineHeight":"28px","color":"#333"}'></div>
        <div :style='{"margin":"10px 0 0","fontSize":"14px","lineHeight":"28px","color":"#333"}'></div>
        <div :style='{"margin":"10px 0 0","fontSize":"14px","lineHeight":"28px","color":"#333"}'></div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
export default {
  data() {
    return {
      activeIndex: '0',
      menuList: [
        { name: "首页", url: "/index/home" },
        { name: "自习室预约", url: "/index/zixishi" },
        { name: "预约信息", url: "/index/yuyuexinxi" },
        { name: "投诉中心", url: "/index/tousu" },
        { name: "违规记录", url: "/index/weigui" },
      ],
      roleMenus: [{"backMenu":[{"child":[{"appFrontIcon":"cuIcon-discover","buttons":["新增","查看","修改","删除"],"menu":"学生","menuJump":"列表","tableName":"xuesheng"}],"menu":"学生管理"},{"child":[{"appFrontIcon":"cuIcon-goodsnew","buttons":["新增","查看","修改","删除"],"menu":"自习室","menuJump":"列表","tableName":"zixishi"}],"menu":"自习室管理"},{"child":[{"appFrontIcon":"cuIcon-present","buttons":["查看","修改","删除","审核"],"menu":"预约信息","menuJump":"列表","tableName":"yuyuexinxi"}],"menu":"预约信息管理"},{"child":[{"appFrontIcon":"cuIcon-send","buttons":["查看","修改","删除","审核"],"menu":"取消预约","menuJump":"列表","tableName":"quxiaoyuyue"}],"menu":"取消预约管理"},{"child":[{"appFrontIcon":"cuIcon-qrcode","buttons":["查看","修改","删除","审核"],"menu":"签到信息","menuJump":"列表","tableName":"qiandaoxinxi"}],"menu":"签到信息管理"},{"child":[{"appFrontIcon":"cuIcon-list","buttons":["查看","修改"],"menu":"轮播图管理","tableName":"config"},{"appFrontIcon":"cuIcon-news","buttons":["新增","查看","修改","删除"],"menu":"公告资讯","tableName":"news"}],"menu":"系统管理"}],"frontMenu":[{"child":[{"appFrontIcon":"cuIcon-discover","buttons":["查看","在线预约"],"menu":"自习室列表","menuJump":"列表","tableName":"zixishi"}],"menu":"自习室模块"}],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"否","hasFrontRegister":"否","roleName":"管理员","tableName":"users"},{"backMenu":[{"child":[{"appFrontIcon":"cuIcon-present","buttons":["查看","取消预约","签到"],"menu":"预约信息","menuJump":"列表","tableName":"yuyuexinxi"}],"menu":"预约信息管理"},{"child":[{"appFrontIcon":"cuIcon-send","buttons":["查看"],"menu":"取消预约","menuJump":"列表","tableName":"quxiaoyuyue"}],"menu":"取消预约管理"},{"child":[{"appFrontIcon":"cuIcon-qrcode","buttons":["查看"],"menu":"签到信息","menuJump":"列表","tableName":"qiandaoxinxi"}],"menu":"签到信息管理"}],"frontMenu":[{"child":[{"appFrontIcon":"cuIcon-discover","buttons":["查看","在线预约"],"menu":"自习室列表","menuJump":"列表","tableName":"zixishi"}],"menu":"自习室模块"},],"hasBackLogin":"是","hasBackRegister":"否","hasFrontLogin":"是","hasFrontRegister":"是","roleName":"学生","tableName":"xuesheng"}],
      baseUrl: '',
      carouselList: [],
      form: {
        ask: '',
        userid: localStorage.getItem('userid')
      },
      iconArr: [
        'el-icon-star-off',
        'el-icon-goods',
        'el-icon-warning',
        'el-icon-question',
        'el-icon-info',
        'el-icon-help',
        'el-icon-picture-outline-round',
        'el-icon-camera-solid',
        'el-icon-video-camera-solid',
        'el-icon-video-camera',
        'el-icon-bell',
        'el-icon-s-cooperation',
        'el-icon-s-order',
        'el-icon-s-platform',
        'el-icon-s-operation',
        'el-icon-s-promotion',
        'el-icon-s-release',
        'el-icon-s-ticket',
        'el-icon-s-management',
        'el-icon-s-open',
        'el-icon-s-shop',
        'el-icon-s-marketing',
        'el-icon-s-flag',
        'el-icon-s-comment',
        'el-icon-s-finance',
        'el-icon-s-claim',
        'el-icon-s-opportunity',
        'el-icon-s-data',
        'el-icon-s-check'
      ],
    }
  },
  // 新增计算属性：实时判断登录状态
  computed: {
    isLogin() {
      // ✅ 关键修改：只读取大写的Token（和Local Storage一致）
      const token = localStorage.getItem('Token');
      const userid = localStorage.getItem('userid');
      const username = localStorage.getItem('username');
      // 只要有Token和userid，就判定为已登录
      return !!((token && userid) || username);
    },
    username() {
      return localStorage.getItem('username') || '';
    },
    notAdmin() {
      return localStorage.getItem('sessionTable')!='"users"';
    }
  },
  created() {
    this.baseUrl = this.$config.baseUrl;
    console.log("当前menuList：", this.menuList);
    this.getCarousel();
  },
  mounted() {
    this.activeIndex = localStorage.getItem('keyPath') || '0';
    // 监听路由变化，同步菜单激活状态
    this.$watch('$route', (newVal) => {
      this.syncMenuActiveIndex(newVal.path);
    }, { immediate: true });
  },
  methods: {
    // 同步菜单激活状态
    syncMenuActiveIndex(path) {
      const menuIndex = this.menuList.findIndex(menu => menu.url === path);
      if (menuIndex !== -1) {
        this.activeIndex = menuIndex + '';
        localStorage.setItem('keyPath', this.activeIndex);
      }
    },
    handleSelect(keyPath) {
      if (keyPath) {
        localStorage.setItem('keyPath', keyPath);
      }
    },
    toLogin() {
      // ✅ 跳转登录页时携带当前路径，方便登录后返回
      this.$router.push({
        path: '/login',
        query: { redirect: this.$route.fullPath }
      });
    },
    logout() {
      localStorage.clear();
      Vue.http.headers.common['Token'] = "";
      this.$router.push('/index/home');
      this.activeIndex = '0'
      localStorage.setItem('keyPath', this.activeIndex);
      this.$forceUpdate(); // 强制更新视图
      this.$message({
        message: '登出成功',
        type: 'success',
        duration: 1000,
      });
    },
    getCarousel() {
      this.$http.get('config/list', {params: { page: 1, limit: 3 }}).then(res => {
        if (res.data.code == 0) {
          this.carouselList = res.data.data.list;
        }
      });
    },
    goMenu(path) {
      if (!this.isLogin) {
        this.toLogin();
      } else {
        this.$router.push(path);
      }
    },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.menu-preview {
  .el-scrollbar {
    height: 100%;

    & >>> .scrollbar-wrapper {
      overflow-x: hidden;
    }
  }
}

// 菜单按钮样式 - 交互色改为渐变主色#845EC2
.menu-preview .el-menu-horizontal-demo .el-menu-item {
  cursor: pointer;
  border: 0;
  padding: 0 16px;
  margin: 0 4px;
  color: #333;
  white-space: nowrap;
  display: flex;
  font-size: 14px;
  line-height: 40px;
  align-items: center;
  position: relative;
  list-style: none;
  height: 40px;
  border-radius: 4px;
  transition: all 0.2s ease;
}

// 菜单hover样式 - 使用渐变浅色调
.menu-preview .el-menu-horizontal-demo .el-menu-item:hover {
  cursor: pointer;
  border: 0;
  padding: 0 16px;
  color: #845EC2;
  white-space: nowrap;
  font-size: 14px;
  line-height: 40px;
  background-color: #f5efff;
  position: relative;
  list-style: none;
  height: 40px;
  border-radius: 4px;
}

// 菜单激活样式 - 使用渐变起始色#845EC2
.menu-preview .el-menu-horizontal-demo .el-menu-item.is-active {
  cursor: pointer;
  border: 0;
  padding: 0 16px;
  color: #fff;
  white-space: nowrap;
  font-size: 14px;
  line-height: 40px;
  background: linear-gradient(90deg, #845EC2, #D65DB1);
  position: relative;
  list-style: none;
  height: 40px;
  border-radius: 4px;
}

.banner-preview {
  .el-carousel >>> .el-carousel__indicator button {
    width: 0;
    height: 0;
    display: none;
  }
}

.banner-preview .el-carousel >>> .el-carousel__container .el-carousel__arrow--left {
  width: 36px;
  font-size: 12px;
  height: 36px;
}

.banner-preview .el-carousel >>> .el-carousel__container .el-carousel__arrow--left:hover {
  background: rgba(0,0,0,0.5);
  color: #fff;
}

.banner-preview .el-carousel >>> .el-carousel__container .el-carousel__arrow--right {
  width: 36px;
  font-size: 12px;
  height: 36px;
}

.banner-preview .el-carousel >>> .el-carousel__container .el-carousel__arrow--right:hover {
  background: rgba(0,0,0,0.5);
  color: #fff;
}

.banner-preview .el-carousel >>> .el-carousel__indicators {
  padding: 0;
  margin: 0 0 10px;
  z-index: 2;
  position: absolute;
  list-style: none;
}

.banner-preview .el-carousel >>> .el-carousel__indicators li {
  border-radius: 10px;
  padding: 0;
  margin: 0 4px;
  background: #fff;
  display: inline-block;
  width: 12px;
  opacity: 0.4;
  transition: 0.3s;
  height: 12px;
}

.banner-preview .el-carousel >>> .el-carousel__indicators li:hover {
  padding: 0;
  margin: 0 4px;
  background: #fff;
  display: inline-block;
  width: 24px;
  opacity: 0.7;
  height: 12px;
}

.banner-preview .el-carousel >>> .el-carousel__indicators li.is-active {
  padding: 0;
  margin: 0 4px;
  background: #fff;
  display: inline-block;
  width: 24px;
  opacity: 1;
  height: 12px;
}

.chat-content {
  .left-content {
    width: 100%;
    text-align: left;
  }
  .right-content {
    width: 100%;
    text-align: right;
  }
}
</style>