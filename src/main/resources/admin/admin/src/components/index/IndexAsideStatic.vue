<template>
	<div class="menu-preview">
		<!-- 竖向 -->
		<el-scrollbar wrap-class="scrollbar-wrapper">
			<el-menu :default-openeds="[]" :unique-opened="true" :style='{"border":0,"padding":"0","listStyle":"none","margin":"0","position":"relative","background":"#FFF"}' class="el-menu-vertical-demo" default-active="0">
				<el-image v-if="false" :style='{"width":"44px","margin":"10px auto","objectFit":"cover","borderRadius":"100%","display":"block","height":"44px"}' src="login_bg.jpg" fit="cover"></el-image>

				<!-- 系统首页 - 蓝色 -->
				<el-submenu index="0" @click.native="menuHandler('')">
					<template slot="title">
						<i v-if='true' :style='{"width":"34px","verticalAlign":"middle","margin":"0 3px","fontSize":"18px","color":"#1E88E5","textAlign":"center"}' class="el-icon-menu el-icon-s-home" />
						<span :style='{"color":"inherit","verticalAlign":"middle","fontSize":"14px"}'>系统首页</span>
					</template>
				</el-submenu>

				<!-- 个人中心 - 绿色 -->
				<el-submenu index="1">
					<template slot="title">
						<i v-if='true' :style='{"width":"34px","verticalAlign":"middle","margin":"0 3px","fontSize":"18px","color":"#07C160","textAlign":"center"}' class="el-icon-menu el-icon-user-solid" />
						<span :style='{"color":"inherit","verticalAlign":"middle","fontSize":"14px"}'>个人中心</span>
					</template>
					<el-menu-item index="1-1" @click="menuHandler('updatePassword')">修改密码</el-menu-item>
					<el-menu-item index="1-2" @click="menuHandler('center')">个人信息</el-menu-item>
				</el-submenu>

				<!-- 动态菜单 - 根据名称匹配颜色 -->
				<el-submenu v-for=" (menu,index) in menuList.backMenu" :key="menu.menu" :index="index+2+''">
					<template slot="title">
						<i v-if='true'
						   :style='{"width":"34px","verticalAlign":"middle","margin":"0 3px","fontSize":"18px","color": getMenuColor(menu.menu),"textAlign":"center"}'
						   class="el-icon-menu"
						   :class="icons[index]"
						/>
						<span :style='{"color":"inherit","verticalAlign":"middle","fontSize":"14px"}'>{{ menu.menu }}</span>
					</template>
					<el-menu-item v-for=" (child,sort) in menu.child" :key="sort" :index="(index+2)+'-'+sort" @click="menuHandler(child.tableName)">{{ child.menu }}</el-menu-item>
				</el-submenu>
			</el-menu>
		</el-scrollbar>
	</div>
</template>

