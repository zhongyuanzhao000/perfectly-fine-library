<template>
    <div class="return_container">

        <h3>现场还书</h3>
        <el-tooltip content="请选择需要还书的数量" placement="bottom"
                    effect="light" style="margin-bottom: 30px">
            <div>
                <p style="color: darkgray">
                    请选择需要还书的数量：
                    <el-input-number v-model="num"  :min="1" :max="10" ></el-input-number>
                </p>
            </div>
        </el-tooltip>

        <el-form-item prop="isbn" v-for="i in num" :key="i">
            <el-input type="text" v-model="returnForm.isbns[i-1]"
                      auto-complete="off" placeholder="请输入归还的副本唯一标识" clearable></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" v-on:click="submitReturn">提交</el-button>
        </el-form-item>
        <el-alert v-for="title in successTitle" :key="title"
                  type="success"
                  center
                  show-icon>《{{title}}》归还成功！
        </el-alert>
        <el-alert v-for="title in failTitle" :key="title"
                  type="error"
                  center
                  show-icon>《{{title}}》归还失败！
        </el-alert>

    </div>
</template>

<script>
    import {getBookFromBookId, getCopyFromIsbn, returnBook,getOrderState} from "../../api";
    import { ElMessage } from 'element-plus'
    export default {
        name: "Return",
        data(){
            return{
                returnForm:{
                    isbns:[]
                },
                successTitle:[],
                failTitle: [],
                num:1,
                bookId:'',
                title:'',
                expiration:'',
                price:'',
                copyId:'',
                orderId: 0,
                state:'',
                flag: null
            }
        },
        methods:{
            submitReturn() {

           //     alert(date);
                for (let c of this.returnForm.isbns) {
                    getCopyFromIsbn(c)
                        .then((response) => {
                            switch (response.status) {
                                case 200:
                                    this.bookId = response.data[0].bookId;
                                    this.copyId = response.data[0].copyId;
                                    this.expiration = response.data[0].expiration+8*60*60*1000;
                                    getBookFromBookId(this.bookId)
                                        .then((response) => {
                                            switch (response.status) {
                                                case 200:
                                                    this.title=response.data.title;
                                                    this.price=response.data.price;
                                                    break;
                                                default:
                                                    break;
                                            }
                                        })
                                        .catch((error) => {
                                            this.$message.warning(error.response.data.message)
                                        });

                                    returnBook(this.copyId)
                                        .then((response) => {
                                            switch (response.status) {
                                                case 202:

                                                    this.orderId = response.data.orderId;
                                                    this.state = response.data.state;
                                                    this.flag = setInterval(() => this.getState(this),1000);
                                                    // this.successTitle.push(this.title);
                                                    break;
                                                case 401:
                                                    // alert('当前用户没有权限');
                                                    this.failTitle.push(this.title);
                                                    this.$message({message:response.data.message,type:"error"});
                                                    break;
                                                case 404:
                                                    // alert('找不到编号为'+c+'的副本');
                                                    this.failTitle.push(this.title);
                                                    this.$message({message:response.data.message,type:"error"});
                                                    break;
                                                default:
                                                    this.failTitle.push(this.title);
                                                    this.$message({message:response.data.message,type:"error"});
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
                            let date=new Date();
                            if(this.expiration<date){

                                let ex = (date-this.expiration) / 1000;
                                let ex_day =  Math.floor(ex / (86400));
                                let ex_hour =  Math.floor((ex % 86400) / 3600);
                                let ex_min =  Math.floor((ex % 3600) / 60);
                                let ex_send = Math.floor(ex % 60);
                                let fine=(this.price/4).toFixed(2);
                                ElMessage.warning({
                                    message: '图书《'+this.title+'》归还已经超期'+ex_day+'天'+ex_hour+'小时'+ex_min+'分钟'+ex_send+'秒'+
                                        ',将被罚款'+fine+'元,请读者尽快登录个人主页缴纳',
                                    type: 'warning'
                                });
                            }
                        }
                        else if (this.state === 'Rejected'){
                            this.$message.warning('还书失败');
                        }
                        clearInterval(_this.flag);
                    }
                    _this.state = resp.data.state;
                })
            }
        }
    }
</script>

<style scoped>

    .return_container{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 65px auto;
        padding: 35px 35px 15px 35px;
        background: #fff;
        width: 60%;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        opacity: 0.85;
    }
</style>
