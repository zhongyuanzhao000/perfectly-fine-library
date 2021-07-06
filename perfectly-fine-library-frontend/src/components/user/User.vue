<template>
    <el-container>
        <el-aside id="aside">
            <div>
                <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
            </div>
            <el-menu>
                <router-link :to="{path:'/user/myInformation'}">
                    <el-menu-item index="1" style="height: 100px;">
                        <i class="el-icon-user"></i>
                        <span class="slot">个人信息</span>
                    </el-menu-item>
                </router-link>
                <router-link :to="{path:'/user/myBorrow'}">
                    <el-menu-item index="2" style="height: 100px;">
                        <i class="el-icon-document"></i>
                        <span class="slot">我的借阅</span>
                    </el-menu-item>
                </router-link>
                <router-link :to="{path:'/user/myReserve'}">
                    <el-menu-item index="3" style="height: 100px;">
                        <i class="el-icon-document"></i>
                        <span class="slot">我的预约</span>
                    </el-menu-item>
                </router-link>
                <router-link :to="{path:'/user/myRecord'}">
                    <el-menu-item index="4" style="height: 100px;">
                        <i class="el-icon-box"></i>
                        <span class="slot">我的记录</span>
                    </el-menu-item>
                </router-link>
                <router-link :to="{path:'/user/myComment'}">
                    <el-menu-item index="5" style="height: 100px;">
                        <i class="el-icon-box"></i>
                        <span class="slot">我的评价</span>
                    </el-menu-item>
                </router-link>
            </el-menu>
        </el-aside>
        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>
</template>

<script>
    export default {
        name: "User"
    }
</script>

<style scoped>

    #aside{
        margin-top: 100px;
    }
    .el-menu ,.el-menu-item{
        border-radius: 15px;
    }

    a{
        text-decoration: none;
    }

</style>
