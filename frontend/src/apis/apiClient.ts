import { getToken } from '@_utils/tokenManager';

const defaultHeaders = {
  'Content-Type': 'application/json',
};

const baseURL = process.env.BASE_URL;

class ApiClient {
  private static addBaseURL(url: string) {
    if (url[0] !== '/') url = '/' + url;
    return baseURL + '/v1' + url;
  }

  private static getHeaders(token: string | null) {
    const headers = new Headers(defaultHeaders);
    if (token) {
      headers.append('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  static async get(path = '', config = {}, isRequiredAuth = false) {
    const url = this.addBaseURL(path);
    const token = isRequiredAuth ? getToken() : null;

    const res = await fetch(url, {
      method: 'GET',
      headers: this.getHeaders(token),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    return res.json();
  }

  static async getWithAuth(path = '', config = {}) {
    return this.get(path, config, true);
  }

  static async post(path = '', data = {}, config = {}, isRequiredAuth = false) {
    const url = this.addBaseURL(path);
    const token = isRequiredAuth ? getToken() : null;

    const res = await fetch(url, {
      method: 'POST',
      headers: this.getHeaders(token),
      body: JSON.stringify(data),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    const contentType = res.headers.get('Content-Type');
    if (contentType && contentType.includes('application/json')) {
      return res.json();
    }

    return;
  }

  static async postWithAuth(path = '', data = {}, config = {}) {
    return this.post(path, data, config, true);
  }

  static async put(path = '', data = {}, config = {}, isRequiredAuth = false) {
    const url = this.addBaseURL(path);
    const token = isRequiredAuth ? getToken() : null;

    const res = await fetch(url, {
      method: 'PUT',
      headers: this.getHeaders(token),
      body: JSON.stringify(data),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    return res.json();
  }

  static async putWithAuth(path = '', data = {}, config = {}) {
    return this.put(path, data, config, true);
  }

  static async patch(
    path = '',
    data = {},
    config = {},
    isRequiredAuth = false,
  ) {
    const url = this.addBaseURL(path);
    const token = isRequiredAuth ? getToken() : null;

    const res = await fetch(url, {
      method: 'patch',
      headers: this.getHeaders(token),
      body: JSON.stringify(data),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    return res.json();
  }

  static async patchWithAuth(path = '', data = {}, config = {}) {
    return this.patch(path, data, config, true);
  }

  static async delete(
    path = '',
    data = {},
    config = {},
    isRequiredAuth = false,
  ) {
    const url = this.addBaseURL(path);
    const token = isRequiredAuth ? getToken() : null;

    const res = await fetch(url, {
      method: 'DELETE',
      headers: this.getHeaders(token),
      body: JSON.stringify(data),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    return res.json();
  }

  static async deleteWithAuth(path = '', data = {}, config = {}) {
    return this.delete(path, data, config, true);
  }
}

export default ApiClient;
