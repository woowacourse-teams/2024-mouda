import ROUTES from '@_constants/routes';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function GoogleLoginButton() {
  const navigate = useNavigate();
  useEffect(() => {
    // 구글 로그인 버튼 초기화
    if (window.google) {
      window.google.accounts.id.initialize({
        client_id: 'YOUR_GOOGLE_CLIENT_ID',
        callback: handleGoogleSignIn,
        ux_mode: 'popup',
      });

      window.google.accounts.id.renderButton(
        document.getElementById('g_id_signin'),
        { theme: 'outline', size: 'large', width: 269 },
      );
    }
  }, []); // 빈 배열을 넘겨서 이 effect가 컴포넌트가 처음 렌더링될 때만 실행되도록 설정
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

  return <div id="g_id_signin"></div>; // 구글 로그인 버튼을 그릴 div
}

export default GoogleLoginButton;
