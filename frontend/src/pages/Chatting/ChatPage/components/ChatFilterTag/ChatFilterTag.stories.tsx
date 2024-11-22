import type { Meta, StoryObj } from '@storybook/react';

import ChatFilterTag from './ChatFilterTag';

const meta: Meta<typeof ChatFilterTag> = {
  component: ChatFilterTag,
};

export default meta;
type Story = StoryObj<typeof ChatFilterTag>;

export const Default: Story = {
  args: { isChecked: true, value: '12' },
};
