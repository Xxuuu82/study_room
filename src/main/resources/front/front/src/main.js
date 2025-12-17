import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
Vue.use(VueResource)
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router/router'
import BaiduMap from 'vue-baidu-map'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import '@/assets/css/iconfont.css'
import config from './config/config'
import validate from './common/validate'
import { isAuth,getCurDateTime,getCurDate } from './common/system'
import App from './App.vue'
import Breadcrumb from '@/components/Breadcrumb'
import FileUpload from '@/components/FileUpload'
import Editor from "@/components/Editor";
import aplayer from 'vue-aplayer';
import store from './store'

// 把 axios 的 baseURL 也设置好（供你在某些模块想用 axios 时使用）
axios.defaults.baseURL = process.env.VUE_APP_API_BASE || 'http://localhost:8080/study_room'
// 不要覆写 Vue.prototype.$http（vue-resource 会定义该属性并且用于 this.$http）
// 把 axios 挂到 Vue.prototype.$axios，供需要用 axios 的代码使用
Vue.prototype.$axios = axios;

// 确保只调用一次 Vue.use(VueResource)，并在使用 Vue.http 前调用它
Vue.use(VueResource)

// 设置 vue-resource 的 root（包含 context-path）
Vue.http.options.root = process.env.VUE_APP_API_BASE || 'http://localhost:8080/study_room'
Vue.http.options.emulateJSON = true


import axios from 'axios'

// 把 axios 的 baseURL 也设置好（供你在某些模块想用 axios 时使用）
axios.defaults.baseURL = process.env.VUE_APP_API_BASE || 'http://localhost:8082/study_room'
// 不要覆写 Vue.prototype.$http（vue-resource 会定义该属性并且用于 this.$http）
// 把 axios 挂到 Vue.prototype.$axios，供需要用 axios 的代码使用
Vue.prototype.$axios = axios;

// 确保只调用一次 Vue.use(VueResource)，并在使用 Vue.http 前调用它
Vue.use(VueResource)

// 设置 vue-resource 的 root（包含 context-path）
Vue.http.options.root = process.env.VUE_APP_API_BASE || 'http://localhost:8082/study_room'
Vue.http.options.emulateJSON = true


Vue.prototype.$config = config;
Vue.prototype.$validate = validate;
Vue.prototype.isAuth = isAuth;
Vue.prototype.getCurDateTime = getCurDateTime;
Vue.prototype.getCurDate = getCurDate;

Vue.use(VueRouter);
Vue.use(VueResource);
Vue.use(ElementUI);
Vue.use(BaiduMap, {});
Vue.use(VueQuillEditor);

Vue.component('Breadcrumb', Breadcrumb);
Vue.component('file-upload', FileUpload);
Vue.component('editor', Editor);
Vue.component('aplayer', aplayer);

Vue.http.options.root = config.baseUrl;
Vue.http.headers.common['Token'] = localStorage.getItem('Token');
Vue.http.interceptors.push(function(request, next) {
　next((response) => {
    if (response.data.code == 401 || response.data.code == 403) {
      this.$router.replace('/login').catch(err => {});
    } else {
      return response;
    }
  });
});

router.afterEach((to, from) => {
  if (from.path == '/login') {
    Vue.http.headers.common['Token'] = localStorage.getItem('Token');
  }
})

new Vue({
  render: h => h(App),
  router,
  store,
}).$mount('#app')
