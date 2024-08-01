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
    tabs: ['Tab1', 'Tab2', 'Tab3'],
    currentTab: 'Tab1',
    onTabClick: (tab: string) => console.log(tab),
  },
  render: (args) => <MoimTabBar {...args} />,
};
