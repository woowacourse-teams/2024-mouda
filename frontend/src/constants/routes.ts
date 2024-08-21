const addDarakbangPrefix = (route: string) => {
  return '/darakbang/:darakbangId' + route;
};

const beforeUsingDarakbangPrefixRoutes = {
  darakbangMain: '/',
  addMoim: '/add-moim',
  moimDetail: '/moim/:moimId',
  participationComplete: '/moim/participation-complete',
  modify: '/modify/:moimId',
  chat: '/chat',
  chattingRoom: '/chatting-room/:moimId',
  please: '/please',
  addPlease: '/please/creation',
  myPage: '/my-page',
  notification: '/notification',
  darakbangManagement: '/darakbang-management',
  darakbangMembers: '/darakbang-members',
  darakbangInvitation: '/darakbang-invitation',
};

const usingDarakbangPrefixRoutes = Object.entries(
  beforeUsingDarakbangPrefixRoutes,
).reduce(
  (object, [key, routes]) => {
    object[key as keyof typeof beforeUsingDarakbangPrefixRoutes] =
      addDarakbangPrefix(routes);
    return object;
  },
  {} as Record<keyof typeof beforeUsingDarakbangPrefixRoutes, string>,
);

const ROUTES = {
  notFound: '/*',
  main: '/',
  home: '/home',
  kakaoOAuth: '/kakao-o-auth',
  darakbangInvitationRoute: '/darakbang-invitation-route',

  darakbangSelectOption: '/darakbang-select-option',
  darakbangSelect: '/darakbang-select',
  darakbangCreation: '/darakbang-creation',
  darakbangEntrance: '/darakbang-entrance',
  darakbangNickname: '/darakbang-nickname',
  darakbangLanding: '/darakbang-landing',

  // /darakbang/:darakbangId/[루트] 형식
  ...usingDarakbangPrefixRoutes,
};

export default ROUTES;
