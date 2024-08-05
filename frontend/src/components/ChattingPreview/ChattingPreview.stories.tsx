import type { Meta, StoryObj } from '@storybook/react';

import ChattingPreview from './ChattingPreview';

const meta: Meta<typeof ChattingPreview> = {
  component: ChattingPreview,
};

export default meta;
type Story = StoryObj<typeof ChattingPreview>;

export const Default: Story = {
  args: {},
};
