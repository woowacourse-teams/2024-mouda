import type { Meta, StoryObj } from '@storybook/react';

import ChatMessage from './ChatMessage';

const meta: Meta<typeof ChatMessage> = {
  component: ChatMessage,
};

export default meta;
type Story = StoryObj<typeof ChatMessage>;

export const Default: Story = {
  args: {
    sender: '테바',
    time: '12:12',
    imageUrl: '',
    message: '여러분~ 싸우지 마세요',
    isMyMessage: false,
  },
};
