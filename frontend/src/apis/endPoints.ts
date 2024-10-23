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
  bet: {
    all: addBaseUrl('/bet', true),
    detail: (betId: number) => addBaseUrl(`/bet/${betId}`, true),
    create: addBaseUrl('/bet', true),
    participate: (betId: number) => addBaseUrl(`/bet/${betId}`, true),
    result: (betId: number) => addBaseUrl(`/bet/${betId}/result`, true),
  },
  kakaoOAuth: addBaseUrl('/auth/kakao/oauth', false),
  myInfo: addBaseUrl('/member/mine', true),

  profile: (darakbangMemberId: number) =>
    addBaseUrl(`/members/${darakbangMemberId}/profile`, true),
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
  bet: 'bet',
};

export { ENDPOINTS, API_URL };
