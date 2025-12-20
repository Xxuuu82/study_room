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
            :loading="submitting"
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
import axios from 'axios';

export default {
  data() {
    return {
      id: "",
      baseUrl: "",
      submitting: false,
      // 只读字段配置
      ro: {
        xuehao: false,
      },
      type: "",
      userTableName: localStorage.getItem("UserTableName"),
      // 表单数据
      ruleForm: {
        tousudanhao: this.getUUID(), // 自动生成投诉单号（后端用）
        xuehao: "", // 投诉人学号（数字字符串）
        zixishi: "", // 自习室（单选）
        zuoweihao: "", // 座位号（字符串或数字）
        tousuleixing: "", // 投诉类型（单选）
        tousuReason: "", // 投诉详情
        tousuTime: "", // 创建时间（初始为空）
        chulizhuangtai: "处理中", // 可选，但后端主导 status 字段
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
  created() {
    let type = this.$route.query.type ? this.$route.query.type : "";
    this.init(type);
    this.baseUrl = this.$config?.baseUrl || '';
  },
  methods: {
    init(type) {
      this.type = type;
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

      if (this.$http) {
        this.$http
            .get(this.userTableName + "/session", { emulateJSON: true })
            .then((res) => {
              if (res.data && (res.data.code === 0 || res.data.success === true)) {
                const userInfo = res.data.data || {};
                if (userInfo.xuehao) {
                  this.ruleForm.xuehao = userInfo.xuehao;
                  this.ro.xuehao = true;
                }
              }
            })
            .catch(err => {
              console.warn("获取用户信息失败：", err);
              this.$message.warning("无法自动获取用户信息，请手动填写");
            });
      }
    },

    // 生成唯一投诉单号
    getUUID() {
      return "TS" + new Date().getTime();
    },

    // 将表单值映射为后端 ComplaintEntity 可接收的字段并提交
    async onSubmit() {
      this.ruleForm.tousuTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
      this.$refs["ruleForm"].validate(async (valid) => {
        if (!valid) return;
        if (this.submitting) return;
        this.submitting = true;

        try {
          // 映射说明：
          // - complaintNo -> 投诉单号
          // - userId -> 学号（尝试转为数字，如果无法转为数字则保留原字符串）
          // - roomId -> 我用 zixishiOptions 的 index+1 作为 roomId（你可以替换为真实 id 映射）
          // - seatId -> 尝试把座位号转为数字，否则 0
          // - category -> 把投诉类型映射为 index+1
          // - detail -> 投诉详情
          const userIdNum = (() => {
            const n = Number(this.ruleForm.xuehao);
            return Number.isNaN(n) ? this.ruleForm.xuehao : n;
          })();

          const roomIndex = this.zixishiOptions.indexOf(this.ruleForm.zixishi);
          const roomId = roomIndex >= 0 ? (roomIndex + 1) : 0;

          const seatIdNum = (() => {
            const n = Number(this.ruleForm.zuoweihao);
            return Number.isNaN(n) ? 0 : n;
          })();

          const categoryIndex = this.tousuleixingOptions.indexOf(this.ruleForm.tousuleixing);
          const category = categoryIndex >= 0 ? (categoryIndex + 1) : 0;

          const payload = {
            complaintNo: this.ruleForm.tousudanhao,
            userId: userIdNum,
            roomId: roomId,
            seatId: seatIdNum,
            category: category,
            evidenceUrls: null, // 如果你有图片上传请传 URL 数组（或 JSON 字符串）
            detail: this.ruleForm.tousuReason,
            // status / isProcessed 后端默认会处理，这里不强制
          };

          console.log("提交的投诉数据：", payload);

// 替换你现有的请求头构造部分（add.vue 中）
          const headers = { 'Content-Type': 'application/json;charset=UTF-8' };
          const token = localStorage.getItem('Token') || localStorage.getItem('token') || localStorage.getItem('Authorization');
          if (token) {
            headers['Authorization'] = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
            headers['Token'] = token;
          }

          const resp = await axios.post('/complaint/save', payload, {
            headers,
            withCredentials: true
          });
          // 根据后端返回判断
          if (resp && resp.data && resp.data.code === 0) {
            this.$message({
              message: this.type === 'edit' ? "投诉更新成功" : "投诉提交成功，等待管理员处理",
              type: "success",
              duration: 1600,
              onClose: () => {
                this.$router.push({
                  path: '/index/tousu',
                  replace: true
                });
              },
            });
          } else {
            // 如果你的 R.ok 返回格式不同（比如没有 code），可以根据实际调整判断
            const msg = (resp && resp.data && resp.data.msg) || '提交失败，请重试';
            this.$message.error(msg);
          }
        } catch (err) {
          console.error("提交投诉出错：", err);
          this.$message.error("提交失败，网络或服务器错误");
        } finally {
          this.submitting = false;
        }
      });
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