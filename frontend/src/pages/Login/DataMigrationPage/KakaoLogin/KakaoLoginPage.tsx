import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import * as S from './KakaoLoginPage.style';
import { useTheme } from '@emotion/react';
import KakaoOAuthLoginIcon from '@_components/Icons/KakaoOAuthIcon';
import { useNavigate, useParams } from 'react-router-dom';
import { useEffect } from 'react';
import ROUTES from '@_constants/routes';

export default function KakaoLoginPage() {
  const theme = useTheme();
  const { type } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (!type || (type !== 'known' && type !== 'unknown')) {
      navigate(ROUTES.notFound);
    }
  }, [type, navigate]);
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
        {type === 'known' && (
          <div css={S.titleWrapper}>
            <span css={S.title({ theme })}>
              카카오 로그인은 <br /> 더 이상 지원하지 않아요
            </span>
            <br />
            <span css={S.subtitle({ theme })}>
              이전 기록을 유지하기 위해 카카오 로그인을 진행해주세요.
            </span>
          </div>
        )}
        {type === 'unknown' && (
          <div css={S.titleWrapper}>
            <span css={S.title({ theme })}>
              카카오 로그인은 <br /> 더 이상 지원하지 않아요
            </span>
            <br />
            <span css={S.subtitle({ theme })}>
              이전에 카카오 계정을 사용했는지 확인하고, 기록을 이전해 드릴게요!
            </span>
          </div>
        )}
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
