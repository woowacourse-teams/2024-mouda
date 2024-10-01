import ROUTES from '@_constants/routes';
import { getInviteCode } from '@_common/inviteCodeManager';
import { kakaoOAuth, appleOAuth, googleOAuth } from '@_apis/auth';
import { setToken } from '@_utils/tokenManager';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

export default function OAuthLoginPage() {
  const navigate = useNavigate();
  const { provider } = useParams();
  useEffect(() => {
    const loginOAuth = async () => {
      try {
        const code = new URL(window.location.href).searchParams.get('code');

        if (code === null) {
          alert('잘못된 접근입니다');
          navigate(ROUTES.home);
          return;
        }
        switch (provider) {
          case 'apple': {
            const response = await appleOAuth(code);
            setToken(response.data.accessToken);
            break;
          }
          case 'google': {
            const response = await googleOAuth(code);
            setToken(response.data.accessToken);
            break;
          }
          case 'kakao': {
            const response = await kakaoOAuth(code);
            setToken(response.data.accessToken);
            break;
          }
          default: {
            alert('지원되지 않는 제공자입니다.');
            navigate(ROUTES.home);
          }
        }

        const inviteCode = getInviteCode();
        if (inviteCode) {
          navigate(`${ROUTES.darakbangInvitationRoute}?code=${inviteCode}`);
        }
        if (!inviteCode) {
          navigate(ROUTES.darakbangSelectOption);
        }
      } catch (error) {
        console.error('OAuth 처리 중 오류 발생:', error);
        alert('로그인 처리 중 오류가 발생했습니다.');
      }
    };

    loginOAuth();
  }, [navigate, provider]);

  return null;
}
