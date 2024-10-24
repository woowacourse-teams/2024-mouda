import * as S from './OAuthMigrationResultPage.style';

import { useNavigate, useParams } from 'react-router-dom';

import Button from '@_components/Button/Button';
import GET_ROUTES from '@_common/getRoutes';
import HappyFallback from '@_components/Fallback/HappyFallback/HappyFallback';
import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';
import ROUTES from '@_constants/routes';
import { getInviteCode } from '@_common/inviteCodeManager';
import { useEffect } from 'react';
import { useTheme } from '@emotion/react';

export default function OAuthMigrationResultPage() {
  const theme = useTheme();
  const { result } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (!result || (result !== 'success' && result !== 'fail')) {
      navigate(GET_ROUTES.default.notFound);
    }
  }, [result, navigate]);

  if (result === 'success') {
    return (
      <LoginLayout>
        <LoginLayout.Header></LoginLayout.Header>
        <LoginLayout.Main>
          <div css={S.titleWrapper}>
            <span css={S.title({ theme })}>
              모우다를 사용할 <br /> 준비가 되었어요
            </span>
            <br />
            <span css={S.subtitle({ theme })}>
              이전 기록을 새로운 계정으로 옮겼어요!
            </span>
          </div>
          <HappyFallback text="" />
        </LoginLayout.Main>
        <LoginLayout.Footer>
          <Button
            shape={'bar'}
            onClick={() => {
              const inviteCode = getInviteCode();
              if (inviteCode) {
                navigate(
                  `${ROUTES.darakbangInvitationRoute}?code=${inviteCode}`,
                );
              } else {
                navigate(ROUTES.darakbangSelectOption);
              }
            }}
          >
            다락방 선택 페이지로 이동하기
          </Button>
        </LoginLayout.Footer>
      </LoginLayout>
    );
  }
  if (result === 'fail') {
    return (
      <LoginLayout>
        <LoginLayout.Header></LoginLayout.Header>
        <LoginLayout.Main>
          <div css={S.titleWrapper}>
            <span css={S.title({ theme })}>
              기록을 이전하는데 <br /> 실패했어요!
            </span>
            <br />
            <span css={S.subtitle({ theme })}>
              혹시 이전 카카오 계정 가입자가 아닌가요?
            </span>
          </div>
          <MissingFallback text="" />
        </LoginLayout.Main>
        <LoginLayout.Footer>
          <Button
            shape={'bar'}
            onClick={() => navigate(ROUTES.darakbangSelectOption)}
          >
            다락방 선택 페이지로 이동하기
          </Button>
        </LoginLayout.Footer>
      </LoginLayout>
    );
  }
}
