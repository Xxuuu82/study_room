<template>
  <div class="addEdit-block" style="width: 100%">
    <!-- 面包屑 -->
    <div :style='{"width":"100%","padding":"20px","margin":"10px auto","borderRadius":"27px","background":"#bfd2fc","boxSizing":"border-box"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'Ξ'" :style='{"fontSize":"14px","lineHeight":"1"}'>
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item>预约信息</el-breadcrumb-item>
        <el-breadcrumb-item>取消预约</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-form
        :style="{
        padding: '30px',
        boxShadow: '0 0px 0px rgba(64, 158, 255, .3)',
        borderRadius: '6px',
        flexWrap: 'wrap',
        background: '#fff',
        display: 'flex',
        width: '100%',
        maxWidth: '1200px',
        margin: '20px auto'
      }"
        class="add-update-preview"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="100px"
    >
      <template>
        <!-- 预约单号 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            label="预约单号"
            prop="yuyuedanhao"
        >
          <el-input
              v-model="ruleForm.yuyuedanhao"
              placeholder="预约单号"
              readonly
          ></el-input>
        </el-form-item>

        <!-- 名称 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            label="名称"
            prop="mingcheng"
        >
          <el-input
              v-model="ruleForm.mingcheng"
              placeholder="名称"
              readonly
          ></el-input>
        </el-form-item>

        <!-- 图片 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="upload"
            label="图片"
            prop="tupian"
        >
          <img
              v-if="ruleForm.tupian && ruleForm.tupian.substring(0, 4) == 'http'"
              :src="ruleForm.tupian.split(',')[0]"
              width="100"
              height="100"
              style="objectFit: cover; borderRadius: 4px;"
          />
          <img
              v-else-if="ruleForm.tupian"
              :src="baseUrl + ruleForm.tupian.split(',')[0]"
              width="100"
              height="100"
              style="objectFit: cover; borderRadius: 4px;"
          />
          <span v-else>无图片</span>
        </el-form-item>

        <!-- 座位号 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            label="座位号"
            prop="zuowei"
        >
          <el-input
              v-model="ruleForm.zuowei"
              placeholder="座位号"
              readonly
          ></el-input>
        </el-form-item>

        <!-- 预约时间 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            label="预约时间"
            prop="yuyueshijian"
        >
          <el-input
              v-model="ruleForm.yuyueshijian"
              placeholder="预约时间"
              readonly
          ></el-input>
        </el-form-item>

        <!-- 取消时间 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="date"
            label="取消时间"
            prop="quxiaoshijian"
        >
          <el-date-picker
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="ruleForm.quxiaoshijian"
              type="datetime"
              placeholder="取消时间"
              readonly
          ></el-date-picker>
        </el-form-item>

        <!-- 学号 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            label="学号"
            prop="xuehao"
        >
          <el-input
              v-model="ruleForm.xuehao"
              placeholder="学号"
              readonly
          ></el-input>
        </el-form-item>

        <!-- 姓名 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            label="姓名"
            prop="xingming"
        >
          <el-input
              v-model="ruleForm.xingming"
              placeholder="姓名"
              readonly
          ></el-input>
        </el-form-item>

        <!-- 手机 -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="input"
            label="手机"
            prop="shouji"
        >
          <el-input
              v-model="ruleForm.shouji"
              placeholder="手机"
              readonly
          ></el-input>
        </el-form-item>

        <!-- 取消原因（必填） -->
        <el-form-item
            :style="{ width: '100%', margin: '0 0 20px 0' }"
            class="textarea"
            label="取消原因"
            prop="quxiaoyuanyin"
        >
          <el-input
              style="width: 100%; max-width: 800px"
              type="textarea"
              :rows="5"
              placeholder="请输入取消预约的原因"
              v-model="ruleForm.quxiaoyuanyin"
          >
          </el-input>
        </el-form-item>
      </template>

      <!-- 按钮区域 -->
      <el-form-item
          :style="{ width: '100%', padding: '0', margin: '0', textAlign: 'center' }"
          class="btn"
      >
        <el-button
            :style="{
            border: '0',
            cursor: 'pointer',
            padding: '0',
            margin: '0 20px 0 0',
            outline: 'none',
            color: 'rgba(255, 255, 255, 1)',
            borderRadius: '30px',
            background: '#93C7B3',
            width: '128px',
            lineHeight: '40px',
            fontSize: '14px',
            height: '40px',
          }"
            type="primary"
            class="btn-success"
            @click="onSubmit"
        >提交</el-button
        >
        <el-button
            :style="{
            border: '0px solid rgba(64, 158, 255, 1)',
            cursor: 'pointer',
            padding: '0',
            margin: '0',
            outline: 'none',
            color: '#fff',
            borderRadius: '30px',
            background: '#93C7B3',
            width: '128px',
            lineHeight: '40px',
            fontSize: '14px',
            height: '40px',
          }"
            class="btn-close"
            @click="back()"
        >返回</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
