import type { Meta, StoryObj } from '@storybook/react';

import ChatList from './ChatList';

const meta: Meta<typeof ChatList> = {
  component: ChatList,
};

export default meta;
type Story = StoryObj<typeof ChatList>;

export const Default: Story = {
  args: {
    chats: [
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
    ],
  },

  decorators: (Story) => {
    return (
      <div style={{ height: '200px' }}>
        <Story />
      </div>
    );
  },
};
