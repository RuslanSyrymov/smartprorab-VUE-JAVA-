import { createRouter, createWebHistory } from 'vue-router';
import ProductList from '@/components/ProductList.vue';
import EditProduct from '@/components/EditProduct.vue';
import CreateProduct from '@/components/CreateProduct.vue';
import LoginUser from '@/components/LoginUser.vue';

const routes = [
    {
        path: '/',
        component: ProductList,
        meta:{
            isGuest:true
        }
    },
    {
        path: '/products/create',
        component: CreateProduct,
        meta: {
            requiresAuth: true,
            requiredRole: 'all',
            menu: true
        }
    },
    {
        path: '/products/:id/edit',
        component: EditProduct,
        meta: {
            requiresAuth: true,
            requiredRole: 'all',
            menu: true
        }
    },
    {
        path: '/login',
        component: LoginUser,
        meta:{
            isGuest:true
        }
    },
];
const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (localStorage.getItem("access_token") === undefined || localStorage.getItem("access_token") === null) {
            next('/login')
        }
        // else {
        //     if (to.matched.some(record => record.meta.requiredRole === "admin")) {
        //         if( localStorage.getItem('user_role') === "admin" ){
        //             next();
        //         }else{
        //             next('/unauthorized');
        //         }
        //     }else{
        //         if(to.matched.some(record => record.meta.requiredRole !== "all")){
        //             if( localStorage.getItem('user_role') === "admin" || localStorage.getItem('user_role') === to.meta.requiredRole){
        //                 next();
        //             }else{
        //                 next('/unauthorized');
        //             }
        //         }else {
        //             next();
        //         }
        //     }
        // }
    } else if(to.matched.some(record => record.meta.isGuest)){
        next();
    }else{
        next('/login');
    }
});



export default router;