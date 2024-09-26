import { Global, ThemeProvider } from '@emotion/react';
import { useEffect, useMemo } from 'react';

import { QueryClientProvider } from '@tanstack/react-query';
import { RouterProvider } from 'react-router-dom';
import createQueryClient from './queryClient';
import fonts from '@_common/font.style';
import { removeInviteCode } from '@_common/inviteCodeManager';
import reset from './common/reset.style';
import router from '@_routes/router';
import { theme } from '@_common/theme/theme.style';

export default function App() {
  const queryClient = useMemo(createQueryClient, []);

  useEffect(() => {
    window.addEventListener('beforeunload', removeInviteCode);
    return () => {
      window.removeEventListener('beforeunload', removeInviteCode);
    };
  }, []);

  return (
    <ThemeProvider theme={theme}>
      <Global styles={[reset, fonts]} />
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
      </QueryClientProvider>
    </ThemeProvider>
  );
}
