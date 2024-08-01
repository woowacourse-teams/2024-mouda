import { getToken } from '@_utils/tokenManager';

type Method = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

const DEFAULT_HEADERS = {
  'Content-Type': 'application/json',
};

const BASE_URL = `${process.env.BASE_URL}/v1`;

class ApiClient {
  private static addBaseURL(endpoint: string) {
    if (endpoint[0] !== '/') endpoint = '/' + endpoint;
    return BASE_URL + endpoint;
  }

  private static getHeaders(token?: string) {
    const headers = new Headers(DEFAULT_HEADERS);
    if (token) {
      headers.append('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  private static async request(
    method: Method,
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
    isRequiredAuth: boolean = false,
  ) {
    const url = this.addBaseURL(endpoint);
    const token = isRequiredAuth ? getToken() : undefined;

    const options: RequestInit = {
      method,
      headers: this.getHeaders(token),
      ...config,
    };

    if (method !== 'GET') {
      options.body = JSON.stringify(data);
    }

    return await fetch(url, options);
  }

  private static async get(
    endpoint: string,
    config: RequestInit = {},
    isRequiredAuth: boolean = false,
  ) {
    return this.request('GET', endpoint, {}, config, isRequiredAuth);
  }

  static async getWithoutAuth(endpoint: string, config: RequestInit = {}) {
    return this.get(endpoint, config, false);
  }

  static async getWithAuth(endpoint: string, config: RequestInit = {}) {
    return this.get(endpoint, config, true);
  }

  private static async post(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
    isRequiredAuth: boolean = false,
  ) {
    return this.request('POST', endpoint, data, config, isRequiredAuth);
  }

  static async postWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return this.post(endpoint, data, config, true);
  }

  static async postWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return this.post(endpoint, data, config, false);
  }

  private static async put(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
    isRequiredAuth: boolean = false,
  ) {
    return this.request('PUT', endpoint, data, config, isRequiredAuth);
  }

  static async putWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return this.put(endpoint, data, config, true);
  }

  static async putWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return this.put(endpoint, data, config, false);
  }

  private static async patch(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
    isRequiredAuth: boolean = false,
  ) {
    return this.request('PATCH', endpoint, data, config, isRequiredAuth);
  }

  static async patchWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return this.patch(endpoint, data, config, true);
  }

  static async patchWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return this.patch(endpoint, data, config, false);
  }

  private static async delete(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
    isRequiredAuth: boolean = false,
  ) {
    return this.request('DELETE', endpoint, data, config, isRequiredAuth);
  }

  static async deleteWithAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return this.delete(endpoint, data, config, true);
  }

  static async deleteWithoutAuth(
    endpoint: string,
    data: object = {},
    config: RequestInit = {},
  ) {
    return this.delete(endpoint, data, config, false);
  }
}

export default ApiClient;
