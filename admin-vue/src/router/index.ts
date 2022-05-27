import {createRouter, createWebHashHistory} from "vue-router";
import {User} from "../entity/User";

const routes: any = [
    {
        path: "/:pathMatch(.*)",
        name: "404",
        meta: {
            title: "页面不存在"
        },
        component: () => import("../views/error/404.vue")
    },
    {
        path: "/403",
        name: "403",
        meta: {
            title: "没有权限"
        },
        component: () => import ("../views/error/403.vue")
    },
    {
        path: "/",
        redirect: `/dashboard`
    },
    {
        path: `/login`,
        name: "Login",
        meta: {
            title: "登录"
        },
        component: () => import ("../views/Login.vue")
    },
    {
        path: "/",
        name: "Home",
        component: () => import("../views/Home.vue"),
        children: [
            {
                path: `/dashboard`,
                name: "dashboard",
                meta: {
                    title: "系统首页",
                    perms: "admin"
                },
                component: () => import ("../views/Dashboard.vue")
            },
            {
                path: `/users`,
                name: "users",
                meta: {
                    title: "用户列表",
                    perms: "root"
                },
                component: () => import ("../views/Users.vue")
            },
            {
                path: `/message`,
                name: "message",
                meta: {
                    title: "消息发送",
                    perms: "admin"
                },
                component: () => import ("../views/Message.vue")
            },
            {
                path: `/mail`,
                name: "mail",
                meta: {
                    title: "用户邮件",
                    perms: "admin"
                },
                component: () => import ("../views/Mail.vue")
            },
            {
                path: `/upload`,
                name: "upload",
                meta: {
                    title: "上传文件",
                    perms: "admin"
                },
                component: () => import ("../views/Upload.vue")
            },
            {
                path: `/user`,
                name: "user",
                meta: {
                    title: "个人中心",
                    perms: "admin"
                },
                component: () => import ("../views/User.vue")
            },
            {
                path: `/history`,
                name: "history",
                meta: {
                    title: "历史记录",
                    perms: "root"
                },
                component: () => import ("../views/History.vue")
            }
        ]
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to: any, from, next) => {
    document.title = `${to.meta.title} | admin-system`;

    let user: User = JSON.parse(sessionStorage.getItem(User.key));

    if (user === null && to.path !== `/login`) {
        next(`/login`);
    } else if (to.meta.perms) {
        isPerms(user.perms, to.meta.perms) ? next() : next('/403');
    } else {
        next();
    }
});

// 根据|符号分割，是否拥有某个权限
const isPerms = (roleArray, permsArray) => {
    let roles = roleArray.split(",");
    let perms = permsArray.split("|");
    for (let i = 0; i < perms.length; i++) {
        for (let j = 0; j < roles.length; j++) {
            if (perms[i] === roles[j]) {
                return true;
            }
        }
    }
    return false;
}

// 获取语言环境
const getLang = (route) => {
    let values = route.split("/");
    if (values.length !== 0) {
        return values[1];
    }
}

export default router;