import type { Meta, StoryObj } from '@storybook/react';
import NotificationCardSkeleton from './NotificationCardSkeleton';

const meta = {
  component: NotificationCardSkeleton,
  title: 'Components/NotificationCardSkeleton',
} satisfies Meta<typeof NotificationCardSkeleton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: (args) => <NotificationCardSkeleton {...args} />,
};
