import Vue from 'vue';
//配置路由
import VueRouter from 'vue-router'
Vue.use(VueRouter);
//1.创建组件
import Index from '@/views/index'
import Home from '@/views/home'
import Login from '@/views/login'
import NotFound from '@/views/404'
import UpdatePassword from '@/views/update-password'
import register from '@/views/register'
import center from '@/views/center'
import appointment_quxiaoyuyue from '@/views/modules/appointment/quxiaoyuyue/list'
import appointment_yuyuexinxi from '@/views/modules/appointment/yuyuexinxi/list'
import xuesheng_list from '@/views/modules/xuesheng/list'
import xuesheng_add from '@/views/modules/xuesheng/add-or-update'
import zixishi_list from '@/views/modules/zixishi/list'
import zixishi_add from '@/views/modules/zixishi/add-or-update'
import qiandaoxinxi from '@/views/modules/qiandaoxinxi/list'
// 删掉重复的旧引入：import blacklist from '@/views/modules/blacklist/list'
import message_shensu from '@/views/modules/message/shensu/list'
import complaint from '@/views/modules/complaint/index'
import ranking from '@/views/modules/ranking/ranking'
import chart from '@/views/modules/chart/chart'
import config from '@/views/modules/config/list'
import news from '@/views/modules/news/list'
// 只保留你新建的blacklist.vue的引入（统一命名为blacklist，避免大小写冲突）
import blacklist from '@/views/blacklist.vue';
// 修正：导入weiguiRecord.vue，路径和blacklist一致（views根目录）
import weiguiRecord from '@/views/weiguiRecord.vue';
import shensu from '@/views/modules/shensu/shensu'
import tubiaozhanshi from '@/views/modules/tubiaozhanshi/tubiaozhanshi'

//2.配置路由   注意：名字
const routes = [{
  path: '/index',
  name: '系统首页',
  component: Index,
  children: [{
    // 这里不设置值，是把main作为默认页面
    path: '/',
    name: '系统首页',
    component: Home,
    meta: { icon: '', title: 'center' }
  }, {
    path: '/updatePassword',
    name: '修改密码',
    component: UpdatePassword,
    meta: { icon: '', title: 'updatePassword' }
  }, {
    path: '/center',
    name: '个人信息',
    component: center,
    meta: { icon: '', title: 'center' }
  }
    , {
    path: '/xuesheng_list',
    name: '学生信息编辑及查询',
    component: xuesheng_list
  }
    , {
    path: '/xuesheng_add',
    name: '新增学生信息',
    component: xuesheng_add
  }
  , {
    path: '/yuyuexinxi',
    name: '预约信息',
    component: appointment_yuyuexinxi
  }
  , {
    path: '/quxiaoyuyue',
    name: '取消预约',
    component: appointment_quxiaoyuyue
  }
    , {
    path: '/zixishi_list',
    name: '自习室信息',
    component: zixishi_list
  }
    , {
    path: '/zixishi_add',
    name: '新增自习室',
    component: zixishi_add
  }
    , {
    path: '/blacklist',
    name: '黑名单管理',
    component: blacklist, // 现在指向你新建的blacklist.vue文件
    meta: { icon: '', title: 'blacklist' } // 补充meta，确保菜单匹配
  }
  , {
    path: '/WeiguiRecord', // 新增违规记录管理路由
    name: '违规记录管理',
    component: weiguiRecord, // 修正：直接使用导入的组件，路径和blacklist一致
    meta: { icon: 'cuIcon-list', title: 'WeiguiRecord' } // 补充icon，和menu匹配
  }
    , {
    path: '/shensu',
    name: '申诉处理',
    component: shensu
  }
    , {
    path: '/complaint',
    name: '投诉处理',
    component: complaint
  }
    , {
    path: '/ranking',
    name: '自习时长排行榜',
    component: ranking
  }
    , {
    path: '/tubiaozhanshi',
    name: '图表展示',
    component: tubiaozhanshi
  }
    , {
    path: '/config',
    name: '轮播图管理',
    component: config
  }
    , {
    path: '/news',
    name: '公告资讯',
    component: news
  }
  ]
},
{
  path: '/login',
  name: 'login',
  component: Login,
  meta: { icon: '', title: 'login' }
},
{
  path: '/register',
  name: 'register',
  component: register,
  meta: { icon: '', title: 'register' }
},
{
  path: '/',
  name: '系统首页',
  redirect: '/index'
}, /*默认跳转路由*/
{
  path: '*',
  component: NotFound
}
]
//3.实例化VueRouter  注意：名字
const router = new VueRouter({
  mode: 'hash',
  /*hash模式改为history*/
  routes // （缩写）相当于 routes: routes
})
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router;