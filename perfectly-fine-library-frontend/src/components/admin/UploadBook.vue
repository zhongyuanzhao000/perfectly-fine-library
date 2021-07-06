<template>
    <el-form :model="uploadForm" :rules="rules" ref="form" >
        <el-row>
            <el-col :span="10">
                <el-form-item prop="title">
                    <el-input v-model="uploadForm.title" placeholder="图书标题" clearable size=""></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="2">
            </el-col>
            <el-col :span="10">
                <el-form-item prop="author">
                    <el-input v-model="uploadForm.author" placeholder="图书作者" clearable size=""></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item prop="description">
            <el-input
                    type="textarea"
                    :rows="4"
                    placeholder="请输入图书简介"
                    v-model="uploadForm.description">
            </el-input>
        </el-form-item>
        <el-row>
            <el-col :span="7">
                <el-form-item prop="isbn">
                    <el-input v-model="uploadForm.isbn" placeholder="图书isbn号" clearable size=""></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="7">
                <el-form-item prop="publicationDate">
                    <el-date-picker
                            v-model="uploadForm.publicationDate"
                            type="month"
                            placeholder="选择出版时间">
                    </el-date-picker>
                </el-form-item>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="7">
                <el-form-item prop="price">
                    <el-input v-model="uploadForm.price" placeholder="图书价格" clearable size=""></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item prop="coverURL">
            <el-input v-model="uploadForm.coverURL" placeholder="封面url" clearable size=""></el-input>
        </el-form-item>
        <el-form-item style="width:100%">
            <el-button type="primary" style="width: 40%;background: #505458;border: none" v-on:click="Upload('form')">上传</el-button>
        </el-form-item>

    </el-form>

</template>

<script>
    import {uploadBook} from "../../api";

    export default {
        name: "UploadBook",
        data() {
            return {
                rules: {
                    title: [{required: true, message: '输入不能为空', trigger: 'blur'}],
                    author:[{required: true, message: '输入不能为空', trigger: 'blur'}],
                    description: [{required: true, message: '输入不能为空', trigger: 'blur'}],
                    isbn: [{required: true, message: '输入不能为空', trigger: 'blur'}],
                    publicationDate: [{required: true, message: '请选择时间', trigger: 'blur'}],
                    price: [{required: true, message: '输入不能为空', trigger: 'blur'}],
                },
                checked: true,
                uploadForm: {
                    title: '',
                    author: '',
                    description:'',
                    isbn:'',
                    publicationDate:'',
                    library:'',
                    coverURL:'',
                    price:''
                },
                dialogImageUrl: '',
                dialogVisible: false,
                responseResult: [],
                value: '',
                imageUrl:'',
            }
        },
        methods:{
            Upload(formname){
                this.$refs[formname].validate((valid) => {
                    if (valid) {
                        uploadBook(this.uploadForm.title,this.uploadForm.author,this.uploadForm.description,this.uploadForm.isbn
                        ,this.uploadForm.publicationDate,this.uploadForm.library,this.uploadForm.coverURL,this.uploadForm.price)
                            .then(resp => {
                                if (resp.status === 201){
                                    this.$message.success('上传成功！');
                                    // alert('上传成功');
                                } else {
                                    // alert('上传失败，要不再试试？')
                                    this.$message({message:resp.data.message,type:"error"});
                                }
                            })
                            .catch((error => {
                                this.$message.warning(error.response.data.message)
                            }))
                    }
                })

            },/*
            将来要写：图片上传
            handlePreview(file){
                this.imageUrl = file.url
            },
            handleSuccess(response){
                this.imageUrl = response;
                this.uploadForm.coverURL = this.imageUrl;
                this.$message.warning('上传成功')
            },
            beforeUpload(file){
                const isvalid = (file.type==='image/jpeg')||(file.type==='image/png');
                const isLt2M=file.size/1024/1024<2;
                if(!isvalid){
                    this.$message.error('上传只能是jpg或者是png格式！')
                }
                if(!isLt2M){
                    this.$message.error('上传图片大小不能超过2M！')
                }
                return isvalid&&isLt2M;
            }*/
        },

    }
</script>

<style scoped>

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
