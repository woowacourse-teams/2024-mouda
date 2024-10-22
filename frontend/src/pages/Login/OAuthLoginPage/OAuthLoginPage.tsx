import ROUTES from '@_constants/routes';
import { googleOAuth } from '@_apis/auth';
import { setAccessToken } from '@_utils/tokenManager';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ApiError } from '@_utils/customError/ApiError';
import useMigrationOAuth from '@_hooks/mutaions/useMigrationOAuth';
import GET_ROUTES from '@_common/getRoutes';

type Provider = 'apple' | 'google' | 'kakao';

export default function OAuthLoginPage() {
  const navigate = useNavigate();
  const params = useParams<'provider'>();
  const provider = params.provider as Provider | undefined;
  const { mutate: kakaoMigration } = useMigrationOAuth(
    () => navigate(`${GET_ROUTES.default.resultMigration}/sucess`),
    () => navigate(`${GET_ROUTES.default.resultMigration}/fail`),
  );

  useEffect(() => {
    const loginOAuth = async () => {
      try {
        const urlParams = new URLSearchParams(window.location.search);

        const redirectToHome = (message = '잘못된 접근입니다') => {
          alert(message);
          navigate(ROUTES.home);
        };

        if (!provider) {
          redirectToHome('지원되지 않는 제공자입니다.');
          return;
        }

        const codeOrToken = urlParams.get('code') || urlParams.get('token');

        if (!codeOrToken) {
          redirectToHome();
          return;
        }

        const oauthHandlers: Record<Provider, () => Promise<boolean | void>> = {
          apple: async () => {
            setAccessToken(codeOrToken);
            navigate(ROUTES.kakaoSelection);
          },
          google: async () => {
            const response = await googleOAuth(codeOrToken);
            setAccessToken(response.data.accessToken);
            navigate(ROUTES.kakaoSelection);
          },
          kakao: async () => {
            kakaoMigration(codeOrToken);
          },
        };

        const handler = oauthHandlers[provider];

        await handler();
      } catch (error) {
        if (error instanceof ApiError) {
          alert(error.message);
        } else {
          alert('알 수 없는 오류가 발생했습니다.');
        }
        navigate(ROUTES.home);
      }
    };

    loginOAuth();
  }, [navigate, provider]);

  return null;
}
