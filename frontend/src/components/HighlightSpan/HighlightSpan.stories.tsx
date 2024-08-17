import type { Meta, StoryObj } from '@storybook/react';

import HighlightSpan from './HighlightSpan';

const meta: Meta<typeof HighlightSpan> = {
  component: HighlightSpan,
};

export default meta;
type Story = StoryObj<typeof HighlightSpan>;

export const Default: Story = {
  decorators: () => {
    return (
      <HighlightSpan>
        <HighlightSpan.Highlight>안녕하세요</HighlightSpan.Highlight>
        <HighlightSpan.Normal>{'\n반갑습니다'}</HighlightSpan.Normal>
      </HighlightSpan>
    );
  },
};
