import type { Meta, StoryObj } from '@storybook/react';

import HighlightSpan from './HighlightSpan';

const meta: Meta<typeof HighlightSpan> = {
  component: HighlightSpan,
};

export default meta;
type Story = StoryObj<typeof HighlightSpan>;

export const Default: Story = {
  args: { highlightTexts: ['어서오세요'], normalTexts: ['반갑습니다'] },
};
