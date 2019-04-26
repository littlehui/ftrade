import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import createLogger from '../plugins/logger'
import * as types from './mutation-types'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

const store = new Vuex.Store({
  modules: {
    user
  },
  strict: debug,
  plugins: debug ? [createLogger()] : []
})

let resourceKey = localStorage.getItem('userResourceKeys') || '[]'
let resourceKeyMap = localStorage.getItem('userResourceKeyMap') || '{}'
// 定义完成后从session中恢复下数据
let sessionData = {
  account: localStorage.getItem('account') || '',
  nickname: localStorage.getItem('nickname') || '',
  authCode: localStorage.getItem('authCode') || '',
  roleCode: localStorage.getItem('roleCode') || '',
  userResourceKeys: JSON.parse(resourceKey),
  userResourceKeyMap: JSON.parse(resourceKeyMap)
}

store.dispatch(types.ACTION_USER_LOGIN, sessionData)

export default store
