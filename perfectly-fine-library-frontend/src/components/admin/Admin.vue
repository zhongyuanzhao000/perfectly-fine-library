<template>
    <el-container>
        <el-aside>
            <el-menu>
                <router-link :to="{path:'/admin/borrow'}">
                    <el-menu-item class="items" style="height: 100px">
                        借书
                    </el-menu-item>
                </router-link>
                <router-link :to="{path:'/admin/return'}">
                    <el-menu-item  class="items" style="height: 100px">
                        还书
                    </el-menu-item>
                </router-link>
                <router-link :to="{path:'/admin/getReserved'}">
                    <el-menu-item  class="items" style="height: 100px">
                        取预约书
                    </el-menu-item>
                </router-link>
                <router-link :to="{path:'/admin/damage'}">
                    <el-menu-item  class="items" style="height: 100px">
                        图书报损/报失
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
        name: "admin",
    }
</script>

<style scoped>

    .items{
        font-size: large;
        font-weight: bold;
    }
    .el-aside{
        margin-top: 40px;
    }
    .el-menu ,.el-menu-item{
        border-radius: 15px;
    }

    a{
        text-decoration: none;
    }

</style>
