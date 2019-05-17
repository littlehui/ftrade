<template>
  <main>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top:10px">
      <el-form :inline="true">
        <div class="queryForm">
          <el-form-item>
            <el-input v-model="params.query.userCode" placeholder="账号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="params.query.userStatus" placeholder="状态">
              <el-option  v-for="item in statusList" :label="item.label" :value="item.value" :key="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button @click.native.prevent="fetchList">
              <i class="fa fa-search" aria-hidden="true"></i>
              查询
            </el-button>
          </el-form-item>
        </div>
        <div class="addOperate">
          <el-button  @click.native.prevent="addUser">
            <i class="fa fa-plus" aria-hidden="true"></i>
            添加用户
          </el-button>
        </div>
      </el-form>
    </div>
    <el-table :data="adminUserList" stripe style="width: 100%" border>
      <el-table-column property="id" label="ID" ></el-table-column>
      <el-table-column property="userCode" label="账号" ></el-table-column>
      <el-table-column property="nickname" label="昵称" ></el-table-column>
      <el-table-column property="roleName" label="角色名称" ></el-table-column>
      <el-table-column property="createTime" label="创建时间" :formatter="formatDate"></el-table-column>
      <el-table-column property="userStatus" label="状态"  :formatter="formatStatus">
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" @click.native.prevent="editUser(scope.row)">编辑</el-button>
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
      <el-form ref="form" :model="userInfo" label-width="80px">
        <el-form-item label="工号" :label-width="formLabelWidth">
          <el-input v-model="userInfo.userCode" :disabled="userInfo.id !== ''"></el-input>
        </el-form-item>
        <el-form-item label="昵称" :label-width="formLabelWidth">
          <el-input v-model="userInfo.nickname" ></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="userInfo.userStatus" placeholder="状态">
            <el-option  v-for="item in statusList" v-if="item.value !== ''" :label="item.label" :value="item.value" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户角色" :label-width="formLabelWidth">
          <el-select v-model="userInfo.roleId" placeholder="请选择用户角色">
            <el-option  v-for="item in roleList"  :label="item.label" :value="item.value" :key="item.value"></el-option>
          </el-select>
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
          totalCount: 100
        },
        query: {
          userCode: '',
          userStatus: ''
        }
      },
      addFormVisible: false,
      formLabelWidth: '120px',
      userInfo: {
        id: '',
        userCode: '',
        nickname: '',
        userStatus: 1,
        roleId: ''
      },
      adminUserList: [],
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
      roleList: [],
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
      return '添加管理员'
    },
    leftSpan () {
      return this.params.query.accountType === 1 ? 10 : 13
    }
  },
  mounted () {
    this.fetchList()
    this.initRoles()
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
    initRoles () {
      this.$http.get(`admin/sys/role/select`).then((resp) => {
        if (resp.data.result === 'success') {
          this.roleList = resp.data.data
        }
      }, (resp) => {
        console.log('初始化角色出错', resp)
      })
    },
    addUser () {
      console.log('add user')
      this.userInfo = {
        id: '',
        userCode: '',
        nickname: '',
        userStatus: 1,
        roleId: ''
      }
      this.addFormVisible = true
    },
    editUser (row) {
      this.addFormVisible = true
      this.userInfo = row
    },
    save () {
      if (this.userInfo.id !== '') {
        this.$http.post('admin/sys/user/edit', this.userInfo).then((resp) => {
          this.$message('修改成功')
          this.fetchList()
          this.addFormVisible = false
        })
        return
      }
      this.$http.post('admin/sys/user/add', this.userInfo).then((resp) => {
        this.$message('添加成功')
        this.fetchList()
        this.addFormVisible = false
      })
    },
    fetchList () {
      this.$http.get('admin/sys/user/list', { params: this.queryCondition }).then((resp) => {
        this.adminUserList = resp.data.data.listData
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
