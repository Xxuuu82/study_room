<template>
  <div :style='{"width":"80%","margin":"10px auto","position":"relative","background":"#fff","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)"}'>
    <!-- 顶部标题 -->
    <div class="title" :style='{"margin":"0","color":"#333","textAlign":"center","background":"rgba(0,0,0,0.102)","fontSize":"20px","lineHeight":"54px","fontWeight":"600"}'>{{ title }}</div>

    <!-- 主体：左侧栏 + 右侧内容 -->
    <div :style='{"display":"flex","padding":"20px"}'>
      <!-- 左侧栏（头像+学号+按钮） -->
      <div :style='{"width":"180px","textAlign":"center","background":"#f5f7fa","borderRadius":"8px","padding":"20px","marginRight":"20px"}'>
        <!-- 头像（仅展示，点击无上传操作） -->
        <div :style='{"marginBottom":"15px","cursor":"pointer"}' @click="avatarClickTip">
          <img
              v-if="sessionForm.touxiang"
              :src="baseUrl + sessionForm.touxiang"
              :style='{"width":"100px","height":"100px","borderRadius":"50%","border":"2px solid #fff","boxShadow":"0 2px 8px rgba(0,0,0,0.1)"}'
          />
          <!-- 无头像时显示文字占位符 -->
          <div
              v-else
              :style='{"width":"100px","height":"100px","borderRadius":"50%","background":"#e6f0ff","color":"#409eff","fontSize":"36px","lineHeight":"100px","display":"inline-block","textAlign":"center"}'
          >
            <<i class="el-icon-user"></</i>
          </div>
          <!-- 隐藏的上传组件：手动控制上传 -->
          <el-upload
              ref="avatarUploader"
              class="avatar-uploader"
              :action="uploadUrl"
              :headers="headers"
              :show-file-list="false"
              :auto-upload="false"
              style="display: none;"
          >
            <input type="file" accept="image/*">
          </el-upload>
        </div>
        <!-- 学号 -->
        <div :style='{"fontSize":"14px","color":"#333","padding":"8px 0","background":"#fff","borderRadius":"4px","marginBottom":"20px"}'>
          学号：{{ sessionForm.xuehao || '未填写' }}
        </div>
        <!-- 按钮组 -->
        <div :style='{"display":"flex","flexDirection":"column","gap":"10px"}'>
          <el-button
              type="primary"
              plain
              @click="switchTab('个人信息')"
              :style='{
              width:"100%",background:activeTab==="个人信息"?"#409EFF":"#fff",
              color:activeTab==="个人信息"?"#fff":"#409EFF",
              borderColor:activeTab==="个人信息"?"#409EFF":"#dcdfe6",
              borderRadius:"4px"
            }'
          >
            个人信息
          </el-button>
          <el-button
              type="primary"
              plain
              @click="switchTab('修改头像')"
              :style='{
              width:"100%",background:activeTab==="修改头像"?"#409EFF":"#fff",
              color:activeTab==="修改头像"?"#fff":"#409EFF",
              borderColor:activeTab==="修改头像"?"#409EFF":"#dcdfe6",
              borderRadius:"4px"
            }'
          >
            修改头像
          </el-button>
          <el-button
              type="primary"
              plain
              @click="switchTab('修改密码')"
              :style='{
              width:"100%",background:activeTab==="修改密码"?"#409EFF":"#fff",
              color:activeTab==="修改密码"?"#fff":"#409EFF",
              borderColor:activeTab==="修改密码"?"#409EFF":"#dcdfe6",
              borderRadius:"4px"
            }'
          >
            修改密码
          </el-button>
          <!-- 取消预约信息按钮 -->
          <el-button
              type="primary"
              plain
              @click="switchTab('取消预约信息')"
              :style='{
              width:"100%",background:activeTab==="取消预约信息"?"#409EFF":"#fff",
              color:activeTab==="取消预约信息"?"#fff":"#409EFF",
              borderColor:activeTab==="取消预约信息"?"#409EFF":"#dcdfe6",
              borderRadius:"4px"
            }'
          >
            取消预约信息
          </el-button>
        </div>
      </div>

      <!-- 右侧内容区：统一布局容器 -->
      <div :style='{"flex":"1","background":"#f5f7fa","borderRadius":"8px","padding":"20px"}'>
        <!-- 个人信息表单 -->
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
              <el-button
                  type="primary"
                  @click="onSubmit('sessionForm')"
                  :style='{"background":"#409EFF","color":"#fff","border":"none","padding":"0 20px","height":"40px"}'
              >
                更新信息
              </el-button>
            </el-form-item>
          </el-form>
        </div>

        <!-- 修改头像区域：显示上传按钮 -->
        <div v-if="activeTab === '修改头像'" :style='{"background":"#fff","borderRadius":"8px","padding":"50px 20px","textAlign":"center","boxShadow":"0 2px 8px rgba(0,0,0,0.05)"}'>
          <p :style='{"fontSize":"16px","color":"#666","marginBottom":"30px"}'>点击下方按钮选择新头像上传</p>
          <el-button
              type="primary"
              @click="triggerAvatarUpload"
              :style='{
                background:"#409EFF",
                color:"#fff",
                border:"none",
                padding:"0 30px",
                height:"48px",
                fontSize:"16px",
                borderRadius:"8px"
              }'
          >
            上传头像
          </el-button>
        </div>

        <!-- 修改密码表单 -->
        <div v-if="activeTab === '修改密码'" :style='{"background":"#fff","borderRadius":"8px","padding":"20px","boxShadow":"0 2px 8px rgba(0,0,0,0.05)"}'>
          <el-form ref="pwdForm" :model="pwdForm" :rules="pwdRules" label-width="80px" :style='{"width":"100%","maxWidth":"500px","margin":"0 auto"}'>
            <el-form-item label="原密码" prop="oldPassword" :style='{"marginBottom":"20px"}'>
              <el-input v-model="pwdForm.oldPassword" type="password" placeholder="请输入原密码" style="width: 300px;"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword" :style='{"marginBottom":"20px"}'>
              <el-input v-model="pwdForm.newPassword" type="password" placeholder="请输入新密码" style="width: 300px;"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword" :style='{"marginBottom":"20px"}'>
              <el-input v-model="pwdForm.confirmPassword" type="password" placeholder="请确认新密码" style="width: 300px;"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                  type="primary"
                  @click="updatePassword"
                  :style='{"background":"#409EFF","color":"#fff","border":"none","padding":"0 20px","height":"40px"}'
              >
                确认修改
              </el-button>
            </el-form-item>
          </el-form>
        </div>

        <!-- 取消预约信息标签页：重新布局 -->
        <div v-if="activeTab === '取消预约信息'" :style='{"width":"100%"}'>
          <!-- 标题 -->
          <div :style='{"fontSize":"18px","color":"#333","fontWeight":"500","marginBottom":"20px","paddingBottom":"10px","borderBottom":"1px solid #eee"}'>
            取消预约信息
          </div>

          <!-- 预约单号查询区域：调整布局 -->
          <div :style='{"background":"#fff","borderRadius":"8px","padding":"20px","boxShadow":"0 2px 8px rgba(0,0,0,0.05)","marginBottom":"20px"}'>
            <el-form
                :style="{ width: '100%', display: 'flex', alignItems: 'center', gap: '15px' }"
                :inline="true"
                :model="searchForm"
            >
              <div :style="{ display: 'flex', flexDirection: 'column', flex: 1 }">
                <label
                    :style="{
                      color: '#333',
                      fontSize: '14px',
                      fontWeight: '500',
                      marginBottom: '4px'
                    }"
                >预约单号</label>
                <el-input
                    v-model="searchForm.yuyuedanhao"
                    placeholder="输入预约单号查询"
                    clearable
                    :style="{ width: '100%', borderColor: '#78abc3' }"
                ></el-input>
              </div>
              <el-button
                  :style="{
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
                    alignSelf: 'flex-end'
                  }"
                  type="success"
                  @click="searchCancelOrder"
              >查询</el-button>
            </el-form>
          </div>

          <!-- 取消预约列表：修复样式 -->
          <div :style='{"background":"#fff","borderRadius":"8px","boxShadow":"0 2px 8px rgba(0,0,0,0.05)","overflow":"hidden"}'>
            <el-table
                class="custom-table"
                :style="{ width: '100%' }"
                :data="cancelOrderList"
                v-loading="cancelOrderLoading"
                border
                :header-cell-style="{ background: '#f8f9fa', color: '#495057', fontWeight: '600' }"
                :row-style="{ height: '45px' }"
            >
              <el-table-column type="index" label="索引" width="80" align="center"></el-table-column>
              <el-table-column prop="yuyuedanhao" label="预约单号" min-width="150" align="center"></el-table-column>
              <el-table-column prop="mingcheng" label="名称" min-width="120" align="center"></el-table-column>
              <el-table-column prop="yuyueshijian" label="预约时间" min-width="180" align="center"></el-table-column>
              <el-table-column prop="quxiaoshijian" label="取消时间" min-width="180" align="center"></el-table-column>
              <el-table-column prop="xuehao" label="学号" min-width="120" align="center"></el-table-column>
              <el-table-column prop="xingming" label="姓名" min-width="120" align="center"></el-table-column>
              <el-table-column prop="shouji" label="手机" min-width="150" align="center"></el-table-column>
            </el-table>
          </div>

          <!-- 分页：调整位置 -->
          <el-pagination
              @size-change="sizeChangeHandle"
              @current-change="currentChangeHandle"
              :current-page="pageIndex"
              background
              :page-sizes="[10, 20, 30, 50]"
              :page-size="pageSize"
              :layout="'total, prev, pager, next, sizes, jumper'"
              :total="totalPage"
              :style="{ width: '100%', margin: '20px 0 0', textAlign: 'right' }"
          ></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import config from '@/config/config'

