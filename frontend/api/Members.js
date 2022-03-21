// 로그인
// 회원가입
// 로그아웃
import API from "./API";

function RIKEYSignIn(data, success, fail) {
  API.POST('/users/login', JSON.stringify({authId: data})).then(success).catch(fail);
  console.log(data, JSON.stringify({authId: data}));
}

export { RIKEYSignIn };