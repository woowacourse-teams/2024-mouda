import type { Meta, StoryObj } from '@storybook/react';
import MoimTabBar from './MoimTabBar';

const meta = {
  component: MoimTabBar,
  title: 'Components/MoimTabBar',
} satisfies Meta<typeof MoimTabBar>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    currentTab: '모임목록',
    onTabClick: (_: string) => {
      _;
    },
  },
  render: (args) => <MoimTabBar {...args} />,
};
