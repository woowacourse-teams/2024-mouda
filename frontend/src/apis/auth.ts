import ApiClient from './apiClient';

export const login = async (loginInputInfo: { nickname: string }) => {
  return await ApiClient.postWithoutAuth('auth/login', loginInputInfo);
};
