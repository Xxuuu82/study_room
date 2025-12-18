const menu = {
    list() {
      return [
        {
          backMenu: [
            {
              child: [
                {
                  appFrontIcon: "cuIcon-discover",
                  buttons: ["新增", "查看", "修改", "删除"],
                  menu: "学生信息查询及编辑",
                  menuJump: "列表",
                  tableName: "xuesheng_list"
                },
                {
                  appFrontIcon: "cuIcon-discover",
                  buttons: ["新增", "查看", "修改", "删除"],
                  menu: "新增学生信息",
                  menuJump: "列表",
                  tableName: "xuesheng_add"
                },
              ],
              menu: "学生信息管理"
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-goodsnew",
                  buttons: ["新增", "查看", "修改", "删除"],
                  menu: "自习室查询及编辑",
                  menuJump: "列表",
                  tableName: "zixishi_list"
                },
                {
                  appFrontIcon: "cuIcon-goodsnew",
                  buttons: ["新增", "查看", "修改", "删除"],
                  menu: "新增自习室",
                  menuJump: "列表",
                  tableName: "zixishi_add"
                },

              ],
              menu: "自习室管理"
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-present",
                  buttons: ["查看", "修改", "删除", "审核"],
                  menu: "预约信息",
                  menuJump: "列表",
                  tableName: "yuyuexinxi"
                },
                {
                  appFrontIcon: "cuIcon-send",
                  buttons: ["查看", "修改", "删除", "审核"],
                  menu: "取消预约",
                  menuJump: "列表",
                  tableName: "quxiaoyuyue"
                }
              ],
              menu: "预约信息管理"
            },
            /*{
              child: [
                {
                  appFrontIcon: "cuIcon-send",
                  buttons: ["查看", "修改", "删除", "审核"],
                  menu: "取消预约",
                  menuJump: "列表",
                  tableName: "quxiaoyuyue"
                }
              ],
              menu: "取消预约管理"
            },*/
            {
              child: [
                {
                  appFrontIcon: "cuIcon-list",
                  buttons: ["查看", "审批"],
                  menu: "黑名单管理",
                  tableName: "blacklist"
                },
                // 核心修改：tableName和路由path一致，补充menuJump
                {
                  appFrontIcon: "cuIcon-list",
                  buttons: ["新增", "查看", "删除"],
                  menu: "违规记录管理",
                  menuJump: "列表", // 和其他菜单保持一致的配置
                  tableName: "WeiguiRecord" // 匹配路由path：weiguiRecord
                }
                ],
                menu: "黑名单管理"
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-list",
                  buttons: ["查看", "审批"],
                  menu: "申诉处理",
                  tableName: "message_shensu"
                },
                {
                  appFrontIcon: "cuIcon-news",
                  buttons: [ "查看", "审批"],
                  menu: "投诉处理",
                  tableName: "message_tousu"
                }
              ],
              menu: "消息处理"
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-qrcode",
                  buttons: ["查看", "修改", "删除", "审核", "分析"],
                  menu: "自习时长排行榜",
                  menuJump: "列表",
                  tableName: "ranking"
                },
                {
                  appFrontIcon: "cuIcon-list",
                  buttons: ["查看", "修改", "删除", "审核", "分析"],
                  menu: "图表展示",
                  menuJump: "列表",
                  tableName: "chart"
                }
              ],
              menu: "数据分析"
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-list",
                  buttons: ["查看", "修改"],
                  menu: "轮播图管理",
                  tableName: "config"
                },
                {
                  appFrontIcon: "cuIcon-news",
                  buttons: ["新增", "查看", "修改", "删除"],
                  menu: "公告资讯",
                  tableName: "news"
                }
              ],
              menu: "系统管理"
            }
          ],
          frontMenu: [
            {
              child: [
                {
                  appFrontIcon: "cuIcon-discover",
                  buttons: ["查看", "在线预约"],
                  menu: "自习室列表",
                  menuJump: "列表",
                  tableName: "zixishi"
                }
              ],
              menu: "自习室模块"
            }
          ],
          hasBackLogin: "是",
          hasBackRegister: "否",
          hasFrontLogin: "否",
          hasFrontRegister: "否",
          roleName: "管理员",
          tableName: "users"
        },
        {
          backMenu: [
            {
              child: [
                {
                  appFrontIcon: "cuIcon-present",
                  buttons: ["查看", "取消预约", "签到","签退"],
                  menu: "预约信息",
                  menuJump: "列表",
                  tableName: "yuyuexinxi"
                }
              ],
              menu: "预约信息管理"
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-send",
                  buttons: ["查看"],
                  menu: "取消预约",
                  menuJump: "列表",
                  tableName: "quxiaoyuyue"
                }
              ],
              menu: "取消预约管理"
            },
            {
              child: [
                {
                  appFrontIcon: "cuIcon-qrcode",
                  buttons: ["查看"],
                  menu: "自习时长信息",
                  menuJump: "列表",
                  tableName: "qiantuixinxi"
                }
              ],
              menu: "自习时长管理"
            }
          ],
          frontMenu: [
            {
              child: [
                {
                  appFrontIcon: "cuIcon-discover",
                  buttons: ["查看", "在线预约"],
                  menu: "自习室列表",
                  menuJump: "列表",
                  tableName: "zixishi"
                }
              ],
              menu: "自习室模块"
            }
          ],
          hasBackLogin: "是",
          hasBackRegister: "否",
          hasFrontLogin: "是",
          hasFrontRegister: "是",
          roleName: "学生",
          tableName: "xuesheng"
        }
      ];
    }
  };

  export default menu;