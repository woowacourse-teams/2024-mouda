import type { Meta, StoryObj } from '@storybook/react';

import MemberCard from './MemberCard';

const meta: Meta<typeof MemberCard> = {
  component: MemberCard,
};

export default meta;
type Story = StoryObj<typeof MemberCard>;

export const Default: Story = {
  args: { name: 'ssssssssssssssssssssssssssssssssssssssssssss' },
};
