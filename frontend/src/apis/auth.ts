import ApiClient from './apiClient';
import { checkStatus } from './apiconfig';

export const login = async (loginInputInfo: { nickname: string }) => {
  const response = await ApiClient.postWithoutAuth(
    'auth/login',
    loginInputInfo,
  );
  checkStatus(response);
  return response.json();
};
