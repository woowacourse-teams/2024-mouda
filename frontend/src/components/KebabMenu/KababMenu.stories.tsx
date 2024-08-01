import type { Meta, StoryObj } from '@storybook/react';
import KebabMenu from './KebabMenu';

const meta = {
  component: KebabMenu,
  title: 'Components/KebabMenu',
} satisfies Meta<typeof KebabMenu>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    options: [
      {
        name: '수정하기',
        onClick: () => {},
      },
      {
        name: '삭제하기',
        onClick: () => {},
      },
    ],
  },
  render: (args) => <KebabMenu {...args} />,
};
