import { Global } from '@emotion/react';
import reset from './reset';

import {
  QueryClient,
  QueryClientProvider,
  MutationCache,
} from '@tanstack/react-query';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import ENDPOINTS from './constants/endpoints';
import { useMemo } from 'react';
import MoimListPage from './pages/MoimList';
import MoimRegisterPage from './pages/MoimRegister';

const router = createBrowserRouter([
  {
    path: ENDPOINTS.main,
    element: <MoimListPage />,
  },
  {
    path: ENDPOINTS.addMoim,
    element: <MoimRegisterPage />,
  },
]);

export default function App() {
  const queryClient = useMemo(() => {
    return new QueryClient({
      mutationCache: new MutationCache({
        onError: (e: Error) => console.log(e),
      }),
    });
  }, []);

  return (
    <>
      <Global styles={reset} />
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
      </QueryClientProvider>
    </>
  );
}
