<template>
    <div class="bor_container">
        <h3>图书报损/报失</h3>
        <el-form-item prop="cid">
            <el-input type="text" v-model="username"
                      auto-complete="off" placeholder="请输入借书的用户id"
                      clearable style="width: 500px;margin-top: 30px">
            </el-input>
        </el-form-item>

        <el-form-item>
            <el-button   v-on:click="getBorrowed">查询</el-button>
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

        <el-row>
            <el-col :span="12">
                <el-form-item>
                    <el-radio-group v-model="radio" >
                        <el-radio-button label="1">报损</el-radio-button>
                        <el-radio-button label="2">报失</el-radio-button>
                    </el-radio-group>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item>
                    <el-button type="primary"  round class="submit" v-on:click="submit">提交</el-button>
                </el-form-item>
            </el-col>

        </el-row>


    </div>
</template>

<script>
    import {bookDamage, bookLost, getBookFromBookId, getBorrowedCopies,getOrderState} from "../../api";
    import {ElMessage} from "element-plus";

    export default {
        name: "Damage",
        data(){
            return{
                copyId:'',
                radio:'1',
                username:'',
                copies:[],
                chosenCids:[],
                orderId:0,
                state:'',
                flag:null
            }
        },
        methods:{
            getBorrowed(){
                getBorrowedCopies(this.username)
                    .then((response) =>{
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

            submit(){
                switch (this.radio) {
                    case '1':
                        for (let i = 0; i < this.copies.length;i++) {
                            if (this.chosenCids[i]) {
                                bookDamage(this.copies[i].copyId)
                                    .then((resp) => {
                                        switch (resp.status) {
                                            case 202:
                                                var fine = (this.copies[i].book.price/2).toFixed(2);
                                                this.orderId = resp.data.orderId;
                                                this.state = resp.data.state;
                                                //如果该order的状态不是成功或者失败，就一直查下去
                                                this.flag = setInterval(()=>this.getState(this,fine,i),1000);
                                                break;
                                            case 401:
                                                this.$message({message: resp.data.message, type: "error"});
                                                break;
                                            default:
                                                break;
                                        }
                                    })
                                    .catch((error) => {
                                        this.$message.warning(error.response.data.message);
                                    })
                            }
                    }
                    break;
                    case '2':
                        for (let i = 0; i<this.copies.length;i++) {
                            if (this.chosenCids[i]) {
                                bookLost(this.copies[i].copyId)
                                    .then((resp) => {
                                        switch (resp.status) {
                                            case 202:
                                                var fine = (this.copies[i].book.price/2).toFixed(2);
                                                this.orderId = resp.data.orderId;
                                                this.state = resp.data.state;
                                                //如果该order的状态不是成功或者失败，就一直查下去
                                                this.flag = setInterval(()=>this.getState(this,fine,i),1000);
                                                break;
                                            case 401:
                                                this.$message({message: resp.data.message, type: "error"});
                                                break;
                                            default:
                                                break;
                                        }
                                    })
                                    .catch((error) => {

                                        this.$message.warning(error.response.data.message);
                                    })
                            }
                        }
                    break;
                }
            },

            getState(_this,fine,i){
                getOrderState(_this.orderId)
                .then((resp) => {
                    _this.state = resp.data.state;
                    if (_this.state === 'Approved' || _this.state === 'Rejected'){
                        if (this.state === 'Approved'){
                            ElMessage.warning({
                                message: '图书《'+this.copies[i].book.title+'》遗失报备成功'+
                                    ',您将被罚款'+fine+'元,请尽快登录个人主页缴纳',
                                type: 'warning'
                            });
                        }
                        else if (this.state === 'Rejected') {
                            this.$message.warning("报损失败");
                        }
                        clearInterval(_this.flag);
                    }
                })
            }
        },
    }
</script>

<style scoped>

    .bor_container{
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
    .submit{
        width: 50%;
        height: 50px;
    }
</style>
