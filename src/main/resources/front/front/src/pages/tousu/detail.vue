<template>
  <div>
    <div :style='{"width":"1000px","padding":"20px","margin":"10px auto","borderRadius":"8px","background":"#f8f9fa","boxShadow":"0 1px 6px rgba(0, 0, 0, .08)"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'/'" :style='{"fontSize":"14px","lineHeight":"1","color":"#333"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item @click="$router.push('/index/tousu')">投诉中心</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index">{{item.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="detail-preview" :style='{"minHeight":"600px","width":"1000px","margin":"10px auto","position":"relative","height":"auto"}'>
      <div class="attr" :style='{"minHeight":"400px","padding":"20px","position":"relative","background":"#fff","display":"flex","borderRadius":"8px","boxShadow":"0 1px 6px rgba(0, 0, 0, .08)"}'>
        <div class="info" :style='{"width":"100%","padding":"20px","background":"#f8f9fa","borderRadius":"8px"}'>
          <!-- 投诉单号 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>投诉单号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.tousudanhao}}</div>
          </div>
          <!-- 关联预约单号 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>关联预约单号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.relatedOrder || '-'}}</div>
          </div>
          <!-- 投诉类型 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>投诉类型</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.tousuleixing}}</div>
          </div>
          <!-- 投诉时间 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>投诉时间</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.tousuTime || '-'}}</div>
          </div>
          <!-- 投诉人学号 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>投诉人学号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.xuehao}}</div>
          </div>
          <!-- 投诉人姓名 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>投诉人姓名</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.xingming}}</div>
          </div>
          <!-- 投诉人手机 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>投诉人手机</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.shouji}}</div>
          </div>
          <!-- 被投诉人学号 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>被投诉人学号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.beitousuXuehao || '无'}}</div>
          </div>
          <!-- 投诉原因 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>投诉原因</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"auto","minHeight":"24px","whiteSpace":"pre-wrap"}'>{{detail.tousuReason || '无'}}</div>
          </div>
          <!-- 投诉诉求 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>投诉诉求</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"auto","minHeight":"24px","whiteSpace":"pre-wrap"}'>{{detail.tousuSuqiu || '无'}}</div>
          </div>
          <!-- 处理状态 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>处理状态</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.chulizhuangtai || '处理中'}}</div>
          </div>
          <!-- 处理反馈 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>处理反馈</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"auto","minHeight":"24px","whiteSpace":"pre-wrap"}'>{{detail.feedback || '暂无处理反馈'}}</div>
          </div>
        </div>
      </div>

      <!-- 返回按钮区域 -->
      <div style="width: 100%; display: flex; justify-content: center; margin: 30px 0 20px;">
        <el-button
            :style="{
              border: '0',
              cursor: 'pointer',
              padding: '0 24px',
              outline: 'none',
              color: '#fff',
              borderRadius: '4px',
              background: '#6c757d',
              width: 'auto',
              lineHeight: '40px',
              fontSize: '14px',
              height: '40px',
              transition: 'all 0.2s ease'
            }"
            @click="goBack"
        >
          <i class="el-icon-arrow-left" style="margin-right: 6px;"></i>返回投诉列表
        </el-button>

        <!-- 新增：处理投诉按钮（管理员权限显示） -->
        <el-button
            v-if="isAuth()"
            :style="{
              border: '0',
              cursor: 'pointer',
              padding: '0 24px',
              outline: 'none',
              color: '#fff',
              borderRadius: '4px',
              background: '#2E61E1',
              width: 'auto',
              lineHeight: '40px',
              fontSize: '14px',
              height: '40px',
              transition: 'all 0.2s ease',
              marginLeft: '15px'
            }"
            @click="onSH"
        >
          <i class="el-icon-setting" style="margin-right: 6px;"></i>处理投诉
        </el-button>
      </div>

      <el-dialog title="投诉处理" v-model="sfshVisiable" width="50%" :style="{borderRadius:'8px'}">
        <el-form ref="shForm" :model="shForm" label-width="80px">
          <el-form-item label="处理状态">
            <el-select v-model="shForm.chulizhuangtai" placeholder="请选择处理状态" :style="{borderRadius:'4px'}">
              <el-option label="已处理" value="已处理"></el-option>
              <el-option label="处理中" value="处理中"></el-option>
              <el-option label="未处理" value="未处理"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="处理反馈">
            <el-input type="textarea" :rows="8" v-model="shForm.feedback" placeholder="请输入处理反馈内容（将同步给投诉人）" :style="{borderRadius:'4px'}"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="sfshVisiable = false" :style="{borderRadius:'4px'}">取 消</el-button>
          <el-button type="primary" @click="shHandler" :style="{borderRadius:'4px',background:'#2E61E1',border:'none'}">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import CountDown from '@/components/CountDown';
