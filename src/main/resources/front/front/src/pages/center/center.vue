<template>
  <div :style='{"width":"80%","margin":"10px auto","position":"relative","background":"#fff","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)"}'>
    <!-- 标题等同你现有布局（保持不变） -->
    <div class="title" :style='{"margin":"0","color":"#333","textAlign":"center","background":"rgba(0,0,0,0.102)","fontSize":"20px","lineHeight":"54px","fontWeight":"600"}'>{{ title }}</div>

    <div :style='{"display":"flex","padding":"20px"}'>
      <div :style='{"width":"180px","textAlign":"center","background":"#f5f7fa","borderRadius":"8px","padding":"20px","marginRight":"20px"}'>
        <div :style='{"marginBottom":"15px","cursor":"pointer"}' @click="avatarClickTip">
          <img
              v-if="sessionForm.touxiang"
              :src="sessionForm.touxiang"
              :style='{"width":"100px","height":"100px","borderRadius":"50%","border":"2px solid #fff","boxShadow":"0 2px 8px rgba(0,0,0,0.1)"}'
          />
          <div
              v-else
              :style='{"width":"100px","height":"100px","borderRadius":"50%","background":"#e6f0ff","color":"#409eff","fontSize":"36px","lineHeight":"100px","display":"inline-block","textAlign":"center"}'>
            <i class="el-icon-user"></i>
          </div>

          <!-- 保留 el-upload，但不要在里面手动放 <input>（这会导致页面上出现两个 file input，进而触发两次选择框） -->
          <el-upload
              ref="avatarUploader"
              class="avatar-uploader"
              :action="uploadUrl"
              :headers="headers"
              :show-file-list="false"
              :auto-upload="false"
              style="display: none;">
          </el-upload>
        </div>

        <div :style='{"fontSize":"14px","color":"#333","padding":"8px 0","background":"#fff","borderRadius":"4px","marginBottom":"20px"}'>
          学号：{{ sessionForm.xuehao || '未填写' }}
        </div>

        <div :style='{"display":"flex","flexDirection":"column","gap":"10px"}'>
          <el-button type="primary" plain @click="switchTab('个人信息')" :style="tabStyle('个人信息')">个人信息</el-button>
          <el-button type="primary" plain @click="switchTab('修改头像')" :style="tabStyle('修改头像')">修改头像</el-button>
          <el-button type="primary" plain @click="switchTab('修改密码')" :style="tabStyle('修改密码')">修改密码</el-button>
          <el-button type="primary" plain @click="switchTab('取消预约信息')" :style="tabStyle('取消预约信息')">取消预约信息</el-button>
        </div>
      </div>

      <div :style='{"flex":"1","background":"#f5f7fa","borderRadius":"8px","padding":"20px"}'>
        <div v-if="activeTab === '个人信息'" :style='{"background":"#fff","borderRadius":"8px","padding":"20px","boxShadow":"0 2px 8px rgba(0,0,0,0.05)"}'>
          <el-form ref="sessionForm" :model="sessionForm" :rules="rules" label-width="80px" :style='{"width":"100%","maxWidth":"500px","margin":"0 auto"}'>
            <el-form-item label="姓名" prop="xingming" :style='{"marginBottom":"20px"}'>
              <el-input v-model="sessionForm.xingming" placeholder="姓名" style="width: 300px;"></el-input>
            </el-form-item>
            <el-form-item label="性别" :style='{"marginBottom":"20px"}'>
              <el-select v-model="sessionForm.xingbie" placeholder="请选择性别" style="width: 300px;">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="手机" prop="shouji" :style='{"marginBottom":"20px"}'>
              <el-input v-model="sessionForm.shouji" placeholder="手机" style="width: 300px;"></el-input>
            </el-form-item>
            <el-form-item label="班级" prop="banji" :style='{"marginBottom":"20px"}'>
              <el-input v-model="sessionForm.banji" placeholder="班级" style="width: 300px;"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit('sessionForm')" :style='{"background":"#409EFF","color":"#fff","border":"none","padding":"0 20px","height":"40px"}'>更新信息</el-button>
            </el-form-item>
          </el-form>
        </div>

        <div v-if="activeTab === '修改头像'" :style='{"background":"#fff","borderRadius":"8px","padding":"30px 40px","textAlign":"center","boxShadow":"0 2px 8px rgba(0,0,0,0.05)"}'>
          <p :style='{"fontSize":"16px","color":"#666","marginBottom":"30px"}'>点击下方按钮选择新头像上传</p>
          <!-- 这里使用单一按钮，点击后只会触发一次文件选择 -->
          <el-button type="primary" @click="triggerAvatarUpload" class="upload-btn">上传头像</el-button>
          <div v-if="uploading" style="margin-top:12px;color:#999">上传中，请稍候…</div>
        </div>

        <div v-if="activeTab === '修改密码'" class="pwd-card">
          <el-form ref="pwdForm" :model="pwdForm" :rules="pwdRules" label-width="120px" class="pwd-form">
            <el-form-item label="原密码" prop="oldPassword">
              <el-input v-model="pwdForm.oldPassword" type="password" placeholder="请输入原密码"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="pwdForm.newPassword" type="password" placeholder="请输入新密码"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="pwdForm.confirmPassword" type="password" placeholder="请确认新密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" class="pwd-submit" @click="updatePassword">确认修改</el-button>
            </el-form-item>
          </el-form>
        </div>

        <div v-if="activeTab === '取消预约信息'" :style='{"width":"100%"}'>
          <div :style='{"fontSize":"18px","color":"#333","fontWeight":"500","marginBottom":"20px","paddingBottom":"10px","borderBottom":"1px solid #eee"}'>取消预约信息</div>
          <div :style='{"background":"#fff","borderRadius":"8px","padding":"20px","boxShadow":"0 2px 8px rgba(0,0,0,0.05)","marginBottom":"20px"}'>
            <el-form :style="{ width: '100%', display: 'flex', alignItems: 'center', gap: '15px' }" :inline="true" :model="searchForm">
              <div :style="{ display: 'flex', flexDirection: 'column', flex: 1 }">
                <label :style="{ color: '#333', fontSize: '14px', fontWeight: '500', marginBottom: '4px' }">预约单号</label>
                <el-input v-model="searchForm.yuyuedanhao" placeholder="输入预约单号查询" clearable :style="{ width: '100%', borderColor: '#78abc3' }"></el-input>
              </div>
              <el-button type="success" @click="searchCancelOrder" :style='{"border":"1px solid #97C9D6","color":"#fff","background":"#4fc08d","borderRadius":"20px","height":"40px","padding":"0 18px"}'>查询</el-button>
            </el-form>
          </div>

          <div :style='{"background":"#fff","borderRadius":"8px","boxShadow":"0 2px 8px rgba(0,0,0,0.05)","overflow":"hidden"}'>
            <el-table class="custom-table" :style="{ width: '100%' }" :data="cancelOrderList" v-loading="cancelOrderLoading" border :header-cell-style="{ background: '#f8f9fa', color: '#495057', fontWeight: '600' }" :row-style="{ height: '45px' }">
              <el-table-column type="index" label="索引" width="80" align="center"></el-table-column>
              <el-table-column prop="yuyuedanhao" label="预约单号" min-width="150" align="center"></el-table-column>
              <el-table-column prop="mingcheng" label="名称" min-width="120" align="center"></el-table-column>
              <el-table-column prop="yuyueStart" label="预约开始时间" min-width="180" align="center"></el-table-column>
              <el-table-column prop="yuyueEnd" label="预约结束时间" min-width="180" align="center"></el-table-column>
              <el-table-column prop="quxiaoshijian" label="取消时间" min-width="180" align="center"></el-table-column>
              <el-table-column prop="xuehao" label="学号" min-width="120" align="center"></el-table-column>
              <el-table-column prop="xingming" label="姓名" min-width="120" align="center"></el-table-column>
              <el-table-column prop="shouji" label="手机" min-width="150" align="center"></el-table-column>
            </el-table>
          </div>

          <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" background :page-sizes="[10,20,30,50]" :page-size="pageSize" :layout="'total, prev, pager, next, sizes, jumper'" :total="totalPage" :style="{ width: '100%', margin: '20px 0 0', textAlign: 'right' }"></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import config from '@/config/config'

