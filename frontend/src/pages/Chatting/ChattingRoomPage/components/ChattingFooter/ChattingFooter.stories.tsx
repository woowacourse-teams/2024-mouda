import type { Meta, StoryObj } from '@storybook/react';

import ChattingFooter from './ChattingFooter';

const meta: Meta<typeof ChattingFooter> = {
  component: ChattingFooter,
};

export default meta;
type Story = StoryObj<typeof ChattingFooter>;

export const Default: Story = {
  args: { onSubmit: (string: string) => alert(string) },
};
