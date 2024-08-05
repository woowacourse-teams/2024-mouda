import ChattingPreview from '@_components/ChattingPreview/ChattingPreview';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import ChattingPreviewLayout from '@_layouts/ChattingPreviewLayout/ChattingPreviewLayout';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { useTheme } from '@emotion/react';
import { Fragment } from 'react';

export default function ChatPage() {
  const theme = useTheme();
  const dummy = new Array(30).fill(undefined).map(() => {
    return {
      title: '밥먹으실 사람',
      participants: [{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }],
    };
  });

  return (
    <Fragment>
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
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
