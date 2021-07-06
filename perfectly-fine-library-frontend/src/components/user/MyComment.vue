<template>
<div id="cmt">
    <h3 id="my-comment">我的评价</h3>
    <div v-if="CmdNumber > 0" >
        <div v-for="c in comments.slice((currentPage - 1) * many,currentPage * many)" :key="c.commendId">
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
                <el-col :span="11">
                    <div style="margin-top: 7px">
                        <el-rate v-model="c.rate" disabled show-score text-color="#ff9900"></el-rate>
                    </div>
                </el-col>
                <el-col :span="5">
                    <div v-if="isAdmin">
                        <el-button type="text" @click=deleteComment style="color: darkslateblue;">
                            删除
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
        <el-row>
            <el-pagination
                    @current-change="handelPageChange"
                    :current-page="currentPage"
                    :total="CmdNumber.length"
                    :page-size="many"
                    style="margin-top: 100px;margin-left: 300px">
            </el-pagination>
        </el-row>
    </div>
    <div v-else>
        你还没有发表过评论
    </div>
</div>
</template>

<script>
    import {
        addReply,
        delComment,
        delReply,
        getCommentByBookIdUsername,
        getRepliesByCommentId,
        getUserInfo
    } from "../../api";

    export default {
        name: "MyComment",
        data(){
            return{
                username:'',
                CmdNumber:0,
                many:5,
                totalPage:0,
                currentPage:1,
                comments:[],
                replyText:'',
                isAdmin:window.localStorage.getItem('role') === 'admin'|| window.localStorage.getItem('role')==='super'
            }
        },
        mounted() {
            this.myComment()
        },
        methods:{

            myComment(){
                getUserInfo()
                    .then(resp => {
                        switch (resp.status) {
                            case 200:
                                this.username = resp.data.username;
                                getCommentByBookIdUsername('',this.username)
                                    .then(resp => {
                                        switch (resp.status) {
                                            case 200:
                                                this.comments = resp.data;
                                                this.CmdNumber = this.comments.length;
                                                this.totalPage = Math.floor((this.boNumber - 1)/(this.width * this.height)) + 1;
                                                for (let i of this.comments){
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
                                                break;
                                            default:
                                                this.$message({message:resp.data.message,type:"error"});
                                                break;
                                        }
                                    })
                                    .catch((error) => {
                                        this.$message.warning(error.response.data.message)
                                    });
                                break;
                            case 401:
                                this.$message({message:resp.data.message,type:"error"});
                                break;
                            default:
                                break;
                        }
                    });

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

            },

            handelPageChange: function (currentPage){// 用于改变页码
                this.currentPage = currentPage;
            },
        }
    }
</script>

<style scoped>
    #cmt{
        border: 1px solid #eaeaea;
        border-radius: 15px;
        background-clip: padding-box;
        padding: 35px 35px 15px 35px;
        background: #fff;
        box-shadow: 0 0 25px #cac6c6;
        opacity: 0.85;
    }
</style>
