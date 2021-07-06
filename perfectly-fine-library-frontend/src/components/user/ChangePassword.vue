<template>
    <el-form :model="changeForm" :rules="rules" class="change_container" label-position="left"
             label-width="0px" v-loading="loading" ref="changePassForm" >
        <h3 class="">修改密码</h3>
        <el-form-item prop="password">
            <el-input type="password" v-model="changeForm.password"
                auto-complete="off" placeholder="请输入您原来的密码"
                clearable show-password></el-input>
        </el-form-item>
        <el-form-item prop="newPassword">
            <el-input type="password" v-model="changeForm.newPassword"
                auto-complete="off" placeholder="请输入您的新密码"
                clearable show-password></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
            <el-input type="password" v-model="changeForm.rePassword"
                auto-complete="off" placeholder="请再次确认您的新密码"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" id="changeButton" @click="onSubmit">修改</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {changePassword} from "../../api";

    export default {
        name: "ChangePassword",
        data(){
            return{
                changeForm:{
                    password:'',
                    newPassword:'',
                    rePassword:''
                },

                rules:{
                    password:[{required: true,message:'输入不能为空',trigger:'blur'}],
                    newPassword: [{ required: true, message: '输入不能为空', trigger: 'blur' },
                        { min: 6, max: 32, message: '长度为6~32个字符' },
                        { pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![-_.,]+$)[\da-zA-Z-_,.]{6,32}$/, message: '字母、数字、特殊字符（- _）至少包含两种且不含帐号' }],
                    rePassword: [{ required: true, message: '输入不能为空', trigger: 'blur' }]
                },
                checked:true,
                loading:false
            }
        },

        methods:{
            // to do : 检查表单是否符合要求：不为空；新密码不包含帐号；再次输入的密码与新密码一致
            // 再用get方法进行修改
            onSubmit() {
                this.change()
            },
            change(){
                    this.$refs["changePassForm"].validate((valid) =>{
                        if (valid){
                            changePassword(this.changeForm.rePassword)
                                .then((response) =>{
                                    switch (response.status) {
                                        case 200:
                                            // alert('修改成功，请重新登陆！');
                                            this.$message.success('修改成功，请重新登陆！');
                                            this.$router.replace('/login');
                                            break;
                                        case 400:
                                            this.$message({message:response.data.message,type:"error"});
                                            break;
                                        case 401:
                                            this.$message({message:response.data.message,type:"error"});
                                            break;
                                    }
                                })
                                .catch((error) => {
                                    this.$message.warning(error.response.data.message)
                                })
                        }
                    })
                }
            }
    }
</script>

<style scoped>
    .change_container{
    border-radius: 15px;
    background-clip: padding-box;
    margin: 15px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    opacity: 0.85;
    }

    #changeButton{
        width: 40%;
        background: #afb4db;
        border: none;
    }

    #changeButton:hover{
        background-color: #4682B4;
    }
</style>
