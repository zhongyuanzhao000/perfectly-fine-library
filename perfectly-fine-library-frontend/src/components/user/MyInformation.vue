<template>
    <el-card>
        <el-description title="用户信息" >
            <el-description-item label="我的id" :span="5" :value = username></el-description-item>
            <el-table-column label="我的身份" :span="3" :value = role></el-table-column>
            <el-description-item label="我的邮箱" :span="7" :value = email></el-description-item>
            <el-description-item label="我的密码" :span="5" value = "********"></el-description-item>
            <el-button type="primary" plain round size="mini" class="changePass" v-on:click="changePassword()">修改</el-button>
            <el-description-item label="最大借阅数量" :span="8" :value="max_borrow_number"></el-description-item>
            <el-description-item label="已经借阅数量" :span="8" :value="boNumber"></el-description-item>
            <el-description-item label="已经预约数量" :span="8" :value="reNumber"></el-description-item>
            <el-description-item label="最大可借阅时长" :span="12" :value="bo_day+'天'+bo_hour+'时'+bo_min+'分'+bo_send+'秒'"></el-description-item>
            <el-description-item label="最大可预约时长" :span="12" :value="re_day+'天'+re_hour+'时'+re_min+'分'+re_send+'秒'"></el-description-item>
            <el-description-item label="信用分数" :span="4" :value="credit"></el-description-item>
            <el-button type="text" @click="dialogVisible=true">查看信用规则</el-button>

            <el-dialog title="信用分规则" v-model="dialogVisible" :before-close="handleClose">
                <span>
                    用户的初始信用分为100，用户的违规行为会扣除相应的信用分，具体为：预约超期扣10分；借阅超期扣20分；图书损坏扣30分；图书遗失扣40分。用户信用分低于50分后，不能预约（但可以取已经预约的书籍）；低于0分后，不能现场借书和取预约书。
                    您可以去图书馆向图书管理员提交重置信用申请！
                </span>
                <template #footer>
                    <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
                    </span>
                </template>

            </el-dialog>
        </el-description>
    </el-card>

    <el-card class="box-card">
       <span>当前欠款：{{fine}}元</span>
        <el-button v-if="fine!=='0'" v-on:click="payMoney()" type="primary" class="pay">一键交款<i class="el-icon-s-finance"></i></el-button>
    </el-card>

</template>

<script>
    import {getUserInfo, getConfig, payBill} from "../../api";
    import ElDescription from "../public/ElDescription";
    import ElDescriptionItem from "../public/ElDescriptionItem";


    export default {
        name: "MyInformation",
        data() {
            return{
                username:'',
                role:'',
                fine:0,//用户罚款
                email: '',
                boNumber:0,// 该用户的借书数
                reNumber:0,// 该用户的预约数
                credit:0,
                max_borrow_number:0,
                borrow_expiration:0,
                reserve_expiration:0,
                bo_day:0,
                bo_hour: 0,
                bo_min: 0,
                bo_send:0,
                re_day: 0,
                re_hour:0,
                re_min: 0,
                re_send: 0,
                orders:[],
                dialogVisible: false//信用规则是否能被看到，初始应该不能被看到
            }
        },
        components:{
            ElDescription,
            ElDescriptionItem,
        }
        ,
        mounted() {
            this.getMyInformation()
        },

        methods:{
            changePassword(){
              this.$router.push({path:'/user/changePassword'});
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(()=>{
                        done();
                    })
                    .catch(() => {});
            },
            getMyInformation() {
                getUserInfo()
                .then(resp => {
                    switch (resp.status) {
                        case 200:
                            this.username = resp.data.username;
                            this.role= resp.data.role;
                            this.reNumber = resp.data.reservedCopyIds.length;
                            this.boNumber = resp.data.borrowedCopyIds.length;
                            this.email = this.username+"@fudan.edu.cn";
                            this.fine = resp.data.fine;
                            this.credit = resp.data.credit;
                            getConfig()
                                .then(response => {
                                    switch (response.status) {
                                        case 200:
                                            for(let i of response.data){

                                                if(i.role===this.role){
                                                    this.max_borrow_number = i.maxBorrowNumber;
                                                    this.borrow_expiration = (i.borrowExpiration) / 1000;
                                                    this.reserve_expiration = (i.reserveExpiration) / 1000;
                                                }
                                            }
                                            this.bo_day =  Math.floor(this.borrow_expiration / 86400);
                                            this.bo_hour =  Math.floor(((this.borrow_expiration) % 86400) / 3600);
                                            this.bo_min = Math.floor( ((this.borrow_expiration) % 3600) / 60);
                                            this.bo_send = this.borrow_expiration % 60;

                                            this.re_day =  Math.floor(this.reserve_expiration / 86400);
                                            this.re_hour =  Math.floor(((this.reserve_expiration) % 86400) / 3600);
                                            this.re_min =  Math.floor(((this.reserve_expiration) % 3600) / 60);
                                            this.re_send = this.reserve_expiration % 60;

                                            break;
                                        case 401:
                                            break;
                                        default:
                                            break;
                                    }
                                })


                            break;
                        case 401:
                            this.$message({message:resp.data.message,type:"error"});
                            break;
                        default:
                            break;
                    }
                });



            },
            payMoney(){
                payBill()
                    .then((resp) => {
                        switch (resp.status) {
                            case 200:
                                this.$message({message: '付款成功！'});
                                this.$router.go(0)
                                break;
                            case 409:
                                this.$message({message: resp.data.message,type:"error"});
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
    }
</script>

<style scoped>
    .pay{
        margin-left: 10%;
    }
    .changePass{
        position: relative;
        right: 5%;
        bottom: 10px;
    }
</style>
