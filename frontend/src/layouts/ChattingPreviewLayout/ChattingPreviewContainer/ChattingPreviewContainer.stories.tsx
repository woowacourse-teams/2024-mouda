import type { Meta, StoryObj } from '@storybook/react';

import ChattingPreviewContainer from './ChattingPreviewContainer';

const meta: Meta<typeof ChattingPreviewContainer> = {
  component: ChattingPreviewContainer,
};

export default meta;
type Story = StoryObj<typeof ChattingPreviewContainer>;

export const Default: Story = {
  args: {},
};
