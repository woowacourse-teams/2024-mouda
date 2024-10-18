import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import * as S from './KakaoSelct.style';
import { useTheme } from '@emotion/react';
import Button from '@_components/Button/Button';
import { useNavigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';
import { getInviteCode } from '@_common/inviteCodeManager';
// import ROUTES from '@_constants/routes';
// import { getMemberToken } from '@_utils/tokenManager';
// import { useEffect } from 'react';
// import { useNavigate } from 'react-router-dom';

export default function KakaoSelectPage() {
  const theme = useTheme();
  const navigate = useNavigate();

  return (
    <LoginLayout>
      <LoginLayout.Header></LoginLayout.Header>
      <LoginLayout.Main>
        <div css={S.titleWrapper}>
          <span css={S.title({ theme })}>
            기존 카카오톡 <br />
            이용자인가요?
          </span>
        </div>
        <MissingFallback text="" />
      </LoginLayout.Main>
      <LoginLayout.Footer>
        <div
          css={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            gap: '2rem',
          }}
        >
          <Button shape="bar" onClick={() => navigate(ROUTES.oAuthSelection)}>
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
        </div>
      </LoginLayout.Footer>
    </LoginLayout>
  );
}
