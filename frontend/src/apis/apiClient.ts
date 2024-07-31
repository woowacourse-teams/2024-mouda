const defaultHeaders = {
  'Content-Type': 'application/json',
};

const baseURL = process.env.BASE_URL;

class ApiClient {
  private static addBaseURL(url: string) {
    if (url[0] !== '/') url = '/' + url;
    return baseURL + '/v1' + url;
  }

  private static getHeaders(token?: string) {
    const headers = new Headers(defaultHeaders);
    if (token) {
      headers.append('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  static async get(path = '', config = {}) {
    const url = this.addBaseURL(path);

    const res = await fetch(url, {
      method: 'GET',
      headers: this.getHeaders(),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    return res.json();
  }

  static async post(path = '', data = {}, config = {}) {
    const url = this.addBaseURL(path);

    const res = await fetch(url, {
      method: 'POST',
      headers: this.getHeaders(),
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

  static async put(path = '', data = {}, config = {}) {
    const url = this.addBaseURL(path);

    const res = await fetch(url, {
      method: 'PUT',
      headers: this.getHeaders(),
      body: JSON.stringify(data),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    return res.json();
  }

  static async patch(path = '', data = {}, config = {}) {
    const url = this.addBaseURL(path);

    const res = await fetch(url, {
      method: 'patch',
      headers: this.getHeaders(),
      body: JSON.stringify(data),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    return res.json();
  }

  static async delete(path = '', data = {}, config = {}) {
    const url = this.addBaseURL(path);

    const res = await fetch(url, {
      method: 'DELETE',
      headers: this.getHeaders(),
      body: JSON.stringify(data),
      ...config,
    });

    if (!res.ok) {
      console.log(res);
      throw new Error(res.statusText);
    }

    return res.json();
  }
}

export default ApiClient;
