import MainPage from '@_pages/MainPage/MainPage';
import MoimCreationPage from '@_pages/MoimCreationPage/MoimCreationPage';
import ROUTES from '@_constants/routes';
import { createBrowserRouter } from 'react-router-dom';
import MoimDetailPage from '@_pages/MoimDetailPage/MoimDetailPage';
import ParticipationCompletePage from '@_pages/ParticipationCompletePage/ParticipationCompletePage';
import LoginPage from '@_pages/LoginPage/LoginPage';

const router = createBrowserRouter([
  {
    path: ROUTES.main,
    element: <MainPage />,
  },
  {
    path: ROUTES.addMoim,
    element: <MoimCreationPage />,
  },
  {
    path: ROUTES.detail,
    element: <MoimDetailPage />,
  },
  {
    path: ROUTES.participationComplete,
    element: <ParticipationCompletePage />,
  },
  {
    path: ROUTES.login,
    element: <LoginPage />,
  },
]);

export default router;
