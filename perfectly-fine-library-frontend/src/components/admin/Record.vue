<template>
    <el-form class="search_container">
    <el-row>
        <el-col :span="12">
            <el-form-item>
                <el-input class="input"
                          placeholder="请输入检索的副本号或用户名"
                          v-model="input" clearable>
                </el-input>
            </el-form-item>
        </el-col>
        <el-col :span="6">
            <el-form-item>
                <el-select v-model="way" placeholder="选择检索方式">
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-col>
        <el-col :span="6">
            <el-form-item>
                <el-button class="button" type="primary"
                           icon="el-icon-search" @click="getOrder"
                           >搜索</el-button>
            </el-form-item>
        </el-col>

        </el-row>


    </el-form>

    <el-table :data="orders" stripe empty-text="-">
        <el-table-column prop="copyId" label="副本编号"></el-table-column>
        <el-table-column prop="operation" label="操作名称"></el-table-column>
        <el-table-column prop="username" label="操作用户"></el-table-column>
        <el-table-column prop="adminUsername" label="操作管理员" ></el-table-column>
        <el-table-column prop="library" label="操作图书馆" ></el-table-column>
        <el-table-column prop="timestamp" label="操作时间"></el-table-column>
        <el-table-column prop="fine" label="造成罚款" ></el-table-column>
        <el-table-column prop="credit" label="扣除信用分"></el-table-column>
    </el-table>
</template>

<script>
    import {getOrderByCopyIdUsername} from "../../api";

    export default {
        name: "CheckCopy",
        data(){
          return{
              input:'',
              copyIdSearch:'',
              usernameSearch:'',
              orders:[],
              way:1,
              orderNum:0,
              options:[{
                  label:'副本号检索',
                  value:1
              }, {
                  label: '用户名检索',
                  value: 2
              }],
          }
        },
        mounted() {
            this.getOrder();
        },
        methods:{
            getOrder() {
                if (this.way === 1){ //如果是按照copyId查询
                    this.copyIdSearch = this.input;
                    getOrderByCopyIdUsername(this.input,'')
                        .then((resp) => {
                            switch (resp.status) {
                                case 200:
                                    var orderNum=0;
                                    for (let i = 0;i < resp.data.length;i++){
                                        if (resp.data[i].state === "Approved"){
                                            this.orders[orderNum] = resp.data[i];
                                            let a=this.orders[orderNum].timestamp;


                                            let year = a.getFullYear();
                                            let month = a.getMonth()+1;
                                            let day = a.getDate();
                                            let hour = a.getHours();
                                            let minute=a.getMinutes();
                                            let second= a.getSeconds();
                                            let s=" ";let t=":"; let u=":";
                                            if(hour<10) s=" 0"; if(minute<10) t=":0";if(second<10) u=":0";

                                            this.orders[orderNum].timestamp=year+"-"+month+"-"+day+s+hour+t+minute+u+second;

                                            orderNum++;
                                        }
                                    }
                                    break;
                                case 401:
                                    this.$message({message: resp.data.message,type:"error"});
                                    break;
                                default:
                                    break;
                            }
                        })
                        .catch((error) => {
                            this.$message.warning(error.response.data.message);
                        })

                } else if (this.way === 2){ //如果是按照用户id查询
                    this.usernameSearch = this.input;
                    getOrderByCopyIdUsername('',this.input)
                        .then((resp) => {
                            switch (resp.status) {
                                case 200:
                                    var orderNum=0;
                                    for (let i = 0;i < resp.data.length;i++){
                                        if (resp.data[i].state === "Approved"){
                                            this.orders[orderNum] = resp.data[i];
                                            let a=this.orders[orderNum].timestamp;
/*
                                            a.setHours(a.getHours()+8);*/

                                            let year = a.getFullYear();
                                            let month = a.getMonth()+1;
                                            let day = a.getDate();
                                            let hour = a.getHours();
                                            let minute=a.getMinutes();
                                            let second= a.getSeconds();
                                            let s=" ";let t=":"; let u=":";
                                            if(hour<10) s=" 0"; if(minute<10) t=":0";if(second<10) u=":0";

                                            this.orders[orderNum].timestamp=year+"-"+month+"-"+day+s+hour+t+minute+u+second;
                                           orderNum++;
                                        }
                                    }
                                    break;
                                case 401:
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
    }
</script>

<style scoped>
    .el-table{
        width: 90%;
        margin-left: 5%;
        margin-top: 30px;
    }
    .el-form{

        width: 50%;
        margin-top: 80px;
        margin-left: 30%;
    }
    .input{
        width: 80%;
    }
</style>
