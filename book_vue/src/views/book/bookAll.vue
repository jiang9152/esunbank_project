<script setup>
import { Edit } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { getAllBookSvc, borrowBookSvc } from "@/api/book.js";

// book物件
const book = ref([
  {
    isbn: "",
    name: "",
    author: "",
    introduction: "",
    inventoryId: 0,
  },
]);

// 查看所有可借的書
const listAll = async () => {
  let result = await getAllBookSvc();
  book.value = result.data;
};
listAll();

// 借書
const borrow = (row) => {
  ElMessageBox.confirm("是否要借閱此書?", "提示", {
    confirmButtonText: "確認",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await borrowBookSvc(row.inventoryId);
      ElMessage.success(result.msg ? result.msg : "借閱成功");
      listAll();
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消借閱",
      });
    });
};
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>所有書籍</span>
      </div>
    </template>
    <el-table :data="book" style="width: 100%">
      <el-table-column width="50" type="index"></el-table-column>
      <el-table-column label="ISBN" prop="isbn"></el-table-column>
      <el-table-column label="書名" prop="name"></el-table-column>
      <el-table-column label="作者" prop="author"></el-table-column>
      <el-table-column label="介紹" prop="introduction"></el-table-column>
      <el-table-column label="借閱" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="borrow(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="目前無書籍可供借閱"></el-empty>
      </template>
    </el-table>
  </el-card>
</template>

<style scoped lang="scss">
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
