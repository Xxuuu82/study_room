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

        <!-- 预约开始+结束时间（同排） -->
        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="date"
            label="预约开始时间"
            prop="yuyue_start"
        >
          <el-date-picker
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="ruleForm.yuyue_start"
              type="datetime"
              placeholder="预约开始时间"
              readonly
          ></el-date-picker>
        </el-form-item>

        <el-form-item
            :style="{ width: '50%', margin: '0 0 20px 0' }"
            class="date"
            label="预约结束时间"
            prop="yuyue_end"
        >
          <el-date-picker
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="ruleForm.yuyue_end"
              type="datetime"
              placeholder="预约结束时间"
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
              :disabled="!canCancel(ruleForm)"
          >
          </el-input>
          <div v-if="!canCancel(ruleForm)" style="margin-top:8px;color:#f56c6c">
            {{ cancelReason(ruleForm) }}
          </div>
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
            cursor: canCancel(ruleForm) ? 'pointer' : 'not-allowed',
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
            :disabled="!canCancel(ruleForm)"
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
        zuowei: "",
        yuyue_start: "", // 预约开始时间（字符串 yyyy-MM-dd HH:mm:ss）
        yuyue_end: "",   // 预约结束时间
        quxiaoshijian: "",
        quxiaoyuanyin: "",
        xuehao: "",
        xingming: "",
        shouji: "",
        yuyueId: "", // 关联预约单ID
        zixishiid: "", // 自习室ID
        qiandaozhuangtai: "", // 来自预约记录
        qiantuizhuangtai: "", // 来自预约记录
        weiguiFlag: null // 来自预约记录，可能是 weiguiFlag 或 weigui_flag
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

    // 初始化预约单数据（强化校验）
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
          this.$message.error('预约数据解析失败，请返回重试');
          this.back();
          return;
        }
      }

      // 如果没有获取到数据，显示错误并返回
      if (!order) {
        this.$message.error('未获取到预约单信息，请返回重试');
        this.back();
        return;
      }

      // 核心字段非空校验
      if (!order.id) {
        this.$message.error('预约单ID缺失，无法取消');
        this.back();
        return;
      }
      if (!order.zixishiid) {
        this.$message.error('自习室ID缺失，无法取消');
        this.back();
        return;
      }
      if (!order.zuowei && order.zuowei !== 0) {
        this.$message.error('座位号缺失，无法取消');
        this.back();
        return;
      }

      // 赋值预约单信息
      this.ruleForm.yuyuedanhao = order.yuyuedanhao || '';
      this.ruleForm.mingcheng = order.mingcheng || '';
      this.ruleForm.zuowei = order.zuowei || '';
      this.ruleForm.yuyue_start = order.yuyueStart || order.yuyue_start || '';
      this.ruleForm.yuyue_end = order.yuyueEnd || order.yuyue_end || '';
      this.ruleForm.xuehao = order.xuehao || '';
      this.ruleForm.xingming = order.xingming || '';
      this.ruleForm.shouji = order.shouji || '';
      this.ruleForm.yuyueId = order.id || '';
      this.ruleForm.zixishiid = order.zixishiid || '';
      this.ruleForm.qiandaozhuangtai = order.qiandaozhuangtai || '';
      this.ruleForm.qiantuizhuangtai = order.qiantuizhuangtai || '';
      // 支持两种字段命名
      this.ruleForm.weiguiFlag = order.weiguiFlag != null ? order.weiguiFlag : order.weigui_flag != null ? order.weigui_flag : null;

      // 如果当前不允许取消，给用户提示（但仍显示页面，禁止提交）
      if (!this.canCancel(this.ruleForm)) {
        // 不自动返回页，防止用户看见详情并理解原因
        this.$message.warning(this.cancelReason(this.ruleForm));
      }

      console.log('初始化后的表单数据:', this.ruleForm);
    },

    // 提交取消预约（前端二次校验）
    onSubmit() {
      // 前端再校验一次，避免页面被绕过
      if (!this.canCancel(this.ruleForm)) {
        this.$message.warning(this.cancelReason(this.ruleForm));
        return;
      }

      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          // 构造提交数据
          const submitData = {
            crossrefid: this.ruleForm.yuyueId,
            yuyueId: this.ruleForm.yuyueId,
            yuyuedanhao: this.ruleForm.yuyuedanhao,
            mingcheng: this.ruleForm.mingcheng,
            zuowei: this.ruleForm.zuowei,
            yuyue_start: this.ruleForm.yuyue_start,
            yuyue_end: this.ruleForm.yuyue_end,
            quxiaoshijian: this.ruleForm.quxiaoshijian,
            quxiaoyuanyin: this.ruleForm.quxiaoyuanyin,
            xuehao: this.ruleForm.xuehao,
            xingming: this.ruleForm.xingming,
            shouji: this.ruleForm.shouji,
            zixishiid: this.ruleForm.zixishiid
          };

          console.log('提交取消预约数据:', submitData);

          this.$http.post('quxiaoyuyue/save', submitData).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "取消预约提交成功！",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.$router.push('/index/yuyuexinxi');
                }
              });
            } else {
              // 显示后端返回的具体错误信息
              this.$message.error(data.msg || '取消预约提交失败！');
            }
          }).catch((error) => {
            console.error('请求失败详情:', error);
            if (error.status === 500) {
              this.$message.error('服务器处理失败，请联系管理员或稍后重试');
            } else if (error.status === 404) {
              this.$message.error('接口不存在，请检查后端服务');
            } else {
              this.$message.error('网络请求失败，请检查网络连接');
            }
          });
        } else {
          this.$message.error('请完善表单信息（取消原因长度5-200字）');
        }
      });
    },

    // 返回列表页
    back() {
      this.$router.push('/index/yuyuexinxi');
    },

    // 辅助：解析后端时间字符串 "yyyy-MM-dd HH:mm:ss" 为 Date
    parseDateTimeString(dateTimeStr) {
      if (!dateTimeStr || typeof dateTimeStr !== 'string') return null;
      const parts = dateTimeStr.trim().split(' ');
      if (parts.length < 2) return null;
      const datePart = parts[0];
      const timePart = parts[1];
      const [y, m, d] = datePart.split('-').map(Number);
      const [hh, mm, ss] = (timePart || '00:00:00').split(':').map(Number);
      if (![y, m, d].every(n => !isNaN(n))) return null;
      return new Date(y, (m || 1) - 1, d || 1, hh || 0, mm || 0, ss || 0);
    },

    // 是否可以取消：仅在“未到可签到时间”且未签到且未违规时允许取消
    canCancel(row) {
      if (!row) return false;
      // 已违规不可取消
      const wf = row.weiguiFlag != null ? row.weiguiFlag : row.weigui_flag != null ? row.weigui_flag : null;
      if (wf === 1 || String(wf) === '1') return false;

      // 若已经签到 且 未签退 -> 不可取消
      if (row.qiandaozhuangtai === '已签到' && row.qiantuizhuangtai !== '已签退') return false;

      // 仅当当前时间 < 预约开始时间才允许取消（未到可签到时间）
      const start = row.yuyue_start || row.yuyueStart || row.yuyueStart;
      if (!start) return false;
      const startDate = this.parseDateTimeString(start);
      if (!startDate) return false;

      return new Date().getTime() < startDate.getTime();
    },

    // 返回禁止取消的原因文本（用于展示）
    cancelReason(row) {
      if (!row) return '不可取消';
      const wf = row.weiguiFlag != null ? row.weiguiFlag : row.weigui_flag != null ? row.weigui_flag : null;
      if (wf === 1 || String(wf) === '1') return '不可取消（已违规）';
      if (row.qiandaozhuangtai === '已签到' && row.qiantuizhuangtai !== '已签退') return '不可取消（已签到）';
      const start = row.yuyue_start || row.yuyueStart || row.yuyueStart;
      if (start) {
        const startDate = this.parseDateTimeString(start);
        if (startDate && new Date().getTime() >= startDate.getTime()) return '（预约已开始）';
      }
      return '不可取消';
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