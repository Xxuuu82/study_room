<template>
  <div style="width: 100%; min-height: 100vh; padding: 0 10px; boxSizing: border-box; background: #bfd2fc;">
    <!-- 面包屑（和预约信息页一致） -->
    <div :style='{"width":"100%","padding":"20px","margin":"10px auto","borderRadius":"27px","background":"#bfd2fc"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'Ξ'" :style='{"fontSize":"14px","lineHeight":"1"}'>
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index">{{item.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 列表容器（全屏自适应） -->
    <div class="list-preview" :style='{"width":"100%","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"20px auto","position":"relative","background":"#fff","boxSizing":"border-box"}'>
      <!-- 查询表单（和预约信息页风格统一） -->
      <el-form
          :inline="true"
          :model="formSearch"
          class="center-form-pv"
          :style="{ width: '100%', padding: '20px 6px', margin: '0 auto', background: 'rgba(0,0,0,0.102)', boxSizing: 'border-box' }"
      >
        <el-row :style="{ flexDirection: 'row', display: 'flex', flexWrap: 'wrap', justifyContent: 'flex-start', gap: '20px' }">
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >名称</label
            >
            <el-input
                v-model="formSearch.mingcheng"
                placeholder="名称"
                clearable
                style="width: 100%;"
            ></el-input>
          </div>
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
              class="select"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >规模</label
            >
            <el-select
                v-model="formSearch.guimo"
                placeholder="请选择规模"
                clearable
                style="width: 100%;"
            >
              <el-option v-for="(item, index) in guimoOptions" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </div>
          <div
              :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
          >
            <label
                :style="{
                margin: '0',
                color: '#000',
                textAlign: 'left',
                display: 'inline-block',
                width: '100%',
                lineHeight: '40px',
                fontSize: '14px',
                fontWeight: '500',
                height: '40px',
              }"
                class="item-label"
            >位置</label
            >
            <el-input
                v-model="formSearch.weizhi"
                placeholder="位置"
                clearable
                style="width: 100%;"
            ></el-input>
          </div>
          <div :style="{ margin: 'auto 0 0', minWidth: '120px' }">
            <el-button
                :style="{
                border: '1px solid #97C9D6',
                cursor: 'pointer',
                padding: '0 24px',
                boxShadow: '0px 2px 2px 0px #78ABC3',
                outline: 'none',
                margin: '10px 0 0',
                color: '#78ABC3',
                borderRadius: '30px',
                background: '#fff',
                width: '100%',
                fontSize: '14px',
                height: '40px',
              }"
                type="success"
                @click="getList(1, curFenlei)"
            >查询</el-button
            >
          </div>
          <!-- 移除添加按钮 -->
        </el-row>
      </el-form>

      <!-- 自习室列表（美化卡片+统一风格） -->
      <div class="list" :style='{"padding":"20px 10px","background":"rgba(0,0,0,0.05)","height":"auto"}'>
        <div class="list2 index-pv1" :style='{"width":"100%","padding":"20px 0","flexWrap":"wrap","gap":"20px","display":"flex","justifyContent":"flex-start"}'>
          <div
              :style='{"boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.2)","padding":"15px","margin":"0","background":"#ffff","borderRadius":"8px","width":"calc(33.33% - 15px)","display":"flex","flexDirection":"column","height":"320px"}'
              v-for="(item, index) in dataList"
              :key="index"
              @click="toDetail(item)"
              class="list-item animation-box"
              style="transition: all 0.3s ease;"
          >
            <img
                :style='{"width":"100%","height":"200px","objectFit":"cover","borderRadius":"4px","marginBottom":"15px"}'
                v-if="item.tupian && item.tupian.substr(0,4)=='http'"
                :src="item.tupian"
                class="image"
            />
            <img
                :style='{"width":"100%","height":"200px","objectFit":"cover","borderRadius":"4px","marginBottom":"15px"}'
                v-else
                :src="baseUrl + (item.tupian?item.tupian.split(',')[0]:'')"
                class="image"
            />
            <div class="item-info" :style='{"padding":"0","flex":"1"}'>
              <div :style='{"lineHeight":"28px","fontSize":"16px","fontWeight":"500","color":"#333"}' class="name ">{{item.mingcheng}}</div>
              <div :style='{"lineHeight":"24px","fontSize":"13px","color":"#666","marginTop":"8px"}'>
                <span>规模：{{item.guimo || '无'}}</span>
                <span style="marginLeft: 15px;">位置：{{item.weizhi || '无'}}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 分页（和预约信息页一致） -->
      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          background
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          :layout="layouts.join()"
          :total="total"
          prev-text="<"
          next-text=">"
          :hide-on-single-page="true"
          :style="{
          width: '100%',
          padding: '20px 0',
          margin: '0 auto',
          whiteSpace: 'nowrap',
          color: '#333',
          fontWeight: '500',
          textAlign: 'center',
          boxSizing: 'border-box',
        }"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      baseUrl: '',
      breadcrumbItem: [{ name: '自习室' }],
      formSearch: {
        mingcheng: '',
        guimo: '',
        weizhi: '',
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      total: 1,
      curFenlei: '全部',
      guimoOptions: [],
      layouts: ["total", "prev", "pager", "next", "sizes", "jumper"]
    }
  },
  created() {
    this.baseUrl = this.$config.baseUrl;
    this.guimoOptions = '大型,中型,小型'.split(',');
    this.getList(1, '全部');
  },
  methods: {
    // 移除add方法
    getList(page, fenlei) {
      this.pageIndex = page;
      let params = {page, limit: this.pageSize};
      let searchWhere = {};
      if (this.formSearch.mingcheng) searchWhere.mingcheng = '%' + this.formSearch.mingcheng + '%';
      if (this.formSearch.guimo) searchWhere.guimo = this.formSearch.guimo;
      if (this.formSearch.weizhi) searchWhere.weizhi = '%' + this.formSearch.weizhi + '%';

      this.$http.get('zixishi/list', {params: Object.assign(params, searchWhere)}).then(res => {
        if (res.data.code == 0) {
          this.dataList = res.data.data.list;
          this.total = res.data.data.total;
          this.pageSize = res.data.data.pageSize;
        }
      });
    },
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.getList(1);
    },
    currentChangeHandle(val) {
      this.getList(val);
    },
    toDetail(item) {
      this.$router.push({path: '/index/zixishiDetail', query: {detailObj: JSON.stringify(item)}});
    },
    isAuth() {
      // 保留原有权限判断逻辑（若有）
      return true;
    }
  }
}
</script>

