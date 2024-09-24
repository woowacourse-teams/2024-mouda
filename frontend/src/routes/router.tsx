import ErrorRoute from './ErrorRoute';

import ProtectedRoute from './ProtectedRoute';

import ROUTES from '@_constants/routes';

import { createBrowserRouter } from 'react-router-dom';
import { lazy, Suspense } from 'react';

const MainPage = lazy(() => import('@_pages/Moim/MainPage/MainPage'));
const MoimCreationPage = lazy(
  () => import('@_pages/Moim/MoimCreationPage/MoimCreationPage'),
);
const MoimDetailPage = lazy(
  () => import('@_pages/Moim/MoimDetailPage/MoimDetailPage'),
);
const ParticipationCompletePage = lazy(
  () =>
    import('@_pages/Moim/ParticipationCompletePage/ParticipationCompletePage'),
);
const ChatPage = lazy(() => import('@_pages/Chatting/ChatPage/ChatPage'));
const ChattingRoomPage = lazy(
  () => import('@_pages/Chatting/ChattingRoomPage/ChattingRoomPage'),
);
const HomePage = lazy(() => import('@_pages/Login/HomePage/HomePage'));

const DarakbangCreationPage = lazy(
  () => import('@_pages/Darakbang/DarakbangCreationPage/DarakbangCreationPage'),
);
const DarakbangEntrancePage = lazy(
  () => import('@_pages/Darakbang/DarakbangEntrancePage/DarakbangEntrancePage'),
);
const DarakbangInvitationPage = lazy(
  () =>
    import('@_pages/Darakbang/DarakbangInvitationPage/DarakbangInvitationPage'),
);
const DarakbangInvitationRoute = lazy(
  () => import('../pages/Navigator/DarakbangInvitationRoute'),
);
const DarakbangLandingPage = lazy(
  () => import('@_pages/Darakbang/DarakbangLandingPage/DarakbangLandingPage'),
);
const DarakbangManagementPage = lazy(
  () =>
    import('@_pages/Darakbang/DarakbangManagementPage/DarakbangManagementPage'),
);
const DarakbangMembersPage = lazy(
  () => import('@_pages/Darakbang/DarakbangMembersPage/DarakbangMembersPage'),
);
const DarakbangNicknamePage = lazy(
  () => import('@_pages/Darakbang/DarakbangNicknamePage/DarakbangNicknamePage'),
);
const DarakbangSelectOptionPage = lazy(
  () =>
    import(
      '@_pages/Darakbang/DarakbangSelectOptionPage/DarakbangSelectOptionPage'
    ),
);
const DarakbangSelectPage = lazy(
  () => import('@_pages/Darakbang/DarakbangSelectPage/DarakbangSelectPage'),
);

const KakaoOAuthLoginPage = lazy(
  () => import('@_pages/Login/KakaoOAuthLoginPage/KakaoOAuthLoginPage'),
);
const MoimModifyPage = lazy(
  () => import('@_pages/Moim/MoimModifyPage/MoimModifyPage'),
);
const MyPage = lazy(() => import('@_pages/Mypage/MyPage'));
const NotFoundPage = lazy(
  () => import('@_pages/Fallback/NotFoundPage/NotFoundPage'),
);
const NotificationPage = lazy(
  () => import('@_pages/Notification/NotificationPage'),
);
const PleaseCreationPage = lazy(
  () => import('@_pages/Please/PleaseCreationPage/PleaseCreationPage'),
);
const PleasePage = lazy(() => import('@_pages/Please/PleasePage/PleasePage'));
const SlashRoute = lazy(() => import('../pages/Navigator/SlashRoute'));

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
  {
    path: ROUTES.darakbangLanding,
    element: <DarakbangLandingPage />,
    requiresAuth: true,
  },
];

const router = createBrowserRouter(
  routesConfig.map((route) => ({
    path: route.path,
    element: route.requiresAuth ? (
      <Suspense fallback={<div>Loading...</div>}>
        <ProtectedRoute>{route.element}</ProtectedRoute>
      </Suspense>
    ) : (
      route.element
    ),
    errorElement: (
      <Suspense fallback={<div>Loading...</div>}>
        <ErrorRoute />
      </Suspense>
    ),
  })),
);

export default router;
