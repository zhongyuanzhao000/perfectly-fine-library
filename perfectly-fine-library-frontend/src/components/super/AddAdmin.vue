<template>
    <el-form :model="addForm" :rules="rules" label-position="left" class="add_container"
             label-width="0px" v-loading="loading" ref="form1" >
        <h3 class="add_title">新增管理员</h3>
        <h4>仅超级管理员有此权限</h4>
        <el-form-item prop="username">
            <el-input type="text" v-model="addForm.username"
                      auto-complete="off" placeholder="请输入管理员的账号" clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input type="password" v-model="addForm.password"
                      auto-complete="off" placeholder="请设置该管理员密码"
                      clearable show-password></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
            <el-input type="password" v-model="addForm.rePassword"
                      auto-complete="off" placeholder="请再次确认密码"
                      clearable show-password></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" id="addButton" v-on:click="addAdmin">新增</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {addAdmin} from "../../api";

    export default {
        name: "addAdmin",
        data(){
            return{
                addForm:{
                    username:'',
                    password:'',
                },
                rules: {
                    username: [{required: true, message: '输入不能为空', trigger: 'blur'},
                        {min:6,max:32,message: '长度为6~32个字符'},
                        {message: '只能包含数字、字母和特殊字符（-_），且只能以字母或_开头',pattern:/^[A-Za-z_][A-Za-z0-9_/-]*$/}],
                    password: [{ required: true, message: '密码不能为空！', trigger: 'blur' },
                        { min: 6, max: 32, message: '长度为6~32个字符' },
                        { pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![-_.,]+$)[\da-zA-Z-_,.]{6,32}$/, message: '字母、数字、特殊字符（- _）至少包含两种且不含帐号' }],
                    rePassword: [{ required: true, message: '密码不能为空！', trigger: 'blur' }],
                },
                checked: true,
                loading: false // 不知道这里啥意思
            }
        },
        methods:{
            addAdmin() {
                /**
                if (this.addForm.username === '' || this.addForm.password === '' || this.addForm.rePassword === '') {
                    // alert('请将信息填写完整！')
                    this.$message.warning('请将信息填写完整！')
                } else if (this.addForm.password.includes(this.addForm.username)) {
                    // alert('密码不能包含帐号，请重新输入！')
                    this.$message.warning('密码不能包含帐号，请重新输入！')
                } else if (this.addForm.rePassword !== this.addForm.password) {
                   // alert('两次密码输入不一致，请重新输入！')
                    this.$message.warning('两次密码输入不一致，请重新输入！')
                } else {
                */
                    this.$refs["form1"].validate((valid) => {
                        if (valid){
                            addAdmin(this.addForm.username,this.addForm.password)
                                .then((response) => {
                                    switch (response.status) {
                                        case 201:
                                            // alert("注册成功");
                                            this.$message.success('添加管理员成功');
                                            this.$router.push('/');
                                            break;
                                        case 400:
                                            this.$message({message: response.data.message, type: "error"});
                                            break;
                                        case 401:
                                            this.$message({message: response.data.message, type: "error"});
                                            break;
                                        case 403:
                                            this.$message({message: response.data.message, type: "error"});
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


    .add_container{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 65px auto;
        width: 350px;
        padding: 35px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        opacity: 0.85;
    }

    #addButton{
        width: 40%;
        background: #afb4db;
        border: none;
    }

    #addButton:hover{
        background-color: #4682B4;
    }


</style>
