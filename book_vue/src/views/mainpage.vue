<script setup>
import {
  Management,
  Promotion,
  UserFilled,
  SwitchButton,
  CaretBottom,
} from "@element-plus/icons-vue";
import { useTokenStore } from "@/stores/token.js";
import { getAllBookSvc } from "@/api/book.js";
import { useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { userLogoutSvc } from "@/api/user.js";

// token存儲
const tokenStore = useTokenStore();

// 觸發未登入驗證
const getBook = async () => {
  await getAllBookSvc();
};
getBook();

// 路由
const router = useRouter();

//登出
const handleCommand = (command) => {
  if (command == "logout") {
    ElMessageBox.confirm("是否確認要登出?", "提示", {
      confirmButtonText: "確認",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        await userLogoutSvc();
        tokenStore.removeToken();
        // infoStore.removeInfo();
        router.push("/users");
        ElMessage({
          type: "success",
          message: "登出成功",
        });
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "取消登出",
        });
      });
  }
};
</script>

<template>
  <el-container class="layout-container">
    <!---左側-->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        text-color="#fff"
        router
      >
        <el-menu-item index="/all">
          <el-icon><Management /></el-icon>
          <span>所有書籍</span>
        </el-menu-item>
        <el-menu-item index="/mine">
          <el-icon><Promotion /></el-icon>
          <span>我的書籍</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!---右側-->
    <el-container>
      <!--上-->
      <el-header>
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar :icon="UserFilled" />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >登出</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!--中-->
      <el-main>
        <router-view></router-view>
      </el-main>
      <!--下-->
      <el-footer>E.SUN BANK project</el-footer>
    </el-container>
  </el-container>
</template>

<style scoped lang="scss">
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>
