import { Meta, StoryObj } from '@storybook/react/*';
import MoimDescription from './MoimDescription';

const meta = {
  title: 'components/MoimDescription',
  component: MoimDescription,
} satisfies Meta<typeof MoimDescription>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    description: '볼 함 차보까?',
  },
  render: (args) => <MoimDescription {...args} />,
};
