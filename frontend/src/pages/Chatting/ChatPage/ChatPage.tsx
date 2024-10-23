import { Fragment, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import ChatCardListSkeleton from './ChatListSkeleton/ChatCardListSkeleton';
import ChatFilterTag from './components/ChatFilterTag/ChatFilterTag';
import ChatFilterTagList from './components/ChatFilterTagList/ChatFilterTagList';
import { ChatRoomType } from '@_types/index';
import ChattingPreview from './components/ChattingPreview/ChattingPreview';
import ChattingPreviewLayout from '@_layouts/ChattingPreviewLayout/ChattingPreviewLayout';
import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import GET_ROUTES from '@_common/getRoutes';
import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useChatPreviews from '@_hooks/queries/useChatPreiview';
import { useTheme } from '@emotion/react';

export default function ChatPage() {
  const theme = useTheme();
  const location = useLocation();
  const [nowChatRoomType, setNowChatRoomType] = useState<ChatRoomType>(
    location?.state?.type || 'MOIM',
  );
  const { data: chatPreviews, isLoading } = useChatPreviews(nowChatRoomType);
  const navigate = useNavigate();

  return (
    <Fragment>
      <ChattingPreviewLayout>
        <ChattingPreviewLayout.Header>
          <ChattingPreviewLayout.Header.Left>
            <h2 css={[theme.typography.h5, common.nonDrag]}>
              <DarakbangNameWrapper>채팅</DarakbangNameWrapper>
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
              value={'룰렛'}
              isChecked={nowChatRoomType === 'BET'}
              onClick={() => setNowChatRoomType('BET')}
            />
          </ChatFilterTagList>
        </ChattingPreviewLayout.HeaderBottom>
        <ChattingPreviewLayout.ContentContainer>
          {isLoading ? (
            <ChatCardListSkeleton />
          ) : chatPreviews && chatPreviews.length > 0 ? (
            chatPreviews.map((chatPreview) => (
              <ChattingPreview
                title={chatPreview.title}
                unreadCount={chatPreview.unreadChatCount}
                tagValue={
                  (nowChatRoomType === 'MOIM' || undefined) &&
                  (chatPreview.isStarted ? '모임 후' : '모임 전')
                }
                fontColor={
                  (nowChatRoomType === 'MOIM' || undefined) &&
                  (chatPreview.isStarted
                    ? theme.colorPalette.yellow[800]
                    : theme.colorPalette.white[100])
                }
                themeColor={
                  (nowChatRoomType === 'MOIM' || undefined) &&
                  (chatPreview.isStarted
                    ? theme.colorPalette.yellow[50]
                    : theme.colorPalette.orange[100])
                }
                participants={chatPreview.participations}
                lastContent={chatPreview.lastContent}
                onClick={() =>
                  navigate(
                    GET_ROUTES.nowDarakbang.chattingRoom(
                      chatPreview.chatRoomId,
                    ),
                  )
                }
                key={chatPreview.chatRoomId}
              />
            ))
          ) : (
            <MissingFallback text="아직 열린 채팅방이 없습니다" />
          )}
        </ChattingPreviewLayout.ContentContainer>
      </ChattingPreviewLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
