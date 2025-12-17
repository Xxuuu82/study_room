<template>
  <div style="width: 100%; min-height: 100vh; padding: 0 10px; boxSizing: border-box; background: #bfd2fc;">
    <!-- 面包屑（和列表页风格统一） -->
    <div :style='{"width":"100%","padding":"20px","margin":"10px auto","borderRadius":"27px","background":"#bfd2fc"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'Ξ'" :style='{"fontSize":"14px","lineHeight":"1"}'>
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index">{{item.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 详情容器（全屏自适应） -->
    <div class="detail-preview" :style='{
      width: "100%",
      margin: "20px auto",
      position: "relative",
      background: "#fff",
      boxShadow: "0px 4px 10px 0px rgba(0,0,0,0.302)",
      borderRadius: "8px",
      boxSizing: "border-box",
      padding: "20px"
    }'>
      <!-- 基础信息模块 -->
      <div class="attr" :style="{
        padding: '20px',
        position: 'relative',
        background: '#f8f9fa',
        borderRadius: '8px',
        display: 'flex',
        flexWrap: 'wrap',
        gap: '20px',
        marginBottom: '20px'
      }">
        <!-- 轮播图 -->
        <el-carousel
            :style="{ flex: '1 1 400px', minWidth: '300px', height: '400px', borderRadius: '8px', overflow: 'hidden' }"
            :autoplay="true"
            trigger="click"
            indicator-position="inside"
            arrow="always"
            :loop="true"
            :interval="3000"
        >
          <el-carousel-item v-for="(item, idx) in detailBanner" :key="idx" :style="{ borderRadius: '8px', width: '100%', height: '100%' }">
            <el-image
                v-if="item && item.substr && item.substr(0,4) === 'http'"
                :src="item"
                fit="cover"
                :style="{ objectFit: 'cover', width: '100%', height: '100%' }"
            />
            <el-image
                v-else
                :src="baseUrl + item"
                fit="cover"
                :style="{ objectFit: 'cover', width: '100%', height: '100%' }"
            />
          </el-carousel-item>
        </el-carousel>
        <!-- 信息面板 -->
        <div class="info" :style="{ flex: '2 1 500px', minWidth: '300px', padding: '20px', background: '#fff', borderRadius: '8px', boxShadow: '0 2px 8px rgba(0,0,0,0.1)'}">
          <div class="item" :style="{ padding: '10px 0', margin: '0 0 20px 0', alignItems: 'center', justifyContent: 'space-between', display: 'flex', borderBottom: '1px solid #eee' }">
            <div :style="{ color: '#333', fontSize: '18px', fontWeight: '500' }">{{ detail.mingcheng }}</div>
          </div>
          <div style="display: grid; gridTemplateColumns: repeat(auto-fit, minmax(250px, 1fr)); gap: 15px;">
            <div class="info-item" :style="{ padding: '12px', background: '#f8f9fa', borderRadius: '6px' }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">规模</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ detail.guimo || '暂无' }}</div>
            </div>
            <div class="info-item" :style="{ padding: '12px', background: '#f8f9fa', borderRadius: '6px' }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">座位</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ detail.sd_zuowei || '暂无' }}</div>
            </div>
            <div class="info-item" :style="{ padding: '12px', background: '#f8f9fa', borderRadius: '6px' }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">位置</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ detail.weizhi || '暂无' }}</div>
            </div>
            <div class="info-item" :style="{ padding: '12px', background: '#f8f9fa', borderRadius: '6px' }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">开放时间</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ parseKeyue(detail.keyueshijian) }}</div>
            </div>
          </div>
          <div class="btn" :style="{ padding: '20px 0 10px', justifyContent: 'center', display: 'flex' }">
            <el-button v-if="isAuth('zixishi','在线预约')" type="success" @click="onAcross('yuyuexinxi')" :style="buttonStyle">在线预约</el-button>
            <el-button v-if="canShowAvailabilityButton" type="primary" @click="openAvailability" :style="buttonStyle">可约时间</el-button>
          </div>
        </div>
      </div>
      <!-- 座位选择模块 -->
      <div class="seat-map" :style="{ padding: '30px', margin: '0 0 20px 0', background: '#f8f9fa', borderRadius: '8px', boxShadow: '0 2px 8px rgba(0,0,0,0.1)', position: 'relative' }">
        <div class="seat-header" :style="{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px', flexWrap: 'wrap', gap: '10px' }">
          <div class="seat-title" :style="{ fontSize: '16px', color: '#333', fontWeight: '500', paddingBottom: '10px', borderBottom: '1px solid #eee', flex: '1 1 100%' }">座位选择</div>
          <div class="seat-legend" :style="{ display: 'flex', gap: '20px', fontSize: '13px', color: '#666' }">
            <div :style="{display: 'flex', alignItems: 'center', gap: '6px'}">
              <div :style="{ width: '16px', height: '16px', border: '2px solid #2E61E1', borderRadius: '4px', background: '#fff' }"></div>
              <span>可选座位</span>
            </div>
            <div :style="{display: 'flex', alignItems: 'center', gap: '6px'}">
              <div :style="{ width: '16px', height: '16px', border: '2px solid #2E61E1', borderRadius: '4px', background: '#2E61E1', boxShadow: '0 0 0 2px rgba(46, 97, 225, 0.3)' }"></div>
              <span>已选座位</span>
            </div>
            <div :style="{display: 'flex', alignItems: 'center', gap: '6px'}">
              <div :style="{ width: '16px', height: '16px', border: '2px solid #dcdfe6', borderRadius: '4px', background: '#f0f2f5' }"></div>
              <span>维护中</span>
            </div>
          </div>
        </div>
        <div v-if="seats" class="seat-grid" :style="{ display: 'grid', gap: '15px', gridTemplateColumns: 'repeat(auto-fill, minmax(45px, 1fr))', justifyItems: 'center', maxWidth: '900px', margin: '0 auto', padding: '20px', background: '#fff', borderRadius: '8px', boxShadow: '0 1px 3px rgba(0,0,0,0.05)'}">
          <div v-for="seat in seats" :key="seat.id" class="seat-item"
               :class="{ 'seat-selected': seat.selected, 'seat-occupied': seat.status === 'occupied', 'seat-available': seat.status === 'available' }"
               @click="handleSeatClick(seat)"
               :style="seatStyle(seat)">
            {{ seat.number }}
            <i v-if="seat.status === 'occupied'" class="el-icon-lock" :style="{ position: 'absolute', bottom: '2px', right: '2px', fontSize: '10px', color: '#c0c4cc' }"></i>
          </div>
        </div>
        <div class="selected-hint" v-if="seats" :style="{ marginTop: '20px', color: '#666', fontSize: '14px', textAlign: 'center', padding: '10px', background: '#fff', borderRadius: '6px', maxWidth: '500px', margin: '0 auto' }">
          <i class="el-icon-info" :style="{color: '#2E61E1', marginRight: '6px'}"></i>
          已选座位：<span style="color: #2e61e1; font-weight: 500;">{{ selectedSeatsText }}</span>
          <span style="margin-left: 10px; color: #999;">（最多选择{{ maxSelect }}个座位）</span>
        </div>
        <div v-else style="padding: 20px; color: #666; text-align: center;">加载座位信息中...</div>
      </div>
      <!-- 返回按钮 -->
      <div style="width: 100%; display: flex; justify-content: center; margin: 30px 0 20px;">
        <el-button @click="goBack" :style="buttonStyle"><i class="el-icon-arrow-left" style="margin-right:6px;"></i>返回自习室列表</el-button>
      </div>
    </div>
    <!-- 可约时间弹窗 -->
    <el-dialog title="可约时间" :visible.sync="availabilityDialog" width="900px">
      <div style="display:flex; gap:12px; align-items:center; margin-bottom:12px;">
        <!-- 核心修改：日期选择器绑定picker-options + 自定义类名 -->
        <el-date-picker
            v-model="availabilityDate"
            type="date"
            placeholder="选择日期(默认今日)"
            style="width:200px;"
            :picker-options="datePickerOptions"
            value-format="yyyy-MM-dd"
            popper-class="custom-availability-date-picker"
        >
        </el-date-picker>
        <el-button type="primary" @click="fetchSeatStatus">查询</el-button>
        <div style="margin-left: auto; color: #666;">已选座位：<span style="color:#2E61E1; font-weight:500">{{ selectedSeatsText }}</span></div>
      </div>
      <div v-if="availabilitySlots && availabilitySlots.length" style="padding:12px; background:#fff; border-radius:6px; border:1px solid #eee;">
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:8px;">
          <div style="font-size:14px; color:#666;">显示：{{ availabilityDate }} （08:00 - 22:00，共 {{ availabilitySlots.length }} 槽）</div>
          <div style="font-size:12px; color:#999;">绿色=可约，灰色=已占或已过</div>
        </div>
        <div class="availability-grid">
          <div
              v-for="slot in availabilitySlots"
              :key="slot.startStr"
              :class="['slot', slot.status, { 'slot-past': slot.isPast }]"
              @click="onSlotClick(slot)"
              :title="formatSlotTitle(slot)"
          >
            <div class="slot-time">{{ formatTimeShort(slot.startStr) }} - {{ formatTimeShort(slot.endStr) }}</div>
            <div class="slot-label">{{ slot.status === 'available' && !slot.isPast ? '可约' : (slot.isPast ? '已过' : '已占') }}</div>
          </div>
        </div>
      </div>
      <div v-else style="padding: 20px; color: #666;">暂无可约数据，或请先选择座位并点击 查询。</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="availabilityDialog=false">关闭</el-button>
      </span>
      <el-button type="text" @click="fetchSeatStatus">刷新</el-button>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tablename: "zixishi",
      baseUrl: "",
      breadcrumbItem: [{ name: "自习室详情" }],
      detailBanner: [],
      detail: {},
      seats: null,
      maxSelect: 1,
      selectedSeatObj: null,
      availabilityDialog: false,
      availabilityDate: new Date().toISOString().slice(0,10),
      availabilityData: null,
      availabilitySlots: [],
      loadingStatus: false,
      // button style used in template
      buttonStyle: {
        border: '1px solid #97C9D6',
        cursor: 'pointer',
        padding: '0 24px',
        boxShadow: '0px 2px 2px 0px #78ABC3',
        outline: 'none',
        color: '#78ABC3',
        borderRadius: '30px',
        background: '#fff',
        fontSize: '14px',
        height: '40px',
        transition: 'all 0.2s'
      },
      availabilityViewOnly: true, // true = 仅查看（点击不跳转）；false = 点击会跳转到预约页面（原行为）
      highlightedSlot: null,      // 用于在仅查看模式下高亮选中槽
      // 新增：日期选择器配置（核心禁用逻辑）
      datePickerOptions: {
        disabledDate: (time) => {
          const today = new Date();
          // 清空时分秒，仅保留年月日
          const todayTime = new Date(today.getFullYear(), today.getMonth(), today.getDate()).getTime();
          const tomorrowTime = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1).getTime();
          const currentTime = new Date(time.getFullYear(), time.getMonth(), time.getDate()).getTime();
          // 仅允许今天和明天，其他日期禁用
          return currentTime !== todayTime && currentTime !== tomorrowTime;
        }
      }
    };
  },
  computed: {
    selectedSeatsText() {
      return (this.seats && this.seats.filter(s=>s.selected).map(s=>s.number).join(', ')) || '暂无';
    },
    canShowAvailabilityButton() {
      return !!this.selectedSeatObj;
    }
  },
  created() {
    this.init();
    this.initSeats();
  },
  methods: {
    goBack() { this.$router.push('/index/zixishi'); },
    init() {
      this.baseUrl = this.$config ? this.$config.baseUrl : '';
      if (this.$route && this.$route.query && this.$route.query.detailObj) {
        try { this.detail = JSON.parse(this.$route.query.detailObj); } catch(e){;}
      }
      if (this.$route && this.$route.query && this.$route.query.storeupObj) {
        try { this.detail.id = JSON.parse(this.$route.query.storeupObj).refid; } catch(e){;}
      }
      if (this.detail && this.detail.id) {
        this.$http.get(this.tablename + "/detail/" + this.detail.id).then(res=>{
          if (res.data && res.data.code === 0) {
            this.detail = res.data.data;
            this.detailBanner = this.detail.tupian ? this.detail.tupian.split(',') : [];
          }
        }).catch(()=>{});
      }
    },
    async initSeats() {
      try {
        if (!this.detail || !this.detail.id) { this.seats = []; return; }
        const res = await this.$http.get(this.tablename + "/seats/" + this.detail.id);
        const dbSeats = res.data;
        const list = Array.isArray(dbSeats) ? dbSeats : (dbSeats.data && Array.isArray(dbSeats.data) ? dbSeats.data : dbSeats);
        this.seats = list.map(seat => ({ id: `seat_${seat.id}`, number: `${seat.id}`, status: Number(seat.status)===1 ? 'available' : 'occupied', selected:false }));
      } catch (e) {
        this.seats = [];
        console.error('initSeats error', e);
      }
    },
    seatStyle(seat) {
      return {
        width: '45px',
        height: '45px',
        borderRadius: '8px',
        cursor: seat.status === 'occupied' ? 'not-allowed' : 'pointer',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        fontSize: '15px',
        fontWeight: '500',
        transition: 'all 0.2s ease-in-out',
        position: 'relative',
        border: seat.status === 'occupied' ? '2px solid #dcdfe6' : '2px solid #2E61E1',
        backgroundColor: seat.selected ? '#2E61E1' : (seat.status === 'occupied' ? '#f0f2f5' : '#fff'),
        color: seat.selected ? '#fff' : (seat.status === 'occupied' ? '#c0c4cc' : '#333')
      };
    },
    handleSeatClick(seat) {
      if (seat.status === 'occupied') { this.$message.warning('该座位已被占用'); return; }
      const selectedCount = this.seats.filter(s=>s.selected).length;
      const newSeats = this.seats.map(s => {
        if (s.id === seat.id) {
          if (!s.selected && selectedCount >= this.maxSelect) { this.$message.warning(`最多选择${this.maxSelect}个座位`); return s; }
          return {...s, selected: !s.selected};
        }
        return s;
      });
      this.seats = newSeats;
      const selecteds = this.seats.filter(s=>s.selected);
      this.selectedSeatObj = selecteds.length === 1 ? selecteds[0] : null;
    },
    onAcross(acrossTable) {
      const selectedSeats = this.seats.filter((s) => s.selected);
      if (selectedSeats.length === 0) { this.$message.error("请先选择座位"); return; }
      try {
        const storageData = {
          version: "1.0",
          timestamp: new Date().getTime(),
          data: {
            seatNumbers: selectedSeats.map((s) => s.number),
            seatIds: selectedSeats.map((s) => s.id),
            zixishiId: this.detail.id,
          },
        };
        localStorage.setItem("seatSelection", btoa(JSON.stringify(storageData)));
        localStorage.setItem("crossTable", "zixishi");
        localStorage.setItem("crossObj", JSON.stringify(this.detail));
      } catch (error) {
        console.error("本地存储失败:", error);
        this.$message.error("数据保存失败，请检查浏览器设置");
        return;
      }
      this.$router.push({ path: `/index/${acrossTable}Add`, query: { type: "cross", seats: selectedSeats.map(s=>s.id).join(","), zixishiId: this.detail.id }});
    },
    parseKeyue(kstr) {
      // 如果后端有 keyueshijian 字段并且格式正确，可解析并返回；
      // 否则强制返回 "开放时间：08:00-22:00"
      try {
        if (!kstr) return '可约时间：08:00-22:00';
        const s = kstr.replace("：", ":").replace("至", "-").replace(/每[天周月年\s]*/g, "").trim();
        if (s.indexOf("-") >= 0) {
          const parts = s.split("-");
          return  '可约时间'+parts[0].trim() + '-' + parts[1].trim();
        }
        return '可约时间：08:00-22:00';
      } catch (e) {
        return '可约时间：08:00-22:00';
      }
    },
    openAvailability() {
      if (!this.selectedSeatObj) {
        this.$message.warning('请先选择座位');
        return;
      }
      // 默认今天
      if (!this.availabilityDate) this.availabilityDate = new Date().toISOString().slice(0,10);
      this.availabilityDialog = true;
      // 立即查询最新可约/已占数据
      this.fetchSeatStatus();
    },

    async fetchSeatStatus() {
      if (!this.selectedSeatObj) {
        this.$message.warning('请先选择座位');
        return;
      }
      this.loadingStatus = true;
      const zixishiid = Number(this.detail.id);
      const zuowei = parseInt(this.selectedSeatObj.number, 10);
      // 确保 date 字符串 yyyy-MM-dd
      let date = this.availabilityDate;
      if (!date) date = new Date().toISOString().slice(0,10);
      else if (date instanceof Date) {
        const d = date;
        date = `${d.getFullYear()}-${('0'+(d.getMonth()+1)).slice(-2)}-${('0'+d.getDate()).slice(-2)}`;
      }
      try {
        const res = await this.$axios.get('/yuyuexinxi/seat/list', { params: { zixishiid, zuowei, date }})
        this.loadingStatus = false;
        const resBody = res.body || res.data || {};
        let bookings = [];
        if (resBody.code !== undefined) {
          if (resBody.code !== 0) {
            this.$message.error(resBody.msg || '获取可约时间失败');
            this.availabilitySlots = [];
            return;
          }
          const data = resBody.data || {};
          bookings = data.bookings || [];
        } else {
          bookings = Array.isArray(resBody) ? resBody : (resBody.bookings || []);
        }
        // 输出所有booking记录
        console.log('fetchSeatStatus bookings:', bookings);

        // 核心修改：直接基于原始bookings生成slot，删除小时级聚合逻辑
        this.availabilitySlots = this._build30MinSlots(date, bookings);

        // 生成bookingIntervals（仅用于调试）
        const bookingIntervals = bookings.map(b => {
          let startStr = b.yuyueStart || b.startStr || b.start;
          let endStr = b.yuyueEnd   || b.endStr   || b.end;
          const s = startStr ? new Date(startStr).getTime() : NaN;
          const e = endStr   ? new Date(endStr).getTime()   : NaN;
          return { start: s, end: e, startStr, endStr };
        });
        // 输出所有bookingIntervals
        console.log('bookingIntervals:', bookingIntervals);

        // 核心调试：输出每个slot与其重叠的booking
        this.availabilitySlots = this.availabilitySlots.map(slot => {
          const slotS = new Date(slot.start).getTime();
          const slotE = new Date(slot.end).getTime();
          // 严格重叠判断逻辑
          const overlappedBookings = bookingIntervals.filter(bi =>
              !isNaN(bi.start) && !isNaN(bi.end) &&
              slotS < bi.end && slotE > bi.start
          );
          const isBooked = overlappedBookings.length > 0;

          // 仅调试重点段：输出11:30-12:00或其它你关心的段
          if (
              slot.startStr.includes("10:30") ||
              slot.startStr.includes("11:00") ||
              slot.startStr.includes("11:30") ||
              slot.startStr.includes("12:00") ||
              slot.startStr.includes("12:30") ||
              slot.startStr.includes("14:00") ||
              slot.startStr.includes("14:30")
          ) {
            console.log(
                `[DEBUG] slot ${slot.startStr} ~ ${slot.endStr} | isBooked: ${isBooked}`,
                { overlappedBookings }
            );
          }

          return {
            ...slot,
            status: isBooked ? 'occupied' : slot.status
          };
        });

        console.log(
            '最终14:30-15:00slot:',
            this.availabilitySlots.find(s => s.startStr.includes('14:30'))
        );
      } catch (err) {
        this.loadingStatus = false;
        console.error('fetchSeatStatus error', err);
        const status = err && (err.status || (err.response && err.response.status));
        if (status === 400) this.$message.error('请求参数错误 (400)');
        else if (status === 404) this.$message.error('接口未找到 (404)');
        else this.$message.error('网络异常，无法连接后端');
        this.availabilitySlots = [];
      }
    },

    // 核心修改：重构_build30MinSlots，直接基于原始预约记录判断，删除小时级聚合逻辑
    _build30MinSlots(dateStr, bookings, granularity = 30) {
      const slots = [];
      const dayStartHour = 8;
      const dayEndHour = 22;
      const now = new Date();
      const isToday = dateStr === now.toISOString().slice(0,10);
      const parts = dateStr.split('-');
      const year = Number(parts[0]);
      const month = Number(parts[1]) - 1;
      const day = Number(parts[2]);

      // 转换预约记录为时间戳区间
      const bookingIntervals = bookings.map(b => {
        const start = new Date(b.yuyueStart || b.startStr || b.start).getTime();
        const end = new Date(b.yuyueEnd || b.endStr || b.end).getTime();
        return { start, end };
      }).filter(bi => !isNaN(bi.start) && !isNaN(bi.end));

      for (let h = dayStartHour; h < dayEndHour; h++) {
        const stepsPerHour = Math.floor(60 / granularity);
        for (let step = 0; step < stepsPerHour; step++) {
          const minute = step * granularity;
          const startDate = new Date(year, month, day, h, minute, 0);
          const endDate = new Date(startDate.getTime() + granularity * 60 * 1000);
          const slotStart = startDate.getTime();
          const slotEnd = endDate.getTime();

          // 1. 默认都是可约
          let status = 'available';

          // 2. 判断是否与原始预约有严格交集（左闭右开）
          const isBooked = bookingIntervals.some(bi => slotStart < bi.end && slotEnd > bi.start);
          if (isBooked) status = 'occupied';

          // 3. 已过时间判断：仅当当前时间 ≥ slot开始时间才算已过
          const isPast = isToday && now.getTime() >= slotStart;
          if (isPast) status = 'occupied';

          slots.push({
            start: startDate,
            end: endDate,
            startStr: this._toDateStr(slotStart),
            endStr: this._toDateStr(slotEnd),
            status,
            isPast
          });
        }
      }
      return slots;
    },

    formatSlotTitle(slot) {
      return `${slot.startStr} → ${slot.endStr}  ${slot.status === 'available' && !slot.isPast ? '可约' : (slot.isPast ? '已过' : '已占')}`;
    },

    onSlotClick(slot) {
      if (slot.status !== 'available' || slot.isPast) {
        this.$message.info('该时段不可预约');
        return;
      }
      if (this.availabilityViewOnly) {
        // 仅查看：高亮，显示提示，不跳转
        this.highlightedSlot = slot.startStr + '→' + slot.endStr;
        this.$message({ message: `查看时段：${this.formatTimeShort(slot.startStr)} - ${this.formatTimeShort(slot.endStr)}`, type: 'info', duration: 1500 });
        return;
      } else {
        // 旧逻辑：选中并跳转到预约页面（保持原行为）
        const pick = { start: slot.startStr, end: slot.endStr, zixishiId: this.detail.id, zuowei: this.selectedSeatObj.number };
        localStorage.setItem('availabilitySelection', JSON.stringify(pick));
        this.$message.success('已选时段，正在跳转到预约页面');
        this.availabilityDialog = false;
        this.$router.push({ path: '/index/yuyuexinxiAdd', query: { type: 'cross' } });
      }
    },

    // 保留该方法但不再使用（避免删除导致其他引用报错）
    _buildAvailabilityFromReservations(dateStr, reservedList) {
      const openStart = '08:00';
      const openEnd = '22:00';
      const hours = [];
      for (let h = 8; h < 22; h++) hours.push({ hour: h, isBooked: false });
      if (Array.isArray(reservedList)) {
        reservedList.forEach(item => {
          try {
            const s = new Date(item.yuyueStart);
            const e = new Date(item.yuyueEnd);
            for (let h = 8; h < 22; h++) {
              const slotStart = new Date(dateStr + ' ' + (('0'+h).slice(-2) + ':00:00'));
              const slotEnd = new Date(dateStr + ' ' + (('0'+(h+1)).slice(-2) + ':00:00'));
              if (e > slotStart && s < slotEnd) {
                const idx = h - 8;
                if (hours[idx]) hours[idx].isBooked = true;
              }
            }
          } catch (e) {}
        });
      }
      const intervals = [];
      let cur = null;
      for (let i = 0; i < hours.length; i++) {
        const h = hours[i];
        const status = h.isBooked ? 'occupied' : 'available';
        const segStartHour = 8 + i;
        const segStartStr = `${dateStr} ${('0'+segStartHour).slice(-2)}:00:00`;
        const segEndStr = `${dateStr} ${('0'+(segStartHour+1)).slice(-2)}:00:00`;
        if (!cur) cur = { status, startStr: segStartStr, endStr: segEndStr };
        else {
          if (cur.status === status) cur.endStr = segEndStr;
          else { intervals.push(cur); cur = { status, startStr: segStartStr, endStr: segEndStr }; }
        }
      }
      if (cur) intervals.push(cur);
      return { date: dateStr, openStart, openEnd, intervals };
    },

    _toDateStr(ts) {
      if (!ts) return '';
      const d = new Date(Number(ts));
      const Y = d.getFullYear();
      const M = ('0'+(d.getMonth()+1)).slice(-2);
      const D = ('0'+d.getDate()).slice(-2);
      const h = ('0'+d.getHours()).slice(-2);
      const m = ('0'+d.getMinutes()).slice(-2);
      const s = ('0'+d.getSeconds()).slice(-2);
      return `${Y}-${M}-${D} ${h}:${m}:${s}`;
    },

    formatTimeShort(fullStr) {
      if (!fullStr) return '';
      const m = fullStr.match(/\d{2}:\d{2}(:\d{2})?$/);
      return m ? m[0].slice(0,5) : fullStr;
    },

    isAuth() { return true; },

    _timeStrFromDate(d) {
      const hh = ('0'+d.getHours()).slice(-2);
      const mm = ('0'+d.getMinutes()).slice(-2);
      return `${hh}:${mm}`;
    },

    _addMinutesToTimeStr(timeStr, addMin) {
      if (!timeStr) return timeStr;
      const parts = timeStr.split(':');
      const d = new Date();
      d.setHours(parseInt(parts[0],10), parseInt(parts[1],10), 0, 0);
      d.setMinutes(d.getMinutes() + addMin);
      return ('0'+d.getHours()).slice(-2) + ':' + ('0'+d.getMinutes()).slice(-2);
    },

    tupianUploadChange(fileUrls) { this.detail.tupian = fileUrls.replace(new RegExp(this.$config?.baseUrl || '', "g"), ""); }
  }
};
</script>
<style lang="scss" scoped>
/* 原有样式保持不变 */
.availability-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 8px;
}
.slot {
  border-radius: 6px;
  padding: 8px;
  text-align: center;
  cursor: pointer;
  user-select: none;
  border: 1px solid #e6e6e6;
}
.slot .slot-time { font-size: 13px; color: #fff; font-weight: 600; }
.slot .slot-label { font-size: 12px; color: #fff; margin-top: 4px; }
/* online-style for available slot: mimic the "在线预约" button */
.slot.available {
  background: #fff !important;
  border: 2px solid #97C9D6 !important; /* same border as online button */
  color: #78ABC3 !important;            /* text color like your online button */
  border-radius: 30px !important;
  box-shadow: 0 2px 6px rgba(120,138,195,0.08) !important;
  padding: 12px 6px !important;
  cursor: pointer;
}
/* hover effect to be slightly filled */
.slot.available:hover {
  background: linear-gradient(180deg, rgba(120,138,195,0.06), rgba(120,138,195,0.02)) !important;
  transform: translateY(-2px);
}
/* small text style inside slot */
.slot .slot-time { font-size: 13px; color: inherit; font-weight: 600; }
.slot .slot-label { font-size: 12px; color: inherit; }
/* occupied/past remain gray and disabled */
.slot.occupied, .slot-past {
  background: #f0f2f5 !important;
  border-color: #e6e6e6 !important;
  color: #999 !important;
  cursor: not-allowed;
  box-shadow: none !important;
}
.slot.occupied { background: #f0f2f5; border-color: #e6e6e6; color: #999; cursor: not-allowed; }
.slot-past { background: #f5f7fa; border-color: #eaeef5; color: #bbb; cursor: not-allowed; }
@media (max-width: 768px) {
  .availability-grid { grid-template-columns: repeat(3, 1fr); }
}
</style>
<!-- 全局样式：强制控制日期选择器禁用样式（必须无scoped） -->
<style lang="scss">
/* 定位自定义类名的日期选择器弹出层 */
.custom-availability-date-picker {
  /* 禁用日期的核心样式：变灰+删除线+禁止点击 */
  .el-date-table td.disabled {
    pointer-events: none !important; /* 彻底禁止点击 */
    .cell {
      background-color: #f5f5f5 !important; /* 灰色背景 */
      color: #c0c4cc !important; /* 灰色文字 */
      text-decoration: line-through !important; /* 删除线 */
      text-decoration-color: #999 !important; /* 删除线颜色 */
      opacity: 1 !important; /* 取消透明，确保样式清晰 */
    }
  }
  /* 确保可选日期样式正常 */
  .el-date-table td:not(.disabled) .cell {
    text-decoration: none !important;
    background-color: transparent !important;
    color: #333 !important;
  }
  /* 高亮今天/明天的可选日期 */
  .el-date-table td.is-today .cell {
    background-color: #f0f5ff !important;
    color: #2e61e1 !important;
    font-weight: 600;
  }
}
</style>