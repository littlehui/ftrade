<template>
  <main>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top:10px">
      <el-form :inline="true">
        <div class="queryForm">
          <el-form-item>
            <el-input v-model="params.query.roleName" placeholder="角色名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="params.query.roleStatus" placeholder="状态">
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
            添加角色
          </el-button>
        </div>
      </el-form>
    </div>
    <el-table :data="roleList" stripe style="width: 100%" border>
      <el-table-column property="id" label="ID" ></el-table-column>
      <el-table-column property="roleName" label="角色名称" ></el-table-column>
      <el-table-column property="createTime" label="创建时间" :formatter="formatDate"></el-table-column>
      <el-table-column property="roleStatus" label="状态"  :formatter="formatStatus">
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" @click.native.prevent="editUser(scope.row)">编辑</el-button>
          <el-button type="text" @click.native.prevent="editRoleResource(scope.row)">分配权限</el-button>
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
        <el-form-item label="角色名称" :label-width="formLabelWidth">
          <el-input v-model="roleInfo.roleName" ></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="roleInfo.roleStatus" placeholder="状态">
            <el-option  v-for="item in statusList" v-if="item.value !== ''" :label="item.label" :value="item.value" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false;">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="权限分配" :visible.sync="authDialogVisible">
      <div v-for="item in resourceTree" :key="item.id" style="margin-top: 10px">
        <el-card class="box-card">
        <el-checkbox  v-model="item.selected" @change="handleCheckAll(item)">{{item.resourceName}}</el-checkbox>
        <div style="margin: 5px 10px;"></div>
        <span style="margin-left: 20px;"></span> <el-checkbox @change="handleCheckItem(item, subItem)" v-for="subItem in item.subItems" v-model="subItem.selected" :label="subItem.resourceName" :key="subItem.id"></el-checkbox>
      </el-card>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="authDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleResource">确 定</el-button>
      </span>
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
          roleName: '',
          roleStatus: ''
        }
      },
      addFormVisible: false,
      formLabelWidth: '120px',
      roleInfo: {
        id: '',
        roleName: '',
        roleStatus: 1
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
      roleList: [],
      loading: false,
      resourceTree: [],
      authDialogVisible: false,
      currAuthRoleId: ''
    }
  },
  computed: {
    queryCondition () {
      let condition = _.assign({}, this.params.pagination)
      condition = _.assign(condition, this.params.query)
      return condition
    },
    dialogTitle () {
      return '添加角色'
    },
    leftSpan () {
      return this.params.query.accountType === 1 ? 10 : 13
    },
    checkedIds () {
      let checkedIds = []
      for (let i in this.resourceTree) {
        if (this.resourceTree[i].selected) {
          checkedIds.push(this.resourceTree[i].id)
        }
        this.addSubIds(checkedIds, this.resourceTree[i].subItems)
      }
      return checkedIds
    }
  },
  mounted () {
    this.fetchList()
  },
  attached () {},
  methods: {
    saveRoleResource () {
      console.log(this.checkedIds)
      this.$http.post('admin/sys/role/saveRoleResource', {
        roleId: this.currAuthRoleId,
        resourceIds: this.checkedIds
      }).then((resp) => {
        this.$message('保存成功')
        this.currAuthRoleId = ''
        this.authDialogVisible = false
      })
    },
    addSubIds (checkedIds, subItems) {
      for (let i in subItems) {
        if (subItems[i].selected) {
          checkedIds.push(subItems[i].id)
        }
        this.addSubIds(checkedIds, subItems[i].subItems)
      }
    },
    handleCheckItem (item, subItem) {
      if (subItem.selected) {
        item.selected = true
      }
    },
    handleCheckAll (item) {
      let selected = item.selected
      for (let i in item.subItems) {
        item.subItems[i].selected = selected
      }
    },
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
    addUser () {
      console.log('add user')
      this.roleInfo = {
        id: '',
        roleName: '',
        roleStatus: 1
      }
      this.addFormVisible = true
    },
    editUser (row) {
      this.addFormVisible = true
      this.roleInfo = row
    },
    editRoleResource (row) {
      this.currAuthRoleId = row.id
      this.fetchResourceTree(row.id)
    },
    save () {
      this.$http.post('admin/sys/role/save', this.roleInfo).then((resp) => {
        this.$message('保存成功')
        this.fetchList()
        this.addFormVisible = false
      })
    },
    fetchList () {
      this.$http.get('admin/sys/role/list', { params: this.queryCondition }).then((resp) => {
        this.roleList = resp.data.data.listData
        this.params.pagination.totalCount = resp.data.data.totalCount
      }, (resp) => {
        console.log(resp)
      })
    },
    fetchResourceTree (roleId) {
      this.$http.get(`admin/sys/role/tree?roleId=${roleId}`).then((resp) => {
        this.resourceTree = resp.data.data
        this.authDialogVisible = true
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
