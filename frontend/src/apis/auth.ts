import ApiClient from './apiClient';

export const kakaoOAuth = async (code: string) => {
  const response = await ApiClient.postWithAuth('/auth/kakao', {
    code,
  });
  return response.json();
};
export const googleOAuth = async (
  idToken: string,
  memberId: string | null = null,
) => {
  const response = await ApiClient.postWithoutAuth('/auth/google', {
    idToken,
    memberId,
  });
  return response.json();
};
