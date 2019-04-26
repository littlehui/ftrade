<template>
  <main>

    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>结果反馈</el-breadcrumb-item>
      <el-breadcrumb-item>反馈列表</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top:10px">
      <el-form :inline="true">
        <div class="queryForm">
          <el-form-item>
            <el-input v-model="params.query.appName" placeholder="应用名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="params.query.appId" placeholder="应用ID"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click.native.prevent="fetchList">
              <i class="fa fa-search" aria-hidden="true"></i>
              查询
            </el-button>
          </el-form-item>
        </div>
      <!--  <div class="addOperate">
          <el-button  @click.native.prevent="addApp">
            <i class="fa fa-plus" aria-hidden="true"></i>
            添加应用
          </el-button>
        </div>-->
      </el-form>
    </div>

    <el-table :data="resourceList" stripe style="width: 100%" border>
      <el-table-column property="id" label="ID" ></el-table-column>
      <el-table-column property="appName" label="应用名称" ></el-table-column>

      <el-table-column property="appCode" label="app编码" ></el-table-column>
      <el-table-column property="recommendCode" label="推荐编码" ></el-table-column>
      <el-table-column property="visitCount" label="访问次数" ></el-table-column>

      <el-table-column property="updateTime" label="更新时间" :formatter="formatDate"></el-table-column>
      <el-table-column property="createTime" label="创建时间" :formatter="formatDate"></el-table-column>
    </el-table>

    <div class="page-block">
      <el-pagination  @size-change="handleSizeChange" @current-change="handleCurrentChange"
                      :current-page="params.pagination.pageNo"
                      :page-sizes="[20, 50, 100, 200]"
                      :page-size="params.pagination.pageSize"
                      layout="total, sizes, prev, pager, next, jumper"
                      :total="params.pagination.totalCount"></el-pagination>
    </div>
  </main>
</template>

<script>
  import _ from 'lodash'
  import moment from 'moment'

  export default {
    data () {
      return {
        params: {
          pagination: {
            pageNo: 1,
            pageSize: 20,
            totalCount: 2
          },
          query: {
            appName: '',
            appId: ''
          }
        },
        addFormVisible: false,
        formLabelWidth: '120px',

        resultInfo: {
          id: '',
          appName: '',
          appCode: '',
          recommendCode: '',
          visitCount: 34,
          updateTime: '',
          createTime: 0
        },
        statusList: [{
          value: '',
          label: '全部'
        },
          {
            value: 1,
            label: '可用'
          }, {
            value: 0,
            label: '禁用'
          }],
        allResource: [],
        resourceList: [
          {
            id: '1213',
            appName: '游戏交易平台',
            appCode: '1jfudh8e',
            recommendCode: 'GAMETRADE_RMD_1213',
            visitCount: 0,
            createTime: 1488792177000,
            updateTime: 1488794177000
          },
          {
            id: '3233',
            appName: '游戏交易平台',
            appCode: '1jfudh8e',
            recommendCode: 'GAMETRADE_RMD_1213_3233',
            visitCount: 1,
            createTime: 1488792177000,
            updateTime: 1488799177000
          }
        ],
        loading: false
      }
    },
    computed: {
      queryCondition () {
        let condition = _.assign({}, this.params.pagination)
        condition = _.assign(condition, this.params.query)
        return condition
      },
      dialogTitle () {
        return '添加规则'
      },
      leftSpan () {
        return this.params.query.accountType === 1 ? 10 : 13
      }
    },
    mounted () {
      this.fetchList()
    },
    attached () {},
    methods: {
      formatDate (row, column) {
        return moment(row[column.property]).format('YYYY-MM-DD HH:mm')
      },
      formatStatus (row, col) {
        return row[col.property] === 1 ? '可用' : '禁用'
      },
      handleSizeChange (val) {
        this.params.pagination.pageSize = val
        this.fetchList()
      },
      handleCurrentChange (val) {
        this.params.pagination.pageNo = val
        this.fetchList()
      },
      addApp () {
        this.appInfo = {
          id: '',
          appName: '',
          appId: '',
          appKey: '',
          description: '',
          appStatus: 1
        }
        this.addFormVisible = true
      },
      editApp (row) {
        this.addFormVisible = true
        this.appInfo = row
        this.roleInfo = row
      },
      save () {
        this.$http.post('admin/app/save', this.appInfo).then((resp) => {
          this.$message('保存成功')
          this.fetchList()
          this.addFormVisible = false
        })
      },
      fetchList () {
        this.$http.get('admin/app/roleList', { params: this.queryCondition }).then((resp) => {
          this.resourceList = resp.data.data.listData
          this.params.pagination.totalCount = resp.data.data.totalCount
        }, (resp) => {
          console.log(resp)
        })
      }
    },
    components: {}
  }
</script>

<style lang="css" scoped>
  .queryForm {
    float: left;
  }
  .addOperate {
    float: right;
  }
  .page-block {
    float: right;
  }
</style>
