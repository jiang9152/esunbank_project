import { createRouter, createWebHistory } from "vue-router";

import LoginVue from "@/views/login.vue";
import MainVue from "@/views/mainpage.vue";
import bookAllVue from "@/views/book/bookAll.vue";
import bookMineVue from "@/views/book/bookMine.vue";

const routes = [
  { path: "/users", component: LoginVue },
  {
    path: "/",
    component: MainVue,
    children: [
      { path: "/all", component: bookAllVue },
      { path: "/mine", component: bookMineVue },
    ],
  },
];

const router = createRouter({
  routes: routes,
  history: createWebHistory(),
});

export default router;
