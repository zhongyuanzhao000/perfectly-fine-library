
/*
const proxy = {
    'GET /admins/token':{
        status:200
    }
}
module.exports = proxy*/
import Mock from 'mockjs'
import * as login from './modules/login.js'
/*
Mock.mock(new RegExp('/books'),'get', {
    'status': 200,
    'books|1-3': [
        {
            'title|1': ['瞎蒙姐', '鹏欣', '汤圆', '离异', '主动来', '陈蓉花'],
            'isbn|1':['dfa','adfa'],
            'author':'大胖氢是你爸爸'
        }
    ]
})
*/
// 可以通过 isOpen 参数设置是否拦截整个模块的 mock 功能
fnCreate(login, true)

/**
 * 创建mock模拟数据
 * @param {*} mod 模块
 * @param {*} isOpen 是否开启?
 */
function fnCreate(mod, isOpen = true) {
    if (isOpen) {
        for (var key in mod) {
            ((res) => {
                if (res.isOpen !== false) {
                    Mock.mock(new RegExp(res.url), res.type, (opts) => {
                        opts['data'] = opts.body ? JSON.parse(opts.body) : null
                        delete opts.body
                        console.log('\n')
                        console.log('%cmock拦截, 请求: ', 'color:blue', opts)
                        console.log('%cmock拦截, 响应: ', 'color:blue', res.data)
                        return res.data
                    })
                }
            })(mod[key]() || {})
        }
    }
}