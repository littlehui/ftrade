<template>
  <main id="bg">
    <el-button v-if="!dialogFormVisible" type="text" class="hh" @click.native="dialogFormVisible = true" v->登录
    </el-button>
    <el-dialog title="登录" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-position="top" :model="frm" :rules="rules" ref="frm">
        <el-form-item label="" prop="account">
          <el-input v-model="frm.userCode" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="" prop="password">
          <el-input type="password" v-model="frm.password" placeholder="密码" @keyup.native.enter="signin"></el-input>
        </el-form-item>
        <el-form-item label="" prop="repassword">
          <el-input type="password" v-model="frm.repassword" placeholder="确认密码" @keyup.native.enter="signin"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="signup">注册</el-button>
    </span>
    </el-dialog>
  </main>

</template>

<script>
  import * as types from '../../../store/mutation-types'
  import {mapActions} from 'vuex/types'

  export default {
    data () {
      return {
        dialogFormVisible: true,
        frm: {
          userCode: '',
          password: ''
        },
        role: {
          roles: [],
          showForVisble: false,
          roleCode: '',
          key: ''
        },
        authUser: {},
        rules: {
          username: [
            {required: true, message: '请输入账号', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'change'}
          ]
        }
      }
    },
    computed: {
      serverUrl: function () {
        switch (process.env.NODE_ENV) {
          case 'production':
            return ''
          default:
            return 'http://localhost:8089'
        }
      }
    },
    mounted () {
      this.initRoles()
    },
    methods: {
      ...mapActions({
        login: types.ACTION_USER_LOGIN // 将 `this.add()` 映射为 `this.$store.dispatch('increment')`
      }),
      initRoles () {
        this.$http.get(`${this.serverUrl}/admin/sys/role/listAll`).then((resp) => {
          if (resp.data.result === 'success') {
            this.options = resp.data.data
          }
        }, (resp) => {
          console.log('初始化角色出错', resp)
        })
      },
      signin () {
        let app = this
        app.$refs.frm.validate((valid) => {
          if (valid) {
            app.$http.post('admin/user/login',
              {userCode: this.frm.userCode, password: this.frm.password}).then((response) => {
              // 登录成功,写入数据到session 以及vuex
              let data = response.data.data
              app.authUser = data.authUser
              app.role.key = data.key
              if (data.roleNum === 0) {
                app.$message('你没有绑定过角色无法登陆')
                return
              }
              if (data.roleNum === 1) {
                // 直接请求选择角色确认方法
                app.role.roleCode = data.roles[0].code
                app.secLogin()
              } else {
                app.role.roles = data.roles
                app.role.showForVisble = true
              }
            }, (error) => {
              console.log(error)
            })
            return true
          }
          return false
        })
      },
      secLogin () {
        let app = this
        app.$http.post('admin/user/realLogin', {key: this.role.key, roleCode: this.role.roleCode}).then((response) => {
          let data = response.data.data
          localStorage.setItem('authCode', data.authCode)
          localStorage.setItem('account', data.account)
          localStorage.setItem('nickname', data.nickname)
          localStorage.setItem('roleCode', data.roleCode)
          localStorage.setItem('userResourceKeys', JSON.stringify(data.userResourceKeys))
          var m = {}
          for (var i = 0; i < data.userResourceKeys.length; i++) {
            m[data.userResourceKeys[i]] = true
          }
          localStorage.setItem('userResourceKeyMap', JSON.stringify(m))
          response.data.data.userResourceKeyMap = m
          // vuex
          this.login(data)
          // 路由
          this.$router.push({name: 'home'})
        }, (error) => {
          console.log(error)
        })
      }
    },
    components: {}
  }
</script>

<style lang="css" scoped>
  main {
    padding: 20px;
    background-color: #475669;
    background-size: cover;
    background-repeat: no-repeat;
    background-position: 50% 50%;
    height: 100vh;
  }

  .hh {
    color: #fff;
    border: 1px solid #fff;
  }
</style>
