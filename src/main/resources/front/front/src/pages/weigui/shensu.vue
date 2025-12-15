<template>
  <div
      :style="{
      width: '100%',
      maxWidth: '1000px',
      padding: '20px',
      boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.302)',
      margin: '10px auto',
      position: 'relative',
      background: '#fff',
      borderRadius: '8px',
      boxSizing: 'border-box'
    }"
  >
    <!-- 面包屑导航（增加跳转事件） -->
    <div :style="{
      marginBottom: '20px',
      paddingBottom: '10px',
      borderBottom: '1px solid #e4e7ed'
    }">
      <el-breadcrumb :separator="'/'" :style='{"fontSize":"14px","lineHeight":"1","color":"#333"}'>
        <el-breadcrumb-item @click="$router.push('/index/home')">首页</el-breadcrumb-item>
        <el-breadcrumb-item @click="$router.push('/index/weigui')">违规记录</el-breadcrumb-item>
        <el-breadcrumb-item>违规申诉</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 违规记录基本信息展示区域 -->
    <div
        :style="{
        width: '100%',
        maxWidth: '800px',
        padding: '20px',
        margin: '0 auto 20px',
        background: '#f8f9fa',
        borderRadius: '8px',
        boxShadow: '0 1px 3px rgba(0, 0, 0, .05)'
      }"
    >
      <h3 :style="{
        margin: '0 0 15px 0',
        fontSize: '16px',
        color: '#333',
        fontWeight: '500'
      }">违规记录信息</h3>

      <div class="info-item" :style="{
        padding: '10px 0',
        display: 'flex',
        borderBottom: '1px dashed #e4e7ed'
      }">
        <div :style="{
          width: '120px',
          textAlign: 'right',
          paddingRight: '15px',
          color: '#666',
          fontSize: '14px'
        }">违规单号：</div>
        <div :style="{
          flex: 1,
          color: '#333',
          fontSize: '14px'
        }">{{ ruleForm.weiguidanhao || '-' }}</div>
      </div>

      <div class="info-item" :style="{
        padding: '10px 0',
        display: 'flex',
        borderBottom: '1px dashed #e4e7ed'
      }">
        <div :style="{
          width: '120px',
          textAlign: 'right',
          paddingRight: '15px',
          color: '#666',
          fontSize: '14px'
        }">违规类型：</div>
        <div :style="{
          flex: 1,
          color: '#333',
          fontSize: '14px'
        }">{{ ruleForm.weiguileixing || '-' }}</div>
      </div>

      <div class="info-item" :style="{
        padding: '10px 0',
        display: 'flex',
        borderBottom: '1px dashed #e4e7ed'
      }">
        <div :style="{
          width: '120px',
          textAlign: 'right',
          paddingRight: '15px',
          color: '#666',
          fontSize: '14px'
        }">违规时间：</div>
        <div :style="{
          flex: 1,
          color: '#333',
          fontSize: '14px'
        }">{{ ruleForm.weiguiTime || '-' }}</div>
      </div>

      <div class="info-item" :style="{
        padding: '10px 0',
        display: 'flex',
        borderBottom: '1px dashed #e4e7ed'
      }">
        <div :style="{
          width: '120px',
          textAlign: 'right',
          paddingRight: '15px',
          color: '#666',
          fontSize: '14px'
        }">关联预约单号：</div>
        <div :style="{
          flex: 1,
          color: '#333',
          fontSize: '14px'
        }">{{ ruleForm.relatedOrder || '-' }}</div>
      </div>

      <div class="info-item" :style="{
        padding: '10px 0',
        display: 'flex',
        borderBottom: '1px dashed #e4e7ed'
      }">
        <div :style="{
          width: '120px',
          textAlign: 'right',
          paddingRight: '15px',
          color: '#666',
          fontSize: '14px'
        }">违规原因：</div>
        <div :style="{
          flex: 1,
          color: '#333',
          fontSize: '14px',
          whiteSpace: 'pre-wrap'
        }">{{ ruleForm.weiguiReason || '-' }}</div>
      </div>

      <div class="info-item" :style="{
        padding: '10px 0',
        display: 'flex'
      }">
        <div :style="{
          width: '120px',
          textAlign: 'right',
          paddingRight: '15px',
          color: '#666',
          fontSize: '14px'
        }">处罚措施：</div>
        <div :style="{
          flex: 1,
          color: '#333',
          fontSize: '14px'
        }">{{ ruleForm.punishment || '-' }}</div>
      </div>
    </div>

    <!-- 申诉表单区域 -->
    <el-form
        class="add-update-preview"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
    >
      <!-- 申诉单号 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="申诉单号"
          prop="shensudanhao"
      >
        <el-input
            v-model="ruleForm.shensudanhao"
            placeholder="申诉单号"
            readonly
        ></el-input>
      </el-form-item>

      <!-- 申诉人学号 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="申诉人学号"
          prop="xuehao"
      >
        <el-input
            v-model="ruleForm.xuehao"
            placeholder="申诉人学号"
            clearable
            :readonly="ro.xuehao"
        ></el-input>
      </el-form-item>

      <!-- 申诉人姓名 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="申诉人姓名"
          prop="xingming"
      >
        <el-input
            v-model="ruleForm.xingming"
            placeholder="申诉人姓名"
            clearable
            :readonly="ro.xingming"
        ></el-input>
      </el-form-item>

      <!-- 申诉人手机 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="申诉人手机"
          prop="shouji"
      >
        <el-input
            v-model="ruleForm.shouji"
            placeholder="申诉人手机"
            clearable
            :readonly="ro.shouji"
        ></el-input>
      </el-form-item>

      <!-- 申诉时间 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="申诉时间"
          prop="shensuTime"
      >
        <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="ruleForm.shensuTime"
            type="datetime"
            placeholder="选择申诉时间"
            style="width: 100%;"
            default-time="12:00:00"
        >
        </el-date-picker>
      </el-form-item>

      <!-- 申诉原因 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="申诉原因"
          prop="shensuReason"
      >
        <el-input
            v-model="ruleForm.shensuReason"
            type="textarea"
            placeholder="请详细描述您的申诉原因，说明违规记录存在的问题或异议点"
            clearable
            :rows="8"
            style="width: 100%;"
        ></el-input>
      </el-form-item>

      <!-- 申诉诉求 -->
      <el-form-item
          :style="{
          width: '100%',
          maxWidth: '800px',
          padding: '10px',
          margin: '0 auto 10px',
          display: 'block',
          boxSizing: 'border-box'
        }"
          label="申诉诉求"
          prop="shensuSuqiu"
      >
        <el-input
            v-model="ruleForm.shensuSuqiu"
            type="textarea"
            placeholder="请说明您的申诉诉求（如：撤销违规记录、减轻处罚等）"
            clearable
            :rows="4"
            style="width: 100%;"
        ></el-input>
      </el-form-item>

      <!-- 提交/返回按钮 -->
      <el-form-item :style="{
        padding: '20px 0 0',
        margin: '0',
        textAlign: 'center',
        width: '100%'
      }">
        <el-button
            :style="{
            margin: '0 10px 10px',
            padding: '0 24px',
            height: '40px',
            borderRadius: '4px',
            fontSize: '14px',
            background: '#2e61e1',
            color: '#fff',
            border: 'none',
            transition: 'opacity 0.2s'
          }"
            type="primary"
            @click="onSubmit"
        >提交申诉</el-button
        >
        <el-button
            :style="{
            margin: '0 10px 10px',
            padding: '0 24px',
            height: '40px',
            borderRadius: '4px',
            fontSize: '14px',
            background: '#f5f7fa',
            color: '#666',
            border: '1px solid #e4e7ed',
            transition: 'opacity 0.2s'
          }"
            @click="back()"
        >返回</el-button
        >
        <!-- 新增：返回违规详情按钮 -->
        <el-button
            :style="{
            margin: '0 10px 10px',
            padding: '0 24px',
            height: '40px',
            borderRadius: '4px',
            fontSize: '14px',
            background: '#6c757d',
            color: '#fff',
            border: 'none',
            transition: 'opacity 0.2s'
          }"
            @click="backToDetail()"
        >返回违规详情</el-button
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
        shensudanhao: false,
        weiguidanhao: true,
        weiguileixing: true,
        weiguiTime: true,
        relatedOrder: true,
        weiguiReason: true,
        punishment: true,
        xuehao: false,
        xingming: false,
        shouji: false,
        shensuTime: false,
        shensuReason: false,
        shensuSuqiu: false,
      },
      type: "",
      userTableName: localStorage.getItem("UserTableName"),
      // 表单数据
      ruleForm: {
        shensudanhao: this.getUUID(), // 自动生成申诉单号
        weiguidanhao: "", // 违规单号（关联）
        weiguileixing: "", // 违规类型
        weiguiTime: "", // 违规时间
        relatedOrder: "", // 关联预约单号
        weiguiReason: "", // 违规原因
        punishment: "", // 处罚措施
        xuehao: "", // 申诉人学号
        xingming: "", // 申诉人姓名
        shouji: "", // 申诉人手机
        shensuTime: "", // 申诉时间
        shensuReason: "", // 申诉原因
        shensuSuqiu: "", // 申诉诉求
        shensuStatus: "申诉中", // 申诉状态默认值
        shensuHandleStatus: "未处理" // 申诉处理状态默认值
      },
      // 表单校验规则
      rules: {
        shensudanhao: [],
        weiguidanhao: [
          { required: true, message: "违规单号不能为空", trigger: "blur" }
        ],
        // 申诉人学号必填
        xuehao: [
          { required: true, message: "申诉人学号不能为空", trigger: "blur" }
        ],
        // 申诉人姓名必填
        xingming: [
          { required: true, message: "申诉人姓名不能为空", trigger: "blur" }
        ],
        // 手机号校验
        shouji: [{ validator: this.validateMobile, trigger: "blur" }],
        // 申诉时间必填
        shensuTime: [
          { required: true, message: "请选择申诉时间", trigger: "change" },
        ],
        // 申诉原因必填
        shensuReason: [
          { required: true, message: "请详细描述申诉原因", trigger: "blur" },
          { min: 20, message: "申诉原因至少填写20个字", trigger: "blur" }
        ],
        // 申诉诉求必填
        shensuSuqiu: [
          { required: true, message: "请说明您的申诉诉求", trigger: "blur" }
        ]
      },
    };
  },
  computed: {},
  created() {
    this.baseUrl = this.$config.baseUrl;
    // 默认填充当前时间为申诉时间
    this.ruleForm.shensuTime = new Date().toISOString().slice(0, 19).replace('T', ' ');

    // 从路由参数/本地存储获取违规记录信息
    this.initShensuData();

    // 初始化用户信息（自动填充当前登录用户信息）
    this.initUserInfo();
  },
  methods: {
    getMakeZero(s) {
      return s < 10 ? "0" + s : s;
    },
    // 手机号校验方法（内置实现，避免依赖外部validate）
    validateMobile(rule, value, callback) {
      if (!value) {
        callback(); // 非必填，空值直接通过
      } else {
        const reg = /^1[3-9]\d{9}$/;
        if (!reg.test(value)) {
          callback(new Error('请输入正确的手机号'));
        } else {
          callback();
        }
      }
    },
    // 初始化申诉数据（从路由/本地存储获取违规记录）
    initShensuData() {
      // 优先从localStorage获取违规记录详情
      const weiguiObj = JSON.parse(localStorage.getItem("currentWeiguiObj") || "{}");
      if (weiguiObj && weiguiObj.weiguidanhao) {
        this.ruleForm.weiguidanhao = weiguiObj.weiguidanhao || "";
        this.ruleForm.weiguileixing = weiguiObj.weiguileixing || "";
        this.ruleForm.weiguiTime = weiguiObj.weiguiTime || "";
        this.ruleForm.relatedOrder = weiguiObj.relatedOrder || "";
        this.ruleForm.weiguiReason = weiguiObj.weiguiReason || "";
        this.ruleForm.punishment = weiguiObj.punishment || "";
      } else {
        // 如果没有违规记录信息，提示并强制返回违规列表页
        this.$message.warning("未获取到违规记录信息，请选择要申诉的违规记录");
        setTimeout(() => {
          this.$router.push('/index/weigui'); // 直接跳转到违规列表页
        }, 1500);
      }
    },
    // 初始化用户信息
    initUserInfo() {
      // 模拟用户信息（如果接口未实现，先填充测试数据）
      if (!this.userTableName) {
        // 本地测试用：手动填充默认用户信息
        this.ruleForm.xuehao = "20230001";
        this.ruleForm.xingming = "张三";
        this.ruleForm.shouji = "13800138000";
        this.ro.xuehao = true;
        this.ro.xingming = true;
        this.ro.shouji = true;
        return;
      }

      this.$http
          .get(this.userTableName + "/session", { emulateJSON: true })
          .then((res) => {
            if (res.data.code === 0) {
              let userInfo = res.data.data || {};
              // 自动填充用户信息
              if (userInfo.xuehao) {
                this.ruleForm.xuehao = userInfo.xuehao;
                this.ro.xuehao = true; // 学号设为只读
              }
              if (userInfo.xingming) {
                this.ruleForm.xingming = userInfo.xingming;
                this.ro.xingming = true; // 姓名设为只读
              }
              if (userInfo.shouji) {
                this.ruleForm.shouji = userInfo.shouji;
                this.ro.shouji = true; // 手机号设为只读
              }
            }
          })
          .catch(err => {
            console.error("获取用户信息失败：", err);
            this.$message.warning("无法自动获取用户信息，请手动填写");
          });
    },
    // 提交申诉
    onSubmit() {
      // 表单校验
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          // 构造提交数据
          const submitData = {
            ...this.ruleForm,
            // 兼容后端字段格式
            shensu_time: this.ruleForm.shensuTime,
            shensu_reason: this.ruleForm.shensuReason,
            shensu_suqiu: this.ruleForm.shensuSuqiu,
            weigui_danhao: this.ruleForm.weiguidanhao
          };

          console.log("提交的申诉数据：", submitData);

          // 模拟提交请求（实际项目替换为真实接口）
          setTimeout(() => {
            // 模拟接口返回成功
            this.$message({
              message: "申诉提交成功，等待管理员审核处理",
              type: "success",
              duration: 2000,
              onClose: () => {
                // 提交成功后跳转到违规列表页
                this.$router.push('/index/weigui');
              },
            });

            // 同步更新违规记录的申诉状态（更新localStorage）
            const weiguiObj = JSON.parse(localStorage.getItem("currentWeiguiObj") || "{}");
            if (weiguiObj) {
              weiguiObj.shensuStatus = "已申诉";
              weiguiObj.shensuHandleStatus = "处理中";
              localStorage.setItem("currentWeiguiObj", JSON.stringify(weiguiObj));
            }

          }, 1000);

          // 真实接口提交代码（注释掉，供实际使用）
          /*
          this.$http.post("shensuinfo/submit", submitData, {
            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            },
            emulateJSON: false
          })
              .then((res) => {
                console.log("申诉提交结果：", res.data);
                if (res.data.code === 0 || res.data.msg === "申诉提交成功") {
                  this.$message({
                    message: "申诉提交成功，等待管理员审核处理",
                    type: "success",
                    duration: 2000,
                    onClose: () => {
                      this.$router.push('/index/weigui'); // 提交成功跳转到违规列表
                    },
                  });
                } else {
                  this.$message.error(res.data.msg || "申诉提交失败");
                }
              })
              .catch(err => {
                console.error("申诉提交失败：", err);
                this.$message.error("网络异常：" + (err.message || "申诉提交失败"));
              });
          */
        }
      });
    },
    // 生成唯一申诉单号（时间戳）
    getUUID() {
      return "SS" + new Date().getTime();
    },
    // 返回上一页（优先返回违规列表）
    back() {
      this.$router.push('/index/weigui'); // 直接跳转，替代go(-1)避免历史记录问题
    },
    // 新增：返回违规详情页
    backToDetail() {
      // 确保违规记录存在才跳转
      const weiguiObj = JSON.parse(localStorage.getItem("currentWeiguiObj") || "{}");
      if (weiguiObj && weiguiObj.weiguidanhao) {
        this.$router.push('/index/weiguiDetail');
      } else {
        this.$message.warning("未获取到违规记录信息，将返回违规列表");
        setTimeout(() => {
          this.$router.push('/index/weigui');
        }, 1000);
      }
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// 移动端适配
@media (max-width: 768px) {
  .add-update-preview .el-form-item >>> .el-form-item__label {
    width: 100% !important;
    text-align: left !important;
    line-height: 24px !important;
    padding: 0 0 8px 0 !important;
  }

  .add-update-preview .el-form-item >>> .el-form-item__content {
    margin-left: 0 !important;
  }

  .info-item {
    flex-direction: column !important;
  }

  .info-item > div:first-child {
    width: 100% !important;
    text-align: left !important;
    padding-right: 0 !important;
    margin-bottom: 5px !important;
  }
}

// 日期选择器宽度
.el-date-editor.el-input {
  width: 100%;
}

// 表单标签样式
.add-update-preview .el-form-item >>> .el-form-item__label {
  padding: 0 10px 0 0;
  color: #666;
  font-weight: 500;
  width: 120px;
  font-size: 14px;
  line-height: 40px;
  text-align: right;
}

// 表单内容区域
.add-update-preview .el-form-item >>> .el-form-item__content {
  margin-left: 120px;
}

// 输入框样式
.add-update-preview .el-input >>> .el-input__inner,
.add-update-preview .el-select >>> .el-input__inner,
.add-update-preview .el-date-editor >>> .el-input__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 0 12px;
  outline: none;
  color: #000;
  background: #bfd2fc;
  width: 100%;
  font-size: 14px;
  height: 40px;
  box-sizing: border-box;
}

// 文本域样式
.add-update-preview .el-textarea >>> .el-textarea__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 12px;
  outline: none;
  color: #000;
  background: #bfd2fc;
  width: 100%;
  font-size: 14px;
  min-height: 100px;
  box-sizing: border-box;
}

// 按钮hover效果
::v-deep .el-button:hover {
  opacity: 0.9;
  transform: translateY(-1px); // 增加hover上移效果
}

// 面包屑样式优化
.el-breadcrumb >>> .el-breadcrumb__separator {
  margin: 0 8px;
  color: #666;
}

.el-breadcrumb >>> .el-breadcrumb__inner {
  color: #333;
  cursor: pointer; // 提示可点击
}

.el-breadcrumb >>> .el-breadcrumb__inner:hover {
  color: #2e61e1; // hover变色
}

.el-breadcrumb >>> .el-breadcrumb__inner a {
  color: #2e61e1;
  text-decoration: none;
}
</style>