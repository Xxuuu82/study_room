<template>
  <div
      :style="{
      width: '100%',
      maxWidth: '1000px',
      padding: '20px',
      margin: '10px auto',
      position: 'relative',
      background: '#fff',
      boxSizing: 'border-box'
    }"
  >
    <!-- 新增面包屑导航 -->
    <div :style="{
      marginBottom: '20px',
      paddingBottom: '10px',
      borderBottom: '1px solid #e4e7ed'
    }">
      <el-breadcrumb :separator="'/'" :style='{"fontSize":"14px","lineHeight":"1","color":"#333"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item @click="$router.push('/index/tousu')">投诉中心</el-breadcrumb-item>
        <el-breadcrumb-item>{{ type === 'edit' ? '编辑投诉' : '新增投诉' }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-form
        class="add-update-preview"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
        :style="{width: '100%', maxWidth: '800px', margin: '0 auto'}"
    >
      <!-- 投诉人学号 -->
      <el-form-item
          label="投诉人学号"
          prop="xuehao"
          :style="{marginBottom: '15px'}"
      >
        <el-input
            v-model="ruleForm.xuehao"
            placeholder="投诉人学号"
            clearable
            :readonly="ro.xuehao"
            :style="{
              width: '100%',
              height: '36px',
              border: '1px solid #dcdfe6',
              borderRadius: '4px',
              background: '#f5f7fa'
            }"
        ></el-input>
      </el-form-item>

      <!-- 自习室（单选框） -->
      <el-form-item
          label="自习室"
          prop="zixishi"
          :style="{marginBottom: '15px'}"
      >
        <el-radio-group
            v-model="ruleForm.zixishi"
            style="width: 100%;"
        >
          <el-radio
              v-for="(item, index) in zixishiOptions"
              :key="index"
              :label="item"
              :style="{
                display: 'inline-block',
                margin: '0 20px 10px 0',
                fontSize: '14px',
                color: '#666'
              }"
          >
            {{ item }}
          </el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 座位号 -->
      <el-form-item
          label="座位号"
          prop="zuoweihao"
          :style="{marginBottom: '15px'}"
      >
        <el-input
            v-model="ruleForm.zuoweihao"
            placeholder="请输入座位号"
            clearable
            :style="{
              width: '100%',
              height: '36px',
              border: '1px solid #dcdfe6',
              borderRadius: '4px'
            }"
        ></el-input>
      </el-form-item>

      <!-- 投诉类型（单选框） -->
      <el-form-item
          label="投诉类型"
          prop="tousuleixing"
          :style="{marginBottom: '15px'}"
      >
        <el-radio-group
            v-model="ruleForm.tousuleixing"
            style="width: 100%;"
        >
          <el-radio
              v-for="(item, index) in tousuleixingOptions"
              :key="index"
              :label="item"
              :style="{
                display: 'inline-block',
                margin: '0 20px 0 0',
                fontSize: '14px',
                color: '#666'
              }"
          >
            {{ item }}
          </el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 投诉详情 -->
      <el-form-item
          label="投诉详情"
          prop="tousuReason"
          :style="{marginBottom: '15px'}"
      >
        <el-input
            v-model="ruleForm.tousuReason"
            type="textarea"
            placeholder="请详细描述投诉原因，至少10个字"
            clearable
            :rows="5"
            :style="{
              width: '100%',
              border: '1px solid #dcdfe6',
              borderRadius: '4px'
            }"
        ></el-input>
      </el-form-item>


      <!-- 提交/返回按钮 -->
      <el-form-item :style="{textAlign: 'left', marginBottom: '0'}">
        <el-button
            type="primary"
            @click="onSubmit"
            :style="{
              padding: '0 20px',
              height: '36px',
              borderRadius: '4px',
              fontSize: '14px',
              background: '#409eff',
              color: '#fff',
              border: 'none',
              marginRight: '10px'
            }"
        >{{ type === 'edit' ? '更新投诉' : '提交投诉' }}</el-button
        >
        <el-button
            @click="back()"
            :style="{
              padding: '0 20px',
              height: '36px',
              borderRadius: '4px',
              fontSize: '14px',
              background: '#fff',
              color: '#666',
              border: '1px solid #dcdfe6'
            }"
        >返回</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      baseUrl: "",
      // 只读字段配置
      ro: {
        xuehao: false,
      },
      type: "",
      userTableName: localStorage.getItem("UserTableName"),
      // 表单数据
      ruleForm: {
        tousudanhao: this.getUUID(), // 自动生成投诉单号（后端用）
        xuehao: "", // 投诉人学号
        zixishi: "", // 自习室（单选）
        zuoweihao: "", // 座位号
        tousuleixing: "", // 投诉类型（单选）
        tousuReason: "", // 投诉详情
        tousuTime: "", // 创建时间（初始为空）
        chulizhuangtai: "处理中", // 默认处理状态
      },
      // 自习室选项（图片中的8个）
      zixishiOptions: ["宿舍楼自习室", "s2s", "北区机房", "北区2号楼", "多媒体教室", "北区三号楼", "图书馆", "大自习室"],
      // 投诉类型选项
      tousuleixingOptions: ["乱吃乱喝", "大声喧哗", "随地大小便", "其他"],
      // 表单校验规则
      rules: {
        // 投诉人学号必填
        xuehao: [
          { required: true, message: "投诉人学号不能为空", trigger: "blur" }
        ],
        // 自习室必填
        zixishi: [
          { required: true, message: "请选择自习室", trigger: "change" }
        ],
        // 座位号非必填（移除必填校验）
        zuoweihao: [],
        // 投诉类型必填
        tousuleixing: [
          { required: true, message: "请选择投诉类型", trigger: "change" }
        ],
        // 投诉详情必填
        tousuReason: [
          { required: true, message: "请详细描述投诉原因", trigger: "blur" },
          { min: 10, message: "投诉原因至少填写10个字", trigger: "blur" }
        ]
      },
    };
  },
  computed: {},
  created() {
    let type = this.$route.query.type ? this.$route.query.type : "";
    this.init(type);
    this.baseUrl = this.$config.baseUrl;
  },
  methods: {
    // 初始化方法
    init(type) {
      this.type = type;
      // 如果是编辑模式，从路由参数获取数据（扩展用）
      if (type === "edit") {
        let editObj = JSON.parse(localStorage.getItem("editComplaintObj") || "{}");
        if (Object.keys(editObj).length > 0) {
          this.ruleForm = {
            ...this.ruleForm,
            ...editObj,
            tousuleixing: editObj.tousuleixing || "",
            zixishi: editObj.zixishi || ""
          };
        } else {
          this.$message.warning('未获取到编辑数据，将创建新投诉');
        }
      }

      // 初始化用户信息（自动填充当前登录用户信息）
      if (!this.userTableName) {
        this.ruleForm.xuehao = "20230001";
        this.ro.xuehao = true;
        return;
      }

      this.$http
          .get(this.userTableName + "/session", { emulateJSON: true })
          .then((res) => {
            if (res.data.code === 0) {
              let userInfo = res.data.data || {};
              if (userInfo.xuehao) {
                this.ruleForm.xuehao = userInfo.xuehao;
                this.ro.xuehao = true; // 学号设为只读
              }
            }
          })
          .catch(err => {
            console.error("获取用户信息失败：", err);
            this.$message.warning("无法自动获取用户信息，请手动填写");
          });
    },
    // 提交/更新投诉
    onSubmit() {
      // 提交前自动填充当前系统时间作为创建时间
      this.ruleForm.tousuTime = new Date().toISOString().slice(0, 19).replace('T', ' ');

      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          // 构造提交数据
          const submitData = {
            ...this.ruleForm,
            tousu_time: this.ruleForm.tousuTime,
            tousu_reason: this.ruleForm.tousuReason,
          };

          console.log("提交的投诉数据：", submitData);

          // 模拟提交请求
          setTimeout(() => {
            this.$message({
              message: this.type === 'edit' ? "投诉更新成功" : "投诉提交成功，等待管理员处理",
              type: "success",
              duration: 2000,
              onClose: () => {
                this.$router.push({
                  path: '/index/tousu',
                  replace: true
                });
              },
            });
          }, 1000);
        }
      });
    },
    // 生成唯一投诉单号
    getUUID() {
      return "TS" + new Date().getTime();
    },
    // 返回投诉列表
    back() {
      this.$router.push({
        path: '/index/tousu',
        replace: true
      });
    },
  },
};
</script>

<style lang="scss" scoped>
// 移动端适配
@media (max-width: 768px) {
  .add-update-preview {
    ::v-deep .el-form-item__label {
      width: 100% !important;
      text-align: left !important;
      line-height: 24px !important;
      padding: 0 0 8px 0 !important;
    }

    ::v-deep .el-form-item__content {
      margin-left: 0 !important;
    }
  }
}

// 表单标签样式
.add-update-preview {
  ::v-deep .el-form-item__label {
    padding: 0;
    color: #666;
    font-weight: 400;
    width: 120px;
    font-size: 14px;
    line-height: 36px;
    text-align: left;
  }

  ::v-deep .el-form-item__content {
    margin-left: 0;
  }

  // 单选框样式
  ::v-deep .el-radio__inner {
    width: 16px;
    height: 16px;
    border-radius: 50%;
  }

  // 面包屑样式
  ::v-deep .el-breadcrumb {
    .el-breadcrumb__separator {
      margin: 0 8px;
      color: #666;
    }

    .el-breadcrumb__inner {
      color: #333;
      cursor: pointer;
    }
  }
}
</style>