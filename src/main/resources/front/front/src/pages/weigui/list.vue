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
          解禁剩余时间：{{ banEndTime }}
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
              <!-- 申诉按钮：未申诉显示可点击，已申诉显示变灰 -->
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
              >{{ row.shensuStatus === '已申诉' ? '已申诉' : '申诉' }}</el-button
              >
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
      // 黑名单状态
      isInBlacklist: false,
      banEndTime: "",

      // 搜索表单
      searchForm: {
        xuehao: '',
        weiguileixing: ''
      },

      // 违规记录列表（按图一格式调整数据结构）
      violationList: [
        {
          xuehao: "236003000",
          xingming: "李四",
          weiguileixing: "大声喧哗",
          weiguiTime: "2025-12-14 00:00:00",
          weiguiZixishi: "$2s",
          weiguiXiangqing: "自习室大声喧哗",
          shensuStatus: "未申诉",
          shensuHandleStatus: "未处理"
        },
        {
          xuehao: "236001000",
          xingming: "周泽楷",
          weiguileixing: "大声喧哗",
          weiguiTime: "2025-12-18 00:04:30",
          weiguiZixishi: "图书馆",
          weiguiXiangqing: "大吵大闹，影响他人自习",
          shensuStatus: "已申诉",
          shensuHandleStatus: "处理中"
        },
        {
          xuehao: "236003000",
          xingming: "李四",
          weiguileixing: "未签到",
          weiguiTime: "2025-12-18 00:06:51",
          weiguiZixishi: "图书馆",
          weiguiXiangqing: "未进行签到",
          shensuStatus: "未申诉",
          shensuHandleStatus: "未处理"
        },
        {
          xuehao: "236002000",
          xingming: "赵皓天",
          weiguileixing: "教室乱吃乱喝",
          weiguiTime: "2025-12-16 00:00:00",
          weiguiZixishi: "图书馆",
          weiguiXiangqing: "教室违规吃东西",
          shensuStatus: "已申诉",
          shensuHandleStatus: "已处理"
        },
        {
          xuehao: "236002000",
          xingming: "赵皓天",
          weiguileixing: "未签到",
          weiguiTime: "2025-12-10 00:00:00",
          weiguiZixishi: "图书馆",
          weiguiXiangqing: "未按时签到",
          shensuStatus: "未申诉",
          shensuHandleStatus: "未处理"
        },
        {
          xuehao: "236001000",
          xingming: "周泽楷",
          weiguileixing: "教室乱吃乱喝",
          weiguiTime: "2025-12-18 00:15:05",
          weiguiZixishi: "图书馆",
          weiguiXiangqing: "乱吃乱喝",
          shensuStatus: "未申诉",
          shensuHandleStatus: "未处理"
        }
      ],

      // 分页相关
      pageIndex: 1,
      pageSize: 10,
      total: 6, // 模拟总数
      dataListLoading: false,
      layouts: ["total", "prev", "pager", "next", "sizes", "jumper"],

      // 下拉选项
      weiguileixingOptions: "大声喧哗,未签到,教室乱吃乱喝,迟到签到,未签退,多次取消,占座超时,违规占位".split(","),
    };
  },
  created() {
    // 页面加载时检查当前登录学生的黑名单状态
    this.fetchBlacklistStatus();
  },
  methods: {
    // 获取违规类型标签样式
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

    // 获取申诉处理状态标签样式
    getShensuHandleTagType(status) {
      const statusMap = {
        "未处理": "default",
        "处理中": "warning",
        "已处理": "success"
      };
      return statusMap[status] || "default";
    },

    // 搜索
    search() {
      this.pageIndex = 1;
      // 这里可后续对接接口
      this.dataListLoading = true;
      setTimeout(() => {
        this.dataListLoading = false;
      }, 500);
    },

    // 分页大小改变
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      // 后续对接接口
    },

    // 当前页改变
    currentChangeHandle(val) {
      this.pageIndex = val;
      // 后续对接接口
    },

    // 跳转到申诉页面
    toShensu(row) {
      // 将当前违规记录存入localStorage，供申诉页读取
      localStorage.setItem("currentWeiguiObj", JSON.stringify(row));
      // 跳转到申诉页（对应router.js中的/index/weiguiShensu路由）
      this.$router.push('/index/weiguiShensu');
      // 提示用户跳转成功
      this.$message.success(`正在跳转到【${row.xuehao}】的申诉页面`);
      // 模拟申诉后状态更新
      row.shensuStatus = "已申诉";
    },

    // ------------------- 黑名单相关 -------------------
    // 更健壮的读取当前登录学号方法（会打印 localStorage keys 供调试）
    getCurrentXuehao() {
      try {
        console.log('[DEBUG] localStorage keys:', Object.keys(localStorage));
      } catch (e) {
        // 某些环境下访问 localStorage 可能出错
      }

      const tryKeys = ['currentUser', 'user', 'userInfo', 'userinfo', 'loginUser', 'xuehao', 'XUEHAO', 'userInfoRes', 'loginInfo', 'username', 'adminName'];
      for (const k of tryKeys) {
        const v = localStorage.getItem(k);
        if (!v) continue;
        console.log(`[DEBUG] localStorage.${k} =`, v);
        // 如果是 JSON，解析后递归查找
        try {
          const parsed = JSON.parse(v);
          // 如果 parsed 是字符串或数字，直接判断并返回
          if (typeof parsed === 'string' || typeof parsed === 'number') {
            const s = String(parsed).trim();
            if (/^\d{6,12}$/.test(s)) {
              console.log('[DEBUG] 从 parsed 原始值中识别到学号:', s);
              return s;
            }
          }
          const findX = (obj) => {
            if (!obj || typeof obj !== 'object') return null;
            const candidates = ['xuehao','xueHao','studentId','username','id','userId'];
            for (const c of candidates) {
              if (obj[c]) return obj[c];
            }
            // 递归搜索
            for (const key of Object.keys(obj)) {
              try {
                const res = findX(obj[key]);
                if (res) return res;
              } catch(e) {}
            }
            return null;
          };
          const found = findX(parsed);
          if (found) {
            console.log('[DEBUG] 从 JSON 中解析到学号:', found);
            return String(found);
          }
        } catch (e) {
          // 如果不是 JSON，则直接返回字符串（可能就是学号）
          const s = v.trim();
          if (/^\d{6,12}$/.test(s)) {
            console.log('[DEBUG] 从字符串中识别到学号:', s);
            return s;
          }
        }
      }

      // 也尝试直接取单独存储的 xuehao
      const direct = localStorage.getItem('xuehao') || localStorage.getItem('XUEHAO');
      if (direct && /^\d{6,12}$/.test(String(direct).trim())) return String(direct).trim();

      // 尝试解析 token payload
      const token = localStorage.getItem('Token') || localStorage.getItem('token') || localStorage.getItem('Authorization');
      if (token) {
        try {
          const raw = token.split(' ')[1] || token; // 处理 "Bearer xxx" 情况
          const payload = raw.split('.')[1];
          if (payload) {
            const json = JSON.parse(atob(payload.replace(/-/g,'+').replace(/_/g,'/')));
            const candidates = json.xuehao || json.username || json.studentId || json.userId;
            if (candidates) {
              console.log('[DEBUG] 从 token payload 中解析到学号:', candidates);
              return String(candidates);
            }
          }
        } catch (e) {
          // ignore
        }
      }

      console.warn('未能从 localStorage 中识别出当前用户学号，请确认登录后将学号存入 localStorage（key: currentUser/user/userInfo/xuehao 等）');
      return null;
    },

    // 向后端请求黑名单列表（按学号查询），并设置 isInBlacklist / banEndTime
    async fetchBlacklistStatus() {
      const xuehao = this.getCurrentXuehao();
      console.log('[DEBUG] 使用学号查询黑名单：', xuehao);
      if (!xuehao) {
        // 没有学号则不继续
        this.isInBlacklist = false;
        this.banEndTime = "";
        return;
      }

      try {
        // 取 token（login.vue 登录时已把 token 存入 localStorage 的 'Token'）
        const token = localStorage.getItem('Token') || localStorage.getItem('token') || localStorage.getItem('Authorization') || '';
        const headers = {};
        if (token) {
          headers['Authorization'] = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
          // 兼容部分后端使用自定义 Token 头
          headers['Token'] = token;
        }

        // 调用后端接口（Controller 映射为 /api/heimingdan/list）
        // 带上 headers（token），并开启 withCredentials 以兼容基于 cookie 的会话（若后端用 token，可忽略 withCredentials）
        const resp = await axios.get('/api/heimingdan/list', {
          params: {
            xuehao: xuehao,
            page: 1,
            size: 10
          },
          headers,
          withCredentials: true
        });

        console.log('[DEBUG] /api/heimingdan/list 响应：', resp && resp.data);

        const data = resp && resp.data;
        if (!data || data.code !== 0) {
          console.warn('获取黑名单接口返回异常：', data);
          this.isInBlacklist = false;
          this.banEndTime = "";
          return;
        }

        const records = (data.data && data.data.records) || [];
        console.log('[DEBUG] records 长度：', records.length);
        if (!records.length) {
          this.isInBlacklist = false;
          this.banEndTime = "";
          return;
        }

        // 打印示例帮助调试
        console.log('[DEBUG] records 示例（前3条）：', records.slice(0, 3));

        // 找到尚未到期的黑名单记录（支持多种返回字段格式）
        const now = Date.now();
        let activeRecord = null;
        for (const r of records) {
          // 尝试找到可能的结束时间字段（多写几个候选）
          const endStrCandidates = [
            r.heimingdan_end_time, r.heimingdanEndTime, r.end_time, r.endTime, r.heimingdan_endtime,
            r.heimingdanEndTime, r.heimingdan_end, r.heiming_end_time, r.end
          ];
          const endStr = endStrCandidates.find(v => v !== undefined && v !== null && String(v).trim() !== '');
          console.log('[DEBUG] 当前记录：', r, ' 选到 endStr:', endStr);

          // 如果无结束时间字段，认为长期封禁
          if (!endStr) {
            console.log('[DEBUG] 该记录无 endStr，认为为长期黑名单：', r);
            activeRecord = { record: r, endDate: null };
            break;
          }

          // 解析时间（支持数字时间戳和多种字符串格式）
          const endDate = this.parseDateStr(endStr);
          console.log('[DEBUG] 解析 endStr -> endDate:', endStr, '=>', endDate);
          if (!endDate || isNaN(endDate.getTime())) {
            console.warn('[DEBUG] 无法解析该 endStr，继续下一个记录：', endStr);
            continue;
          }

          if (endDate.getTime() > now) {
            activeRecord = { record: r, endDate };
            console.log('[DEBUG] 找到有效未到期的黑名单记录：', r);
            break;
          } else {
            console.log('[DEBUG] 该记录已过期：', endDate, ' now:', new Date(now));
          }
        }

        if (activeRecord) {
          this.isInBlacklist = true;
          // 展示剩余时间（也包含精确解禁时间以便识别）
          if (!activeRecord.endDate) this.banEndTime = '长期封禁';
          else this.banEndTime = this.formatRemainingTime(activeRecord.endDate);
        } else {
          this.isInBlacklist = false;
          this.banEndTime = "";
        }
      } catch (err) {
        console.error('fetchBlacklistStatus 出错：', err);
        this.isInBlacklist = false;
        this.banEndTime = "";
      }
    },

    // 将数据库的时间字符串解析成 Date 对象
    // 支持数字时间戳（毫秒）、"yyyy-MM-dd HH:mm:ss"、"yyyy/MM/dd HH:mm:ss"、ISO 等
    parseDateStr(str) {
      if (!str && str !== 0) return null;
      // 如果已经是 Date
      if (str instanceof Date) return str;
      // 如果是数字类型（时间戳毫秒）
      if (typeof str === 'number') {
        return new Date(str);
      }
      // 去掉两端空格
      let s = String(str).trim();

      // 如果是纯数字（长度 10 或 13），当作时间戳处理（10s -> *1000）
      if (/^\d{10}$/.test(s)) {
        return new Date(Number(s) * 1000);
      }
      if (/^\d{13}$/.test(s)) {
        return new Date(Number(s));
      }

      // 把空格分隔的日期时间改为 ISO 格式 (T) 以便在各浏览器中解析一致
      // 例如 "2025-12-22 09:00:00" -> "2025-12-22T09:00:00"
      if (/^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/.test(s)) {
        s = s.replace(' ', 'T');
      }
      if (/^\d{4}\/\d{2}\/\d{2} \d{2}:\d{2}:\d{2}$/.test(s)) {
        s = s.replace(' ', 'T').replace(/\//g, '-');
      }

      // 尝试直接用 Date 解析
      const d = new Date(s);
      if (isNaN(d.getTime())) {
        return null;
      }
      return d;
    },

    // 将剩余时间格式化为 "Xd Yh Zm（解禁时间: yyyy-mm-dd hh:mm:ss）"
    formatRemainingTime(endDate) {
      const now = Date.now();
      let diff = endDate.getTime() - now;
      if (diff <= 0) return '已过期';
      const days = Math.floor(diff / (24 * 3600 * 1000));
      diff %= 24 * 3600 * 1000;
      const hours = Math.floor(diff / (3600 * 1000));
      diff %= 3600 * 1000;
      const minutes = Math.floor(diff / (60 * 1000));
      const pad = (n) => (n < 10 ? '0' + n : '' + n);
      const fmtEnd = `${endDate.getFullYear()}-${pad(endDate.getMonth() + 1)}-${pad(endDate.getDate())} ${pad(endDate.getHours())}:${pad(endDate.getMinutes())}:${pad(endDate.getSeconds())}`;
      return `${days}天${hours}小时${minutes}分钟（解禁时间: ${fmtEnd}）`;
    }
    // ------------------- 黑名单相关 结束 -------------------
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