import type { Meta, StoryObj } from '@storybook/react';

import ChatInfoAccordion from './ChatInfoAccordion';

const meta: Meta<typeof ChatInfoAccordion> = {
  component: ChatInfoAccordion,
};

export default meta;
type Story = StoryObj<typeof ChatInfoAccordion>;

export const Default: Story = {
  args: { tagTheme: 'orange', tagValue: '모임 중', textList: [''] },
};
