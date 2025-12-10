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
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="图片" v-if="type!='cross' || (type=='cross' && !ro.tupian)" prop="tupian">
        <file-upload
            tip="点击上传图片"
            action="file/upload"
            :limit="3"
            :multiple="true"
            :fileUrls="ruleForm.tupian?ruleForm.tupian:''"
            @change="tupianUploadChange"
        ></file-upload>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' class="upload" v-else label="图片" prop="tupian">
        <img v-if="ruleForm.tupian.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.tupian.split(',')[0]" width="100" height="100">
        <img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.tupian.split(',')" :src="baseUrl+item" width="100" height="100">
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}'  label="规模" prop="guimo">
        <el-select v-model="ruleForm.guimo" placeholder="请选择规模"  >
          <el-option
              v-for="(item,index) in guimoOptions"
              :key="index"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="座位" prop="zuowei">
        <el-input v-model="ruleForm.zuowei"
                  placeholder="座位" clearable readonly></el-input>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="位置" prop="weizhi">
        <el-input v-model="ruleForm.weizhi"
                  placeholder="位置" clearable ></el-input>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="设施" prop="sheshi">
        <el-input v-model="ruleForm.sheshi"
                  placeholder="设施" clearable ></el-input>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="可约时间" prop="keyueshijian">
        <el-input v-model="ruleForm.keyueshijian"
                  placeholder="可约时间" clearable ></el-input>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}'  label="是否有课" prop="shifouyouke">
        <el-select v-model="ruleForm.shifouyouke" placeholder="请选择是否有课"  >
          <el-option
              v-for="(item,index) in shifouyoukeOptions"
              :key="index"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item :style='{"width":"80%","padding":"10px","margin":"0 2% 10px","display":"inline-block"}' label="详情" prop="xiangqing">
        <editor
            :style='{"minHeight":"400px","padding":"0","boxShadow":"0 0 6px #2e61e1","margin":"0","borderColor":"#ccc","backgroundColor":"#fff","borderRadius":"0","color":"#000","borderWidth":"0","width":"100%","borderStyle":"solid","height":"auto"}'
            v-model="ruleForm.xiangqing"
            class="editor"
            action="file/upload">
        </editor>
      </el-form-item>

      <el-form-item :style='{"padding":"0","margin":"0"}'>
        <el-button :style='{"border":"0","cursor":"pointer","padding":"0","margin":"0 20px 0 0","backgroundColor":"#fff","color":"rgba(255, 255, 255, 1)","backgroundImage":"url(http://codegen.caihongy.cn/20221018/f6c1f39bff574b6cb2564bbedb76b604.png)","outline":"none","borderRadius":"4px","width":"128px","lineHeight":"40px","fontSize":"14px","backgroundSize":"100% 100%","height":"40px"}'  type="primary" @click="onSubmit">提交</el-button>
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
        tupian : false,
        guimo : false,
        zuowei : false,
        weizhi : false,
        sheshi : false,
        keyueshijian : false,
        shifouyouke : false,
        xiangqing : false,
      },
      type: '',
      userTableName: localStorage.getItem('UserTableName'),
      ruleForm: {
        mingcheng: '',
        tupian: '',
        guimo: '',
        zuowei: '20',
        weizhi: '',
        sheshi: '',
        keyueshijian: '',
        shifouyouke: '是',
        xiangqing: '',
      },
      guimoOptions: [],
      shifouyoukeOptions: [],
      rules: {
        mingcheng: [
          { required: true, message: '名称不能为空', trigger: 'blur' },
        ],
        tupian: [
        ],
        guimo: [
        ],
        zuowei: [
          { validator: this.$validate.isIntNumer, trigger: 'blur' },
        ],
        weizhi: [
          { required: true, message: '位置不能为空', trigger: 'blur' },
        ],
        sheshi: [
        ],
        keyueshijian: [
        ],
        shifouyouke: [
        ],
        xiangqing: [
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
          if(o=='tupian'){
            this.ruleForm.tupian = obj[o].split(",")[0];
            this.ro.tupian = true;
            continue;
          }
          if(o=='guimo'){
            this.ruleForm.guimo = obj[o];
            this.ro.guimo = true;
            continue;
          }
          if(o=='zuowei'){
            this.ruleForm.zuowei = obj[o];
            this.ro.zuowei = true;
            continue;
          }
          if(o=='weizhi'){
            this.ruleForm.weizhi = obj[o];
            this.ro.weizhi = true;
            continue;
          }
          if(o=='sheshi'){
            this.ruleForm.sheshi = obj[o];
            this.ro.sheshi = true;
            continue;
          }
          if(o=='keyueshijian'){
            this.ruleForm.keyueshijian = obj[o];
            this.ro.keyueshijian = true;
            continue;
          }
          if(o=='shifouyouke'){
            this.ruleForm.shifouyouke = obj[o];
            this.ro.shifouyouke = true;
            continue;
          }
          if(o=='xiangqing'){
            this.ruleForm.xiangqing = obj[o];
            this.ro.xiangqing = true;
            continue;
          }
        }
      }
      // 获取用户信息
      this.$http.get(this.userTableName + '/session', {emulateJSON: true}).then(res => {
        if (res.data.code == 0) {
          var json = res.data.data;
        }
      });
      this.guimoOptions = "大型,中型,小型".split(',')
      this.shifouyoukeOptions = "是,否".split(',')
    },

    // 多级联动参数
    // 多级联动参数
    info(id) {
      this.$http.get('zixishi/detail/${id}', {emulateJSON: true}).then(res => {
        if (res.data.code == 0) {
          this.ruleForm = res.data.data;
        }
      });
    },
    // 提交
    onSubmit() {

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
                console.log('s');
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
            this.$http.get('zixishi/list', {
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


                this.$http.post('zixishi/add', this.ruleForm).then(res => {
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


            this.$http.post('zixishi/add', this.ruleForm).then(res => {
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
    tupianUploadChange(fileUrls) {
      this.ruleForm.tupian = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");;
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