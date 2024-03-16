//請求實例

import axios from "axios";
import { ElMessage } from "element-plus";
import router from "@/router";

const baseURL = "/api";
const instance = axios.create({ baseURL });

//request攔截器
import { useTokenStore } from "@/stores/token.js";

instance.interceptors.request.use(
  (config) => {
    const tokenStore = useTokenStore();
    if (tokenStore.token) {
      config.headers.Authorization = tokenStore.token;
    }
    return config;
  },
  (err) => {
    Promise.reject(err);
  }
);

//response攔截器
instance.interceptors.response.use(
  (result) => {
    if (result.data.code == 0) {
      return result.data;
    }

    ElMessage.error(result.data.msg ? result.data.msg : "服務異常");
  },
  (err) => {
    if (err.response.status == 401) {
      ("請登入");
      router.push("/users");
    } else {
      ElMessage.error("服務異常");
    }

    return Promise.reject(err);
  }
);

export default instance;
