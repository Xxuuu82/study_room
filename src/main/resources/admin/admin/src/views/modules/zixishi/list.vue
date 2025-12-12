<template>
  <div style="width: 100%; min-height: 100vh; padding: 0; box-sizing: border-box; background: #fff;">
    <!-- 列表容器 -->
    <div class="list-preview" :style='{"width":"100%","boxShadow":"0px 4px 10px 0px rgba(0,0,0,0.302)","margin":"0 auto","position":"relative","background":"#fff","boxSizing":"border-box","padding":"0"}'>
      <!-- 查询表单 - 顶格显示 -->
      <div class="search-form-container" :style='{"width":"100%","padding":"20px","background":"#f8f9fa","borderBottom":"1px solid #e8e8e8"}'>
        <el-form
            class="center-form-pv"
            :style="{ width: '100%', margin: '0 auto', boxSizing: 'border-box' }"
            :inline="true"
            :model="searchForm"
        >
          <el-row :style="{ flexDirection: 'row', display: 'flex', flexWrap: 'wrap', justifyContent: 'flex-start', alignItems: 'center', gap: '20px' }">
            <div
                :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
            >
              <label
                  :style="{
                  margin: '0 0 8px 0',
                  color: '#333',
                  textAlign: 'left',
                  display: 'inline-block',
                  width: '100%',
                  lineHeight: '1',
                  fontSize: '14px',
                  fontWeight: '600',
                  height: 'auto',
                }"
                  class="item-label"
              >名称</label>
              <el-input
                  v-model="searchForm.mingcheng"
                  placeholder="请输入名称"
                  clearable
                  style="width: 100%;"
              ></el-input>
            </div>
            <div
                :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
                class="select"
                label="规模"
                prop="guimo"
            >
              <label
                  :style="{
                  margin: '0 0 8px 0',
                  color: '#333',
                  textAlign: 'left',
                  display: 'inline-block',
                  width: '100%',
                  lineHeight: '1',
                  fontSize: '14px',
                  fontWeight: '600',
                  height: 'auto',
                }"
                  class="item-label"
              >规模</label>
              <el-select
                  @change="guimoChange"
                  clearable
                  v-model="searchForm.guimo"
                  placeholder="请选择规模"
                  style="width: 100%;"
              >
                <el-option
                    v-for="(item, index) in guimoOptions"
                    v-bind:key="index"
                    :label="item"
                    :value="item"
                    style="padding-left: 8px;"
                ></el-option>
              </el-select>
            </div>
            <div
                :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
            >
              <label
                  :style="{
                  margin: '0 0 8px 0',
                  color: '#333',
                  textAlign: 'left',
                  display: 'inline-block',
                  width: '100%',
                  lineHeight: '1',
                  fontSize: '14px',
                  fontWeight: '600',
                  height: 'auto',
                }"
                  class="item-label"
              >位置</label>
              <el-input
                  v-model="searchForm.weizhi"
                  placeholder="请输入位置"
                  clearable
                  style="width: 100%;"
              ></el-input>
            </div>
            <div
                :style="{ margin: '0', flexDirection: 'column', display: 'flex', minWidth: '200px', flex: '1 1 200px' }"
                class="select"
                label="是否有课"
                prop="shifouyouke"
            >
              <label
                  :style="{
                  margin: '0 0 8px 0',
                  color: '#333',
                  textAlign: 'left',
                  display: 'inline-block',
                  width: '100%',
                  lineHeight: '1',
                  fontSize: '14px',
                  fontWeight: '600',
                  height: 'auto',
                }"
                  class="item-label"
              >是否有课</label>
              <el-select
                  @change="shifouyoukeChange"
                  clearable
                  v-model="searchForm.shifouyouke"
                  placeholder="请选择是否有课"
                  style="width: 100%;"
              >
                <el-option
                    v-for="(item, index) in shifouyoukeOptions"
                    v-bind:key="index"
                    :label="item"
                    :value="item"
                    style="padding-left: 8px;"
                ></el-option>
              </el-select>
            </div>
            <div :style="{ margin: 'auto 0 0', minWidth: '120px' }">
              <el-button
                  :style="{
                  border: 'none',
                  cursor: 'pointer',
                  padding: '0 24px',
                  boxShadow: '0px 2px 4px 0px rgba(0,0,0,0.1)',
                  outline: 'none',
                  margin: '28px 0 0',
                  color: '#fff',
                  borderRadius: '4px',
                  background: '#409EFF',
                  width: '100%',
                  fontSize: '14px',
                  height: '40px',
                  transition: 'all 0.3s',
                }"
                  type="primary"
                  @click="search()"
              >查询</el-button>
            </div>
          </el-row>
        </el-form>
      </div>

      <!-- 表格区域 -->
      <div class="table-container" :style='{"padding":"20px"}'>
        <el-table
            class="custom-table"
            :stripe="true"
            :style="{
            padding: '0',
            borderColor: '#eee',
            borderRadius: '0',
            borderWidth: '1px',
            background: '#fff',
            width: '100%',
            margin: '0',
            borderStyle: 'solid',
            height: 'fit-content',
            boxSizing: 'border-box',
          }"
            :data="dataList"
            v-loading="dataListLoading"
            border
        >
          <el-table-column
              type="index"
              label="索引"
              width="80"
              align="center"
              header-align="center"
              class-name="index-column"
          >
            <template #default="{ $index }">
              <span class="index-badge">{{ $index + 1 + (pageIndex - 1) * pageSize }}</span>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="mingcheng"
              label="名称"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <span style="color: #333; font-weight: 500;">{{ scope.row.mingcheng || '-' }}</span>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="tupian"
              label="图片"
              width="120"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <div v-if="scope.row.tupian" style="display: inline-block;">
                <img
                    v-if="scope.row.tupian.substring(0, 4) == 'http'"
                    :src="scope.row.tupian.split(',')[0]"
                    width="60"
                    height="60"
                    style="objectFit: cover; borderRadius: 4px; border: 1px solid #f0f0f0;"
                />
                <img
                    v-else
                    :src="$base.url + scope.row.tupian.split(',')[0]"
                    width="60"
                    height="60"
                    style="objectFit: cover; borderRadius: 4px; border: 1px solid #f0f0f0;"
                />
              </div>
              <div v-else style="color: #999; font-size: 12px;">无图片</div>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="guimo"
              label="规模"
              width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <el-tag :type="scope.row.guimo === '大型' ? 'danger' : scope.row.guimo === '中型' ? 'warning' : 'success'" style="borderRadius: 12px; padding: 2px 10px; margin-left: 8px;">
                {{ scope.row.guimo || '-' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="zuowei"
              label="座位"
              width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <span style="color: #409EFF; font-weight: 500;">{{ scope.row.zuowei || '0' }}</span>
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="weizhi"
              label="位置"
              min-width="120"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.weizhi || '-' }}
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="keyueshijian"
              label="可约时间"
              min-width="140"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.keyueshijian || '-' }}
            </template>
          </el-table-column>
          <el-table-column
              :resizable="true"
              :sortable="false"
              prop="shifouyouke"
              label="是否有课"
              width="100"
              align="center"
              header-align="center"
          >
            <template slot-scope="scope">
              <el-tag :type="scope.row.shifouyouke === '是' ? 'danger' : 'success'" style="borderRadius: 12px; padding: 2px 10px; margin-left: 8px;">
                {{ scope.row.shifouyouke || '-' }}
              </el-tag>
            </template>
          </el-table-column>
          <!-- 操作列 -->
          <el-table-column
              label="操作"
              width="180"
              align="center"
              header-align="center"
              fixed="right"
          >
            <template slot-scope="scope">
              <div style="display: flex; gap: 6px; justifyContent: center; alignItems: center; flexWrap: wrap;">
                <!-- 详情按钮 -->
                <el-button
                    type="primary"
                    size="mini"
                    @click="addOrUpdateHandler(scope.row.id, 'info')"
                    style="borderRadius: 4px; padding: 5px 12px; height: 30px; lineHeight: 20px; transition: all 0.2s; borderColor: #409EFF; background: #409EFF; color: #fff; border: none;"
                >详情</el-button>
                <!-- 修改按钮 -->
                <el-button
                    type="warning"
                    size="mini"
                    @click="editHandler(scope.row.id)"
                    style="borderRadius: 4px; padding: 5px 12px; height: 30px; lineHeight: 20px; transition: all 0.2s; borderColor: #E6A23C; background: #E6A23C; color: #fff; border: none;"
                >修改</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination-container" :style='{"marginTop":"20px","paddingTop":"20px","borderTop":"1px solid #eee"}'>
          <el-pagination
              @size-change="sizeChangeHandle"
              @current-change="currentChangeHandle"
              :current-page="pageIndex"
              background
              :page-sizes="[10, 20, 30, 50]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalPage"
              :hide-on-single-page="false"
              :style="{
              width: '100%',
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
    </div>

    <!-- 详情对话框 - 居中模态框 -->
    <el-dialog
        title="自习室详情"
        :visible.sync="detailDialogVisible"
        width="50%"
        :close-on-click-modal="false"
        center
    >
      <div v-if="currentDetailData" style="padding: 20px;">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="名称">
            <span style="font-weight: 600; color: #333;">{{ currentDetailData.mingcheng || "-" }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="规模">
            <el-tag :type="currentDetailData.guimo === '大型' ? 'danger' : currentDetailData.guimo === '中型' ? 'warning' : 'success'" style="margin-left: 8px;">
              {{ currentDetailData.guimo || "-" }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="座位">
            <span style="color: #409EFF; font-weight: 600;">{{ currentDetailData.zuowei || "0" }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="位置">
            {{ currentDetailData.weizhi || "-" }}
          </el-descriptions-item>
          <el-descriptions-item label="可约时间">
            {{ currentDetailData.keyueshijian || "-" }}
          </el-descriptions-item>
          <el-descriptions-item label="是否有课">
            <el-tag :type="currentDetailData.shifouyouke === '是' ? 'danger' : 'success'" style="margin-left: 8px;">
              {{ currentDetailData.shifouyouke || "-" }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="图片" :span="2">
            <div v-if="currentDetailData.tupian" style="margin-top: 10px;">
              <img
                  v-if="currentDetailData.tupian.substring(0, 4) == 'http'"
                  :src="currentDetailData.tupian.split(',')[0]"
                  style="width: 150px; height: 100px; objectFit: cover; borderRadius: 4px; border: 1px solid #f0f0f0;"
              />
              <img
                  v-else
                  :src="$base.url + currentDetailData.tupian.split(',')[0]"
                  style="width: 150px; height: 100px; objectFit: cover; borderRadius: 4px; border: 1px solid #f0f0f0;"
              />
            </div>
            <div v-else style="color: #999; font-size: 12px;">无图片</div>
          </el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">
            {{ currentDetailData.beizhu || "无备注" }}
          </el-descriptions-item>
          <el-descriptions-item v-if="currentDetailData.addtime" label="创建时间" :span="2">
            {{ currentDetailData.addtime }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="detailDialogVisible = false"
          style="padding: 8px 20px; font-size: 14px; height: 36px;"
        >关 闭</el-button>
      </span>
    </el-dialog>

    <!-- 修改对话框 - 居中模态框 -->
    <el-dialog
        title="修改自习室信息"
        :visible.sync="editDialogVisible"
        width="50%"
        :close-on-click-modal="false"
        center
    >
      <div v-if="currentEditData" style="padding: 20px;">
        <el-form
          ref="editForm"
          :model="currentEditData"
          :rules="editRules"
          label-width="100px"
          label-position="left"
        >
          <el-form-item label="名称" prop="mingcheng">
            <el-input
              v-model="currentEditData.mingcheng"
              placeholder="请输入名称"
            ></el-input>
          </el-form-item>

          <el-form-item label="规模" prop="guimo">
            <el-select v-model="currentEditData.guimo" placeholder="请选择规模" style="width: 100%; padding-left: 8px;">
              <el-option label="大型" value="大型" style="padding-left: 8px;"></el-option>
              <el-option label="中型" value="中型" style="padding-left: 8px;"></el-option>
              <el-option label="小型" value="小型" style="padding-left: 8px;"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="座位" prop="zuowei">
            <el-input
              v-model="currentEditData.zuowei"
              placeholder="请输入座位数量"
            ></el-input>
          </el-form-item>

          <el-form-item label="位置" prop="weizhi">
            <el-input
              v-model="currentEditData.weizhi"
              placeholder="请输入位置"
            ></el-input>
          </el-form-item>

          <el-form-item label="可约时间" prop="keyueshijian">
            <el-input
              v-model="currentEditData.keyueshijian"
              placeholder="请输入可约时间"
            ></el-input>
          </el-form-item>

          <el-form-item label="是否有课" prop="shifouyouke">
            <el-select v-model="currentEditData.shifouyouke" placeholder="请选择是否有课" style="width: 100%; padding-left: 8px;">
              <el-option label="是" value="是" style="padding-left: 8px;"></el-option>
              <el-option label="否" value="否" style="padding-left: 8px;"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="备注" prop="beizhu">
            <el-input
              type="textarea"
              v-model="currentEditData.beizhu"
              placeholder="请输入备注"
              :rows="3"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="editDialogVisible = false"
          style="padding: 8px 20px; font-size: 14px; height: 36px; margin-right: 10px;"
        >取 消</el-button>
        <el-button
          type="primary"
          @click="saveEdit"
          style="padding: 8px 20px; font-size: 14px; height: 36px;"
        >保 存</el-button>
      </span>
    </el-dialog>

    <!-- 添加/修改页面 -->
    <add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>

    <yuyuexinxi-cross-add-or-update
        v-if="yuyuexinxiCrossAddOrUpdateFlag"
        :parent="this"
        ref="yuyuexinxiCrossaddOrUpdate"
    ></yuyuexinxi-cross-add-or-update>
  </div>
</template>

<script>
import axios from "axios";
import AddOrUpdate from "./add-or-update";
import yuyuexinxiCrossAddOrUpdate from "../yuyuexinxi/add-or-update";
export default {
  data() {
    return {
      searchForm: {
        mingcheng: "",
        guimo: "",
        weizhi: "",
        shifouyouke: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      sfshVisiable: false,
      shForm: {},
      chartVisiable: false,
      chartVisiable1: false,
      chartVisiable2: false,
      chartVisiable3: false,
      chartVisiable4: false,
      chartVisiable5: false,
      addOrUpdateFlag: false,
      yuyuexinxiCrossAddOrUpdateFlag: false,
      guimoOptions: [],
      shifouyoukeOptions: [],

      // 新增：详情模态框相关数据
      detailDialogVisible: false, // 控制详情模态框显示/隐藏
      currentDetailData: null, // 当前查看的详情数据

      // 新增：修改模态框相关数据
      editDialogVisible: false, // 控制修改模态框显示/隐藏
      currentEditData: null, // 当前修改的数据
      editForm: null, // 表单引用

      // 表单验证规则
      editRules: {
        mingcheng: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        guimo: [
          { required: true, message: '请选择规模', trigger: 'change' }
        ],
        zuowei: [
          { required: true, message: '请输入座位数量', trigger: 'blur' },
          { pattern: /^[0-9]+$/, message: '座位数量必须为数字', trigger: 'blur' }
        ],
        weizhi: [
          { required: true, message: '请输入位置', trigger: 'blur' }
        ],
        shifouyouke: [
          { required: true, message: '请选择是否有课', trigger: 'change' }
        ]
      }
    };
  },
  created() {
    this.init();
    this.getDataList();
  },
  mounted() {},
  filters: {
    htmlfilter: function (val) {
      return val.replace(/<[^>]*>/g).replace(/undefined/g, "");
    },
  },
  components: {
    AddOrUpdate,
    yuyuexinxiCrossAddOrUpdate,
  },
  methods: {
    init() {
      this.guimoOptions = "大型,中型,小型".split(",");
      this.shifouyoukeOptions = "是,否".split(",");
    },

    guimoChange() {
      // 规模选择变化处理
    },

    shifouyoukeChange() {
      // 是否有课选择变化处理
    },

    search() {
      this.pageIndex = 1;
      this.getDataList();
    },

    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: "id",
        order: "desc",
      };
      if (
        this.searchForm.mingcheng != "" &&
        this.searchForm.mingcheng != undefined
      ) {
        params["mingcheng"] = "%" + this.searchForm.mingcheng + "%";
      }
      if (this.searchForm.guimo != "" && this.searchForm.guimo != undefined) {
        params["guimo"] = this.searchForm.guimo;
      }
      if (this.searchForm.weizhi != "" && this.searchForm.weizhi != undefined) {
        params["weizhi"] = "%" + this.searchForm.weizhi + "%";
      }
      if (
        this.searchForm.shifouyouke != "" &&
        this.searchForm.shifouyouke != undefined
      ) {
        params["shifouyouke"] = this.searchForm.shifouyouke;
      }
      this.$http({
        url: "zixishi/page",
        method: "get",
        params: params,
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.total;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },
    // 添加/修改
    addOrUpdateHandler(id, type) {
      if (type === 'info') {
        // 查看详情，显示居中模态框
        this.showDetail(id);
      } else {
        // 修改，显示修改模态框
        this.editHandler(id);
      }
    },

    // 显示详情模态框
    showDetail(id) {
      this.dataListLoading = true;
      this.$http({
        url: `zixishi/info/${id}`,
        method: "get",
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.currentDetailData = data.data;
          this.detailDialogVisible = true;
        } else {
          this.$message.error(data.msg);
        }
        this.dataListLoading = false;
      }).catch((error) => {
        this.dataListLoading = false;
        console.error("获取详情失败:", error);
        this.$message.error("获取详情失败");
      });
    },

    // 显示修改模态框
    editHandler(id) {
      this.dataListLoading = true;
      this.$http({
        url: `zixishi/info/${id}`,
        method: "get",
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.currentEditData = { ...data.data };
          this.editDialogVisible = true;
        } else {
          this.$message.error(data.msg);
        }
        this.dataListLoading = false;
      }).catch((error) => {
        this.dataListLoading = false;
        console.error("获取修改数据失败:", error);
        this.$message.error("获取修改数据失败");
      });
    },

    // 保存修改
    saveEdit() {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确定要保存修改吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$http({
              url: "zixishi/update",
              method: "post",
              data: this.currentEditData
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '修改成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.editDialogVisible = false;
                    this.getDataList(); // 刷新列表
                  }
                });
              } else {
                this.$message.error(data.msg);
              }
            }).catch((error) => {
              console.error("保存失败:", error);
              this.$message.error("保存失败");
            });
          });
        } else {
          this.$message.error('请填写完整信息');
          return false;
        }
      });
    },

    yuyuexinxiCrossAddOrUpdateHandler(
      row,
      type,
      crossOptAudit,
      statusColumnName,
      tips,
      statusColumnValue
    ) {
      this.showFlag = false;
      this.addOrUpdateFlag = false;
      this.yuyuexinxiCrossAddOrUpdateFlag = true;
      this.$storage.set("crossObj", row);
      this.$storage.set("crossTable", "zixishi");
      this.$storage.set("statusColumnName", statusColumnName);
      this.$storage.set("statusColumnValue", statusColumnValue);
      this.$storage.set("tips", tips);
      if (statusColumnName != "" && !statusColumnName.startsWith("[")) {
        var obj = this.$storage.getObj("crossObj");
        for (var o in obj) {
          if (o == statusColumnName && obj[o] == statusColumnValue) {
            this.$message({
              message: tips,
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              },
            });
            this.showFlag = true;
            this.yuyuexinxiCrossAddOrUpdateFlag = false;
            return;
          }
        }
      }
      this.$nextTick(() => {
        this.$refs.yuyuexinxiCrossaddOrUpdate.init(row.id, type);
      });
    },
  }
};
</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

