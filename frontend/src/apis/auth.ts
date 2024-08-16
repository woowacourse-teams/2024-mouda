import ApiClient from './apiClient';

export const login = async (loginInputInfo: { nickname: string }) => {
  const response = await ApiClient.postWithoutAuth(
    'auth/login',
    loginInputInfo,
  );
  return response.json();
};

export const kakaoOAuth = async (code: string) => {
  const response = await ApiClient.postWithoutAuth('auth/kakao/oauth', {
    code,
  });
  console.log(response);
  return response.json();
};
