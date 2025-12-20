<template>
  <div>
    <el-card>
      <div style="margin-bottom: 12px;">
        <el-form :inline="true" size="small">
          <el-form-item label="投诉编号">
            <el-input v-model="q.complaintNo" placeholder="模糊查询" clearable />
          </el-form-item>

          <el-form-item label="状态">
            <el-select v-model="q.status" placeholder="全部" clearable style="width: 140px;">
              <el-option label="待处理" :value="0" />
              <el-option label="处理中" :value="1" />
              <el-option label="已结案" :value="2" />
              <el-option label="已驳回" :value="3" />
            </el-select>
          </el-form-item>

          <el-form-item label="类型">
            <el-select v-model="q.category" placeholder="全部" clearable style="width: 160px;">
              <el-option label="乱吃乱喝" :value="1" />
              <el-option label="大声喧哗" :value="2" />
              <el-option label="随地大小便" :value="3" />
              <el-option label="其他" :value="4" />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSearch">查询</el-button>
            <el-button @click="reset">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <el-table :data="list" border size="small" v-loading="loading">
        <el-table-column prop="complaintNo" label="投诉编号" width="160" />
        <el-table-column prop="userId" label="投诉人学号" width="130" />
        <el-table-column prop="roomId" label="自习室" width="90" />
        <el-table-column prop="seatId" label="座位号" width="90" />

        <el-table-column prop="category" label="类型" width="110">
          <template slot-scope="scope">
            <el-tag size="mini">{{ categoryText(scope.row.category) }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="detail" label="投诉详情" min-width="220" show-overflow-tooltip />

        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag size="mini" :type="statusTagType(scope.row.status)">
              {{ statusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="创建时间" width="170">
          <template slot-scope="scope">
            {{ formatTime(scope.row.createdAt) }}
          </template>
        </el-table-column>

        <el-table-column label="处理时间" width="170">
          <template slot-scope="scope">
            {{ formatTime(scope.row.handledAt) }}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="240" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="openDetail(scope.row)">详情</el-button>

            <el-button
              type="text"
              size="mini"
              @click="openApprove(scope.row)"
              :disabled="!canApprove(scope.row)"
            >通过</el-button>

            <el-button
              type="text"
              size="mini"
              @click="openReject(scope.row)"
              :disabled="!canReject(scope.row)"
            >驳回</el-button>

            <el-popconfirm title="确定删除？" @confirm="remove(scope.row.id)">
              <el-button slot="reference" type="text" size="mini" style="color:#f56c6c;">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-top: 12px; text-align: right;">
        <el-pagination
          background
          layout="total, prev, pager, next, sizes"
          :total="total"
          :page-size="page.limit"
          :current-page="page.page"
          :page-sizes="[10, 20, 50]"
          @size-change="onSizeChange"
          @current-change="onPageChange"
        />
      </div>
    </el-card>

    <!-- 详情 -->
    <el-dialog title="投诉详情" :visible.sync="detailVisible" width="760px">
      <div v-if="detail">
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="投诉编号">{{ detail.complaintNo }}</el-descriptions-item>
          <el-descriptions-item label="投诉人学号">{{ detail.userId }}</el-descriptions-item>
          <el-descriptions-item label="自习室">{{ detail.roomId }}</el-descriptions-item>
          <el-descriptions-item label="座位号">{{ detail.seatId }}</el-descriptions-item>
          <el-descriptions-item label="类型">{{ categoryText(detail.category) }}</el-descriptions-item>
          <el-descriptions-item label="状态">{{ statusText(detail.status) }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatTime(detail.createdAt) }}</el-descriptions-item>
          <el-descriptions-item label="处理时间">{{ formatTime(detail.handledAt) }}</el-descriptions-item>

          <el-descriptions-item label="投诉详情" :span="2">{{ detail.detail }}</el-descriptions-item>
          <el-descriptions-item label="处理摘要" :span="2">{{ detail.handleSummary || '-' }}</el-descriptions-item>
          <el-descriptions-item label="驳回原因" :span="2">{{ detail.rejectReason || '-' }}</el-descriptions-item>
        </el-descriptions>

        <div style="margin-top: 14px;">
          <div style="font-weight: 600; margin-bottom: 8px;">证据图片</div>
          <div v-if="evidenceUrls.length === 0">无</div>
          <el-image
            v-for="(u, i) in evidenceUrls"
            :key="i"
            :src="u"
            style="width: 120px; height: 120px; margin-right: 10px;"
            :preview-src-list="evidenceUrls"
          />
        </div>
      </div>
      <span slot="footer">
        <el-button @click="detailVisible=false">关闭</el-button>
      </span>
    </el-dialog>

    <!-- 通过 -->
    <el-dialog title="通过并结案" :visible.sync="approveVisible" width="520px">
      <el-form label-width="90px" size="small">
        <el-form-item label="处理摘要">
          <el-input type="textarea" v-model="approveForm.handleSummary" :rows="4" placeholder="填写处理说明（可选）" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="approveVisible=false">取消</el-button>
        <el-button type="primary" @click="doApprove">确定通过</el-button>
      </span>
    </el-dialog>

    <!-- 驳回 -->
    <el-dialog title="驳回投诉" :visible.sync="rejectVisible" width="520px">
      <el-form label-width="90px" size="small">
        <el-form-item label="驳回原因" required>
          <el-input type="textarea" v-model="rejectForm.rejectReason" :rows="4" placeholder="必填：为什么驳回" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="rejectVisible=false">取消</el-button>
        <el-button type="danger" @click="doReject">确定驳回</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import http from '@/utils/http'
import api from '@/utils/api'

export default {
  name: 'Complaint',
  data () {
    return {
      loading: false,
      list: [],
      total: 0,
      page: { page: 1, limit: 10 },
      q: { complaintNo: '', status: null, category: null },

      detailVisible: false,
      detail: null,
      evidenceUrls: [],

      approveVisible: false,
      approveForm: { id: null, handleSummary: '' },

      rejectVisible: false,
      rejectForm: { id: null, rejectReason: '' },

      handlerAdminId: this.getAdminId()
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    // ===== 管理员ID（按你项目存储改）=====
    getAdminId () {
      try {
        const u = JSON.parse(localStorage.getItem('user') || '{}')
        return u.id || u.userId || 1
      } catch (e) {
        return 1
      }
    },

    // ===== 时间格式化：兼容毫秒时间戳/Date/字符串 =====
    formatTime (v) {
      if (!v) return '-'
      // 如果是毫秒时间戳（你截图里 1765...）
      if (typeof v === 'number' || /^\d{13}$/.test(String(v))) {
        const d = new Date(Number(v))
        if (isNaN(d.getTime())) return String(v)
        return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} `
          + `${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`
      }
      return String(v)
    },

    // ===== 字段归一化：兼容下划线/驼峰 + 强制 number =====
    normalizeRow (r) {
      const row = Object.assign({}, r || {})

      // id 兜底（防止后端用别名）
      row.id = row.id || row.complaint_id || row.complaintId || null

      row.complaintNo = row.complaintNo || row.complaint_no || ''
      row.userId = row.userId || row.user_id || ''
      row.roomId = row.roomId || row.room_id || ''
      row.seatId = row.seatId || row.seat_id || ''
      row.createdAt = row.createdAt || row.created_at || ''
      row.handledAt = row.handledAt || row.handled_at || ''
      row.evidenceUrls = row.evidenceUrls || row.evidence_urls || ''
      row.handleSummary = row.handleSummary || row.handle_summary || ''
      row.rejectReason = row.rejectReason || row.reject_reason || ''
      row.handlerAdminId = row.handlerAdminId || row.handler_admin_id || null

      row.status = row.status != null ? Number(row.status) : 0
      row.category = row.category != null ? Number(row.category) : 0

      return row
    },
    parseEvidence (detail) {
      let urls = []
      try {
        const v = detail.evidenceUrls
        if (Array.isArray(v)) urls = v
        else if (typeof v === 'string' && v.trim()) urls = JSON.parse(v)
      } catch (e) {
        urls = []
      }
      this.evidenceUrls = Array.isArray(urls) ? urls : []
    },

    // ===== 按钮规则：仅“已结案(2)”彻底锁死；驳回(3)允许重新通过/再驳回 =====
    canApprove (row) {
      const s = row && row.status != null ? Number(row.status) : 0
      return s !== 2
    },
    canReject (row) {
      const s = row && row.status != null ? Number(row.status) : 0
      return s !== 2
    },

    categoryText (v) {
      const m = { 1: '乱吃乱喝', 2: '大声喧哗', 3: '随地大小便', 4: '其他' }
      return m[Number(v)] || '未知'
    },
    statusText (v) {
      const m = { 0: '待处理', 1: '处理中', 2: '已结案', 3: '已驳回' }
      return m[Number(v)] || '未知'
    },
    statusTagType (v) {
      const s = Number(v)
      if (s === 1) return 'warning'
      if (s === 2) return 'success'
      if (s === 3) return 'danger'
      return ''
    },

    onSearch () {
      this.page.page = 1
      this.fetch()
    },
    reset () {
      this.q = { complaintNo: '', status: null, category: null }
      this.page.page = 1
      this.fetch()
    },
    onSizeChange (s) {
      this.page.limit = s
      this.page.page = 1
      this.fetch()
    },
    onPageChange (p) {
      this.page.page = p
      this.fetch()
    },

    // ===== 列表 =====
    async fetch () {
      this.loading = true
      try {
        const params = {
          page: this.page.page,
          limit: this.page.limit,
          complaintNo: this.q.complaintNo || undefined,
          status: this.q.status === null ? undefined : this.q.status,
          category: this.q.category === null ? undefined : this.q.category
        }

        const { data: res } = await http.get(api.complaintpage, { params })
        if (!res || res.code !== 0) return this.$message.error((res && res.msg) || '加载失败')

        const data = res.data || {}
        const arr = data.list || data.records || []
        this.list = (arr || []).map(x => this.normalizeRow(x))
        this.total = data.total || 0
      } catch (e) {
        this.$message.error('请求失败，请检查后端接口/登录状态')
      } finally {
        this.loading = false
      }
    },

    // ===== 详情：先用“列表行”填充，保证信息/状态对得上；再请求后端补全 =====
    async openDetail (row) {
      const base = this.normalizeRow(row)
      this.detail = base
      this.parseEvidence(base)
      this.detailVisible = true

      // 再去请求后端详情（如果后端返回 null，也不会把弹窗变空）
      if (!base.id) return
      try {
        const { data: res } = await http.get(api.complaintinfo + base.id)
        if (!res || res.code !== 0) return

        // 兼容两种返回：data = entity 或 data = { complaint: entity }
        const d = res.data && res.data.complaint ? res.data.complaint : res.data
        if (!d) return

        const full = this.normalizeRow(d)
        // 用后端返回覆盖（但保持不为空）
        this.detail = Object.assign({}, this.detail, full)
        this.parseEvidence(this.detail)
      } catch (e) {
        // 忽略：保持列表行信息展示
      }
    },

    // ===== 通过/驳回 =====
    openApprove (row) {
      const r = this.normalizeRow(row)
      this.approveForm = { id: r.id, handleSummary: '' }
      this.approveVisible = true
    },
    async doApprove () {
      try {
        const { data: res } = await http.post(api.complaintapprove, {
          id: this.approveForm.id,
          handlerAdminId: this.handlerAdminId,
          handleSummary: this.approveForm.handleSummary
        })
        if (!res || res.code !== 0) return this.$message.error((res && res.msg) || '处理失败')
        this.$message.success('已通过并结案')
        this.approveVisible = false
        this.fetch()
      } catch (e) {
        this.$message.error('请求失败')
      }
    },

    openReject (row) {
      const r = this.normalizeRow(row)
      this.rejectForm = { id: r.id, rejectReason: '' }
      this.rejectVisible = true
    },
    async doReject () {
      if (!this.rejectForm.rejectReason) return this.$message.warning('请填写驳回原因')
      try {
        const { data: res } = await http.post(api.complaintreject, {
          id: this.rejectForm.id,
          handlerAdminId: this.handlerAdminId,
          rejectReason: this.rejectForm.rejectReason
        })
        if (!res || res.code !== 0) return this.$message.error((res && res.msg) || '驳回失败')
        this.$message.success('已驳回')
        this.rejectVisible = false
        this.fetch()
      } catch (e) {
        this.$message.error('请求失败')
      }
    },

    // ===== 删除（按你项目风格：/delete + body: Long[]）=====
    async remove (id) {
      try {
        const { data: res } = await http.post(api.complaintdelete, [id])
        if (!res || res.code !== 0) return this.$message.error((res && res.msg) || '删除失败')
        this.$message.success('已删除')
        this.fetch()
      } catch (e) {
        this.$message.error('请求失败')
      }
    }
  }
}
</script>
