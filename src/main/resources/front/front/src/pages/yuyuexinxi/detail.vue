<template>
  <div>
    <div :style='{"width":"1000px","padding":"20px","margin":"10px auto","borderRadius":"8px","background":"#f8f9fa","boxShadow":"0 1px 6px rgba(0, 0, 0, .08)"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'/'" :style='{"fontSize":"14px","lineHeight":"1","color":"#333"}'>
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index">{{item.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="detail-preview" :style='{"minHeight":"600px","width":"1000px","margin":"10px auto","position":"relative","height":"auto"}'>
      <div class="attr" :style='{"minHeight":"400px","padding":"20px","position":"relative","background":"#fff","display":"flex","borderRadius":"8px","boxShadow":"0 1px 6px rgba(0, 0, 0, .08)"}'>
        <div class="info" :style='{"width":"100%","padding":"20px","background":"#f8f9fa","borderRadius":"8px"}'>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","alignItems":"center","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div :style='{"color":"#2e61e1","fontSize":"18px","fontWeight":"500"}'>
              {{detail.qiandaozhuangtai}}
            </div>
          </div>

          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>预约单号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.yuyuedanhao}}</div>
          </div>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>名称</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.mingcheng}}</div>
          </div>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>座位</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.zuowei}}</div>
          </div>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>签到状态</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.qiandaozhuangtai}}</div>
          </div>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>预约时间</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.yuyueshijian}}</div>
          </div>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>备注</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.beizhu}}</div>
          </div>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>学号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.xuehao}}</div>
          </div>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>姓名</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.xingming}}</div>
          </div>
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>手机</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.shouji}}</div>
          </div>
          <!-- 已删除“取消预约”“签到”“审核”按钮 -->
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
          <i class="el-icon-arrow-left" style="margin-right: 6px;"></i>返回预约列表
        </el-button>
      </div>

      <!-- 已删除下方的el-tabs标签页区域 -->
      <el-dialog title="审核" :visible.sync="sfshVisiable" width="50%" :style="{borderRadius:'8px'}">
        <el-form ref="shForm" :model="shForm" label-width="80px">
          <el-form-item label="审核状态">
            <el-select v-model="shForm.sfsh" placeholder="审核状态" :style="{borderRadius:'4px'}">
              <el-option label="通过" value="是"></el-option>
              <el-option label="不通过" value="否"></el-option>
              <el-option label="待审核" value="待审核"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="内容">
            <el-input type="textarea" :rows="8" v-model="shForm.shhf" :style="{borderRadius:'4px'}"></el-input>
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
  //数据集合
  data() {
    return {
      tablename: 'yuyuexinxi',
      baseUrl: '',
      breadcrumbItem: [
        {
          name: '详情信息'
        }
      ],
      title: '',
      detailBanner: [],
      endTime: 0,
      detail: {},
      activeName: 'first', // 可保留（无实际影响）
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
        sfsh: '是',
        shhf: ''
      },
    }
  },
  created() {
    this.init();
  },
  //方法集合
  methods: {
    // 返回列表页方法
    goBack() {
      this.$router.push('/index/yuyuexinxi');
    },
    init() {
      this.baseUrl = this.$config.baseUrl;
      if(this.$route.query.detailObj) {
        this.detail = JSON.parse(this.$route.query.detailObj);
      }
      this.$http.get(this.tablename + '/detail/'  + this.detail.id, {}).then(res => {
        if (res.data.code == 0) {
          this.detail = res.data.data;
          this.title = this.detail.qiandaozhuangtai;
          this.$forceUpdate();
        }
      });
    },
    onAcross(acrossTable,crossOptAudit,statusColumnName,tips,statusColumnValue){
      localStorage.setItem('crossTable',`yuyuexinxi`);
      localStorage.setItem('crossObj', JSON.stringify(this.detail));
      localStorage.setItem('statusColumnName',statusColumnName);
      localStorage.setItem('statusColumnValue',statusColumnValue);
      localStorage.setItem('tips',tips);
      if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
        var obj = JSON.parse(localStorage.getItem('crossObj'));
        for (var o in obj){
          if(o==statusColumnName && obj[o]==statusColumnValue){
            this.$message({
              type: 'success',
              message: tips,
              duration: 1500
            });
            return
          }
        }
      }
      this.$router.push({path: '/index/' + acrossTable + 'Add', query: {type: 'cross'}});
    },
    onSH() {
      this.sfshVisiable = true;
    },
    shHandler(){
      this.$confirm(`确定操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.detail.sfsh = this.shForm.sfsh;
        this.detail.shhf = this.shForm.shhf;
        this.$http.post(`${this.tablename}/update`, this.detail).then(res => {
          if (res.data.code == 0) {
            this.sfshVisiable = false;
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
            });
          }
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
      window.open(this.baseUrl+file)
    },
    // 补充isAuth方法（避免按钮权限判断报错）
    isAuth() {
      return true;
    }
  },
  components: {
    CountDown
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.detail-preview {
  .attr {
  }
}

// 面包屑样式优化
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__separator {
  margin: 0 8px;
  color: #666;
  font-weight: normal;
}

.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__inner a {
  color: #2e61e1;
  display: inline-block;
  text-decoration: none;
}

.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__inner {
  color: #333;
  display: inline-block;
}

// 按钮hover效果
.btn >>> .el-button:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

// 返回按钮hover效果
::v-deep .el-button:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

// 分页样式保持简约风格
.el-pagination >>> .el-pagination__total {
  margin: 0 10px 0 0;
  color: #666;
  font-weight: 400;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.el-pagination >>> .btn-prev {
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
}

.el-pagination >>> .btn-next {
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
}

.el-pagination >>> .btn-prev:hover,
.el-pagination >>> .btn-next:hover {
  background: #e8f0fe;
  color: #2e61e1;
}

.el-pagination >>> .btn-prev:disabled {
  border: none;
  cursor: not-allowed;
  border-radius: 4px;
  padding: 0;
  margin: 0 5px;
  color: #C0C4CC;
  background: #f8f9fa;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.el-pagination >>> .btn-next:disabled {
  border: none;
  cursor: not-allowed;
  border-radius: 4px;
  padding: 0;
  margin: 0 5px;
  color: #C0C4CC;
  background: #f8f9fa;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.el-pagination >>> .el-pager {
  padding: 0;
  margin: 0;
  display: inline-block;
  vertical-align: top;
}

.el-pagination >>> .el-pager .number {
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
}

.el-pagination >>> .el-pager .number:hover {
  cursor: pointer;
  padding: 0 4px;
  margin: 0 5px;
  color: #2e61e1;
  background: #e8f0fe;
}

.el-pagination >>> .el-pager .number.active {
  cursor: default;
  padding: 0 4px;
  margin: 0 5px;
  color: #FFF;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  border-radius: 4px;
  background: #2e61e1;
  text-align: center;
  min-width: 30px;
  height: 28px;
}

.el-pagination >>> .el-pagination__sizes {
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.el-pagination >>> .el-pagination__sizes .el-input {
  margin: 0 5px;
  width: 100px;
  position: relative;
}

.el-pagination >>> .el-pagination__sizes .el-input .el-input__inner {
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

.el-pagination >>> .el-pagination__jump {
  margin: 0 0 0 24px;
  color: #606266;
  display: inline-block;
  vertical-align: top;
  font-size: 13px;
  line-height: 28px;
  height: 28px;
}

.el-pagination >>> .el-pagination__jump .el-input {
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
}

.el-pagination >>> .el-pagination__jump .el-input .el-input__inner {
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
</style>