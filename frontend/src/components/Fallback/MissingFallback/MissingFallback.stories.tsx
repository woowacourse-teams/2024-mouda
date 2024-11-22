import type { Meta, StoryObj } from '@storybook/react';

import MissingFallback from './MissingFallback';
import { css } from '@emotion/react';

const meta: Meta<typeof MissingFallback> = {
  component: MissingFallback,
};

export default meta;
type Story = StoryObj<typeof MissingFallback>;

export const Default: Story = {
  args: { text: '없어요' },

  decorators: (Story) => {
    return (
      <div
        css={css`
          width: 1000px;
          height: 1000px;
          border: solid 1px;
        `}
      >
        <Story />
      </div>
    );
  },
};
