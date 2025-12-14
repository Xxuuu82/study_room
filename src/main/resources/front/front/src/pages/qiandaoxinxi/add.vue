<template>
  <div :style='{"width":"1000px","padding":"20px","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"10px auto","position":"relative","background":"#fff"}'>
    <el-form
        class="add-update-preview"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
    >
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="名称" prop="mingcheng">
        <el-input v-model="ruleForm.mingcheng"
                  placeholder="名称" clearable ></el-input>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="签到时间" prop="qiandaoshijian" >
        <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="ruleForm.qiandaoshijian"
            type="datetime"
            placeholder="签到时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="人脸识别" v-if="type!='cross' || (type=='cross' && !ro.renlianshibie)" prop="renlianshibie">
        <file-upload
            tip="点击上传人脸识别"
            action="file/upload"
            :limit="3"
            :multiple="true"
            :fileUrls="ruleForm.renlianshibie?ruleForm.renlianshibie:''"
            @change="renlianshibieUploadChange"
        ></file-upload>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' class="upload" v-else label="人脸识别" prop="renlianshibie">
        <img v-if="ruleForm.renlianshibie.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.renlianshibie.split(',')[0]" width="100" height="100">
        <img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.renlianshibie.split(',')" :src="baseUrl+item" width="100" height="100">
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="签到备注" prop="qiandaobeizhu">
        <el-input v-model="ruleForm.qiandaobeizhu"
                  placeholder="签到备注" clearable ></el-input>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="学号" prop="xuehao">
        <el-input v-model="ruleForm.xuehao"
                  placeholder="学号" clearable ></el-input>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="手机" prop="shouji">
        <el-input v-model="ruleForm.shouji"
                  placeholder="手机" clearable ></el-input>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="班级" prop="banji">
        <el-input v-model="ruleForm.banji"
                  placeholder="班级" clearable ></el-input>
      </el-form-item>

      <el-form-item :style='{"padding":"0","margin":"0"}'>
        <el-button v-if="!faceMatchFlag" :style='{"border":"0","cursor":"pointer","padding":"0","margin":"0 20px 0 0","backgroundColor":"#fff","color":"rgba(255, 255, 255, 1)","backgroundImage":"url(http://codegen.caihongy.cn/20221018/f6c1f39bff574b6cb2564bbedb76b604.png)","outline":"none","borderRadius":"4px","width":"128px","lineHeight":"40px","fontSize":"14px","backgroundSize":"100% 100%","height":"40px"}' type="primary" @click="onSubmit">人脸校验</el-button>
        <el-button :style='{"border":"0","cursor":"pointer","padding":"0","margin":"0 20px 0 0","backgroundColor":"#fff","color":"rgba(255, 255, 255, 1)","backgroundImage":"url(http://codegen.caihongy.cn/20221018/f6c1f39bff574b6cb2564bbedb76b604.png)","outline":"none","borderRadius":"4px","width":"128px","lineHeight":"40px","fontSize":"14px","backgroundSize":"100% 100%","height":"40px"}' v-if="faceMatchFlag" type="primary" @click="onSubmit">提交</el-button>
        <el-button :style='{"border":"0px solid rgba(64, 158, 255, 1)","cursor":"pointer","padding":"0","margin":"0","backgroundColor":"#fff","color":"#fff","backgroundImage":"url(http://codegen.caihongy.cn/20221018/71c4fd01e19542dda750b9c9523c11bd.png)","outline":"none","borderRadius":"4px","width":"128px","lineHeight":"40px","fontSize":"14px","backgroundSize":"100% 100%","height":"40px"}' @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: '',
      baseUrl: '',
      ro:{
        mingcheng : false,
        qiandaoshijian : false,
        renlianshibie : false,
        qiandaobeizhu : false,
        xuehao : false,
        shouji : false,
        banji : false,
        shhf : false,
      },
      type: '',
      userface : '',
      faceMatchFlag: false,
      userTableName: localStorage.getItem('UserTableName'),
      ruleForm: {
        mingcheng: '',
        qiandaoshijian: '',
        renlianshibie: '',
        qiandaobeizhu: '',
        xuehao: '',
        shouji: '',
        banji: '',
      },
      rules: {
        mingcheng: [
        ],
        qiandaoshijian: [
        ],
        renlianshibie: [
          { required: true, message: '人脸识别不能为空', trigger: 'blur' },
        ],
        qiandaobeizhu: [
        ],
        xuehao: [
        ],
        shouji: [
          { validator: this.$validate.isMobile, trigger: 'blur' },
        ],
        banji: [
        ],
        shhf: [
        ],
      },
    };
  },
  computed: {



  },
  created() {
    //this.bg();
    let type = this.$route.query.type ? this.$route.query.type : '';
    this.init(type);
    this.baseUrl = this.$config.baseUrl;
    this.ruleForm.qiandaoshijian = this.getCurDateTime()
  },
  methods: {
    getMakeZero(s) {
      return s < 10 ? '0' + s : s;
    },
    // 下载
    download(file){
      window.open(`${file}`)
    },
    // 初始化
    init(type) {
      this.type = type;
      if(type=='cross'){
        var obj = JSON.parse(localStorage.getItem('crossObj'));
        for (var o in obj){
          if(o=='mingcheng'){
            this.ruleForm.mingcheng = obj[o];
            this.ro.mingcheng = true;
            continue;
          }
          if(o=='qiandaoshijian'){
            this.ruleForm.qiandaoshijian = obj[o];
            this.ro.qiandaoshijian = true;
            continue;
          }
          if(o=='renlianshibie'){
            this.ruleForm.renlianshibie = obj[o].split(",")[0];
            this.ro.renlianshibie = true;
            continue;
          }
          if(o=='qiandaobeizhu'){
            this.ruleForm.qiandaobeizhu = obj[o];
            this.ro.qiandaobeizhu = true;
            continue;
          }
          if(o=='xuehao'){
            this.ruleForm.xuehao = obj[o];
            this.ro.xuehao = true;
            continue;
          }
          if(o=='shouji'){
            this.ruleForm.shouji = obj[o];
            this.ro.shouji = true;
            continue;
          }
          if(o=='banji'){
            this.ruleForm.banji = obj[o];
            this.ro.banji = true;
            continue;
          }
        }
      }
      // 获取用户信息
      this.$http.get(this.userTableName + '/session', {emulateJSON: true}).then(res => {
        if (res.data.code == 0) {
          var json = res.data.data;
          this.userface = res.data.data.touxiang;
          if((json.xuehao!=''&&json.xuehao) || json.xuehao==0){
            this.ruleForm.xuehao = json.xuehao
          }
          if((json.shouji!=''&&json.shouji) || json.shouji==0){
            this.ruleForm.shouji = json.shouji
          }
          if((json.banji!=''&&json.banji) || json.banji==0){
            this.ruleForm.banji = json.banji
          }
        }
      });
    },

    // 多级联动参数
    // 多级联动参数
    info(id) {
      this.$http.get('qiandaoxinxi/detail/${id}', {emulateJSON: true}).then(res => {
        if (res.data.code == 0) {
          this.ruleForm = res.data.data;
        }
      });
    },
    // 提交
    onSubmit() {
      if(!this.faceMatchFlag) {
        //人脸校验
        if(!this.ruleForm.renlianshibie) {
          this.$message({
            message: "请上传人脸图片",
            type: 'error',
            duration: 1500
          });
          return;
        }
        if(this.ruleForm.renlianshibie!=null) {
          this.ruleForm.renlianshibie = this.ruleForm.renlianshibie.replace(new RegExp(this.$config.baseUrl,"g"),"");
        }
        let params = {
          face1:this.userface.replace("upload/",""),
          face2:this.ruleForm.renlianshibie.replace("upload/",""),
        }
        this.$http.get('matchFace', {params: params}).then(res => {
          if (res.data && res.data.code == 0) {
            if(res.data.score>60) {
              this.faceMatchFlag = true;
              this.$message({
                message: "匹配成功",
                type: "success",
                duration: 1500,
              });
            } else {
              this.faceMatchFlag = false;
              this.$message.error("匹配失败");
              return;
            }
          } else {
            this.faceMatchFlag = false;
            this.$message.error("匹配失败");
            return;
          }
        });
        return;
      }

      //更新跨表属性
      var crossuserid;
      var crossrefid;
      var crossoptnum;
      this.$refs["ruleForm"].validate(valid => {
        if(valid) {
          if(this.type=='cross'){
            var statusColumnName = localStorage.getItem('statusColumnName');
            var statusColumnValue = localStorage.getItem('statusColumnValue');
            if(statusColumnName && statusColumnName!='') {
              var obj = JSON.parse(localStorage.getItem('crossObj'));
              if(!statusColumnName.startsWith("[")) {
                for (var o in obj){
                  if(o==statusColumnName){
                    obj[o] = statusColumnValue;
                  }
                }
                var table = localStorage.getItem('crossTable');
                this.$http.post(table+'/update', obj).then(res => {});
              } else {
                crossuserid=Number(localStorage.getItem('userid'));
                crossrefid=obj['id'];
                crossoptnum=localStorage.getItem('statusColumnName');
                crossoptnum=crossoptnum.replace(/\[/,"").replace(/\]/,"");
              }
            }
          }
          if(crossrefid && crossuserid) {
            this.ruleForm.crossuserid=crossuserid;
            this.ruleForm.crossrefid=crossrefid;
            var params = {
              page: 1,
              limit: 10,
              crossuserid:crossuserid,
              crossrefid:crossrefid,
            }
            this.$http.get('qiandaoxinxi/list', {
              params: params
            }).then(res => {
              if(res.data.data.total>=crossoptnum) {
                this.$message({
                  message: localStorage.getItem('tips'),
                  type: 'success',
                  duration: 1500,
                });
                return false;
              } else {
                // 跨表计算


                this.$http.post('qiandaoxinxi/add', this.ruleForm).then(res => {
                  if (res.data.code == 0) {
                    this.$message({
                      message: '操作成功',
                      type: 'success',
                      duration: 1500,
                      onClose: () => {
                        this.$router.go(-1);
                      }
                    });
                  } else {
                    this.$message({
                      message: res.data.msg,
                      type: 'error',
                      duration: 1500
                    });
                  }
                });
              }
            });
          } else {


            this.$http.post('qiandaoxinxi/add', this.ruleForm).then(res => {
              if (res.data.code == 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.$router.go(-1);
                  }
                });
              } else {
                this.$message({
                  message: res.data.msg,
                  type: 'error',
                  duration: 1500
                });
              }
            });
          }
        }
      });
    },
    // 获取uuid
    getUUID () {
      return new Date().getTime();
    },
    // 返回
    back() {
      this.$router.go(-1);
    },
    renlianshibieUploadChange(fileUrls) {
      this.ruleForm.renlianshibie = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");;
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.el-date-editor.el-input {
  width: auto;
}

.add-update-preview .el-form-item >>> .el-form-item__label {
  padding: 0 10px 0 0;
  color: #666;
  font-weight: 500;
  width: 120px;
  font-size: 14px;
  line-height: 40px;
  text-align: center;
}

.add-update-preview .el-form-item >>> .el-form-item__content {
  margin-left: 120px;
}

.add-update-preview .el-input >>> .el-input__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 0 12px;
  outline: none;
  color: #000;
  background: #BFD2FC;
  width: 100%;
  font-size: 14px;
  height: 40px;
}

