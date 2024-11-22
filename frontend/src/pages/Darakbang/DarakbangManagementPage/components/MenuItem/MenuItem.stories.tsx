import type { Meta, StoryObj } from '@storybook/react';

import MenuItem from './MenuItem';

const meta: Meta<typeof MenuItem> = {
  component: MenuItem,
};

export default meta;
type Story = StoryObj<typeof MenuItem>;

export const Default: Story = {
  args: { description: '멤버 목록', isLastItem: false },
};
