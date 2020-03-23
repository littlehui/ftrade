<template>
  <main>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>文件管理</el-breadcrumb-item>
      <el-breadcrumb-item>app信息</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top:10px">
      <el-form :inline="true">
        <div class="queryForm">
          <el-form-item>
            <el-input v-model="params.query.appName" placeholder="appName"></el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="params.query.deleted" placeholder="状态">
              <el-option v-for="item in deleteList" :label="item.label" :value="item.value"
                         :key="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button @click.native.prevent="resetPageNoQuery">
              <i class="fa fa-search" aria-hidden="true"></i>
              查询
            </el-button>
          </el-form-item>
        </div>
        <div class="addOperate">
          <el-button v-if="this.userResourceKeyMap['file:manager:app:add']" @click.native.prevent="showVideoWin">
            <i class="fa fa-plus" aria-hidden="true"></i>
            新建app
          </el-button>
        </div>
      </el-form>
    </div>
    <el-table :data="logList" stripe style="width: 100%" border>
      <el-table-column property="appId" label="appId"></el-table-column>
      <el-table-column property="appName" label="app名称"></el-table-column>
      <el-table-column property="bucket" label="bucket"></el-table-column>
      <el-table-column property="cdnDomain" label="cdn域名"></el-table-column>
      <el-table-column property="signNeed" label="是否需要cdn鉴权" :formatter="formatBoolean"></el-table-column>
      <el-table-column property="signKey" label="鉴权key"></el-table-column>
      <el-table-column property="region" label="区域"></el-table-column>
      <el-table-column property="createTime" label="创建时间" :formatter="formatDate"></el-table-column>
      <el-table-column property="deleted" label="状态" :formatter="formatDelete"></el-table-column>
      <el-table-column property="remark" label="备注"></el-table-column>
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" v-if="userResourceKeyMap['file:manager:app:uptede'] && !scope.row.deleted"
                     @click.native.prevent="showUptDialog(scope.row)">修改
          </el-button>
          <el-button type="text" v-if="userResourceKeyMap['file:manager:app:delete']  && !scope.row.deleted"
                     @click.native.prevent="delBucket(scope.row)">删除
          </el-button>
          <el-button type="text" v-if="userResourceKeyMap['file:manager:app:showKey']  && !scope.row.deleted"
                     @click.native.prevent="showKey(scope.row.appId)">查看key
          </el-button>
          <el-button type="text" v-if="userResourceKeyMap['file:manager:app:recovery']  && scope.row.deleted"
                     @click.native.prevent="recovery(scope.row.id)">恢复使用
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="page-block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="params.pagination.pageNo"
                     :page-sizes="[20, 50, 100, 200]"
                     :page-size="params.pagination.pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="params.pagination.totalCount"></el-pagination>
    </div>

    <!-- 增加App -->
    <el-dialog :title="add.dialogTitle" :visible.sync="add.bucketVisible">
      <el-form ref="form" :model="add.appInfo" class="demo-form-inline">
        <el-form-item label="app名称" :label-width="formLabelWidth">
          <el-input v-model="add.appInfo.appName"></el-input>
        </el-form-item>
        <el-form-item label="bucket" :label-width="formLabelWidth">
          <el-input v-model="add.appInfo.bucket"></el-input>
        </el-form-item>
        <el-form-item label="cdn域名" :label-width="formLabelWidth">
          <el-input v-model="add.appInfo.cdnDomain"></el-input>
        </el-form-item>
        <el-form-item label="需要cdn鉴权" :label-width="formLabelWidth">
          <template>
            <el-select v-model="add.appInfo.signNeed" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="鉴权key" :label-width="formLabelWidth">
          <el-input v-model="add.appInfo.signKey"></el-input>
        </el-form-item>
        <el-form-item label="密钥" :label-width="formLabelWidth">
          <el-input v-model="add.appInfo.key">
            <el-button slot="append" icon="el-icon-search" @click.native.prevent="getKey">自动生成</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="add.appInfo.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="add.bucketVisible = false;">关 闭</el-button>
        <el-button type="primary" @click.native.prevent="addBucket">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 修改app -->
    <el-dialog :title="upt.dialogTitle" :visible.sync="upt.bucketVisible">
      <el-form ref="form" :model="upt.appInfo" class="demo-form-inline">
        <input type="hidden" v-model="upt.appInfo.id"/>
        <el-form-item label="app名称" :label-width="formLabelWidth">
          <el-input v-model="upt.appInfo.appName"></el-input>
        </el-form-item>

        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="upt.appInfo.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="upt.bucketVisible = false;">关 闭</el-button>
        <el-button type="primary" @click.native.prevent="uptBucket">确 定</el-button>
      </div>
    </el-dialog>

    <!-- key 操作 -->
    <el-dialog :title="key.dialogTitle" :visible.sync="key.keyVisible">
      <el-form :inline="true" ref="form" class="demo-form-inline">
        <el-form-item label="key" :label-width="formLabelWidth">
          <el-input v-model="key.keyValue" :disabled="key.isEdit" style="width:500px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-if="key.isEdit" @click.native.prevent="key.isEdit = false">编 辑</el-button>
          <el-button type="primary" v-if="!key.isEdit" @click.native.prevent="uptSecret">提 交</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="key.keyVisible = false;">关 闭</el-button>
      </div>
    </el-dialog>
  </main>
