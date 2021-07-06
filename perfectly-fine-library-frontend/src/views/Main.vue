<template>
    <body id="mainbg" style="height: 100%;width: 100%">
    <el-container class="main">
        <el-header>
            <NavMenu></NavMenu>
        </el-header>
        <el-main>
            <router-view/>
        </el-main>
        <el-footer>
            <Footer></Footer>
        </el-footer>
    </el-container>
    </body>

</template>

<script>
    import NavMenu from "../components/public/NavMenu";
    import Footer from "../components/public/Footer";

    export default {
        name: "Main",
        components: {
            NavMenu,
            Footer,
        },
    }
</script>

<style scoped>

    #mainbg{
        height: 100%;
        width: 100%;
        background-size: cover;
        background: url("../assets/background4.jpg") no-repeat fixed center;
        background-size: 100% 100%;
        overflow: auto;
    }


</style>