.add-update-preview .el-select >>> .el-input__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 0 10px;
  outline: none;
  color: #000;
  background: #BFD2FC;
  width: 100%;
  font-size: 14px;
  height: 40px;
}

.add-update-preview .el-date-editor >>> .el-input__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 0 10px 0 30px;
  outline: none;
  color: #000;
  background: #BFD2FC;
  width: 100%;
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
  border: 1px dashed rgba(64, 158, 255, 1);
  cursor: pointer;
  border-radius: 6px;
  color: rgba(64, 158, 255, 1);
  width: 100px;
  font-size: 32px;
  line-height: 100px;
  text-align: center;
  height: 100px;
}

.add-update-preview >>> .el-upload-list .el-upload-list__item {
  border: 1px dashed rgba(64, 158, 255, 1);
  cursor: pointer;
  border-radius: 6px;
  color: rgba(64, 158, 255, 1);
  width: 100px;
  font-size: 32px;
  line-height: 100px;
  text-align: center;
  height: 100px;
}

.add-update-preview >>> .el-upload .el-icon-plus {
  border: 1px dashed rgba(64, 158, 255, 1);
  cursor: pointer;
  border-radius: 6px;
  color: rgba(64, 158, 255, 1);
  width: 100px;
  font-size: 32px;
  line-height: 100px;
  text-align: center;
  height: 100px;
}

.add-update-preview .el-textarea >>> .el-textarea__inner {
  border: 2px solid #2e61e1;
  border-radius: 4px;
  padding: 12px;
  outline: none;
  color: #000;
  background: #BFD2FC;
  width: 100%;
  font-size: 14px;
  height: 120px;
}
</style>