<script>
import menu from '@/utils/menu'
export default {
	data() {
		return {
			menuList: [],
			dynamicMenuRoutes: [],
			role: '',
			icons: [
				'el-icon-s-cooperation',
				'el-icon-s-order',
				'el-icon-s-platform',
				'el-icon-s-fold',
				'el-icon-s-unfold',
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
				'el-icon-s-custom',
				'el-icon-s-opportunity',
				'el-icon-s-data',
				'el-icon-s-check',
				'el-icon-s-grid',
				'el-icon-menu',
				'el-icon-chat-dot-square',
				'el-icon-message',
				'el-icon-postcard',
				'el-icon-position',
				'el-icon-microphone',
				'el-icon-close-notification',
				'el-icon-bangzhu',
				'el-icon-time',
				'el-icon-odometer',
				'el-icon-crop',
				'el-icon-aim',
				'el-icon-switch-button',
				'el-icon-full-screen',
				'el-icon-copy-document',
				'el-icon-mic',
				'el-icon-stopwatch',
			],
			menulistBorderBottom: {},
		}
	},
	mounted() {
		const menus = menu.list()
		if(menus) {
			this.menuList = menus
		} else {
			let params = {
				page: 1,
				limit: 1,
				sort: 'id',
			}

			this.$http({
				url: "menu/list",
				method: "get",
				params: params
			}).then(({
				data
			}) => {
				if (data && data.code === 0) {
					this.menuList = JSON.parse(data.data.list[0].menujson);
					this.$storage.set("menus", this.menuList);
				}
			})
		}
		this.role = this.$storage.get('role')

		for(let i=0;i<this.menuList.length;i++) {
			if(this.menuList[i].roleName == this.role) {
				this.menuList = this.menuList[i];
				break;
			}
		}
		this.styleChange()
	},
	created(){
		this.icons.sort(()=>{
			return (0.5-Math.random())
		})
	},
	methods: {
		// 新增：根据菜单名称返回对应颜色
		getMenuColor(menuName) {
			switch (menuName) {
              case '系统首页':
                return '#409EFF'; // 蓝色

              case '个人中心':
                return '#67C23A'; // 绿色

              case '学生信息管理':
                return '#FFC107'; // 黄色

              case '自习室管理':
                return '#F56C6C'; // 红色

              case '预约信息管理':
                return '#909399'; // 灰色

              case '黑名单管理':
                return '#FF7043'; // 橘红色

              case '消息处理':
                return '#8E44AD'; // 紫色

              case '数据分析':
                return '#26A69A'; // 蓝绿色

              case '系统管理':
                return '#87CEEB'; // 淡蓝色

              default:
                return '#333'; // 默认深灰
            }

		},
		styleChange() {
			this.$nextTick(() => {
				document.querySelectorAll('.el-menu-vertical-demo .el-submenu .el-menu').forEach(el => {
				  el.removeAttribute('style')
				  const icon = {"border":"none","display":"none"}
				  Object.keys(icon).forEach((key) => {
					el.style[key] = icon[key]
				  })
				})
			})
		},
		menuHandler(name) {
			let router = this.$router
			name = '/'+name
			router.push(name)
		},
	}
}
</script>
<style lang="scss" scoped>
	.menu-preview {
	  .el-scrollbar {
	    height: 100%;

	    & >>> .scrollbar-wrapper {
	      overflow-x: hidden;
	    }

				// 竖向
		.el-menu-vertical-demo {
		  .el-submenu:first-of-type >>> .el-submenu__title .el-submenu__icon-arrow {
		    display: none;
		  }
		}

		.el-menu-vertical-demo>.el-menu-item {
				  				  cursor: pointer;
				  				  padding: 0 20px;
				  				  color: #333;
				  				  white-space: nowrap;
				  				  background: #fff;
				  				  display: flex;
				  				  border-color: #D8D8D8;
				  				  border-width: 0 0 1px;
				  				  justify-content: center;
				  				  align-items: center;
				  				  position: relative;
				  				  border-style: solid;
				  		}

		.el-menu-vertical-demo>.el-menu-item:hover {
						color: #fff;
						background: 'url(login_bg.jpg)' center center no-repeat;
					}

		.el-menu-vertical-demo .el-submenu >>> .el-submenu__title {
						cursor: pointer;
						padding: 0 20px;
						color: #333;
						white-space: nowrap;
						background: #fff;
						display: flex;
						border-color: #D8D8D8;
						border-width: 0 0 1px;
						justify-content: center;
						align-items: center;
						position: relative;
						border-style: solid;
					}

		.el-menu-vertical-demo .el-submenu >>> .el-submenu__title:hover {
						color: #fff;
						background: 'url(login_bg.jpg)' center center no-repeat;
					}

		.el-menu-vertical-demo .el-submenu >>> .el-submenu__title .el-submenu__icon-arrow {
						margin: -7px 0 0 0;
						top: 50%;
						color: inherit;
						vertical-align: middle;
						font-size: 0;
						position: absolute;
						right: 20px;
					}

		.el-menu-vertical-demo .el-submenu {
						padding: 0;
						margin: 0;
						list-style: none;
					}

		.el-menu-vertical-demo .el-submenu >>> .el-menu .el-menu-item {
						padding: 0 40px;
						color: #A2A2A2;
						background: #F9F9F9;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}

		.el-menu-vertical-demo .el-submenu >>> .el-menu .el-menu-item:hover {
						padding: 0 40px;
						color: #fff;
						background: #97C9D6;
						line-height: 50px;
						height: 50px;
					}

		.el-menu-vertical-demo .el-submenu >>> .el-menu .el-menu-item.is-active {
						padding: 0 40px;
						color: #fff;
						background: #97C9D6;
						line-height: 50px;
						height: 50px;
					}
		// 竖向
			  }
	  	}
</style>