export default {
  data() {
    return {
      tablename: 'tousuinfo',
      baseUrl: '',
      breadcrumbItem: [
        {
          name: '投诉记录详情'
        }
      ],
      title: '',
      detailBanner: [],
      endTime: 0,
      detail: {},
      activeName: 'first',
      total: 1,
      pageSize: 5,
      pageSizes: [10,20,30,50],
      totalPage: 1,
      rules: {
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ]
      },
      buynumber: 1,
      sfshVisiable: false,
      shForm: {
        chulizhuangtai: '已处理',
        feedback: ''
      },
    }
  },
  created() {
    this.init();
  },
  methods: {
    // 返回投诉列表（优化跳转逻辑）
    goBack() {
      // 优先跳转到投诉列表页，避免历史记录问题
      this.$router.push({
        path: '/index/tousu',
        replace: true // 替换当前历史记录，避免回退循环
      });
    },
    init() {
      this.baseUrl = this.$config.baseUrl;
      // 优先从路由参数获取详情数据
      if(this.$route.query.detailObj) {
        this.detail = JSON.parse(this.$route.query.detailObj);
        // 预填充处理表单默认值
        this.shForm.chulizhuangtai = this.detail.chulizhuangtai || '已处理';
        this.shForm.feedback = this.detail.feedback || '';
      }

      // 如果有ID则请求最新详情数据
      if(this.detail.id) {
        this.$http.get(`${this.tablename}/detail/${this.detail.id}`, {})
            .then(res => {
              if (res.data.code === 0) {
                this.detail = res.data.data;
                this.title = this.detail.tousudanhao;
                // 同步更新处理表单数据
                this.shForm.chulizhuangtai = this.detail.chulizhuangtai || '已处理';
                this.shForm.feedback = this.detail.feedback || '';
              }
            })
            .catch(err => {
              console.error('获取投诉详情失败：', err);
              this.$message.error('获取投诉详情失败，请刷新页面重试');
            });
      } else {
        // 无详情数据时返回列表页
        this.$message.warning('未获取到投诉详情数据');
        setTimeout(() => this.goBack(), 1500);
      }
    },
    onAcross(acrossTable,crossOptAudit,statusColumnName,tips,statusColumnValue){
      localStorage.setItem('crossTable',`tousuinfo`);
      localStorage.setItem('crossObj', JSON.stringify(this.detail));
      localStorage.setItem('statusColumnName',statusColumnName);
      localStorage.setItem('statusColumnValue',statusColumnValue);
      localStorage.setItem('tips',tips);

      if(statusColumnName && !statusColumnName.startsWith("[")) {
        const obj = JSON.parse(localStorage.getItem('crossObj'));
        for (const o in obj) {
          if(o === statusColumnName && obj[o] === statusColumnValue) {
            this.$message({
              type: 'success',
              message: tips,
              duration: 1500
            });
            return;
          }
        }
      }

      this.$router.push({
        path: `/index/${acrossTable}Add`,
        query: {type: 'cross'},
        replace: true
      });
    },
    // 打开处理投诉弹窗
    onSH() {
      // 弹窗前同步最新状态
      this.shForm.chulizhuangtai = this.detail.chulizhuangtai || '已处理';
      this.shForm.feedback = this.detail.feedback || '';
      this.sfshVisiable = true;
    },
    // 处理投诉提交
    shHandler(){
      // 简单校验
      if(!this.shForm.feedback) {
        return this.$message.warning('请填写处理反馈内容');
      }

      this.$confirm(`确定提交处理结果？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        // 合并处理数据
        const updateData = {
          id: this.detail.id,
          chulizhuangtai: this.shForm.chulizhuangtai,
          feedback: this.shForm.feedback
        };

        this.$http.post(`${this.tablename}/update`, updateData)
            .then(res => {
              if (res.data.code === 0) {
                this.sfshVisiable = false;
                // 更新本地详情数据
                this.detail.chulizhuangtai = this.shForm.chulizhuangtai;
                this.detail.feedback = this.shForm.feedback;
                this.$message({
                  message: "处理结果提交成功",
                  type: "success",
                  duration: 1500,
                });
              } else {
                this.$message.error(res.data.msg || '处理结果提交失败');
              }
            })
            .catch(err => {
              console.error('提交处理结果失败：', err);
              this.$message.error('网络异常，处理结果提交失败');
            });
      });
    },
    curChange(page) {
      this.getDiscussList(page);
    },
    prevClick(page) {
      this.getDiscussList(page);
    },
    nextClick(page) {
      this.getDiscussList(page);
    },
    download(file){
      if(!file) {
        this.$message({
          type: 'error',
          message: '文件不存在',
          duration: 1500,
        });
        return;
      }
      window.open(`${this.baseUrl}${file}`);
    },
    // 权限控制（可根据实际需求修改）
    isAuth() {
      // 模拟管理员权限，实际可从用户信息判断
      return true;
    }
  },
  components: {
    CountDown
  }
}
</script>

<style lang="scss" scoped>
/* 修复样式语法错误：移除非法的 scss 标识，统一使用正确的深度选择器 */
.detail-preview {
  .attr {
    // 空样式占位
  }
}

// 面包屑样式优化（使用 ::v-deep 替代 >>> 避免解析错误）
.breadcrumb-preview {
  ::v-deep .el-breadcrumb__separator {
    margin: 0 8px;
    color: #666;
    font-weight: normal;
  }

  ::v-deep .el-breadcrumb__inner a {
    color: #2e61e1;
    display: inline-block;
    text-decoration: none;
  }

  ::v-deep .el-breadcrumb__inner {
    color: #333;
    display: inline-block;
    cursor: pointer; // 提示可点击
    &:hover {
      color: #2e61e1;
    }
  }
}

// 按钮hover效果
::v-deep .el-button {
  &:hover {
    opacity: 0.9;
    transform: translateY(-1px);
  }
}

// 分页样式保持简约风格
::v-deep .el-pagination {
  .el-pagination__total {
    margin: 0 10px 0 0;
    color: #666;
    font-weight: 400;
    display: inline-block;
    vertical-align: top;
    font-size: 13px;
    line-height: 28px;
    height: 28px;
  }

  .btn-prev, .btn-next {
    border: none;
    border-radius: 4px;
    padding: 0;
    margin: 0 5px;
    color: #666;
    background: #f8f9fa;
    display: inline-block;
    vertical-align: top;
    font-size: 13px;
    line-height: 28px;
    min-width: 35px;
    height: 28px;
    transition: all 0.2s ease;

    &:hover {
      background: #e8f0fe;
      color: #2e61e1;
    }

    &:disabled {
      cursor: not-allowed;
      color: #C0C4CC;
    }
  }

  .el-pager {
    padding: 0;
    margin: 0;
    display: inline-block;
    vertical-align: top;

    .number {
      cursor: pointer;
      padding: 0 4px;
      margin: 0 5px;
      color: #666;
      display: inline-block;
      vertical-align: top;
      font-size: 13px;
      line-height: 28px;
      border-radius: 4px;
      background: #f8f9fa;
      text-align: center;
      min-width: 30px;
      height: 28px;
      transition: all 0.2s ease;

      &:hover {
        color: #2e61e1;
        background: #e8f0fe;
      }

      &.active {
        cursor: default;
        color: #FFF;
        background: #2e61e1;
      }
    }
  }

  .el-pagination__sizes {
    display: inline-block;
    vertical-align: top;
    font-size: 13px;
    line-height: 28px;
    height: 28px;

    .el-input {
      margin: 0 5px;
      width: 100px;
      position: relative;

      .el-input__inner {
        border: 1px solid #e9ecef;
        cursor: pointer;
        padding: 0 25px 0 8px;
        color: #606266;
        display: inline-block;
        font-size: 13px;
        line-height: 28px;
        border-radius: 4px;
        outline: 0;
        background: #FFF;
        width: 100%;
        text-align: center;
        height: 28px;
      }
    }
  }

  .el-pagination__jump {
    margin: 0 0 0 24px;
    color: #606266;
    display: inline-block;
    vertical-align: top;
    font-size: 13px;
    line-height: 28px;
    height: 28px;

    .el-input {
      border-radius: 4px;
      padding: 0 2px;
      margin: 0 2px;
      display: inline-block;
      width: 50px;
      font-size: 14px;
      line-height: 18px;
      position: relative;
      text-align: center;
      height: 28px;

      .el-input__inner {
        border: 1px solid #e9ecef;
        cursor: pointer;
        padding: 0 3px;
        color: #606266;
        display: inline-block;
        font-size: 14px;
        line-height: 28px;
        border-radius: 4px;
        outline: 0;
        background: #FFF;
        width: 100%;
        text-align: center;
        height: 28px;
      }
    }
  }
}

// 弹窗样式优化
::v-deep .el-dialog {
  border-radius: 8px;
  overflow: hidden;

  .el-dialog__header {
    background: #f8f9fa;
    padding: 15px 20px;
    border-bottom: 1px solid #e9ecef;
  }

  .el-dialog__body {
    padding: 20px;
  }

  .el-dialog__footer {
    padding: 15px 20px;
    border-top: 1px solid #e9ecef;
    text-align: right;
  }
}

// 表单样式优化
::v-deep .el-form-item {
  margin-bottom: 15px;

  .el-form-item__label {
    font-weight: 500;
    color: #333;
  }

  .el-select .el-input__inner,
  .el-textarea__inner {
    border-radius: 4px;
    border: 1px solid #e9ecef;
    &:focus {
      border-color: #2e61e1;
    }
  }
}
</style>