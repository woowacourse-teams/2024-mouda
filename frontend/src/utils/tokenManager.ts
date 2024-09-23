const TOKEN_KEY = 'access-token';

export const setToken = (token: string): void => {
  if (process.env.MSW !== 'true') {
    localStorage.setItem(TOKEN_KEY, token);
  }
};

// TODO: token 관리 로직 추가 필요(토큰 존재, 토큰 만료)
export const getToken = () => {
  if (process.env.MSW !== 'true') {
    const token = localStorage.getItem(TOKEN_KEY);
    return token;
  } else {
    return 'random';
  }
};

export const removeToken = (): void => {
  localStorage.removeItem(TOKEN_KEY);
};
