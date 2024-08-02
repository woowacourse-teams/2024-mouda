import type { Meta, StoryObj } from '@storybook/react';

import ChatList from '@_components/ChatList/ChatList';
import ChattingFooter from '@_components/ChattingFooter/ChattingFooter';
import ChattingRoomLayout from './ChattingRoomLayout';

const meta: Meta<typeof ChattingRoomLayout> = {
  component: ChattingRoomLayout,
};

export default meta;
type Story = StoryObj<typeof ChattingRoomLayout>;

const chats = [
  {
    sender: '상돌',
    time: '14:00',
    message: '안나야 공은 찰 줄 아냐',
    isMyMessage: false,
  },
  {
    sender: '안나',
    time: '14:04',
    message: '시비걸꺼면 나가라',
    isMyMessage: true,
  },
  {
    sender: '안나',
    time: '14:04',
    message: '지하돌아',
    isMyMessage: true,
  },
  {
    sender: '테바',
    time: '14:06',
    message: '여러분~ 싸우지 마세요',
    isMyMessage: false,
  },
  {
    sender: '테바',
    time: '14:07',
    message:
      '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
    isMyMessage: false,
  },
  {
    sender: '테바',
    time: '14:07',
    message:
      '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
    isMyMessage: false,
  },
  {
    sender: '테바',
    time: '14:07',
    message:
      '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
    isMyMessage: false,
  },
  {
    sender: '테바',
    time: '14:07',
    message:
      '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
    isMyMessage: false,
  },
  {
    sender: '테바',
    time: '14:07',
    message:
      '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
    isMyMessage: false,
  },
  {
    sender: '테바',
    time: '14:07',
    message:
      '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
    isMyMessage: false,
  },
  {
    sender: '테바',
    time: '14:07',
    message:
      '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
    isMyMessage: false,
  },
  {
    sender: '테바',
    time: '14:07',
    message:
      '두 분 다 강퇴 당하고 싶지 않으시면 서로 말 예쁘게 해주시고 ~ 각자 괜찮은 시간이나 좀 남겨주세요!',
    isMyMessage: false,
  },
];
export const Default: Story = {
  args: {},

  render: () => {
    return (
      <div style={{ width: '300px' }}>
        <ChattingRoomLayout>
          <ChattingRoomLayout.Header>
            <ChattingRoomLayout.Header.Left>
              <h1>왼쪽</h1>
            </ChattingRoomLayout.Header.Left>
            <ChattingRoomLayout.Header.Center>
              제목
            </ChattingRoomLayout.Header.Center>
          </ChattingRoomLayout.Header>
          <ChatList chats={chats} />
          <ChattingRoomLayout.Footer>
            <ChattingFooter onSubmit={() => {}} />
          </ChattingRoomLayout.Footer>
        </ChattingRoomLayout>
      </div>
    );
  },
};
