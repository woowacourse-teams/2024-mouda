import type { Meta, StoryObj } from '@storybook/react';

import Roulette from './Roulette';

const meta: Meta<typeof Roulette> = {
  component: Roulette,
};

export default meta;
type Story = StoryObj<typeof Roulette>;

export const Default: Story = {
  args: {
    loser: "승",
    nameList: ["소파", "하디", "승", "하", "가", "나", "다", "라", "마"],
    startSpeed: 5,
    minMs: 3000
  },
};