.list-preview {
  border-radius: 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.search-form-container {
  .center-form-pv {
    .el-input, .el-select {
      width: 100%;
    }
    .el-input__inner {
      border: 1px solid #dcdfe6;
      border-radius: 4px;
      padding: 0 12px;
      outline: none;
      color: #606266;
      font-size: 14px;
      height: 40px;
      line-height: 40px;
      transition: border-color 0.3s;

      &:hover {
        border-color: #c0c4cc;
      }

      &:focus {
        border-color: #409EFF;
        box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.1);
      }
    }

    .el-select .el-input__inner {
      padding: 0 30px 0 12px;
    }

    .el-button {
      &:hover {
        background: #66b1ff !important;
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
      }
    }
  }
}

.custom-table {
  .el-table__header-wrapper {
    th {
      background: #f8f9fa !important;
      color: #495057 !important;
      font-weight: 600 !important;
      font-size: 14px !important;
      border-bottom: 2px solid #e9ecef !important;
      padding: 12px 0 !important;
    }

    .cell {
      font-weight: 600;
    }
  }

  .el-table__body-wrapper {
    .el-table__row {
      transition: all 0.3s ease;

      &:hover {
        background: #f5f7fa !important;
      }

      td {
        border-color: #e9ecef !important;
        padding: 10px 0 !important;
        font-size: 14px !important;
      }
    }
  }

  .index-column .index-badge {
    display: inline-block;
    width: 24px;
    height: 24px;
    line-height: 24px;
    border-radius: 50%;
    background: #409EFF;
    color: white;
    font-weight: 500;
    font-size: 12px;
  }

  /* 规模/是否有课标签样式 */
  .el-tag {
    margin-left: 8px !important;
  }
}

