import ErrorRoute from './ErrorRoute';
import ProtectedRoute from './ProtectedRoute';
import ROUTES from '@_constants/routes';
import { createBrowserRouter, Outlet } from 'react-router-dom';
import NotFoundPage from '@_pages/Fallback/NotFoundPage/NotFoundPage';
import SlashRoute from '../pages/Navigator/SlashRoute';
import HomePage from '@_pages/Login/HomePage/HomePage';
import OAuthLoginPage from '@_pages/Login/OAuthLoginPage/OAuthLoginPage';
import MainPage from '@_pages/Moim/MainPage/MainPage';
import MoimCreationPage from '@_pages/Moim/MoimCreationPage/MoimCreationPage';
import MoimDetailPage from '@_pages/Moim/MoimDetailPage/MoimDetailPage';
import ParticipationCompletePage from '@_pages/Moim/ParticipationCompletePage/ParticipationCompletePage';
import ChatPage from '@_pages/Chatting/ChatPage/ChatPage';
import ChattingRoomPage from '@_pages/Chatting/ChattingRoomPage/ChattingRoomPage';
import MoimModifyPage from '@_pages/Moim/MoimModifyPage/MoimModifyPage';
import PleasePage from '@_pages/Please/PleasePage/PleasePage';
import PleaseCreationPage from '@_pages/Please/PleaseCreationPage/PleaseCreationPage';
import MyPage from '@_pages/Mypage/MyPage';
import NotificationPage from '@_pages/Notification/NotificationPage';
import DarakbangSelectOptionPage from '@_pages/Darakbang/DarakbangSelectOptionPage/DarakbangSelectOptionPage';
import DarakbangSelectPage from '@_pages/Darakbang/DarakbangSelectPage/DarakbangSelectPage';
import DarakbangCreationPage from '@_pages/Darakbang/DarakbangCreationPage/DarakbangCreationPage';
import DarakbangEntrancePage from '@_pages/Darakbang/DarakbangEntrancePage/DarakbangEntrancePage';
import DarakbangNicknamePage from '@_pages/Darakbang/DarakbangNicknamePage/DarakbangNicknamePage';
import DarakbangLandingPage from '@_pages/Darakbang/DarakbangLandingPage/DarakbangLandingPage';
import DarakbangManagementPage from '@_pages/Darakbang/DarakbangManagementPage/DarakbangManagementPage';
import DarakbangMembersPage from '@_pages/Darakbang/DarakbangMembersPage/DarakbangMembersPage';
import DarakbangInvitationPage from '@_pages/Darakbang/DarakbangInvitationPage/DarakbangInvitationPage';
import DarakbangInvitationRoute from '@_pages/Navigator/DarakbangInvitationRoute';
import BetListPage from '@_pages/Bet/BetListPage/BetListPage';
import BetDetailPage from '@_pages/Bet/BetDetailPage/BetDetailPage';
import BetCreationPage from '@_pages/Bet/BetCreationPage/BetCreationPage';
import BetResultPage from '@_pages/Bet/BetResultPage/BetResultPage';
import SettingPage from '@_pages/Mypage/SettingPage/SettingPage';
import KakaoSelectPage from '@_pages/Login/DataMigrationPage/Explanation/KakaoSelect';
import KakaoLoginPage from '@_pages/Login/DataMigrationPage/KakaoLogin/KakaoLoginPage';
import OAuthMigrationResultPage from '@_pages/Login/DataMigrationPage/OAuthMigrationResult/OAuthMigrationResultPage';

const routesConfig = [
  {
    path: ROUTES.notFound,
    element: <NotFoundPage />,
    requiresAuth: false,
  },
  {
    path: ROUTES.main,
    element: <SlashRoute />,
    requiresAuth: false,
  },
  {
    path: ROUTES.darakbangMain,
    element: <MainPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.addMoim,
    element: <MoimCreationPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.moimDetail,
    element: <MoimDetailPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.participationComplete,
    element: <ParticipationCompletePage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.chat,
    element: <ChatPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.chattingRoom,
    element: <ChattingRoomPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.home,
    element: <HomePage />,
    requiresAuth: false,
  },
  {
    path: ROUTES.kakaoSelection,
    element: <KakaoSelectPage />,
    requiresAuth: false,
  },
  {
    path: ROUTES.resultMigration,
    element: <OAuthMigrationResultPage />,
    requiresAuth: false,
  },
  {
    path: ROUTES.oAuthSelection,
    element: <KakaoLoginPage />,
    requiresAuth: false,
  },
  {
    path: ROUTES.oAuth,
    element: <OAuthLoginPage />,
    requiresAuth: false,
  },
  {
    path: ROUTES.modify,
    element: <MoimModifyPage />,
    requiresAuth: false,
  },
  {
    path: ROUTES.please,
    element: <PleasePage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.addPlease,
    element: <PleaseCreationPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.myPage,
    element: <MyPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.notification,
    element: <NotificationPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangSelectOption,
    element: <DarakbangSelectOptionPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangSelect,
    element: <DarakbangSelectPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangCreation,
    element: <DarakbangCreationPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangEntrance,
    element: <DarakbangEntrancePage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangNickname,
    element: <DarakbangNicknamePage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangLanding,
    element: <DarakbangLandingPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangManagement,
    element: <DarakbangManagementPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangMembers,
    element: <DarakbangMembersPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangInvitation,
    element: <DarakbangInvitationPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.darakbangInvitationRoute,
    element: <DarakbangInvitationRoute />,
    requiresAuth: false,
  },
  {
    path: ROUTES.darakbangLanding,
    element: <DarakbangLandingPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.bet,
    element: <Outlet />,
    children: [
      {
        path: '',
        element: <BetListPage />,
      },
      {
        path: ':betId',
        element: <BetDetailPage />,
      },
      {
        path: 'creation',
        element: <BetCreationPage />,
      },
      {
        path: ':betId/result',
        element: <BetResultPage />,
      },
    ],
    requiresAuth: true,
  },
  {
    path: ROUTES.setting,
    element: <SettingPage />,
    requiresAuth: true,
  },
];

const router = createBrowserRouter(
  routesConfig.map((route) => ({
    ...route,
    path: route.path,
    element: route.requiresAuth ? (
      <ProtectedRoute>{route.element}</ProtectedRoute>
    ) : (
      route.element
    ),
    errorElement: <ErrorRoute />,
  })),
);

export default router;
