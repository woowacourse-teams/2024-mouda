import type { Meta, StoryObj } from '@storybook/react';
import MyMoimListFilterTag from './MyMoimListFilterTag';

const meta = {
  component: MyMoimListFilterTag,
  title: 'Components/MyMoimListFilterTag',
} satisfies Meta<typeof MyMoimListFilterTag>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    label: '나의모임필터태그',
    isSelected: false,
    onClick: () => {},
  },
  render: (args) => <MyMoimListFilterTag {...args} />,
};

export const Unselected: Story = {
  args: {
    label: '선택안됨',
    isSelected: false,
    onClick: () => {},
  },
  render: (args) => <MyMoimListFilterTag {...args} />,
};

export const Selected: Story = {
  args: {
    label: '선택됨',
    isSelected: true,
    onClick: () => {},
  },
  render: (args) => <MyMoimListFilterTag {...args} />,
};
