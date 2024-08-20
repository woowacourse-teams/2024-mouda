import ROUTES from '@_constants/routes';
import { getInviteCode } from '@_common/inviteCodeManager';
import { kakaoOAuth } from '@_apis/auth';
import { requestPermission } from '@_service/notification';
import { setToken } from '@_utils/tokenManager';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import useServeToken from '@_hooks/mutaions/useServeToken';

export default function KakaoOAuthLoginPage() {
  const navigate = useNavigate();
  const { mutate: serveToken } = useServeToken();

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
      requestPermission(serveToken);
      const inviteCode = getInviteCode();
      if (inviteCode) {
        navigate(`${ROUTES.darakbangInvitationRoute}?code=${inviteCode}`);
      }
      if (!inviteCode) {
        navigate(ROUTES.darakbangSelectOption);
      }
    };

    loginKakaoOAuth();
  }, [navigate, serveToken]);

  return null;
}
