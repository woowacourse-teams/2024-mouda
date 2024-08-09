import type { Meta, StoryObj } from '@storybook/react';

import ChattingPreview from './ChattingPreview';

const meta: Meta<typeof ChattingPreview> = {
  component: ChattingPreview,
};

export default meta;
type Story = StoryObj<typeof ChattingPreview>;

export const NoContent: Story = {
  args: {
    chatPreview: {
      moimId: 3,
      title: '운동 모임',
      currentPeople: 10,
      isStarted: true,
      lastContent: '',
      unreadContentCount: 0,
    },
  },
};

export const HasContent: Story = {
  args: {
    chatPreview: {
      moimId: 2,
      title: '독서 클럽',
      currentPeople: 8,
      isStarted: true,
      lastContent: '이번 주 독서 토론은 어떤 책으로 할까요?',
      unreadContentCount: 1,
    },
  },
};

export const Over300UnreadMessage: Story = {
  args: {
    chatPreview: {
      moimId: 2,
      title: '독서 클럽',
      currentPeople: 8,
      isStarted: true,
      lastContent: '이번 주 독서 토론은 어떤 책으로 할까요?',
      unreadContentCount: 301,
    },
  },
};

export const AfterMoim: Story = {
  args: {
    chatPreview: {
      moimId: 2,
      title: '독서 클럽',
      currentPeople: 8,
      isStarted: false,
      lastContent: '이번 주 독서 토론은 어떤 책으로 할까요?',
      unreadContentCount: 1,
    },
  },
};