.pagination-container {
  .el-pagination {
    .el-pagination__total {
      margin: 0 10px 0 0;
      color: #666;
      font-weight: 400;
      font-size: 14px;
    }

    .btn-prev,
    .btn-next,
    .number {
      border: 1px solid #d8dce5;
      border-radius: 4px;
      font-size: 14px;
      min-width: 32px;
      height: 32px;
      line-height: 30px;
    }

    .number.active {
      background: #409EFF;
      border-color: #409EFF;
      color: #fff;
    }

    .number:hover:not(.active) {
      border-color: #409EFF;
      color: #409EFF;
    }

    .el-pagination__jump {
      font-size: 14px;
    }

    .el-input__inner {
      height: 32px;
      line-height: 32px;
    }
  }
}

/* 对话框样式优化 */
.el-dialog {
  .el-dialog__header {
    background: #f8f9fa;
    border-bottom: 1px solid #e8e8e8;
    padding: 15px 20px;

    .el-dialog__title {
      font-weight: 600;
      color: #333;
    }
  }

  .el-dialog__body {
    padding: 20px;
  }

  .el-dialog__footer {
    border-top: 1px solid #e8e8e8;
    padding: 15px 20px;
    text-align: center;

    .el-button {
      padding: 8px 20px !important;
      font-size: 14px !important;
      height: 36px !important;
      min-width: 80px;

      &:hover {
        transform: translateY(-1px);
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
      }
    }
  }
}

