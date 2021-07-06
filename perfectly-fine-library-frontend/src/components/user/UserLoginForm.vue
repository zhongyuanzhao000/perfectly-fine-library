<template>
    <el-form :model="userLoginForm" :rules="rules" class="loginContainer" label-position="left"
             label-width="0px" v-loading="loading" ref="user_login_form">
        <h3>用户登陆</h3>
        <el-form-item prop="username">
            <el-input v-model="userLoginForm.username" placeholder="请输入学号/学工号" clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input v-model="userLoginForm.password" placeholder="请输入密码" clearable show-password></el-input>
        </el-form-item>
        <el-form-item>
            <!-- to do 实现记住密码 -->
            <el-checkbox v-model="checked">记住密码</el-checkbox>
        </el-form-item>
        <el-form-item>
            <el-button v-on:click="login" id="loginButton">登陆</el-button>
        </el-form-item>
        <el-button type="text" @click="toRegister" style="color: darkslateblue;" class="jmp">没有帐号？点此注册</el-button>
        <el-button type="text" @click="toAdminLogin" style="margin-left: 100px;color: darkslateblue" class="jmp">切换到管理员登陆</el-button>
    </el-form>
</template>

<script>
    import {userLogin} from "../../api";

    export default {
        name: 'login',
        data () {
            return {
                userLoginForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [{ required: true, message: '学号/学工号不能为空！', trigger: 'blur' }],
                    password: [{ required: true, message: '密码不能为空！', trigger: 'blur' }]
                },
                checked: true, // ？
                responseResult: [] // ?
            }
        },
        methods: {
            onSubmit() {
                this.login()
            },

            login () {
                this.$refs['user_login_form'].validate((valid) => {
                    if (valid){
                        userLogin(this.$data.userLoginForm)
                            .then((response) =>{
                                switch (response.status) {
                                    case 200:
                                        window.sessionStorage.setItem("token",response.headers.authorization);
                                        window.localStorage.setItem("role","user");
                                        this.$message.success('登陆成功！');
                                        this.$router.replace("/");
                                        break;
                                    case 401:
                                        //this.$message({message:'用户名或密码错误',type:'error'});
                                        break;
                                    default:
                                        break;
                                }
                            })
                            .catch((error) => {
                                this.$message.warning(error.response.data.message);
                            });
                    }
                })
            },

            toRegister(){
                this.$router.push({
                    path:'/register/user'
                })
            },
            toAdminLogin(){
                this.$router.push({
                    path:'/login/admin'
                })
            }
        }
    }
</script>

<style scoped>

    .loginContainer{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 90px auto;
        width: 30%;
        padding: 35px 35px 15px 35px;
        background: #F5F5F5 ;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        position: center;
        opacity: 0.85;
    }

    #loginButton{
        width: 40%;
        background: #afb4db;
        border: none;
    }

    #loginButton:hover{
        background-color: #4682B4;
    }
</style>