export default {
  data() {
    const validateMobile = (rule, value, callback) => {
      if (!value) {
        callback();
      } else {
        const mobileReg = /^1[3-9]\d{9}$/;
        if (!mobileReg.test(value)) {
          callback(new Error("请输入正确的手机号码"));
        } else {
          callback();
        }
      }
    };

    const vm = this;

    return {
      title: '个人中心',
      activeTab: '个人信息',
      baseUrl: config.baseUrl,
      sessionForm: {},
      pwdForm: { oldPassword: '', newPassword: '', confirmPassword: '' },
      rules: { xingming: [{ required: true, message: '请输入姓名', trigger: 'blur' }], shouji: [{ validator: validateMobile, trigger: 'blur' }] },
      pwdRules: {
        oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
        newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }],
        confirmPassword: [{ required: true, message: '请确认新密码', trigger: 'blur' },
          { validator: function(rule, value, callback) { if (value !== vm.pwdForm.newPassword) callback(new Error('两次密码不一致')); else callback(); }, trigger: 'blur' }]
      },
      uploadUrl: config.baseUrl + 'file/upload',
      headers: { Token: localStorage.getItem('Token') },
      userTableName: localStorage.getItem('UserTableName'),
      searchForm: { yuyuedanhao: '' },
      cancelOrderList: [], cancelOrderLoading: false, pageIndex: 1, pageSize: 10, totalPage: 0,
      uploading: false
    }
  },
  created() {
    this.getSession();
  },
  methods: {
    tabStyle(name) {
      return { width: "100%", background: this.activeTab === name ? "#409EFF" : "#fff", color: this.activeTab === name ? "#fff" : "#409EFF", borderColor: this.activeTab === name ? "#409EFF" : "#dcdfe6", borderRadius: "4px" };
    },

    getSession() {
      this.$http.get(this.userTableName + '/session', { emulateJSON: true }).then(res => {
        if (res.data && res.data.code === 0) {
          this.sessionForm = res.data.data || {};
          if (this.sessionForm.touxiang) {
            try {
              const raw = this.sessionForm.touxiang.toString();
              const fileName = raw.split('/').pop();
              if (fileName) {
                this.sessionForm.touxiang = this.baseUrl + 'file/getAvatar?fileName=' + encodeURIComponent(fileName);
              } else {
                this.sessionForm.touxiang = '';
              }
            } catch (e) {
              console.error('处理头像路径失败：', e);
              this.sessionForm.touxiang = '';
            }
          }
        }
      }).catch((err) => {
        console.error('getSession 错误：', err);
        this.$message.error('获取个人信息失败');
      });
    },

    switchTab(tabName) {
      this.activeTab = tabName;
      this.title = tabName === '个人信息' ? '个人中心' : tabName;
      if (tabName === '取消预约信息') { this.pageIndex = 1; this.getCancelOrderList(); }
    },

    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // 强制以表单格式发送，兼容后端
          this.$http.post(this.userTableName + '/update', this.sessionForm, { emulateJSON: true }).then(res => {
            if (res.data && res.data.code === 0) {
              this.$message.success('更新成功');
            } else {
              this.$message.error(res.data?.msg || '更新失败');
            }
          }).catch((err) => {
            console.error('更新信息失败：', err);
            this.$message.error('网络请求失败，请重试');
          });
        }
      });
    },

    updatePassword() {
      this.$refs['pwdForm'].validate(valid => {
        if (!valid) return;
        if (!this.sessionForm.mima) { this.$message.error('未获取到原密码信息'); return; }
        if (this.pwdForm.oldPassword !== this.sessionForm.mima) { this.$message.error('原密码错误'); return; }
        const payload = { id: this.sessionForm.id, mima: this.pwdForm.newPassword };
        this.$http.post(this.userTableName + '/update', payload, { emulateJSON: true }).then(res => {
          if (res.data && res.data.code === 0) {
            this.$message.success('密码修改成功');
            this.pwdForm = { oldPassword: '', newPassword: '', confirmPassword: '' };
          } else {
            this.$message.error(res.data?.msg || '密码修改失败');
          }
        }).catch(err => {
          console.error('修改密码请求失败：', err);
          const status = err && err.status ? err.status : (err && err.response && err.response.status ? err.response.status : null);
          if (status === 415) {
            this.$message.error('请求格式不被后端接受 (415)，请检查后端是否允许 POST 表单或JSON');
          } else {
            this.$message.error('网络请求失败，请重试');
          }
        });
      });
    },

    avatarClickTip() {
      this.$message.info('请点击左侧「修改头像」按钮，在右侧页面上传新头像');
    },

    // 仅触发一次文件选择：解决“出现两次选择框”的问题
    triggerAvatarUpload() {
      const uploader = this.$refs.avatarUploader;
      if (!uploader) {
        this.$message.error('上传组件未初始化');
        return;
      }

      // 找到 el-upload 内部真实的 input[type=file]
      // Element UI 内部可能把 input 放在组件 DOM 内，通过 $el 查询即可
      const el = uploader.$el || (uploader.$refs && uploader.$refs.input && uploader.$refs.input.$el) || null;
      const fileInput = (el && el.querySelector) ? el.querySelector('input[type="file"]') : null;

      if (!fileInput) {
        this.$message.error('上传组件加载失败');
        return;
      }

      // 清空并绑定一次性 onchange，然后触发 click —— 只触发一次选择框
      fileInput.value = '';
      fileInput.onchange = (e) => {
        const file = e.target.files && e.target.files[0];
        if (file) {
          // 仅使用第一次选择的文件（如果用户选择后取消并重新选择会再次触发）
          this.uploadAvatarFile(file);
        }
        // 解绑，防止重复绑定导致多次触发
        fileInput.onchange = null;
      };
      fileInput.click();
    },

    // 上传文件：原实现保留，仅增加上传状态标记以在 UI 显示“上传中”
    uploadAvatarFile(file) {
      const formData = new FormData();
      formData.append('file', file);
      const uploadUrl = this.baseUrl + 'file/upload';
      this.uploading = true;
      this.$http.post(uploadUrl, formData, { headers: { 'Content-Type': 'multipart/form-data', Token: localStorage.getItem('Token') } }).then(res => {
        this.uploading = false;
        if (res.data && res.data.code === 0) {
          const filePath = res.data.file || (res.data.data && (res.data.data.file || res.data.data)) || res.data.url;
          if (!filePath) { this.$message.error('后端未返回文件路径'); return; }
          const filenameOnly = filePath.toString().split('/').pop();
          const fullAvatarUrl = this.baseUrl + 'file/getAvatar?fileName=' + encodeURIComponent(filenameOnly);
          this.sessionForm.touxiang = fullAvatarUrl;
          // 更新用户头像到数据库（使用 emulateJSON 保持兼容）
          this.$http.post(this.userTableName + '/update', { id: this.sessionForm.id, touxiang: filePath }, { emulateJSON: true }).then(updateRes => {
            if (updateRes.data && updateRes.data.code === 0) {
              this.$message.success('头像上传成功');
              this.switchTab('个人信息');
            } else {
              this.$message.error(updateRes.data?.msg || '头像更新失败，请重试');
            }
          }).catch(err => {
            console.error('头像更新请求失败：', err);
            this.$message.error('头像更新请求失败');
          });
        } else {
          this.$message.error(res.data?.msg || '头像上传失败');
        }
      }).catch(err => {
        this.uploading = false;
        console.error('文件上传失败：', err);
        this.$message.error('网络异常，上传失败');
      });
    },

    searchCancelOrder() { this.pageIndex = 1; this.getCancelOrderList(); },

    getCancelOrderList() {
      if (!this.sessionForm.xuehao) { this.$message.warning('未获取到学号信息，无法查询取消预约记录'); this.cancelOrderLoading = false; return; }
      this.cancelOrderLoading = true;
      const params = { page: this.pageIndex, limit: this.pageSize, sort: 'id', order: 'desc', xuehao: this.sessionForm.xuehao };
      if (this.searchForm.yuyuedanhao) params.yuyuedanhao = `%${this.searchForm.yuyuedanhao}%`;
      this.$http({ url: 'quxiaoyuyue/page', method: 'get', params }).then(({ data }) => {
        this.cancelOrderLoading = false;
        if (data && data.code === 0) { this.cancelOrderList = data.data.list || []; this.totalPage = data.data.total || 0; }
        else { this.cancelOrderList = []; this.totalPage = 0; this.$message.warning(data?.msg || '暂无取消预约记录'); }
      }).catch(() => {
        this.cancelOrderList = []; this.totalPage = 0; this.cancelOrderLoading = false; this.$message.error('获取取消预约记录失败');
      });
    },

    sizeChangeHandle(val) { this.pageSize = val; this.getCancelOrderList(); },
    currentChangeHandle(val) { this.pageIndex = val; this.getCancelOrderList(); }
  }
}
</script>