/* 表单样式优化 */
.el-form {
  .el-form-item {
    margin-bottom: 20px;

    .el-form-item__label {
      color: #333;
      font-weight: 500;
    }

    .el-select {
      .el-input__inner {
        padding-left: 8px;
      }
    }
  }
}

/* 选择框选项样式 */
.el-select-dropdown {
  .el-select-dropdown__item {
    padding-left: 8px !important;
  }
}

/* 按钮悬停效果 */
.el-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

/* 小屏幕适配 */
@media (max-width: 768px) {
  .search-form-container {
    padding: 15px !important;

    .el-row {
      gap: 15px !important;
    }

    > div {
      min-width: 100% !important;
      flex: 1 1 100% !important;
    }
  }

  .el-table-column--label {
    min-width: 80px !important;
  }

  .el-table-column__content {
    font-size: 12px;
  }

  .el-button {
    font-size: 12px;
    padding: 4px 8px !important;
    height: 26px !important;
    line-height: 18px !important;
  }

  .custom-table {
    .index-column .index-badge {
      width: 20px;
      height: 20px;
      line-height: 20px;
      font-size: 10px;
    }

    .el-tag {
      padding: 1px 6px !important;
      font-size: 11px;
    }
  }

  /* 对话框在小屏幕上的适配 */
  .el-dialog {
    width: 90% !important;
    max-width: 90% !important;

    .el-dialog__header {
      padding: 12px 15px;
    }

    .el-dialog__body {
      padding: 15px;
    }

    .el-dialog__footer {
      padding: 12px 15px;

      .el-button {
        padding: 6px 16px !important;
        font-size: 13px !important;
        height: 32px !important;
        min-width: 70px;
      }
    }
  }

  .el-descriptions {
    .el-descriptions-item__label {
      font-size: 12px;
    }

    .el-descriptions-item__content {
      font-size: 12px;
    }
  }
}
</style>