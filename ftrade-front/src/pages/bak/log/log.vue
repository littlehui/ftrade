<template>
  <main>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日志管理</el-breadcrumb-item>
      <el-breadcrumb-item>日志信息</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top:10px">
      <el-form :inline="true">
        <div class="queryForm">
          <el-form-item>
            <el-input v-model="params.query.operator" placeholder="操作人"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="params.query.ip" placeholder="ip"></el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="params.query.resultType" placeholder="状态">
              <el-option v-for="item in statusList" :label="item.label" :value="item.value"
                         :key="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <div class="block">
              <el-date-picker
                v-model="params.query.queryDate"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd HH:mm:ss"
                :default-time="['00:00:00', '23:59:59']">
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button @click.native.prevent="resetPageNoQuery">
              <i class="fa fa-search" aria-hidden="true"></i>
              查询
            </el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
    <el-table :data="logList" stripe style="width: 100%" border>
      <el-table-column property="operator" label="操作人"></el-table-column>
      <el-table-column property="ip" label="ip"></el-table-column>
      <el-table-column property="classMethod" label="请求方法"></el-table-column>
      <el-table-column property="createTime" label="访问时间" :formatter="formatDate"></el-table-column>
      <el-table-column property="operateTime" label="请求时长"></el-table-column>
      <el-table-column property="obj" label="请求参数"></el-table-column>
      <el-table-column property="resultType" label="请求结果" :formatter="formatStatus"></el-table-column>
      <el-table-column property="remark" label="错误信息"></el-table-column>
    </el-table>
    <div class="page-block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
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
  import moment from 'moment/moment'

  export default {
    data () {
      return {
        params: {
          pagination: {
            pageNo: 1,
            pageSize: 20,
            totalCount: 100
          },
          query: {
            operator: '',
            ip: '',
            resultType: '',
            queryDate: ''
          }
        },
        logList: [],
        statusList: [{
          value: '',
          label: '全部'
        },
          {
            value: '1',
            label: '正常'
          }, {
            value: '2',
            label: '失败'
          }, {
            value: '3',
            label: '错误'
          }]
      }
    },
    computed: {
      queryCondition () {
        let condition = _.assign({}, this.params.pagination)
        condition = _.assign(condition, this.params.query)
        return condition
      }
    },
    mounted () {
      this.fetchList()
    },
    attached () {
    },
    methods: {
      formatDate (row, column) {
        if (row[column.property] === null) {
          return '-'
        }
        return moment(row[column.property]).format('YYYY-MM-DD HH:mm')
      },
      formatStatus (row, col) {
        return row[col.property] === 1 ? '正常' : (row[col.property] === 2 ? '失败' : '错误')
      },
      handleSizeChange (val) {
        this.params.pagination.pageSize = val
        this.fetchList()
      },
      handleCurrentChange (val) {
        this.params.pagination.pageNo = val
        this.fetchList()
      },
      fetchList () {
        var param = this.queryCondition
        var queryDate = param.queryDate
        if (queryDate !== '' && queryDate !== null && queryDate instanceof Array) {
          param.queryDate = queryDate.join(',')
        }
        this.$http.get('admin/log/list', {params: param}).then((resp) => {
          this.logList = resp.data.data.listData
          this.params.pagination.totalCount = resp.data.data.totalCount
        }, (resp) => {
          console.log(resp)
        })
      },
      resetPageNoQuery () {
        this.params.pagination.pageNo = 1
        this.fetchList()
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
