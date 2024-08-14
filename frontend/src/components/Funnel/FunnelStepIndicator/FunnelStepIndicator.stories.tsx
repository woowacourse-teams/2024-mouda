import type { Meta, StoryObj } from '@storybook/react';
import FunnelStepIndicator from './FunnelStepIndicator';

const meta = {
  component: FunnelStepIndicator,
  title: 'Components/FunnelStepIndicator',
} satisfies Meta<typeof FunnelStepIndicator>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    totalSteps: 3,
    currentStep: 1,
  },
  render: (args) => <FunnelStepIndicator {...args} />,
};
