import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import * as S from './OAuthSelectPage.style';
import { useTheme } from '@emotion/react';
import KakaoOAuthLoginIcon from '@_components/Icons/KakaoOAuthIcon';

export default function OAuthSelectPage() {
  const theme = useTheme();

  const kakaoAuthLogin = () => {
    if (
      !process.env.KAKAO_O_AUTH_CLIENT_ID ||
      !process.env.KAKAO_OAUTH_REDIRECT_URI
    ) {
      throw new Error('kakao OAuth 정보가 없습니다.');
    }
    const params = {
      client_id: process.env.KAKAO_O_AUTH_CLIENT_ID,
      redirect_uri: process.env.KAKAO_OAUTH_REDIRECT_URI,
      response_type: 'code',
      scope: 'openid',
    };
    const queryString = new URLSearchParams(params).toString();
    const kakaoOAuthUrl = `${process.env.KAKAO_REQUEST_URL}?${queryString}`;
    if (process.env.MSW == 'true') {
      window.location.href = 'http://localhost:8081/kakao-o-auth?code=1';
    } else {
      window.location.href = kakaoOAuthUrl;
    }
  };

  return (
    <LoginLayout>
      <LoginLayout.Header></LoginLayout.Header>
      <LoginLayout.Main>
        <div css={S.titleWrapper}>
          <span css={S.title({ theme })}>
            카카오톡 로그인은 <br /> 더이상 지원하지 않아요
          </span>
          <br />
          <span css={S.subtitle({ theme })}>
            카카오톡 로그인하면 데이터를 옮겨 드려요!
          </span>
        </div>
        <MissingFallback text="" />
      </LoginLayout.Main>
      <LoginLayout.Footer>
        <button
          css={{
            background: 'none',
            border: 'none',
            cursor: 'pointer',
          }}
          onClick={kakaoAuthLogin}
        >
          <KakaoOAuthLoginIcon type="bar" />
        </button>
      </LoginLayout.Footer>
    </LoginLayout>
  );
}
