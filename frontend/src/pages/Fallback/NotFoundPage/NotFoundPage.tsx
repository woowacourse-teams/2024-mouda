import CompleteLayout from '@_layouts/CompleteLayout/CompleteLayout';
import { useNavigate } from 'react-router-dom';

import BackLogo from '@_common/assets/back.svg';
import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';
import Button from '@_components/Button/Button';
import ROUTES from '@_constants/routes';

export default function NotFoundPage() {
  const navigate = useNavigate();

  return (
    <CompleteLayout>
      <CompleteLayout.Header>
        <CompleteLayout.Header.Left>
          <div onClick={() => navigate(ROUTES.main)}>
            <BackLogo />
          </div>
        </CompleteLayout.Header.Left>
      </CompleteLayout.Header>
      <CompleteLayout.ContentContainer>
        <span>404 ERROR</span>
        <MissingFallback text="페이지를 찾을 수가 없어요" />
        <CompleteLayout.BottomButtonWrapper>
          <Button
            shape={'bar'}
            disabled={false}
            onClick={() => navigate(ROUTES.main)}
          >
            메인 페이지로 가기
          </Button>
        </CompleteLayout.BottomButtonWrapper>
      </CompleteLayout.ContentContainer>
    </CompleteLayout>
  );
}
