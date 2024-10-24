import type { Meta, StoryObj } from '@storybook/react';

import ChattingPreview from './ChattingPreview';

const meta: Meta<typeof ChattingPreview> = {
  component: ChattingPreview,
};

export default meta;
type Story = StoryObj<typeof ChattingPreview>;

export const NoContent: Story = {
  args: {
    title: '운동 모임',
    participants: [
      {
        darakbangMemberId: 1,
        nickname: 'aa',
        role: 'MOIMEE',
        profile: '',
      },
    ],
    tagValue: '모임 전',
    lastContent: '',
    unreadCount: 0,
  },
};

export const HasContent: Story = {
  args: {
    title: '독서 클럽',
    participants: [
      {
        darakbangMemberId: 1,
        nickname: 'aa',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'bb',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'cc',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'dd',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'ee',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'ff',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'gg',
        role: 'MOIMEE',
        profile: '',
      },
    ],
    tagValue: '모임 후',
    lastContent: '이번 주 독서 토론은 어떤 책으로 할까요?',
    unreadCount: 2,
  },
};

export const Over300UnreadMessage: Story = {
  args: {
    title: '독서 클럽',
    participants: [
      {
        darakbangMemberId: 1,
        nickname: 'aa',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'bb',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'cc',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'dd',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'ee',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'ff',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'gg',
        role: 'MOIMEE',
        profile: '',
      },
    ],
    tagValue: '모임 전',
    lastContent: '이번 주 독서 토론은 어떤 책으로 할까요?',
    unreadCount: 301,
  },
};

export const AfterMoim: Story = {
  args: {
    title: '독서 클럽',
    participants: [
      {
        darakbangMemberId: 1,
        nickname: 'aa',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'bb',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'cc',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'dd',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'ee',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'ff',
        role: 'MOIMEE',
        profile: '',
      },
      {
        darakbangMemberId: 1,
        nickname: 'gg',
        role: 'MOIMEE',
        profile: '',
      },
    ],
    tagValue: '모임 후',
    lastContent: '이번 주 독서 토론은 어떤 책으로 할까요?',
    unreadCount: 1,
  },
};
