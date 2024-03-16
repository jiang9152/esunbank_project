<script setup>
import { ref } from "vue";
import { useUserInfoStore } from "@/stores/userinfo.js";

const infoStore = useUserInfoStore();

const userInfo = ref({ ...infoStore.info });

const rules = {
  nickname: [
    { required: true, message: "請輸入暱稱", trigger: "blur" },
    { pattern: /^\${2,10$/, message: "請輸入2-10個字", trigger: "blur" },
  ],
  email: [
    { required: true, message: "請輸入email", trigger: "blur" },
    { type: "email", message: "email格式不正確", trigger: "blur" },
  ],
};

import { userInfoUpdSvc } from "@/api/user.js";
import { ElMessage } from "element-plus";
const updateUserInfo = async () => {
  let result = await userInfoUpdSvc(userInfo.value);
  ElMessage.success(result.msg ? result.msg : "更新成功");

  infoStore.setInfo(userInfo.value);
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>基本資料</span>
      </div>
    </template>
    <el-row :span="12">
      <el-form
        :model="userInfo"
        :rules="rules"
        label-width="100px"
        size="large"
      >
        <el-form-item label="使用者姓名">
          <el-input v-model="userInfo.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="使用者暱稱" prop="nickname">
          <el-input v-model="userInfo.nickname"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="userInfo.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateUserInfo">儲存</el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </el-card>
</template>
