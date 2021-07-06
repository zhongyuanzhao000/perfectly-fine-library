<template>
    <el-form>
        <div class="things">
            <h3>教师</h3>
            <h4> 最大借阅数量：</h4>
            <el-row>
                <el-col :span="9"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min='0' max="100" v-model="teacher.maxBorrowNumber"></el-input-number>
                    </el-form-item>
                </el-col>
            </el-row>

            <h4>借阅超期时长：</h4>
            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min='0' max="1000" v-model="teacher.bo_day"></el-input-number><span>天</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                       <el-input-number min="0" max="23" v-model="teacher.bo_hour" ></el-input-number><span>时</span>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="teacher.bo_min"></el-input-number><span>分</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                      <el-input-number min="0" max="59" v-model="teacher.bo_send"></el-input-number><span>秒</span>
                    </el-form-item>
                </el-col>
            </el-row>

            <h4>预约超期时长：</h4>
            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="1000" v-model="teacher.re_day"></el-input-number><span>天</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="23" v-model="teacher.re_hour"></el-input-number><span>时</span>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="teacher.re_min"></el-input-number><span>分</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="teacher.re_send"></el-input-number><span>秒</span>
                    </el-form-item>
                </el-col>
            </el-row>
        </div>

        <div class="things">
            <h3>本科生</h3>
            <h4>最大借阅数量：</h4>
            <el-row>
                <el-col :span="9"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="100" v-model="undergraduate.maxBorrowNumber"></el-input-number>
                    </el-form-item>
                </el-col>
            </el-row>
            <h4>借阅超期时长：</h4>
            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="1000" v-model="undergraduate.bo_day"></el-input-number><span>天</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="23" v-model="undergraduate.bo_hour"></el-input-number><span>时</span>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="undergraduate.bo_min"></el-input-number><span>分</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="undergraduate.bo_send"></el-input-number><span>秒</span>
                    </el-form-item>
                </el-col>
            </el-row>
            <h4>预约超期时长：</h4>
            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="1000" v-model="undergraduate.re_day"></el-input-number><span>天</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="23" v-model="undergraduate.re_hour"></el-input-number><span>时</span>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="undergraduate.re_min"></el-input-number><span>分</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="undergraduate.re_send"></el-input-number><span>秒</span>
                    </el-form-item>
                </el-col>
            </el-row>
        </div>

        <div class="things">
            <h3>研究生</h3>
            <h4>最大借阅数量：</h4>
            <el-row>
                <el-col :span="9"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="100" v-model="postgraduate.maxBorrowNumber"></el-input-number>
                    </el-form-item>
                </el-col>
            </el-row>
            <h4>借阅超期时长：</h4>
            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                       <el-input-number min="0" max="1000" v-model="postgraduate.bo_day"></el-input-number><span>天</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number  min="0" max="23" v-model="postgraduate.bo_hour"></el-input-number><span>时</span>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="postgraduate.bo_min"></el-input-number><span>分</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="postgraduate.bo_send"></el-input-number><span>秒</span>
                    </el-form-item>
                </el-col>
            </el-row>
            <h4>预约超期时长：</h4>
            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="1000" v-model="postgraduate.re_day"></el-input-number><span>天</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="23" v-model="postgraduate.re_hour"></el-input-number><span>时</span>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="postgraduate.re_min"></el-input-number><span>分</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item class="items">
                        <el-input-number min="0" max="59" v-model="postgraduate.re_send"></el-input-number><span>秒</span>
                    </el-form-item>
                </el-col>
            </el-row>
        </div>
        <el-form-item>
            <el-button  @click="setConfig" id="configButton">修改</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import { getConfig,modifyConfig } from '../../api'
    export default {
        name: "ModifyConfig",
        data() {
            return{
                undergraduate:{
                    maxBorrowNumber: 1,
                    borrowExpiration: 0,
                    reserveExpiration: 0,
                    bo_day:1,
                    bo_hour: 0,
                    bo_min: 0,
                    bo_send:0,
                    re_day: 1,
                    re_hour:0,
                    re_min: 0,
                    re_send: 0

                },
                postgraduate:{
                    maxBorrowNumber: 1,
                    borrowExpiration: 0,
                    reserveExpiration: 0,
                    bo_day:1,
                    bo_hour: 0,
                    bo_min: 0,
                    bo_send:0,
                    re_day: 1,
                    re_hour:0,
                    re_min: 0,
                    re_send: 0
                },
                teacher:{
                    maxBorrowNumber: 1,
                    borrowExpiration: 0,
                    reserveExpiration: 0,
                    bo_day:1,
                    bo_hour: 0,
                    bo_min: 0,
                    bo_send:0,
                    re_day: 1,
                    re_hour:0,
                    re_min: 0,
                    re_send:0
                },
            }
        },

        mounted() {
            this.getConfig();
        },

        methods:{
            //获取初始设置信息的方法
            getConfig() {
                getConfig()
                    .then((resp) => {
                        switch (resp.status) {
                            case 200:
                                for(let i of resp.data){
                                    if(i.role==='UNDERGRADUATE'){
                                        this.undergraduate.maxBorrowNumber = i.maxBorrowNumber;
                                        this.undergraduate.borrowExpiration = i.borrowExpiration / 1000;
                                        this.undergraduate.reserveExpiration = i.reserveExpiration / 1000;
                                        this.undergraduate.bo_day = Math.floor(this.undergraduate.borrowExpiration/86400);
                                        this.undergraduate.bo_hour = Math.floor((this.undergraduate.borrowExpiration % 86400)/3600);
                                        this.undergraduate.bo_min = Math.floor(((this.undergraduate.borrowExpiration) % 3600) / 60);
                                        this.undergraduate.bo_send = (this.undergraduate.borrowExpiration) % 60;
                                        this.undergraduate.re_day = Math.floor((this.undergraduate.reserveExpiration) / 86400);
                                        this.undergraduate.re_hour = Math.floor(((this.undergraduate.reserveExpiration) % 86400) / 3600);
                                        this.undergraduate.re_min = Math.floor(((this.undergraduate.reserveExpiration) % 3600) / 60);
                                        this.undergraduate.re_send = (this.undergraduate.reserveExpiration) % 60;
                                    }
                                    if(i.role==='POSTGRADUATE'){
                                        this.postgraduate.maxBorrowNumber = i.maxBorrowNumber;
                                        this.postgraduate.borrowExpiration = i.borrowExpiration / 1000;
                                        this.postgraduate.reserveExpiration = i.reserveExpiration / 1000;
                                        this.postgraduate.bo_day =Math.floor( (this.postgraduate.borrowExpiration) / 86400);
                                        this.postgraduate.bo_hour = Math.floor(((this.postgraduate.borrowExpiration) % 86400) / 3600);
                                        this.postgraduate.bo_min = Math.floor(((this.postgraduate.borrowExpiration) % 3600) / 60);
                                        this.postgraduate.bo_send = (this.postgraduate.borrowExpiration) % 60;
                                        this.postgraduate.re_day = Math.floor((this.postgraduate.reserveExpiration) / 86400);
                                        this.postgraduate.re_hour =  Math.floor(((this.postgraduate.reserveExpiration) % 86400) / 3600);
                                        this.postgraduate.re_min =  Math.floor(((this.postgraduate.reserveExpiration) % 3600) / 60);
                                        this.postgraduate.re_send = (this.postgraduate.reserveExpiration) % 60;
                                    }
                                    if(i.role==='TEACHER'){
                                        this.teacher.maxBorrowNumber = i.maxBorrowNumber;
                                        this.teacher.borrowExpiration = i.borrowExpiration / 1000;
                                        this.teacher.reserveExpiration = i.reserveExpiration / 1000;
                                        this.teacher.bo_day =  Math.floor((this.teacher.borrowExpiration) / 86400);
                                        this.teacher.bo_hour =  Math.floor(((this.teacher.borrowExpiration) % 86400) / 3600);
                                        this.teacher.bo_min =  Math.floor(((this.teacher.borrowExpiration) % 3600) / 60);
                                        this.teacher.bo_send = (this.teacher.borrowExpiration) % 60;
                                        this.teacher.re_day =  Math.floor((this.teacher.reserveExpiration) / 86400);
                                        this.teacher.re_hour =  Math.floor(((this.teacher.reserveExpiration) % 86400) / 3600);
                                        this.teacher.re_min =  Math.floor(((this.teacher.reserveExpiration) % 3600) / 60);
                                        this.teacher.re_send = (this.teacher.reserveExpiration) % 60;
                                    }
                                }
                                break;
                            case 401:
                                this.$message({message:resp.data.message,type:"error"});
                                break;
                            default:
                                break;
                        }
                    })
            },

            //修改设置信息的方法
            setConfig() {
                this.undergraduate.borrowExpiration = 1000 * (this.undergraduate.bo_day * 86400 + this.undergraduate.bo_hour * 3600 + this.undergraduate.bo_min * 60 + this.undergraduate.bo_send  * 1);
                this.postgraduate.borrowExpiration = 1000 * (this.postgraduate.bo_day * 86400 + this.postgraduate.bo_hour * 3600 + this.postgraduate.bo_min * 60 + this.postgraduate.bo_send  * 1);
                this.teacher.borrowExpiration = 1000 * ( this.teacher.bo_day * 86400 + this.teacher.bo_hour * 3600 + this.teacher.bo_min * 60 + this.teacher.bo_send  * 1);

                this.undergraduate.reserveExpiration = 1000 * (this.undergraduate.re_day * 86400 + this.undergraduate.re_hour * 3600 + this.undergraduate.re_min * 60 + this.undergraduate.re_send  * 1);
                this.postgraduate.reserveExpiration = 1000 * (this.postgraduate.re_day * 86400 + this.postgraduate.re_hour * 3600 + this.postgraduate.re_min * 60 + this.postgraduate.re_send  * 1);
                this.teacher.reserveExpiration = 1000 * ( this.teacher.re_day * 86400 + this.teacher.re_hour * 3600 + this.teacher.re_min * 60 + this.teacher.re_send * 1);

                modifyConfig(this.undergraduate.maxBorrowNumber,this.undergraduate.borrowExpiration,this.undergraduate.reserveExpiration,
                    this.postgraduate.maxBorrowNumber,this.postgraduate.borrowExpiration,this.postgraduate.reserveExpiration,
                    this.teacher.maxBorrowNumber,this.teacher.borrowExpiration,this.teacher.reserveExpiration)
                    .then((response) => {
                        switch (response.status) {
                            case 200:
                                this.$message.success('修改成功');
                                break;
                            case 400:
                                this.$message.error('表单错误');
                                break;
                            case 401:
                                this.$message.error('当前用户没有操作权限');
                                break;
                            default:
                                break;

                        }
                    })
            }
        }
    }
</script>

<style scoped>
    .el-form{
        border-radius: 15px;
        background-clip: padding-box;
        margin-top: 40px;
        margin-left: 5%;
        width: 90%;
        padding-top: 5%;
        padding-bottom: 5%;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        opacity: 0.85;
    }
    .el-input-number{
        width: 70%;
        height: 80%;
        margin-right: 5%;
    }
    h3{
        font-size: 150%;
        letter-spacing: 2em;
        margin-left: 8%;

    }
    .items{
        width: 200px;
    }
    .things{
        padding-top: 2%;
        padding-bottom: 2%;
        margin-bottom: 2%;
        margin-left: 15%;
        width: 70%;
        background-color: #F5F5F5;
        border-radius: 5%;
    }
    span{
        font-size: 140%;
        font-weight: bold;
    }
    #configButton{
        width: 20%;
        background: #afb4db;
        border: none;
    }
    #configButton:hover{
        background-color: #4682B4;
    }

</style>
