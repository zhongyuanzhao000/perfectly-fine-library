//import Mock from 'mockjs'

// 登录

export function regi1() {
    return {
        // isOpen: false,
        url: '/auth/users/verification_id',
        type: 'get',
        data: {
                'id': 777
        }
    }
}

export function regi2() {
    return {
        url: '/auth/users',
        type: 'post',
        data: {
        }
    }
}

export function userlogin() {
    return {
        url: '/auth/users/token',
        type: 'get',
        data: {
        }
    }
}

export function adminlogin() {
    return {
        url: '/auth/admins/token',
        type: 'get',
        data: {
        }
    }
}

export function pay() {
    return {
        url: '/users/pay',
        type: 'get',
    }
}

/*
export function getToken() {
    return {
        // isOpen: false,
        url: '/admins/token',
        type: 'get',
        data: {
           // 'msg': 'success',
            'status':200,
            //'expire': Mock.Random.natural(60 * 60, 60 * 60 * 12),
            // 'token': Mock.Random.string('abcdefghijklmnopqrstuvwxyz0123456789', 32)
        }
    }
}

export function borrow() {

    return{
        url:'/copies/borrow',
        type:'put',
        data:{
            'status':401,
            'title':'dasdfo'
        }
    }
}

export function getuid(){
    return{
        url:'/users/getuid',
        type:'get',
        data:{
            'status':200,
            'uid':777
        }
    }
}

export function getaid(){
    return{
        url:'/admins/getaid',
        type:'get',
        data:{
            'status':200,
            'aid':778,
        }
    }
}


export function getlib(){
    return{
        url:'/libraries',
        type:'get',
        status:200,
        data:
            [
                {
                    "name": "邯郸",
                    "lid": "HD"
                },
                {
                    "name": "张江",
                    "lid": "ZJ"
                },
                {
                    "name": "江湾",
                    "lid": "JW"
                },
                {
                    "name": "枫林",
                    "lid": "FL"
                },
            ]

    }
}

export function getReserved(){
    return{
        url:'/copies',
        type:'get',
        data:{
            'status':200,
            'copies':[{
                'cid':'89130ad-001',
                'book':{
                    'title':'有一个美丽的小女孩'
                }
            },{
                'cid':'adfpip39-032',
                'book':{
                    'title':'她的名字叫做小薇'
                }
            },{
                'cid':'aa7ijw2-002',
                'book':{
                    'title':'她有双温柔的眼睛'
                }
            },{
                'cid':'7920jl7-001',
                'book':{
                    'title':'她悄悄偷走我的心'
                }
            }]
        }
    }
}
/*
export function getBooks(){
    return{
        url:'/books',
        type:'get',
        data:{
            'status':200,
            'books':[{title:'啦啦啦',isbn:'adsf33098'},
                {title:'鹏欣',isbn:'ada833098'},
                {title:'汤圆',isbn:'a00098'},
                {title:'离异',isbn:'a398'},
                {title:'主动来',isbn:'adsf38'},
                {title:'陈蓉花',isbn:'a33098'},

            ]
        }
    }
}*/
