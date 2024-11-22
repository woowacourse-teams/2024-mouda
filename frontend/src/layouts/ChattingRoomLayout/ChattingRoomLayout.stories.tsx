import type { Meta, StoryObj } from '@storybook/react';

import { Chat } from '@_types/index';
import ChatList from '@_pages/Chatting/ChattingRoomPage/components/ChatList/ChatList';
import ChattingFooter from '@_pages/Chatting/ChattingRoomPage/components/ChattingFooter/ChattingFooter';
import ChattingRoomLayout from './ChattingRoomLayout';

const meta: Meta<typeof ChattingRoomLayout> = {
  component: ChattingRoomLayout,
};

export default meta;
type Story = StoryObj<typeof ChattingRoomLayout>;

const chats: Chat[] = [
  {
    chatId: 1,
    content: '안녕하세요! 오늘 날씨 어때요?',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:00',
    isMyMessage: false,
  },
  {
    chatId: 2,
    content: '안녕하세요! 저는 괜찮아요.',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:01',
    isMyMessage: true,
  },
  {
    chatId: 3,
    content: '요즘 어떤 일 하고 계세요?',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:02',
    isMyMessage: false,
  },
  {
    chatId: 4,
    content: '프로젝트 작업 중이에요.',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:03',
    isMyMessage: true,
  },
  {
    chatId: 5,
    content: '주말에 만나서 이야기해요.',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:04',
    isMyMessage: false,
  },
  {
    chatId: 6,
    content: '좋아요! 그때 봅시다.',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:05',
    isMyMessage: true,
  },
  {
    chatId: 7,
    content: '다음 주에 발표 준비 잘 하고 있나요?',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:06',
    isMyMessage: false,
  },
  {
    chatId: 8,
    content: '네, 열심히 하고 있어요.',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:07',
    isMyMessage: true,
  },
  {
    chatId: 9,
    content: '도움 필요하면 언제든지 말해요.',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:08',
    isMyMessage: false,
  },
  {
    chatId: 10,
    content: '감사합니다! 도움 요청할게요.',
    chatType: 'BASIC',
    participation: {
      darakbangMemberId: 1,
      nickname: 'idk',
      profile: '',
      role: 'MOIMEE',
    },
    date: '2024-08-01',
    time: '10:09',
    isMyMessage: true,
  },
];

export const Default: Story = {
  args: {},

  render: () => {
    return (
      <div style={{ width: '300px' }}>
        <ChattingRoomLayout>
          <ChattingRoomLayout.Header>
            <ChattingRoomLayout.Header.Left>
              왼쪽
            </ChattingRoomLayout.Header.Left>
            <ChattingRoomLayout.Header.Center>
              제목ddddddddddddddddd
            </ChattingRoomLayout.Header.Center>
          </ChattingRoomLayout.Header>
          <ChatList chats={chats} />
          <ChattingRoomLayout.Footer>
            <ChattingFooter
              onSubmit={() => {}}
              disabled={false}
              onMenuClick={() => {}}
              onTextAreaFocus={() => {}}
            />
          </ChattingRoomLayout.Footer>
        </ChattingRoomLayout>
      </div>
    );
  },
};
