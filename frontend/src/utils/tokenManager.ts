const ACCESS_TOKEN_KEY = 'access-token';
const MEMBER_TOKEN_KEY = 'member-id';
export const setAccessToken = (token: string): void => {
  if (process.env.MSW !== 'true') {
    localStorage.setItem(ACCESS_TOKEN_KEY, token);
  }
};

// TODO: token 관리 로직 추가 필요(토큰 존재, 토큰 만료)
export const getAccessToken = () => {
  if (process.env.MSW !== 'true') {
    const token = localStorage.getItem(ACCESS_TOKEN_KEY);
    return token;
  } else {
    return 'random';
  }
};

export const removeAccessToken = (): void => {
  localStorage.removeItem(ACCESS_TOKEN_KEY);
};

export const setMemberToken = (token: string): void => {
  if (process.env.MSW !== 'true') {
    sessionStorage.setItem(MEMBER_TOKEN_KEY, token);
  }
};

// TODO: token 관리 로직 추가 필요(토큰 존재, 토큰 만료)
export const getMemberToken = () => {
  if (process.env.MSW !== 'true') {
    const token = sessionStorage.getItem(MEMBER_TOKEN_KEY);
    return token;
  } else {
    return 'random';
  }
};

export const removeMemberToken = (): void => {
  sessionStorage.removeItem(MEMBER_TOKEN_KEY);
};
