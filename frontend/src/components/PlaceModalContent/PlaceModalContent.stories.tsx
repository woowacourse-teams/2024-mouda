import type { Meta, StoryObj } from '@storybook/react';

import PlaceModalContent from './PlaceModalContent';

const meta: Meta<typeof PlaceModalContent> = {
  component: PlaceModalContent,
};

export default meta;
type Story = StoryObj<typeof PlaceModalContent>;

export const Default: Story = {
  args: {},
};
