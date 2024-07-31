import { getToken } from './tokenManager';

export const checkAuthentication = () => {
  return !!getToken();
};