<style lang="scss" scoped>
/* 全局样式调整 */
.el-form-item >>> .el-form-item__label {
  color: #333;
  font-weight: 500;
  text-align: right;
  line-height: 40px;
}
.el-input >>> .el-input__inner {
  border: 1px solid #ccc;
  border-radius: 6px;
  height: 40px;
  padding: 0 12px;
  transition: box-shadow .2s;
}
.el-input >>> .el-input__inner:focus {
  box-shadow: 0 0 6px rgba(64,158,255,0.12);
  border-color: #409EFF;
}

.el-select >>> .el-input__inner {
  border: 1px solid #ccc;
  border-radius: 6px;
  height: 40px;
  padding: 0 12px;
}

/* 上传按钮风格 */
.upload-btn {
  border-radius: 8px;
  padding: 8px 18px;
  background: linear-gradient(90deg,#4aa8ff,#2f86f6);
  color: #fff;
  box-shadow: 0 6px 18px rgba(47,134,246,0.12);
}

/* 修改密码卡片美化 */
.pwd-card {
  background:#fff;
  border-radius:8px;
  padding: 28px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  max-width:640px;
  margin:0 auto;
}
.pwd-form .el-form-item {
  margin-bottom: 18px;
}
.pwd-form .el-form-item .el-input__inner {
  border-radius: 6px;
  height: 44px;
  padding: 0 14px;
}

/* 更好看的确认按钮 */
.pwd-submit {
  background: linear-gradient(90deg,#ffb980,#ff8a65);
  border: none;
  color: #fff;
  border-radius: 24px;
  padding: 10px 22px;
  box-shadow: 0 6px 18px rgba(255,138,101,0.12);
}

/* 取消预约列表样式优化 */
.custom-table {
  .el-table__body-wrapper tr:hover td {
    background: rgba(147, 199, 179, 0.05);
  }
}
</style>