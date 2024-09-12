import type { Meta, StoryObj } from '@storybook/react';
import MoimModifyPage from './MoimModifyPage';

const meta = {
  component: MoimModifyPage,
  title: 'pages/MoimModifyPage',
} satisfies Meta<typeof MoimModifyPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: (args) => <MoimModifyPage {...args} />,
};
