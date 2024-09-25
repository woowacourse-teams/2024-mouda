import { css, useTheme } from '@emotion/react';

import GET_ROUTES from '@_common/getRoutes';
import GoogleOAuthIcon from '@_common/assets/googleLogin.svg';
import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MainLogoIcon from '@_components/Icons/MainLogoIcon';
import { Navigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getToken } from '@_utils/tokenManager';
import AppleOAuthIcon from '@_components/Icons/AppleOAuthIcon';

export default function HomePage() {
  const theme = useTheme();
  const nowToken = getToken();

  if (nowToken) {
    const lastDarakbangId = getLastDarakbangId();
    if (lastDarakbangId) {
      return <Navigate to={GET_ROUTES.nowDarakbang.main()} />;
    }
    if (!lastDarakbangId) {
      return <Navigate to={ROUTES.darakbangSelectOption} />;
    }
  }

  const kakaoAuthLogin = () => {
    if (
      !process.env.KAKAO_O_AUTH_CLIENT_ID ||
      !process.env.OAUTH_REDIRECT_URI
    ) {
      throw new Error('Kakao OAuth 정보가 없습니다.');
    }

    const requestUrl = `https://kauth.kakao.com/oauth/authorize`;

    const params = {
      client_id: process.env.KAKAO_O_AUTH_CLIENT_ID,
      redirect_uri: process.env.OAUTH_REDIRECT_URI,
      response_type: 'code',
      scope: 'openid',
    };
    const queryString = new URLSearchParams(params).toString();
    const kakaoOAuthUrl = `${requestUrl}?${queryString}`;
    if (process.env.MSW == 'true') {
      window.location.href = 'http://localhost:8081/kakao-o-auth?code=1';
    } else {
      window.location.href = kakaoOAuthUrl;
    }
  };

  const appleAuthLogin = () => {};

  return (
    <LoginLayout>
      <LoginLayout.Main>
        <div
          css={css`
            display: flex;
            flex-direction: column;
            gap: 28px;
            align-items: center;
          `}
        >
          <MainLogoIcon />
          <div
            css={css`
              display: flex;
              flex-direction: column;
              align-items: start;
            `}
          >
            <h4 css={theme.typography.h4}>모여봐요 우리의</h4>
            <h2 css={theme.typography.h2}>다락방</h2>
          </div>
        </div>
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
        <button
          css={{
            background: 'none',
            border: 'none',
          }}
          onClick={kakaoAuthLogin}
        >
          <GoogleOAuthIcon />
        </button>
      </LoginLayout.Footer>
    </LoginLayout>
  );
}
