<script setup>
import { Edit, Search } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  getBorrowedBookSvc,
  getBookDetailSvc,
  returnBookSvc,
} from "@/api/book.js";

// borrowRecord物件
const borrowRecord = ref([
  {
    recordId: 0,
    userId: 0,
    inventoryId: 0,
    borrowingTime: "",
    returnTime: "",
  },
]);

// 查看已借書的紀錄
const listBorrowed = async () => {
  let result = await getBorrowedBookSvc();
  borrowRecord.value = result.data;
};
listBorrowed();

// 查看書的細項彈窗
const open = async (rowData) => {
  let result = await getBookDetailSvc(rowData.inventoryId);
  let content =
    "ISBN:" +
    result.data.isbn +
    "  書名:" +
    result.data.name +
    "  作者:" +
    result.data.author;
  ElMessageBox.alert(content, "書籍編號:" + rowData.inventoryId);
};

// 還書
const returnBook = (rowData) => {
  ElMessageBox.confirm("確認歸還此書?", "提示", {
    confirmButtonText: "確認",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await returnBookSvc(rowData.inventoryId);
      ElMessage.success(result.msg ? result.msg : "歸還成功");
      listBorrowed();
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消歸還",
      });
    });
};
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>我的書籍</span>
      </div></template
    >
    <el-table :data="borrowRecord" style="width: 100%">
      <el-table-column width="50" type="index"></el-table-column>
      <el-table-column prop="inventoryId" label="書籍編號" />
      <el-table-column prop="borrowingTime" label="借出時間" />
      <el-table-column label="歸還/查看" width="150">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="returnBook(row)"
          ></el-button>
          <el-button
            :icon="Search"
            circle
            plain
            type="success"
            @click="open(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="無借閱紀錄" />
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
