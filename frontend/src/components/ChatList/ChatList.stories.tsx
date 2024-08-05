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
        content: '안녕하세요! 오늘 날씨 어때요?',
        nickname: '홍길동',
        date: '2024-08-01',
        time: '10:00',
        isMyMessage: false,
      },
      {
        chatId: 2,
        content: '안녕하세요! 저는 괜찮아요.',
        nickname: '김철수',
        date: '2024-08-01',
        time: '10:01',
        isMyMessage: true,
      },
      {
        chatId: 3,
        content: '요즘 어떤 일 하고 계세요?',
        nickname: '홍길동',
        date: '2024-08-01',
        time: '10:02',
        isMyMessage: false,
      },
      {
        chatId: 4,
        content: '프로젝트 작업 중이에요.',
        nickname: '김철수',
        date: '2024-08-01',
        time: '10:03',
        isMyMessage: true,
      },
      {
        chatId: 5,
        content: '주말에 만나서 이야기해요.',
        nickname: '홍길동',
        date: '2024-08-01',
        time: '10:04',
        isMyMessage: false,
      },
      {
        chatId: 6,
        content: '좋아요! 그때 봅시다.',
        nickname: '김철수',
        date: '2024-08-01',
        time: '10:05',
        isMyMessage: true,
      },
      {
        chatId: 7,
        content: '다음 주에 발표 준비 잘 하고 있나요?',
        nickname: '홍길동',
        date: '2024-08-01',
        time: '10:06',
        isMyMessage: false,
      },
      {
        chatId: 8,
        content: '네, 열심히 하고 있어요.',
        nickname: '김철수',
        date: '2024-08-01',
        time: '10:07',
        isMyMessage: true,
      },
      {
        chatId: 9,
        content: '도움 필요하면 언제든지 말해요.',
        nickname: '홍길동',
        date: '2024-08-01',
        time: '10:08',
        isMyMessage: false,
      },
      {
        chatId: 10,
        content: '감사합니다! 도움 요청할게요.',
        nickname: '김철수',
        date: '2024-08-01',
        time: '10:09',
        isMyMessage: true,
      },
      {
        chatId: 11,
        content: '여러분~ 싸우지 마세요',
        nickname: '테바',
        date: '2024-08-01',
        time: '12:12',
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
