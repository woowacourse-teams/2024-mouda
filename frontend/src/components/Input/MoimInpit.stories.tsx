import type { Meta, StoryObj } from '@storybook/react';

import MoimInput from './MoimInput';

const meta: Meta<typeof MoimInput> = {
  component: MoimInput,
};

export default meta;
type Story = StoryObj<typeof MoimInput>;

export const Default: Story = {
  args: {
    title: 'sdfsdf',
    placeholder: 'place-holder',
    validateFun: () => false,
  },
};
