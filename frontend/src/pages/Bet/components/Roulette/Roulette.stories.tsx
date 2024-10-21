import type { Meta, StoryObj } from '@storybook/react';

import Roulette from './Roulette';

const meta: Meta<typeof Roulette> = {
  component: Roulette,
};

export default meta;
type Story = StoryObj<typeof Roulette>;

export const Default: Story = {
  args: {
    loser: "하디",
    nameList: ["소파", "하디", "승", "하", "가", "나"],
    startSpeed: 2,
    minMs: 3000,
    itemPercent: 66,
    stopSpeed: 1
  },
};
