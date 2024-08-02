import type { Meta, StoryObj } from '@storybook/react';
import MyMoimListFilters from './MyMoimListFilters';

const meta = {
  component: MyMoimListFilters,
  title: 'Components/MyMoimListFilters',
} satisfies Meta<typeof MyMoimListFilters>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: (args) => <MyMoimListFilters {...args} />,
};
