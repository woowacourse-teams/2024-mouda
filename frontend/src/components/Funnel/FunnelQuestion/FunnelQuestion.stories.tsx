import type { Meta, StoryObj } from '@storybook/react';
import FunnelQuestion from './FunnelQuestion';

const meta = {
  component: FunnelQuestion,
  title: 'Components/FunnelQuestion',
} satisfies Meta<typeof FunnelQuestion>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => (
    <FunnelQuestion>
      <FunnelQuestion.Text>질문인데 </FunnelQuestion.Text>
      <FunnelQuestion.Highlight>강조</FunnelQuestion.Highlight>
      <FunnelQuestion.Text>가 있는 질문</FunnelQuestion.Text>
    </FunnelQuestion>
  ),
};
