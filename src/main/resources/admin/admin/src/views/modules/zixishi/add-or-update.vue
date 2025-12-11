<template>
	<div class="addEdit-block" style="width: 100%;">
		<el-form
			:style='{"padding":"30px","boxShadow":"0 0px 0px rgba(64, 158, 255, .3)","borderRadius":"6px","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%"}'
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="100px"
		>
			<template>
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' class="input" v-if="type!='info'" label="名称" prop="mingcheng">
					<el-input v-model="ruleForm.mingcheng" placeholder="名称" clearable :readonly="ro.mingcheng"></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' v-else class="input" label="名称" prop="mingcheng">
					<el-input v-model="ruleForm.mingcheng" placeholder="名称" readonly></el-input>
				</el-form-item>

				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' class="select" v-if="type!='info'" label="规则" prop="guimo">
					<el-select :disabled="ro.guimo" v-model="ruleForm.guimo" placeholder="请选择规则">
						<el-option v-for="(item,index) in guimoOptions" v-bind:key="index" :label="item" :value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' v-else class="input" label="规则" prop="guimo">
					<el-input v-model="ruleForm.guimo" placeholder="规则" readonly></el-input>
				</el-form-item>

				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' class="input" v-if="type!='info'" label="位置" prop="weizhi">
					<el-input v-model="ruleForm.weizhi" placeholder="位置" clearable :readonly="ro.weizhi"></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' v-else class="input" label="位置" prop="weizhi">
					<el-input v-model="ruleForm.weizhi" placeholder="位置" readonly></el-input>
				</el-form-item>

				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' class="select" v-if="type!='info'" label="是否有课" prop="shifouyouke">
					<el-select :disabled="ro.shifouyouke" v-model="ruleForm.shifouyouke" placeholder="请选择是否有课">
						<el-option v-for="(item,index) in shifouyoukeOptions" v-bind:key="index" :label="item" :value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' v-else class="input" label="是否有课" prop="shifouyouke">
					<el-input v-model="ruleForm.shifouyouke" placeholder="是否有课" readonly></el-input>
				</el-form-item>

				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' class="upload" v-if="type!='info' && !ro.tupian" label="图片" prop="tupian">
					<file-upload tip="点击上传图片" action="file/upload" :limit="3" :multiple="true" :fileUrls="ruleForm.tupian?ruleForm.tupian:''"
						@change="tupianUploadChange"></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' class="upload" v-else-if="ruleForm.tupian" label="图片" prop="tupian">
					<img v-if="ruleForm.tupian.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" v-bind:key="index"
						:src="ruleForm.tupian.split(',')[0]" width="100" height="100">
					<img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index"
						v-for="(item,index) in ruleForm.tupian.split(',')" :src="$base.url+item" width="100" height="100">
				</el-form-item>

				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' class="input" v-if="type!='info'" label="座位" prop="zuowei">
					<el-input v-model="ruleForm.zuowei" placeholder="座位" clearable :readonly="ro.zuowei"></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' v-else class="input" label="座位" prop="zuowei">
					<el-input v-model="ruleForm.zuowei" placeholder="座位" readonly></el-input>
				</el-form-item>

				<!-- 可约时间选择 -->
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' class="input" v-if="type!='info'" label="可约时间" prop="keyueshijian">
					<el-input v-model="ruleForm.keyueshijian" placeholder="点击选择可约时间" clearable :readonly="ro.keyueshijian"
						@click.native="showTimePicker = true" @focus="showTimePicker = true" style="cursor: pointer;">
						<template slot="append">
							<i class="el-icon-time"></i>
						</template>
					</el-input>
				</el-form-item>
				<el-form-item :style='{"width":"50%","margin":"0 0 20px 0"}' v-else class="input" label="可约时间" prop="keyueshijian">
					<el-input v-model="ruleForm.keyueshijian" placeholder="可约时间" readonly></el-input>
				</el-form-item>
			</template>

			<!-- 时间选择弹窗 -->
			<el-dialog title="选择可约时间" :visible.sync="showTimePicker" width="500px" :close-on-click-modal="false"
				custom-class="time-picker-dialog">
				<div class="time-picker-container">
					<div class="time-section">
						<div class="section-title">开始时间</div>
						<div class="time-grid">
							<div v-for="time in startTimes" :key="'start-' + time.value" class="time-item"
								:class="{ 'selected': selectedStartTime === time.value }" @click="selectStartTime(time.value)">
								<div class="time-label">{{ time.label }}</div>
								<div class="time-period">{{ time.period }}</div>
							</div>
						</div>
					</div>

					<div class="time-section">
						<div class="section-title">结束时间</div>
						<div class="time-grid">
							<div v-for="time in endTimes" :key="'end-' + time.value" class="time-item"
								:class="{ 'selected': selectedEndTime === time.value, 'disabled': isTimeDisabled(time.value) }"
								@click="selectEndTime(time.value)">
								<div class="time-label">{{ time.label }}</div>
								<div class="time-period">{{ time.period }}</div>
							</div>
						</div>
					</div>

					<div class="selected-time-display">
						<div class="display-title">已选择时间段</div>
						<div class="display-time">{{ formatSelectedTime() }}</div>
					</div>

					<div class="time-picker-actions">
						<el-button class="cancel-btn" @click="showTimePicker = false">取消</el-button>
						<el-button class="confirm-btn" type="primary" @click="confirmTimeSelection"
							:disabled="!selectedStartTime || !selectedEndTime">确认</el-button>
					</div>
				</div>
			</el-dialog>

			<!-- 详情部分移动到是否有课下方，占整行宽度 -->
			<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-if="type!='info'" label="详情" prop="xiangqing">
				<editor style="min-width: 200px; max-width: 100%;" v-model="ruleForm.xiangqing" class="editor"
					action="file/upload"></editor>
			</el-form-item>
			<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-else-if="ruleForm.xiangqing" label="详情" prop="xiangqing">
				<span
					:style='{"fontSize":"14px","lineHeight":"40px","color":"#000","fontWeight":"500","display":"inline-block","width":"100%"}'
					v-html="ruleForm.xiangqing"></span>
			</el-form-item>

			<el-form-item :style='{"width":"100%","padding":"0","margin":"0"}' class="btn">
				<el-button
					:style='{"border":"0","cursor":"pointer","padding":"0","margin":"0 20px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"30px","background":"#93C7B3","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'
					v-if="type!='info'" type="primary" class="btn-success" @click="onSubmit">提交</el-button>
				<el-button
					:style='{"border":"0px solid rgba(64, 158, 255, 1)","cursor":"pointer","padding":"0","margin":"0","outline":"none","color":"#fff","borderRadius":"30px","background":"#93C7B3","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'
					v-if="type!='info'" class="btn-close" @click="back()">取消</el-button>
				<el-button
					:style='{"border":"0px solid rgba(64, 158, 255, 1)","cursor":"pointer","padding":"0","margin":"0","outline":"none","color":"#fff","borderRadius":"30px","background":"#93C7B3","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'
					v-if="type=='info'" class="btn-close" @click="back()">返回</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
