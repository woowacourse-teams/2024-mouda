import type { Meta, StoryObj } from '@storybook/react';
import NavigationBar from './NavigationBar';

const meta = {
  component: NavigationBar,
  title: 'Components/NavigationBar',
} satisfies Meta<typeof NavigationBar>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: (args) => <NavigationBar {...args} />,
};
