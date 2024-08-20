import type { Meta, StoryObj } from '@storybook/react';
import FunnelInput from './FunnelInput';

const meta = {
  component: FunnelInput,
  title: 'Components/FunnelInput',
} satisfies Meta<typeof FunnelInput>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    placeholder: '플레이스홀더',
  },
  render: (args) => <FunnelInput {...args} />,
};
