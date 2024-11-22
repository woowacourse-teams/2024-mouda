import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';
import * as S from './KakaoSelct.style';
import { useTheme } from '@emotion/react';
import Button from '@_components/Button/Button';
import { useNavigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';
import { getInviteCode } from '@_common/inviteCodeManager';
import GET_ROUTES from '@_common/getRoutes';

export default function KakaoSelectPage() {
  const theme = useTheme();
  const navigate = useNavigate();

  return (
    <LoginLayout>
      <LoginLayout.Header></LoginLayout.Header>
      <LoginLayout.Main>
        <div css={S.titleWrapper}>
          <span css={S.title({ theme })}>
            기존 카카오 계정으로 <br /> 로그인하신 적이 있나요?
          </span>
        </div>
        <MissingFallback text="" />
      </LoginLayout.Main>
      <LoginLayout.Footer>
        <div
          css={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'flex-start',
            alignItems: 'center',
            gap: '2rem',
          }}
        >
          <Button
            shape="bar"
            onClick={() =>
              navigate(`${GET_ROUTES.default.oAuthSelection}/known`)
            }
          >
            예
          </Button>
          <Button
            shape="bar"
            onClick={() => {
              const inviteCode = getInviteCode();
              if (inviteCode) {
                navigate(
                  `${ROUTES.darakbangInvitationRoute}?code=${inviteCode}`,
                );
              }
              navigate(ROUTES.darakbangSelectOption);
            }}
          >
            아니오
          </Button>
          <span
            css={S.explain({ theme })}
            onClick={() =>
              navigate(`${GET_ROUTES.default.oAuthSelection}/unknown`)
            }
          >
            잘 기억나지 않아요
          </span>
        </div>
      </LoginLayout.Footer>
    </LoginLayout>
  );
}
