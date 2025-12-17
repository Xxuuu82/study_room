<template>
  <div style="width: 100%; min-height: 100vh; padding: 0 10px; boxSizing: border-box;">
    <!-- 面包屑 -->
    <div :style='{"width":"100%","padding":"20px","margin":"10px auto","borderRadius":"27px","background":"#bfd2fc","boxSizing":"border-box"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'Ξ'" :style='{"fontSize":"14px","lineHeight":"1"}'>
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index">{{item.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 列表容器 -->
    <div class="list-preview" :style='{"width":"100%","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"20px auto","position":"relative","background":"#fff","boxSizing":"border-box","padding":"0 10px"}'>
      <!-- 查询表单 -->
      <el-form
          class="center-form-pv"
          :style="{ width: '100%', padding: '20px 6px', margin: '0 auto', background: 'rgba(0,0,0,0.102)', boxSizing: 'border-box' }"
          :inline="true"
          :model="searchForm"
      >
        <el-row :style="{ flexDirection: 'row', display: 'flex', flexWrap: 'wrap', justifyContent: 'flex-start', gap: '20px' }">
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >预约单号</label
            >
            <el-input
                v-model="searchForm.yuyuedanhao"
                placeholder="预约单号"
                clearable
                style="width: 100%;"
            ></el-input>
          </div>
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
              class="select"
              label="签到状态"
              prop="qiandaozhuangtai"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >签到状态</label
            >
            <el-select
                @change="qiandaozhuangtaiChange"
                clearable
                v-model="searchForm.qiandaozhuangtai"
                placeholder="请选择签到状态"
                style="width: 100%;"
            >
              <el-option
                  v-for="(item, index) in qiandaozhuangtaiOptions"
                  v-bind:key="index"
                  :label="item"
                  :value="item"
              ></el-option>
            </el-select>
          </div>
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
              class="select"
              label="签退状态"
              prop="qiantuizhuangtai"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >签退状态</label
            >
            <el-select
                @change="qiantuixinxiChange"
                clearable
                v-model="searchForm.qiantuizhuangtai"
                placeholder="请选择签退状态"
                style="width: 100%;"
            >
              <el-option
                  v-for="(item, index) in qiantuizhuangtaiOptions"
                  v-bind:key="index"
                  :label="item"
                  :value="item"
              ></el-option>
            </el-select>
          </div>
          <div :style="{ margin: 'auto 0 0', minWidth: '120px' }">
            <el-button
                :style="{
                border: '1px solid #97C9D6',
                cursor: 'pointer',
                padding: '0 24px',
                boxShadow: '0px 2px 2px 0px #78ABC3',
                outline: 'none',
                margin: '10px 0 0',
                color: '#78ABC3',
                borderRadius: '30px',
                background: '#fff',
                width: '100%',
                fontSize: '14px',
                height: '40px',
              }"
                type="success"
                @click="search()"
            >查询</el-button
            >
          </div>
        </el-row>
      </el-form>

      <!-- 表格 -->
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
          :data="dataList"
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
            :resizable="true"
            :sortable="false"
            prop="yuyuedanhao"
            label="预约单号"
            min-width="120"
            align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.yuyuedanhao }}
          </template>
        </el-table-column>
        <el-table-column
            prop="mingcheng"
            label="名称"
            min-width="120"
            header-align="center"
            align="center"
        >
          <template #default="{ row }">
            <div class="text-ellipsis" :title="row.mingcheng">
              {{ row.mingcheng || "-" }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
            :resizable="true"
            :sortable="false"
            prop="zuowei"
            label="座位"
            min-width="100"
            align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.zuowei }}
          </template>
        </el-table-column>
        <el-table-column
            :resizable="true"
            :sortable="false"
            prop="qiandaozhuangtai"
            label="签到状态"
            min-width="100"
            align="center"
        >
          <template slot-scope="scope">
            <el-tag :type="scope.row.qiandaozhuangtai === '已签到' ? 'success' : 'warning'">
              {{ scope.row.qiandaozhuangtai }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            :resizable="true"
            :sortable="false"
            prop="qiantuizhuangtai"
            label="签退状态"
            min-width="100"
            align="center"
        >
          <template slot-scope="scope">
            <el-tag :type="scope.row.qiantuizhuangtai === '已签退' ? 'info' : 'warning'">
              {{ scope.row.qiantuizhuangtai }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            :resizable="true"
            :sortable="false"
            prop="yuyueStart"
            label="预约开始时间"
            min-width="160"
            align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.yuyueStart || "-" }}
          </template>
        </el-table-column>
        <el-table-column
            :resizable="true"
            :sortable="false"
            prop="yuyueEnd"
            label="预约结束时间"
            min-width="160"
            align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.yuyueEnd || "-" }}
          </template>
        </el-table-column>
        <el-table-column
            :resizable="true"
            :sortable="false"
            prop="beizhu"
            label="备注"
            min-width="120"
            align="center"
        >
          <template #default="{ row }">
            <div class="remark-box">
              {{ row.beizhu || "无备注" }}
            </div>
          </template>
        </el-table-column>
        <!-- 操作列 -->
        <el-table-column
            label="操作"
            min-width="320"
            align="center"
        >
          <template slot-scope="scope">

            <div style="display: flex; gap: 6px; justify-content: center; align-items: center; flex-wrap: wrap;">
              <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-view"
                  @click="toDetail(scope.row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #409EFF; background: #409EFF; color: #fff;"
              >详情</el-button>

              <!-- 取消预约：只有 canCancel(row) 返回 true 才显示按钮 -->
              <el-button
                  v-if="canCancel(scope.row)"
                  type="danger"
                  size="mini"
                  icon="el-icon-circle-close"
                  @click="cancelOrder(scope.row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #F56C6C; background: #F56C6C; color: #fff;"
              >取消预约</el-button>

              <!-- 不可取消时显示原因文案 -->
              <span v-else style="color:#999;font-size:13px;padding:0 8px;">
                {{ cancelReason(scope.row) }}
              </span>

              <!-- 签到 / 未到 / 已过期 状态显示 -->
              <el-button
                  size="mini"
                  icon="el-icon-check"
                  v-if="scope.row.qiandaozhuangtai === '未签到' && isWithinSignInWindow(scope.row)"
                  @click="signIn(scope.row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #67C23A; background: #67C23A; color: #fff;"
              >签到</el-button>

              <span
                  v-else-if="scope.row.qiandaozhuangtai === '未签到' && isSignInBefore(scope.row)"
                  style="color: #999; font-size: 13px; padding: 0 8px;"
              >未到可签到时间</span>

              <span
                  v-else-if="scope.row.qiandaozhuangtai === '未签到' && isSignInExpired(scope.row)"
                  style="color: #f56c6c; font-size: 13px; padding: 0 8px;"
              >签到已过期</span>

              <!-- 签退按钮 / 签退已过期 -->
              <el-button
                  size="mini"
                  icon="el-icon-arrow-right"
                  v-if="scope.row.qiantuizhuangtai === '未签退' && scope.row.qiandaozhuangtai === '已签到' && isWithinSignOutWindow(scope.row)"
                  @click="signOut(scope.row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #909399; background: #909399; color: #fff;"
              >签退</el-button>

              <span
                  v-else-if="scope.row.qiantuizhuangtai === '未签退' && scope.row.qiandaozhuangtai === '已签到' && isSignOutExpired(scope.row)"
                  style="color: #f56c6c; font-size: 13px; padding: 0 8px;"
              >签退已过期</span>

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
export default {
  data() {
    return {
      baseUrl: '',
      breadcrumbItem: [{ name: '预约信息' }],
      searchForm: {
        yuyuedanhao: '',
        qiandaozhuangtai: '',
        qiantuizhuangtai: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      total: 0,
      totalPage: 0,
      dataListLoading: false,
      layouts: ["total", "prev", "pager", "next", "sizes", "jumper"],
      qiandaozhuangtaiOptions: "已签到,未签到".split(","),
      qiantuizhuangtaiOptions: "已签退,未签退".split(",")
    };
  },
  created() {
    this.baseUrl = this.$config.baseUrl;
    this.init();
    this.getDataList();
  },
  methods: {
    init() {
      this.qiandaozhuangtaiOptions = "已签到,未签到".split(",");
      this.qiantuizhuangtaiOptions = "已签退,未签退".split(",");
    },
    search() {
      this.pageIndex = 1;
      this.getDataList();
    },
    qiandaozhuangtaiChange() {},
    qiantuixinxiChange() {},
    getDataList() {
      this.dataListLoading = true;
      const token = localStorage.getItem('Token');
      const username = localStorage.getItem('username');
      if (!token || !username) {
        this.$message.error('请先登录！');
        this.$router.push('/login');
        this.dataListLoading = false;
        return;
      }

      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sfsh: '是'
      };

      if (this.searchForm.yuyuedanhao) {
        params["yuyuedanhao"] = "%" + this.searchForm.yuyuedanhao + "%";
      }
      if (this.searchForm.qiandaozhuangtai) {
        params["qiandaozhuangtai"] = this.searchForm.qiandaozhuangtai;
      }
      if (this.searchForm.qiantuizhuangtai) {
        params["qiantuizhuangtai"] = this.searchForm.qiantuizhuangtai;
      }

      this.$http.get('yuyuexinxi/list', {params: params}).then(res => {
        if (res.data.code == 0) {
          this.dataList = res.data.data.list;
          this.total = res.data.data.total;
          this.pageSize = res.data.data.pageSize;
          this.totalPage = res.data.data.totalPage;
        } else {
          this.dataList = [];
          this.total = 0;
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      }).catch(() => {
        this.dataListLoading = false;
        this.$message.error('获取预约信息失败！');
      });
    },
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    toDetail(row) {
      this.$router.push({
        path: '/index/yuyuexinxiDetail',
        query: { detailObj: JSON.stringify(row) }
      });
    },

    // 取消预约：前端再兜底校验一次
    cancelOrder(row) {
      if (!this.canCancel(row)) {
        this.$message.warning(this.cancelReason(row));
        return;
      }
      this.$router.push({
        path: '/index/yuyuexinxi-quxiaoyuyue',
        query: {
          orderData: JSON.stringify(row)
        }
      });
    },

    // 解析后端时间字符串 "yyyy-MM-dd HH:mm:ss" 为 Date
    parseDateTimeString(dateTimeStr) {
      if (!dateTimeStr || typeof dateTimeStr !== 'string') return null;
      const parts = dateTimeStr.trim().split(' ');
      if (parts.length < 2) return null;
      const datePart = parts[0];
      const timePart = parts[1];
      const [y, m, d] = datePart.split('-').map(Number);
      const [hh, mm, ss] = (timePart || '00:00:00').split(':').map(Number);
      if (![y, m, d].every(n => !isNaN(n))) return null;
      return new Date(y, (m || 1) - 1, d || 1, hh || 0, mm || 0, ss || 0);
    },

    // 是否可以取消：仅在“未到可签到时间”且未签到且未违规时允许取消
    canCancel(row) {
      if (!row) return false;
      // 获取 weigui flag（兼容两种命名）
      const wf = row.weiguiFlag != null ? row.weiguiFlag : row.weigui_flag;
      if (wf === 1 || String(wf) === '1') return false; // 已违规不可取消

      // 若已经签到 且 未签退 -> 不可取消
      if (row.qiandaozhuangtai === '已签到' && row.qiantuizhuangtai !== '已签退') return false;

      // 仅当当前时间 < 预约开始时间才允许取消（未到可签到时间）
      const start = row.yuyueStart || row.yuyue_start;
      if (!start) return false;
      const startDate = this.parseDateTimeString(start);
      if (!startDate) return false;

      return new Date().getTime() < startDate.getTime();
    },

    // 返回禁止取消的原因文本（用于展示）
    cancelReason(row) {
      if (!row) return '不可取消';
      const wf = row.weiguiFlag != null ? row.weiguiFlag : row.weigui_flag;
      if (wf === 1 || String(wf) === '1') return '不可取消（已违规）';
      if (row.qiandaozhuangtai === '已签到' && row.qiantuizhuangtai !== '已签退') return '不可取消（已签到）';
      const start = row.yuyueStart || row.yuyue_start;
      if (start) {
        const startDate = this.parseDateTimeString(start);
        if (startDate && new Date().getTime() >= startDate.getTime()) return '（预约已开始）';
      }
      return '不可取消';
    },

    // 判断当前时间是否在预约开始和结束之间（含边界）
    isNowBetween(startStr, endStr) {
      const start = this.parseDateTimeString(startStr);
      const end = this.parseDateTimeString(endStr);
      if (!start || !end) return false;
      const now = new Date();
      return now.getTime() >= start.getTime() && now.getTime() <= end.getTime();
    },

    // 用在模板上：是否可以签到（未签到且当前时间在预约区间内）
    isWithinSignInWindow(row) {
      const start = row.yuyueStart || row.yuyue_start;
      const end = row.yuyueEnd || row.yuyue_end;
      if (!start || !end) return false;
      return this.isNowBetween(start, end);
    },

    // 是否在签到开始前（用于显示“未到可签到时间”）
    isSignInBefore(row) {
      const start = row.yuyueStart || row.yuyue_start;
      if (!start) return false;
      const startDate = this.parseDateTimeString(start);
      if (!startDate) return false;
      return new Date().getTime() < startDate.getTime();
    },

    // 是否已过签到时间（当前时间 > 结束时间，且仍未签到）
    isSignInExpired(row) {
      const end = row.yuyueEnd || row.yuyue_end;
      if (!end) return false;
      const endDate = this.parseDateTimeString(end);
      if (!endDate) return false;
      return new Date().getTime() > endDate.getTime();
    },

    // 用在模板上：是否可以签退（已签到且当前时间不超过结束时间且仍在预约区间内）
    isWithinSignOutWindow(row) {
      const start = row.yuyueStart || row.yuyue_start;
      const end = row.yuyueEnd || row.yuyue_end;
      if (!start || !end) return false;
      const now = new Date();
      const endDate = this.parseDateTimeString(end);
      if (!endDate) return false;
      return now.getTime() <= endDate.getTime();
    },

    // 新增：判断已签到但已超过结束时间（签退已过期）
    isSignOutExpired(row) {
      const qiandao = row.qiandaozhuangtai === '已签到';
      const qiantuix = !(row.qiantuizhuangtai === '已签退');
      const end = row.yuyueEnd || row.yuyue_end;
      if (!qiandao || !qiantuix || !end) return false;
      const endDate = this.parseDateTimeString(end);
      if (!endDate) return false;
      return new Date().getTime() > endDate.getTime();
    },

    // 修复：调用新的签到事务接口（前端增加时间检查并确保发送 JSON）
    signIn(row) {
      // 1. 时间校验：只有在预约开始时间之后且在结束时间之前（或等于）才允许签到
      const start = row.yuyueStart || row.yuyue_start;
      const end = row.yuyueEnd || row.yuyue_end;
      if (!start || !end) {
        this.$message.warning('预约时间信息不完整，无法签到！');
        return;
      }
      if (!this.isWithinSignInWindow(row)) {
        this.$message.warning('当前不在签到时间范围内，无法签到！');
        return;
      }

      // 2. 字段校验（避免空 body 导致后端 415）
      if (!row.yuyuedanhao && !row.id) {
        this.$message.error('缺少预约单号或预约ID，无法签到！');
        return;
      }

      this.$confirm('确定签到吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        const signInData = {
          mingcheng: row.mingcheng || '',
          xuehao: localStorage.getItem('username') || '',
          shouji: localStorage.getItem('phone') || '',
          banji: localStorage.getItem('banji') || '',
          yuyuedanhao: row.yuyuedanhao,
          yuyueId: row.id // 传递预约ID
        };

        // 3. 强制以 JSON 形式发送，带 Content-Type: application/json
        let payload = JSON.stringify(signInData);
        let config = {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8'
          }
        };

        this.$http.post('yuyuexinxi/signIn', payload, config).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message.success('签到成功！');
            this.getDataList();
          } else {
            this.$message.error((data && data.msg) || '签到失败！');
          }
        }).catch((err) => {
          console.error('签到请求错误：', err);
          const status = err && err.status ? err.status : (err && err.response && err.response.status ? err.response.status : null);
          if (status === 415) {
            this.$message.error('请求格式不被服务器接受 (415)，已尝试以 JSON 发送，请检查后端是否支持 application/json。');
          } else {
            this.$message.error('签到请求失败，请重试！');
          }
        });
      }).catch(() => {
        this.$message.info('已取消签到');
      });
    },

    // 修复签退：确保发送 JSON，接收并处理后端返回（避免 415）
    signOut(row) {
      // 新增校验：只有在已签到 的情况下才允许签退（并且在预约结束时间之前）
      if (!row || row.qiandaozhuangtai !== '已签到') {
        this.$message.warning('请先签到后再签退！');
        return;
      }

      const start = row.yuyueStart || row.yuyue_start;
      const end = row.yuyueEnd || row.yuyue_end;
      if (!start || !end) {
        this.$message.warning('预约时间信息不完整，无法签退！');
        return;
      }

      const now = new Date();
      const endDate = this.parseDateTimeString(end);
      const startDate = this.parseDateTimeString(start);
      if (!endDate || !startDate) {
        this.$message.error('预约时间解析异常，无法签退！');
        return;
      }

      // 情形 A：已经超出预约结束时间（now > end）
      if (now.getTime() > endDate.getTime()) {
        // 无法签退，需将该预约标记为违纪（weigui_flag = 1）
        const payload = JSON.stringify({ id: row.id, weigui_flag: 1 });
        const cfg = { headers: { 'Content-Type': 'application/json;charset=UTF-8' } };
        this.$http.post('yuyuexinxi/update', payload, cfg).then((res) => {
          if (res && res.data && res.data.code === 0) {
            this.$message.warning('已超时未签退，已记录违规（weigui_flag=1）');
            this.getDataList();
          } else {
            this.$message.error((res && res.data && res.data.msg) || '记录违规失败，请重试！');
          }
        }).catch((err) => {
          console.error('记录违规失败：', err);
          this.$message.error('记录违规请求失败，请重试！');
        });
        return;
      }

      // 情形 B：当前时间在或早于结束时间（允许正常签退）
      this.$confirm('确定签退吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        // 1. 构造签退数据（核心：时间转字符串）
        const now2 = new Date();
        const qiantuishijianStr = now2.getFullYear() + "-" +
            String(now2.getMonth() + 1).padStart(2, '0') + "-" +
            String(now2.getDate()).padStart(2, '0') + " " +
            String(now2.getHours()).padStart(2, '0') + ":" +
            String(now2.getMinutes()).padStart(2, '0') + ":" +
            String(now2.getSeconds()).padStart(2, '0');

        const signOutData = {
          zixishiid: Number(row.zixishiid) || 0,
          zuowei: Number(row.zuowei) || 0,
          mingcheng: row.mingcheng || '',
          qiantuishijian: qiantuishijianStr,
          qiantuibeizhu: '正常签退',
          xuehao: localStorage.getItem('username') || '',
          shouji: localStorage.getItem('phone') || '',
          banji: localStorage.getItem('banji') || '',
          yuyuedanhao: row.yuyuedanhao || '',
          zixishichang: 0.0
        };

        // 2. 发送签退保存请求 — 以 JSON 发送并带 Content-Type（避免 415）
        const payload = JSON.stringify(signOutData);
        const cfg = { headers: { 'Content-Type': 'application/json;charset=UTF-8' } };

        this.$http.post('qiantuixinxi/save', payload, cfg).then((response) => {
          if (!response || !response.data) {
            this.$message.error('签退接口返回异常！');
            return Promise.reject(new Error('接口返回空数据'));
          }
          const data = response.data;
          if (data.code === 0) {
            // 成功保存签退记录后，更新预约的签退状态（同样以 JSON 发送）
            const updatePayload = JSON.stringify({
              id: row.id,
              qiantuizhuangtai: '已签退'
            });
            return this.$http.post('yuyuexinxi/update', updatePayload, cfg);
          } else {
            this.$message.error(data.msg || '签退失败！');
            return Promise.reject(new Error(data.msg || '签退失败'));
          }
        }).then((updateResponse) => {
          if (!updateResponse || !updateResponse.data) {
            this.$message.error('更新签退状态接口返回异常！');
            return;
          }
          const updateData = updateResponse.data;
          if (updateData.code === 0) {
            this.$message.success('签退成功！');
            this.getDataList();
          } else {
            this.$message.error(updateData.msg || '更新签退状态失败！');
          }
        }).catch((err) => {
          console.error('签退失败详情：', err);
          const status = err && err.status ? err.status : (err && err.response && err.response.status ? err.response.status : null);
          if (status === 415) {
            this.$message.error('签退请求格式不被服务器接受 (415)，已尝试以 JSON 发送，请检查后端是否支持 application/json。');
          } else {
            const errorMsg = err && err.response && err.response.data && err.response.data.msg
                ? err.response.data.msg
                : (err.message || '签退请求失败，请重试！');
            this.$message.error(errorMsg);
          }
        });
      }).catch(() => {
        this.$message.info('已取消签退');
      });
    }
  }
};
</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
}

.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__separator {
  margin: 0 9px;
  color: #000;
  font-weight: 500;
}
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__inner {
  color: #000;
  display: inline-block;
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

.justifyContent {
  justify-content: center;
}
.objectFit {
  object-fit: cover;
}

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
}
</style>