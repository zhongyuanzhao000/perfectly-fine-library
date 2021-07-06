<template>
    <search-bar @onSearch="searchResult($event)"></search-bar>

    <div v-if="bookCount>0" style="margin-top: 80px">
        <el-row v-for="n in getRowCount" :key="n">
            <template v-for="item in books.slice((currentPage-1)*(width*height)+(n-1)*width,(currentPage-1)*(width*height)+n*width)" :key="item.id">
                <el-col :span="4">
                    <el-card :body-style="{ padding: '0px' }" @click="toDetail(item.bookId)">
                        <div class="image">
                            <img :src="item.cover" class="image">
                        </div>
                        <div style="padding: 14px;">
                            <span>{{item.title}}</span>
                            <div class="bottom">
                                <time class="author">{{ item.author }}</time>
                            </div>
                        </div>
                    </el-card>
                </el-col>
                <el-col :span="1"></el-col>
            </template>
        </el-row>
        <el-pagination
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :total="books.length"
                style="margin-top: 50px">
        </el-pagination>
    </div>
    <div v-else>
        对不起，找不到相关书籍
    </div>
</template>

<script>
    import SearchBar from "../public/SearchBar";
    import {getIndexBooks, isbnSearch, titleSearch} from "../../api";
    export default {
        name: "Books",
        data(){
            return{
                books:[],
                bookCount:1,
                currentPage:1,
                totalPage:1,
                width:5,
                height:2,
                totalRow:0
            }
        },
        mounted:function () {
            this.loadBooks()
        }
        ,
        methods:{
            loadBooks(){
                getIndexBooks()
                .then(resp => {
                    this.books = resp.data;
                });
                if(this.books.length > 0) {
                    this.bookCount=this.books.length;
                    this.totalPage = Math.floor((this.bookCount - 1)/(this.width*this.height))+1;
                    this.currentPage = 1;
                    this.totalRow=Math.floor((this.bookCount-1)/this.width)+1
                }
            },

            handleCurrentChange: function (currentPage) {
                this.currentPage = currentPage
            }
            ,
            searchResult (args) {
                var way=args[0];
                var searchBook=args[1];
                switch (way) {
                    case 1:
                        titleSearch(searchBook)
                       .then(resp => {
                            switch (resp.status) {
                                case 200:
                                    this.books=resp.data;
                                    break;
                                default:
                                    break;
                            }
                        });break;
                    case 2:
                        isbnSearch(searchBook)
                        .then(resp => {
                            switch (resp.status) {
                                case 200:
                                    this.books = resp.data;
                                    break;
                                default:
                                    break;
                            }
                        })
                        .catch((error) => {
                            this.$message.warning(error.response.data.message);
                        });
                    break;
                }
                if(this.books.length>0) {
                    this.bookCount=this.books.length;
                    this.totalPage = Math.floor((this.bookCount - 1)/(this.width*this.height))+1;
                    this.currentPage = 1;
                    this.totalRow=Math.floor((this.bookCount-1)/this.width)+1
                }
            },

            toDetail(bid){
                this.$router.push({
                    path:'/detail',
                    query:{
                        bid:bid
                    }
                })
            }
        },
        computed:{

            getPageBookCount(){
                if(this.currentPage===this.totalPage){
                    return this.bookCount%(this.width*this.height);
                }else {
                    return (this.width*this.height)
                }
            }
            ,

            getRowCount(){
                return Math.floor((this.getPageBookCount-1)/this.width)+1
            },

        }
        ,
        components:{
            SearchBar
        }
    }
</script>

<style scoped>
    .author {
        font-size: 13px;

    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    div.image {
        width: 200px;
        height: 150px;
        display: block;
    }
    img.image{
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

</style>
