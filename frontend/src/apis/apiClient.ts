import { ApiError } from '@_utils/customError/ApiError';
import { getAccessToken } from '@_utils/tokenManager';
import { addBaseUrl } from './endPoints';

type Method = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

// const DEFAULT_HEADERS = {
//   'Content-Type': 'application/json',
// };

function getHeaders(isRequiredAuth: boolean, isFormData: boolean = false) {
  const headers = new Headers();

  // FormData가 아닌 경우에만 Content-Type 설정
  if (!isFormData) {
    headers.append('Content-Type', 'application/json');
  }

  // 인증이 필요한 경우 Authorization 헤더 추가
  if (isRequiredAuth) {
    const token = getAccessToken();
    headers.append('Authorization', `Bearer ${token}`);
  }

  return headers;
}

async function request(
  method: Method,
  endpoint: string,
  data: object | FormData = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
  isRequiredLastDarakbang: boolean = false,
) {
  const url = addBaseUrl(endpoint, isRequiredLastDarakbang);
  // data가 FormData인지 확인하여 헤더 설정
  const isFormData = data instanceof FormData;
  const headers = getHeaders(isRequiredAuth, isFormData);
  const options: RequestInit = {
    method,
    headers: headers,
    ...config,
  };

  if (method !== 'GET') {
    options.body = isFormData ? data : JSON.stringify(data);
  }

  const response = await fetch(url, options);

  if (!response.ok) {
    const json = await response.json();
    throw new ApiError(response.status, json.message);
  }

  return response;
}

async function get(
  endpoint: string,
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
  isRequiredLastDarakbang: boolean = false,
) {
  return request(
    'GET',
    endpoint,
    {},
    config,
    isRequiredAuth,
    isRequiredLastDarakbang,
  );
}

async function post(
  endpoint: string,
  data: object | FormData = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
  isRequiredLastDarakbang: boolean = false,
) {
  return request(
    'POST',
    endpoint,
    data,
    config,
    isRequiredAuth,
    isRequiredLastDarakbang,
  );
}

async function put(
  endpoint: string,
  data: object = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
  isRequiredLastDarakbang: boolean = false,
) {
  return request(
    'PUT',
    endpoint,
    data,
    config,
    isRequiredAuth,
    isRequiredLastDarakbang,
  );
}

async function patch(
  endpoint: string,
  data: object = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
  isRequiredLastDarakbang: boolean = false,
) {
  return request(
    'PATCH',
    endpoint,
    data,
    config,
    isRequiredAuth,
    isRequiredLastDarakbang,
  );
}

/**
 * delete는 예약어로 사용할 수 없는 함수 이름이라 부득이 `deleteMethod`로 이름을 지었습니다.
 */
async function deleteMethod(
  endpoint: string,
  data: object = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
  isRequiredLastDarakbang: boolean = false,
) {
  return request(
    'DELETE',
    endpoint,
    data,
    config,
    isRequiredAuth,
    isRequiredLastDarakbang,
  );
}

const ApiClient = {
  async getWithoutAuth(endpoint: string, config: RequestInit = {}) {
    return get(endpoint, config, false);
  },
  async getWithAuth(endpoint: string, config: RequestInit = {}) {
    return get(endpoint, config, true);
  },
  async getWithLastDarakbangId(endpoint: string, config: RequestInit = {}) {
    return get(endpoint, config, true, true);
  },

  async postWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return post(endpoint, data, config, false);
  },
  async postWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return post(endpoint, data, config, true);
  },
  async postWithLastDarakbangId(
    endpoint: string,
    data: object | FormData = {},
    config: RequestInit = {},
  ) {
    return post(endpoint, data, config, true, true);
  },

  async putWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return put(endpoint, data, config, false);
  },
  async putWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return put(endpoint, data, config, true);
  },
  async putWithLastDarakbangId(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return put(endpoint, data, config, true, true);
  },

  async patchWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return patch(endpoint, data, config, false);
  },
  async patchWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return patch(endpoint, data, config, true);
  },
  async patchWithLastDarakbangId(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return patch(endpoint, data, config, true, true);
  },

  async deleteWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return deleteMethod(endpoint, data, config, false);
  },
  async deleteWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return deleteMethod(endpoint, data, config, true);
  },
  async deleteWithLastDarakbangId(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return deleteMethod(endpoint, data, config, true, true);
  },
};

export default ApiClient;
