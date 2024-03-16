import request from "@/utils/request.js";

// 註冊
export const userRegisterSvc = (registerData) => {
  const params = new URLSearchParams();
  for (let key in registerData) {
    params.append(key, registerData[key]);
  }

  return request.post("/user/new", params);
};

// 登入
export const userLoginSvc = (loginData) => {
  const params = new URLSearchParams();
  for (let key in loginData) {
    params.append(key, loginData[key]);
  }

  return request.post("/user", params);
};

// 登出
export const userLogoutSvc = () => {
  return request.get("/user");
};
