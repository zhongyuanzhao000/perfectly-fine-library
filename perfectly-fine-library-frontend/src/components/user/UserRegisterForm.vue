<template>
    <el-form :model="UserRegisterForm" :rules="rules" class="register_container" label-position="left"
             label-width="0px" v-loading="loading" ref="user_register_form" >
        <h3 class="register_title">用户注册</h3>
        <el-form-item prop="username">
            <el-input type="text" v-model="UserRegisterForm.username"
                      auto-complete="off" placeholder="请输入您的学号/学工号" clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input type="password" v-model="UserRegisterForm.password"
                      auto-complete="off" placeholder="请设置您的密码"
                      clearable show-password></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
            <el-input type="password" v-model="UserRegisterForm.rePassword"
                      auto-complete="off" placeholder="请再次确认您的密码"
                      clearable show-password></el-input>
        </el-form-item>
        <el-form-item>
            <el-select v-model="UserRegisterForm.role" placeholder="请选择您的身份">
                <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" id="registerButton" @click="onSubmit">注册</el-button>
        </el-form-item>
        <el-form-item>
            <router-link to="/login/user" id="">已有账户？点击登陆</router-link>
        </el-form-item>
    </el-form>
</template>

<script>
    import { userRegisterFirst, userRegisterSecond} from "../../api";

    export default {
        name: 'Register',
        data () {
            return {
                UserRegisterForm: {
                    username: '',
                    password: '',
                    rePassword: '',
                    role:'',//用户的身份
                    id: '',// 后端生成的id
                    code:'' //用户输入的验证码
                },
                options:[{
                    value: 'TEACHER',
                    label: '教师'
                },{
                    value: 'UNDERGRADUATE',
                    label: '本科生'
                },{
                    value: 'POSTGRADUATE',
                    label: '研究生'
                }],
                rules: {
                    username: [{ required: true, message: '输入不能为空', trigger: 'blur' },
                        { pattern: /^(\d{11}|\d{5})$/, message: '请输入11位的学号或5位的工号' }],
                    password: [{ required: true, message: '输入不能为空', trigger: 'blur' },
                        { min: 6, max: 32, message: '长度为6~32个字符' },
                        { pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![-_.,]+$)[\da-zA-Z-_,.]{6,32}$/, message: '字母、数字、特殊字符（- _）至少包含两种且不含帐号' }],
                    rePassword: [{ required: true, message: '输入不能为空', trigger: 'blur' }]
                },
                checked: true,
                loading: false // 不知道这里啥意思
            }
        },
        methods: {
            onSubmit() {
                this.register();
            },


            register() { //注册的第一阶段
                this.$refs["user_register_form"].validate((valid) => {
                    if (valid) {
                        userRegisterFirst(this.UserRegisterForm.username,this.UserRegisterForm.password,this.UserRegisterForm.role)
                        .then(resp => {
                            switch (resp.status) {
                                //mock只能200，部署时候记得改成202
                                case 202:
                                    this.UserRegisterForm.id = resp.data.verificationId;
                                    this.checkVerification();
                                    break;
                                case 400:
                                    this.$message({message:'表单错误（表单不完整或用户名/密码格式错误）',type:'error'});
                                    break;
                                case 409:
                                    this.$message({message:resp.data.message,type:'error'});
                                    break;
                                default:
                                    break;
                            }
                        })
                            // !! to-do  如何写catch中的内容
                        .catch((error) => {
                            this.$message.warning(error.response.data.message);
                        })
                    }
                })
            },

            checkVerification() {
                this.$prompt('请输入学邮收到的验证码','提示',{
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(({value}) => {
                    this.UserRegisterForm.code = value;
                    userRegisterSecond(this.UserRegisterForm.id,this.UserRegisterForm.code)
                        .then(response => {
                            switch (response.status) {
                                case 201:
                                    this.$message.success('注册成功！');
                                    this.$router.replace('/login/user');
                                    break;
                                case 401:
                                    this.$message({message:'验证码错误',type:'error'});
                                    break;
                                default:
                                    break;

                            }
                        })
                        .catch((error) => {
                            this.$message.warning(error.response.data.message);
                        })
                })
            }
        }
    }
</script>

<style scoped>


    .register_container{
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

    .register_title{
        margin: 0px auto 40px auto;
        text-align: center;
        color: #505458;
    }

    #registerButton{
        width: 40%;
        background: #afb4db;
        border: none;
    }

    #registerButton:hover{
        background-color: #4682B4;
    }
</style>