</template>

<script>
  import _ from 'lodash'
  import moment from 'moment'
  import {mapGetters} from 'vuex'

  export default {
    data () {
      return {
        formLabelWidth: '120px',
        params: {
          pagination: {
            pageNo: 1,
            pageSize: 20,
            totalCount: 100
          },
          query: {
            appName: '',
            deleted: false
          }
        },
        logList: [],
        deleteList: [{
          value: false,
          label: '正常'
        }, {
          value: true,
          label: '删除'
        }],
        add: {
          dialogTitle: '增加app',
          bucketVisible: false,
          appInfo: {
            appName: '',
            bucket: '',
            cdnDomain: '',
            signNeed: false,
            signKey: '',
            key: '',
            remark: ''
          }
        },
        upt: {
          dialogTitle: '修改app',
          bucketVisible: false,
          appInfo: {
            id: '',
            appName: '',
            remark: ''
          }
        },
        key: {
          dialogTitle: 'key信息',
          keyVisible: false,
          keyValue: '',
          keyId: '',
          isEdit: false
        },
        options: [{
          value: false,
          label: '否'
        }, {
          value: true,
          label: '是'
        }]
      }
    },
    computed: {
      queryCondition () {
        let condition = _.assign({}, this.params.pagination)
        condition = _.assign(condition, this.params.query)
        return condition
      },
      uptCondition () {
        let condition = _.assign({}, this.upt.appInfo)
        condition = _.assign(condition, this.upt.appInfo)
        return condition
      },
      ...mapGetters([
        'isLogin',
        'nickname',
        'userResourceKeys',
        'roleCode',
        'userResourceKeyMap'
      ])
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
      formatDelete (row, column) {
        return row[column.property] === true ? '删除' : '正常'
      },
      formatBoolean (row, column) {
        return row[column.property] === true ? '是' : '否'
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
        this.$http.get('admin/app/list', {params: this.queryCondition}).then((resp) => {
          this.logList = resp.data.data.listData
          this.params.pagination.totalCount = resp.data.data.totalCount
        }, (resp) => {
          console.log(resp)
        })
      },
      resetPageNoQuery () {
        this.params.pagination.pageNo = 1
        this.fetchList()
      },
      delBucket (row) {
        this.$confirm('此操作将永久删除app的存储空间，app下的文件将无法获取, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http.post('admin/app/delBucket?id=' + row.id).then((resp) => {
            this.$message('删除成功')
            this.fetchList()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      recovery (id) {
        this.$confirm('你确定要重新使用该app吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http.post('admin/app/recoveryBucket?id=' + id).then((resp) => {
            this.$message('恢复成功')
            this.fetchList()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      showVideoWin () {
        this.add.appInfo.appName = ''
        this.add.appInfo.bucket = ''
        this.add.appInfo.key = ''
        this.add.appInfo.remark = ''
        this.add.appInfo.cdnDomain = ''
        this.add.appInfo.signNeed = false
        this.add.appInfo.signKey = ''
        this.add.bucketVisible = true
      },
      addBucket () {
        var bucket = this.add.appInfo.bucket
        if (!/^[a-z0-9]{1,20}$/.test(bucket)) {
          this.$message('bucket只能为小写和数字且少于20')
          return
        }
        this.$http.get('admin/app/save', {params: this.add.appInfo}).then((resp) => {
          this.$message('增加成功')
          this.fetchList()
          this.add.bucketVisible = false
        }, (resp) => {
          console.log(resp)
        })
      },
      getKey () {
        this.$http.post('admin/app/getRandomKey').then((resp) => {
          this.add.appInfo.key = resp.data.data
        })
      },
      showUptDialog (row) {
        this.upt.appInfo.id = row.id
        this.upt.appInfo.appName = row.appName
        this.upt.appInfo.remark = row.remark
        this.upt.bucketVisible = true
      },
      uptBucket () {
        this.$http.get('admin/app/uptBucket', {params: this.uptCondition}).then((resp) => {
          this.$message('修改成功')
          this.fetchList()
          this.upt.bucketVisible = false
        }, (resp) => {
          console.log(resp)
        })
      },
      showKey (appId) {
        this.key.keyId = appId
        this.$http.get('admin/app/getKey?appId=' + appId).then((resp) => {
          this.key.keyValue = resp.data.data
          this.key.isEdit = true
          this.key.keyVisible = true
        }, (resp) => {
          console.log(resp)
        })
      },
      uptSecret () {
        this.$http.get('admin/app/updateKey?id=' + this.key.keyId + '&key=' + this.key.keyValue).then((resp) => {
          this.$message('修改成功')
          this.key.keyVisible = false
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
