<template>
  <el-dialog
    :title="title"
    :visible.sync="open"
    width="500px"
    :close-on-click-modal="false"
  >
    <!-- 还原修改弹窗样式 -->
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      label-width="100px"
      validate-on-rule-change="false"
    >
      <el-form-item label="学生姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入学生姓名" />
      </el-form-item>
      <el-form-item label="学号" prop="studentId">
        <el-input v-model="form.studentId" placeholder="请输入9位学号" />
      </el-form-item>
      <el-form-item label="自习时长" prop="studyDurationMin">
        <el-input v-model.number="form.studyDurationMin" placeholder="请输入总分钟数" type="number" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入11位手机号" />
      </el-form-item>
      <el-form-item label="班级" prop="className">
        <el-input v-model="form.className" placeholder="请输入班级" />
      </el-form-item>
    </el-form>
    <template slot="footer">
      <el-button type="primary" @click="submitForm" plain>确 定</el-button>
      <el-button @click="cancel" plain>取 消</el-button>
    </template>
  </el-dialog>
</template>

<script>
export default {
  name: "AddOrUpdate",
  data() {
    return {
      open: false,
      title: "",
      isEdit: false,
      oldStudentId: "", // 恢复原始oldStudentId字段
      form: {
        name: "",
        studentId: "",
        studyDurationMin: "",
        phone: "",
        className: ""
      },
      rules: {
        name: [{ required: true, message: "请输入学生姓名", trigger: "blur" }],
        studentId: [
          { required: true, message: "请输入学号", trigger: "blur" },
          { pattern: /^[0-9]{9}$/, message: "学号必须是9位数字", trigger: "blur" }
        ],
        studyDurationMin: [
          { required: true, message: "请输入自习时长", trigger: "blur" },
          { type: "number", min: 1, message: "时长必须大于0且为数字", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { pattern: /^[0-9]{11}$/, message: "手机号必须是11位数字", trigger: "blur" }
        ],
        className: [{ required: true, message: "请输入班级", trigger: "blur" }]
      }
    };
  },
  methods: {
    // 恢复原始init逻辑（传studentId）
    init(studentId) {
      this.open = true;
      this.oldStudentId = studentId || "";
      this.isEdit = !!studentId;
      this.title = this.isEdit ? "修改自习时长" : "新增自习时长";

      // 初始化表单
      this.form = {
        name: "",
        studentId: "",
        studyDurationMin: "",
        phone: "",
        className: ""
      };

      if (studentId) {
        // 查询详情接口（恢复原始逻辑）
        this.$http.get(`/ranking/info/${studentId}`).then(({ data }) => {
          if (data && data.code === 0 && data.data) {
            const res = data.data;
            this.form = {
              name: res.name || "",
              studentId: res.student_id || res.studentId || "",
              studyDurationMin: res.study_duration_min || res.studyDurationMin || "",
              phone: res.phone || "",
              className: res.className || ""
            };
          }
        });
      }
    },

    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          this.$message.warning("请完善表单信息并确保格式正确");
          return;
        }

        // 恢复原始提交数据格式
        const submitData = {
          oldStudentId: this.oldStudentId,
          name: this.form.name,
          student_id: this.form.studentId,
          study_duration_min: Number(this.form.studyDurationMin),
          phone: this.form.phone,
          className: this.form.className
        };

        const loading = this.$loading({ text: "提交中..." });
        this.$http({
          url: this.isEdit ? "/ranking/update" : "/ranking/save",
          method: "post",
          data: submitData
        }).then(({ data }) => {
          loading.close();
          if (data && data.code === 0) {
            this.$message.success(data.msg || "操作成功");
            this.open = false;
            this.$parent.getDataList();
          } else {
            this.$message.error(data?.msg || "操作失败");
          }
        }).catch(() => {
          loading.close();
          this.$message.error("提交失败，无法连接数据库");
        });
      });
    },

    cancel() {
      this.open = false;
      if (this.$refs.form) this.$refs.form.resetFields();
    }
  }
};
</script>

<style scoped>
.el-dialog__footer { text-align: center; }
.el-button { min-width: 80px; margin: 0 5px; cursor: pointer; }
</style>