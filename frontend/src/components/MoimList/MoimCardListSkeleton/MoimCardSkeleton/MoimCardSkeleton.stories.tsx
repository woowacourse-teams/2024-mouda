import type { Meta, StoryObj } from '@storybook/react';
import MoimCardSkeleton from './MoimCardSkeleton';

const meta = {
  component: MoimCardSkeleton,
  title: 'Components/MoimCardSkeleton',
} satisfies Meta<typeof MoimCardSkeleton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {},
  render: (args) => <MoimCardSkeleton {...args} />,
};
