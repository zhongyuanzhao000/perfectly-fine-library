<template>
    <el-container class="uploadContainer">
        <el-main>
            <el-form>
            <el-tabs type="border-card">
                <el-tab-pane label="上传图书">
                    <UploadBook></UploadBook>
                </el-tab-pane>
                <el-tab-pane label="添加副本" >
                    <UploadCopy></UploadCopy>
                </el-tab-pane>
            </el-tabs>
            </el-form>
        </el-main>

    </el-container>
</template>

<script>
    import UploadBook from "./UploadBook";
    import UploadCopy from "./UploadCopy";
    export default {
        name: "Upload",
        components: {UploadCopy, UploadBook},
    }
</script>

<style scoped>
    .uploadContainer{
        border-radius: 15px;
        background-clip: padding-box;

        margin: 50px auto 90px;
        width: 70%;
        padding: 35px 35px 15px 35px;
        background: #F5F5F5 ;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        position: center;
        opacity: 0.85;
    }

    .el-input{
        float: left;
        width: 100%;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
</style>
