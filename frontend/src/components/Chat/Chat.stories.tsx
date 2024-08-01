import type { Meta, StoryObj } from '@storybook/react';

import Chat from './Chat';

const meta: Meta<typeof Chat> = {
  component: Chat,
};

export default meta;
type Story = StoryObj<typeof Chat>;

export const Default: Story = {
  args: {
    sender: '테바',
    time: '12:12',
    imageUrl: '',
    message: '여러분~ 싸우지 마세요',
    isMyMessage: false,
  },
};
