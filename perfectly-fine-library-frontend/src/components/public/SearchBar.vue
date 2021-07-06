<template>
    <el-form class="container">
        <el-input class="input"
                  placeholder="请输入检索的书籍"
                  v-model="searchBook" clearable>
        </el-input>
        <el-select v-model="way" placeholder="选择检索方式">
            <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
        </el-select>
        <el-button class="button" type="primary"
                   icon="el-icon-search" @click="goSearch"
                    style="margin-left: 60px">搜索</el-button>
    </el-form>
</template>

<script>
    export default {
        name: "SearchBar",
        data(){
            return{
                searchBook:'',
                way:1,
                options:[{
                    label:'标题检索',
                    value:1
                }, {
                    label: 'isbn检索',
                    value: 2
                }],
            }
        },
        methods:{
            goSearch(){
                this.$emit('onSearch',[this.way,this.searchBook])
            }
        }
    }


</script>

<style scoped>

    .container{
        border-radius: 15px;
        width: 70%;
        padding: 35px 35px 35px 35px;
        background: #F5F5F5 ;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        opacity: 0.85;

        margin-left: 11%;
        margin-top: 4%;
    }
    .input{
        width: 400px;
        float: left;
    }

</style>
