import { Navigate, useNavigate, useSearchParams } from 'react-router-dom';
import { removeInviteCode, setInviteCode } from '@_common/inviteCodeManager';

import Button from '@_components/Button/Button';
import CompleteLayout from '@_layouts/CompleteLayout/CompleteLayout';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import ROUTES from '@_constants/routes';
import useDarakbangNameByCode from '@_hooks/queries/useDarakbangNameByCode';

export default function DarakbangInvitationRoute() {
  const [searchParam] = useSearchParams();
  const navigate = useNavigate();

  const code = searchParam.get('code');
  const { darakbangName } = useDarakbangNameByCode(code || '');
  const isRightCode = darakbangName && darakbangName !== '';
  if (darakbangName === undefined) return null;
  if (isRightCode) {
    if (code) setInviteCode(code);
    return <Navigate to={ROUTES.darakbangNickname} replace />;
  }
  if (!isRightCode) {
    removeInviteCode();
    return (
      <CompleteLayout>
        <CompleteLayout.ContentContainer>
          <MissingFallback text="유효하지 않은 참여코드입니다" />
        </CompleteLayout.ContentContainer>
        <CompleteLayout.BottomButtonWrapper>
          <Button
            shape="bar"
            onClick={() => navigate(ROUTES.darakbangSelectOption)}
          >
            참여페이지로 돌아가기
          </Button>
        </CompleteLayout.BottomButtonWrapper>
      </CompleteLayout>
    );
  }
}
