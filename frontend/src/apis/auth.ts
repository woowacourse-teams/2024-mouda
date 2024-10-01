import ApiClient from './apiClient';

export const login = async (loginInputInfo: { nickname: string }) => {
  const response = await ApiClient.postWithoutAuth(
    '/auth/login',
    loginInputInfo,
  );
  return response.json();
};

export const kakaoOAuth = async (code: string) => {
  const response = await ApiClient.postWithoutAuth('/auth/kakao/oauth', {
    code,
  });
  return response.json();
};
export const appleOAuth = async (code: string) => {
  const response = await ApiClient.postWithoutAuth('/auth/apple/oauth', {
    code,
  });
  return response.json();
};

export const googleOAuth = async (code: string) => {
  const response = await ApiClient.postWithoutAuth('/auth/apple/oauth', {
    code,
  });
  return response.json();
};
