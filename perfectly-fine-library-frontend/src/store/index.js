import { createStore } from 'vuex'

export default createStore({/*
  state: {
    token: localStorage.getItem('token') || null,
    user: {
      id: window.localStorage.getItem('id' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('id' || '[]')).id,
      role:window.localStorage.getItem('role' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('role' || '[]')).role
    }
  },
  mutations: {
    login (state, data) {
      localStorage.setItem('token',data.token)
      state.user = data.user
      state.token = data.token
      window.localStorage.setItem('user', JSON.stringify(state.user))
    }
  },
  actions: {
  },
  modules: {
  }*/
})
