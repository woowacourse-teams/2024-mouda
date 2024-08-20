import KakaoOAuthLoginIcon from '@_components/Icons/KakaoOAuthIcon';
import MainLogoIcon from '@_components/Icons/MainLogoIcon';
import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import { css, useTheme } from '@emotion/react';
import { requestPermission } from '@_service/notification';
import useServeToken from '@_hooks/mutaions/useServeToken';
export default function HomePage() {
  const theme = useTheme();

  const kakaoAuthLogin = () => {
    const { mutate } = useServeToken();
    requestPermission(mutate);
    if (
      !process.env.KAKAO_O_AUTH_CLIENT_ID ||
      !process.env.KAKAO_O_AUTH_REDIRECT_URI
    ) {
      throw new Error('Kakao OAuth 정보가 없습니다.');
    }

    const requestUrl = `https://kauth.kakao.com/oauth/authorize`;

    const params = {
      client_id: process.env.KAKAO_O_AUTH_CLIENT_ID,
      redirect_uri: process.env.KAKAO_O_AUTH_REDIRECT_URI,
      response_type: 'code',
      scope: 'openid',
    };
    const queryString = new URLSearchParams(params).toString();
    const kakaoOAuthUrl = `${requestUrl}?${queryString}`;

    window.location.href = kakaoOAuthUrl;
  };

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
          onClick={kakaoAuthLogin}
        >
          <KakaoOAuthLoginIcon />
        </button>
      </LoginLayout.Footer>
    </LoginLayout>
  );
}
