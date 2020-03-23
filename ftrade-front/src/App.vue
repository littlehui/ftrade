<template>
  <div id="app">
    <div v-if="isLogin">
      <el-container>
        <el-header height="80px">
          <div class="navbar-header" >
            <a class="navbar-brand" href="/">FTRADE<span>-dev</span></a>
          </div>
          <div class="navbar-menu">
            <el-menu :default-active="defaultTopMenuActive" class="el-menu-demo" mode="horizontal" @select="handleSelect" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
              <el-menu-item :index="item.index" :key="item.index" v-for="item in topMenuList">{{item.title}}</el-menu-item>
            </el-menu>
          </div>
          <div class="navbar-user">
            <el-dropdown split-button type="plain" @command="handleCommand">
              {{nickname}}
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div class="navbar-change">
            <el-button type="plain" icon="el-icon-sort" @click="changeUrl">切换权限管理后台</el-button>
          </div>
        </el-header>
        <el-container>
          <el-aside>
            <el-menu :default-active="defaultLeftMenuActive" class="el-menu-vertical-demo" background-color="#D3DCE6" :router="true" @select="handleSelectLeftMenu">
              <el-menu-item :index="item.index" :key="item.index" v-for="item in leftMenuList" >{{item.title}}</el-menu-item>
            </el-menu>
          </el-aside>
          <el-container>
            <el-main>
              <router-view/>
            </el-main>
            <el-footer style="height: 0px"></el-footer>
          </el-container>
        </el-container>
      </el-container>

    </div>
    <div v-else>
      <signin></signin>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import * as types from './store/mutation-types'

const navConfig = require('./router/nav.config.json')

export default {
  name: 'App',
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'resetTopMenuIndex'
  },
  computed: {
    // 使用对象展开运算符将 getter 混入 computed 对象中
    ...mapGetters([
      'isLogin',
      'nickname',
      'userResourceKeys',
      'roleCode',
      'userResourceKeyMap'
    ]),
    topMenuList: function () {
      let topMenuList = []
      if (this.roleCode === '') {
        return topMenuList
      } else {
        for (let i = 0; i < navConfig.length; i++) {
          if (this.userResourceKeyMap[navConfig[i].resourceKey]) {
            topMenuList.push({
              title: navConfig[i].title,
              index: navConfig[i].name,
              url: navConfig[i].url
            })
          }
        }
      }
      return topMenuList
    },
    defaultTopMenuActive: function () {
      let path = this.$route.path
      if (this.topMenuList.length === 0) {
        return
      }
      for (let i = 0; i < this.topMenuList.length; i++) {
        if (path.startsWith(this.topMenuList[i].url)) {
          return this.topMenuList[i].index
        }
      }
      return this.topMenuList[0].index
    },
    defaultLeftMenuActive: function () {
      return this.$route.path
    },
    defaultLeftMenuName: function () {
      if (this.leftMenuList.length > 0) {
        return this.leftMenuList[0].name
      }
      return ''
    },
    leftMenuList: function () {
      let secondMenuList = []
      if (this.topMenuList.length < 1) {
        return secondMenuList
      }
      for (let i = 0; i < navConfig.length; i++) {
        if (this.topMenuIndex !== navConfig[i].name) {
          continue
        }
        for (let j = 0; j < navConfig[i].subMenus.length; j++) {
          if (this.userResourceKeyMap[navConfig[i].subMenus[j].resourceKey]) {
            secondMenuList.push({
              title: navConfig[i].subMenus[j].title,
              index: navConfig[i].url + navConfig[i].subMenus[j].url,
              name: navConfig[i].subMenus[j].name
            })
          }
        }
      }
      return secondMenuList
    }
  },
  methods: {
    ...mapActions({
      logout: types.ACTION_USER_LOGOUT // 将 `this.add()` 映射为 `this.$store.dispatch('increment')`
    }),
    resetTopMenuIndex () {
      let path = this.$route.path
      for (let i = 0; i < this.topMenuList.length; i++) {
        if (path.startsWith(this.topMenuList[i].url)) {
          this.topMenuIndex = this.topMenuList[i].index
        }
      }
    },
    handleCommand (command) {
      if (command === 'logout') {
        this.$http.post('admin/user/logout').then((response) => {
          // vuex 清理
          this.logout()
        }, (error) => {
          console.log(error)
        })
        // session 清理
        localStorage.setItem('account', '')
        localStorage.setItem('nickname', '')
        localStorage.setItem('authCode', '')
        localStorage.setItem('userResourceKeys', '[]')
        localStorage.setItem('userResourceKeyMap', '{}')
      }
    },
    handleSelect: function (val) {
      this.topMenuIndex = val
      if (this.defaultLeftMenuName) {
        this.$router.push({ name: this.defaultLeftMenuName })
      }
    },
    handleSelectLeftMenu: function (val) {
      console.log('left menu', val)
    },
    changeUrl () {
      window.location.href = '/auth/index.html#/'
    }
  },
  data () {
    return {
      topMenuIndex: 'file-message'
    }
  },
  mounted () {

  }
}
</script>
<style>
  .el-menu--horizontal {
    border-bottom: none;
  }
  a {
    text-decoration: none;
  }
  .navbar-header {
    padding-left: 30px;
    padding-right: 30px;
    width: 250px;
    float: left;
  }
  .navbar-brand {
    font-size: 35px;
    font-weight: 700;
    color: #999;
  }
  .navbar-menu {
    float: left;
    line-height: 80px;
    padding-top: 10px;
  }
  .navbar-user {
    float: right;
    color: #fff;
  }
  .navbar-brand span {
    color: #2280d0;
  }
  .el-header, .el-footer {
    background-color: #545c64;
    color: #333;
    text-align: center;
    line-height: 80px;
  }
  .navbar-change {
    float: right;
    color: #fff;
    margin-right: 10px;
  }
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 250px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    min-height:850px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
  #app {
  }
</style>
