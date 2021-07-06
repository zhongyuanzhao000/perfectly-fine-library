<template>
    <el-tabs type="border-card" id="tabs">
        <el-tab-pane>
            <template #label>
                图书详情
            </template>
            <div>
                <el-row style="margin-top: 35px">
                    <el-col :span="10" style="position: center">
                        <img :src="cover" style="height: 350px;width:350px;overflow: hidden">
                    </el-col>
                    <el-col :span="2"></el-col>
                    <el-col :span="10" style="width: 200px">
                        <h4>《{{title}}》</h4>
                        <span>作者：{{author}}</span>
                        <p>价格：{{price}}</p>
                        <p>ISBN号：{{isbn}}</p>
                        <p style="font-size: 0.9em">简介：{{brief}}</p>
                    </el-col>
                </el-row>
            </div>
        </el-tab-pane>
        <el-tab-pane>
            <template #label>
                副本信息
            </template>
            <el-table :data="copies" stripe height="445" empty-text="-">
                <el-table-column prop="isbn" label="副本唯一标识"></el-table-column>
                <el-table-column prop="copyId" label="副本编号"></el-table-column>
                <el-table-column prop="status" label="副本状态"></el-table-column>
                <el-table-column prop="library" label="所在图书馆"></el-table-column>
                <el-table-column prop="behavior" label="操作">
                    <template #default="scope">
                        <el-button @click="reserve(scope.row.copyId)" v-if="isUser&&(scope.row.status==='Available')">预约</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-tab-pane>
        <el-tab-pane>
            <template #label>
                图书评价
            </template>
            <div v-if="commentNum > 0">
                <el-scrollbar>
                    <div v-for="c in comments" :key="c.commendId">
                        <div>
                            <el-row>
                                <el-col :span="2">
                                    <div>
                                        <el-avatar icon="el-icon-user-solid"></el-avatar>
                                    </div>
                                </el-col>
                                <el-col :span="4">
                                    <div style="margin-top: 7px">
                                        {{c.username}}
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div style="margin-top: 7px">
                                        <el-rate v-model="c.rate" disabled show-score text-color="#ff9900"></el-rate>
                                    </div>
                                </el-col>
                                <el-col :span="4">
                                    <div style="margin-top: 7px">
                                        {{c.timestamp}}
                                    </div>
                                </el-col>
                                <el-col :span="2">
                                    <div v-if="isAdmin||c.canDelete">
                                        <el-button type="text" v-on:click="deleteComment(c.commentId)" style="color: darkslateblue;">
                                            删除
                                        </el-button>
                                    </div>
                                </el-col>
                                <el-col :span="3">
                                    <div v-if="!isAdmin">
                                        <el-button type="text" v-on:click="reply(c.commentId,null)" style="color: darkslateblue;">
                                            回复
                                        </el-button>
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24">
                                    <div class="content">
                                        <p>
                                            {{c.content}}
                                        </p>
                                        <div style="position: center">
                                            <el-collapse style="position: center">
                                                <el-collapse-item title="展开评论回复" style="position: center;color: darkslateblue;">
                                                    <div v-for="r of c.replies" :key="r.replyId">
                                                        <div>
                                                            <el-row>
                                                                <el-col :span="2">
                                                                    <div>
                                                                        <el-avatar icon="el-icon-user-solid"></el-avatar>
                                                                    </div>
                                                                </el-col>
                                                                <el-col :span="4">
                                                                    <div style="margin-top: 7px">
                                                                        {{r.username}}
                                                                    </div>
                                                                </el-col>
                                                                <el-col :span="4" >
                                                                    <div v-if="r.toId !== null">
                                                                        to {{r.toUsername}}
                                                                    </div>
                                                                </el-col>
                                                                <el-col :span="4">
                                                                    <div style="margin-top: 7px">
                                                                        {{r.timestamp}}
                                                                    </div>
                                                                </el-col>
                                                                <el-col :span="5">
                                                                    <div v-if="isAdmin || r.canDelete">
                                                                        <el-button type="text" v-on:click="deleteReply(r.replyId)" style="color: darkslateblue;">
                                                                            删除
                                                                        </el-button>
                                                                    </div>
                                                                </el-col>
                                                                <el-col :span="5">
                                                                        <el-button type="text" v-on:click="reply(c.commentId,r.replyId)" style="color: darkslateblue;">
                                                                            回复
                                                                        </el-button>
                                                                </el-col>
                                                            </el-row>
                                                            <el-row>
                                                                <div>
                                                                    <p>
                                                                        {{r.content}}
                                                                    </p>
                                                                </div>
                                                            </el-row>
                                                        </div>
                                                    </div>
                                                </el-collapse-item>
                                            </el-collapse>
                                        </div>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </el-scrollbar>
            </div>
            <div v-else>
                对不起！当前书籍暂无评论！
            </div>
            <!-- 通过设置v-if来打开或者关闭评论窗口的功能可能会有问题哒   如果实在不行就用弹窗输入吧-->
            <el-button v-if="!openComment" type="primary" icon="el-icon-chat-line-square"
                       v-on:click="openCmt" style="margin-top: 30px">评论
            </el-button>
            <el-button v-else type="primary" icon="el-icon-caret-top" v-on:click="closeCmt">收起</el-button>
            <el-form v-if="isUser&&openComment">
                <el-form-item>
                    <el-input
                            type="textarea"
                            autosize
                            placeholder="请输入内容"
                            v-model="content">
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-rate v-model="rate"></el-rate>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"  v-on:click="submitComment">提交</el-button>
                </el-form-item>
            </el-form>


        </el-tab-pane>
    </el-tabs>
