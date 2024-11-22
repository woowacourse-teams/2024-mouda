import type { Meta, StoryObj } from '@storybook/react';

import ChattingRoomSeparator from './ChattingRoomSeparator';

const meta: Meta<typeof ChattingRoomSeparator> = {
  component: ChattingRoomSeparator,
};

export default meta;
type Story = StoryObj<typeof ChattingRoomSeparator>;

export const Default: Story = {
  args: { string: '2024년 3월 2일' },
};
