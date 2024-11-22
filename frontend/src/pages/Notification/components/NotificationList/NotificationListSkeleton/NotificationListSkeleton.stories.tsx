import type { Meta, StoryObj } from '@storybook/react';
import NotificationListSkeleton from './NotificationListSkeleton';

const meta = {
  component: NotificationListSkeleton,
  title: 'Components/NotificationListSkeleton',
} satisfies Meta<typeof NotificationListSkeleton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <NotificationListSkeleton />,
};
