import type { Meta, StoryObj } from '@storybook/react';
import Tag from './Tag';

const meta = {
  component: Tag,
  title: 'Components/Tag',
} satisfies Meta<typeof Tag>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    children: '모집중',
    color: 'green',
  },
  render: (args) => <Tag {...args} />,
};

export const GreenTag: Story = {
  args: {
    children: '모집중',
    color: 'green',
  },
  render: (args) => <Tag {...args} />,
};

export const RedTag: Story = {
  args: {
    children: '모집취소',
    color: 'red',
  },
  render: (args) => <Tag {...args} />,
};

export const GreyTag: Story = {
  args: {
    children: '모집완료',
    color: 'grey',
  },
  render: (args) => <Tag {...args} />,
};
