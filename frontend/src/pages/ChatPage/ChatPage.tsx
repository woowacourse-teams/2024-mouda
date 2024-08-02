import ChattingPreview from '@_components/ChattingPreview/ChattingPreview';
import ChattingPreviewLayout from '@_layouts/ChattingPreviewLayout/ChattingPreviewLayout';
import { useTheme } from '@emotion/react';

export default function ChatPage() {
  const theme = useTheme();
  const dummy = new Array(30).fill(undefined).map(() => {
    return {
      title: '밥먹으실 사람',
      participants: [{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }],
    };
  });

  return (
    <ChattingPreviewLayout>
      <ChattingPreviewLayout.Header>
        <ChattingPreviewLayout.Header.Left>
          <h2 css={theme.typography.h5}>채팅</h2>
        </ChattingPreviewLayout.Header.Left>
      </ChattingPreviewLayout.Header>
      <ChattingPreviewLayout.ContentContainer>
        {dummy.map((dum, id) => (
          <ChattingPreview {...dum} key={id} />
        ))}
      </ChattingPreviewLayout.ContentContainer>
    </ChattingPreviewLayout>
  );
}
