webpackJsonp([9],{"Ray/":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("M4fF"),l=a.n(r),n=a("PJh5"),o=a.n(n),s={data:function(){return{params:{pagination:{pageNo:1,pageSize:20,totalCount:100},query:{operator:"",ip:"",resultType:"",queryDate:""}},logList:[],statusList:[{value:"",label:"全部"},{value:"1",label:"正常"},{value:"2",label:"失败"},{value:"3",label:"错误"}]}},computed:{queryCondition:function(){var e=l.a.assign({},this.params.pagination);return e=l.a.assign(e,this.params.query)}},mounted:function(){this.fetchList()},attached:function(){},methods:{formatDate:function(e,t){return null===e[t.property]?"-":o()(e[t.property]).format("YYYY-MM-DD HH:mm")},formatStatus:function(e,t){return 1===e[t.property]?"正常":2===e[t.property]?"失败":"错误"},handleSizeChange:function(e){this.params.pagination.pageSize=e,this.fetchList()},handleCurrentChange:function(e){this.params.pagination.pageNo=e,this.fetchList()},fetchList:function(){var e=this,t=this.queryCondition,a=t.queryDate;""!==a&&null!==a&&a instanceof Array&&(t.queryDate=a.join(",")),this.$http.get("admin/log/list",{params:t}).then(function(t){e.logList=t.data.data.listData,e.params.pagination.totalCount=t.data.data.totalCount},function(e){console.log(e)})},resetPageNoQuery:function(){this.params.pagination.pageNo=1,this.fetchList()}},components:{}},i={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("main",[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",{attrs:{to:{path:"/"}}},[e._v("首页")]),e._v(" "),a("el-breadcrumb-item",[e._v("日志管理")]),e._v(" "),a("el-breadcrumb-item",[e._v("日志信息")])],1),e._v(" "),a("div",{staticStyle:{"margin-top":"10px"}},[a("el-form",{attrs:{inline:!0}},[a("div",{staticClass:"queryForm"},[a("el-form-item",[a("el-input",{attrs:{placeholder:"操作人"},model:{value:e.params.query.operator,callback:function(t){e.$set(e.params.query,"operator",t)},expression:"params.query.operator"}})],1),e._v(" "),a("el-form-item",[a("el-input",{attrs:{placeholder:"ip"},model:{value:e.params.query.ip,callback:function(t){e.$set(e.params.query,"ip",t)},expression:"params.query.ip"}})],1),e._v(" "),a("el-form-item",[a("el-select",{attrs:{placeholder:"状态"},model:{value:e.params.query.resultType,callback:function(t){e.$set(e.params.query,"resultType",t)},expression:"params.query.resultType"}},e._l(e.statusList,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",[a("div",{staticClass:"block"},[a("el-date-picker",{attrs:{type:"daterange",align:"right","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",format:"yyyy-MM-dd","value-format":"yyyy-MM-dd HH:mm:ss","default-time":["00:00:00","23:59:59"]},model:{value:e.params.query.queryDate,callback:function(t){e.$set(e.params.query,"queryDate",t)},expression:"params.query.queryDate"}})],1)]),e._v(" "),a("el-form-item",[a("el-button",{nativeOn:{click:function(t){return t.preventDefault(),e.resetPageNoQuery(t)}}},[a("i",{staticClass:"fa fa-search",attrs:{"aria-hidden":"true"}}),e._v("\n            查询\n          ")])],1)],1)])],1),e._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.logList,stripe:"",border:""}},[a("el-table-column",{attrs:{property:"operator",label:"操作人"}}),e._v(" "),a("el-table-column",{attrs:{property:"ip",label:"ip"}}),e._v(" "),a("el-table-column",{attrs:{property:"classMethod",label:"请求方法"}}),e._v(" "),a("el-table-column",{attrs:{property:"createTime",label:"访问时间",formatter:e.formatDate}}),e._v(" "),a("el-table-column",{attrs:{property:"operateTime",label:"请求时长"}}),e._v(" "),a("el-table-column",{attrs:{property:"obj",label:"请求参数"}}),e._v(" "),a("el-table-column",{attrs:{property:"resultType",label:"请求结果",formatter:e.formatStatus}}),e._v(" "),a("el-table-column",{attrs:{property:"remark",label:"错误信息"}})],1),e._v(" "),a("div",{staticClass:"page-block"},[a("el-pagination",{attrs:{"current-page":e.params.pagination.pageNo,"page-sizes":[20,50,100,200],"page-size":e.params.pagination.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.params.pagination.totalCount},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1)},staticRenderFns:[]};var p=a("VU/8")(s,i,!1,function(e){a("Sqte")},"data-v-5da80c25",null);t.default=p.exports},Sqte:function(e,t){}});
//# sourceMappingURL=9.975cb73a0c09d25090e7.js.map