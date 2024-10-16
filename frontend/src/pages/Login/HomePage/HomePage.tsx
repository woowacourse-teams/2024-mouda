import { css, useTheme } from '@emotion/react';
import { Navigate, useNavigate } from 'react-router-dom';
import GET_ROUTES from '@_common/getRoutes';
import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MainLogoIcon from '@_components/Icons/MainLogoIcon';
import AppleOAuthIcon from '@_components/Icons/AppleOAuthIcon';
import ROUTES from '@_constants/routes';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getAccessToken } from '@_utils/tokenManager';
import GoogleLoginButton from '@_components/GoogleLoginButton/GoogleLoginButton';
export default function HomePage() {
  const theme = useTheme();
  const nowToken = getAccessToken();
  const navigate = useNavigate();

  if (nowToken) {
    const lastDarakbangId = getLastDarakbangId();
    if (lastDarakbangId) {
      return <Navigate to={GET_ROUTES.nowDarakbang.main()} />;
    }
    if (!lastDarakbangId) {
      return <Navigate to={ROUTES.darakbangSelectOption} />;
    }
  }

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
      response_type: 'code id_token',
      response_mode: 'form_post',
      scope: 'name email',
    };
    const queryString = new URLSearchParams(params).toString();
    const appleOAuthUrl = `${process.env.APPLE_REQUEST_URL}?${queryString}`;
    if (process.env.MSW === 'true') {
      window.location.href = 'http://localhost:8081/kakao-o-auth?code=1';
    } else {
      window.location.href = appleOAuthUrl;
    }
  };

  const handleDataMigraionLink = () => {
    navigate(ROUTES.oAuthMigration);
  };

  return (
    <LoginLayout>
      <LoginLayout.Header></LoginLayout.Header>
      <LoginLayout.Main>
        <div
          css={css`
            display: flex;
            flex-direction: column;
            gap: 28px;
            height: 70vh;
            justify-content: center;
            align-items: center;
          `}
        >
          <MainLogoIcon />
          <div
            css={css`
              display: flex;
              flex-direction: column;
              justify-content: center;
              align-items: start;
            `}
          >
            <h4 css={theme.typography.h4}>모여봐요 우리의</h4>
            <h2 css={theme.typography.h2}>다락방</h2>
          </div>
        </div>
      </LoginLayout.Main>
      <LoginLayout.Footer>
        <div
          css={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            gap: '1rem',
          }}
        >
          <GoogleLoginButton />

          <button
            css={{
              background: 'none',
              border: 'none',
            }}
            onClick={appleAuthLogin}
          >
            <AppleOAuthIcon />
          </button>
        </div>
        <button
          css={{
            color: 'gray',
            background: 'none',
            border: 'none',
          }}
          onClick={handleDataMigraionLink}
        >
          카카오톡 로그인 회원이었나요? 데이터 이전을 원하시면 여기를 클릭하세요
        </button>
      </LoginLayout.Footer>
    </LoginLayout>
  );
}
