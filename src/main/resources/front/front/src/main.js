import Vue from 'vue';
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';
import axios from 'axios';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/router';
import BaiduMap from 'vue-baidu-map';
import VueQuillEditor from 'vue-quill-editor';
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import '@/assets/css/iconfont.css';
import config from './config/config';
import validate from './common/validate';
import { isAuth, getCurDateTime, getCurDate } from './common/system';
import App from './App.vue';
import Breadcrumb from '@/components/Breadcrumb';
import FileUpload from '@/components/FileUpload';
import Editor from '@/components/Editor';
import aplayer from 'vue-aplayer';
import store from './store';

// -------------------- axios 全局配置（必须在使用 axios 前） --------------------
axios.defaults.withCredentials = true; // 全局携带 cookie（重要）
axios.defaults.baseURL = process.env.VUE_APP_API_BASE || 'http://localhost:8080/study_room';
Vue.prototype.$axios = axios;

// -------------------- VueResource（如果项目还在使用） --------------------
Vue.use(VueResource);
Vue.http.options.root = process.env.VUE_APP_API_BASE || 'http://localhost:8080/study_room';
Vue.http.options.emulateJSON = true;

// -------------------- 其它原有注册 --------------------
Vue.prototype.$config = config;
Vue.prototype.$validate = validate;
Vue.prototype.isAuth = isAuth;
Vue.prototype.getCurDateTime = getCurDateTime;
Vue.prototype.getCurDate = getCurDate;

Vue.use(VueRouter);
Vue.use(ElementUI);
Vue.use(BaiduMap, {});
Vue.use(VueQuillEditor);

Vue.component('Breadcrumb', Breadcrumb);
Vue.component('file-upload', FileUpload);
Vue.component('editor', Editor);
Vue.component('aplayer', aplayer);

// -------------------- Vue.http 拦截器（修复空白和 unused 错误） --------------------
Vue.http.headers.common['Token'] = localStorage.getItem('Token') || '';
Vue.http.interceptors.push((request, next) => {
    next((response) => {
        try {
            if (response && response.data && (response.data.code === 401 || response.data.code === 403)) {
                // 401/403 -> 强制到登录页
                router.replace('/login').catch(e => {
                    // 忽略重复导航异常
                    console.warn('router replace error', e);
                });
            }
        } catch (e) {
            console.warn('response interceptor error', e);
        }
        return response;
    });
});

router.afterEach((to, from) => {
    if (from.path === '/login') {
        Vue.http.headers.common['Token'] = localStorage.getItem('Token') || '';
    }
});

// -------------------- 挂载实例 --------------------
new Vue({
    render: h => h(App),
    router,
    store,
}).$mount('#app');