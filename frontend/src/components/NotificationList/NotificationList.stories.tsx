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
        type: '아무거나',
        title: '아무거나',
        time: '아무거나',
      },
      {
        type: '아무거나',
        title: '아무거나',
        time: '아무거나',
      },
    ],
  },
  render: (args) => <NotificationList {...args} />,
};
