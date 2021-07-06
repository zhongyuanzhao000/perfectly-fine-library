<template>
  <div id="app" style="height: 100%;height: 100%">
    <router-view/>
  </div>
</template>
<!--
学生：在图书页面中申请跨校区借阅，申请预约，在‘我的借书’页面中续借、查看借书信息（还有多久到期），
  上交购买书表单，查看自己借书信用分并在此界面中查看信用分改变记录，
管理员：上传图书，修改图书信息，管理其他账户，
-->

<script>
export default {
  name: 'App',
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0;
  width: 100%;
  min-height: 100%;
  height: auto;
  background-size: 100% 100%;
  background: url("../src/assets/background4.jpg") no-repeat center 0;
  /*margin-top: 60px;*/
  /*自带的css，注释掉因为它使得页面上方有空隙*/
}
</style>
