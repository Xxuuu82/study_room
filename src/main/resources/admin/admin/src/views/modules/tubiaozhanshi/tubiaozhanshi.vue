<template>
  <div class="page-container">
    <!-- 外层容器：页面背景为浅灰，内容区域为白色卡片 -->
    <div class="content-card">
      <!-- 核心数据卡片：替换为运营类指标，保留原有布局 -->
      <div class="stat-section">
        <div class="stat-card">
          <div class="stat-title">当日预约量</div>
          <div class="stat-value">3473 人次</div>
          <div class="stat-desc">较昨日 <span class="text-up">↑7.5%</span></div>
        </div>
        <div class="stat-card">
          <div class="stat-title">今日违规次数</div>
          <div class="stat-value">35 次</div>
          <div class="stat-desc">较昨日 <span class="text-down">↓8.0%</span></div>
        </div>
        <div class="stat-card">
          <div class="stat-title">平均自习时长</div>
          <div class="stat-value">4.1小时</div>
          <div class="stat-desc">目标值 <span class="text-highlight">≥3小时</span></div>
        </div>
      </div>

      <!-- 图表区1：保留原趋势图 + 饼图，不做任何修改 -->
      <div class="chart-section chart-row-1">
        <div class="chart-item chart-main">
          <div class="chart-title">个人近15天平均自习时长趋势</div>
          <div class="chart-content">
            <div id="personalTrendChart" style="width:100%;height:280px;"></div>
          </div>
        </div>
        <div class="chart-item chart-side">
          <div class="chart-title">个人自习时段分布</div>
          <div class="chart-content">
            <div id="timeDistributionChart" style="width:100%;height:280px;"></div>
          </div>
        </div>
      </div>

      <!-- 图表区2：TOP10排行调整为运营类指标 -->
      <div class="chart-section chart-row-2">
        <div class="chart-item chart-full">
          <div class="chart-title">各学院预约量TOP10</div>
          <div class="chart-content">
            <div id="top10Chart" style="width:100%;height:350px;"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'StudyDataChart',
  data() {
    return {
      // 保留原有个人时长和时段分布数据，不修改
      personalData: {
        trend: [
          { date: '12-04', duration: 3.5 },
          { date: '12-05', duration: 3.2 },
          { date: '12-06', duration: 2.9 },
          { date: '12-07', duration: 3.1 },
          { date: '12-08', duration: 3.9 },
          { date: '12-09', duration: 3.5 },
          { date: '12-10', duration: 4.2 },
          { date: '12-11', duration: 3.2 },
          { date: '12-12', duration: 4.2 },
          { date: '12-13', duration: 3.2 },
          { date: '12-14', duration: 3.9 },
          { date: '12-15', duration: 3.2 },
          { date: '12-16', duration: 3.3 },
          { date: '12-17', duration: 2.9 },
          { date: '12-18', duration: 3.6 }
        ],
        timeDistribution: [
          { name: '早间（6-9点）', value: 5 },
          { name: '上午（9-12点）', value: 25 },
          { name: '下午（14-17点）', value: 45 },
          { name: '晚间（18-22点）', value: 20 },
        ]
      },
      // TOP10数据替换为各学院预约量
      top10Data: [
        { academy: '信息学院', code: '01', count: 556 },
        { academy: '人文学院', code: '02', count: 482 },
        { academy: '商学院', code: '03', count: 425 },
        { academy: '物理学院', code: '04', count: 410 },
        { academy: '外国语学院', code: '05', count: 388 },
        { academy: '法学院', code: '06', count: 360 },
        { academy: '机械学院', code: '07', count: 325 },
        { academy: '海洋学院', code: '08', count: 272 },
        { academy: '医学院', code: '09', count: 255 },
      ],
      // 保留原有TOP10配色方案
      top10Colors: [
        '#EF6B6B', '#8ECDC4', '#45B7D1', '#96CEB4', '#FECA57',
        '#7F9FF3', '#54A0FF', '#FF27CF', '#00D2D3'
      ],
      trendChart: null,
      top10Chart: null,
      distributionChart: null
    }
  },
  mounted() {
    this.initCharts()
    window.addEventListener('resize', () => {
      this.trendChart?.resize()
      this.top10Chart?.resize()
      this.distributionChart?.resize()
    })
  },
  beforeDestroy() {
    this.trendChart?.dispose()
    this.top10Chart?.dispose()
    this.distributionChart?.dispose()
  },
  methods: {
    initCharts() {
      // 1. 个人趋势折线图：完全保留原有逻辑，不做任何修改
      this.trendChart = echarts.init(document.getElementById('personalTrendChart'))
      this.trendChart.setOption({
        backgroundColor: 'transparent',
        tooltip: { trigger: 'axis', formatter: '{b}：{c}小时' },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: {
          type: 'category',
          data: this.personalData.trend.map(item => item.date),
          axisLabel: {
            color: '#666',
            interval: 0,
            rotate: 0
          }
        },
        yAxis: {
          type: 'value',
          name: '时长（小时）',
          nameTextStyle: { color: '#666' },
          axisLabel: { color: '#666' }
        },
        series: [{
          name: '自习时长',
          type: 'line',
          data: this.personalData.trend.map(item => item.duration),
          smooth: true,
          itemStyle: { color: '#409eff' },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
              { offset: 1, color: 'rgba(64, 158, 255, 0.05)' }
            ])
          }
        }]
      })

      // 2. 时段分布饼图：完全保留原有逻辑，不做任何修改
      this.distributionChart = echarts.init(document.getElementById('timeDistributionChart'))
      this.distributionChart.setOption({
        backgroundColor: 'transparent',
        tooltip: { trigger: 'item', formatter: '{b}：{c}%' },
        legend: {
          orient: 'vertical',
          left: 10,
          top: 'center',
          textStyle: { color: '#666', fontSize: 12 },
          itemGap: 15,
          itemWidth: 12,
          itemHeight: 12
        },
        series: [{
          name: '时段占比',
          type: 'pie',
          radius: ['20%', '80%'],
          center: ['60%', '50%'],
          data: this.personalData.timeDistribution,
          label: {
            show: true,
            formatter: '{c}%',
            fontSize: 12,
            position: 'outside'
          },
          labelLine: {
            show: true,
            length: 15,
            length2: 10
          },
          itemStyle: {
            color: (params) => {
              const colors = ['#409eff', '#67c23a', '#e6a23c', '#f56c6c', '#909399']
              return colors[params.dataIndex]
            }
          }
        }]
      })

      // 3. TOP10柱状图：调整为学院预约量统计，保留原有配色和样式
      this.top10Chart = echarts.init(document.getElementById('top10Chart'))
      this.top10Chart.setOption({
        backgroundColor: 'transparent',
        tooltip: { trigger: 'axis', formatter: '{b}：{c}人次' },
        grid: { left: '8%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: {
          type: 'value',
          name: '预约量（人次）',
          nameTextStyle: { color: '#666' },
          axisLabel: { color: '#666' }
        },
        yAxis: {
          type: 'category',
          data: this.top10Data.map(item => `${item.academy}（${item.code}）`),
          axisLabel: { color: '#666', fontSize: 11 }
        },
        series: [{
          name: '预约量',
          type: 'bar',
          data: this.top10Data.map(item => item.count),
          itemStyle: {
            color: (params) => {
              return this.top10Colors[params.dataIndex] || '#67c23a';
            }
          },
          label: { show: true, position: 'right', color: '#333' }
        }]
      })
    }
  }
}
</script>

