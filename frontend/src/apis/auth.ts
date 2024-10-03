import ApiClient from './apiClient';

export const kakaoOAuth = async (code: string) => {
  const response = await ApiClient.postWithoutAuth('/auth/kakao/oauth', {
    code,
  });
  return response.json();
};
export const appleOAuth = async (
  code: string,
  memberId: string | null = null,
) => {
  const response = await ApiClient.postWithoutAuth('/auth/apple/oauth', {
    code,
    memberId,
  });
  return response.json();
};

export const googleOAuth = async (
  code: string,
  memberId: string | null = null,
) => {
  const response = await ApiClient.postWithoutAuth('/auth/google/oauth', {
    code,
    memberId,
  });
  return response.json();
};
