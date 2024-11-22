import type { Meta, StoryObj } from '@storybook/react';

import DateTimeModalContent from './DateTimeModalContent';

const meta: Meta<typeof DateTimeModalContent> = {
  component: DateTimeModalContent,
};

export default meta;
type Story = StoryObj<typeof DateTimeModalContent>;

export const Default: Story = {
  args: {},
};
