import type { Meta, StoryObj } from '@storybook/react';
import PleaseCreationPage from './PleaseCreationPage';

const meta = {
  component: PleaseCreationPage,
  title: 'pages/PleaseCreationPage',
} satisfies Meta<typeof PleaseCreationPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: (args) => <PleaseCreationPage {...args} />,
};
