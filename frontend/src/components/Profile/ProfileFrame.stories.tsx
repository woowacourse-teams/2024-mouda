import type { Meta, StoryObj } from '@storybook/react';
import ProfileFrame from './ProfileFrame';
import EmptyProfile from '@_common/assets/empty_profile.svg';
import Plus from '@_common/assets/tabler_plus.svg?url';

const meta = {
  component: ProfileFrame,
  title: 'Components/ProfileFrame',
} satisfies Meta<typeof ProfileFrame>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    width: '7rem',
    height: '7rem',
    src: EmptyProfile,
  },
  render: (args) => <ProfileFrame {...args} />,
};

export const WithCustomImage: Story = {
  args: {
    width: '7rem',
    height: '7rem',
    src: Plus,
  },
  render: (args) => <ProfileFrame {...args} />,
};

export const WithErrorHandling: Story = {
  args: {
    width: '7rem',
    height: '7rem',
    src: 'invalid-url.jpg', // 오류를 발생시키기 위한 잘못된 URL
  },
  render: (args) => <ProfileFrame {...args} />,
};
