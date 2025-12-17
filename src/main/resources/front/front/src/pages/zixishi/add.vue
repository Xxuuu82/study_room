<template>
  <div :style='{"width":"1000px","padding":"20px","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"10px auto","position":"relative","background":"#fff"}'>
    <el-form
        class="add-update-preview"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
    >
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="名称" prop="mingcheng">
        <el-input v-model="ruleForm.mingcheng" placeholder="名称" clearable></el-input>
      </el-form-item>

      <el-form-item
          :key="'tupian-upload-' + type"
          :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}'
          label="图片"
          v-if="type!='cross' || (type=='cross' && !ro.tupian)"
          prop="tupian"
      >
        <file-upload
            tip="点击上传图片"
            action="file/upload"
            :limit="3"
            :multiple="true"
            :fileUrls="ruleForm.tupian?ruleForm.tupian:''"
            @change="tupianUploadChange"
        ></file-upload>
      </el-form-item>

      <el-form-item
          :key="'tupian-preview-' + type"
          :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}'
          class="upload"
          v-else
          label="图片"
          prop="tupian"
      >
        <img
            v-if="ruleForm.tupian && ruleForm.tupian.substring && ruleForm.tupian.substring(0,4)=='http'"
            class="upload-img"
            style="margin-right:20px;"
            :src="ruleForm.tupian.split(',')[0]"
            width="100" height="100"
        />
        <template v-else>
          <img
              v-for="(item,index) in (ruleForm.tupian ? ruleForm.tupian.split(',') : [])"
              :key="'preview-'+index"
              class="upload-img"
              style="margin-right:20px;"
              :src="baseUrl + item"
              width="100" height="100"
          />
        </template>
      </el-form-item>

      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="规模" prop="guimo">
        <el-select v-model="ruleForm.guimo" placeholder="请选择规模">
          <el-option v-for="(item,index) in guimoOptions" :key="index" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="座位" prop="zuowei">
        <el-input v-model="ruleForm.zuowei" placeholder="座位" clearable readonly></el-input>
      </el-form-item>

      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="位置" prop="weizhi">
        <el-input v-model="ruleForm.weizhi" placeholder="位置" clearable></el-input>
      </el-form-item>

      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="设施" prop="sheshi">
        <el-input v-model="ruleForm.sheshi" placeholder="设施" clearable></el-input>
      </el-form-item>

      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="可约时间" prop="keyueshijian">
        <el-input v-model="ruleForm.keyueshijian" placeholder="可约时间" clearable></el-input>
      </el-form-item>

      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="是否有课" prop="shifouyouke">
        <el-select v-model="ruleForm.shifouyouke" placeholder="请选择是否有课">
          <el-option v-for="(item,index) in shifouyoukeOptions" :key="index" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="详情" prop="xiangqing">
        <editor
            :style='{"minHeight":"400px","padding":"0","boxShadow":"0 0 6px #2e61e1","margin":"0","borderColor":"#ccc","backgroundColor":"#fff","borderRadius":"0","color":"#000","borderWidth":"0","width":"100%","borderStyle":"solid","height":"auto"}'
            v-model="ruleForm.xiangqing"
            class="editor"
            action="file/upload">
        </editor>
      </el-form-item>

      <!-- 跨表预约：今/明 + 固定时间下拉（30 分钟步进），起始首项动态取自浏览器时间向上取至下一个 30 分钟 -->
      <el-form-item v-if="type==='cross'" label="预约时间" :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"block"}'>
        <div class="time-select-container">
          <el-date-picker
              v-model="startDate"
              type="date"
              placeholder="选择日期"
              :disabled-date="disabledAvailabilityDate"
              value-format="yyyy-MM-dd"
              @change="onDateChange"
          />
          <el-time-select
              v-model="startTime"
              placeholder="起始时间"
              :picker-options="timeStartOptions"
          />
          <el-time-select
              v-model="endTime"
              placeholder="结束时间"
              :picker-options="timeEndOptions"
          />
        </div>
        <div class="time-tip">
          * 粒度为 30 分钟。若选择今天，可选时间已根据当前时间 ({{_currentTimeStr()}}) 自动延后。
        </div>
      </el-form-item>

      <el-form-item :style='{"padding":"0","margin":"0"}'>
        <el-button type="primary" @click="onSubmit" :style='{"border":"0","cursor":"pointer","padding":"0","margin":"0 20px 0 0","outline":"none","borderRadius":"4px","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'>提交</el-button>
        <el-button @click="back" :style='{"border":"0px solid rgba(64, 158, 255, 1)","cursor":"pointer","padding":"0","margin":"0","outline":"none","borderRadius":"4px","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'>返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: '',
      baseUrl: '',
      ro:{
        mingcheng : false,
        tupian : false,
        guimo : false,
        zuowei : false,
        weizhi : false,
        sheshi : false,
        keyueshijian : false,
        shifouyouke : false,
        xiangqing : false,
      },
      type: '',
      userTableName: localStorage.getItem('UserTableName') || 'xuesheng',
      ruleForm: {
        mingcheng: '',
        tupian: '',
        guimo: '',
        zuowei: '20',
        weizhi: '',
        sheshi: '',
        keyueshijian: '',
        shifouyouke: '是',
        xiangqing: '',
      },
      guimoOptions: [],
      shifouyoukeOptions: [],
      rules: {
        mingcheng: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
        zuowei: [{ validator: this.$validate ? this.$validate.isIntNumer : () => true, trigger: 'blur' }],
        weizhi: [{ required: true, message: '位置不能为空', trigger: 'blur' }],
      },

      // appointment controls
      startDate: new Date().toISOString().slice(0,10),
      startTime: '', // 'HH:mm'
      endTime: '',

      // reactive earliest start computed from browser time; refresh on focus or date change
      earliestStartForPicker: '',

      sessionUser: {}
    };
  },
  computed: {
    timeStartOptions() {
      let openStart = '08:00';
      const lastStart = '21:30';

      // 如果选择的是今天
      if (this.startDate === this._todayStr()) {
        const now = new Date();
        const roundedNow = this._roundUpTo30(now);
        const minAvailable = this._timeStrFromDate(roundedNow);

        // 如果当前时间已经过了 21:30，则今天无法预约
        if (this._timeStrToMinutes(minAvailable) > this._timeStrToMinutes(lastStart)) {
          openStart = '23:59'; // 使得列表为空
        } else {
          // 取 08:00 和当前取整时间中的较大者
          openStart = this._timeStrToMinutes(minAvailable) > this._timeStrToMinutes(openStart)
              ? minAvailable : openStart;
        }
      }
      return { start: openStart, step: '00:30', end: lastStart };
    },

    timeEndOptions() {
      const openEnd = '22:00';
      // 结束时间至少比开始时间晚 30 分钟
      const startLimit = this.startTime ? this._addMinutesToTimeStr(this.startTime, 30) : '08:30';
      return {
        start: startLimit,
        step: '00:30',
        end: openEnd,
        minTime: this.startTime
      };
    }
  },

    timeEndOptions() {
      const openEnd = '22:00';
      if (this.startTime) {
        const s = this._addMinutesToTimeStr(this.startTime, 30);
        return { start: s, step: '00:30', end: openEnd };
      } else {
        // use earliestStartForPicker as basis
        if (this.startDate === this._todayStr()) {
          const base = this.earliestStartForPicker && this.earliestStartForPicker.length ? this.earliestStartForPicker : '08:00';
          const s = this._addMinutesToTimeStr(base, 30);
          return { start: s, step: '00:30', end: openEnd };
        } else {
          return { start: '08:30', step: '00:30', end: openEnd };
        }
      }
    },


  created() {
    const type = this.$route.query.type ? this.$route.query.type : '';
    this.init(type);
    this.baseUrl = this.$config ? this.$config.baseUrl : '';
    this.refreshEarliestStart(); // initialize earliest
    // session user
    this.$http.get(this.userTableName + '/session', { emulateJSON: true }).then(res => {
      if (res.data && res.data.code == 0) {
        this.sessionUser = res.data.data || {};
      }
    }).catch(()=>{});
    // prefill from availabilitySelection if exists
    const pickRaw = localStorage.getItem('availabilitySelection');
    if (pickRaw) {
      try {
        const pick = JSON.parse(pickRaw);
        if (pick && pick.start && pick.end) {
          const s = pick.start.split(' ');
          const e = pick.end.split(' ');
          this.startDate = s[0];
          this.startTime = s[1] ? s[1].slice(0,5) : '';
          this.endTime = e[1] ? e[1].slice(0,5) : '';
        }
      } catch (e) {}
    }
  },
  watch: {
    startDate() {
      // refresh earliest when date changes
      this.refreshEarliestStart();
      // reset endTime so user reselect if needed
      this.endTime = '';
      // if startTime earlier than earliestStart, adjust
      if (this.startTime && this.startDate === this._todayStr()) {
        const earliestMin = this._timeStrToMinutes(this.earliestStartForPicker || '08:00');
        if (this._timeStrToMinutes(this.startTime) < earliestMin) {
          this.startTime = this.earliestStartForPicker;
        }
      }
    }
  },
  methods: {
    init(type) {
      this.type = type;
      if (type == 'cross') {
        const obj = JSON.parse(localStorage.getItem('crossObj') || '{}');
        if (obj.mingcheng) { this.ruleForm.mingcheng = obj.mingcheng; this.ro.mingcheng = true; }
        if (obj.tupian) { this.ruleForm.tupian = (obj.tupian && obj.tupian.split) ? obj.tupian.split(',')[0] : obj.tupian; this.ro.tupian = true; }
        if (obj.guimo) { this.ruleForm.guimo = obj.guimo; this.ro.guimo = true; }
        if (obj.zuowei) { this.ruleForm.zuowei = obj.zuowei; this.ro.zuowei = true; }
        if (obj.weizhi) { this.ruleForm.weizhi = obj.weizhi; this.ro.weizhi = true; }
        if (obj.sheshi) { this.ruleForm.sheshi = obj.sheshi; this.ro.sheshi = true; }
        if (obj.keyueshijian) { this.ruleForm.keyueshijian = obj.keyueshijian; this.ro.keyueshijian = true; }
        if (obj.shifouyouke) { this.ruleForm.shifouyouke = obj.shifouyouke; this.ro.shifouyouke = true; }
        if (obj.xiangqing) { this.ruleForm.xiangqing = obj.xiangqing; this.ro.xiangqing = true; }
      }
      this.guimoOptions = "大型,中型,小型".split(',');
      this.shifouyoukeOptions = "是,否".split(',');
    },

    // refresh earliest start from browser (dynamic)
    refreshEarliestStart() {
      // only compute when startDate is today; else set empty
      if (this.startDate === this._todayStr()) {
        const r = this._roundUpTo30(new Date());
        this.earliestStartForPicker = this._timeStrFromDate(r);
      } else {
        this.earliestStartForPicker = '';
      }
    },

    _todayStr() {
      const d = new Date();
      return d.getFullYear() + '-' + ('0'+(d.getMonth()+1)).slice(-2) + '-' + ('0'+d.getDate()).slice(-2);
    },

    _roundUpTo30(d) {
      const m = d.getMinutes();
      const newDate = new Date(d);
      if (m > 0 && m <= 30) {
        newDate.setMinutes(30);
      } else if (m > 30) {
        newDate.setHours(d.getHours() + 1);
        newDate.setMinutes(0);
      }
      newDate.setSeconds(0);
      newDate.setMilliseconds(0);
      return newDate;
    },

    _currentTimeStr() {
      const n = new Date();
      return ('0'+n.getHours()).slice(-2) + ":" + ('0'+n.getMinutes()).slice(-2);
    },

    _timeStrFromDate(d) {
      const hh = ('0'+d.getHours()).slice(-2);
      const mm = ('0'+d.getMinutes()).slice(-2);
      return `${hh}:${mm}`;
    },

    // 切换日期时，重置时间防止冲突
    onDateChange() {
      this.startTime = '';
      this.endTime = '';
      this.$nextTick(() => {
        this.setDefaultStartTime();
      });
    },

    setDefaultStartTime() {
      if (this.type !== 'cross') return;
      const earliest = this._earliestStartForDate(this.startDate);
      this.startTime = earliest;
      this.endTime = this._addMinutesToTimeStr(this.startTime, 30);
    },

    _addMinutesToTimeStr(timeStr, addMin) {
      if (!timeStr) return timeStr;
      const parts = timeStr.split(':');
      const d = new Date();
      d.setHours(parseInt(parts[0],10), parseInt(parts[1],10), 0, 0);
      d.setMinutes(d.getMinutes() + addMin);
      return ('0'+d.getHours()).slice(-2) + ':' + ('0'+d.getMinutes()).slice(-2);
    },

    _timeStrToMinutes(timeStr) {
      const p = timeStr.split(':');
      return parseInt(p[0],10)*60 + parseInt(p[1],10);
    },

    validateBeforeSubmit(startDateTime, endDateTime) {
      const gran = 30;
      const granMs = gran * 60 * 1000;

      if (endDateTime <= startDateTime) {
        this.$message.error('结束时间必须晚于开始时间');
        return false;
      }
      if ((startDateTime.getSeconds() !== 0) || (startDateTime.getMilliseconds() !== 0) ||
          (endDateTime.getSeconds() !== 0) || (endDateTime.getMilliseconds() !== 0)) {
        this.$message.error('请把秒和毫秒设置为 0');
        return false;
      }
      if (startDateTime.getMinutes() % gran !== 0 || endDateTime.getMinutes() % gran !== 0) {
        this.$message.error('开始/结束时间必须为 30 分钟的倍数（例如 08:00 或 08:30）');
        return false;
      }
      const now = new Date();
      if (startDateTime.toISOString().slice(0, 10) === now.toISOString().slice(0, 10)) {
        const roundedNowMs = Math.ceil(now.getTime() / granMs) * granMs;
        if (startDateTime.getTime() < roundedNowMs) {
          this.$message.error(`当天预约必须在当前时间之后（按30分钟取整），`);
          return false;
        }
      }
      if ((endDateTime.getTime() - startDateTime.getTime()) % granMs !== 0) {
        this.$message.error('预约时长必须为 30 分钟的倍数');
        return false;
      }
      return true;
    },

    async onSubmit() {
      this.$refs["ruleForm"].validate(async valid => {
        if (!valid) return;
        if (this.type === 'cross') {
          if (!this.startDate || !this.startTime || !this.endTime) {
            this.$message.error('请先选择预约日期与时间');
            return;
          }
          // ensure earliest computed fresh
          this.refreshEarliestStart();

          const startDt = new Date(`${this.startDate} ${this.startTime}:00`);
          const endDt = new Date(`${this.startDate} ${this.endTime}:00`);
          if (!this.validateBeforeSubmit(startDt, endDt)) return;

          const crossObj = JSON.parse(localStorage.getItem('crossObj') || '{}');
          const payload = {
            zixishiid: crossObj.id || crossObj.zixishiId || null,
            zuowei: parseInt(this.ruleForm.zuowei || crossObj.zuowei || 0, 10),
            xuehao: this.sessionUser && (this.sessionUser.username || this.sessionUser.xuehao) ? (this.sessionUser.username || this.sessionUser.xuehao) : (localStorage.getItem('username') || ''),
            xingming: this.sessionUser && (this.sessionUser.name || this.sessionUser.xingming) ? (this.sessionUser.name || this.sessionUser.xingming) : '',
            shouji: this.sessionUser && (this.sessionUser.shouji || this.sessionUser.mobile) ? (this.sessionUser.shouji || this.sessionUser.mobile) : '',
            yuyueStart: this._formatDate(startDt),
            yuyueEnd: this._formatDate(endDt),
            mingcheng: this.ruleForm.mingcheng || crossObj.mingcheng || ''
          };

          try {
            const res = await this.$http.post('/yuyuexinxi/submit', payload);
            if (res && res.data && res.data.code === 0) {
              this.$message({ message: '预约成功', type: 'success', duration: 1500, onClose: ()=> { this.$router.go(-1); }});
            } else {
              this.$message.error(res && res.data && res.data.msg ? res.data.msg : '预约失败');
            }
          } catch (err) {
            console.error('submit error', err);
            this.$message.error('预约请求失败');
          }
          return;
        }

        // non-cross: create study room
        try {
          const res = await this.$http.post('zixishi/add', this.ruleForm);
          if (res && res.data && res.data.code == 0) {
            this.$message({ message: '操作成功', type: 'success', duration: 1500, onClose: () => { this.$router.go(-1); }});
          } else {
            this.$message.error(res && res.data && res.data.msg ? res.data.msg : '操作失败');
          }
        } catch (e) {
          this.$message.error('请求失败');
        }
      });
    },

    _formatDate(d) {
      const Y = d.getFullYear();
      const M = ('0'+(d.getMonth()+1)).slice(-2);
      const D = ('0'+d.getDate()).slice(-2);
      const h = ('0'+d.getHours()).slice(-2);
      const m = ('0'+d.getMinutes()).slice(-2);
      const s = ('0'+d.getSeconds()).slice(-2);
      return `${Y}-${M}-${D} ${h}:${m}:${s}`;
    },

    disabledAvailabilityDate(date) {
      if (!date) return true;
      const d = new Date(date);
      const today = new Date();
      today.setHours(0,0,0,0);
      const max = new Date(today);
      max.setDate(today.getDate() + 1);
      max.setHours(23,59,59,999);
      return d < today || d > max;
    },

    tupianUploadChange(fileUrls) {
      this.ruleForm.tupian = fileUrls.replace(new RegExp(this.$config.baseUrl, "g"), "");
    },

    getUUID() { return new Date().getTime(); },
    back() { this.$router.go(-1); }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.el-date-editor.el-input { width: auto; }

.add-update-preview .el-form-item >>> .el-form-item__label {
  padding: 0 10px 0 0;
  color: #666;
  font-weight: 500;
  width: 120px;
  font-size: 14px;
  line-height: 40px;
  text-align: center;
}

.add-update-preview .el-form-item >>> .el-form-item__content { margin-left: 120px; }

.add-update-preview .el-input >>> .el-input__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 0 12px;
  outline: none;
  color: #000;
  background: #BFD2FC;
  width: 100%;
  font-size: 14px;
  height: 40px;
}

.add-update-preview .el-select >>> .el-input__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 0 10px;
  outline: none;
  color: #000;
  background: #BFD2FC;
  width: 100%;
  font-size: 14px;
  height: 40px;
}

.add-update-preview .el-date-editor >>> .el-input__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 0 10px 0 30px;
  outline: none;
  color: #000;
  background: #BFD2FC;
  width: 100%;
  font-size: 14px;
  height: 40px;
}

/* upload visuals */
.add-update-preview >>> .upload .upload-img {
  border: 1px dashed rgba(64, 158, 255, 1);
  cursor: pointer;
  border-radius: 6px;
  width: 100px;
  height: 100px;
}

/* textarea */
.add-update-preview .el-textarea >>> .el-textarea__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 12px;
  outline: none;
  color: #000;
  background: #BFD2FC;
  width: 100%;
  font-size: 14px;
  height: 120px;
}

/* 强制修改下拉组件的背景和边框，使其符合你的 UI */
.add-update-preview .el-time-select >>> .el-input__inner {
  border: 2px solid #2e61e1 !important;
  background: #BFD2FC !important;
  color: #000 !important;
  width: 150px !important;
}
</style>