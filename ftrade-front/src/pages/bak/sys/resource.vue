<template>
  <main>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>资源管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top:10px">
      <el-form :inline="true">
        <div class="queryForm">
          <el-form-item>
            <el-input v-model="params.query.resourceName" placeholder="资源名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click.native.prevent="fetchList">
              <i class="fa fa-search" aria-hidden="true"></i>
              查询
            </el-button>
          </el-form-item>
        </div>
        <div class="addOperate">
          <el-button  @click.native.prevent="addResource">
            <i class="fa fa-plus" aria-hidden="true"></i>
            添加资源
          </el-button>
        </div>
      </el-form>
    </div>
    <el-table :data="resourceList" stripe style="width: 100%" border>
      <el-table-column property="id" label="ID" ></el-table-column>
      <el-table-column property="resourceName" label="资源名称" ></el-table-column>
      <el-table-column property="resourceKey" label="资源ID" ></el-table-column>
      <el-table-column property="createTime" label="创建时间" :formatter="formatDate"></el-table-column>
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
      <el-form ref="form" :model="resourceInfo" label-width="80px">
        <el-form-item label="资源名称" :label-width="formLabelWidth">
          <el-input v-model="resourceInfo.resourceName" ></el-input>
        </el-form-item>
        <el-form-item label="资源ID" :label-width="formLabelWidth">
          <el-input v-model="resourceInfo.resourceKey" ></el-input>
        </el-form-item>
        <el-form-item label="父级资源" :label-width="formLabelWidth">
          <el-select v-model="resourceInfo.parentId" placeholder="请选择父级资源">
            <el-option  v-for="item in allResource" v-if="resourceInfo.id !== item.value"  :label="item.label" :value="item.value" :key="item.value"></el-option>
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
          resourceName: ''
        }
      },
      addFormVisible: false,
      formLabelWidth: '120px',
      resourceInfo: {
        id: '',
        resourceName: '',
        resourceKey: '',
        parentId: ''
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
      resourceList: [],
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
      return '添加资源'
    },
    leftSpan () {
      return this.params.query.accountType === 1 ? 10 : 13
    }
  },
  mounted () {
    this.fetchList()
    this.fetchResources()
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
    addResource () {
      this.resourceInfo = {
        id: '',
        resourceName: '',
        resourceKey: '',
        parentId: ''
      }
      this.addFormVisible = true
    },
    editUser (row) {
      this.addFormVisible = true
      this.resourceInfo = row
    },
    save () {
      this.$http.post('admin/sys/resource/save', this.resourceInfo).then((resp) => {
        this.$message('保存成功')
        this.fetchList()
        this.fetchResources()
        this.addFormVisible = false
      })
    },
    fetchList () {
      this.$http.get('admin/sys/resource/list', { params: this.queryCondition }).then((resp) => {
        this.resourceList = resp.data.data.listData
        this.params.pagination.totalCount = resp.data.data.totalCount
      }, (resp) => {
        console.log(resp)
      })
    },
    fetchResources () {
      this.$http.get(`admin/sys/resource/select`).then((resp) => {
        if (resp.data.result === 'success') {
          this.allResource = []
          this.allResource.push({
            value: 0,
            label: '无'
          })
          for (let i in resp.data.data) {
            this.allResource.push(resp.data.data[i])
          }
        }
      }, (resp) => {
        console.log('初始化 error', resp)
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
