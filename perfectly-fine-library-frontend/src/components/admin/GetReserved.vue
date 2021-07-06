<template>
    <div class="get_container">

        <h3>取预约书</h3>
        <el-form-item prop="username" :rules="rules">
            <el-input type="text" v-model="username"
                      auto-complete="off" placeholder="请输入预约图书的读者学工号"
                      clearable></el-input>
        </el-form-item>
        <el-form-item>
            <el-button   v-on:click="getReserved">查询</el-button>
        </el-form-item>

        <el-card class="box-card" v-for="i in copies.length" :key="i">
            <div>
                {{'《' + copies[i-1].book.title+'》'}}
                <el-checkbox v-model="chosenCids[i-1]"></el-checkbox>
            </div>
            <div>
                {{copies[i-1].copyId}}
            </div>
        </el-card>

        <el-form-item>
            <el-button type="primary"  v-on:click="borrow">借书</el-button>
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
        </el-alert>

    </div>
</template>

<script>
    import {borrowBook, getBookFromBookId,getReservedCopies,getOrderState} from "../../api";

    export default {
        name: "GetReserved",
        data(){
            return {
                username:'',
                chosenCids:[],//得到的cids中哪些书籍被确认借用
                copies:[],
                successTitle:[],
                failTitle:[],
                bookId:'',
                title:'',
                orderId:0,
                state:'Creating',
                flag:null, //定时器
                rules:{
                    username: [{ required: true, message: '学号/学工号不能为空！', trigger: 'blur' }]
                }
            }
        },
        methods:{
            getReserved(){
                getReservedCopies(this.username)
               .then((response)=>{
                    switch (response.status) {
                        case 200:
                            this.copies = response.data;
                            if(this.copies.length > 0) {
                                for (let i = 0; i < this.copies.length; i++) {
                                    this.chosenCids[i] = false;
                                    getBookFromBookId(this.copies[i].bookId)
                                    .then((response) => {
                                        switch (response.status) {
                                            case 200:
                                                this.copies[i].book = response.data;
                                                break;
                                        }
                                    })
                                }
                            } else
                            {
                                this.$message.warning("对不起，该用户没有预约中的书籍！");
                            }
                            break;
                        default:
                            this.$message({message:response.data.message,type:"error"});
                    }
                })
                .catch((error => {
                    this.$message.warning(error.response.data.message)
                }))
            },

            borrow(){

                for (let i = 0; i < this.copies.length;i++) {
                    if (this.chosenCids[i]) {

                        borrowBook(this.copies[i].copyId, this.username)
                            .then((response) => {
                                switch (response.status) {
                                    case 202:
                                        this.orderId = response.data.orderId;
                                        this.state = response.data.state;
                                        //如果该order的状态不是成功或者失败，就一直查下去
                                        this.flag = setInterval(()=>this.getState(this,i),1000);
                                        break;
                                    case 401:
                                        // alert('当前用户没有权限');
                                        this.failTitle.push(this.copies[i].book.title);
                                        this.$message({message: response.data.message, type: "error"});
                                        break;
                                    case 404:
                                        // alert('找不到编号为'+c+'的副本');
                                        this.failTitle.push(this.copies[i].book.title);
                                        this.$message({message: response.data.message, type: "error"});
                                        break;
                                    default:
                                        this.failTitle.push(this.copies[i].book.title);
                                        break;
                                }
                            })
                            .catch((error) => {
                                this.failTitle.push(this.copies[i].book.title);
                                this.$message.warning(error.response.data.message)
                            })
                    }
                }
            },

            getState(_this,i){
                getOrderState(_this.orderId)
                .then((resp) => {
                    _this.state = resp.data.state;
                    if (_this.state === 'Approved' || _this.state === 'Rejected'){
                        if (this.state === 'Approved'){
                            this.$message.success('取预约成功!');
                            _this.successTitle.push(this.copies[i].book.title);
                        } else if(this.state === 'Rejected'){
                            this.$message.error('取预约失败!');
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

    .get_container{
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
