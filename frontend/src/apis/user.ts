import { checkStatus, defaultOptions } from './apiconfig';
import ENDPOINTS from './endPoints';

export const login = async (loginInputInfo: { nickname: string }) => {
  const url = `${ENDPOINTS.auth}/login`;

  const requestBody = {
    method: 'POST',
    defaultOptions,
    body: JSON.stringify(loginInputInfo),
  };

  const response = await fetch(url, requestBody);
  console.log(response);

  checkStatus(response);

  const json = await response.json();
  return json.data;
};
