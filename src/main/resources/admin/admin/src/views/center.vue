<template>
  <div class="personal-container">
    <!-- 信息卡片 -->
    <el-card class="info-card" shadow="hover">
      <div class="card-header">
        <h2>个人信息</h2>
        <el-tag type="info" size="small">{{ roleLabel }}</el-tag>
      </div>

      <el-form
        class="info-form"
        :model="ruleForm"
        label-width="100px"
      >
        <el-row :gutter="20">
          <!-- 头像区域 -->
          <el-col :span="24" class="avatar-group">
            <el-form-item label="头像">
              <file-upload
                tip="点击上传头像"
                action="file/upload"
                :limit="1"
                :fileUrls="ruleForm.touxiang || ''"
                @change="xueshengtouxiangUploadChange"
              />
              <div class="avatar-tip">建议上传正方形图片（200*200px）</div>
            </el-form-item>
          </el-col>

          <!-- 管理员信息 -->
          <el-col :span="12" v-if="flag === 'users'">
            <el-form-item label="用户名">
              <el-input v-model="ruleForm.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="flag === 'users'">
            <el-form-item label="角色">
              <el-input v-model="roleLabel" readonly placeholder="角色信息" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="flag === 'users'">
            <el-form-item label="登录时间">
              <el-input v-model="loginTime" readonly placeholder="最近登录时间" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="flag === 'users'">
            <el-form-item label="账户状态">
              <el-tag type="success">正常</el-tag>
            </el-form-item>
          </el-col>

          <!-- 学生信息（保留原有逻辑） -->
          <el-col :span="12" v-if="flag === 'xuesheng'">
            <el-form-item label="学号" prop="xuehao">
              <el-input v-model="ruleForm.xuehao" readonly placeholder="学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="flag === 'xuesheng'">
            <el-form-item label="姓名" prop="xingming">
              <el-input v-model="ruleForm.xingming" placeholder="姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="flag === 'xuesheng'">
            <el-form-item label="性别" prop="xingbie">
              <el-select v-model="ruleForm.xingbie" placeholder="请选择性别">
                <el-option
                  v-for="(item, index) in xueshengxingbieOptions"
                  :key="index"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="flag === 'xuesheng'">
            <el-form-item label="手机" prop="shouji">
              <el-input v-model="ruleForm.shouji" placeholder="手机" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="flag === 'xuesheng'">
            <el-form-item label="班级" prop="banji">
              <el-input v-model="ruleForm.banji" placeholder="班级" />
            </el-form-item>
          </el-col>

          <!-- 操作按钮 -->
          <el-col :span="24" class="btn-group">
            <el-button type="primary" @click="onUpdateHandler">保存修改</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { isNumber, isIntNumer, isEmail, isMobile, isPhone, isURL, checkIdCard } from "@/utils/validate";

export default {
  data() {
    return {
      ruleForm: {},
      flag: "",
      xueshengxingbieOptions: [],
      roleLabel: "管理员", // 补充角色信息
      loginTime: "", // 登录时间
    };
  },
  mounted() {
    const table = this.$storage.get("sessionTable");
    this.flag = table;
    // 获取当前时间作为登录时间示例
    this.loginTime = new Date().toLocaleString();
    // 原有接口逻辑
    this.$http({
      url: `${this.$storage.get("sessionTable")}/session`,
      method: "get",
    }).then(({ data }) => {
      if (data && data.code === 0) {
        this.ruleForm = data.data;
      } else {
        this.$message.error(data.msg);
      }
    });
    this.xueshengxingbieOptions = "男,女".split(",");
  },
  methods: {
    xueshengtouxiangUploadChange(fileUrls) {
      this.ruleForm.touxiang = fileUrls;
    },
    onUpdateHandler() {
      // 原有校验逻辑
      if (this.flag === "xuesheng") {
        if (!this.ruleForm.xuehao) {
          this.$message.error("学号不能为空");
          return;
        }
        if (!this.ruleForm.mima) {
          this.$message.error("密码不能为空");
          return;
        }
        if (!this.ruleForm.xingming) {
          this.$message.error("姓名不能为空");
          return;
        }
        if (this.ruleForm.shouji && !isMobile(this.ruleForm.shouji)) {
          this.$message.error("手机格式不正确");
          return;
        }
      }
      if (this.flag === "users" && !this.ruleForm.username.trim()) {
        this.$message.error("用户名不能为空");
        return;
      }
      // 上传头像处理
      if (this.ruleForm.touxiang) {
        this.ruleForm.touxiang = this.ruleForm.touxiang.replace(
          new RegExp(this.$base.url, "g"),
          ""
        );
      }
      // 提交修改
      this.$http({
        url: `${this.$storage.get("sessionTable")}/update`,
        method: "post",
        data: this.ruleForm,
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "修改信息成功",
            type: "success",
            duration: 1500,
          });
        } else {
          this.$message.error(data.msg);
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.personal-container {
  padding: 20px;
  background-color: #f5f7fa;

  .info-card {
    border-radius: 12px;
    overflow: hidden;

    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 16px 20px;
      border-bottom: 1px solid #eee;
      background-color: #fff;

      h2 {
        margin: 0;
        font-size: 18px;
        font-weight: 600;
        color: #333;
      }
    }

    .info-form {
      padding: 20px;
      background-color: #fff;

      .avatar-group {
        margin-bottom: 20px;

        .avatar-tip {
          margin-top: 8px;
          font-size: 12px;
          color: #999;
        }
      }

      .btn-group {
        display: flex;
        justify-content: flex-end;
        padding-top: 10px;
      }
    }
  }
}

// 统一表单样式
.el-form-item {
  margin-bottom: 20px;

  .el-form-item__label {
    color: #666;
    font-weight: 500;
  }

  .el-input__inner,
  .el-select__wrapper {
    border-radius: 6px;
    border-color: #dcdfe6;
    transition: all 0.3s;

    &:focus {
      border-color: #409eff;
      box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
    }
  }
}
</style>