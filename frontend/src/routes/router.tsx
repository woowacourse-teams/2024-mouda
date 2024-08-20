import ChatPage from '@_pages/ChatPage/ChatPage';
import ChattingRoomPage from '@_pages/ChattingRoomPage/ChattingRoomPage';
import DarakbangCreationPage from '@_pages/DarakbangCreationPage/DarakbangCreationPage';
import DarakbangEntrancePage from '@_pages/DarakbangEntrancePage/DarakbangEntrancePage';
import DarakbangInvitationPage from '@_pages/DarakbangInvitationPage/DarakbangInvitationPage';
import DarakbangInvitationRoute from './DarakbangInvitationRoute';
import DarakbangLandingPage from '@_pages/DarakbangLandingPage/DarakbangLandingPage';
import DarakbangManagementPage from '@_pages/DarakbangManagementPage/DarakbangManagementPage';
import DarakbangMembersPage from '@_pages/DarakbangMembersPage/DarakbangMembersPage';
import DarakbangNicknamePage from '@_pages/DarakbangNicknamePage/DarakbangNicknamePage';
import DarakbangSelectOptionPage from '@_pages/DarakbangSelectOptionPage/DarakbangSelectOptionPage';
import DarakbangSelectPage from '@_pages/DarakbangSelectPage/DarakbangSelectPage';
import ErrorRoute from './ErrorRoute';
import HomePage from '@_pages/HomePage/HomePage';
import KakaoOAuthLoginPage from '@_pages/KakaoOAuthLoginPage/KakaoOAuthLoginPage';
import MainPage from '@_pages/MainPage/MainPage';
import MoimCreationPage from '@_pages/MoimCreationPage/MoimCreationPage';
import MoimDetailPage from '@_pages/MoimDetailPage/MoimDetailPage';
import MoimModifyPage from '@_pages/MoimModifyPage/MoimModifyPage';
import MyPage from '@_pages/Mypage/MyPage';
import NotificationPage from '@_pages/NotificationPage/NotificationPage';
import ParticipationCompletePage from '@_pages/ParticipationCompletePage/ParticipationCompletePage';
import PleaseCreationPage from '@_pages/PleaseCreationPage/PleaseCreationPage';
import PleasePage from '@_pages/PleasePage/PleasePage';
import ProtectedRoute from './ProtectedRoute';
import ROUTES from '@_constants/routes';
import SlashRoute from './SlashRoute';
import { createBrowserRouter } from 'react-router-dom';

const routesConfig = [
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
    path: ROUTES.kakaoOAuth,
    element: <KakaoOAuthLoginPage />,
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
    requiresAuth: true,
  },
];

const router = createBrowserRouter(
  routesConfig.map((route) => ({
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
