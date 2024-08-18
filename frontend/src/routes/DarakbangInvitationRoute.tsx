import { useNavigate, useSearchParams } from 'react-router-dom';

import Button from '@_components/Button/Button';
import CompleteLayout from '@_layouts/CompleteLayout/CompleteLayout';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import ROUTES from '@_constants/routes';
import useDarakbangNameByCode from '@_hooks/queries/useDarakbangNameByCode';

export default function DarakbangInvitationRoute() {
  const [searchParam] = useSearchParams();
  const navigate = useNavigate();

  const code = searchParam.get('code');
  const { darakbangName, isLoading } = useDarakbangNameByCode(code || '');
  const isRightCode = code !== '';
  if (isLoading) return null;
  if (isRightCode) {
    navigate(ROUTES.darakbangNickname, { state: { darakbangName } });
  }

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
