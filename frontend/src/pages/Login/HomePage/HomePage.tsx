import { css, useTheme } from '@emotion/react';
import { Navigate } from 'react-router-dom';
import GET_ROUTES from '@_common/getRoutes';
import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MainLogoIcon from '@_components/Icons/MainLogoIcon';
import AppleOAuthIcon from '@_components/Icons/AppleOAuthIcon';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getAccessToken } from '@_utils/tokenManager';
import GoogleLoginButton from '@_components/GoogleLoginButton/GoogleLoginButton';
import * as S from './HomePage.style';
import KakaoOAuthLoginIcon from '@_components/Icons/KakaoOAuthIcon';
export default function HomePage() {
  const theme = useTheme();
  const nowToken = getAccessToken();

  if (nowToken) {
    const lastDarakbangId = getLastDarakbangId();
    if (lastDarakbangId) {
      return <Navigate to={GET_ROUTES.nowDarakbang.main()} />;
    }
    if (!lastDarakbangId) {
      return <Navigate to={GET_ROUTES.default.kakaoSelection} />;
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

  return (
    <LoginLayout>
      <LoginLayout.Header></LoginLayout.Header>
      <LoginLayout.Main>
        <div
          css={css`
            display: flex;
            flex-direction: column;
            gap: 2rem;
            align-items: center;
            justify-content: center;

            margin-top: 30%;
          `}
          aria-label="어색한 공동체 속 우리만의 작은 공간. 모여봐요 우리의 다락방"
          tabIndex={1}
        >
          <MainLogoIcon />
        </div>
      </LoginLayout.Main>
      <LoginLayout.Footer>
        <section
          css={css`
            display: flex;
            flex-direction: column;
            gap: 3rem;
            align-items: center;
            justify-content: center;

            margin-bottom: 10rem;
          `}
        >
          <span css={theme.typography.b3}>3초만에 모우다를 시작해보세요!</span>
          <div
            css={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              gap: '2rem',
            }}
          >
            <button
              css={{
                background: 'none',
                border: 'none',
                cursor: 'pointer',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
              }}
              onClick={appleAuthLogin}
              aria-label="애플 계정으로 로그인"
            >
              <AppleOAuthIcon type={'circle'} />
            </button>
            <GoogleLoginButton type={'circle'} />
            <div css={S.boundary({ theme })}></div>
            <button
              css={{
                display: 'flex',
                alignItems: 'center',
                justifyCsontent: 'center',
                background: 'none',
                border: 'none',
              }}
              onClick={() => {
                alert(
                  '카카오톡 로그인은 더 이상 지원하지 않아요! 다른 로그인을 이용하여 계정을 옮겨 보세요!',
                );
              }}
              aria-label="카카오 로그인 기능은 더 이상 지원하지 않아요. 애플 로그인 또는 구글 로그인 기능을 이용해주세요."
            >
              <KakaoOAuthLoginIcon type="circle" />
            </button>
          </div>
          <div css={S.explain({ theme })}>
            카카오톡 로그인은 더 이상 지원하지 않아요!
            <br />
            다른 로그인을 이용해주세요
          </div>
        </section>
      </LoginLayout.Footer>
    </LoginLayout>
  );
}
