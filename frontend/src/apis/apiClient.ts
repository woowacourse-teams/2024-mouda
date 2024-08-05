import { getToken } from '@_utils/tokenManager';

type Method = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

const DEFAULT_HEADERS = {
  'Content-Type': 'application/json',
};

const BASE_URL = `${process.env.BASE_URL}/v1`;

function addBaseUrl(endpoint: string) {
  if (endpoint[0] !== '/') endpoint = '/' + endpoint;
  return BASE_URL + endpoint;
}

function getHeaders(token?: string) {
  const headers = new Headers(DEFAULT_HEADERS);
  if (token) {
    headers.append('Authorization', `Bearer ${token}`);
  }
  return headers;
}

const checkStatus = async (response: Response) => {
  const statusHead = Math.floor(response.status / 100);
  if (statusHead === 4 || statusHead === 5) {
    const json = await response.json();
    throw new Error(json.message);
  }
  return response;
};

async function request(
  method: Method,
  endpoint: string,
  data: object = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
) {
  const url = addBaseUrl(endpoint);
  const token = isRequiredAuth ? getToken() : undefined;

  const options: RequestInit = {
    method,
    headers: getHeaders(token),
    ...config,
  };

  if (method !== 'GET') {
    options.body = JSON.stringify(data);
  }

  const response = await fetch(url, options);

  if (response.status === 401) {
    throw new Error('Unauthorized');
  }

  if (!response.ok) {
    throw new Error('API request failed');
  }

  checkStatus(response);

  return response;
}

async function get(
  endpoint: string,
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
) {
  return request('GET', endpoint, {}, config, isRequiredAuth);
}

async function post(
  endpoint: string,
  data: object = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
) {
  return request('POST', endpoint, data, config, isRequiredAuth);
}

async function put(
  endpoint: string,
  data: object = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
) {
  return request('PUT', endpoint, data, config, isRequiredAuth);
}

async function patch(
  endpoint: string,
  data: object = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
) {
  return request('PATCH', endpoint, data, config, isRequiredAuth);
}

/**
 * delete는 예약어로 사용할 수 없는 함수 이름이라 부득이 `deleteMethod`로 이름을 지었습니다.
 */
async function deleteMethod(
  endpoint: string,
  data: object = {},
  config: RequestInit = {},
  isRequiredAuth: boolean = false,
) {
  return request('DELETE', endpoint, data, config, isRequiredAuth);
}

const ApiClient = {
  async getWithoutAuth(endpoint: string, config: RequestInit = {}) {
    return get(endpoint, config, false);
  },
  async getWithAuth(endpoint: string, config: RequestInit = {}) {
    return get(endpoint, config, true);
  },

  async postWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return post(endpoint, data, config, true);
  },
  async postWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return post(endpoint, data, config, false);
  },

  async putWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return put(endpoint, data, config, true);
  },
  async putWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return put(endpoint, data, config, false);
  },

  async patchWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return patch(endpoint, data, config, true);
  },
  async patchWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return patch(endpoint, data, config, false);
  },

  async deleteWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return deleteMethod(endpoint, data, config, true);
  },
  async deleteWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return deleteMethod(endpoint, data, config, false);
  },
};

export default ApiClient;
