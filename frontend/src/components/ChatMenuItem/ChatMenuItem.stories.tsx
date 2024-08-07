import type { Meta, StoryObj } from '@storybook/react';

import ChatMenuItem from './ChatMenuItem';
import Plus from '@_common/assets/plus.svg';

const meta: Meta<typeof ChatMenuItem> = {
  component: ChatMenuItem,
};

export default meta;
type Story = StoryObj<typeof ChatMenuItem>;

export const Default: Story = {
  args: { icon: <Plus />, description: '더하기' },
};
