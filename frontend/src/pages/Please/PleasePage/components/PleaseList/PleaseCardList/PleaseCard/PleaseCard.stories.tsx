import type { Meta, StoryObj } from '@storybook/react';
import PleaseCard from './PleaseCard';

const meta = {
  component: PleaseCard,
  title: 'Components/PleaseCard',
} satisfies Meta<typeof PleaseCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    please: {
      pleaseId: 1,
      title: '제목',
      description: '설명',
      interestCount: 0,
      isInterested: false,
    },
  },
  render: (args) => <PleaseCard {...args} />,
};