import {
	isNumber,
	isIntNumer,
	isEmail,
	isPhone,
	isMobile,
	isURL,
	checkIdCard
} from "@/utils/validate";
export default {
	data() {
		let self = this
		var validateIntNumber = (rule, value, callback) => {
			if (!value) {
				callback();
			} else if (!isIntNumer(value)) {
				callback(new Error("请输入整数"));
			} else {
				callback();
			}
		};
		return {
			id: '',
			type: '',

			// 时间选择相关数据
			showTimePicker: false,
			selectedStartTime: '',
			selectedEndTime: '',
			startTimes: [{
					label: '08:00',
					value: '08:00',
					period: '上午'
				},
				{
					label: '09:00',
					value: '09:00',
					period: '上午'
				},
				{
					label: '10:00',
					value: '10:00',
					period: '上午'
				},
				{
					label: '11:00',
					value: '11:00',
					period: '上午'
				},
				{
					label: '12:00',
					value: '12:00',
					period: '中午'
				},
				{
					label: '13:00',
					value: '13:00',
					period: '下午'
				},
				{
					label: '14:00',
					value: '14:00',
					period: '下午'
				},
				{
					label: '15:00',
					value: '15:00',
					period: '下午'
				},
				{
					label: '16:00',
					value: '16:00',
					period: '下午'
				},
				{
					label: '17:00',
					value: '17:00',
					period: '下午'
				},
				{
					label: '18:00',
					value: '18:00',
					period: '晚上'
				},
				{
					label: '19:00',
					value: '19:00',
					period: '晚上'
				},
				{
					label: '20:00',
					value: '20:00',
					period: '晚上'
				},
				{
					label: '21:00',
					value: '21:00',
					period: '晚上'
				},
				{
					label: '22:00',
					value: '22:00',
					period: '晚上'
				}
			],
			endTimes: [{
					label: '09:00',
					value: '09:00',
					period: '上午'
				},
				{
					label: '10:00',
					value: '10:00',
					period: '上午'
				},
				{
					label: '11:00',
					value: '11:00',
					period: '上午'
				},
				{
					label: '12:00',
					value: '12:00',
					period: '中午'
				},
				{
					label: '13:00',
					value: '13:00',
					period: '下午'
				},
				{
					label: '14:00',
					value: '14:00',
					period: '下午'
				},
				{
					label: '15:00',
					value: '15:00',
					period: '下午'
				},
				{
					label: '16:00',
					value: '16:00',
					period: '下午'
				},
				{
					label: '17:00',
					value: '17:00',
					period: '下午'
				},
				{
					label: '18:00',
					value: '18:00',
					period: '晚上'
				},
				{
					label: '19:00',
					value: '19:00',
					period: '晚上'
				},
				{
					label: '20:00',
					value: '20:00',
					period: '晚上'
				},
				{
					label: '21:00',
					value: '21:00',
					period: '晚上'
				},
				{
					label: '22:00',
					value: '22:00',
					period: '晚上'
				},
				{
					label: '23:00',
					value: '23:00',
					period: '晚上'
				}
			],

			ro: {
				mingcheng: false,
				tupian: false,
				guimo: false,
				zuowei: false,
				weizhi: false,
				keyueshijian: false,
				shifouyouke: false,
				xiangqing: false,
			},

			ruleForm: {
				mingcheng: '',
				tupian: '',
				guimo: '',
				zuowei: '20',
				weizhi: '',
				keyueshijian: '',
				shifouyouke: '是',
				xiangqing: '',
			},

			guimoOptions: [],
			shifouyoukeOptions: [],

			rules: {
				mingcheng: [{
					required: true,
					message: '名称不能为空',
					trigger: 'blur'
				}, ],
				tupian: [],
				guimo: [],
				zuowei: [{
					validator: validateIntNumber,
					trigger: 'blur'
				}, ],
				weizhi: [{
					required: true,
					message: '位置不能为空',
					trigger: 'blur'
				}, ],
				keyueshijian: [],
				shifouyouke: [],
				xiangqing: [],
			}
		};
	},
	props: ["parent"],
	computed: {},
	created() {},
	methods: {
		// 时间选择相关方法
		selectStartTime(time) {
			this.selectedStartTime = time;
			// 如果结束时间早于开始时间，清空结束时间
			if (this.selectedEndTime && this.compareTimes(this.selectedEndTime, time) <= 0) {
				this.selectedEndTime = '';
			}
		},

		selectEndTime(time) {
			if (this.isTimeDisabled(time)) return;
			this.selectedEndTime = time;
		},

		isTimeDisabled(time) {
			if (!this.selectedStartTime) return false;
			return this.compareTimes(time, this.selectedStartTime) <= 0;
		},

		compareTimes(time1, time2) {
			const [h1, m1] = time1.split(':').map(Number);
			const [h2, m2] = time2.split(':').map(Number);
			if (h1 !== h2) return h1 - h2;
			return m1 - m2;
		},

		formatSelectedTime() {
			if (!this.selectedStartTime && !this.selectedEndTime) {
				return '请选择开始和结束时间';
			}
			if (!this.selectedStartTime) {
				return '请选择开始时间';
			}
			if (!this.selectedEndTime) {
				return '请选择结束时间';
			}
			return `${this.selectedStartTime} - ${this.selectedEndTime}`;
		},

		confirmTimeSelection() {
			if (this.selectedStartTime && this.selectedEndTime) {
				this.ruleForm.keyueshijian = `${this.selectedStartTime} - ${this.selectedEndTime}`;
				this.showTimePicker = false;
			}
		},

		// 初始化时间选择器
		initTimePicker() {
			if (this.ruleForm.keyueshijian) {
				const times = this.ruleForm.keyueshijian.split(' - ');
				if (times.length === 2) {
					this.selectedStartTime = times[0].trim();
					this.selectedEndTime = times[1].trim();
				}
			}
		},

		// 下载
		download(file) {
			window.open(`${file}`)
		},
		// 初始化
		init(id, type) {
			if (id) {
				this.id = id;
				this.type = type;
			}
			if (this.type == 'info' || this.type == 'else') {
				this.info(id);
			} else if (this.type == 'logistics') {
				this.logistics = false;
				this.info(id);
			} else if (this.type == 'cross') {
				var obj = this.$storage.getObj('crossObj');
				for (var o in obj) {
					if (o == 'mingcheng') {
						this.ruleForm.mingcheng = obj[o];
						this.ro.mingcheng = true;
						continue;
					}
					if (o == 'tupian') {
						this.ruleForm.tupian = obj[o];
						this.ro.tupian = true;
						continue;
					}
					if (o == 'guimo') {
						this.ruleForm.guimo = obj[o];
						this.ro.guimo = true;
						continue;
					}
					if (o == 'zuowei') {
						this.ruleForm.zuowei = obj[o];
						this.ro.zuowei = true;
						continue;
					}
					if (o == 'weizhi') {
						this.ruleForm.weizhi = obj[o];
						this.ro.weizhi = true;
						continue;
					}
					if (o == 'keyueshijian') {
						this.ruleForm.keyueshijian = obj[o];
						this.ro.keyueshijian = true;
						continue;
					}
					if (o == 'shifouyouke') {
						this.ruleForm.shifouyouke = obj[o];
						this.ro.shifouyouke = true;
						continue;
					}
					if (o == 'xiangqing') {
						this.ruleForm.xiangqing = obj[o];
						this.ro.xiangqing = true;
						continue;
					}
				}

				this.ruleForm.zuowei = '20'

				this.ruleForm.shifouyouke = '是'

			}


			// 获取用户信息
			this.$http({
				url: `${this.$storage.get('sessionTable')}/session`,
				method: "get"
			}).then(({
				data
			}) => {
				if (data && data.code === 0) {

					var json = data.data;
					if (this.$storage.get("role") != "管理员") {
						this.ro.zuowei = true;
					}
				} else {
					this.$message.error(data.msg);
				}
			});

			this.guimoOptions = "大型,中型,小型".split(',')
			this.shifouyoukeOptions = "是,否".split(',')

			// 初始化时间选择器
			this.initTimePicker();
		},
		// 多级联动参数

		info(id) {
			this.$http({
				url: `zixishi/info/${id}`,
				method: "get"
			}).then(({
				data
			}) => {
				if (data && data.code === 0) {
					this.ruleForm = data.data;
					//解决前台上传图片后台不显示的问题
					let reg = new RegExp('../../../upload', 'g') //g代表全部
					this.ruleForm.xiangqing = this.ruleForm.xiangqing.replace(reg, '../../../study_room/upload');
					// 初始化时间选择器
					this.initTimePicker();
				} else {
					this.$message.error(data.msg);
				}
			});
		},


		// 提交
		onSubmit() {

			if (this.ruleForm.tupian != null) {
				this.ruleForm.tupian = this.ruleForm.tupian.replace(new RegExp(this.$base.url, "g"), "");
			}

			var objcross = this.$storage.getObj('crossObj');

			//更新跨表属性
			var crossuserid;
			var crossrefid;
			var crossoptnum;
			if (this.type == 'cross') {
				var statusColumnName = this.$storage.get('statusColumnName');
				var statusColumnValue = this.$storage.get('statusColumnValue');
				if (statusColumnName != '') {
					var obj = this.$storage.getObj('crossObj');
					if (statusColumnName && !statusColumnName.startsWith("[")) {
						for (var o in obj) {
							if (o == statusColumnName) {
								obj[o] = statusColumnValue;
							}
						}
						var table = this.$storage.get('crossTable');
						this.$http({
							url: `${table}/update`,
							method: "post",
							data: obj
						}).then(({
							data
						}) => {});
					} else {
						crossuserid = this.$storage.get('userid');
						crossrefid = obj['id'];
						crossoptnum = this.$storage.get('statusColumnName');
						crossoptnum = crossoptnum.replace(/\[/, "").replace(/\]/, "");
					}
				}
			}
			this.$refs["ruleForm"].validate(valid => {
				if (valid) {
					if (crossrefid && crossuserid) {
						this.ruleForm.crossuserid = crossuserid;
						this.ruleForm.crossrefid = crossrefid;
						let params = {
							page: 1,
							limit: 10,
							crossuserid: this.ruleForm.crossuserid,
							crossrefid: this.ruleForm.crossrefid,
						}
						this.$http({
							url: "zixishi/page",
							method: "get",
							params: params
						}).then(({
							data
						}) => {
							if (data && data.code === 0) {
								if (data.data.total >= crossoptnum) {
									this.$message.error(this.$storage.get('tips'));
									return false;
								} else {
									this.$http({
										url: `zixishi/${!this.ruleForm.id ? "save" : "update"}`,
										method: "post",
										data: this.ruleForm
									}).then(({
										data
									}) => {
										if (data && data.code === 0) {
											this.$message({
												message: "操作成功",
												type: "success",
												duration: 1500,
												onClose: () => {
													this.parent.showFlag = true;
													this.parent.addOrUpdateFlag = false;
													this.parent.zixishiCrossAddOrUpdateFlag = false;
													this.parent.search();
													this.parent.contentStyleChange();
												}
											});
										} else {
											this.$message.error(data.msg);
										}
									});

								}
							} else {}
						});
					} else {
						this.$http({
							url: `zixishi/${!this.ruleForm.id ? "save" : "update"}`,
							method: "post",
							data: this.ruleForm
						}).then(({
							data
						}) => {
							if (data && data.code === 0) {
								this.$message({
									message: "操作成功",
									type: "success",
									duration: 1500,
									onClose: () => {
										this.parent.showFlag = true;
										this.parent.addOrUpdateFlag = false;
										this.parent.zixishiCrossAddOrUpdateFlag = false;
										this.parent.search();
										this.parent.contentStyleChange();
									}
								});
							} else {
								this.$message.error(data.msg);
							}
						});
					}
				}
			});
		},
		// 获取uuid
		getUUID() {
			return new Date().getTime();
		},
		// 返回
		back() {
			this.parent.showFlag = true;
			this.parent.addOrUpdateFlag = false;
			this.parent.zixishiCrossAddOrUpdateFlag = false;
			this.parent.contentStyleChange();
		},
		tupianUploadChange(fileUrls) {
			this.ruleForm.tupian = fileUrls;
		},
	}
};
</script>

