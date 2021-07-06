<template>
    <div id="bor_container"><!--
        <el-form :model="borrowForm" :rules="rules" label-position="left"
                 label-width="0px" v-loading="loading" :ref="borrowForm">-->

            <h3>现场借书</h3>
            <el-form-item prop="username">
                <el-input type="text" v-model="borrowForm.username"
                          auto-complete="off" placeholder="请输入需要借书的读者学工号"
                          clearable style="width: 500px">
                </el-input>
            </el-form-item>

            <el-tooltip content="请选择需要借书的数量" placement="bottom"
                        effect="light" style="margin-top: 30px">
                <div>
                    <p style="color: darkgray">请选择需要借书的数量：
                        <el-input-number v-model="num"  :min="1" :max="10" ></el-input-number>
                    </p>
                </div>
            </el-tooltip>

            <el-form-item prop="isbn" v-for="i in num" :key="i">
                <el-input type="text" v-model="borrowForm.isbns[i-1]"
                          auto-complete="off" placeholder="请输入借用的副本唯一标识"
                          clearable style="width: 500px;margin-top: 30px">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary"  v-on:click="borrow">提交</el-button>
            </el-form-item>

            <el-alert v-for="title in successTitle" :key="title"
                      type="success"
                      center
                      show-icon>《{{title}}》借阅成功！
            </el-alert>
            <el-alert v-for="title in failTitle" :key="title"
                      type="error"
                      center
                      show-icon>《{{title}}》借阅失败！
            </el-alert><!--
        </el-form>-->
    </div>
</template>

<script>
    import {borrowBook, getBookFromBookId,getCopyFromIsbn,getOrderState} from "../../api";

    export default {
        name: "Borrow",
        data() {
            return {
                borrowForm: {
                    isbns: [],
                    username: ''
                },
                successTitle: [],
                failTitle: [],
                num: 1,
                bookId: '',
                title: '',
                copyId: '',
                orderId: 0,
                state: '',
                flag: null
            }
        },
        methods: {
            borrow() {
                for (let c of this.borrowForm.isbns) {
                    getCopyFromIsbn(c)
                        .then((response) => {
                            switch (response.status) {
                                case 200:
                                    for (let i of response.data) {
                                        this.bookId = i.bookId;
                                        this.copyId = i.copyId;
                                    }
                                    getBookFromBookId(this.bookId)
                                        .then((response) => {
                                            switch (response.status) {
                                                case 200:
                                                    this.title = response.data.title;
                                                    break;
                                            }
                                        })
                                        .catch((error) => {
                                            this.$message.warning(error.response.data.message)
                                        });

                                    borrowBook(this.copyId, this.borrowForm.username)
                                        .then((response) => {
                                            switch (response.status) {
                                                case 202:
                                                    this.orderId = response.data.orderId;
                                                    this.state = response.data.state;
                                                    this.flag = setInterval(()=>this.getState(this),1000);
                                                    break;
                                                case 401:
                                                    // alert('当前用户没有权限');
                                                    this.$message({message: response.data.message, type: "error"});
                                                    this.failTitle.push(this.title);
                                                    break;
                                                case 404:
                                                    // alert('找不到编号为'+c+'的副本');
                                                    this.$message({message: response.data.message, type: "error"});
                                                    this.failTitle.push(this.title);
                                                    break;
                                                default:
                                                    this.failTitle.push(this.title);
                                                    break;
                                            }
                                        })
                                        .catch((error) => {
                                            this.failTitle.push(this.title);
                                            this.$message.warning(error.response.data.message)
                                        });

                                    break;
                            }
                        })
                        .catch((error) => {
                            this.$message.warning(error.response.data.message)
                        });


                }
            },

            getState(_this){
                getOrderState(_this.orderId)
                .then((resp) => {
                    _this.state = resp.data.state;
                    if (_this.state === 'Approved' || _this.state === 'Rejected'){
                        if (this.state === 'Approved'){
                            _this.successTitle.push(_this.title);
                        }
                        else if (this.state === 'Rejected'){
                            this.$message.warning('借阅失败！');
                        }
                        clearInterval(_this.flag);
                    }
                    //_this.state = resp.data.state;
                })
            }
        }
    }
</script>

<style scoped>

    #bor_container{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 65px auto;
        padding: 35px 35px 15px 35px;
        background: #fff;
        width: 70%;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        opacity: 0.85;
    }
</style>
