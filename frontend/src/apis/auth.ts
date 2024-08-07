import ApiClient from './apiClient';

export const login = async (loginInputInfo: { nickname: string }) => {
  const response = await ApiClient.postWithoutAuth(
    'auth/login',
    loginInputInfo,
  );
  return response.json();
};
