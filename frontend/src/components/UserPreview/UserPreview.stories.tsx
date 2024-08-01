import type { Meta, StoryObj } from '@storybook/react';

import UserPreview from './UserPreview';

const meta: Meta<typeof UserPreview> = {
  component: UserPreview,
};

export default meta;
type Story = StoryObj<typeof UserPreview>;

export const Default: Story = {
  args: {
    imageUrl: '',
    size: '',
  },
};
