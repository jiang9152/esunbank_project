import request from "@/utils/request.js";

// 查看所有可借的書
export const getAllBookSvc = () => {
  return request.get("/book");
};

// 查看已借書的紀錄
export const getBorrowedBookSvc = () => {
  return request.get("/book/mine");
};

// 查看書的細項
export const getBookDetailSvc = (id) => {
  return request.get("/book/" + id);
};

// 借書
export const borrowBookSvc = (inventoryId) => {
  const params = new URLSearchParams();
  params.append("inventoryId", inventoryId);
  return request.post("/book", params);
};

// 還書
export const returnBookSvc = (inventoryId) => {
  const params = new URLSearchParams();
  params.append("inventoryId", inventoryId);
  return request.post("/book/mine", params);
};
