import { useNavigate, useParams } from 'react-router-dom';

import Back from '@_common/assets/back.svg';
import ChatList from '@_components/ChatList/ChatList';
import ChattingFooter from '@_components/ChattingFooter/ChattingFooter';
import ChattingRoomLayout from '@_layouts/ChattingRoomLayout/ChattingRoomLayout';
import { useTheme } from '@emotion/react';

export default function ChattingRoomPage() {
  const theme = useTheme();
  const params = useParams();
  const navigate = useNavigate();

  const moimId = params.moimId;
  const moimTitle = '모임 ' + moimId;
  const chats = [
    {
      sender: '테바',
      time: '14:07',
      message:
        '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
      isMyMessage: false,
    },
    {
      sender: '테바j',
      time: '14:07',
      message:
        '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
      isMyMessage: true,
    },
  ];

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
        <ChattingFooter onSubmit={() => {}} />
      </ChattingRoomLayout.Footer>
    </ChattingRoomLayout>
  );
}
