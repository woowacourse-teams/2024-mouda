import type { Meta, StoryObj } from '@storybook/react';
import ProfileCard from './ProfileCard';
import EmptyProfile from '@_common/assets/default_profile.svg?url';
import Plus from '@_common/assets/tabler_plus.svg?url';

const meta = {
  component: ProfileCard,
  title: 'Components/ProfileCard',
} satisfies Meta<typeof ProfileCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    info: {
      darakbangMemberId: 1,
      nickname: '치코',
      profile: EmptyProfile,
      role: 'MOIMEE',
    },
  },
  render: (args) => <ProfileCard {...args} />,
};

export const WithCustomImage: Story = {
  args: {
    info: {
      darakbangMemberId: 1,
      nickname: '치코',
      profile: Plus,
      role: 'MOIMEE',
    },
  },
  render: (args) => <ProfileCard {...args} />,
};

export const WithErrorHandling: Story = {
  args: {
    info: {
      darakbangMemberId: 1,
      nickname: '치코',
      profile: 'invalid-url.jpg', // 오류를 발생시키기 위한 잘못된 URL
      role: 'MOIMEE',
    },
  },
  render: (args) => <ProfileCard {...args} />,
};
