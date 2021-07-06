<template>
    <div id="send_container">
        <h3>您可以点如下按钮一键发送邮件提醒<br>预约、借阅超期的用户以及有罚款未缴纳的用户~</h3>
        <el-button @click="send" type="primary">一键发送</el-button>
        <img src="../../assets/cat.jpg" alt="" class="img">
    </div>
</template>

<script>
    import {sendEmail} from "../../api";

    export default {
        name: "SendEmail",

        methods:{
            send() {
                sendEmail()
                .then(resp => {
                    switch (resp.status) {
                        case 200:
                            this.$message.success('发送邮件成功！');
                            this.$router.replace('/');
                            break;
                        case 401:
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
    #send_container{
        height: 300px;
    }
    h3{
        margin-top: 50px;
        margin-bottom: 50px;
        color: goldenrod;
        letter-spacing: 0.1em;
    }
    .el-button{
        font-weight: bolder;
        letter-spacing: 0.1em;
    }
    .img{
        height: 320px;
        width: 240px;
        position: absolute;
        left: 70%;
        bottom: 220px;
    }

</style>
