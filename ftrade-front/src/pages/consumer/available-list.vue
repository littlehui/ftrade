<template>
  <main>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>文件管理</el-breadcrumb-item>
      <el-breadcrumb-item>文件信息</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top:10px">
      <el-form :inline="true">
        <div class="queryForm">
          <el-form-item>
            <el-input v-model="params.query.fileId" placeholder="fileId"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="params.query.appId" placeholder="appId"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="params.query.bucket" placeholder="地址"></el-input>
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
      <el-table-column property="fileId" label="fileId"></el-table-column>
      <!--
            <el-table-column property="fileKey" label="文件路径"></el-table-column>
      -->
      <el-table-column property="fileSize" label="文件大小" :formatter="formatSize"></el-table-column>
      <el-table-column property="appId" label="appId"></el-table-column>
      <el-table-column property="fileUrl" label="文件地址"></el-table-column>
      <el-table-column property="fileStatus" label="文件状态" :formatter="formatStatus"></el-table-column>
      <el-table-column property="createTime" label="创建时间" :formatter="formatDate"></el-table-column>
      <!--   <el-table-column label="操作" fixed="right">
           <template slot-scope="scope">
             <el-button type="text" @click.native.prevent="showInfo(scope.row)">查看地址</el-button>
           </template>
         </el-table-column>-->
    </el-table>
    <div class="page-block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="params.pagination.pageNo"
                     :page-sizes="[20, 50, 100, 200]"
                     :page-size="params.pagination.pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="params.pagination.totalCount"></el-pagination>
    </div>
    <!-- 视频信息 -->
    <el-dialog :title="fileInfoTitle" :visible.sync="fileInfoFormVisible">
      <el-form ref="form" :model="fileInfo" label-width="80px">
        <el-form-item label="文件地址" :label-width="formLabelWidth">
          <el-input v-model="fileInfo.fileUrl" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="文件大小" :label-width="formLabelWidth">
          <el-input v-model="fileInfo.fileSize" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="etag" :label-width="formLabelWidth">
          <el-input v-model="fileInfo.etag" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="fileInfoFormVisible = false;">取 消</el-button>
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
            totalCount: 3
          },
          query: {
            fileId: '',
            appId: '',
            bucket: ''
          }
        },
        logList: [
          {
            fileId: 1,
            fileSize: 134217728,
            appId: 'GAMETRADE',
            fileUrl: '/spark/job/data/201611',
            fileStatus: 3,
            createTime: 1533543377000
          },
          {
            fileId: 2,
            fileSize: 134217728,
            appId: 'GAMETRADE',
            fileUrl: '/spark/job/data/201611',
            fileStatus: 3,
            createTime: 1533542377000
          },
          {
            fileId: 3,
            fileSize: 134217728,
            appId: 'GAMETRADE',
            fileUrl: '/spark/job/data/201611',
            fileStatus: 3,
            createTime: 1533541377000
          }

        ],
        fileInfo: {
          fileUrl: '',
          etag: '',
          fileSize: ''
        },
        formLabelWidth: '120px',
        fileInfoTitle: '文件信息',
        fileInfoFormVisible: false
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
        if (row[col.property] === 1) {
          return '初始化，准备加入处理队列'
        } else if (row[col.property] === 2) {
          return '处理中'
        } else if (row[col.property] === 3) {
          return '正常'
        } else if (row[col.property] === 4) {
          return '文件下载失败'
        } else {
          return '文件上传失败'
        }
      },
      formatSize (row, column) {
        let val = row[column.property]
        return this.size(val)
      },
      size (val) {
        if (val === null) {
          return ''
        }
        val = val / 1024
        if (val < 1) {
          return (val * 1024).toFixed(2) + 'B'
        }
        val = val / 1024
        if (val < 1) {
          return (val * 1024).toFixed(2) + 'KB'
        }
        val = val / 1024
        if (val < 1) {
          return (val * 1024).toFixed(2) + 'MB'
        }
        val = val / 1024
        if (val < 1) {
          return (val * 1024).toFixed(2) + 'GB'
        }
        return val
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
        this.$http.get('admin/file/list', {params: this.queryCondition}).then((resp) => {
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
      showInfo (row) {
        console.info(row.id)
        this.$http.get('admin/file/getFileInfo?id=' + row.id).then((resp) => {
          this.fileInfo.etag = resp.data.data.etag
          this.fileInfo.fileUrl = resp.data.data.fileUrl
          this.fileInfo.fileSize = this.size(resp.data.data.fileSize)
          this.fileInfoFormVisible = true
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
