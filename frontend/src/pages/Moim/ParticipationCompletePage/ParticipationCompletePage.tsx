import CompleteLayout from '@_layouts/CompleteLayout/CompleteLayout';
import { useNavigate } from 'react-router-dom';

import BackLogo from '@_common/assets/back.svg';

import Button from '@_components/Button/Button';
export default function ParticipationCompletePage() {
  const navigate = useNavigate();

  return (
    <CompleteLayout>
      <CompleteLayout.Header>
        <CompleteLayout.Header.Left>
          <div onClick={() => navigate(-1)}>
            <BackLogo />
          </div>
        </CompleteLayout.Header.Left>
      </CompleteLayout.Header>
      <CompleteLayout.ContentContainer>
        <div>참여가 완료되었어요!</div>
        <CompleteLayout.BottomButtonWrapper>
          <Button shape={'bar'} disabled={false} onClick={() => navigate(-1)}>
            확인하기
          </Button>
        </CompleteLayout.BottomButtonWrapper>
      </CompleteLayout.ContentContainer>
    </CompleteLayout>
  );
}
