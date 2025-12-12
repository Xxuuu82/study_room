<template>
	<div style="height: 100%;">
		<!-- 垂直布局侧边栏（保持白色，突出渐变主体） -->
		<index-aside v-if="'vertical' == 'vertical'" :style='{"boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.3020)","overflow":"hidden","top":"0","left":"0","background":"#fff","bottom":"0","width":"210px","fontSize":"0px","position":"fixed","height":"100%","zIndex":"1001"}'></index-aside>
		<el-main :style='"vertical" == "vertical" ? {"minHeight":"100%","padding":"0","margin":"0 0 0 210px","position":"relative","display":"block"} : {"minHeight":"100%","margin":"0","position":"relative"}'>
			<!-- 核心修改1：顶部导航栏 → 红橙黄亮渐变（无紫色，高饱和度） -->
			<index-header :style='{"boxShadow":"0 1px 8px rgba(255, 110, 64, .4)","padding":"8px 20px","alignItems":"center","background":"linear-gradient(90deg, #FF5722, #FF9800, #FFEB3B)","display":"flex","width":"100%","position":"relative","justifyContent":"center","zIndex":"1"}'></index-header>

			<!-- 水平布局侧边栏 → 浅橙黄背景（亮色调配套） -->
			<index-aside v-if="'vertical' == 'horizontal'" :style='{"width":"100%","borderColor":"#efefef","borderStyle":"solid","background":"#FFF3E0","borderWidth":"0 0 1px 0","height":"auto"}'></index-aside>

			<!-- 核心修改2：面包屑导航 → 稍微浅一点的橙黄（兼顾加深+亮度） -->
			<bread-crumbs :title="title" :style='{"padding":"0 20px","margin":"15px auto 0 auto","borderColor":"#FFE8CC","alignItems":"center","borderWidth":"0 0 1px 0","background":"#FFF7ED","display":"flex","width":"calc(100% - 60px)","borderStyle":"solid","height":"40px"}' class="bread-crumbs"></bread-crumbs>

			<!-- 路由视图区域（保持不变） -->
			<router-view :style='{"padding":"30px","flexWrap":"wrap","background":"url(login_bg.jpg) center center/cover fixed no-repeat","display":"flex","height":"calc(100vh - 120px)"}' class="router-view" style="height:auto;background: transparent;"></router-view>
		</el-main>
	</div>
</template>

<script>
	import IndexAside from '@/components/index/IndexAsideStatic'
	import IndexHeader from '@/components/index/IndexHeader'
	import menu from "@/utils/menu";
	export default {
		components: {
			IndexAside,
			IndexHeader
		},
		data() {
			return {
				menuList: [],
				role: "",
				currentIndex: -2,
				itemMenu: [],
				title: '',
			};
		},
		mounted() {
			let menus = menu.list();
			this.menuList = menus;
			this.role = this.$storage.get("role");
		},
		created() {
			this.init();
		},
		methods: {
			init(){
				this.$nextTick(()=>{

				})
			},
			menuHandler(menu) {
				this.$router.push({
					name: menu.tableName
				});
				this.title = menu.menu;
			},
			titleChange(index, menus) {
				this.currentIndex = index
				this.itemMenu = menus;
				console.log(menus);
			},
			homeChange(index) {
				this.itemMenu = [];
				this.title = ""
				this.currentIndex = index
				this.$router.push({
					name: 'home'
				});
			},
			centerChange(index) {
				this.itemMenu = [{
					"buttons": ["新增", "查看", "修改", "删除"],
					"menu": "修改密码",
					"tableName": "updatePassword"
				}, {
					"buttons": ["新增", "查看", "修改", "删除"],
					"menu": "个人信息",
					"tableName": "center"
				}];
				this.title = ""
				this.currentIndex = index
				this.$router.push({
					name: 'home'
				});

			}
		}
	};
</script>
<style lang="scss" scoped>
	a {
		text-decoration: none;
		color: #555;
	}

	// 配套修改：链接hover为渐变中的亮橙（统一红橙黄风格）
	a:hover {
		background: #FF9800;
		color: #fff !important; // 文字变白，增强对比和亮度
	}

	.el-main {
		padding: 0;
		display: block;
	}

	.nav-list {
		width: 100%;
		margin: 0 auto;
		text-align: left;
		margin-top: 20px;

		.nav-title {
			display: inline-block;
			font-size: 15px;
			color: #333;
			padding: 15px 25px;
			border: none;
		}

		.nav-title.active {
			color: #555;
			cursor: default;
			background-color: #fff;
		}
	}

	.nav-item {
		margin-top: 20px;
		background: #FFFFFF;
		padding: 15px 0;

		.menu {
			padding: 15px 25px;
		}
	}

	.detail-form-content {
	    background: transparent;
	}

	/* 核心修改：面包屑颜色稍微浅一点（兼顾加深+亮度） */
	::v-deep .bread-crumbs {
		// 浅一点的背景色（比之前的#FFF0D9更亮）
		background: #FFF7ED !important;
		// 文字颜色保持适中（不深不浅）
		color: #444 !important;
		// 浅一点的边框色（比之前的#FFC180更亮）
		border-color: #FFE8CC !important;
	}
</style>