import ROUTES from '@_constants/routes';
import { useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

function GoogleLoginButton() {
  const navigate = useNavigate();
  const g_sso = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (g_sso.current) {
      window.google.accounts.id.initialize({
        client_id: process.env.GOOGLE_O_AUTH_CLIENT_ID,
        callback: handleGoogleSignIn,
        ux_mode: 'popup',
      });

      window.google.accounts.id.renderButton(g_sso.current, {
        theme: 'outline',
        size: 'large',
        width: 269,
      });
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
    console.log('Google JWT Token: ', response.credential);
    navigate(`${ROUTES.oAuthGoogle}/google?code=${response.credential}`);
  };

  return <div ref={g_sso}></div>;
}

export default GoogleLoginButton;
