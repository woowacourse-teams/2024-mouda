import type { Meta, StoryObj } from '@storybook/react';
import ProfileCard from './ProfileCard';
import EmptyProfile from '@_common/assets/empty_profile.svg';
import Plus from '@_common/assets/tabler_plus.svg?url';

const meta = {
  component: ProfileCard,
  title: 'Components/ProfileCard',
} satisfies Meta<typeof ProfileCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    name: '치코',
    src: EmptyProfile,
  },
  render: (args) => <ProfileCard {...args} />,
};

export const WithCustomImage: Story = {
  args: {
    name: '치코',
    src: Plus,
  },
  render: (args) => <ProfileCard {...args} />,
};

export const WithErrorHandling: Story = {
  args: {
    name: '치코',
    src: 'invalid-url.jpg', // 오류를 발생시키기 위한 잘못된 URL
  },
  render: (args) => <ProfileCard {...args} />,
};
