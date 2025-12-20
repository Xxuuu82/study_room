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
            <el-button type="primary" @click="fetch()">查询</el-button>
            <el-button @click="reset()">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <el-table :data="list" border size="small" v-loading="loading">
        <el-table-column prop="complaintNo" label="投诉编号" width="140" />
        <el-table-column prop="userId" label="投诉人学号" width="120" />
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

        <el-table-column prop="createdAt" label="创建时间" width="170" />
        <el-table-column prop="handledAt" label="处理时间" width="170" />

        <el-table-column label="操作" width="240" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="openDetail(scope.row.id)">详情</el-button>

            <el-button
              type="text"
              size="mini"
              @click="openApprove(scope.row.id)"
              :disabled="scope.row.status === 2 || scope.row.status === 3"
            >通过</el-button>

            <el-button
              type="text"
              size="mini"
              @click="openReject(scope.row.id)"
              :disabled="scope.row.status === 2 || scope.row.status === 3"
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
    <el-dialog title="投诉详情" :visible.sync="detailVisible" width="720px">
      <div v-if="detail">
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="投诉编号">{{ detail.complaintNo }}</el-descriptions-item>
          <el-descriptions-item label="投诉人学号">{{ detail.userId }}</el-descriptions-item>
          <el-descriptions-item label="自习室">{{ detail.roomId }}</el-descriptions-item>
          <el-descriptions-item label="座位号">{{ detail.seatId }}</el-descriptions-item>
          <el-descriptions-item label="类型">{{ categoryText(detail.category) }}</el-descriptions-item>
          <el-descriptions-item label="状态">{{ statusText(detail.status) }}</el-descriptions-item>
          <el-descriptions-item label="详情" :span="2">{{ detail.detail }}</el-descriptions-item>
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
        <el-button type="primary" @click="doApprove()">确定通过</el-button>
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
        <el-button type="danger" @click="doReject()">确定驳回</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
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
    // 你按实际登录存储字段改一下
    getAdminId () {
      try {
        const u = JSON.parse(localStorage.getItem('user') || '{}')
        return u.id || u.userId || 1
      } catch (e) {
        return 1
      }
    },
    categoryText (v) {
      const m = { 1: '乱吃乱喝', 2: '大声喧哗', 3: '随地大小便', 4: '其他' }
      return m[v] || '未知'
    },
    statusText (v) {
      const m = { 0: '待处理', 1: '处理中', 2: '已结案', 3: '已驳回' }
      return m[v] || '未知'
    },
    statusTagType (v) {
      if (v === 1) return 'warning'
      if (v === 2) return 'success'
      if (v === 3) return 'danger'
      return ''
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
    async fetch () {
      this.loading = true
      try {
        const params = {
          page: this.page.page,
          limit: this.page.limit,
          complaintNo: this.q.complaintNo,
          status: this.q.status,
          category: this.q.category
        }
        const res = await api.complaintPage(params)
        if (res.code !== 0) return this.$message.error(res.msg || '加载失败')
        this.list = (res.data && res.data.list) || []
        this.total = (res.data && res.data.total) || 0
      } finally {
        this.loading = false
      }
    },
    async openDetail (id) {
      const res = await api.complaintInfo(id)
      if (res.code !== 0) return this.$message.error(res.msg || '获取详情失败')
      this.detail = res.data.complaint
      this.evidenceUrls = res.data.evidenceUrls || []
      this.detailVisible = true
    },
    openApprove (id) {
      this.approveForm = { id, handleSummary: '' }
      this.approveVisible = true
    },
    async doApprove () {
      const res = await api.complaintApprove({
        id: this.approveForm.id,
        handlerAdminId: this.handlerAdminId,
        handleSummary: this.approveForm.handleSummary
      })
      if (res.code !== 0) return this.$message.error(res.msg || '处理失败')
      this.$message.success('已通过并结案')
      this.approveVisible = false
      this.fetch()
    },
    openReject (id) {
      this.rejectForm = { id, rejectReason: '' }
      this.rejectVisible = true
    },
    async doReject () {
      if (!this.rejectForm.rejectReason) return this.$message.warning('请填写驳回原因')
      const res = await api.complaintReject({
        id: this.rejectForm.id,
        handlerAdminId: this.handlerAdminId,
        rejectReason: this.rejectForm.rejectReason
      })
      if (res.code !== 0) return this.$message.error(res.msg || '驳回失败')
      this.$message.success('已驳回')
      this.rejectVisible = false
      this.fetch()
    },
    async remove (id) {
      const res = await api.complaintDelete(id)
      if (res.code !== 0) return this.$message.error(res.msg || '删除失败')
      this.$message.success('已删除')
      this.fetch()
    }
  }
}
</script>
