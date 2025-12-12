<el-table
        class="tables"
        :stripe="false"
        :style="{
          padding: '0',
          boxShadow: '0px 4px 10px 0px rgba(0,0,0,0.3020)',
          borderColor: '#eee',
          borderRadius: '30px',
          borderWidth: '1px 0 0 1px',
          background: '#fff',
          width: '100%',
          borderStyle: 'solid',
          height: 'fit-content',
        }"
        v-if="isAuth('qiantuixinxi', '查看')"
        :data="dataList"
        v-loading="dataListLoading"
        @selection-change="selectionChangeHandler"
      >
        <el-table-column
          :resizable="true"
          type="selection"
          align="center"
          width="50"
        ></el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          label="索引"
          type="index"
          width="50"
        />
        <el-table-column
          :resizable="true"
          :sortable="false"
          prop="mingcheng"
          label="名称"
        >
          <template slot-scope="scope">
            {{ scope.row.mingcheng }}
          </template>
        </el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          prop="qiantuishijian"
          label="签退时间"
        >
          <template slot-scope="scope">
            {{ scope.row.qiantuishijian }}
          </template>
        </el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          prop="zixishichang"
          label="自习时长"
        >
          <template slot-scope="scope">
            {{ formatDuration(scope.row.zixishichang) }}
          </template>
        </el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          prop="qiantuibeizhu"
          label="签退备注"
        >
          <template slot-scope="scope">
            {{ scope.row.qiantuibeizhu }}
          </template>
        </el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          prop="xuehao"
          label="学号"
        >
          <template slot-scope="scope">
            {{ scope.row.xuehao }}
          </template>
        </el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          prop="shouji"
          label="手机"
        >
          <template slot-scope="scope">
            {{ scope.row.shouji }}
          </template>
        </el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          prop="banji"
          label="班级"
        >
          <template slot-scope="scope">
            {{ scope.row.banji }}
          </template>
        </el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          prop="shhf"
          label="回复"
        ></el-table-column>
        <el-table-column
          :resizable="true"
          :sortable="false"
          v-if="isAuth('qiantuixinxi', '审核')"
          prop="sfsh"
          label="回复"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="shDialog(scope.row)"
              >回复</el-button
            >
          </template>
        </el-table-column>
        <el-table-column width="300" label="操作">
          <template slot-scope="scope">
            <el-button
              :style="{
                border: '0',
                cursor: 'pointer',
                padding: '0 24px',
                margin: '0 10px 6px 0',
                outline: 'none',
                color: '#fff',
                borderRadius: '30px',
                background: '#93C7B3',
                width: 'auto',
                fontSize: '14px',
                height: '32px',
              }"
              v-if="isAuth('qiantuixinxi', '查看')"
              type="success"
              size="mini"
              @click="addOrUpdateHandler(scope.row.id, 'info')"
              >详情</el-button
            >
            <el-button
              :style="{
                border: '0',
                cursor: 'pointer',
                padding: '0 24px',
                margin: '0 10px 6px 0',
                outline: 'none',
                color: '#fff',
                borderRadius: '30px',
                background: '#93C7B3',
                width: 'auto',
                fontSize: '14px',
                height: '32px',
              }"
              v-if="isAuth('qiantuixinxi', '修改')"
              type="primary"
              size="mini"
              @click="addOrUpdateHandler(scope.row.id)"
              >修改</el-button
            >

            <el-button
              :style="{
                border: '0',
                cursor: 'pointer',
                padding: '0 24px',
                margin: '0 10px 6px 0',
                outline: 'none',
                color: '#fff',
                borderRadius: '30px',
                background: '#93C7B3',
                width: 'auto',
                fontSize: '14px',
                height: '32px',
              }"
              v-if="isAuth('qiantuixinxi', '删除')"
              type="danger"
              size="mini"
              @click="deleteHandler(scope.row.id)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>