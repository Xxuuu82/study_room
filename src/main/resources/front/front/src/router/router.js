import VueRouter from 'vue-router'

// 引入组件（修复quxiaoyuyueAdd的路径）
import Index from '../pages'
import Home from '../pages/home/home'
import Login from '../pages/login/login'
import Register from '../pages/register/register'
import Center from '../pages/center/center'
import Storeup from '../pages/storeup/list'
import News from '../pages/news/news-list'
import NewsDetail from '../pages/news/news-detail'
import xueshengList from '../pages/xuesheng/list'
import xueshengDetail from '../pages/xuesheng/detail'
import xueshengAdd from '../pages/xuesheng/add'
import zixishiList from '../pages/zixishi/list'
import zixishiDetail from '../pages/zixishi/detail'
import zixishiAdd from '../pages/zixishi/add'
import yuyuexinxiList from '../pages/yuyuexinxi/list'
import yuyuexinxiDetail from '../pages/yuyuexinxi/detail'
import yuyuexinxiAdd from '../pages/yuyuexinxi/add'
import quxiaoyuyueList from '../pages/quxiaoyuyue/list'
import quxiaoyuyueDetail from '../pages/quxiaoyuyue/detail'
import quxiaoyuyueAdd from '../pages/quxiaoyuyue/add'
import qiandaoxinxiList from '../pages/qiandaoxinxi/list'
import qiandaoxinxiDetail from '../pages/qiandaoxinxi/detail'
import qiandaoxinxiAdd from '../pages/qiandaoxinxi/add'
// 引入投诉中心和黑名单详情组件
import Tousu from '../pages/tousu/list.vue'
import Weigui from '../pages/weigui/list.vue'
// 新增：导入取消预约组件（在yuyuexinxi文件夹下）
import quxiaoyuyueForm from '../pages/yuyuexinxi/quxiaoyuyue.vue'



const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

// 配置路由
const router = new VueRouter({
    routes:[
        // 根路径重定向到首页
        {
            path: '/',
            redirect: '/index/home'
        },
        {
            path: '/study_room',
            redirect: '/index/home'
        },
        {
            path: '/index',
            component: Index,
            children:[
                {
                    path: 'home',
                    component: Home,
                    meta: { requiresAuth: false } // 首页无需登录
                },
                {
                    path: 'center',
                    component: Center,
                    meta: { requiresAuth: true } // 个人中心需要登录
                },
                {
                    path: 'storeup',
                    component: Storeup,
                    meta: { requiresAuth: true } // 收藏需要登录
                },
                {
                    path: 'news',
                    component: News,
                    meta: { requiresAuth: false } // 新闻列表无需登录
                },
                {
                    path: 'newsDetail',
                    component: NewsDetail,
                    meta: { requiresAuth: false } // 新闻详情无需登录
                },
                {
                    path: 'xuesheng',
                    component: xueshengList,
                    meta: { requiresAuth: true } // 学生相关需要登录
                },
                {
                    path: 'xueshengDetail',
                    component: xueshengDetail,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'xueshengAdd',
                    component: xueshengAdd,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'zixishi',
                    component: zixishiList,
                    meta: { requiresAuth: true } // 自习室相关需要登录
                },
                {
                    path: 'zixishiDetail',
                    component: zixishiDetail,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'zixishiAdd',
                    component: zixishiAdd,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'yuyuexinxi', // 预约信息核心路由
                    component: yuyuexinxiList,
                    meta: { requiresAuth: true } // 必须登录才能访问
                },
                {
                    path: 'yuyuexinxiDetail',
                    component: yuyuexinxiDetail,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'yuyuexinxiAdd',
                    component: yuyuexinxiAdd,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'quxiaoyuyue',
                    component: quxiaoyuyueList,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'quxiaoyuyueDetail',
                    component: quxiaoyuyueDetail,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'quxiaoyuyueAdd',
                    component: quxiaoyuyueAdd,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'qiandaoxinxi',
                    component: qiandaoxinxiList,
                    meta: { requiresAuth: true } // 签到信息需要登录
                },
                {
                    path: 'qiandaoxinxiDetail',
                    component: qiandaoxinxiDetail,
                    meta: { requiresAuth: true }
                },
                {
                    path: 'qiandaoxinxiAdd',
                    component: qiandaoxinxiAdd,
                    meta: { requiresAuth: true }
                },
                // 投诉中心路由
                {
                    path: 'tousu',
                    component: Tousu,
                    meta: { requiresAuth: true } // 投诉中心需要登录
                },
                // 黑名单详情路由
                {
                    path: 'weigui',
                    component: Weigui,
                    meta: { requiresAuth: true } // 违规记录需要登录
                },
                // 修正：取消预约表单路由（规范路径，避免斜杠导致的问题）
                {
                    path: 'yuyuexinxi-quxiaoyuyue',
                    name: 'quxiaoyuyueForm',
                    component: quxiaoyuyueForm,
                    meta: { requiresAuth: true }
                }
            ]
        },
        {
            path: '/login',
            component: Login,
            meta: { requiresAuth: false } // 登录页无需登录
        },
        {
            path: '/register',
            component: Register,
            meta: { requiresAuth: false } // 注册页无需登录
        },
    ]
})

// 新增：全局路由守卫（核心！控制登录权限）
router.beforeEach((to, from, next) => {
    // ✅ 关键修改：读取大写的Token（和Local Storage一致）
    const token = localStorage.getItem('Token')
    const isLogin = !!token // 转为布尔值，有token则为已登录

    // 2. 判断当前页面是否需要登录
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth)

    // 3. 核心权限逻辑
    if (requiresAuth && !isLogin) {
        // 需登录但未登录 → 跳登录页，并记录目标路径（登录后可跳转回原页面）
        next({
            path: '/login',
            query: { redirect: to.fullPath } // 携带要跳转的目标路径
        })
    } else if (to.path === '/login' && isLogin) {
        // 已登录却访问登录页 → 跳回首页（避免重复登录）
        next('/index/home')
    } else {
        // 无需登录 或 已登录 → 正常跳转
        next()
    }
})

export default router