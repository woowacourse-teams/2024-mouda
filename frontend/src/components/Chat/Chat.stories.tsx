import type { Meta, StoryObj } from '@storybook/react';

import Chat from './Chat';

const meta: Meta<typeof Chat> = {
  component: Chat,
};

export default meta;
type Story = StoryObj<typeof Chat>;

export const Default: Story = {
  args: {
    chat: {
      chatId: 11,
      content: '여러분~ 싸우지 마세요',
      nickname: '테바',
      date: '2024-08-01',
      time: '12:12',
      isConfirmChat: true,
      chatType: 'BASIC',
      isMyMessage: false,
    },
  },
};
