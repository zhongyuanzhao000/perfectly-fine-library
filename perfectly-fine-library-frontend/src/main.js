import './plugins/axios'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'

//import ElementPlus from 'element-plus'
//import 'element-plus/lib/theme-chalk/index.css'

//import './plugins/element.js'
var axios = require('axios');

axios.defaults.headers.post['Content-Type']='application/json';
axios.defaults.baseURL = '/api/v1';
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
/*
axios.interceptors.request.use(config=>{
    config.headers.Authorization=window.sessionStorage.getItem('token')
    return config
})
*/

const app = createApp(App);

//require('./mocker');

axios.interceptors.request.use(config =>{
    config.headers.Authorization = window.sessionStorage.getItem("token");
    return config
});
installElementPlus(app);
app.config.globalProperties.$axios = axios;
app.use(store).use(router).mount('#app');
