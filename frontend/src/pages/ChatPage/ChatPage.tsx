import ChattingPreviewLayout from '@_layouts/ChattingPreviewLayout/ChattingPreviewLayout';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { useTheme } from '@emotion/react';

// import useMyMoims from '@_hooks/queries/useMyMoim';
// import { useNavigate } from 'react-router-dom';

export default function ChatPage() {
  const theme = useTheme();
  // const { moims, isLoading } = useMyMoims();
  // const navigate = useNavigate();
  return (
    <ChattingPreviewLayout>
      <ChattingPreviewLayout.Header>
        <ChattingPreviewLayout.Header.Left>
          <h2 css={theme.typography.h5}>채팅</h2>
        </ChattingPreviewLayout.Header.Left>
      </ChattingPreviewLayout.Header>
      <ChattingPreviewLayout.ContentContainer>
        {/* {!isLoading &&
          moims?.map((moim, id) => (
           
          ))} */}
      </ChattingPreviewLayout.ContentContainer>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </ChattingPreviewLayout>
  );
}
