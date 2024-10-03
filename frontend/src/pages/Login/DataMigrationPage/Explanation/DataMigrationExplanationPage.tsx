import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import KakaoOAuthLoginIcon from '@_components/Icons/KakaoOAuthIcon';
import { useTheme } from '@emotion/react';
import { explanationSection } from './DataMigrationExplanationPage.style';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import SolidArrow from '@_components/Icons/SolidArrow';
import { useNavigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';

export default function DataMigrationExplanationPage() {
  const theme = useTheme();
  const navigate = useNavigate();
  const kakaoAuthLogin = () => {
    if (
      !process.env.KAKAO_O_AUTH_CLIENT_ID ||
      !process.env.KAKAO_OAUTH_REDIRECT_URI
    ) {
      throw new Error('Google OAuth 정보가 없습니다.');
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
      <LoginLayout.Header>
        <LoginLayout.Header.Left>
          <SolidArrow
            direction="left"
            onClick={() => {
              navigate(ROUTES.home);
            }}
          />
        </LoginLayout.Header.Left>
      </LoginLayout.Header>
      <LoginLayout.Main>
        <section>
          <MissingFallback text="" />
          <p css={explanationSection({ theme })}>
            모우다는 이용자 여러분의 더 좋은 서비스 제공을 위하여 카카오톡 소셜
            로그인에서 구글과 애플의 소셜 로그인으로 전환을 선택했습니다.
            <br /> <br /> 2024년 10월 5일 이전 카카오톡을 이용하여 로그인 하셨던
            기존 이용자들 중에서 기존 데이터를 이전을 원하시는 분들에게는 데이터
            전환 절차를 안내해 드리고 있습니다. 절차는 아래와 같습니다.
            <br />
            <br /> 1. 카카오톡 로그인을 진행한다. <br /> 2. 구글과 애플중
            데이터이전을 원하시는 소셜 선택하고 로그인을 진행한다.
          </p>
        </section>
      </LoginLayout.Main>
      <LoginLayout.Footer>
        <button
          css={{
            color: 'gray',
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
