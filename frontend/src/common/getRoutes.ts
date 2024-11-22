import { getLastDarakbangId } from './lastDarakbangManager';

const getDarakbangIdRoute = (darakbangId: number) =>
  '/darakbang/' + darakbangId;

const getNowDarakbangRoute = () =>
  getDarakbangIdRoute(getLastDarakbangId() || 0);

const GET_ROUTES = {
  nowDarakbang: {
    main: () => getNowDarakbangRoute(),
    addMoim: () => getNowDarakbangRoute() + '/add-moim',
    moimDetail: (moimId: number) => getNowDarakbangRoute() + '/moim/' + moimId,
    moimParticipateComplete: () =>
      getNowDarakbangRoute() + '/moim/participation-complete',
    modify: (moimId: number) => getNowDarakbangRoute() + '/modify/' + moimId,

    chat: () => getNowDarakbangRoute() + '/chat',
    chattingRoom: (moimId: number) =>
      getNowDarakbangRoute() + '/chatting-room/' + moimId,

    please: () => getNowDarakbangRoute() + '/please',
    addPlease: () => getNowDarakbangRoute() + '/please/creation',

    myPage: () => getNowDarakbangRoute() + '/my-page',

    notification: () => getNowDarakbangRoute() + '/notification',

    darakbangManagement: () => getNowDarakbangRoute() + '/darakbang-management',
    darakbangMembers: () => getNowDarakbangRoute() + '/darakbang-members',
    darakbangInvitation: () => getNowDarakbangRoute() + '/darakbang-invitation',
    darakbangLanding: () => getNowDarakbangRoute() + '/darakbang-landing',

    bet: () => getNowDarakbangRoute() + '/bet',
    betCreation: () => getNowDarakbangRoute() + '/bet/creation',
    betDetail: (betId: number) => getNowDarakbangRoute() + '/bet/' + betId,
    betResult: (betId: number) =>
      getNowDarakbangRoute() + '/bet/' + betId + '/result',
    setting: () => getNowDarakbangRoute() + '/my-page/setting',
  },
  default: {
    notFound: '/*',
    main: '/',
    home: '/home',
    kakaoSelection: '/oauth-migration',
    oAuthSelection: '/oauth-select',
    resultMigration: '/oauth-migration',
    oAuthGoogle: '/oauth',
    oAuth: '/oauth/:provider',
  },
};
export default GET_ROUTES;
