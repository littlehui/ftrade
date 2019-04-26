// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import configRouter from './router'
import VueRouter from 'vue-router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import signin from './pages/signin'
import * as types from './store/mutation-types'

Vue.config.productionTip = false

// 相关模块引入
Vue.use(VueRouter)
Vue.use(VueAxios, axios)
Vue.use(ElementUI)

// 组件列表
Vue.component('signin', signin)

const router = new VueRouter({
  routes: configRouter
})

/* eslint-disable no-new */
const app = new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

switch (process.env.NODE_ENV) {
  case 'production':
    axios.defaults.baseURL = ''
    break
  default:
    axios.defaults.baseURL = 'http://localhost:8089'
    break
}

axios.interceptors.request.use(function (config) {
  if (store.state.user.authCode) {
    config.headers.Authorization = `${store.state.user.authCode}`
  }
  return config
}, function (error) {
  return Promise.reject(error)
})

axios.interceptors.response.use(function (response) {
  if (response.status === 200) {
    if (response.data.result !== 'success') {
      const message = response.data.messages.join(' ')
      app.$message(message)
      return Promise.reject(new Error(message))
    }
  }
  return response
}, function (error) {
  if (!error.response) {
    return Promise.reject(error)
  }
  if (error.response.status === 401) {
    store.dispatch(types.ACTION_USER_LOGOUT).then(() => {
      localStorage.setItem('account', '')
      localStorage.setItem('nickname', '')
      localStorage.setItem('authCode', '')
      localStorage.setItem('roleCode', '')
      localStorage.setItem('userResourceKeys', '[]')
      localStorage.setItem('userResourceKeyMap', '{}')
    })
  } else if (error.response.status === 403) {
    app.$message(`未授权,请联系管理员`)
  } else {
    app.$message(`服务错误:${error.response.status} ${error.response.statusText}`)
  }
  return Promise.reject(error)
})
