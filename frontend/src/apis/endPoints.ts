import { getLastDarakbangId } from '@_common/lastDarakbangManager';

const API_BASE_URL = `${process.env.API_BASE_URL}/v1`;
export function addBaseUrl(
  endpoint: string,
  isNeedLastDarakbang: boolean = false,
) {
  if (isNeedLastDarakbang)
    endpoint = '/darakbang/' + (getLastDarakbangId() || 0) + endpoint;
  return API_BASE_URL + endpoint;
}
const API_URL = {
  darakbang: {
    role: addBaseUrl('/role', true),
    mine: addBaseUrl('/darakbang/mine', false),
    name: addBaseUrl('', true),
  },
  moim: addBaseUrl('/moim', true),
  moims: addBaseUrl('/moim', true),
  auth: addBaseUrl('/auth', true),
  chamyo: addBaseUrl('/chamyo', true),
  chat: addBaseUrl('/chat', true),
  zzim: addBaseUrl('/zzim', true),
  interest: addBaseUrl('/interest', true),
  please: addBaseUrl('/please', true),
  notification: addBaseUrl('/notification', true),
  kakaoOAuth: addBaseUrl('/auth/kakao/oauth', false),
};

const ENDPOINTS = {
  moim: 'moim',
  moims: 'moim',
  auth: 'auth',
  chamyo: 'chamyo',
  chat: 'chat',
  zzim: 'zzim',
  interest: 'interest',
  please: 'please',
  notification: 'notification',
};
export { ENDPOINTS, API_URL };