<style lang="scss" scoped>
// 全局盒模型
* {
  box-sizing: border-box;
}

// 面包屑样式
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__separator {
  margin: 0 9px;
  color: #000;
  font-weight: 500;
}
.breadcrumb-preview .el-breadcrumb >>> .el-breadcrumb__inner {
  color: #000;
  display: inline-block;
}

// 查询表单样式
.center-form-pv {
  .el-input, .el-select {
    width: 100%;
  }
  .el-input__inner {
    border: 1px solid #78abc3;
    border-radius: 4px;
    padding: 0 12px;
    box-shadow: 0px 2px 2px 0px #78abc3;
    outline: none;
    color: #000;
    font-size: 14px;
    height: 40px;
  }
  .el-select .el-input__inner {
    border: 1px solid #78abc3;
    border-radius: 4px;
    padding: 0 10px;
    box-shadow: 0px 2px 2px 0px #78abc3;
    outline: none;
    color: #000;
    font-size: 14px;
    height: 40px;
  }
}

// 卡片悬停效果
.list-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0,0,0,0.15);
}

// 分页样式
.el-pagination >>> {
  .el-pagination__total {
    margin: 0 10px 0 0;
    color: #666;
    font-weight: 400;
  }
  .el-pager .number {
    cursor: pointer;
    padding: 0 4px;
    margin: 0 5px;
    color: #666;
    background: #f4f4f5;
    min-width: 30px;
    height: 28px;
    line-height: 28px;
    border-radius: 2px;
  }
  .el-pager .number:hover, .el-pager .number.active {
    background: #2e61e1;
    color: #fff;
  }
}

// 响应式适配
@media (max-width: 992px) {
  .list-item {
    width: calc(50% - 15px) !important;
  }
}
@media (max-width: 576px) {
  .list-item {
    width: 100% !important;
  }
}
</style>