import type { Meta, StoryObj } from '@storybook/react';

import ErrorControlledInput from './ErrorControlledInput';

const meta: Meta<typeof ErrorControlledInput> = {
  component: ErrorControlledInput,
};

export default meta;
type Story = StoryObj<typeof ErrorControlledInput>;

export const Default: Story = {
  args: {
    placeholder: 'place-holder',
  },
};
