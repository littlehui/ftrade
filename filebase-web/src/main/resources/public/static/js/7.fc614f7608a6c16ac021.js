webpackJsonp([7],{EpSj:function(e,t){},rHLy:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=r("mvHQ"),s=r.n(o),a=r("Dd8w"),n=r.n(a),l=r("Du/2"),i=r("NYxO"),u={data:function(){return{dialogFormVisible:!0,frm:{userCode:"",password:""},role:{roles:[],showForVisble:!1,roleCode:"",key:""},authUser:{},rules:{username:[{required:!0,message:"请输入账号",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"change"}]}}},computed:{serverUrl:function(){return""}},mounted:function(){this.initRoles()},methods:n()({},Object(i.b)({login:l.a}),{initRoles:function(){var e=this;this.$http.get(this.serverUrl+"/admin/sys/role/listAll").then(function(t){"success"===t.data.result&&(e.options=t.data.data)},function(e){console.log("初始化角色出错",e)})},signin:function(){var e=this,t=this;t.$refs.frm.validate(function(r){return!!r&&(t.$http.post("admin/user/login",{userCode:e.frm.userCode,password:e.frm.password}).then(function(e){var r=e.data.data;t.authUser=r.authUser,t.role.key=r.key,0!==r.roleNum?1===r.roleNum?(t.role.roleCode=r.roles[0].code,t.secLogin()):(t.role.roles=r.roles,t.role.showForVisble=!0):t.$message("你没有绑定过角色无法登陆")},function(e){console.log(e)}),!0)})},secLogin:function(){var e=this;this.$http.post("admin/user/realLogin",{key:this.role.key,roleCode:this.role.roleCode}).then(function(t){var r=t.data.data;localStorage.setItem("authCode",r.authCode),localStorage.setItem("account",r.account),localStorage.setItem("nickname",r.nickname),localStorage.setItem("roleCode",r.roleCode),localStorage.setItem("userResourceKeys",s()(r.userResourceKeys));for(var o={},a=0;a<r.userResourceKeys.length;a++)o[r.userResourceKeys[a]]=!0;localStorage.setItem("userResourceKeyMap",s()(o)),t.data.data.userResourceKeyMap=o,e.login(r),e.$router.push({name:"home"})},function(e){console.log(e)})}}),components:{}},c={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("main",{attrs:{id:"bg"}},[e.dialogFormVisible?e._e():r("el-button",{directives:[{name:"",rawName:"v-"}],staticClass:"hh",attrs:{type:"text"},nativeOn:{click:function(t){e.dialogFormVisible=!0}}},[e._v("登录\n  ")]),e._v(" "),r("el-dialog",{attrs:{title:"登录",visible:e.dialogFormVisible,width:"30%"},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[r("el-form",{ref:"frm",attrs:{"label-position":"top",model:e.frm,rules:e.rules}},[r("el-form-item",{attrs:{label:"",prop:"account"}},[r("el-input",{attrs:{placeholder:"用户名"},model:{value:e.frm.userCode,callback:function(t){e.$set(e.frm,"userCode",t)},expression:"frm.userCode"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"",prop:"password"}},[r("el-input",{attrs:{type:"password",placeholder:"密码"},nativeOn:{keyup:function(t){return"button"in t||!e._k(t.keyCode,"enter",13,t.key,"Enter")?e.signin(t):null}},model:{value:e.frm.password,callback:function(t){e.$set(e.frm,"password",t)},expression:"frm.password"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"",prop:"repassword"}},[r("el-input",{attrs:{type:"password",placeholder:"确认密码"},nativeOn:{keyup:function(t){return"button"in t||!e._k(t.keyCode,"enter",13,t.key,"Enter")?e.signin(t):null}},model:{value:e.frm.repassword,callback:function(t){e.$set(e.frm,"repassword",t)},expression:"frm.repassword"}})],1)],1),e._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"primary"},nativeOn:{click:function(t){return t.preventDefault(),e.signup(t)}}},[e._v("注册")])],1)],1)],1)},staticRenderFns:[]};var d=r("VU/8")(u,c,!1,function(e){r("EpSj")},"data-v-6f43e19d",null);t.default=d.exports}});
//# sourceMappingURL=7.fc614f7608a6c16ac021.js.map