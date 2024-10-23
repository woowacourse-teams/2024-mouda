import type { Meta, StoryObj } from '@storybook/react';

import HappyFallback from './HappyFallback';
import { css } from '@emotion/react';

const meta: Meta<typeof HappyFallback> = {
  component: HappyFallback,
};

export default meta;
type Story = StoryObj<typeof HappyFallback>;

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
