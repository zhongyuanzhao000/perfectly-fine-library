<template >
    <el-menu :default-active="activeIndex"
             class="el-menu" mode="horizontal"
             background-color="#DCDCDC"
             text-color="#F0FFFF"
             font-size=""
             active-text-color="#4682B4"
             router>

        <el-menu-item index="" class="items"><img src="../../assets/Nav/logo.png"></el-menu-item>

        <el-menu-item index="/" class="menu-item">主页</el-menu-item>

        <!--以下为管理员内容-->
        <el-menu-item v-if="isAdmin" index="/admin/borrow" class="menu-item ">
                借/还书
        </el-menu-item>
        <el-menu-item v-if="isAdmin" index="/upload" class="menu-item ">
                上传图书
        </el-menu-item>
        <el-menu-item v-if="isAdmin" index="/record" class="menu-item ">
            查看记录
        </el-menu-item>


        <!--以下为用户内容-->
        <el-menu-item v-if="isUser" index="/user/myInformation" class="menu-item ">
                个人信息
        </el-menu-item>

        <!--以下为超级管理员内容-->
        <el-menu-item v-if="isSuper" index="/reset" class="menu-item">
            信用重置
        </el-menu-item>
        <el-menu-item v-if="isSuper" index="/addAdmin" class="menu-item ">
            添加管理员
        </el-menu-item>
        <el-menu-item v-if="isSuper" index="/config" class="menu-item ">
            修改设置
        </el-menu-item>
        <el-menu-item v-if="isSuper" index="/sendEmail" class="menu-item ">
            超期提醒
        </el-menu-item>
        <!--登出-->
        <el-menu-item v-if="unlogged" index="/login/user" class="menu-item">
           登录
        </el-menu-item>
        <el-menu-item  v-else v-on:click="logOut" index="/logout" class="menu-item">
            退出登录
        </el-menu-item>

    </el-menu>

</template>

<script>
    export default {
        data(){
            return{
                activeIndex:'/',
                activeIndex2:'1',
                unlogged:window.localStorage.getItem('role')!=='admin'&&window.localStorage.getItem('role')!=='user'&&window.localStorage.getItem('role')!=='super',
                isAdmin:window.localStorage.getItem('role')==='admin'||window.localStorage.getItem('role')==='super',
                isUser:window.localStorage.getItem('role') === 'user',
                isSuper:window.localStorage.getItem('role') === 'super'
            };
        },/*
        computed:{
            unlogged:function () {
                return window.localStorage.getItem('role') !== 'admin' && window.localStorage.getItem('role') !== 'user' && window.localStorage.getItem('role') !== 'super'
            },
            isAdmin:function () {
                return window.localStorage.getItem('role')==='admin'||window.localStorage.getItem('role')==='super'
            },
            isUser:function() {
                return window.localStorage.getItem('role') === 'user'
            },
            isSuper:function() {
                return window.localStorage.getItem('role') === 'super'
            }
        },*/
        created() {
            this.activeIndex=this.$route.path
        },
        methods:{
            handleSelect(key,keyPath) {
                console.log(key, keyPath);
            },
            logOut(){
                window.localStorage.setItem('role','');
                this.unlogged=true;
                this.isAdmin=false;
                this.isUser=false;
                this.isSuper=false;
                window.sessionStorage.setItem('token','');
                if(this.$route.path==='/'){
                    this.$router.go(0);
                }else{
                    this.$router.push('/');
                }

            }
        },
        components:{

        },
        name: "NavMenu"
    }
</script>

<style scoped>

    .el-menu{
        height: 75px;
        position: center;
        text-align: center;
    }

    .items{
        height: 75px;
        position: center;
        font-size: large;
        font-weight: bold;
        text-align: center;
        width: 25%;
    }

    .menu-item{
        font-size: large;
        font-weight: bold;

    }

</style>
