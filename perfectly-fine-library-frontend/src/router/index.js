import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
      path:'/',
      name:'Main',
      component:()=>import('../views/Main'),
      children:[
          {
              path:'',
              alias:'home',
              component:()=>import('../components/library/Books')
          },{
              path:'user',
              component:()=>import('../components/user/User'),
              children:[
                  {
                      path:'changePassword',
                      component:()=>import('../components/user/ChangePassword')
                  },{
                      path:'myBorrow',
                      component:()=>import('../components/user/MyBorrow')
                  },{
                      path: 'myReserve',
                      component:()=>import('../components/user/MyReserve')
                  },{
                      path:'myInformation',
                      component:()=>import('../components/user/MyInformation')
                  },{
                      path:'myRecord',
                      component:()=>import('../components/user/MyRecord')
                  },{
                      path: 'myComment',
                      component:()=>import('../components/user/MyComment')
                  }
              ]
          },{
              path:'admin',
              component:()=>import('../components/admin/Admin'),
              children: [
                  {
                      path: 'borrow',
                      component:()=>import('../components/admin/Borrow')
                  },{
                      path: 'return',
                      component:()=>import('../components/admin/Return')
                  },{
                      path: 'getReserved',
                      component:()=>import('../components/admin/GetReserved')
                  },{
                      path:'damage',
                      component:()=>import('../components/admin/Damage')
                  }
              ]
          },{
              path:'upload',
              component:()=>import('../components/admin/Upload')
          },{
              path:'addAdmin',
              component:()=>import('../components/super/AddAdmin')
          },{
              path: 'detail',
              component:()=>import('../components/library/Detail')
          },{
              path:'config',
              component:()=>import('../components/super/ModifyConfig')
          },{
              path:'record',
              component:()=>import('../components/admin/Record')
          }, {
              path: 'sendEmail',
              component: () => import('../components/super/SendEmail')
          },{
              path:'reset',
              component:()=>import('../components/super/Credit')
          }
      ]
  },{
        path:'/login',
        name:'form',
        component:()=>import('../views/Form'),
        children: [
            {
                path: 'user',
                component:()=>import('../components/user/UserLoginForm')
            },{
                path: 'admin',
                component:()=>import('../components/admin/AdminLoginForm')
            }
        ]
    },{
    path: "/register",
        component:()=>import('../views/Form'),
        children: [
            {
                path: 'user',
                component:()=>import('../components/user/UserRegisterForm')
            }
        ]
    },{
    //404重定向到首页
        path: "/:pathMatch(.*)*",
        redirect:"/"
    }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
});

export default router

