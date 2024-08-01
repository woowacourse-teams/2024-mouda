import type { Meta, StoryObj } from '@storybook/react';
import ComentList from './ComentList';

const meta = {
  component: ComentList,
  title: 'Components/ComentList',
} satisfies Meta<typeof ComentList>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    comments: [
      {
        id: 0,
        nickname: 'nickname',
        content: 'content',
        dateTime: '2023-04-04 14:00',
        src: '',
        child: [
          {
            id: 0,
            nickname: 'nickname',
            content: 'content',
            dateTime: '2023-04-04 14:00',
            src: '',
            child: [],
          },
        ],
      },
      {
        id: 3,
        nickname: 'nickname',
        content: 'content',
        dateTime: '2023-04-04 14:00',
        src: '',
        child: [
          {
            id: 4,
            nickname: 'nickname',
            content: 'content',
            dateTime: '2023-04-04 14:00',
            src: '',
            child: [],
          },
        ],
      },
    ],
  },
  render: (args) => <ComentList {...args} />,
};
