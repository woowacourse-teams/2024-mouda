import type { Meta, StoryObj } from '@storybook/react';

import ChattingPreview from './ChattingPreview';

const meta: Meta<typeof ChattingPreview> = {
  component: ChattingPreview,
};

export default meta;
type Story = StoryObj<typeof ChattingPreview>;

export const Default: Story = {
  args: {
    title: '축구 하실 사람?',
    lastChat: '아니 시간 안 정하냐 방장아;',
    participants: [
      { imageUrl: '' },
      { imageUrl: '' },
      { imageUrl: '' },
      { imageUrl: '' },
    ],
  },
};
