import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import AppleOAuthIcon from '@_components/Icons/AppleOAuthIcon';
import { useNavigate } from 'react-router-dom';
import { getMemberToken } from '@_utils/tokenManager';
import ROUTES from '@_constants/routes';
import { useEffect } from 'react';
import GoogleLoginButton from '@_components/GoogleLoginButton/GoogleLoginButton';
export default function OAuthSelectPage() {
  const navigate = useNavigate();

  useEffect(() => {
    if (!getMemberToken()) {
      console.log('잘못된 접근입니다.');
      alert('잘못된 접근입니다.');
      navigate(ROUTES.home);
    }
  }, [navigate]);
  const appleAuthLogin = () => {
    if (
      !process.env.APPLE_O_AUTH_CLIENT_ID ||
      !process.env.APPLE_OAUTH_REDIRECT_URI
    ) {
      throw new Error('Apple OAuth 정보가 없습니다.');
    }

    const params = {
      client_id: process.env.APPLE_O_AUTH_CLIENT_ID,
      redirect_uri: process.env.APPLE_OAUTH_REDIRECT_URI,
      response_type: 'code',
      scope: 'openid',
    };
    const queryString = new URLSearchParams(params).toString();
    const appleOAuthUrl = `${process.env.APPLE_REQUEST_URL}?${queryString}`;
    if (process.env.MSW == 'true') {
      window.location.href = 'http://localhost:8081/kakao-o-auth?code=1';
    } else {
      window.location.href = appleOAuthUrl;
    }
  };

  return (
    <LoginLayout>
      <LoginLayout.Header></LoginLayout.Header>
      <LoginLayout.Main>
        <section>
          <MissingFallback text="앞으로 하실 로그인을 선택해주세요" />
        </section>
      </LoginLayout.Main>
      <LoginLayout.Footer>
        <button
          css={{
            background: 'none',
            border: 'none',
          }}
          onClick={appleAuthLogin}
        >
          <AppleOAuthIcon />
        </button>
        <GoogleLoginButton />
      </LoginLayout.Footer>
    </LoginLayout>
  );
}
