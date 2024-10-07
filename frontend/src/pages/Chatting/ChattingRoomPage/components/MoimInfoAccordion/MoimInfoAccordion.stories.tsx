import type { Meta, StoryObj } from '@storybook/react';

import MoimInfoAccordion from './MoimInfoAccordion';

const meta: Meta<typeof MoimInfoAccordion> = {
  component: MoimInfoAccordion,
};

export default meta;
type Story = StoryObj<typeof MoimInfoAccordion>;

export const Default: Story = {
  args: { date: "2023-10-21", time: "11:11", place: '', status: 'MOIMING' },
};
