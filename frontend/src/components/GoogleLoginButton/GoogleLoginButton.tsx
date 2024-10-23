import { useEffect, useRef } from 'react';

import ROUTES from '@_constants/routes';
import { useNavigate } from 'react-router-dom';

interface GoogleLoginButtonProps {
  type: 'bar' | 'circle';
}
function GoogleLoginButton({ type }: GoogleLoginButtonProps) {
  const navigate = useNavigate();
  const g_sso = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (g_sso.current) {
      const renderOption =
        type === 'bar'
          ? {
              theme: 'outline',
              size: 'large',
              width: 269,
            }
          : {
              type: 'icon',
              shape: 'circle',
              theme: 'outline',
              size: 'large',
            };
      window.google.accounts.id.initialize({
        client_id: process.env.GOOGLE_O_AUTH_CLIENT_ID,
        callback: handleGoogleSignIn,
        ux_mode: 'popup',
      });

      window.google.accounts.id.renderButton(g_sso.current, renderOption);
    }
  }, [g_sso]);

  const handleGoogleSignIn = (response: {
    credential: string;
    error: string;
  }) => {
    if (response.error && response.error === 'AbortError') {
      console.error('요청이 중단되었습니다. 다시 시도하세요.');
      return;
    }
    navigate(`${ROUTES.oAuthGoogle}/google?code=${response.credential}`);
  };

  return <div ref={g_sso}></div>;
}

export default GoogleLoginButton;
