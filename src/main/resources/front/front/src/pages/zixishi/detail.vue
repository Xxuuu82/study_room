<template>
  <div style="width: 100%; min-height: 100vh; padding: 0 10px; boxSizing: border-box; background: #bfd2fc;">
    <!-- 面包屑（和列表页风格统一） -->
    <div :style='{"width":"100%","padding":"20px","margin":"10px auto","borderRadius":"27px","background":"#bfd2fc"}' class="breadcrumb-preview">
      <el-breadcrumb :separator="'Ξ'" :style='{"fontSize":"14px","lineHeight":"1"}'>
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item, index) in breadcrumbItem" :key="index">{{item.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 详情容器（全屏自适应） -->
    <div class="detail-preview" :style='{
      width: "100%",
      margin: "20px auto",
      position: "relative",
      background: "#fff",
      boxShadow: "0px 4px 10px 0px rgba(0,0,0,0.302)",
      borderRadius: "8px",
      boxSizing: "border-box",
      padding: "20px"
    }'>
      <!-- 基础信息模块 -->
      <div class="attr" :style="{
        padding: '20px',
        position: 'relative',
        background: '#f8f9fa',
        borderRadius: '8px',
        display: 'flex',
        flexWrap: 'wrap',
        gap: '20px',
        marginBottom: '20px'
      }">
        <!-- 轮播图 -->
        <el-carousel
            :style="{
            flex: '1 1 400px',
            minWidth: '300px',
            height: '400px',
            borderRadius: '8px',
            overflow: 'hidden'
          }"
            trigger="click"
            indicator-position="inside"
            arrow="always"
            autoplay="true"
            interval="3000"
            loop="true"
        >
          <el-carousel-item
              :style="{ borderRadius: '8px', width: '100%', height: '100%' }"
              v-for="(item, idx) in detailBanner"
              :key="idx"
          >
            <el-image
                :style="{ objectFit: 'cover', width: '100%', height: '100%' }"
                v-if="item.substr(0, 4) == 'http'"
                :src="item"
                fit="cover"
                class="image"
            ></el-image>
            <el-image
                :style="{ objectFit: 'cover', width: '100%', height: '100%' }"
                v-else
                :src="baseUrl + item"
                fit="cover"
                class="image"
            ></el-image>
          </el-carousel-item>
        </el-carousel>

        <!-- 信息面板 -->
        <div class="info" :style="{
          flex: '2 1 500px',
          minWidth: '300px',
          padding: '20px',
          background: '#fff',
          borderRadius: '8px',
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
        }">
          <!-- 标题和收藏 -->
          <div class="item" :style="{
            padding: '10px 0',
            margin: '0 0 20px 0',
            alignItems: 'center',
            justifyContent: 'space-between',
            display: 'flex',
            borderBottom: '1px solid #eee'
          }">
            <div :style="{ color: '#333', fontSize: '18px', fontWeight: '500' }">
              {{ detail.mingcheng }}
            </div>
            <!-- 收藏功能已注释 -->
            <!-- <div
                @click="storeup(1)"
                v-show="!isStoreup"
                :style="{
                padding: '8px 12px',
                background: '#f8f9fa',
                borderRadius: '4px',
                cursor: 'pointer',
                transition: 'all 0.2s'
              }"
                @mouseenter="this.style.background='#e9ecef'"
                @mouseleave="this.style.background='#f8f9fa'"
            >
              <i :style="{ color: '#999', fontSize: '14px', marginRight: '6px' }" class="el-icon-star-off"></i>
              <span :style="{ color: '#666', fontSize: '14px' }">点我收藏</span>
            </div>
            <div
                @click="storeup(-1)"
                v-show="isStoreup"
                :style="{
                padding: '8px 12px',
                background: '#f8f9fa',
                borderRadius: '4px',
                cursor: 'pointer',
                transition: 'all 0.2s'
              }"
                @mouseenter="this.style.background='#e9ecef'"
                @mouseleave="this.style.background='#f8f9fa'"
            >
              <i :style="{ color: '#2E61E1', fontSize: '14px', marginRight: '6px' }" class="el-icon-star-on"></i>
              <span :style="{ color: '#666', fontSize: '14px' }">取消收藏</span>
            </div> -->
          </div>

          <!-- 信息列表 -->
          <div style="display: grid; gridTemplateColumns: repeat(auto-fit, minmax(250px, 1fr)); gap: 15px;">
            <div class="info-item" :style="{
              padding: '12px',
              background: '#f8f9fa',
              borderRadius: '6px'
            }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">规模</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ detail.guimo || '暂无' }}</div>
            </div>
            <div class="info-item" :style="{
              padding: '12px',
              background: '#f8f9fa',
              borderRadius: '6px'
            }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">座位</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ detail.sd_zuowei || '暂无' }}</div>
            </div>
            <div class="info-item" :style="{
              padding: '12px',
              background: '#f8f9fa',
              borderRadius: '6px'
            }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">位置</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ detail.weizhi || '暂无' }}</div>
            </div>
            <div class="info-item" :style="{
              padding: '12px',
              background: '#f8f9fa',
              borderRadius: '6px'
            }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">设施</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ detail.sheshi || '暂无' }}</div>
            </div>
            <div class="info-item" :style="{
              padding: '12px',
              background: '#f8f9fa',
              borderRadius: '6px'
            }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">可约时间</div>
              <div :style="{ color: '#333', fontSize: '14px', fontWeight: '400' }">{{ detail.keyueshijian || '暂无' }}</div>
            </div>
            <div class="info-item" :style="{
              padding: '12px',
              background: '#f8f9fa',
              borderRadius: '6px'
            }">
              <div :style="{ color: '#999', fontSize: '13px', marginBottom: '4px' }">是否有课</div>
              <el-tag :type="detail.shifouyouke === '是' ? 'warning' : 'success'" size="mini">
                {{ detail.shifouyouke || '暂无' }}
              </el-tag>
            </div>
          </div>

          <!-- 在线预约按钮 -->
          <div class="btn" :style="{
            padding: '20px 0 10px',
            justifyContent: 'center',
            display: 'flex'
          }">
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
                fontSize: '14px',
                height: '40px',
                transition: 'all 0.2s'
              }"
                v-if="isAuth('zixishi', '在线预约')"
                @click="onAcross('yuyuexinxi', '', '', '')"
                type="success"
                @mouseenter="this.style.transform='translateY(-1px)'"
                @mouseleave="this.style.transform='translateY(0)'"
            >在线预约</el-button
            >
          </div>
        </div>
      </div>

      <!-- 座位选择模块（美化版） -->
      <div class="seat-map" :style="{
        padding: '30px',
        margin: '0 0 20px 0',
        background: '#f8f9fa',
        borderRadius: '8px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        position: 'relative'
      }">
        <!-- 标题+状态说明 -->
        <div class="seat-header" :style="{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          marginBottom: '20px',
          flexWrap: 'wrap',
          gap: '10px'
        }">
          <div class="seat-title" :style="{
            fontSize: '16px',
            color: '#333',
            fontWeight: '500',
            paddingBottom: '10px',
            borderBottom: '1px solid #eee',
            flex: '1 1 100%'
          }">
            座位选择
          </div>
          <!-- 状态说明标签 -->
          <div class="seat-legend" :style="{
            display: 'flex',
            gap: '20px',
            fontSize: '13px',
            color: '#666'
          }">
            <div :style="{display: 'flex', alignItems: 'center', gap: '6px'}">
              <div :style="{
                width: '16px',
                height: '16px',
                border: '2px solid #2E61E1',
                borderRadius: '4px',
                background: '#fff'
              }"></div>
              <span>可选座位</span>
            </div>
            <div :style="{display: 'flex', alignItems: 'center', gap: '6px'}">
              <div :style="{
                width: '16px',
                height: '16px',
                border: '2px solid #2E61E1',
                borderRadius: '4px',
                background: '#2E61E1',
                boxShadow: '0 0 0 2px rgba(46, 97, 225, 0.3)'
              }"></div>
              <span>已选座位</span>
            </div>
            <div :style="{display: 'flex', alignItems: 'center', gap: '6px'}">
              <div :style="{
                width: '16px',
                height: '16px',
                border: '2px solid #dcdfe6',
                borderRadius: '4px',
                background: '#f0f2f5'
              }"></div>
              <span>已占用</span>
            </div>
          </div>
        </div>

        <!-- 座位网格 -->
        <div v-if="seats" class="seat-grid" :style="{
          display: 'grid',
          gap: '15px',
          gridTemplateColumns: 'repeat(auto-fill, minmax(45px, 1fr))',
          justifyItems: 'center',
          maxWidth: '900px',
          margin: '0 auto',
          padding: '20px',
          background: '#fff',
          borderRadius: '8px',
          boxShadow: '0 1px 3px rgba(0,0,0,0.05)'
        }">
          <!-- 座位方框（美化版） -->
          <div
              v-for="seat in seats"
              :key="seat.id"
              class="seat-item"
              :class="{
              'seat-selected': seat.selected,
              'seat-occupied': seat.status === 'occupied',
              'seat-available': seat.status === 'available'
            }"
              @click="handleSeatClick(seat)"
              :style="{
              width: '45px',
              height: '45px',
              borderRadius: '8px',
              cursor: seat.status === 'occupied' ? 'not-allowed' : 'pointer',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              fontSize: '15px',
              fontWeight: '500',
              transition: 'all 0.2s ease-in-out',
              position: 'relative',
              border: seat.status === 'occupied'
                ? '2px solid #dcdfe6'
                : '2px solid #2E61E1',
              backgroundColor: seat.selected
                ? '#2E61E1'
                : (seat.status === 'occupied' ? '#f0f2f5' : '#fff'),
              color: seat.selected ? '#fff' : (seat.status === 'occupied' ? '#c0c4cc' : '#333')
            }"
          >
            <!-- 座位号 -->
            {{ seat.number }}
            <!-- 已占用座位添加禁用图标 -->
            <i
                v-if="seat.status === 'occupied'"
                class="el-icon-lock"
                :style="{
                position: 'absolute',
                bottom: '2px',
                right: '2px',
                fontSize: '10px',
                color: '#c0c4cc'
              }"
            ></i>
          </div>
        </div>

        <!-- 已选座位提示 -->
        <div class="selected-hint" v-if="seats" :style="{
          marginTop: '20px',
          color: '#666',
          fontSize: '14px',
          textAlign: 'center',
          padding: '10px',
          background: '#fff',
          borderRadius: '6px',
          maxWidth: '500px',
          margin: '0 auto'
        }">
          <i class="el-icon-info" :style="{color: '#2E61E1', marginRight: '6px'}"></i>
          已选座位：<span style="color: #2e61e1; font-weight: 500;">{{ selectedSeatsText }}</span>
          <span style="marginLeft: 10px; color: '#999';">（最多选择{{ maxSelect }}个座位）</span>
        </div>

        <div v-else style="padding: 20px; color: #666; text-align: center;">加载座位信息中...</div>
      </div>

      <!-- 新增：返回按钮区域 -->
      <div style="width: 100%; display: flex; justify-content: center; margin: 30px 0 20px;">
        <el-button
            :style="{
            border: '1px solid #97C9D6',
            cursor: 'pointer',
            padding: '0 24px',
            boxShadow: '0px 2px 2px 0px #78ABC3',
            outline: 'none',
            color: '#78ABC3',
            borderRadius: '30px',
            background: '#fff',
            fontSize: '14px',
            height: '40px',
            transition: 'all 0.2s'
          }"
            @click="goBack"
            @mouseenter="this.style.transform='translateY(-1px)'"
            @mouseleave="this.style.transform='translateY(0)'"
        >
          <i class="el-icon-arrow-left" style="margin-right: 6px;"></i>返回自习室列表
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import CountDown from "@/components/CountDown";
export default {
  data() {
    return {
      tablename: "zixishi",
      baseUrl: "",
      breadcrumbItem: [{ name: "自习室详情" }],
      title: "",
      detailBanner: [],
      endTime: 0,
      detail: {},
      total: 1,
      pageSize: 5,
      pageSizes: [10, 20, 30, 50],
      totalPage: 1,
      rules: {
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      },
      // 收藏功能相关数据已注释
      // storeupParams: {
      //   name: "",
      //   picture: "",
      //   refid: 0,
      //   tablename: "zixishi",
      //   userid: localStorage.getItem("userid"),
      // },
      // isStoreup: false,
      // storeupInfo: {},
      buynumber: 1,
      seats: null, // 座位数据
      maxSelect: 1,
      error: ""
    };
  },
  computed: {
    // 已选座位文字显示
    selectedSeatsText() {
      return (
          this.seats
              .filter((s) => s.selected)
              .map((s) => s.number)
              .join(", ") || "暂无"
      );
    },
  },
  created() {
    this.init();
    this.initSeats();
  },
  methods: {
    // 新增：返回列表页方法
    goBack() {
      this.$router.push('/index/zixishi');
    },
    init() {
      this.baseUrl = this.$config.baseUrl;
      if (this.$route.query.detailObj) {
        this.detail = JSON.parse(this.$route.query.detailObj);
      }
      if (this.$route.query.storeupObj) {
        this.detail.id = JSON.parse(this.$route.query.storeupObj).refid;
      }
      this.$http
          .get(this.tablename + "/detail/" + this.detail.id, {})
          .then((res) => {
            if (res.data.code == 0) {
              this.detail = res.data.data;
              this.title = this.detail.mingcheng;
              this.detailBanner = this.detail.tupian
                  ? this.detail.tupian.split(",")
                  : [];
              this.$forceUpdate();
            }
          });

      // 收藏状态获取已注释
      // this.getStoreupStatus();
    },
    async initSeats(zixishiId) {
      try {
        // 1. 从后端获取座位数据
        const response = await this.$http.get(this.tablename + "/seats/" + this.detail.id, {});
        const dbSeats = response.data;
        console.log(dbSeats);
        // 2. 转换为前端需要的格式
        this.seats = dbSeats.map((seat) => ({
          id: `seat_${seat.id}`,
          number: `${seat.id}`,
          status: seat.status === 1 ? "available" : "occupied",
          selected: false,
        }));

        // 3. 清除旧状态
        this.error = "";
      } catch (err) {
        this.error = "座位数据加载失败";
        console.error("API错误:", err.response?.data || err.message);

        // 可选：显示默认空数据
        this.seats = [];
      }
    },

    // 获取座位颜色（保留原有逻辑，兼容样式）
    getSeatColor(seat) {
      if (seat.selected) return "#2E61E1";
      return seat.status === "occupied" ? "#F0F2F5" : "#fff";
    },

    // 点击座位（原有逻辑完全不变）
    handleSeatClick(seat) {
      if (seat.status === "occupied") {
        this.$message.warning("该座位已被占用");
        return;
      }

      const selectedCount = this.seats.filter((s) => s.selected).length;
      const newSeats = this.seats.map((s) => {
        if (s.id === seat.id) {
          if (!s.selected && selectedCount >= this.maxSelect) {
            this.$message.warning(`最多选择${this.maxSelect}个座位`);
            return s;
          }
          return { ...s, selected: !s.selected };
        }
        return s;
      });

      this.seats = newSeats;
    },
    onAcross(
        acrossTable,
        crossOptAudit,
        statusColumnName,
        tips,
        statusColumnValue
    ) {
      // ========== 1. 数据验证 ==========
      const validateSelection = () => {
        const selectedSeats = this.seats.filter((s) => s.selected);

        // 基础验证
        if (selectedSeats.length === 0) {
          this.$message.error("请先选择座位");
          return false;
        }

        // 二次验证（可选）：检查是否有座位在选中后被占用
        const conflictSeats = selectedSeats.filter(
            (s) => s.status === "occupied"
        );
        if (conflictSeats.length > 0) {
          this.$message.error(
              `座位 ${conflictSeats.map((s) => s.number)} 已被占用，请重新选择`
          );
          return false;
        }

        return selectedSeats;
      };

      // ========== 2. 数据存储 ==========
      const storeSelectionData = (seats) => {
        try {
          // 结构化存储数据
          const storageData = {
            version: "1.0",
            timestamp: new Date().getTime(),
            data: {
              seatNumbers: seats.map((s) => s.number),
              seatIds: seats.map((s) => s.id),
              zixishiId: this.detail.id,
            },
          };

          // 使用加密存储（可选）
          const encrypted = btoa(JSON.stringify(storageData));
          localStorage.setItem("seatSelection", encrypted);

          // 同步存储基础跨表数据
          localStorage.setItem("crossTable", "zixishi");
          localStorage.setItem("crossObj", JSON.stringify(this.detail));
        } catch (error) {
          console.error("本地存储失败:", error);
          this.$message.error("数据保存失败，请检查浏览器设置");
          return false;
        }
        return true;
      };

      // ========== 3. 主流程 ==========
      const selected = validateSelection();
      if (!selected) return;

      if (!storeSelectionData(selected)) return;

      // ========== 4. 路由跳转 ==========
      const routeParams = {
        path: `/index/${acrossTable}Add`,
        query: {
          type: "cross",
          // 通过URL传递基础信息（防localStorage失效）
          seats: selected.map((s) => s.id).join(","),
          zixishiId: this.detail.id,
        },
      };

      // 处理特殊状态跳转
      if (statusColumnName && !statusColumnName.startsWith("[")) {
        const obj = JSON.parse(localStorage.getItem("crossObj") || {});
        if (obj[statusColumnName] === statusColumnValue) {
          this.$message.success(tips);
          return;
        }
      }
      const obj = JSON.parse(localStorage.getItem("crossObj") || {});

      obj["zuowei"] =
          this.seats
              .filter((s) => s.selected)
              .map((s) => s.number)
              .join(", ") || "暂无";
      var updatedCrossObjStr = JSON.stringify(obj);

      // 保存回 localStorage
      localStorage.setItem("crossObj", updatedCrossObjStr);
      console.log(updatedCrossObjStr);
      this.$router.push(routeParams);
    },
    // 收藏功能方法已注释
    // storeup(type) {
    //   if (type == 1 && !this.isStoreup) {
    //     this.storeupParams.name = this.title;
    //     this.storeupParams.picture = this.detailBanner[0];
    //     this.storeupParams.refid = this.detail.id;
    //     this.storeupParams.type = type;
    //     this.$http.post("storeup/add", this.storeupParams).then((res) => {
    //       if (res.data.code == 0) {
    //         this.isStoreup = true;
    //         this.$message({
    //           type: "success",
    //           message: "收藏成功!",
    //           duration: 1500,
    //         });
    //       }
    //     });
    //   }
    //   if (type == -1 && this.isStoreup) {
    //     let delIds = new Array();
    //     delIds.push(this.storeupInfo.id);
    //     this.$http.post("storeup/delete", delIds).then((res) => {
    //       if (res.data.code == 0) {
    //         this.isStoreup = false;
    //         this.$message({
    //           type: "success",
    //           message: "取消成功!",
    //           duration: 1500,
    //         });
    //       }
    //     });
    //   }
    // },
    // getStoreupStatus() {
    //   if (localStorage.getItem("Token")) {
    //     this.$http
    //         .get("storeup/list", {
    //           params: {
    //             page: 1,
    //             limit: 1,
    //             type: 1,
    //             refid: this.detail.id,
    //             tablename: "zixishi",
    //             userid: localStorage.getItem("userid"),
    //           },
    //         })
    //         .then((res) => {
    //           if (res.data.code == 0 && res.data.data.list.length > 0) {
    //             this.isStoreup = true;
    //             this.storeupInfo = res.data.data.list[0];
    //           }
    //         });
    //   }
    // },
    curChange(page) {
      this.getDiscussList(page);
    },
    prevClick(page) {
      this.getDiscussList(page);
    },
    nextClick(page) {
      this.getDiscussList(page);
    },
    // 下载
    download(file) {
      if (!file) {
        this.$message({
          type: "error",
          message: "文件不存在",
          duration: 1500,
        });
        return;
      }
      window.open(this.baseUrl + file);
    },
    isAuth() {
      // 保留原有权限判断逻辑
      return true;
    }
  },
  components: {
    CountDown,
  },
};
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