</template>

<script>
    import {
        getBookFromBookId,
        getCopyFromBid,
        reserveBook,
        getCommentByBookIdUsername,
        getRepliesByCommentId, addComment, delComment, addReply, getOrderState, getUserInfo, delReply,

    } from "../../api";
    export default{
        name: "Detail",
        data(){
            return{
                coNumber:0,// 有多少本副本
                copies:[],// 一系列的副本信息
                title:'',
                isbn:'',
                author:'',
                brief:'',
                cover:'',
                value:4.5,
                price:'',//图书的价格   to do
                bookId:0,
                comments:[],//这本书的所有评价
                commentNum:0,//这本书评价的总数
                isUser:window.localStorage.getItem('role') === 'user',
                isAdmin:window.localStorage.getItem('role') === 'admin'|| window.localStorage.getItem('role')==='super',
                content:'',
                rate:'',
                openComment:false,
                replyText:'',
                orderId:0,
                state:'Creating',
                flag:null, //定时器
                myName:''
            }
        },

        mounted() {
            this.details();
        },

        methods: {
            //返回图书详情信息的方法
            details() {
                getUserInfo().then(resp => {
                    switch (resp.status) {
                        case 200:
                          this.myName=resp.data.username;
                    }
                })
                    .catch((error) => {
                        this.$message.warning(error.msg)
                    });

                getBookFromBookId(window.location.href.split('=')[1]).then(resp => {
                    switch (resp.status) {
                        case 200:
                            this.title = resp.data.title;
                            this.author = resp.data.author;
                            this.brief = resp.data.brief;
                            this.cover = resp.data.cover;
                            this.isbn = resp.data.isbn;
                            this.price = resp.data.price;
                            this.bookId = resp.data.bookId;
                    }
                })
                    .catch((error) => {
                        this.$message.warning(error.msg)
                    });

                getCopyFromBid(window.location.href.split('=')[1])
                    .then(resp => {
                        switch (resp.status) {
                            case 200:
                                this.copies = resp.data;
                                if (this.copies.length > 0) {
                                    this.coNumber = this.copies.length;
                                }
                                break;
                            case 401:
                                this.$message({message: resp.data.message, type: "error"});
                                break;
                        }
                    })
                    .catch((error) => {
                        this.$message.warning(error.response.data.message)
                    });

                //获取该图书评论的方法
                    getCommentByBookIdUsername(window.location.href.split('=')[1],'')
                        .then((resp) => {
                            switch (resp.status) {
                                case 200:
                                    this.comments = resp.data;
                                    if (this.comments.length > 0){
                                        this.commentNum = this.comments.length;
                                        for(let i of this.comments){
                                            let a=new Date(i.timestamp);

                                            let year = a.getFullYear();
                                            let month = a.getMonth()+1;
                                            let day = a.getDate();
                                            let hour = a.getHours() ;
                                            let minute=a.getMinutes();
                                            let second=a.getSeconds();
                                            let s=" ";let t=":"; let u=":";
                                            if(hour<10) s=" 0"; if(minute<10) t=":0";if(second<10) u=":0";



                                            i.timestamp=year+"-"+month+"-"+day+s+hour+t+minute+u+second;
                                            i.canDelete = this.myName === i.username;
                                          //  alert(this.myName +i.username+i.canDelete);
                                            getRepliesByCommentId(i.commentId)
                                                .then((resp) => {
                                                    switch (resp.status) {
                                                        case 200:
                                                            i.replies = resp.data;
                                                            for(let r of i.replies){
                                                                let b=new Date(r.timestamp);

                                                                let year = b.getFullYear();
                                                                let month = b.getMonth()+1;
                                                                let day = b.getDate();
                                                                let hour = b.getHours();
                                                                let minute=b.getMinutes();
                                                                let second=b.getSeconds();
                                                                let s=" ";let t=":"; let u=":";
                                                                if(hour<10) s=" 0"; if(minute<10) t=":0";if(second<10) u=":0"

                                                                r.timestamp=year+"-"+month+"-"+day+s+hour+t+minute+u+second;

                                                                r.canDelete = this.myName === r.username;
                                                                if(r.toId!=null){
                                                                    for(let q of i.replies){
                                                                        if(r.toId === q.replyId){
                                                                            r.toUsername = q.username;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            break;
                                                    }
                                                })
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                        })


            },

            // 用户预约的方法
            reserve(cid){
                reserveBook(cid)
                    .then(resp => {
                        switch (resp.status) {
                            case 202:
                                this.orderId = resp.data.orderId;
                                this.state = resp.data.state;
                                //如果该order的状态不是成功或者失败，就一直查下去
                                this.flag = setInterval(()=>this.getState(this),1000);
                                break;
                            case 400:
                                this.$message({message:resp.data.message,type:'error'});
                                break;
                            case 401:
                                this.$message({message:resp.data.message,type:"error"});
                                break;
                            case 403:
                                this.$message({message:resp.data.message,type:"error"});
                                break;
                            case 404:
                                this.$message({message:resp.data.message,type:"error"});
                                break;
                        }
                    })
                    .catch((error) => {
                        this.$message.warning(error.response.data.message);
                    });

            },

            //获取当前order状态的方法
            getState(_this){
              getOrderState(_this.orderId)
              .then((resp) => {
                  _this.state = resp.data.state;
                  if (_this.state === 'Approved' || _this.state === 'Rejected'){
                      if (this.state === 'Approved'){
                          this.$router.go(0);
                          this.$message.success('预约成功');
                      } else if(this.state === 'Rejected'){
                          this.$message.error('预约失败');
                      }
                      clearInterval(_this.flag);
                  }
                  _this.state = resp.data.state;
              })
            },

            //展开评论
            openCmt(){
                this.openComment = true;
            },

            //关闭评论
            closeCmt(){
                this.openComment=false;
            },

            deleteComment(commentId){
                delComment(commentId)
                    .then((resp) => {
                        switch (resp.status) {
                            case 200:
                                this.$message.success("删除成功！");
                                this.$router.go(0);
                                break;
                            default:
                                break;
                        }
                    }).catch((error) => {
                    this.$message.warning(error.response.data.message);
                });

            },

            deleteReply(replyId){
                delReply(replyId)
                    .then((resp) => {
                        switch (resp.status) {
                            case 200:
                                this.$message.success("删除成功！");
                                this.$router.go(0);
                                break;
                            default:
                                break;
                        }
                    }).catch((error) => {
                    this.$message.warning(error.response.data.message);
                });

            },

            submitComment(){
                addComment(this.bookId,this.rate,this.content)
                    .then((resp) => {
                        switch (resp.status) {
                            case 201:
                                this.$message.success("评论成功！");
                                this.$router.go(0);
                                break;
                            default:
                                break;
                        }
                    }).catch((error) => {
                    this.$message.warning(error.response.data.message);
                });

            },

            reply(commentId,replyId){
                this.$prompt('请输入您的回复','提示',{
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(({value}) => {
                    this.replyText= value;
                    addReply(commentId,this.replyText,replyId)
                        .then(response => {
                            switch (response.status) {
                                case 201:
                                    this.$message.success('回复成功！');
                                    this.$router.go(0);
                                    break;
                                case 401:
                                    this.$message({message:'错误',type:'error'});
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

    #tabs{
        height: 500px;
        width: 800px;
        position: center;
        margin-left: 200px;
        margin-top: 25px;
        margin-bottom: 25px;
    }
</style>
