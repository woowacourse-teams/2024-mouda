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
    totalSteps: [
      '이름입력',
      '오프라인/온라인선택',
      '장소선택',
      '날짜/시간설정',
      '최대인원설정',
      '설명입력',
    ],
    currentStep: '이름입력',
  },
  render: (args) => <FunnelStepIndicator {...args} />,
};
