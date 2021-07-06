<template>
    <el-select
            v-model="titleSearch"
            filterable
            remote
            reserve-keyword
            placeholder="搜索添加副本的图书标题"
            :remote-method="remoteMethod1"
            :loading="loading"
            @change="changeIsbn">
        <el-option
                v-for="item in options"
                :key="options.indexOf(item)"
                :value="item.title">
        </el-option>
    </el-select>

    <el-select
            v-model="isbnSearch"
            filterable
            remote
            reserve-keyword
            placeholder="搜索添加副本的图书isbn号"
            :remote-method="remoteMethod2"
            :loading="loading"
            @change="changeTitle">
        <el-option
                v-for="item in options"
                :key="item.isbn"
                :value="item.isbn">
        </el-option>
    </el-select>

    <el-tooltip content="请选择上传该副本的数量" placement="bottom" effect="light">
        <el-input-number v-model="num"  :min="1" :max="1000" ></el-input-number>
    </el-tooltip>

    <el-form-item style="width:100%">
        <el-button type="primary" style="width: 40%;background: #505458;border: none" v-on:click="Upload">上传</el-button>
    </el-form-item>
</template>

<script>
    import {getBookFromTitleIsbn, uploadCopy} from "../../api";

    export default {
        name: "UploadCopy",
        methods:{
            remoteMethod1(query) {
                if(query!==''){
                    this.titleSearch=query
                    getBookFromTitleIsbn(query,'')
                        .then((response)=>{
                            switch (response.status) {
                                case 200:
                                    this.options=response.data;
                                    break;
                                default:
                                    this.$message({message: response.data.message,type:"error"});
                                    break;
                            }
                        })
                        .catch((error) => {
                            this.$message.warning(error.response.data.message);
                        })
                }
            },

            remoteMethod2(query) {
                if(query!==''){
                    this.isbnSearch=query
                    getBookFromTitleIsbn('',query)
                        .then((response)=>{
                            switch (response.status) {
                                case 200:
                                    this.options=response.data;
                                    break;
                                default:
                                    this.$message({message: response.data.message,type:"error"});
                                    break;
                            }
                        })
                        .catch((error) => {
                            this.$message.warning(error.response.data.message);
                        })
                }
            },

            changeTitle(){
                for(let item of this.options){
                    if(item.isbn === this.isbnSearch){
                        this.titleSearch = item.title;
                        this.bidSearch = item.bookId;
                        break;
                    }
                }
            },

            changeIsbn(){
                for(let item of this.options){
                    if(item.title === this.titleSearch){
                        this.isbnSearch = item.isbn;
                        this.bidSearch = item.bookId;
                        break;
                    }
                }
            },

            Upload(){
                uploadCopy(this.bidSearch,this.num)
                    .then((response)=>{
                        switch (response.status) {
                            case 201:
                                this.$message.success("上传成功")
                                break;
                            default:
                                this.$message({message:response.data.message,type:"error"});
                                break;
                        }
                    })
                    .catch((error) => {
                        this.$message.warning(error.response.data.message);
                    })
            },

        },
        data(){
            return {
                num: 1,
                options:[],
                bidSearch:'',
                titleSearch:'',
                isbnSearch:'',
                selectedNum:0,
                lid:window.localStorage.getItem('lid')
            }
        }
    }
</script>

<style scoped>

</style>
