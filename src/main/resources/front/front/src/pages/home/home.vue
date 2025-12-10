<template>
  <div class="home-preview" :style='{"width":"100%","margin":"10px 0","display":"flex","flexDirection":"column","alignItems":"center"}'>
    <!-- 公告资讯模块（保持不变） -->
    <div class="news" :style='{
        "border":"2px solid #845EC2",
        "padding":"20px 10px 40px",
        "margin":"50px auto",
        "background":"linear-gradient(180deg, #f5efff, #fff)",
        "width":"calc(100% - 160px)",
        "max-width":"1200px",
        "position":"relative",
        "height":"auto",
        "borderRadius":"12px",
        "boxShadow":"0 4px 15px rgba(132, 94, 194, 0.1)"
    }'>
      <div v-if="false" class="idea newsIdea" :style='{"padding":"20px","flexWrap":"wrap","background":"#efefef","justifyContent":"space-between","display":"flex"}'>
        <div class="box1" :style='{"width":"20%","background":"#fff","height":"80px"}'></div>
        <div class="box2" :style='{"width":"20%","background":"#fff","height":"80px"}'></div>
        <div class="box3" :style='{"width":"20%","background":"#fff","height":"80px"}'></div>
        <div class="box4" :style='{"width":"20%","background":"#fff","height":"80px"}'></div>
        <div class="box5" :style='{"width":"20%","background":"#fff","display":"none","height":"80px"}'></div>
        <div class="box6" :style='{"width":"20%","background":"#fff","display":"none","height":"80px"}'></div>
        <div class="box7" :style='{"width":"20%","background":"#fff","display":"none","height":"80px"}'></div>
        <div class="box8" :style='{"width":"20%","background":"#fff","display":"none","height":"80px"}'></div>
        <div class="box9" :style='{"width":"20%","background":"#fff","display":"none","height":"80px"}'></div>
        <div class="box10" :style='{"width":"20%","background":"#fff","display":"none","height":"80px"}'></div>
      </div>

      <div class="title" :style='{"width":"200px","margin":"10px auto","lineHeight":"54px","textAlign":"center"}'>
        <span :style='{"color":"#845EC2","fontSize":"28px","fontWeight":"500","textShadow":"0 1px 3px rgba(132, 94, 194, 0.2)"}'>公告资讯</span>
      </div>

      <div v-if="newsList.length" class="list list5 index-pv1" :style='{"padding":"10px","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%","justifyContent":"space-around","height":"auto","borderRadius":"8px"}'>
        <div @click="toDetail('newsDetail', newsList[0])" v-if="newsList.length>0" class="list-item animation-box" :style='{"width":"94%","boxShadow":" 0px 4px 10px 0px rgba(132, 94, 194, 0.15)","margin":"0 0 20px","background":"#fff","height":"250px","borderRadius":"8px","overflow":"hidden","transition":"all 0.3s ease"}'>
          <img :style='{"width":"50%","objectFit":"cover","float":"left","height":"100%"}' :src="baseUrl + newsList[0].picture" alt="">
          <div :style='{"width":"50%","padding":"10px","overflow":"hidden","background":"#fff","display":"inline-block","height":"250px"}'>
            <div :style='{"padding":"0 10px","lineHeight":"32px","fontSize":"16px","overflow":"hidden","color":"#333","height":"32px","fontWeight":"500"}' class="new5-one-info-title line1">{{newsList[0].title}}</div>
            <div :style='{"padding":"0 10px","margin":"5px 0","overflow":"hidden","color":"#666","lineHeight":"30px","fontSize":"14px","height":"120px"}' class="new5-one-info-descript line3">{{newsList[0].introduction}}</div>
            <div :style='{"padding":"10px","lineHeight":"32px","fontSize":"12px","color":"#999"}' class="new5-one-info-time">{{newsList[0].addtime}}</div>
          </div>
        </div>
        <div :style='{"width":"100%","flexWrap":"wrap","background":"#fff","justifyContent":"center","display":"flex","height":"275px"}' class="list-body">
          <div @click="toDetail('newsDetail', newsList[1])" v-if="newsList.length>1" class="new5-item animation-box" :style='{"width":"30%","padding":"10px","boxShadow":"0px 4px 10px 0px rgba(132, 94, 194, 0.15)","position":"relative","background":"#FFFFFF","height":"280px","borderRadius":"8px","overflow":"hidden","transition":"all 0.3s ease"}'>
            <div :style='{"padding":"10px","color":"#fff","textAlign":"center","background":"linear-gradient(90deg, #845EC2, #D65DB1)","display":"inline-block","width":"100%","lineHeight":"32px","fontSize":"12px","borderRadius":"4px 4px 0 0"}' class="new5-item-time">{{newsList[1].addtime.split(" ")[0]}}</div>
            <div :style='{"padding":"0 10px","lineHeight":"32px","fontSize":"16px","overflow":"hidden","color":"#333","height":"64px","fontWeight":"500"}' class="new5-item-title line1">{{newsList[1].title}}</div>
            <div :style='{"padding":"0 10px","lineHeight":"32px","fontSize":"14px","overflow":"hidden","color":"#666","height":"96px"}' class="new5-item-descript line3">{{newsList[1].introduction}}</div>
          </div>
          <div @click="toDetail('newsDetail', newsList[2])" v-if="newsList.length>2" class="new5-item animation-box" :style='{"padding":"10px","boxShadow":"0px 4px 10px 0px rgba(132, 94, 194, 0.15)","margin":"0 10px","background":"#FFFFFF","width":"30%","position":"relative","height":"280px","borderRadius":"8px","overflow":"hidden","transition":"all 0.3s ease"}'>
            <div :style='{"padding":"10px","color":"#fff","textAlign":"center","background":"linear-gradient(90deg, #D65DB1, #FF6F91)","display":"inline-block","width":"100%","lineHeight":"32px","fontSize":"12px","borderRadius":"4px 4px 0 0"}' class="new5-item-time">{{newsList[2].addtime.split(" ")[0]}}</div>
            <div :style='{"padding":"0 10px","lineHeight":"32px","fontSize":"16px","overflow":"hidden","color":"#333","height":"64px","fontWeight":"500"}' class="new5-item-title line1">{{newsList[2].title}}</div>
            <div :style='{"padding":"0 10px","lineHeight":"32px","fontSize":"14px","overflow":"hidden","color":"#666","height":"96px"}' class="new5-item-descript line3">{{newsList[2].introduction}}</div>
          </div>
          <div @click="toDetail('newsDetail', newsList[3])" v-if="newsList.length>3" class="new5-item animation-box" :style='{"width":"30%","padding":"10px","boxShadow":"0px 4px 10px 0px rgba(132, 94, 194, 0.15)","position":"relative","background":"#FFFFFF","height":"280px","borderRadius":"8px","overflow":"hidden","transition":"all 0.3s ease"}'>
            <div :style='{"padding":"10px","color":"#fff","textAlign":"center","background":"linear-gradient(90deg, #FF6F91, #FF9671)","display":"inline-block","width":"100%","lineHeight":"32px","fontSize":"12px","borderRadius":"4px 4px 0 0"}' class="new5-item-time">{{newsList[3].addtime.split(" ")[0]}}</div>
            <div :style='{"padding":"0 10px","lineHeight":"32px","fontSize":"16px","overflow":"hidden","color":"#333","height":"64px","fontWeight":"500"}' class="new5-item-title line1">{{newsList[3].title}}</div>
            <div :style='{"padding":"0 10px","lineHeight":"32px","fontSize":"14px","overflow":"hidden","color":"#666","height":"96px"}' class="new5-item-descript line3">{{newsList[3].introduction}}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 自习室展示模块 - 调整为公告卡片样式（顶部渐变标题栏+下方图片） -->
    <div class="lists" :style='{
        "border":"2px solid #FF9671",
        "padding":"20px 10px 50px",
        "margin":"50px auto",
        "background":"linear-gradient(180deg, #fff9e6, #fff)",
        "width":"calc(100% - 160px)",
        "max-width":"1200px",
        "position":"relative",
        "height":"auto",
        "borderRadius":"12px",
        "boxShadow":"0 4px 15px rgba(255, 150, 113, 0.1)"
    }'>
      <div class="title" :style='{"width":"200px","margin":"10px auto","lineHeight":"54px","textAlign":"center"}'>
        <span :style='{"color":"#FF9671","fontSize":"28px","fontWeight":"500","textShadow":"0 1px 3px rgba(255, 150, 113, 0.2)"}'>自习室展示</span>
      </div>

      <!-- 自习室列表：顶部渐变标题栏（放自习室名）+ 下方图片 -->
      <div class="list list2 index-pv1" :style='{"padding":"0 10px","flexWrap":"wrap","background":"transparent","display":"flex","width":"100%","justifyContent":"space-around","height":"auto"}'>
        <div :style='{"boxShadow":"0px 4px 10px 0px rgba(255, 150, 113, 0.15)","margin":"10px 0","background":"#ffff","display":"flex","flexDirection":"column","width":"30%","position":"relative","height":"280px","borderRadius":"8px","overflow":"hidden","transition":"all 0.3s ease"}' v-for="(item,index) in zixishiList" class="list-item animation-box" :key="index" @click="toDetail('zixishiDetail', item)">
          <!-- 顶部渐变标题栏（放自习室名） -->
          <div :style='{"padding":"10px","color":"#fff","textAlign":"center","background":"linear-gradient(90deg, #FF9671, #FFC75F)","width":"100%","lineHeight":"32px","fontSize":"16px","fontWeight":"500"}'>
            {{item.mingcheng}}
          </div>
          <!-- 下方图片 -->
          <img :style='{"width":"100%","objectFit":"cover","height":"calc(100% - 32px)"}' v-if="preHttp(item.tupian)" :src="item.tupian.split(',')[0]" alt="" />
          <img :style='{"width":"100%","objectFit":"cover","height":"calc(100% - 32px)"}' v-else :src="baseUrl +  (item.tupian?item.tupian.split(',')[0]:'')" alt="" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  //数据集合
  data() {
    return {
      baseUrl: '',
      queryList:[
        {
          queryName:"名称",
        },
      ],
      queryIndex: 0,
      zixishimingcheng: '',
      newsList: [],
      zixishiList: [],
    }
  },
  created() {
    this.baseUrl = this.$config.baseUrl;
    this.getNewsList();
    this.getList();
  },
  //方法集合
  methods: {
    preHttp(str) {
      return str && str.substr(0,4)=='http';
    },
    search(tablename) {
      if (this.queryIndex == 0 && this.zixishimingcheng) {
        this.$router.push({path: '/index/' + tablename, query: {indexQueryCondition: this.zixishimingcheng}});
      }
    },
    getNewsList() {
      this.$http.get('news/list', {params: {
          page: 1,
          limit: 6,
          order: 'desc'}}).then(res => {
        if (res.data.code == 0) {
          this.newsList = res.data.data.list;
        }
      });
    },
    getList() {
      let autoSortUrl = "";

      this.$http.get('zixishi/list', {params: {
          page: 1,
          limit: 6,
        }}).then(res => {
        if (res.data.code == 0) {
          this.zixishiList = res.data.data.list;
        }
      });
    },
    toDetail(path, item) {
      this.$router.push({path: '/index/' + path, query: {detailObj: JSON.stringify(item)}});
    },
    moreBtn(path) {
      this.$router.push({path: '/index/' + path});
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.home-preview {
  box-sizing: border-box;

  .index-pv1 .animation-box {
    transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
    z-index: initial;
  }

  .index-pv1 .animation-box:hover {
    transform: translateY(-5px) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
    -webkit-perspective: 1000px;
    perspective: 1000px;
    transition: 0.3s;
    z-index: 1;
  }

  .index-pv1 .animation-box img {
    transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
  }

  .index-pv1 .animation-box img:hover {
    transform: scale(0.98) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
    -webkit-perspective: 1000px;
    perspective: 1000px;
    transition: 0.3s;
  }

  // 单行/多行文本溢出省略
  .line1 {
    display: -webkit-box;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }

  .line3 {
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }
}
</style>