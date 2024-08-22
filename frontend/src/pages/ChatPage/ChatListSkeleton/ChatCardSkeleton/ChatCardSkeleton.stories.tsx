import type { Meta, StoryObj } from '@storybook/react';
import ChatCardSkeleton from './ChatCardSkeleton';

const meta = {
  component: ChatCardSkeleton,
  title: 'Components/ChatCardSkeleton',
} satisfies Meta<typeof ChatCardSkeleton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {},
  render: (args) => <ChatCardSkeleton {...args} />,
};
