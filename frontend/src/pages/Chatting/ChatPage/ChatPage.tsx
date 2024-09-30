import ChatCardListSkeleton from './ChatListSkeleton/ChatCardListSkeleton';
import ChattingPreview from '../ChattingRoomPage/ChattingPreview/ChattingPreview';
import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import GET_ROUTES from '@_common/getRoutes';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useChatPreviews from '@_hooks/queries/useChatPreiview';
import { useNavigate } from 'react-router-dom';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import { useTheme } from '@emotion/react';
import { Fragment } from 'react';
import ListContent from '@_layouts/components/ListContent/ListContent';
import ListPageLayout from '@_layouts/ListPageLayout/ListPageLayout';

export default function ChatPage() {
  const theme = useTheme();
  const { chatPreviews, isLoading } = useChatPreviews();
  const { darakbangName } = useNowDarakbangName();
  const navigate = useNavigate();

  return (
    <Fragment>
      <ListPageLayout>
        <ListPageLayout.TriHeader>
          <ListPageLayout.TriHeader.Left>
            <h2 css={[theme.typography.h5, common.nonScroll]}>
              <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
            </h2>
          </ListPageLayout.TriHeader.Left>
        </ListPageLayout.TriHeader>

        <ListPageLayout.Main>
          <ListContent>
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
          </ListContent>
        </ListPageLayout.Main>
      </ListPageLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
