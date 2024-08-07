import { useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Back from '@_common/assets/back.svg';
import CalenderClock from '@_components/Icons/CalenderClock';
import ChatBottomMenu from '@_components/ChatBottomMenu/ChatBottomMenu';
import ChatList from '@_components/ChatList/ChatList';
import ChatMenuItem from '@_components/ChatMenuItem/ChatMenuItem';
import ChattingFooter from '@_components/ChattingFooter/ChattingFooter';
import ChattingRoomLayout from '@_layouts/ChattingRoomLayout/ChattingRoomLayout';
import Picker from '@_components/Icons/Picker';
import useChats from '@_hooks/queries/useChat';
import useMoims from '@_hooks/queries/useMoims';
import useSendMessage from '@_hooks/mutaions/useSendMessage';
import { useTheme } from '@emotion/react';

export default function ChattingRoomPage() {
  const theme = useTheme();
  const params = useParams();
  const navigate = useNavigate();
  const [isMenuOpened, setIsMenuOpened] = useState(false);

  const moimId = +(params.moimId || '0');
  const { moims } = useMoims();
  const { chats } = useChats(moimId);
  const { mutate: handleSendMessage } = useSendMessage(moimId);

  const moimTitle = useMemo(
    () => moims?.find((moim) => moim.moimId === moimId)?.title,
    [moims, moimId],
  );

  return (
    <ChattingRoomLayout>
      <ChattingRoomLayout.Header>
        <ChattingRoomLayout.Header.Left>
          <div onClick={() => navigate(-1)}>
            <Back />
          </div>
        </ChattingRoomLayout.Header.Left>
        <ChattingRoomLayout.Header.Center>
          <h2 css={theme.typography.s1}>{moimTitle}</h2>
        </ChattingRoomLayout.Header.Center>
      </ChattingRoomLayout.Header>
      <ChatList chats={chats} />
      <ChattingRoomLayout.Footer>
        <ChattingFooter
          onSubmit={handleSendMessage}
          onMenuClick={() => setIsMenuOpened(!isMenuOpened)}
        />
        {isMenuOpened && (
          <ChatBottomMenu>
            <ChatMenuItem
              icon={<Picker />}
              description="장소 정하기"
              onClick={() => alert('장소정하기')}
            />
            <ChatMenuItem
              icon={<CalenderClock />}
              description="날짜/시간 정하기"
            />
          </ChatBottomMenu>
        )}
      </ChattingRoomLayout.Footer>
    </ChattingRoomLayout>
  );
}
