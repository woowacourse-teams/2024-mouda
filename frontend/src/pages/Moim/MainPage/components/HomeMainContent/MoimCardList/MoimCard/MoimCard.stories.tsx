import { Meta, StoryObj } from '@storybook/react/*';
import MoimCard from './MoimCard';

const meta = {
  title: 'components/MoimCard',
  component: MoimCard,
} satisfies Meta<typeof MoimCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    moimInfo: {
      title: '볼 함 차보까?',
      date: '2021-10-30',
      time: '19:00',
      place: '서울 마포구 독막로 96 1층',
      maxPeople: 5,
      currentPeople: 3,
      isZzimed: true,
      moimId: 1,
      authorNickname: '김코딩',
      participants: [
        {
          darakbangMemberId: 1,
          nickname: '김코딩',
          profile: 'https://avatars.githubusercontent.com/u/78939596?v=4',
          role: 'MOIMER',
        },
        {
          darakbangMemberId: 1,
          nickname: '김디자인',
          profile: 'https://avatars.githubusercontent.com/u/78939596?v=4',
          role: 'MOIMEE',
        },
      ],
      description: '코딩하면서 놀아요',
      status: 'MOIMING',
      comments: [
        {
          commentId: 1,
          nickname: '김코딩',
          content: '안녕하세요',
          dateTime: '2021-10-30 19:00',
          profile: 'https://avatars.githubusercontent.com/u/78939596?v=4',
          children: [],
        },
      ],
    },
  },
  render: (args) => <MoimCard {...args} />,
};
