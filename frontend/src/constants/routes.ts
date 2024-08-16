/**
 * TODO: 라우터 경로 도메인별로 재정의 
const ROUTES = {
  main: '/',
  moim: '/moim',
  moimCreation: '/moim-create',
  moimDetail: '/moim/:moimId',
  moimParticipationComplete: '/moim/:moimId/participation-complete',
  moimModify: '/moim/:moimId/modify',

  chatting: '/chatting',
  chattingRoom: '/chatting/:moimId',

  login: '/login',
};
 */

const ROUTES = {
  main: '/',
  addMoim: '/add-moim',
  detail: '/moim/:moimId',
  participationComplete: '/moim/participation-complete',
  login: '/login',
  modify: '/modify/:moimId',
  chat: '/chat',
  chattingRoom: '/chatting-room/:moimId',
  please: '/please',
  addPlease: '/please/creation',
  myPage: '/my-page',
  notification: '/notification',
};

export default ROUTES;
