import type { Meta, StoryObj } from '@storybook/react';
import ZzimButton from './ZzimButton';

const meta = {
  component: ZzimButton,
  title: 'Components/ZzimButton',
} satisfies Meta<typeof ZzimButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    isChecked: true,
    onClick: () => {},
  },
  render: (args) => <ZzimButton {...args} />,
};

export const CheckedZzim: Story = {
  args: {
    isChecked: true,
    onClick: () => {},
  },
  render: (args) => <ZzimButton {...args} />,
};

export const NoneCheckedZzim: Story = {
  args: {
    isChecked: false,
    onClick: () => {},
  },
  render: (args) => <ZzimButton {...args} />,
};