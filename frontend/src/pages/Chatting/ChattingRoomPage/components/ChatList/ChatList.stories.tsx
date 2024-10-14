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
        chatId: 1,
        content: '안녕하세요! 어떻게 지내세요?',
        isMyMessage: true,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:00',
        chatType: 'BASIC',
      },
      {
        chatId: 2,
        content: '안녕하세요! 잘 지내고 있어요.',
        isMyMessage: false,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:01',
        chatType: 'BASIC',
      },
      {
        chatId: 3,
        content: '2024-08-01 15:00',
        isMyMessage: true,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:05',
        chatType: 'DATETIME',
      },
      {
        chatId: 4,
        content: '2024-08-01 15:00',
        isMyMessage: false,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:06',
        chatType: 'DATETIME',
      },
      {
        chatId: 5,
        content: '카페 A에서 만날까요?',
        isMyMessage: true,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:10',
        chatType: 'PLACE',
      },
      {
        chatId: 6,
        content: '강남역 스타벅스에서 만나는 건 어떨까요?',
        isMyMessage: false,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:12',
        chatType: 'PLACE',
      },
      {
        chatId: 7,
        content: '오늘 날씨가 좋네요!',
        isMyMessage: true,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:15',
        chatType: 'BASIC',
      },
      {
        chatId: 8,
        content: '네, 정말 좋습니다!',
        isMyMessage: false,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:16',
        chatType: 'BASIC',
      },
      {
        chatId: 9,
        content: '2024-08-02 10:00',
        isMyMessage: true,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:20',
        chatType: 'DATETIME',
      },
      {
        chatId: 10,
        content: '2024-08-02 10:00',
        isMyMessage: false,
        participation: { nickname: 'idk', profile: '', role: 'MOIMEE' },
        date: '2024-08-01',
        time: '10:21',
        chatType: 'DATETIME',
      },
    ],
  },
};
