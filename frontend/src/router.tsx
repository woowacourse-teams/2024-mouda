import MainPage from '@_pages/MainPage/MainPage';
import MoimCreationPage from '@_pages/MoimCreationPage/MoimCreationPage';
import ROUTES from '@_constants/routes';
import { createBrowserRouter } from 'react-router-dom';
import TempDetailPage from '@_pages/TempDetail/TempDetailPage';

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
    element: <TempDetailPage />,
  },
]);

export default router;
