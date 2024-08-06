import ChatPage from '@_pages/ChatPage/ChatPage';
import ChattingRoomPage from '@_pages/ChattingRoomPage/ChattingRoomPage';
import LoginPage from '@_pages/LoginPage/LoginPage';
import MainPage from '@_pages/MainPage/MainPage';
import MoimCreationPage from '@_pages/MoimCreationPage/MoimCreationPage';
import MoimDetailPage from '@_pages/MoimDetailPage/MoimDetailPage';
import ParticipationCompletePage from '@_pages/ParticipationCompletePage/ParticipationCompletePage';
import ProtectedRoute from './ProtectedRoute';
import MoimModifyPage from '@_pages/MoimModifyPage/MoimModifyPage';
import ROUTES from '@_constants/routes';
import { createBrowserRouter } from 'react-router-dom';
import PleasePage from '@_pages/PleasePage/PleasePage';

const routesConfig = [
  {
    path: ROUTES.main,
    element: <MainPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.addMoim,
    element: <MoimCreationPage />,
    requiresAuth: true,
  },
  {
    path: ROUTES.detail,
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
    path: ROUTES.login,
    element: <LoginPage />,
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
];

const router = createBrowserRouter(
  routesConfig.map((route) => ({
    path: route.path,
    element: route.requiresAuth ? (
      <ProtectedRoute>{route.element}</ProtectedRoute>
    ) : (
      route.element
    ),
  })),
);

export default router;