// 轮播图样式
.el-carousel >>> .el-carousel__arrow {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: rgba(0,0,0,0.3);
  color: #fff;
  transition: all 0.3s;
}
.el-carousel >>> .el-carousel__arrow:hover {
  background: rgba(46, 97, 225, 0.8);
}
.el-carousel >>> .el-carousel__indicators {
  bottom: 10px;
}
.el-carousel >>> .el-carousel__indicators li {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: rgba(255,255,255,0.5);
  margin: 0 4px;
}
.el-carousel >>> .el-carousel__indicators li.is-active {
  width: 20px;
  border-radius: 4px;
  background: #fff;
}

// 座位样式优化
.seat-item {
  // 可选座位悬停效果
  &.seat-available:not(.seat-selected):hover {
    transform: scale(1.1);
    box-shadow: 0 4px 8px rgba(46, 97, 225, 0.15);
    border-color: #1E4CD8 !important;
  }

  // 已选座位动画效果
  &.seat-selected {
    box-shadow: 0 0 0 3px rgba(46, 97, 225, 0.3);
    animation: pulse 1.5s infinite;
  }

  // 已占用座位样式强化
  &.seat-occupied {
    cursor: not-allowed;
    opacity: 0.8;
  }
}

// 脉冲动画
@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(46, 97, 225, 0.4);
  }
  70% {
    box-shadow: 0 0 0 6px rgba(46, 97, 225, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(46, 97, 225, 0);
  }
}

// 解决scoped动画穿透问题
::v-deep .seat-item.seat-selected {
  animation: pulse 1.5s infinite;
}

// 响应式适配
@media (max-width: 768px) {
  .attr {
    flex-direction: column !important;
  }
  .seat-grid {
    gridTemplateColumns: repeat(auto-fill, minmax(35px, 1fr)) !important;
  }
  .seat-item {
    width: 35px !important;
    height: 35px !important;
    font-size: 12px !important;
  }
  .info-item {
    min-width: 100% !important;
  }
  .seat-legend {
    flex-wrap: wrap !important;
    gap: 10px !important;
  }
}

// 按钮悬停效果
.el-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}
</style>