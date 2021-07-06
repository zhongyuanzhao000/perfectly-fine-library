<template>
    <el-form :model="adminLoginForm" :rules="rules" class="admin_container"
             label-width="0px" v-loading="loading" label-position="left" ref="admin_login_form">
        <h3>管理员登陆</h3>
        <el-form-item prop="username">
            <el-input v-model="adminLoginForm.username" placeholder="请输入管理员帐号" clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input v-model="adminLoginForm.password" placeholder="请输入密码" show-password clearable></el-input>
        </el-form-item>
        <el-form-item>
            <!-- to do 实现记住密码 -->
            <el-checkbox v-model="checked">记住密码</el-checkbox>
        </el-form-item>
        <el-form-item prop="library">
            <el-select v-model="adminLoginForm.library" filterable placeholder="请选择上班的分馆">
                <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button v-on:click="login" id="login_button">登陆</el-button>
        </el-form-item>
        <el-button type="text" @click="toUserLogin" style="margin-left: 100px;color: darkslateblue" class="jmp">切换到用户登陆</el-button>
    </el-form>
</template>

<script>

    import {adminLogin} from "../../api";

    export default {
        name: 'AdminLogin',
        data () {
            return {
                adminLoginForm: {
                    username: '',
                    password: '',
                    library: ''
                },
                rules: {
                    username: [{ required: true, message: '用户名不能为空！', trigger: 'blur' }],
                    password: [{ required: true, message: '密码不能为空！', trigger: 'blur' }],
                    library:[{ required: true, message: '请选择图书馆', trigger: 'blur' }]
                },
                checked: true,
                options: [{
                    label:"邯郸",
                    value:'HD',
                },{
                    label:"枫林",
                    value:'FL',
                },{
                    label:"张江",
                    value:'ZJ',
                },{
                    label:"江湾",
                    value:'JW',
                }],
            }
        },



        methods: {

            login(){
                this.$refs['admin_login_form'].validate((valid) => {
                    if (valid){
                        adminLogin(this.$data.adminLoginForm,this.adminLoginForm.library)
                            .then((response)=>{
                                switch (response.status) {
                                    case 200:
                                        window.sessionStorage.setItem("token",response.headers.authorization);
                                        window.localStorage.setItem("role","admin");
                                        window.localStorage.setItem("lid",this.adminLoginForm.library);
                                        if(response.data.role==='SUPERADMIN'){
                                            window.localStorage.setItem("role","super");
                                        }
                                        this.$message.success('管理员登陆成功！');
                                        this.$router.replace('/');
                                        break;
                                    case 400:
                                        this.$message({message:'表单错误。可能是图书馆错误',type:"error"});
                                        break;
                                    case 401:
                                        this.$message({message:'认证失败',type:"error"});
                                        break;
                                }
                            })
                            .catch((error) => {
                                this.$message.warning(error.response.data.message)
                            });
                    }
                })
            },

            toUserLogin(){
                this.$router.push({
                    path:'/login/user'
                })
            }
        }
    }
</script>

<style scoped>

    .admin_container{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 70px auto;
        width: 30%;
        padding: 35px 35px 15px 35px;
        background: #F5F5F5 ;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        position: center;
        opacity: 0.85;
    }

    #login_button{
        width: 40%;
        background: #afb4db;
        border: none;
    }

    #login_button:hover{
        background-color: #4682B4;
    }

</style>
