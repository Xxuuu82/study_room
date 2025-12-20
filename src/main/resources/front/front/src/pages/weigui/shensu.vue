<template>
  <div
      :style="{
      width: '100%',
      maxWidth: '1000px',
      padding: '20px',
      boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.302)',
      margin: '10px auto',
      position: 'relative',
      background: '#fff',
      borderRadius: '8px',
      boxSizing: 'border-box'
    }"
  >
    <!-- 面包屑导航 -->
    <div :style="{ marginBottom: '20px', paddingBottom: '10px', borderBottom: '1px solid #e4e7ed' }">
      <el-breadcrumb :separator="'/'" :style='{"fontSize":"14px","lineHeight":"1","color":"#333"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item @click="$router.push('/index/weigui')">违规记录</el-breadcrumb-item>
        <el-breadcrumb-item>违规申诉</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 违规记录基本信息展示区域 -->
    <div :style="{ width: '100%', maxWidth: '800px', padding: '20px', margin: '0 auto 20px', background: '#f8f9fa', borderRadius: '8px', boxShadow: '0 1px 3px rgba(0, 0, 0, .05)' }">
      <h3 :style="{ margin: '0 0 15px 0', fontSize: '16px', color: '#333', fontWeight: '500' }">违规记录信息</h3>

      <div class="info-item" :style="{ padding: '10px 0', display: 'flex', borderBottom: '1px dashed #e4e7ed' }">
        <div :style="{ width: '120px', textAlign: 'right', paddingRight: '15px', color: '#666', fontSize: '14px' }">违规单号：</div>
        <div :style="{ flex: 1, color: '#333', fontSize: '14px' }">{{ ruleForm.weiguidanhao || '-' }}</div>
      </div>

      <div class="info-item" :style="{ padding: '10px 0', display: 'flex', borderBottom: '1px dashed #e4e7ed' }">
        <div :style="{ width: '120px', textAlign: 'right', paddingRight: '15px', color: '#666', fontSize: '14px' }">违规类型：</div>
        <div :style="{ flex: 1, color: '#333', fontSize: '14px' }">{{ ruleForm.weiguileixing || '-' }}</div>
      </div>

      <div class="info-item" :style="{ padding: '10px 0', display: 'flex', borderBottom: '1px dashed #e4e7ed' }">
        <div :style="{ width: '120px', textAlign: 'right', paddingRight: '15px', color: '#666', fontSize: '14px' }">违规时间：</div>
        <div :style="{ flex: 1, color: '#333', fontSize: '14px' }">{{ ruleForm.weiguiTime || '-' }}</div>
      </div>

      <div class="info-item" :style="{ padding: '10px 0', display: 'flex', borderBottom: '1px dashed #e4e7ed' }">
        <div :style="{ width: '120px', textAlign: 'right', paddingRight: '15px', color: '#666', fontSize: '14px' }">违规原因：</div>
        <div :style="{ flex: 1, color: '#333', fontSize: '14px', whiteSpace: 'pre-wrap' }">{{ ruleForm.weiguiReason || '-' }}</div>
      </div>
    </div>

    <!-- 申诉表单区域 -->
    <el-form class="add-update-preview" ref="ruleForm" :model="ruleForm" :rules="rules" label-width="120px">
      <el-form-item label="申诉单号" prop="shensudanhao" :style="{ width: '100%', maxWidth: '800px', padding: '10px', margin: '0 auto 10px', display: 'block', boxSizing: 'border-box' }">
        <el-input v-model="ruleForm.shensudanhao" placeholder="申诉单号" readonly></el-input>
      </el-form-item>

      <el-form-item label="申诉人学号" prop="xuehao" :style="{ width: '100%', maxWidth: '800px', padding: '10px', margin: '0 auto 10px', display: 'block', boxSizing: 'border-box' }">
        <el-input v-model="ruleForm.xuehao" placeholder="申诉人学号" clearable :readonly="ro.xuehao"></el-input>
      </el-form-item>

      <el-form-item label="申诉人姓名" prop="xingming" :style="{ width: '100%', maxWidth: '800px', padding: '10px', margin: '0 auto 10px', display: 'block', boxSizing: 'border-box' }">
        <el-input v-model="ruleForm.xingming" placeholder="申诉人姓名" clearable :readonly="ro.xingming"></el-input>
      </el-form-item>

      <el-form-item label="申诉人手机" prop="shouji" :style="{ width: '100%', maxWidth: '800px', padding: '10px', margin: '0 auto 10px', display: 'block', boxSizing: 'border-box' }">
        <el-input v-model="ruleForm.shouji" placeholder="申诉人手机" clearable :readonly="ro.shouji"></el-input>
      </el-form-item>

      <el-form-item label="申诉时间" prop="shensuTime" :style="{ width: '100%', maxWidth: '800px', padding: '10px', margin: '0 auto 10px', display: 'block', boxSizing: 'border-box' }">
        <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" v-model="ruleForm.shensuTime" type="datetime" placeholder="选择申诉时间" style="width: 100%;" default-time="12:00:00"></el-date-picker>
      </el-form-item>

      <el-form-item label="申诉原因" prop="shensuReason" :style="{ width: '100%', maxWidth: '800px', padding: '10px', margin: '0 auto 10px', display: 'block', boxSizing: 'border-box' }">
        <el-input v-model="ruleForm.shensuReason" type="textarea" placeholder="请详细描述申诉原因" clearable :rows="8" style="width: 100%;"></el-input>
      </el-form-item>

      <el-form-item label="申诉诉求" prop="shensuSuqiu" :style="{ width: '100%', maxWidth: '800px', padding: '10px', margin: '0 auto 10px', display: 'block', boxSizing: 'border-box' }">
        <el-input v-model="ruleForm.shensuSuqiu" type="textarea" placeholder="请说明您的申诉诉求（如：撤销违规记录、减轻处罚等）" clearable :rows="4" style="width: 100%;"></el-input>
      </el-form-item>

      <el-form-item :style="{ padding: '20px 0 0', margin: '0', textAlign: 'center', width: '100%' }">
        <el-button :loading="submitting" type="primary" @click="onSubmit" :style="{ margin: '0 10px 10px', padding: '0 24px', height: '40px', borderRadius: '4px', fontSize: '14px', background: '#2e61e1', color: '#fff', border: 'none' }">提交申诉</el-button>
        <el-button @click="back" :style="{ margin: '0 10px 10px', padding: '0 24px', height: '40px', borderRadius: '4px', fontSize: '14px', background: '#f5f7fa', color: '#666', border: '1px solid #e4e7ed' }">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      submitting: false,
      id: "",
      baseUrl: "",
      ro: {
        shensudanhao: false,
        weiguidanhao: true,
        weiguileixing: true,
        weiguiTime: true,
        weiguiReason: true,
        xuehao: false,
        xingming: false,
        shouji: false,
        shensuTime: false,
        shensuReason: false,
        shensuSuqiu: false,
      },
      userTableName: localStorage.getItem("UserTableName"),
      ruleForm: {
        shensudanhao: "SS" + new Date().getTime(),
        weiguidanhao: "",
        weiguileixing: "",
        weiguiTime: "",
        weiguiReason: "",
        xuehao: "",
        xingming: "",
        shouji: "",
        shensuTime: "",
        shensuReason: "",
        shensuSuqiu: "",
        shensuStatus: "申诉中",
        shensuHandleStatus: "未处理"
      },
      rules: {
        shensudanhao: [],
        weiguidanhao: [{ required: true, message: "违规单号不能为空", trigger: "blur" }],
        xuehao: [{ required: true, message: "申诉人学号不能为空", trigger: "blur" }],
        xingming: [{ required: true, message: "申诉人姓名不能为空", trigger: "blur" }],
        shouji: [{ validator: (r, v, c) => { if (!v) c(); else { const reg = /^1[3-9]\d{9}$/; if (!reg.test(v)) c(new Error('请输入正确的手机号')); else c(); } }, trigger: "blur" }],
        shensuTime: [{ required: true, message: "请选择申诉时间", trigger: "change" }],
        shensuReason: [{ required: true, message: "请详细描述申诉原因", trigger: "blur" }, { min: 20, message: "申诉原因至少填写20个字", trigger: "blur" }],
        shensuSuqiu: [{ required: true, message: "请说明您的申诉诉求", trigger: "blur" }]
      }
    };
  },
  created() {
    this.baseUrl = this.$config?.baseUrl || '';
    this.ruleForm.shensuTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
    this.initShensuData();
    this.initUserInfo();
  },
  methods: {
    getAuthHeaders() {
      const token = localStorage.getItem('Token') || localStorage.getItem('token') || localStorage.getItem('Authorization') || '';
      const headers = {};
      if (token) {
        headers['Authorization'] = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
        headers['Token'] = token;
      }
      return headers;
    },

    async initShensuData() {
      const routeId = this.$route && (this.$route.query.id || this.$route.params.id);
      if (routeId) { await this.fetchWeiguiDetail(routeId); return; }

      const stored = localStorage.getItem("currentWeiguiObj");
      if (stored) {
        try {
          const obj = JSON.parse(stored);
          const id = obj.id || obj.ID;
          if (id) { await this.fetchWeiguiDetail(id); return; }
          this.fillRuleFormFromObj(obj);
          return;
        } catch (e) {
          console.warn('currentWeiguiObj parse error', e);
        }
      }

      this.$message.warning("未获取到违规记录信息，请选择要申诉的违规记录");
      setTimeout(() => this.$router.push('/index/weigui'), 1200);
    },

    async fetchWeiguiDetail(id) {
      try {
        console.log('[DEBUG] 正在请求 /api/weigui/detail id=', id);
        const resp = await axios.get('/api/weigui/detail', {
          params: { id },
          headers: this.getAuthHeaders(),
          withCredentials: true
        });
        console.log('[DEBUG] /api/weigui/detail 返回:', resp && resp.data);

        const data = resp && resp.data;
        if (data && (data.code === 401 || resp.status === 401)) {
          this.$message.warning('请先登录，再查看违规详情');
          setTimeout(() => { this.$router.push('/login'); }, 800);
          return;
        }

        let record = null;
        if (data) {
          if (data.code === 0 && data.data) record = data.data;
          else if (data.data && data.data.data) record = data.data.data;
          else if (data.record) record = data.record;
          else record = data;
        }

        if (!record || Object.keys(record).length === 0) {
          console.warn('fetchWeiguiDetail: 未检测到 record，在 localStorage 回退');
          const stored = localStorage.getItem("currentWeiguiObj");
          if (stored) { try { this.fillRuleFormFromObj(JSON.parse(stored)); } catch (e) { console.warn('回退 localStorage parse error', e); } return; }
          this.$message.error('未能获取到违规详情');
          setTimeout(() => this.$router.push('/index/weigui'), 1000);
          return;
        }

        this.fillRuleFormFromObj(record);
        try { localStorage.setItem("currentWeiguiObj", JSON.stringify(record)); }
        catch (e) { console.warn('localStorage set currentWeiguiObj failed', e); }

      } catch (err) {
        console.error('fetchWeiguiDetail 出错：', err);
        if (err.response && err.response.status === 401) {
          this.$message.warning('请先登录');
          setTimeout(() => this.$router.push('/login'), 800);
          return;
        }
        const stored = localStorage.getItem("currentWeiguiObj");
        if (stored) { try { this.fillRuleFormFromObj(JSON.parse(stored)); } catch (e) { console.warn('回退 localStorage parse error', e); } return; }
        this.$message.error('无法获取违规详情，请稍后重试');
        setTimeout(() => this.$router.push('/index/weigui'), 1200);
      }
    },

    fillRuleFormFromObj(r) {
      if (!r) return;
      const getField = (...names) => {
        for (const n of names) if (r[n] !== undefined && r[n] !== null && r[n] !== '') return r[n];
        return null;
      };
      this.ruleForm.weiguidanhao = getField('weiguiDanhao','weiguidanhao','weigui_danhao') || this.ruleForm.weiguidanhao;
      this.ruleForm.weiguileixing = getField('weiguileixingName','weiguileixing','weiguiLeixing') || '';
      const rawTime = getField('weiguiShijian','weigui_shijian','addtime','addTime');
      this.ruleForm.weiguiTime = this.formatAnyTime(rawTime) || '';
      this.ruleForm.weiguiReason = getField('weiguiBeizhu','weigui_beizhu','otherWeiguiDesc') || '';
      const recXuehao = getField('xuehao','XUEHAO');
      const recXingming = getField('xingming','XINGMING','name');
      if (recXuehao) { this.ruleForm.xuehao = recXuehao; this.ro.xuehao = true; }
      if (recXingming) { this.ruleForm.xingming = recXingming; this.ro.xingming = true; }
    },

    initUserInfo() {
      if (!this.userTableName) {
        if (!this.ruleForm.xuehao) this.ruleForm.xuehao = "20230001";
        if (!this.ruleForm.xingming) this.ruleForm.xingming = "张三";
        if (!this.ruleForm.shouji) this.ruleForm.shouji = "13800138000";
        this.ro.xuehao = true; this.ro.xingming = true; this.ro.shouji = true;
        return;
      }
      if (this.$http) {
        this.$http.get(this.userTableName + "/session", { emulateJSON: true })
            .then((res) => {
              if (res.data && (res.data.code === 0 || res.data.success === true)) {
                const userInfo = res.data.data || {};
                if (userInfo.xuehao) { this.ruleForm.xuehao = userInfo.xuehao; this.ro.xuehao = true; }
                if (userInfo.xingming) { this.ruleForm.xingming = userInfo.xingming; this.ro.xingming = true; }
                if (userInfo.shouji) { this.ruleForm.shouji = userInfo.shouji; this.ro.shouji = true; }
              }
            })
            .catch(err => { console.warn("获取用户信息失败：", err); });
      }
    },

    // ========== 提交到后端 ==========
    async onSubmit() {
      this.$refs["ruleForm"].validate(async (valid) => {
        if (!valid) return;
        if (this.submitting) return;
        this.submitting = true;

        let current = {};
        try { current = JSON.parse(localStorage.getItem("currentWeiguiObj") || "{}"); } catch (e) { console.warn('parse currentWeiguiObj failed', e); }

        // 注意：数据库表没有 weiguiDanhao 字段，所以不提交该字段。
        // 数据库存在 shensuTupian, shensuNeirong, shensuShijian 等字段。
        // 这里把前端的 shensuSuqiu (申诉诉求) 映射到 shensuTupian（如果你需要映射到其它字段，请调整）
        const payload = {
          shensuDanhao: this.ruleForm.shensudanhao,
          xuehao: this.ruleForm.xuehao,
          xingming: this.ruleForm.xingming,
          shensuNeirong: this.ruleForm.shensuReason,
          shensuTupian: this.ruleForm.shensuSuqiu || '',
          shensuShijian: this.ruleForm.shensuTime || new Date().toISOString().slice(0,19).replace('T',' '),
          weiguiRecordId: current.id || null
        };

        console.log('[DEBUG] 发送申诉 payload=', payload);

        try {
          const resp = await axios.post('/api/shensu/add', payload, {
            headers: { 'Content-Type': 'application/json;charset=UTF-8', ...this.getAuthHeaders() },
            withCredentials: true
          });

          console.log('[DEBUG] /api/shensu/add 返回：', resp && resp.data);

          const data = resp && resp.data;
          const ok = (data && (data.code === 0 || data.code === 200 || data.success === true)) || resp.status === 200;

          if (ok) {
            this.$message({
              message: "申诉提交成功，等待管理员审核处理",
              type: "success",
              duration: 2000,
              onClose: () => { this.$router.push('/index/weigui'); }
            });
            try {
              const stored = JSON.parse(localStorage.getItem("currentWeiguiObj") || "{}");
              if (stored) { stored.shensuStatus = "已申诉"; stored.shensuHandleStatus = "处理中"; localStorage.setItem("currentWeiguiObj", JSON.stringify(stored)); }
            } catch (e) { console.warn('update localStorage currentWeiguiObj failed', e); }
          } else {
            const msg = (data && (data.msg || data.message)) || '申诉提交失败';
            this.$message.error(msg);
          }
        } catch (err) {
          console.error('申诉提交请求出错：', err);
          if (err.response && err.response.status === 401) {
            this.$message.warning('请先登录再提交申诉');
            setTimeout(()=> this.$router.push('/login'), 800);
          } else {
            this.$message.error('网络或服务器错误，提交失败');
          }
        } finally {
          this.submitting = false;
        }
      });
    },

    getUUID() { return "SS" + new Date().getTime(); },

    back() { this.$router.push('/index/weigui'); },

    formatAnyTime(val) {
      if (!val && val !== 0) return '';
      if (Array.isArray(val)) { const arr = val.map(v=>String(v).padStart(2,'0')); if (arr.length>=6) return `${arr[0]}-${arr[1]}-${arr[2]} ${arr[3]}:${arr[4]}:${arr[5]}`; return arr.join('-'); }
      if (typeof val === 'number') { if (String(val).length===10) return new Date(val*1000).toISOString().replace('T',' ').slice(0,19); return new Date(val).toISOString().replace('T',' ').slice(0,19); }
      if (val instanceof Object && !(val instanceof Date)) {
        try {
          const asStr = String(val);
          if (asStr && asStr.indexOf('[')===0) return asStr;
          return asStr;
        } catch(e) {
          // 避免 ESLint no-empty，记录并返回原始值字符串
          console.warn('formatAnyTime parse error', e);
          return String(val);
        }
      }
      try { const s = String(val).trim(); if (/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}/.test(s)) return s.replace('T',' ').slice(0,19); return s; } catch(e){ return String(val); }
    },

    parseDateStr(str) {
      if (!str && str !== 0) return null;
      if (str instanceof Date) return str;
      if (typeof str === 'number') return new Date(str);
      if (Array.isArray(str)) { const arr = str.map(n=>Number(n)); if (arr.length>=6) return new Date(arr[0], arr[1]-1, arr[2], arr[3], arr[4], arr[5]); return null; }
      let s = String(str).trim();
      if (/^\d{10}$/.test(s)) return new Date(Number(s)*1000);
      if (/^\d{13}$/.test(s)) return new Date(Number(s));
      if (/^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/.test(s)) s = s.replace(' ', 'T');
      if (/^\d{4}\/\d{2}\/\d{2} \d{2}:\d{2}:\d{2}$/.test(s)) s = s.replace(' ', 'T').replace(/\//g, '-');
      const d = new Date(s); if (isNaN(d.getTime())) return null; return d;
    },

    formatEndTime(endDate) {
      const d = endDate instanceof Date ? endDate : new Date(endDate); if (isNaN(d.getTime())) return ''; const pad = n=> (n<10? '0'+n : ''+n); return `${d.getFullYear()}-${pad(d.getMonth()+1)}-${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
    }
  }
};
</script>

<style scoped>
/* 样式调整：输入框白底、细边框，更接近图二样式 */
@media (max-width: 768px) {
  .add-update-preview .el-form-item >>> .el-form-item__label { width: 100% !important; text-align: left !important; line-height: 24px !important; padding: 0 0 8px 0 !important; }
  .add-update-preview .el-form-item >>> .el-form-item__content { margin-left: 0 !important; }
  .info-item { flex-direction: column !important; }
  .info-item > div:first-child { width: 100% !important; text-align: left !important; padding-right: 0 !important; margin-bottom: 5px !important; }
}
.add-update-preview .el-input >>> .el-input__inner,
.add-update-preview .el-select >>> .el-input__inner,
.add-update-preview .el-date-editor >>> .el-input__inner {
  border: 1px solid #e6e6e6;
  border-radius: 4px;
  padding: 0 12px;
  outline: none;
  color: #333;
  background: #fff;
  width: 100%;
  font-size: 14px;
  height: 40px;
  box-sizing: border-box;
}
.add-update-preview .el-textarea >>> .el-textarea__inner {
  border: 1px solid #e6e6e6;
  border-radius: 4px;
  padding: 12px;
  outline: none;
  color: #333;
  background: #fff;
  width: 100%;
  font-size: 14px;
  min-height: 100px;
  box-sizing: border-box;
}
</style>