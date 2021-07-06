<template>
    <!--这里就实现了头像-->
    <div>
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
    </div>
    <el-menu
        default-active="3"
        style="">
        <a href="#/user/my-information" class="link">
            <el-menu-item index="1" style="height: 100px;">
                <i class="el-icon-apple"></i>
                <span class="slot">我的信息</span>
            </el-menu-item>
        </a>
        <a href="#/user/change" class="link">
            <el-menu-item index="2" style="height: 100px;" v-on:click="jump(2)">
                <!-- i用于加上图标-->
                <i class="el-icon-setting"></i>
                <span class="slot">修改密码</span>
            </el-menu-item>
        </a>
        <a href="#/user#my-borrow" class="link">
            <el-menu-item index="3" style="height: 100px;">
                <i class="el-icon-document"></i>
                <span class="slot">我的借阅</span>
            </el-menu-item>
        </a>
        <a href="#/user#my-reserve" class="link">
            <el-menu-item index="4" style="height: 100px;">
                <i class="el-icon-box"></i>
                <span class="slot">我的预约</span>
            </el-menu-item>
        </a>

    </el-menu>
</template>

<script>
    export default {
        name: "SideMenu"
        ,
        data(){
            return{
                type:1
            }
        },
        created() {
            this.type=1
        },
        methods:{
            jump(index){
                this.type  = index
            }
        }

    }
</script>

<style scoped>

    .slot{
        font-size : large;
    }
</style>
