<template>
  <main>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统接入</el-breadcrumb-item>
      <el-breadcrumb-item>冷启动规则</el-breadcrumb-item>
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
        <div class="addOperate">
          <el-button  @click.native.prevent="addApp">
            <i class="fa fa-plus" aria-hidden="true"></i>
            添加规则
          </el-button>
        </div>
      </el-form>
    </div>
    <el-table :data="resourceList" stripe style="width: 100%" border>
      <el-table-column property="id" label="ID" ></el-table-column>
      <el-table-column property="appName" label="应用名称" ></el-table-column>

      <el-table-column property="roleName" label="规则名称" ></el-table-column>
      <el-table-column property="roleCode" label="规则编码" ></el-table-column>
      <el-table-column property="callBackUrl" label="回调地址" ></el-table-column>
      <el-table-column property="resultJson" label="固定返回值" ></el-table-column>

      <el-table-column property="description" label="描述" ></el-table-column>
      <el-table-column property="createTime" label="创建时间" :formatter="formatDate"></el-table-column>

      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" @click.native.prevent="editApp(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="page-block">
      <el-pagination  @size-change="handleSizeChange" @current-change="handleCurrentChange"
                      :current-page="params.pagination.pageNo"
                      :page-sizes="[20, 50, 100, 200]"
                      :page-size="params.pagination.pageSize"
                      layout="total, sizes, prev, pager, next, jumper"
                      :total="params.pagination.totalCount"></el-pagination>
    </div>

    <el-dialog :title="dialogTitle" :visible.sync="addFormVisible">
      <el-form ref="form" :model="roleInfo" label-width="80px">
        <el-form-item label="应用名称" :label-width="formLabelWidth">
          <el-input v-model="roleInfo.appName" :disabled="roleInfo.id !== ''"></el-input>
        </el-form-item>
        <el-form-item label="appId" :label-width="formLabelWidth">
          <el-input v-model="roleInfo.appKey" :disabled="roleInfo.id !== ''"></el-input>
        </el-form-item>

        <el-form-item label="规则名称" :label-width="formLabelWidth">
          <el-input v-model="roleInfo.roleName"></el-input>
        </el-form-item>
        <el-form-item label="规则编码" :label-width="formLabelWidth">
          <el-input v-model="roleInfo.roleCode" :disabled="roleInfo.id !== ''"></el-input>
        </el-form-item>
        <el-form-item label="回调地址" :label-width="formLabelWidth">
          <el-input v-model="roleInfo.callBackUrl"></el-input>
        </el-form-item>
        <el-form-item label="固定返回值" :label-width="formLabelWidth">
          <el-input v-model="roleInfo.resultJson"></el-input>
        </el-form-item>

        </el-form-item>
        <el-form-item label="规则描述" :label-width="formLabelWidth">
          <el-input v-model="roleInfo.description" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false;">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
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
            totalCount: 1
          },
          query: {
            appName: '',
            appId: ''
          }
        },
        addFormVisible: false,
        formLabelWidth: '120px',
        appInfo: {
          id: '',
          appName: '',
          appId: '',
          appKey: '',
          description: '',
          appStatus: 1
        },
        roleInfo: {
          id: '',
          appName: '',
          appId: '',
          appKey: '',
          description: '',
          appStatus: 1,
          roleName:'',
          roleCode:'',
          resultJson:'',
          callBackUrl:''
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
            id: '11',
            appName: '游戏交易平台',
            appKey: '1jfudh8e',
            description: '',
            appStatus: 1,
            roleName:'返回默认热门商品',
            roleCode:'GAMETRADE_ROLE',
            resultJson:'',
            callBackUrl:'http://commdity.17173.com/commdity/list.do?recomd=true',
            createTime: 1488792177000
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
