import type { Meta, StoryObj } from '@storybook/react';
import CommentList from './CommentList';

const meta = {
  component: CommentList,
  title: 'Components/CommentList',
} satisfies Meta<typeof CommentList>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    moimId: 1,
    comments: [
      {
        commentId: 0,
        nickname: 'nickname',
        content: 'content',
        dateTime: '2023-04-04 14:00',
        profile: '',
        children: [
          {
            commentId: 0,
            nickname: 'nickname',
            content: 'content',
            dateTime: '2023-04-04 14:00',
            profile: '',
            children: [],
          },
        ],
      },
      {
        commentId: 3,
        nickname: 'nickname',
        content: 'content',
        dateTime: '2023-04-04 14:00',
        profile: '',
        children: [
          {
            commentId: 4,
            nickname: 'nickname',
            content: 'content',
            dateTime: '2023-04-04 14:00',
            profile: '',
            children: [],
          },
        ],
      },
    ],
  },
  render: (args) => <CommentList {...args} />,
};
