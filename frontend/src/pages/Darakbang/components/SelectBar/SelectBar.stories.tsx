import type { Meta, StoryObj } from '@storybook/react';

import SelectBar from './SelectBar';

const meta: Meta<typeof SelectBar> = {
  component: SelectBar,
};

export default meta;
type Story = StoryObj<typeof SelectBar>;

export const Default: Story = {
  args: { children: '기존 다락방 참여하기' },
};
