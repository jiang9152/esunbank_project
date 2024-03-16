import { defineStore } from "pinia";
import { ref } from "vue";

export const useTokenStore = defineStore(
  "token",
  () => {
    const token = ref("");
    // 儲存token
    const setToken = (newToken) => {
      token.value = newToken;
    };
    //移除token
    const removeToken = () => {
      token.value = "";
    };

    return { token, setToken, removeToken };
  },
  { persist: true } //持久化
);
