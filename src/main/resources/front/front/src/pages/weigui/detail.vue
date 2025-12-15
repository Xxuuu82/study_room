<template>
  <div>
    <div :style='{"width":"1000px","padding":"20px","margin":"10px auto","borderRadius":"8px","background":"#f8f9fa","boxShadow":"0 1px 6px rgba(0, 0, 0, .08)"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'/'" :style='{"fontSize":"14px","lineHeight":"1","color":"#333"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item @click="$router.push('/index/weigui')">违规记录</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index">{{item.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="detail-preview" :style='{"minHeight":"600px","width":"1000px","margin":"10px auto","position":"relative","height":"auto"}'>
      <div class="attr" :style='{"minHeight":"400px","padding":"20px","position":"relative","background":"#fff","display":"flex","borderRadius":"8px","boxShadow":"0 1px 6px rgba(0, 0, 0, .08)"}'>
        <div class="info" :style='{"width":"100%","padding":"20px","background":"#f8f9fa","borderRadius":"8px"}'>
          <!-- 违规单号 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>违规单号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.weiguidanhao}}</div>
          </div>
          <!-- 违规类型 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>违规类型</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.weiguileixing}}</div>
          </div>
          <!-- 违规时间 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>违规时间</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.weiguiTime || '-'}}</div>
          </div>
          <!-- 关联预约单号 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>关联预约单号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.relatedOrder || '-'}}</div>
          </div>
          <!-- 处理状态 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>处理状态</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.chulizhuangtai}}</div>
          </div>
          <!-- 申诉状态 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>申诉状态</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.shensuStatus || '未申诉'}}</div>
          </div>
          <!-- 申诉处理状态 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>申诉处理状态</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.shensuHandleStatus || '未处理'}}</div>
          </div>
          <!-- 违规原因 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>违规原因</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"auto","minHeight":"24px","whiteSpace":"pre-wrap"}'>{{detail.weiguiReason || '无'}}</div>
          </div>
          <!-- 处罚措施 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>处罚措施</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.punishment || '-'}}</div>
          </div>
          <!-- 申诉处理结果 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>申诉处理结果</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"auto","minHeight":"24px","whiteSpace":"pre-wrap"}'>{{detail.shensuResult || '暂无'}}</div>
          </div>
          <!-- 学号 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>学号</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.xuehao}}</div>
          </div>
          <!-- 姓名 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>姓名</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.xingming}}</div>
          </div>
          <!-- 手机 -->
          <div class="item" :style='{"padding":"12px 16px","margin":"0 0 12px 0","background":"#fff","justifyContent":"space-between","display":"flex","borderRadius":"4px","boxShadow":"0 1px 3px rgba(0, 0, 0, .05)"}'>
            <div class="lable" :style='{"padding":"0 16px 0 0","color":"#333","textAlign":"right","width":"100px","fontSize":"14px","lineHeight":"24px","height":"24px","fontWeight":"500"}'>手机</div>
            <div  :style='{"flex":1,"padding":"0 16px","fontSize":"14px","lineHeight":"24px","color":"#666","height":"24px"}'>{{detail.shouji}}</div>
          </div>
        </div>
      </div>

      <!-- 按钮区域：返回 + 申诉 -->
      <div style="width: 100%; display: flex; justify-content: center; gap: 20px; margin: 30px 0 20px;">
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
          <i class="el-icon-arrow-left" style="margin-right: 6px;"></i>返回违规记录列表
        </el-button>

        <!-- 申诉按钮：仅当未申诉/申诉中时显示 -->
        <el-button
            v-if="detail.shensuStatus !== '申诉成功' && detail.shensuStatus !== '申诉失败'"
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
              transition: 'all 0.2s ease'
            }"
            @click="goShensu"
        >
          <i class="el-icon-edit" style="margin-right: 6px;"></i>发起申诉
        </el-button>
      </div>

      <el-dialog title="违规审核处理" :visible.sync="sfshVisiable" width="50%" :style="{borderRadius:'8px'}">
        <el-form ref="shForm" :model="shForm" label-width="80px">
          <el-form-item label="处理状态">
            <el-select v-model="shForm.chulizhuangtai" placeholder="请选择处理状态" :style="{borderRadius:'4px'}">
              <el-option label="已处理" value="已处理"></el-option>
              <el-option label="处理中" value="处理中"></el-option>
              <el-option label="未处理" value="未处理"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="申诉处理状态">
            <el-select v-model="shForm.shensuHandleStatus" placeholder="请选择申诉处理状态" :style="{borderRadius:'4px'}">
              <el-option label="已处理" value="已处理"></el-option>
              <el-option label="处理中" value="处理中"></el-option>
              <el-option label="未处理" value="未处理"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="处理结果/申诉回复">
            <el-input type="textarea" :rows="8" v-model="shForm.shensuResult" :style="{borderRadius:'4px'}"></el-input>
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
      tablename: 'weiguiinfo',
      baseUrl: '',
      breadcrumbItem: [
        {
          name: '违规记录详情'
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
        shensuHandleStatus: '未处理',
        shensuResult: ''
      },
    }
  },
  created() {
    this.init();
  },
  methods: {
    // 返回违规列表页
    goBack() {
      this.$router.push('/index/weigui');
    },
    // 跳转到申诉页面
    goShensu() {
      // 将当前违规记录存入localStorage，供申诉页读取
      localStorage.setItem("currentWeiguiObj", JSON.stringify(this.detail));
      // 跳转到申诉页（对应router.js中的/index/weiguiShensu路由）
      this.$router.push('/index/weiguiShensu');
    },
    init() {
      this.baseUrl = this.$config.baseUrl;
      // 优先从localStorage读取违规记录（列表页跳转过来的场景）
      if (localStorage.getItem("currentWeiguiObj")) {
        this.detail = JSON.parse(localStorage.getItem("currentWeiguiObj"));
      }
      // 兼容路由参数传递的场景
      else if(this.$route.query.detailObj) {
        this.detail = JSON.parse(this.$route.query.detailObj);
      }

      // 如果有详情ID，调用接口获取最新数据
      if(this.detail.id) {
        this.$http.get(this.tablename + '/detail/'  + this.detail.id, {}).then(res => {
          if (res.data.code == 0) {
            this.detail = res.data.data;
            this.title = this.detail.weiguidanhao;
            this.$forceUpdate();
          }
        });
      } else {
        this.$message.warning("未获取到违规记录信息！");
        setTimeout(() => {
          this.goBack();
        }, 1500);
      }
    },
    onAcross(acrossTable,crossOptAudit,statusColumnName,tips,statusColumnValue){
      localStorage.setItem('crossTable',`weiguiinfo`);
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
        // 合并处理数据
        const updateData = {
          ...this.detail,
          chulizhuangtai: this.shForm.chulizhuangtai,
          shensuHandleStatus: this.shForm.shensuHandleStatus,
          shensuResult: this.shForm.shensuResult
        };
        this.$http.post(`${this.tablename}/update`, updateData).then(res => {
          if (res.data.code == 0) {
            this.sfshVisiable = false;
            this.detail = updateData;
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
/* 样式部分保持与预约详情一致 */
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
  cursor: pointer; /* 添加鼠标指针，提示可点击 */
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