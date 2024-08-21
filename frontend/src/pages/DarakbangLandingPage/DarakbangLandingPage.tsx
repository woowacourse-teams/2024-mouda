import Button from '@_components/Button/Button';
import CompleteLayout from '@_layouts/CompleteLayout/CompleteLayout';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import useMyInfo from '@_hooks/queries/useMyInfo';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';

export default function DarakbangLandingPage() {
  const { darakbangName } = useNowDarakbangName();
  const { myInfo } = useMyInfo();
  return (
    <CompleteLayout>
      <CompleteLayout.Header>
        <CompleteLayout.Header.Center>
          {darakbangName}
        </CompleteLayout.Header.Center>
      </CompleteLayout.Header>
      <CompleteLayout.ContentContainer>
        <HighlightSpan isCenterAlign>
          <HighlightSpan.Highlight>{myInfo?.nickname}</HighlightSpan.Highlight>
          {`님 반가워요~!\n이제 `}
          <HighlightSpan.Highlight>모임</HighlightSpan.Highlight>을
          확인해볼까요?
        </HighlightSpan>{' '}
        <CompleteLayout.BottomButtonWrapper>
          <Button shape="bar">네~ 좋아요~</Button>
        </CompleteLayout.BottomButtonWrapper>
      </CompleteLayout.ContentContainer>
    </CompleteLayout>
  );
}
