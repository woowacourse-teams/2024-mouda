import type { Meta, StoryObj } from '@storybook/react';

import ChattingRoomSidebar from './ChattingRoomSidebar';

const meta: Meta<typeof ChattingRoomSidebar> = {
  component: ChattingRoomSidebar,
};

export default meta;
type Story = StoryObj<typeof ChattingRoomSidebar>;

export const Default: Story = {
  args: {
    members: [
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
      { nickname: 'gg', profile: '', role: 'MOIMEE' },
    ],
  },
};
