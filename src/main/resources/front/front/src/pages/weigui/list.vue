<template>
  <div style="width: 100%; min-height: 100vh; padding: 0 10px; boxSizing: border-box;">
    <!-- 固定的黑名单状态Header -->
    <div style="
      position: sticky;
      top: 0;
      z-index: 100;
      width: 100%;
      padding: 15px 20px;
      margin: 10px auto 20px;
      border-radius: 27px;
      background: #f8f9fa;
      box-shadow: 0px 4px 10px 0px rgba(0,0,0,0.1);
      box-sizing: border-box;
    " class="blacklist-header">
      <div style="display: flex; align-items: center; justify-content: space-between;">
        <div style="font-size: 16px; font-weight: 600; color: #333;">
          当前黑名单状态：
          <el-tag :type="isInBlacklist ? 'danger' : 'success'" size="medium">
            {{ isInBlacklist ? '已列入黑名单' : '正常状态' }}
          </el-tag>
        </div>
        <div v-if="isInBlacklist" style="font-size: 14px; color: #e64340;">
          解禁时间：{{ banEndTime }}
        </div>
      </div>
    </div>

    <!-- 面包屑 -->
    <div :style='{"width":"100%","padding":"20px","margin":"10px auto","borderRadius":"27px","background":"#bfd2fc","boxSizing":"border-box"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'Ξ'" :style='{"fontSize":"14px","lineHeight":"1"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item>违规记录</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 列表容器 -->
    <div class="list-preview" :style='{"width":"100%","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"20px auto","position":"relative","background":"#fff","boxSizing":"border-box","padding":"0 10px"}'>

      <!-- 违规记录表格 -->
      <el-table
          class="custom-table"
          :stripe="false"
          :style="{
          padding: '0',
          boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.3020)',
          borderColor: '#eee',
          borderRadius: '8px',
          borderWidth: '1px 0 0 1px',
          background: '#fff',
          width: '100%',
          margin: '20px auto',
          borderStyle: 'solid',
          height: 'fit-content',
          boxSizing: 'border-box',
          overflow: 'visible',
        }"
          :data="violationList"
          v-loading="dataListLoading"
          border
      >
        <el-table-column
            type="index"
            label="索引"
            width="80"
            align="center"
            class-name="index-column"
        >
          <template #default="{ $index }">
            <span class="index-badge">{{ $index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="xuehao"
            label="学号"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.xuehao }}
          </template>
        </el-table-column>
        <el-table-column
            prop="xingming"
            label="姓名"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.xingming }}
          </template>
        </el-table-column>
        <el-table-column
            prop="weiguileixing"
            label="违规类型"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            <el-tag :type="getViolationTagType(row.weiguileixing)">
              {{ row.weiguileixing }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="weiguiTime"
            label="违规时间"
            min-width="160"
            align="center"
        >
          <template #default="{ row }">
            {{ row.weiguiTime || "-" }}
          </template>
        </el-table-column>
        <el-table-column
            prop="weiguiZixishi"
            label="违规自习室"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            {{ row.weiguiZixishi || "-" }}
          </template>
        </el-table-column>
        <el-table-column
            prop="weiguiXiangqing"
            label="违规详情"
            min-width="180"
            align="center"
        >
          <template #default="{ row }">
            <div class="remark-box" :title="row.weiguiXiangqing">
              {{ row.weiguiXiangqing || "无" }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
            prop="shensuHandleStatus"
            label="申诉处理状态"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            <el-tag :type="getShensuHandleTagType(row.shensuHandleStatus)">
              {{ row.shensuHandleStatus || '未处理' }}
            </el-tag>
          </template>
        </el-table-column>
        <!-- 操作列 -->
        <el-table-column
            label="操作"
            min-width="100"
            align="center"
        >
          <template #default="{ row }">
            <div style="display: flex; gap: 6px; justify-content: center; align-items: center; flex-wrap: wrap;">
              <el-button
                  type="warning"
                  size="mini"
                  icon="el-icon-circle-check"
                  @click="toShensu(row)"
                  :disabled="row.shensuStatus === '已申诉'"
                  :style="{
                    borderRadius: '4px',
                    padding: '0 10px',
                    height: '32px',
                    lineHeight: '32px',
                    transition: 'all 0.2s',
                    borderColor: row.shensuStatus === '已申诉' ? '#ccc' : '#E6A23C',
                    background: row.shensuStatus === '已申诉' ? '#f5f5f5' : '#E6A23C',
                    color: row.shensuStatus === '已申诉' ? '#999' : '#fff'
                  }"
              >{{ row.shensuStatus === '已申诉' ? '已申诉' : '申诉' }}</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          background
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          :layout="layouts.join()"
          :total="total"
          prev-text="<"
          next-text=">"
          :hide-on-single-page="true"
          :style="{
          width: '100%',
          padding: '20px 0',
          margin: '0 auto',
          whiteSpace: 'nowrap',
          color: '#333',
          fontWeight: '500',
          textAlign: 'center',
          boxSizing: 'border-box',
        }"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      isInBlacklist: false,
      banEndTime: "",
      searchForm: { xuehao: '', weiguileixing: '' },
      violationList: [],
      pageIndex: 1,
      pageSize: 10,
      total: 0,
      dataListLoading: false,
      layouts: ["total", "prev", "pager", "next", "sizes", "jumper"],
      weiguileixingOptions: "大声喧哗,未签到,教室乱吃乱喝,迟到签到,未签退,多次取消,占座超时,违规占位".split(","),
    };
  },
  created() {
    this.fetchBlacklistStatus();
    this.fetchViolationList();
  },
  methods: {
    getViolationTagType(type) {
      const typeMap = {
        "大声喧哗": "warning",
        "未签到": "danger",
        "教室乱吃乱喝": "danger",
        "迟到签到": "warning",
        "未签退": "danger",
        "多次取消": "info",
        "占座超时": "danger",
        "违规占位": "danger"
      };
      return typeMap[type] || "info";
    },
    getShensuHandleTagType(status) {
      const statusMap = { "未处理": "default", "处理中": "warning", "已处理": "success" };
      return statusMap[status] || "default";
    },

    search() {
      this.pageIndex = 1;
      this.fetchViolationList();
    },
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.fetchViolationList();
    },
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.fetchViolationList();
    },
    toShensu(row) {
      localStorage.setItem("currentWeiguiObj", JSON.stringify(row));
      this.$router.push('/index/weiguiShensu');
      this.$message.success(`正在跳转到【${row.xuehao}】的申诉页面`);
      row.shensuStatus = "已申诉";
    },

    getCurrentXuehao() {
      try { console.log('[DEBUG] localStorage keys:', Object.keys(localStorage)); } catch (e) {}
      const tryKeys = ['currentUser','user','userInfo','userinfo','loginUser','xuehao','XUEHAO','userInfoRes','loginInfo','username','adminName'];
      for (const k of tryKeys) {
        const v = localStorage.getItem(k);
        if (!v) continue;
        try {
          const parsed = JSON.parse(v);
          if (typeof parsed === 'string' || typeof parsed === 'number') {
            const s = String(parsed).trim();
            if (/^\d{6,12}$/.test(s)) return s;
          }
          const findX = (obj) => {
            if (!obj || typeof obj !== 'object') return null;
            const candidates = ['xuehao','xueHao','studentId','username','id','userId'];
            for (const c of candidates) if (obj[c]) return obj[c];
            for (const key of Object.keys(obj)) {
              try { const res = findX(obj[key]); if (res) return res; } catch(e){}
            }
            return null;
          };
          const found = findX(parsed);
          if (found) return String(found);
        } catch (e) {
          const s = v.trim();
          if (/^\d{6,12}$/.test(s)) return s;
        }
      }
      const direct = localStorage.getItem('xuehao') || localStorage.getItem('XUEHAO');
      if (direct && /^\d{6,12}$/.test(String(direct).trim())) return String(direct).trim();
      const token = localStorage.getItem('Token') || localStorage.getItem('token') || localStorage.getItem('Authorization');
      if (token) {
        try {
          const raw = token.split(' ')[1] || token;
          const payload = raw.split('.')[1];
          if (payload) {
            const json = JSON.parse(atob(payload.replace(/-/g,'+').replace(/_/g,'/')));
            const candidates = json.xuehao || json.username || json.studentId || json.userId;
            if (candidates) return String(candidates);
          }
        } catch(e){}
      }
      console.warn('未能从 localStorage 中识别出当前用户学号');
      return null;
    },

    getAuthHeaders() {
      const token = localStorage.getItem('Token') || localStorage.getItem('token') || localStorage.getItem('Authorization') || '';
      const headers = {};
      if (token) {
        headers['Authorization'] = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
        headers['Token'] = token;
      }
      return headers;
    },

    // 格式化后端可能返回的各种时间类型（数组/数字/字符串/Date）
    formatAnyTime(val) {
      if (!val && val !== 0) return '';
      if (Array.isArray(val)) {
        const arr = val.map(v => String(v).padStart(2, '0'));
        if (arr.length >= 6) return `${arr[0]}-${arr[1]}-${arr[2]} ${arr[3]}:${arr[4]}:${arr[5]}`;
        return arr.join('-');
      }
      if (typeof val === 'number') {
        if (String(val).length === 10) return new Date(val * 1000).toISOString().replace('T',' ').slice(0,19);
        return new Date(val).toISOString().replace('T',' ').slice(0,19);
      }
      if (val instanceof Object && !(val instanceof Date)) {
        try {
          const asStr = String(val);
          if (asStr && asStr.indexOf('[') === 0) return asStr;
          return asStr;
        } catch (e) {}
      }
      try {
        const s = String(val).trim();
        if (/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}/.test(s)) return s.replace('T',' ').slice(0,19);
        return s;
      } catch (e) {
        return String(val);
      }
    },

    // 从后端加载当前学生的违规列表（只显示当前登录学号的记录）
    async fetchViolationList() {
      const xuehao = this.getCurrentXuehao();
      console.log('[DEBUG] fetchViolationList 使用学号：', xuehao);
      if (!xuehao) {
        this.violationList = [];
        this.total = 0;
        return;
      }

      this.dataListLoading = true;
      try {
        const resp = await axios.get('/api/weigui/list', {
          params: {
            page: this.pageIndex,
            size: this.pageSize,
            xuehao: xuehao,
            weiguiLeixing: this.searchForm.weiguileixing || undefined
          },
          headers: this.getAuthHeaders(),
          withCredentials: true
        });

        console.log('[DEBUG] /api/weigui/list 响应：', resp && resp.data);

        const data = resp && resp.data;
        const records = Array.isArray(data.data) ? data.data : (data.data && data.data.records) || [];
        this.total = data.total || (data.data && data.data.total) || records.length || 0;

        const mapped = records.map(r => {
          const getField = (...names) => {
            for (const n of names) {
              if (r[n] !== undefined && r[n] !== null) return r[n];
            }
            return null;
          };
          const rawType = getField('weiguileixingName', 'weiguiLeixing', 'weiguileixing', 'weiguileixingName') || '';
          const rawTime = getField('weiguiShijian', 'weigui_shijian', 'addtime', 'addTime');
          return {
            id: getField('id'),
            xuehao: getField('xuehao') || xuehao,
            xingming: getField('xingming') || '',
            weiguileixing: rawType,
            weiguiTime: this.formatAnyTime(rawTime),
            weiguiZixishi: getField('zixishiMingcheng', 'zixishi_mingcheng') || '',
            weiguiXiangqing: getField('weiguiBeizhu', 'weigui_beizhu', 'otherWeiguiDesc') || '',
            shensuStatus: getField('shensuStatus') || '未申诉',
            shensuHandleStatus: getField('shensuHandleStatus') || '未处理'
          };
        });

        this.violationList = mapped;
      } catch (err) {
        console.error('fetchViolationList 出错：', err);
        this.violationList = [];
        this.total = 0;
      } finally {
        this.dataListLoading = false;
      }
    },

    // 黑名单：只展示“解禁时间”（不做实时倒计时）
    async fetchBlacklistStatus() {
      const xuehao = this.getCurrentXuehao();
      if (!xuehao) { this.isInBlacklist = false; this.banEndTime = ""; return; }
      try {
        const resp = await axios.get('/api/heimingdan/list', {
          params: { xuehao, page: 1, size: 10 },
          headers: this.getAuthHeaders(),
          withCredentials: true
        });
        const data = resp && resp.data;
        if (!data || data.code !== 0) { this.isInBlacklist = false; this.banEndTime = ""; return; }
        const records = (data.data && data.data.records) || [];
        if (!records.length) { this.isInBlacklist = false; this.banEndTime = ""; return; }

        const now = Date.now();
        let active = null;
        for (const r of records) {
          const endStr = r.heimingdanEndTime || r.heimingdan_end_time || r.endTime || r.end_time || r.heimingdan_end;
          if (!endStr) { active = { record: r, endDate: null }; break; }
          const endDate = this.parseDateStr(endStr);
          if (!endDate) continue;
          if (endDate.getTime() > now) { active = { record: r, endDate }; break; }
        }

        if (active) {
          this.isInBlacklist = true;
          if (!active.endDate) {
            this.banEndTime = '长期封禁';
          } else {
            this.banEndTime = this.formatEndTime(active.endDate);
          }
        } else {
          this.isInBlacklist = false;
          this.banEndTime = "";
        }
      } catch (e) {
        console.error(e);
        this.isInBlacklist = false;
        this.banEndTime = "";
      }
    },

    parseDateStr(str) {
      if (!str && str !== 0) return null;
      if (str instanceof Date) return str;
      if (typeof str === 'number') return new Date(str);
      if (Array.isArray(str)) {
        const arr = str.map(n => Number(n));
        if (arr.length >= 6) return new Date(arr[0], arr[1] - 1, arr[2], arr[3], arr[4], arr[5]);
        return null;
      }
      let s = String(str).trim();
      if (/^\d{10}$/.test(s)) return new Date(Number(s) * 1000);
      if (/^\d{13}$/.test(s)) return new Date(Number(s));
      if (/^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/.test(s)) s = s.replace(' ', 'T');
      if (/^\d{4}\/\d{2}\/\d{2} \d{2}:\d{2}:\d{2}$/.test(s)) s = s.replace(' ', 'T').replace(/\//g, '-');
      const d = new Date(s);
      if (isNaN(d.getTime())) return null;
      return d;
    },

    // 将结束时间格式化为 "yyyy-mm-dd hh:mm:ss"
    formatEndTime(endDate) {
      const d = endDate instanceof Date ? endDate : new Date(endDate);
      if (isNaN(d.getTime())) return '';
      const pad = (n) => (n < 10 ? '0' + n : '' + n);
      return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
    },

    formatRemainingTime(endDate) {
      const now = Date.now();
      let diff = endDate.getTime() - now;
      if (diff <= 0) return '已过期';
      const days = Math.floor(diff / (24 * 3600 * 1000));
      diff %= 24 * 3600 * 1000;
      const hours = Math.floor(diff / (3600 * 1000));
      diff %= 3600 * 1000;
      const minutes = Math.floor(diff / (60 * 1000));
      const pad = n => (n < 10 ? '0' + n : '' + n);
      const fmtEnd = `${endDate.getFullYear()}-${pad(endDate.getMonth() + 1)}-${pad(endDate.getDate())} ${pad(endDate.getHours())}:${pad(endDate.getMinutes())}:${pad(endDate.getSeconds())}`;
      return `${days}天${hours}小时${minutes}分钟（解禁时间: ${fmtEnd}）`;
    }
  }
};
</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
}

