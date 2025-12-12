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
                @change="qiantuizhuangtaiChange"
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
        <!-- 新增：预约开始时间列 -->
        <el-table-column
            :resizable="true"
            :sortable="false"
            prop="yuyue_start"
            label="预约开始时间"
            min-width="160"
            align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.yuyue_start }}
          </template>
        </el-table-column>
        <!-- 新增：预约结束时间列 -->
        <el-table-column
            :resizable="true"
            :sortable="false"
            prop="yuyue_end"
            label="预约结束时间"
            min-width="160"
            align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.yuyue_end }}
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
              {{ row.qiantuibeizhu || "无备注" }}
            </div>
          </template>
        </el-table-column>
        <!-- 操作列 -->
        <el-table-column
            label="操作"
            min-width="280"
            align="center"
        >
          <template slot-scope="scope">
            <div style="display: flex; gap: 6px; justify-content: center; align-items: center; flex-wrap: wrap;">
              <!-- 详情按钮 -->
              <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-view"
                  @click="toDetail(scope.row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #409EFF; background: #409EFF; color: #fff;"
              >详情</el-button
              >
              <!-- 取消预约按钮 -->
              <el-button
                  type="danger"
                  size="mini"
                  icon="el-icon-circle-close"
                  @click="cancelOrder(scope.row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #F56C6C; background: #F56C6C; color: #fff;"
              >取消预约</el-button
              >
              <!-- 签到按钮（未签到显示） -->
              <el-button
                  size="mini"
                  icon="el-icon-check"
                  v-if="scope.row.qiandaozhuangtai === '未签到'"
                  @click="signIn(scope.row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #67C23A; background: #67C23A; color: #fff;"
              >签到</el-button
              >
              <!-- 签退按钮（未签退显示） -->
              <el-button
                  size="mini"
                  icon="el-icon-arrow-right"
                  v-if="scope.row.qiantuizhuangtai === '未签退'"
                  @click="signOut(scope.row)"
                  style="border-radius: 4px; padding: 0 10px; height: 32px; line-height: 32px; transition: all 0.2s; border-color: #909399; background: #909399; color: #fff;"
              >签退</el-button
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
      total: 0, // 修正：分页总数字段名
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
    qiantuizhuangtaiChange() {},
    getDataList() {
      this.dataListLoading = true;
      // ✅ 关键修改：替换登录校验逻辑（用已存储的Token/用户名替代不存在的userid）
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

      // 移除多余的排序参数，保持和原代码一致
      if (this.searchForm.yuyuedanhao) {
        params["yuyuedanhao"] = "%" + this.searchForm.yuyuedanhao + "%";
      }
      if (this.searchForm.qiandaozhuangtai) {
        params["qiandaozhuangtai"] = this.searchForm.qiandaozhuangtai;
      }
      if (this.searchForm.qiantuizhuangtai) {
        params["qiantuizhuangtai"] = this.searchForm.qiantuizhuangtai;
      }

      // 修正：使用和原代码一致的get请求方式
      this.$http.get('yuyuexinxi/list', {params: params}).then(res => {
        if (res.data.code == 0) {
          this.dataList = res.data.data.list;
          this.total = res.data.data.total; // 修正：分页总数赋值
          this.pageSize = res.data.data.pageSize;
          this.totalPage = res.data.data.totalPage;
          console.log('预约列表数据:', this.dataList);
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
    // 取消预约（直接跳转，不再有确认对话框）
    cancelOrder(row) {
      if (!row.zixishiid) {
        console.warn('当前预约单缺少自习室ID(zixishiid)，请检查后端接口返回数据！');
      }
      this.$router.push({
        path: '/index/yuyuexinxi-quxiaoyuyue',
        query: {
          orderData: JSON.stringify(row)
        }
      });
    },

    // ========== 仅修改这部分：签到方法（新增写入数据库逻辑） ==========
    signIn(row) {
      this.$confirm('确定签到吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        // 新增：日期格式化函数
        const formatDate = (date) => {
          const y = date.getFullYear();
          const m = String(date.getMonth() + 1).padStart(2, '0');
          const d = String(date.getDate()).padStart(2, '0');
          const h = String(date.getHours()).padStart(2, '0');
          const min = String(date.getMinutes()).padStart(2, '0');
          const s = String(date.getSeconds()).padStart(2, '0');
          return `${y}-${m}-${d} ${h}:${min}:${s}`;
        };

        // 步骤1：先写入签到信息到qiandaoxinxi表（日期用格式化后的字符串）
        const signInData = {
          mingcheng: row.mingcheng || '',
          qiandaoshijian: formatDate(new Date()), // 转为后端支持的格式
          qiandaobeizhu: '正常签到',
          xuehao: localStorage.getItem('username') || '',
          shouji: localStorage.getItem('phone') || '',
          banji: localStorage.getItem('banji') || '',
          addtime: formatDate(new Date()) // 转为后端支持的格式
        };

        // 调用签到添加接口
        this.$http.post('qiandaoxinxi/add', signInData).then(() => {
          // 步骤2：更新预约状态（原有逻辑保留）
          return this.$http.post('yuyuexinxi/update', {
            id: row.id,
            qiandaozhuangtai: '已签到'
          });
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message.success('签到成功！');
            this.getDataList();
          } else {
            this.$message.error(data.msg || '签到失败！');
          }
        }).catch(() => {
          this.$message.error('签到请求失败，请重试！');
        });
      }).catch(() => {
        this.$message.info('已取消签到');
      });
    },

    // ========== 仅修改这部分：签退方法（新增写入数据库逻辑） ==========
    signOut(row) {
      this.$confirm('确定签退吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        // 复用日期格式化函数
        const formatDate = (date) => {
          const y = date.getFullYear();
          const m = String(date.getMonth() + 1).padStart(2, '0');
          const d = String(date.getDate()).padStart(2, '0');
          const h = String(date.getHours()).padStart(2, '0');
          const min = String(date.getMinutes()).padStart(2, '0');
          const s = String(date.getSeconds()).padStart(2, '0');
          return `${y}-${m}-${d} ${h}:${min}:${s}`;
        };

        // 步骤1：先写入签退信息到qiantuixinxi表（日期格式化+字段类型修正）
        const signOutData = {
          zixishiid: Number(row.zixishiid) || 0, // 确保是数字类型
          zuowei: Number(row.zuowei) || 0, // 确保是数字类型
          mingcheng: row.mingcheng || '',
          qiantuishijian: formatDate(new Date()), // 日期格式化
          qiantuibeizhu: '正常签退',
          xuehao: localStorage.getItem('username') || '',
          shouji: localStorage.getItem('phone') || '',
          banji: localStorage.getItem('banji') || '',
          addtime: formatDate(new Date()) // 日期格式化
        };

        // 调用签退保存接口
        this.$http.post('qiantuixinxi/save', signOutData).then(() => {
          // 步骤2：更新预约状态
          return this.$http.post('yuyuexinxi/update', {
            id: row.id,
            qiantuizhuangtai: '已签退'
          });
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message.success('签退成功！');
            this.getDataList();
          } else {
            this.$message.error(data.msg || '签退失败！');
          }
        }).catch(() => {
          this.$message.error('签退请求失败，请重试！');
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

/* 按钮悬停效果 */
.el-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

/* 修正样式拼写错误 */
.justifyContent {
  justify-content: center;
}
.objectFit {
  object-fit: cover;
}

/* 小屏幕适配 */
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