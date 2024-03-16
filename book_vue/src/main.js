//import "./assets/main.css";

import { createApp } from "vue";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import router from "@/router";
import { createPinia } from "pinia";
import App from "./App.vue";
import { createPersistedState } from "pinia-persistedstate-plugin";
import locale from "element-plus/es/locale/lang/zh-tw";

const app = createApp(App);
const pinia = createPinia();
const persist = createPersistedState();

pinia.use(persist);
app.use(pinia);
app.use(router);
app.use(ElementPlus, { locale });

app.mount("#app");