// 黑名单header样式
.blacklist-header {
  .el-tag {
    margin-left: 8px;
  }
}

// 复用原页面样式
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__separator {
  margin: 0 9px;
  color: #000;
  font-weight: 500;
}
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__inner {
  color: #000;
  display: inline-block;
  cursor: pointer; /* 添加鼠标指针，提示可点击 */
}
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__inner a {
  color: #000;
  text-decoration: none;
}

.custom-table {
  border-radius: 8px;
  overflow: visible !important;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;

  .el-table__header-wrapper {
    th {
      background: #f8f9fa;
      color: #495057;
      font-weight: 600;
      font-size: 14px;
      border-bottom: 2px solid #e9ecef !important;
      padding: 12px 0;
    }
  }

  .el-table__body-wrapper {
    overflow: visible !important;
    .el-table__row {
      transition: all 0.3s ease;
      &:hover {
        background: #f1f3f5;
      }
      td {
        border-color: #e9ecef !important;
        padding: 12px 5px;
      }
    }
  }

  .index-column .index-badge {
    display: inline-block;
    width: 24px;
    height: 24px;
    line-height: 24px;
    border-radius: 50%;
    background: #93c7b3;
    color: white;
    font-weight: 500;
  }

  .text-ellipsis {
    max-width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .remark-box {
    padding: 4px 8px;
    background: #f8f9fa;
    border-radius: 4px;
    font-size: 13px;
    max-width: 200px;
    margin: 0 auto;
  }
}

.center-form-pv {
  .el-input, .el-select {
    width: 100%;
  }
  .el-input__inner {
    border: 1px solid #78abc3;
    border-radius: 4px;
    padding: 0 12px;
    box-shadow: 0px 2px 2px 0px #78abc3;
    outline: none;
    color: #000;
    font-size: 14px;
    height: 40px;
  }
  .el-select .el-input__inner {
    border: 1px solid #78abc3;
    border-radius: 4px;
    padding: 0 10px;
    box-shadow: 0px 2px 2px 0px #78abc3;
    outline: none;
    color: #000;
    font-size: 14px;
    height: 40px;
  }
}

.el-pagination >>> {
  .el-pagination__total {
    margin: 0 10px 0 0;
    color: #666;
    font-weight: 400;
  }
  .el-pager .number {
    cursor: pointer;
    padding: 0 4px;
    margin: 0 5px;
    color: #666;
    background: #f4f4f5;
    min-width: 30px;
    height: 28px;
    line-height: 28px;
    border-radius: 2px;
  }
  .el-pager .number:hover, .el-pager .number.active {
    background: #2e61e1;
    color: #fff;
  }
}

.el-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

// 响应式适配
@media (max-width: 768px) {
  .el-table-column--label {
    min-width: 80px !important;
  }
  .el-table-column__content {
    font-size: 12px;
  }
  .el-button {
    font-size: 12px;
    padding: 0 8px !important;
  }
  .blacklist-header {
    flex-direction: column;
    gap: 10px;
    align-items: flex-start !important;
  }
}
</style>