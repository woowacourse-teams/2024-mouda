import ROUTES from '@_constants/routes';
import { getInviteCode } from '@_common/inviteCodeManager';
import { kakaoOAuth, googleOAuth } from '@_apis/auth';
import {
  getMemberToken,
  removeMemberToken,
  setAccessToken,
  setMemberToken,
} from '@_utils/tokenManager';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

type Provider = 'apple' | 'google' | 'kakao';

export default function OAuthLoginPage() {
  const navigate = useNavigate();
  const params = useParams<'provider'>();
  const provider = params.provider as Provider | undefined;

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
          },
          google: async () => {
            const response = await googleOAuth(codeOrToken, getMemberToken());
            setAccessToken(response.data.accessToken);
          },
          kakao: async () => {
            const response = await kakaoOAuth(codeOrToken);
            setAccessToken(response.data.accessToken);
            setMemberToken(response.data.memberId);
            navigate(ROUTES.oAuthSelection);
            return true; // 조기 반환
          },
        };

        const handler = oauthHandlers[provider];

        const shouldReturn = await handler();
        if (shouldReturn) return;

        removeMemberToken();

        const inviteCode = getInviteCode();
        if (inviteCode) {
          navigate(`${ROUTES.darakbangInvitationRoute}?code=${inviteCode}`);
        } else {
          navigate(ROUTES.darakbangSelectOption);
        }
      } catch (error) {
        console.error('OAuth 처리 중 오류 발생:', error);
        alert('로그인 처리 중 오류가 발생했습니다.');
        navigate(ROUTES.home);
      }
    };

    loginOAuth();
  }, [navigate, provider]);

  return null;
}
