import { kakaoOAuth } from '@_apis/auth';
import ROUTES from '@_constants/routes';
import useServeToken from '@_hooks/mutaions/useServeToken';
import { requestPermission } from '@_service/notification';
import { setToken } from '@_utils/tokenManager';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function KakaoOAuthLoginPage() {
  const navigate = useNavigate();
  const { mutate } = useServeToken();

  useEffect(() => {
    const loginKakaoOAuth = async () => {
      const code = new URL(window.location.href).searchParams.get('code');
      if (code === null) {
        return;
      }

      const response = await kakaoOAuth(code);
      setToken(response.data.accessToken);
      requestPermission(mutate);
      navigate(ROUTES.main);
    };

    loginKakaoOAuth();
  }, [navigate, mutate]);

  return null;
}
