import { useNavigate, useParams } from 'react-router-dom';

import Back from '@_common/assets/back.svg';
import ChatList from '@_components/ChatList/ChatList';
import ChattingFooter from '@_components/ChattingFooter/ChattingFooter';
import ChattingRoomLayout from '@_layouts/ChattingRoomLayout/ChattingRoomLayout';
import useChats from '@_hooks/queries/useChat';
import useSendMessage from '@_hooks/mutaions/useSendMessage';
import { useTheme } from '@emotion/react';

export default function ChattingRoomPage() {
  const theme = useTheme();
  const params = useParams();
  const navigate = useNavigate();

  const moimId = +(params.moimId || '0');
  const { chats } = useChats(moimId);
  const { mutate: handleSendMessage } = useSendMessage(moimId);
  const moimTitle = '모임 ' + moimId;

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
        <ChattingFooter onSubmit={handleSendMessage} />
      </ChattingRoomLayout.Footer>
    </ChattingRoomLayout>
  );
}
