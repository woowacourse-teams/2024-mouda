import type { Meta, StoryObj } from '@storybook/react';

import ProfileCardList from './ProfileCardList';

const meta: Meta<typeof ProfileCardList> = {
  component: ProfileCardList,
};

export default meta;
type Story = StoryObj<typeof ProfileCardList>;

export const Default: Story = {
  args: {
    profiles: [
      {
        nickname: '222222222',
        id: 1,
        profileUrl: '1',
      },
      {
        nickname: '222222222',
        id: 1,
        profileUrl: '',
      },
      {
        nickname: '222222222',
        id: 1,
        profileUrl: '',
      },
      {
        nickname: '222222222',
        id: 1,
        profileUrl: '',
      },
      {
        nickname: '222222222',
        id: 1,
        profileUrl: '',
      },
    ],
  },
};
