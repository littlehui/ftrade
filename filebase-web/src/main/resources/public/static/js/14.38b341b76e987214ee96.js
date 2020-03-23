webpackJsonp([14],{Io1b:function(e,t){},z0Bt:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("M4fF"),l=a.n(i),r=a("PJh5"),n=a.n(r),o={data:function(){return{params:{pagination:{pageNo:1,pageSize:20,totalCount:100},query:{searchkey:"",startTimeMills:0,endTimeMills:0}},logList:[],fileInfo:{fileUrl:"",etag:"",fileSize:""},formLabelWidth:"120px",fileInfoTitle:"票据信息",fileInfoFormVisible:!1}},computed:{queryCondition:function(){var e=l.a.assign({},this.params.pagination);return e=l.a.assign(e,this.params.query)}},mounted:function(){this.fetchList()},attached:function(){},methods:{formatDate:function(e,t){return null===e[t.property]?"-":n()(e[t.property]).format("YYYY-MM-DD HH:mm")},formatStatus:function(e,t){return 1===e[t.property]?"新建":2===e[t.property]?"已支付":3===e[t.property]?"完成":4===e[t.property]?"失败":"无效"},formatSize:function(e,t){var a=e[t.property];return this.size(a)},size:function(e){return null===e?"":(e/=1024)<1?(1024*e).toFixed(2)+"B":(e/=1024)<1?(1024*e).toFixed(2)+"KB":(e/=1024)<1?(1024*e).toFixed(2)+"MB":(e/=1024)<1?(1024*e).toFixed(2)+"GB":e},handleSizeChange:function(e){this.params.pagination.pageSize=e,this.fetchList()},handleCurrentChange:function(e){this.params.pagination.pageNo=e,this.fetchList()},fetchList:function(){var e=this;this.$http.get("admin/file/list",{params:this.queryCondition}).then(function(t){e.logList=t.data.data.listData,e.params.pagination.totalCount=t.data.data.totalCount},function(e){console.log(e)})},resetPageNoQuery:function(){this.params.pagination.pageNo=1,this.fetchList()},showInfo:function(e){var t=this;console.info(e.id),this.$http.get("admin/file/getFileInfo?id="+e.id).then(function(e){t.fileInfo.etag=e.data.data.etag,t.fileInfo.fileUrl=e.data.data.fileUrl,t.fileInfo.fileSize=t.size(e.data.data.fileSize),t.fileInfoFormVisible=!0},function(e){console.log(e)})}},components:{}},s={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("main",[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",{attrs:{to:{path:"/"}}},[e._v("首页")]),e._v(" "),a("el-breadcrumb-item",[e._v("票据管理")]),e._v(" "),a("el-breadcrumb-item",[e._v("历史票据")])],1),e._v(" "),a("div",{staticStyle:{"margin-top":"10px"}},[a("el-form",{attrs:{inline:!0}},[a("div",{staticClass:"queryForm"},[a("el-form-item",[a("el-input",{attrs:{placeholder:"fileId"},model:{value:e.params.query.searchKey,callback:function(t){e.$set(e.params.query,"searchKey",t)},expression:"params.query.searchKey"}})],1),e._v(" "),a("el-form-item",[a("el-input",{attrs:{placeholder:"appId"},model:{value:e.params.query.startTimeMills,callback:function(t){e.$set(e.params.query,"startTimeMills",t)},expression:"params.query.startTimeMills"}})],1),e._v(" "),a("el-form-item",[a("el-input",{attrs:{placeholder:"bucket"},model:{value:e.params.query.endTimeMills,callback:function(t){e.$set(e.params.query,"endTimeMills",t)},expression:"params.query.endTimeMills"}})],1),e._v(" "),a("el-form-item",[a("el-button",{nativeOn:{click:function(t){return t.preventDefault(),e.resetPageNoQuery(t)}}},[a("i",{staticClass:"fa fa-search",attrs:{"aria-hidden":"true"}}),e._v("\n            查询\n          ")])],1)],1)])],1),e._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.logList,stripe:"",border:""}},[a("el-table-column",{attrs:{property:"id",label:"序号"}}),e._v(" "),a("el-table-column",{attrs:{property:"name",label:"名称"}}),e._v(" "),a("el-table-column",{attrs:{property:"companyName",label:"公司名"}}),e._v(" "),a("el-table-column",{attrs:{property:"faceAmount",label:"票面金额"}}),e._v(" "),a("el-table-column",{attrs:{property:"buyAmount",label:"购买金额"}}),e._v(" "),a("el-table-column",{attrs:{property:"status",label:"状态",formatter:e.formatStatus}}),e._v(" "),a("el-table-column",{attrs:{property:"createTime",label:"创建时间",formatter:e.formatDate}}),e._v(" "),a("el-table-column",{attrs:{label:"操作",fixed:"right"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text"},nativeOn:{click:function(a){a.preventDefault(),e.showInfo(t.row)}}},[e._v("查看详情")])]}}])})],1),e._v(" "),a("div",{staticClass:"page-block"},[a("el-pagination",{attrs:{"current-page":e.params.pagination.pageNo,"page-sizes":[20,50,100,200],"page-size":e.params.pagination.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.params.pagination.totalCount},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1),e._v(" "),a("el-dialog",{attrs:{title:e.fileInfoTitle,visible:e.fileInfoFormVisible},on:{"update:visible":function(t){e.fileInfoFormVisible=t}}},[a("el-form",{ref:"form",attrs:{model:e.fileInfo,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"文件地址","label-width":e.formLabelWidth}},[a("el-input",{attrs:{disabled:!0},model:{value:e.fileInfo.fileUrl,callback:function(t){e.$set(e.fileInfo,"fileUrl",t)},expression:"fileInfo.fileUrl"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"文件大小","label-width":e.formLabelWidth}},[a("el-input",{attrs:{disabled:!0},model:{value:e.fileInfo.fileSize,callback:function(t){e.$set(e.fileInfo,"fileSize",t)},expression:"fileInfo.fileSize"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"etag","label-width":e.formLabelWidth}},[a("el-input",{attrs:{disabled:!0},model:{value:e.fileInfo.etag,callback:function(t){e.$set(e.fileInfo,"etag",t)},expression:"fileInfo.etag"}})],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{nativeOn:{click:function(t){e.fileInfoFormVisible=!1}}},[e._v("取 消")])],1)],1)],1)},staticRenderFns:[]};var f=a("VU/8")(o,s,!1,function(e){a("Io1b")},"data-v-1b9d614c",null);t.default=f.exports}});
//# sourceMappingURL=14.38b341b76e987214ee96.js.map