<template>
    <el-form>
        <el-row>
            <el-col :span="12">
                <el-form-item>
                    <el-input class="input" v-model="input" clearable
                                placeholder="请输入想要重置信用分的用户名">
                    </el-input>
                </el-form-item>
            </el-col>
            <el-col :span="6">
                <el-form-item>
                    <el-button class="button" type="primary"  icon="el-icon-search" @click="reset">
                    重置
                    </el-button>
                </el-form-item>
            </el-col>
        </el-row>
    </el-form>


</template>

<script>
    import {resetUserCredit} from "../../api";

    export default {
        name: "Credit",
        data(){
            return{
                input:'',
                username:'',
                credit:0//用户的信用分数
            }
        },
        methods:{
            reset(){
                resetUserCredit(this.input)
                .then((resp) => {
                    switch (resp.status) {
                        case 200:
                            this.$message.success('重置成功！');
                            break;
                        case 404:
                            //不存在
                            this.$message({message:'用户名不存在1',type:'error'});
                            break;
                        case 401:
                            //没有权限
                            this.$message({message:'您没有重置的权限！',type:'error'});
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
