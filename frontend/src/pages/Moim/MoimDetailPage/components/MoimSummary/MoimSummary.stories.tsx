import type { Meta, StoryObj } from '@storybook/react';
import MoimSummary from './MoimSummary';

const meta = {
  component: MoimSummary,
  title: 'Components/MoimSummary',
} satisfies Meta<typeof MoimSummary>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    moimInfo: {
      title: '풋살 할 사람?',
      status: 'COMPLETED',
    },
  },
  render: (args) => <MoimSummary {...args} />,
};
