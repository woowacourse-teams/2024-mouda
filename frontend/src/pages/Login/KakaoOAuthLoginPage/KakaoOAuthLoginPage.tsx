import ROUTES from '@_constants/routes';
import { getInviteCode } from '@_common/inviteCodeManager';
import { kakaoOAuth } from '@_apis/auth';
import { setToken } from '@_utils/tokenManager';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function KakaoOAuthLoginPage() {
  const navigate = useNavigate();

  useEffect(() => {
    const loginKakaoOAuth = async () => {
      const code = new URL(window.location.href).searchParams.get('code');

      if (code === null) {
        alert('잘못된 접근입니다');
        navigate(ROUTES.home);
        return;
      }

      const response = await kakaoOAuth(code);
      setToken(response.data.accessToken);

      const inviteCode = getInviteCode();
      if (inviteCode) {
        navigate(`${ROUTES.darakbangInvitationRoute}?code=${inviteCode}`);
      }
      if (!inviteCode) {
        navigate(ROUTES.darakbangSelectOption);
      }
    };

    loginKakaoOAuth();
  }, [navigate]);

  return null;
}