<style lang="scss" scoped>
	.amap-wrapper {
		width: 100%;
		height: 500px;
	}

	.search-box {
		position: absolute;
	}

	.el-date-editor.el-input {
		width: auto;
	}

	.add-update-preview .el-form-item>>>.el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		font-weight: 500;
		width: 100px;
		font-size: 14px;
		line-height: 40px;
		text-align: right;
	}

	.add-update-preview .el-form-item>>>.el-form-item__content {
		margin-left: 100px;
	}

	.add-update-preview .el-input>>>.el-input__inner {
		border: 2px solid #797979;
		border-radius: 0;
		padding: 0 12px;
		box-shadow: 0 0 0px rgba(64, 158, 255, .5);
		outline: none;
		color: #000;
		width: 400px;
		font-size: 14px;
		height: 40px;
	}

	.add-update-preview .el-select>>>.el-input__inner {
		border: 2px solid #797979;
		border-radius: 0;
		padding: 0 10px;
		box-shadow: 0 0 0px rgba(64, 158, 255, .5);
		outline: none;
		color: #000;
		width: 200px;
		font-size: 14px;
		height: 40px;
	}

	.add-update-preview .el-date-editor>>>.el-input__inner {
		border: 2px solid #797979;
		border-radius: 0;
		padding: 0 10px 0 30px;
		box-shadow: 0 0 0px rgba(64, 158, 255, .5);
		outline: none;
		color: #000;
		width: 200px;
		font-size: 14px;
		height: 40px;
	}

	.add-update-preview>>>.el-upload--picture-card {
		background: transparent;
		border: 0;
		border-radius: 0;
		width: auto;
		height: auto;
		line-height: initial;
		vertical-align: middle;
	}

	.add-update-preview>>>.upload .upload-img {
		border: 1px dashed #797979;
		cursor: pointer;
		border-radius: 6px;
		color: #797979;
		width: 90px;
		font-size: 32px;
		line-height: 90px;
		text-align: center;
		height: 90px;
	}

	.add-update-preview>>>.el-upload-list .el-upload-list__item {
		border: 1px dashed #797979;
		cursor: pointer;
		border-radius: 6px;
		color: #797979;
		width: 90px;
		font-size: 32px;
		line-height: 90px;
		text-align: center;
		height: 90px;
	}

	.add-update-preview>>>.el-upload .el-icon-plus {
		border: 1px dashed #797979;
		cursor: pointer;
		border-radius: 6px;
		color: #797979;
		width: 90px;
		font-size: 32px;
		line-height: 90px;
		text-align: center;
		height: 90px;
	}

	.add-update-preview .el-textarea>>>.el-textarea__inner {
		border: 2px solid #797979;
		border-radius: 0;
		padding: 12px;
		box-shadow: 0 0 0px rgba(64, 158, 255, .5);
		outline: none;
		color: #000;
		width: 400px;
		font-size: 14px;
		height: 120px;
	}

	/* 时间选择器弹窗样式 - 优化版 */
	.time-picker-dialog {
		border-radius: 12px;
		overflow: hidden;
		box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);

		.el-dialog__header {
			background: #fff;
			border-bottom: 1px solid #f0f0f0;
			padding: 15px 20px;
			border-radius: 12px 12px 0 0;

			.el-dialog__title {
				font-size: 16px;
				font-weight: 600;
				color: #333;
			}

			.el-dialog__headerbtn {
				top: 15px;
				right: 20px;
			}
		}

		.el-dialog__body {
			padding: 0;
			max-height: 70vh;
			overflow-y: auto;
		}

		.time-picker-container {
			padding: 20px;
			background: #fff;
		}

		.time-section {
			margin-bottom: 20px;

			.section-title {
				font-size: 14px;
				font-weight: 600;
				color: #333;
				margin-bottom: 12px;
				padding-left: 5px;
			}

			.time-grid {
				display: grid;
				grid-template-columns: repeat(5, 1fr);
				gap: 8px;
			}

			.time-item {
				padding: 8px 5px;
				text-align: center;
				border-radius: 6px;
				border: 1px solid #e8e8e8;
				background: #fff;
				cursor: pointer;
				transition: all 0.2s ease;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
				min-height: 50px;

				&:hover {
					border-color: #409EFF;
					background: #f5f9ff;
				}

				&.selected {
					border-color: #409EFF;
					background: #ecf5ff;
					box-shadow: 0 2px 8px rgba(64, 158, 255, 0.2);

					.time-label {
						color: #409EFF;
						font-weight: 600;
					}

					.time-period {
						color: #409EFF;
					}
				}

				&.disabled {
					cursor: not-allowed;
					background: #f5f5f5;
					border-color: #eee;
					opacity: 0.5;

					.time-label {
						color: #ccc;
					}

					.time-period {
						color: #ddd;
					}

					&:hover {
						border-color: #eee;
						background: #f5f5f5;
					}
				}

				.time-label {
					font-size: 12px;
					color: #333;
					font-weight: 500;
					line-height: 1.2;
				}

				.time-period {
					font-size: 10px;
					color: #999;
					margin-top: 2px;
					line-height: 1.2;
				}
			}
		}

		.selected-time-display {
			padding: 15px;
			margin: 20px 0;
			background: linear-gradient(135deg, #f8f9fa 0%, #f0f5ff 100%);
			border-radius: 8px;
			border: 1px solid #e8f3ff;
			text-align: center;
			box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);

			.display-title {
				font-size: 13px;
				color: #666;
				margin-bottom: 8px;
				font-weight: 500;
			}

			.display-time {
				font-size: 15px;
				color: #409EFF;
				font-weight: 600;
				min-height: 20px;
			}
		}

		.time-picker-actions {
			display: flex;
			justify-content: space-between;
			gap: 12px;
			margin-top: 20px;

			.el-button {
				flex: 1;
				height: 36px;
				font-size: 13px;
				border-radius: 6px;
				border: none;
				transition: all 0.2s ease;
			}

			.cancel-btn {
				background: #f8f9fa;
				color: #666;
				border: 1px solid #e8e8e8;

				&:hover {
					background: #e8e8e8;
					border-color: #ddd;
					color: #333;
				}
			}

			.confirm-btn {
				background: #409EFF;
				color: #fff;
				box-shadow: 0 2px 6px rgba(64, 158, 255, 0.3);

				&:hover {
					background: #3089e5;
					box-shadow: 0 3px 8px rgba(64, 158, 255, 0.4);
				}

				&:disabled {
					background: #c0c4cc;
					cursor: not-allowed;
					box-shadow: none;

					&:hover {
						background: #c0c4cc;
					}
				}
			}
		}
	}
</style>