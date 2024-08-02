const getEndpoint = (string: string) => {
  return `${process.env.BASE_URL}/${string}`;
};

const ENDPOINTS = {
  moim: getEndpoint('v1/moim'),
  moims: getEndpoint('v1/moim'),
  auth: getEndpoint('v1/auth'),
  chamyo: getEndpoint('v1/chamyo'),
  zzim: getEndpoint('v1/zzim'),
};
export default ENDPOINTS;
