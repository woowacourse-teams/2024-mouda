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
    comments: [
      {
        commentId: 0,
        nickname: 'nickname',
        content: 'content',
        dateTime: '2023-04-04 14:00',
        profile: '',
        child: [
          {
            commentId: 0,
            nickname: 'nickname',
            content: 'content',
            dateTime: '2023-04-04 14:00',
            profile: '',
            child: [],
          },
        ],
      },
      {
        commentId: 3,
        nickname: 'nickname',
        content: 'content',
        dateTime: '2023-04-04 14:00',
        profile: '',
        child: [
          {
            commentId: 4,
            nickname: 'nickname',
            content: 'content',
            dateTime: '2023-04-04 14:00',
            profile: '',
            child: [],
          },
        ],
      },
    ],
    onWriteClick: () => {},
  },
  render: (args) => <CommentList {...args} />,
};
