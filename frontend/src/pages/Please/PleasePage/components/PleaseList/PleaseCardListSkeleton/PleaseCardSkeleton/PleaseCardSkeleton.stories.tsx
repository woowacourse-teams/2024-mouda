import type { Meta, StoryObj } from '@storybook/react';
import PleaseCardSkeleton from './PleaseCardSkeleton';

const meta = {
  component: PleaseCardSkeleton,
  title: 'Components/PleaseCardSkeleton',
} satisfies Meta<typeof PleaseCardSkeleton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {},
  render: (args) => <PleaseCardSkeleton {...args} />,
};
