<template>
    <div id="borrow_container">
        <h3 id="my-borrow">我的借阅</h3>
        <div v-if="boNumber > 0" style="margin-top: -100px">
            <el-row :gutter="10" v-for="n in getRowCount" :key="n" class="row" >
                <template v-for="item in books.slice((currentPage - 1)*(width * height) + (n - 1)*width,
                                            (currentPage - 1)*(width  *height) + n * width)" :key="item.id"
                                            style="height: 300px">
                    <el-col :span="6" style="padding: 100px;height: 300px">
                        <el-tooltip class="el-tooltip" placement="right" style="height: 250px">
                            <template #content>
                                <p>{{item.title}}</p>
                                <p>
                                    {{item.author}}
                                </p>
                                <p style="width: 250px">
                                    {{item.brief}}
                                </p>
                            </template>
                            <el-card style="width: 220px;height: 233px;" class="book"
                                     bodyStyle="padding:10px" shadow="hover" @click="toDetail(item.bookId)">
                                <div class="cover">
                                    <img style="height: 200px" :src="item.cover" alt="封面">
                                </div>

                                <div class="borrowInfo" style="height: 45px">
                                    <p class="p-time">借出时间：{{item.version}}</p>
                                    <p class="p-time">归还时间：{{item.expiration}}</p>
                                </div>

                            </el-card>
                        </el-tooltip>
                    </el-col>
                    <el-col :span="1"></el-col>
                </template>
            </el-row>
            <el-row>
                <el-pagination
                        @current-change="handelPageChange"
                        :current-page="currentPage"
                        :total="borrow.length"
                        :page-size="width*height"
                        style="margin-top: 100px;margin-left: 300px">
                </el-pagination>
            </el-row>
        </div>
        <div v-else>
            您没有借用中的书籍！
        </div>
    </div>
</template>

<script>

    import {getBookFromBookId, getCopyFromCopyId, getUserInfo} from "../../api";


    export default {
        name: "MyBorrow",
        data(){
          return{
              boNumber:0,// 借阅的图书的总量
              width:3,// 每页展示的图书有三列
              height:2,// 每页展示的图书有两行
              totalRow:0,// 一共有的行数
              totalPage:0,// 一共有的页数
              currentPage:1,// 当前所在的页码数
              borrow:[], //借阅的书的列表，从后端获取
              bookId:'',
              books:[],
          }
        },
        mounted() {
            this.myBorrow()
        },
        methods:{
            myBorrow(){
                getUserInfo()
                .then(resp => {
                    switch (resp.status) {
                        case 200:
                            this.borrow = resp.data.borrowedCopyIds;
                            if (this.borrow.length > 0){
                                this.boNumber = this.borrow.length;
                                this.totalPage = Math.floor((this.boNumber - 1)/(this.width * this.height)) + 1;
                                this.totalRow = Math.floor((this.boNumber - 1)/(this.width)) + 1;
                                for(let i = 0; i < this.borrow.length; i++){
                                    getCopyFromCopyId(this.borrow[i])
                                        .then(resp => {
                                            if (resp.status === 200) {
                                                this.bookId = resp.data.bookId;
                                                let aaa=resp.data;
                                                getBookFromBookId(this.bookId)
                                                    .then(resp => {
                                                        if (resp.status === 200) {
                                                            this.books[i] = resp.data;
                                                            let a=new Date(aaa.version);
                                                            let b=new Date(aaa.expiration);

                                                            let year = a.getFullYear();
                                                            let month = a.getMonth()+1;
                                                            let day = a.getDate();
                                                            let hour = a.getHours();
                                                            let minute=a.getMinutes();
                                                            let second=a.getSeconds();
                                                            let s=" ";let t=":"; let u=":"
                                                            if(hour<10) s=" 0"; if(minute<10) t=":0";if(second<10) u=":0"

                                                            a=year+"-"+month+"-"+day+s+hour+t+minute+u+second;
                                                            year = b.getFullYear();
                                                            month = b.getMonth()+1;
                                                            day = b.getDate();
                                                            hour = b.getHours();
                                                            minute=b.getMinutes();
                                                            second=b.getSeconds();
                                                            s=" ";t=":"; u=":";
                                                            if(hour<10) s=" 0"; if(minute<10) t=":0";if(second<10) u=":0"

                                                            b=year+"-"+month+"-"+day+s+hour+t+minute+u+second;
                                                            this.books[i].version = a;
                                                            this.books[i].expiration =b;
                                                        } else {
                                                            // alert('错误');
                                                        }
                                                    })
                                                    .catch((error) =>{
                                                        this.$message.warning(error.response.data.message)
                                                    })

                                            } else {
                                                //alert('错误');
                                            }
                                        })
                                        .catch((error) =>{
                                            this.$message.warning(error.response.data.message)
                                        })
                                }
                            }
                            break;
                        case 401:// 认证失败。客户端应该提示重新登录
                            this.$message({message:resp.data.message,type:"error"});
                            break;
                    }
                })
                .catch((error) => {
                    this.$message.warning(error.response.data.message)
                })
            },

            handelPageChange: function (currentPage){// 用于改变页码
                this.currentPage = currentPage;
            },

            toDetail(bid){
                this.$router.push({
                    path: '/detail',
                    query:{
                        bid: bid
                    }
                })
            }
        },
        computed:{
            getPageBookCount() {// 返回页面该展示的书籍的本数
                if (this.currentPage === this.totalPage){// 如果当前页面是最后一页
                    return (this.boNumber % (this.width * this.height));
                }else {// 如果当前页面不是最后一页，页面将被填满
                    return this.width * this.height;
                }
            },

            getRowCount() { // 返回本页面应有的行数
                var number = this.getPageBookCount;
                return Math.floor((number - 1)/(this.width)) + 1;
            }
        }
    }
</script>

<style scoped>
    .row{
    display:block;
    flex-wrap: wrap;
    }

    #borrow_container{
        border: 1px solid #eaeaea;
        border-radius: 15px;
        background-clip: padding-box;
        padding: 35px 35px 15px 35px;
        background: #fff;
        box-shadow: 0 0 25px #cac6c6;
        opacity: 0.85;
    }

    .el-tooltip{
        height: 50px;
    }
    .el-card{
        height: 350px;
    }

    .p-time{
         text-align-all: center;
         font-size: smaller;
     }
</style>
