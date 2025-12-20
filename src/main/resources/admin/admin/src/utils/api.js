const api = {
  // 积分订单
  orderpage: 'orders/page',
  orderdelete: 'orders/delete',
  orderinfo: 'orders/info/',
  ordersave: 'orders/save',
  orderupdate: 'orders/update',

  // 配置
  configpage: 'config/page',
  configdelete: 'config/delete',
  configinfo: 'config/info/',
  configsave: 'config/save',
  configupdate: 'config/update',

  // ===== 投诉管理（按你后端接口来）=====
  complaintpage: 'complaint/page',
  complaintinfo: 'complaint/info/',
  complaintapprove: 'complaint/approve',
  complaintreject: 'complaint/reject',
  complaintdelete: 'complaint/delete',
           // + id（DELETE）
}

export default api