export default {
  data() {
    // 内置手机号验证方法
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

    return {
      title: '个人中心',
      activeTab: '个人信息',
      baseUrl: config.baseUrl,
      sessionForm: {},
      pwdForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      rules: {
        xingming: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        shouji: [{ validator: validateMobile, trigger: 'blur' }]
      },
      pwdRules: {
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认新密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.pwdForm.newPassword) {
                callback(new Error('两次密码不一致'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ]
      },
      uploadUrl: config.baseUrl + 'file/upload',
      headers: { Token: localStorage.getItem('Token') },
      userTableName: localStorage.getItem('UserTableName'),
      // 取消预约信息相关数据
      searchForm: { yuyuedanhao: '' },
      cancelOrderList: [],
      cancelOrderLoading: false,
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0
    }
  },
  created() {
    this.getSession();
  },
  methods: {
    getSession() {
      this.$http.get(this.userTableName + '/session', { emulateJSON: true }).then(res => {
        if (res.data && res.data.code === 0) {
          this.sessionForm = res.data.data;
        }
      }).catch(() => {
        this.$message.error('获取个人信息失败');
      });
    },
    switchTab(tabName) {
      this.activeTab = tabName;
      this.title = tabName === '个人信息' ? '个人中心' : tabName;
      // 切换到取消预约信息时加载数据
      if (tabName === '取消预约信息') {
        this.pageIndex = 1;
        this.getCancelOrderList();
      }
    },
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$http.post(this.userTableName + '/update', this.sessionForm).then(res => {
            if (res.data && res.data.code === 0) {
              this.$message.success('更新成功');
            } else {
              this.$message.error(res.data?.msg || '更新失败');
            }
          }).catch(() => {
            this.$message.error('网络请求失败，请重试');
          });
        }
      });
    },
    updatePassword() {
      this.$refs['pwdForm'].validate(valid => {
        if (valid) {
          if (!this.sessionForm.mima) {
            this.$message.error('未获取到原密码信息');
            return;
          }
          if (this.pwdForm.oldPassword !== this.sessionForm.mima) {
            this.$message.error('原密码错误');
            return;
          }
          this.$http.post(this.userTableName + '/update', {
            id: this.sessionForm.id,
            mima: this.pwdForm.newPassword
          }).then(res => {
            if (res.data && res.data.code === 0) {
              this.$message.success('密码修改成功');
              this.pwdForm = { oldPassword: '', newPassword: '', confirmPassword: '' };
            } else {
              this.$message.error(res.data?.msg || '密码修改失败');
            }
          }).catch(() => {
            this.$message.error('网络请求失败，请重试');
          });
        }
      });
    },
    // 点击头像仅提示
    avatarClickTip() {
      this.$message.info('请点击左侧「修改头像」按钮，在右侧页面上传新头像');
    },
    // 触发文件选择框
    triggerAvatarUpload() {
      if (this.$refs.avatarUploader) {
        const uploader = this.$refs.avatarUploader;
        const fileInput = uploader.$el.querySelector('input[type="file"]');
        if (fileInput) {
          // 清空历史文件
          fileInput.value = '';
          // 绑定change事件
          fileInput.onchange = (e) => {
            const file = e.target.files[0];
            if (file) {
              this.uploadAvatarFile(file);
            }
            // 解绑事件，防止重复触发
            fileInput.onchange = null;
          };
          fileInput.click();
        } else {
          this.$message.error('上传组件加载失败');
        }
      } else {
        this.$message.error('上传组件未初始化');
      }
    },
    // 手动上传头像文件
    uploadAvatarFile(file) {
      const formData = new FormData();
      formData.append('file', file);
      this.$http.post(this.uploadUrl, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          Token: localStorage.getItem('Token')
        }
      }).then(res => {
        if (res.data && res.data.code === 0) {
          this.sessionForm.touxiang = 'upload/' + res.data.file;
          // 更新用户头像到数据库
          this.$http.post(this.userTableName + '/update', {
            id: this.sessionForm.id,
            touxiang: this.sessionForm.touxiang
          }).then(updateRes => {
            if (updateRes.data && updateRes.data.code === 0) {
              this.$message.success('头像上传成功');
              this.switchTab('个人信息');
            } else {
              this.$message.error('头像更新失败，请重试');
            }
          }).catch(() => {
            this.$message.error('头像更新请求失败');
          });
        } else {
          this.$message.error(res.data?.msg || '头像上传失败');
        }
      }).catch(() => {
        this.$message.error('网络异常，上传失败');
      });
    },
    // 取消预约信息相关方法
    searchCancelOrder() {
      this.pageIndex = 1;
      this.getCancelOrderList();
    },
    getCancelOrderList() {
      if (!this.sessionForm.xuehao) {
        this.$message.warning('未获取到学号信息，无法查询取消预约记录');
        this.cancelOrderLoading = false;
        return;
      }

      this.cancelOrderLoading = true;
      const params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: 'id',
        order: 'desc',
        xuehao: this.sessionForm.xuehao
      };
      if (this.searchForm.yuyuedanhao) {
        params.yuyuedanhao = `%${this.searchForm.yuyuedanhao}%`;
      }
      this.$http({
        url: 'quxiaoyuyue/page',
        method: 'get',
        params
      }).then(({ data }) => {
        this.cancelOrderLoading = false;
        if (data && data.code === 0) {
          this.cancelOrderList = data.data.list || [];
          this.totalPage = data.data.total || 0;
        } else {
          this.cancelOrderList = [];
          this.totalPage = 0;
          this.$message.warning(data?.msg || '暂无取消预约记录');
        }
      }).catch(() => {
        this.cancelOrderLoading = false;
        this.$message.error('获取取消预约记录失败');
      });
    },
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.getCancelOrderList();
    },
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getCancelOrderList();
    }
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
  border-radius: 4px;
  height: 40px;
  padding: 0 12px;
}
.el-select >>> .el-input__inner {
  border: 1px solid #ccc;
  border-radius: 4px;
  height: 40px;
  padding: 0 12px;
}
.el-icon-user {
  font-size: 36px !important;
  line-height: 100px !important;
}
.avatar-wrap:hover {
  opacity: 0.9;
  transition: opacity 0.2s;
}

/* 取消预约列表样式优化 */
.custom-table {
  .el-table__body-wrapper tr:hover td {
    background: rgba(147, 199, 179, 0.1);
  }
}
</style>