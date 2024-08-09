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
    status: 'MOIMING',
  },
  render: (args) => <Tag {...args} />,
};

export const RecruitingTag: Story = {
  args: {
    status: 'MOIMING',
  },
  render: (args) => <Tag {...args} />,
};

export const RecruitingCanceledTag: Story = {
  args: {
    status: 'CANCELED',
  },
  render: (args) => <Tag {...args} />,
};

export const RecruitingCompletedTag: Story = {
  args: {
    status: 'COMPLETED',
  },
  render: (args) => <Tag {...args} />,
};
