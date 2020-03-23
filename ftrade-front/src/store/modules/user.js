import {
  USER_LOGIN,
  USER_LOGOUT,
  AUTH_CODE_SET,
  AUTH_MENU_LIST_SET,
  ACTION_USER_LOGIN,
  ACTION_USER_LOGOUT
} from '../mutation-types'

// initial state,单一状态树
/*const state = {
  // 用户权限列表
  account: '', // 登录账号
  nickname: '', // 昵称
  roleCode: '',
  authCode: '',
  userResourceKeys: [],
  userResourceKeyMap: {}
}*/

const state = {
  // 用户权限列表
  account: 'CY6034', // 登录账号
  nickname: 'littlehui', // 昵称
  roleCode: 'Admin',
  authCode: 'Admin',
  userResourceKeys: ["consumer:manager"],
  userResourceKeyMap: {}
}

// getters, 派生属性
const getters = {
  isLogin: state => state.authCode !== '',
  nickname: state => state.nickname,
  roleCode: state => state.roleCode,
  userResourceKeys: state => state.userResourceKeys,
  authCode: state => state.authCode,
  userResourceKeyMap: state => state.userResourceKeyMap
}
// actions, 中转操作, 不直接操作数据,只作用于mutation, 可以用异步操作
const actions = {
  [ACTION_USER_LOGIN] ({ commit }, responseData) {
    commit(USER_LOGIN, responseData)
  },
  [ACTION_USER_LOGOUT] ({ commit }, responseData) {
    commit(USER_LOGOUT, responseData)
  }
}
// mutations, 对状态树的直接修改
const mutations = {
  [USER_LOGIN] (state, {account, nickname, authCode, roleCode, userResourceKeys, userResourceKeyMap}) {
    state.account = account
    state.nickname = nickname
    state.authCode = authCode
    state.roleCode = roleCode
    state.userResourceKeys = userResourceKeys
    state.userResourceKeyMap = userResourceKeyMap
  },
  [USER_LOGOUT] (state) {
    state.account = ''
    state.nickname = ''
    state.authCode = ''
    state.userResourceKeys = []
    state.roleCode = ''
    state.userResourceKeyMap = {}
  },
  [AUTH_CODE_SET] (state) {

  },
  [AUTH_MENU_LIST_SET] (state) {

  }
}

// 暴露出去
export default {
  state,
  getters,
  actions,
  mutations
}
