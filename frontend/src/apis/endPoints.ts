import ENV from '@_apis/env';

const getEndpoint = (string: string) => {
  return `${ENV.baseUrl}/${string}`;
};

const ENDPOINTS = {
  moim: getEndpoint('v1/moim'),
  moims: getEndpoint('v1/moim'),
};
export default ENDPOINTS;
