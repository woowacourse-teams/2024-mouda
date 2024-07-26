import { Global } from '@emotion/react';
import { QueryClientProvider } from '@tanstack/react-query';
import { RouterProvider } from 'react-router-dom';
import createQueryClient from './queryClient';
import fonts from '@_common/font.style';
import reset from './common/reset.style';
import router from './router';
import { useMemo } from 'react';

export default function App() {
  const queryClient = useMemo(createQueryClient, []);

  return (
    <>
      <Global styles={[reset, fonts]} />
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
      </QueryClientProvider>
    </>
  );
}
