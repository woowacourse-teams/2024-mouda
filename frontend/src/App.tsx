import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import ENDPOINTS from './constants/endpoints';
import TmpAddMoim from './pages/TmpAddMoim';
import TmpMain from './pages/TmpMain';
import { useMemo } from 'react';

const router = createBrowserRouter([
  {
    path: ENDPOINTS.main,
    element: <TmpMain />,
  },
  {
    path: ENDPOINTS.addMoim,
    element: <TmpAddMoim />,
  },
]);

export default function App() {
  const queryClient = useMemo(() => {
    return new QueryClient();
  }, []);

  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}
