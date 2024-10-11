import ChatCardListSkeleton from './ChatListSkeleton/ChatCardListSkeleton';
import ChatFilterTag from './components/ChatFilterTag/ChatFilterTag';
import ChatFilterTagList from './components/ChatFilterTagList/ChatFilterTagList';
import { ChatRoomType } from '@_types/index';
import ChattingPreview from './components/ChattingPreview/ChattingPreview';
import ChattingPreviewLayout from '@_layouts/ChattingPreviewLayout/ChattingPreviewLayout';
import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import GET_ROUTES from '@_common/getRoutes';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useChatPreviews from '@_hooks/queries/useChatPreiview';
import { useNavigate } from 'react-router-dom';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { useState } from 'react';
import { useTheme } from '@emotion/react';

export default function ChatPage() {
  const theme = useTheme();
  const [nowChatRoomType, setNowChatRoomType] = useState<ChatRoomType>('MOIM');
  const { chatPreviews, isLoading } = useChatPreviews(nowChatRoomType);
  const { darakbangName } = useNowDarakbangName();
  const navigate = useNavigate();

  return (
    <ChattingPreviewLayout>
      <ChattingPreviewLayout.Header>
        <ChattingPreviewLayout.Header.Left>
          <h2 css={[theme.typography.h5, common.nonDrag]}>
            <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
          </h2>
        </ChattingPreviewLayout.Header.Left>
      </ChattingPreviewLayout.Header>
      <ChattingPreviewLayout.HeaderBottom>
        <ChatFilterTagList>
          <ChatFilterTag
            value={'모임'}
            isChecked={nowChatRoomType === 'MOIM'}
            onClick={() => setNowChatRoomType('MOIM')}
          />
          <ChatFilterTag
            value={'안내면진다'}
            isChecked={nowChatRoomType === 'BET'}
            onClick={() => setNowChatRoomType('BET')}
          />
        </ChatFilterTagList>
      </ChattingPreviewLayout.HeaderBottom>
      <ChattingPreviewLayout.ContentContainer>
        {isLoading ? (
          <ChatCardListSkeleton />
        ) : chatPreviews && chatPreviews.length > 0 ? (
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
