import { getAccessToken } from './tokenManager';

// TODO: 정확한 auth 검증 필요 (지금은 token이 localstorage에 있어도 만료여부를 모름)
export const checkAuthentication = () => {
  const token = getAccessToken();
  return !!token;
};
