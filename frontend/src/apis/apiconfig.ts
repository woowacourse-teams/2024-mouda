export const defaultHeaders = {
  'Content-Type': 'application/json',
};

export const defaultOptions = {
  headers: defaultHeaders,
};

export const checkStatus = async (response: Response) => {
  const statusHead = Math.floor(response.status / 100);
  if (statusHead === 4 || statusHead === 5) {
    const json = await response.json();
    throw new Error(json.message);
  }
  return response;
};
