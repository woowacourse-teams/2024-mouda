import MainPage from '@_pages/MainPage/MainPage';
import MoimCreationPage from '@_pages/MoimCreationPage/MoimCreationPage';
import ROUTES from '@_constants/routes';
import { createBrowserRouter } from 'react-router-dom';
import MoimDetailPage from '@_pages/MoimDetailPage/MoimDetailPage';
import ParticipationCompletePage from '@_pages/ParticipationCompletePage/ParticipationCompletePage';
import LoginPage from '@_pages/LoginPage/LoginPage';
import ProtectedRoute from './ProtectedRoute';
import MoimModifyPage from '@_pages/MoimModifyPage/MoimModifyPage';

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
    path: ROUTES.login,
    element: <LoginPage />,
    requiresAuth: false,
  },
  {
    path: ROUTES.modify,
    element: <MoimModifyPage />,
    requiresAuth: false,
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
