import type { Meta, StoryObj } from '@storybook/react';
import NotificationList from './NotificationList';

const meta = {
  component: NotificationList,
  title: 'Components/NotificationList',
} satisfies Meta<typeof NotificationList>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    notifications: [
      {
        type: 'MOIM_CREATED',
        createdAt: '1시간전',
        message: '테스트',
      },
      {
        type: 'MOIM_CREATED',
        createdAt: '1시간전',
        message: '테스트',
      },
    ],
  },
  render: (args) => <NotificationList {...args} />,
};
