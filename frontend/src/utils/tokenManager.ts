const TOKEN_KEY = 'access-token';

export const setToken = (token: string): void => {
  localStorage.setItem(TOKEN_KEY, token);
};

// TODO: token 관리 로직 추가 필요(토큰 존재, 토큰 만료)
export const getToken = (): string => {
  const token = localStorage.getItem(TOKEN_KEY);
  if (token === null) {
    throw new Error('로컬 스토리지에 토큰이 없습니다.');
  }
  return token;
};

export const removeToken = (): void => {
  localStorage.removeItem(TOKEN_KEY);
};