<style lang="scss" scoped>
/* 页面外层容器：浅灰背景，居中内容 */
.page-container {
  padding: 22px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 80px);
  display: flex;
}

/* 内容主卡片：纯白背景，统一容器 */
.content-card {
  width: 100%;
  max-width: 1550px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  padding: 24px;
}

/* 核心数据卡片区：新增下降样式 */
.stat-section {
  display: flex;
  gap: 20px;
  margin-bottom: 24px;

  .stat-card {
    flex: 1;
    padding: 20px;
    border-radius: 8px;
    background-color: #fafafa;
    text-align: center;

    .stat-title {
      font-size: 14px;
      color: #999;
      margin-bottom: 8px;
    }

    .stat-value {
      font-size: 32px;
      font-weight: 600;
      color: #333;
      margin-bottom: 8px;
    }

    .stat-desc {
      font-size: 12px;
      color: #666;

      .text-up {
        color: #67c23a;
      }

      .text-down {
        color: #f56c6c;
      }

      .text-highlight {
        color: #409eff;
        font-weight: 500;
      }
    }
  }
}

/* 图表区通用样式：完全保留原有布局和响应式逻辑 */
.chart-section {
  display: flex;
  gap: 20px;
  margin-bottom: 24px;

  .chart-item {
    border-radius: 8px;
    background-color: #fafafa;
    padding: 16px;

    .chart-title {
      font-size: 16px;
      font-weight: 500;
      color: #333;
      margin-bottom: 12px;
      padding-bottom: 8px;
      border-bottom: 1px solid #f0f0f0;
    }

    .chart-content {
      width: 100%;
      height: 280px;
    }
  }

  &.chart-row-1 {
    .chart-main {
      flex: 6;
    }
    .chart-side {
      flex: 4;
    }
  }

  &.chart-row-2 {
    .chart-full {
      flex: 1 1 100%;
    }
    .chart-content {
      height: 350px;
    }
  }
}

/* 响应式适配：完全保留原有逻辑 */
@media (max-width: 1200px) {
  .chart-section.chart-row-1 {
    flex-direction: column;
  }
  .stat-section {
    flex-wrap: wrap;
    .stat-card {
      flex: 1 1 calc(50% - 10px);
      margin-bottom: 10px;
    }
  }
}
@media (max-width: 768px) {
  .stat-section {
    flex-direction: column;
  }
  .content-card {
    padding: 16px;
  }
  ::v-deep #personalTrendChart .xAxis .axisLabel {
    font-size: 10px;
  }
  ::v-deep #timeDistributionChart .legend {
    font-size: 10px !important;
    itemGap: 10px !important;
  }
}
</style>