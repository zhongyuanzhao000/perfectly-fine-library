<template>
    <h3>我的记录信息</h3>
    <el-table :data="orders" stripe empty-text="-">
        <el-table-column prop="copyId" label="副本编号"></el-table-column>
        <el-table-column prop="operation" label="操作名称"></el-table-column>
        <el-table-column prop="username" label="操作用户"></el-table-column>
        <el-table-column prop="adminUsername" label="操作管理员" ></el-table-column>
        <el-table-column prop="library" label="操作图书馆" ></el-table-column>
        <el-table-column prop="timestamp" label="操作时间"></el-table-column>
        <el-table-column prop="fine" label="造成罚款"></el-table-column>
        <el-table-column prop="credit" label="扣除信用分"></el-table-column>
    </el-table>
</template>

<script>
    import {getOrderByCopyIdUsername, getUserInfo} from "../../api";

    export default {
        name: "MyRecord",
        data() {
            return{
                username:'',
                orderNum:0,
                orders:[]
            }
        },
        mounted() {
            this.getMyInformation()
        },
        methods:{
           dateFormat(fmt, date) {
        let ret;
        const opt = {
            "Y+": date.getFullYear().toString(),        // 年
            "m+": (date.getMonth() + 1).toString(),     // 月
            "d+": date.getDate().toString(),            // 日
            "H+": date.getHours().toString(),           // 时
            "M+": date.getMinutes().toString(),         // 分
            "S+": date.getSeconds().toString()          // 秒
            // 有其他格式化字符需求可以继续添加，必须转化成字符串
        };
        for (let k in opt) {
            ret = new RegExp("(" + k + ")").exec(fmt);
            if (ret) {
                fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
            }
        }
        return fmt;
    },
            getMyInformation() {
                getUserInfo()
                    .then(resp => {
                        switch (resp.status) {
                            case 200:
                                this.username = resp.data.username;
                                getOrderByCopyIdUsername('',this.username)
                                .then((resp) => {
                                    switch (resp.status) {
                                        case 200:
                                            for (let i = 0;i < resp.data.length;i++){
                                                if (resp.data[i].state === "Approved"){
                                                    this.orders[this.orderNum] = resp.data[i];
                                                    let a= new Date(this.orders[this.orderNum].timestamp);
                                                    /*
                                                    a.setHours(a.getHours()+8);
                                                    this.dateFormat("YYYY-mm-dd HH:MM", a);
                                                    this.orders[this.orderNum].timestamp=a;*/

                                                    let year = a.getFullYear();
                                                    let month = a.getMonth()+1;
                                                    let day = a.getDate();
                                                    let hour = a.getHours();
                                                    let minute=a.getMinutes();
                                                    let second=a.getSeconds();
                                                    let s=" ";let t=":"; let u=":"
                                                    if(hour<10) s=" 0"; if(minute<10) t=":0";if(second<10) u=":0";

                                                    this.orders[this.orderNum].timestamp=year+"-"+month+"-"+day+s+hour+t+minute+u+second;

                                                    //   alert(this.orders[this.orderNum].timestamp);
                                                    this.orderNum++;
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
                                });
                                break;
                            case 401:
                                this.$message({message:resp.data.message,type:"error"});
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

</style>
