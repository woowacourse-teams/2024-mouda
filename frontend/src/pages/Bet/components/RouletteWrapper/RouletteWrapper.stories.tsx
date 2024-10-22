import type { Meta, StoryObj } from '@storybook/react';

import Roulette from '@_pages/Bet/components/Roulette/Roulette';
import RouletteWrapper from './RouletteWrapper';

const meta: Meta<typeof RouletteWrapper> = {
  component: RouletteWrapper,
};

export default meta;
type Story = StoryObj<typeof RouletteWrapper>;

export const Default: Story = {
  args: {
    mainDescription: '00:00',
    description: '너만 오면*100%*',
    title: '너만 오면고',
    children: (
      <Roulette
        nameList={[
          '안녕',
          '바보',
          '멍청이',
          '소파',
          '대구',
          '고기',
          '심지',
          '치코',
          '수야',
        ]}
        loser="안녕"
        minMs={0}
        backgroundColor="#D2D5DB"
        startSpeed={10}
        itemPercent={100}
        font="700 normal 5rem 'Pretendard'"
        style={{ width: '230px', height: '96px', borderRadius: '1rem' }}
      />
    ),
  },
};
