export const defaultHeaders = {
  'Content-Type': 'application/json',
};

export const defaultOptions = {
  headers: defaultHeaders,
};

export const checkStatus = (response: Response) => {
  const statusHead = Math.floor(response.status / 100);
  if (statusHead === 4 || statusHead === 5) {
    throw new Error('요청을 처리하지 못했습니다.');
  }
  return response;
};
