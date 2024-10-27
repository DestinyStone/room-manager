const routes = [
  {
    path: '/',
    redirect: '/admin',
  },
  {
    path: '/admin',
    name: "admin",
    component: () => import("@/views/home/HomeMain"),
    children: [
      {
        path: '/group-manager',
        name: "group-manager",
        component: () => import("@/views/admin/group/GroupManager"),
        meta: {
          title: "客房分类管理",
          routes: [
            { title: "信息管理"},
            { path: "/group-manager", title: "客房分类管理"},
          ]
        }
      },
      {
        path: '/room-manager',
        name: "room-manager",
        component: () => import("@/views/admin/room/RoomManager"),
        meta: {
          title: "宾馆客房管理",
          routes: [
            { title: "信息管理"},
            { path: "/room-manager", title: "宾馆客房管理"},
          ]
        }
      },
    ],
  },
  {
    path: '/login',
    name: 'login',
    component: () => import("@/views/auth/LoginPage"),
  },
]
export default routes;
