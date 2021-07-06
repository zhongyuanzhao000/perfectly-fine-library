<template>
    <div id="formbg" style="height: 100%;width: 100%">
        <router-view/>
    </div>
</template>

<script>
    export default {
        name: "Form"
    }
</script>

<style scoped>
    #formbg{
        height: 100%;
        width: 100%;
        background-size: cover;

        background: url("../assets/background4.jpg") no-repeat fixed center;
        background-size: 100% 100%;
        overflow: auto;
    }

</style>
