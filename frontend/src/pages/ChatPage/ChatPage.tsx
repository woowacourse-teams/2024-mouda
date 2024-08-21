import ChattingPreview from '@_components/ChattingPreview/ChattingPreview';
import ChattingPreviewLayout from '@_layouts/ChattingPreviewLayout/ChattingPreviewLayout';
import GET_ROUTES from '@_common/getRoutes';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useChatPreviews from '@_hooks/queries/useChatPreiview';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';

export default function ChatPage() {
  const theme = useTheme();
  const { chatPreviews } = useChatPreviews();
  const navigate = useNavigate();
  return (
    <ChattingPreviewLayout>
      <ChattingPreviewLayout.Header>
        <ChattingPreviewLayout.Header.Left>
          <h2 css={[theme.typography.h5, common.nonScroll]}>우아한테크코스</h2>
        </ChattingPreviewLayout.Header.Left>
      </ChattingPreviewLayout.Header>
      <ChattingPreviewLayout.ContentContainer>
        {chatPreviews && chatPreviews.length > 0 ? (
          chatPreviews?.map((chatPreview) => (
            <ChattingPreview
              chatPreview={chatPreview}
              onClick={() =>
                navigate(
                  GET_ROUTES.nowDarakbang.chattingRoom(chatPreview.moimId),
                )
              }
              key={chatPreview.moimId}
            />
          ))
        ) : (
          <MissingFallback text="아직 열린 채팅방이 없습니다" />
        )}
      </ChattingPreviewLayout.ContentContainer>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </ChattingPreviewLayout>
  );
}
