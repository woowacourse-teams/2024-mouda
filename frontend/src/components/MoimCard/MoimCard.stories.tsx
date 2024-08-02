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
      status: 'MOIMING',
      comments: [],
      participants: [
        {
          nickname: '치코',
          profile: '',
          role: 'moimer',
        },
        {
          nickname: '치코',
          profile: '',
          role: 'moimee',
        },
        {
          nickname: '치코',
          profile: '',
          role: 'moimer',
        },
        {
          nickname: '치코',
          profile: '',
          role: 'moimer',
        },
        {
          nickname: '치코',
          profile: '',
          role: 'moimer',
        },
        {
          nickname: '치코',
          profile: '',
          role: 'moimer',
        },
        {
          nickname: '치코',
          profile: '',
          role: 'moimer',
        },
        {
          nickname: '치코',
          profile: '',
          role: 'moimer',
        },
      ],
      description: '코딩하면서 놀아요',
    },
  },
  render: (args) => <MoimCard {...args} />,
};
