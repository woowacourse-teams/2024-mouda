import MainPage from '@_pages/MainPage/MainPage';
import MoimCreationPage from '@_pages/MoimCreationPage/MoimCreationPage';
import ROUTES from '@_constants/routes';
import { createBrowserRouter } from 'react-router-dom';

const router = createBrowserRouter([
  {
    path: ROUTES.main,
    element: <MainPage />,
  },
  {
    path: ROUTES.addMoim,
    element: <MoimCreationPage />,
  },
]);

export default router;
