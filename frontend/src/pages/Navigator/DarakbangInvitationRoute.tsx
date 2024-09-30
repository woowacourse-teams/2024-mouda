import { Navigate, useNavigate, useSearchParams } from 'react-router-dom';
import { removeInviteCode, setInviteCode } from '@_common/inviteCodeManager';

import Button from '@_components/Button/Button';
import CompleteLayout from '@_layouts/CompleteLayout/CompleteLayout';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import ROUTES from '@_constants/routes';
import { setLastDarakbangId } from '@_common/lastDarakbangManager';
import useDarakbangNameByCode from '@_hooks/queries/useDarakbangNameByCode';
import useMyDarakbangs from '@_hooks/queries/useMyDarakbang';

export default function DarakbangInvitationRoute() {
  const [searchParam] = useSearchParams();
  const navigate = useNavigate();

  const code = searchParam.get('code');
  const { darakbangName } = useDarakbangNameByCode(code || '');
  const { myDarakbangs } = useMyDarakbangs();
  const isRightCode = darakbangName && darakbangName !== '';
  if (darakbangName === undefined) return null;
  if (myDarakbangs === undefined) return null;
  if (isRightCode) {
    const nowDarakbang = myDarakbangs.find(
      (darakbang) => darakbang.name === darakbangName,
    );
    const isParticipated = nowDarakbang !== undefined;
    if (isParticipated) {
      alert('이미 참여한 다락방입니다');
      setLastDarakbangId(nowDarakbang.darakbangId);
      return <Navigate to={ROUTES.main} replace />;
    }
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