// 数字，邮件，手机，url，身份证校验
import { isMobile } from "@/utils/validate";
export default {
  data() {
    let self = this;
    var validateMobile = (rule, value, callback) => {
      if (!value) {
        callback();
      } else if (!isMobile(value)) {
        callback(new Error("请输入正确的手机号码"));
      } else {
        callback();
      }
    };
    return {
      baseUrl: '',
      ruleForm: {
        yuyuedanhao: "",
        mingcheng: "",
        tupian: "",
        zuowei: "",
        yuyueshijian: "",
        quxiaoshijian: "",
        quxiaoyuanyin: "",
        xuehao: "",
        xingming: "",
        shouji: "",
        yuyueId: "", // 关联预约单ID
        zixishiid: "" // 新增：自习室ID，解决非法表名问题
      },
      rules: {
        quxiaoyuanyin: [
          { required: true, message: '请输入取消原因', trigger: 'blur' },
          { min: 5, max: 200, message: '取消原因长度在5-200字之间', trigger: 'blur' }
        ],
        shouji: [{ validator: validateMobile, trigger: "blur" }]
      }
    };
  },
  created() {
    this.baseUrl = this.$config.baseUrl;
    // 初始化当前时间
    this.ruleForm.quxiaoshijian = this.getCurDateTime();
    // 接收list.vue传递的预约单数据
    this.initOrderData();
  },
  methods: {
    // 获取当前时间（yyyy-MM-dd HH:mm:ss）
    getCurDateTime() {
      const date = new Date();
      const y = date.getFullYear();
      const m = (date.getMonth() + 1).toString().padStart(2, '0');
      const d = date.getDate().toString().padStart(2, '0');
      const h = date.getHours().toString().padStart(2, '0');
      const min = date.getMinutes().toString().padStart(2, '0');
      const s = date.getSeconds().toString().padStart(2, '0');
      return `${y}-${m}-${d} ${h}:${min}:${s}`;
    },

    // 初始化预约单数据
    initOrderData() {
      console.log('路由参数:', this.$route.query);

      let order = null;

      // 从orderData字段获取
      if (this.$route.query.orderData) {
        try {
          order = JSON.parse(this.$route.query.orderData);
          console.log('解析到的订单数据:', order);
        } catch (e) {
          console.error('解析orderData失败:', e);
        }
      }

      // 如果没有获取到数据，显示错误并返回
      if (!order) {
        this.$message.error('未获取到预约单信息');
        this.back();
        return;
      }

      // 赋值预约单信息
      this.ruleForm.yuyuedanhao = order.yuyuedanhao || '';
      this.ruleForm.mingcheng = order.mingcheng || '';
      this.ruleForm.tupian = order.tupian || '';
      this.ruleForm.zuowei = order.zuowei || '';
      this.ruleForm.yuyueshijian = order.yuyueshijian || '';
      this.ruleForm.xuehao = order.xuehao || '';
      this.ruleForm.xingming = order.xingming || '';
      this.ruleForm.shouji = order.shouji || '';
      this.ruleForm.yuyueId = order.id || '';
      // 新增：赋值自习室ID，解决非法表名问题
      this.ruleForm.zixishiid = order.zixishiid || '';

      console.log('初始化后的表单数据:', this.ruleForm);
    },

    // 提交取消预约（仅修改这部分：补充yuyueshijian参数）
    onSubmit() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          // 构造提交数据（关键修改：添加yuyueshijian字段）
          const submitData = {
            crossrefid: this.ruleForm.yuyueId, // 核心：传递原预约ID给后端crossrefid
            yuyueId: this.ruleForm.yuyueId,
            yuyuedanhao: this.ruleForm.yuyuedanhao,
            mingcheng: this.ruleForm.mingcheng,
            zuowei: this.ruleForm.zuowei,
            yuyueshijian: this.ruleForm.yuyueshijian, // 新增：传递预约时间
            quxiaoshijian: this.ruleForm.quxiaoshijian,
            quxiaoyuanyin: this.ruleForm.quxiaoyuanyin,
            xuehao: this.ruleForm.xuehao,
            xingming: this.ruleForm.xingming,
            shouji: this.ruleForm.shouji,
            zixishiid: this.ruleForm.zixishiid // 传递自习室ID到后端
          };

          console.log('提交取消预约数据:', submitData);

          this.$http.post('quxiaoyuyue/save', submitData).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "取消预约提交成功！",
                type: "success",
                duration: 1500,
                onClose: () => {
                  // 返回预约信息列表页
                  this.$router.push('/index/yuyuexinxi');
                }
              });
            } else {
              this.$message.error(data.msg || '取消预约提交失败！');
            }
          }).catch((error) => {
            console.error('请求失败:', error);
            this.$message.error('网络请求失败，请重试');
          });
        } else {
          this.$message.error('请完善表单信息');
        }
      });
    },

    // 返回列表页
    back() {
      this.$router.push('/index/yuyuexinxi');
    }
  }
};
</script>
<style lang="scss" scoped>
.amap-wrapper {
  width: 100%;
  height: 500px;
}

