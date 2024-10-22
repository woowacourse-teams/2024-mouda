import ApiClient from './apiClient';

export const kakaoOAuth = async (code: string) => {
  await ApiClient.postWithAuth('/auth/kakao', {
    code,
  });
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
