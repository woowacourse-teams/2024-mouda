import Button from '@_components/Button/Button';
import CompleteLayout from '@_layouts/CompleteLayout/CompleteLayout';
import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import GET_ROUTES from '@_common/getRoutes';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import { common } from '@_common/common.style';
import { theme } from '@_common/theme/theme.style';
import useMyInfo from '@_hooks/queries/useMyInfo';
import { useNavigate } from 'react-router-dom';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';

export default function DarakbangLandingPage() {
  const navigate = useNavigate();

  const { darakbangName } = useNowDarakbangName();
  const { myInfo } = useMyInfo();
  return (
    <CompleteLayout>
      <CompleteLayout.Header>
        <CompleteLayout.Header.Center>
          <span css={[[theme.typography.h5, common.nonDrag]]}>
            <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
          </span>
        </CompleteLayout.Header.Center>
      </CompleteLayout.Header>
      <CompleteLayout.ContentContainer>
        <HighlightSpan
          isCenterAlign
          ariaLabel={`${myInfo?.nickname}님 반가워요~! 이제 모임을 확인해볼까요?`}
        >
          <HighlightSpan.Highlight>{myInfo?.nickname}</HighlightSpan.Highlight>
          {`님 반가워요~!\n이제 `}
          <HighlightSpan.Highlight>모임</HighlightSpan.Highlight>을
          확인해볼까요?
        </HighlightSpan>
        <CompleteLayout.BottomButtonWrapper>
          <Button
            shape="bar"
            onClick={() => navigate(GET_ROUTES.nowDarakbang.main())}
          >
            네~ 좋아요~
          </Button>
        </CompleteLayout.BottomButtonWrapper>
      </CompleteLayout.ContentContainer>
    </CompleteLayout>
  );
}
