import Button from '@_components/Button/Button';
import CompleteLayout from '@_layouts/CompleteLayout/CompleteLayout';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';

export default function DarakbangLandingPage() {
  const darakbangName = '우아한테크코스';
  const nickname = 'hi';
  return (
    <CompleteLayout>
      <CompleteLayout.Header>
        <CompleteLayout.Header.Center>
          {darakbangName}
        </CompleteLayout.Header.Center>
      </CompleteLayout.Header>
      <CompleteLayout.ContentContainer>
        <HighlightSpan isCenterAlign>
          <HighlightSpan.Highlight>{nickname}</HighlightSpan.Highlight>
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