.search-box {
  position: absolute;
}

.el-date-editor.el-input {
  width: auto;
}

.add-update-preview .el-form-item >>> .el-form-item__label {
  padding: 0 10px 0 0;
  color: #666;
  font-weight: 500;
  width: 100px;
  font-size: 14px;
  line-height: 40px;
  text-align: right;
}

.add-update-preview .el-form-item >>> .el-form-item__content {
  margin-left: 100px;
}

.add-update-preview .el-input >>> .el-input__inner {
  border: 2px solid #797979;
  border-radius: 0;
  padding: 0 12px;
  box-shadow: 0 0 0px rgba(64, 158, 255, 0.5);
  outline: none;
  color: #000;
  width: 100%;
  max-width: 400px;
  font-size: 14px;
  height: 40px;
}

.add-update-preview .el-select >>> .el-input__inner {
  border: 2px solid #797979;
  border-radius: 0;
  padding: 0 10px;
  box-shadow: 0 0 0px rgba(64, 158, 255, 0.5);
  outline: none;
  color: #000;
  width: 200px;
  font-size: 14px;
  height: 40px;
}

.add-update-preview .el-date-editor >>> .el-input__inner {
  border: 2px solid #797979;
  border-radius: 0;
  padding: 0 10px 0 30px;
  box-shadow: 0 0 0px rgba(64, 158, 255, 0.5);
  outline: none;
  color: #000;
  width: 200px;
  font-size: 14px;
  height: 40px;
}

.add-update-preview >>> .el-upload--picture-card {
  background: transparent;
  border: 0;
  border-radius: 0;
  width: auto;
  height: auto;
  line-height: initial;
  vertical-align: middle;
}

.add-update-preview >>> .upload .upload-img {
  border: 1px dashed #797979;
  cursor: pointer;
  border-radius: 6px;
  color: #797979;
  width: 90px;
  font-size: 32px;
  line-height: 90px;
  text-align: center;
  height: 90px;
}

.add-update-preview >>> .el-upload-list .el-upload-list__item {
  border: 1px dashed #797979;
  cursor: pointer;
  border-radius: 6px;
  color: #797979;
  width: 90px;
  font-size: 32px;
  line-height: 90px;
  text-align: center;
  height: 90px;
}

.add-update-preview >>> .el-upload .el-icon-plus {
  border: 1px dashed #797979;
  cursor: pointer;
  border-radius: 6px;
  color: #797979;
  width: 90px;
  font-size: 32px;
  line-height: 90px;
  text-align: center;
  height: 90px;
}

.add-update-preview .el-textarea >>> .el-textarea__inner {
  border: 2px solid #797979;
  border-radius: 0;
  padding: 12px;
  box-shadow: 0 0 0px rgba(64, 158, 255, 0.5);
  outline: none;
  color: #000;
  width: 100%;
  font-size: 14px;
  height: 120px;
}
</style>