import type { Meta, StoryObj } from '@storybook/react';
import SkeletonPiece from './SkeletonPiece';

const meta = {
  component: SkeletonPiece,
  title: 'Components/SkeletonPiece',
} satisfies Meta<typeof SkeletonPiece>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    width: '100px',
    height: '20px',
  },
  render: (args) => <SkeletonPiece {...args} />,
};
