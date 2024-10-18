import type { Meta, StoryObj } from '@storybook/react';
import ProfileFrame from './ProfileFrame';
import EmptyProfile from '@_common/assets/default_profile.svg?url';
import Plus from '@_common/assets/tabler_plus.svg?url';

const meta = {
  component: ProfileFrame,
  title: 'Components/ProfileFrame',
} satisfies Meta<typeof ProfileFrame>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    width: 7,
    height: 7,
    src: EmptyProfile,
  },
  render: (args) => <ProfileFrame {...args} />,
};

export const WithCustomImageAndCrown: Story = {
  args: {
    width: 7,
    height: 7,
    src: Plus,
    role: 'MOIMER',
  },
  render: (args) => <ProfileFrame {...args} />,
};

export const WithCustomImage: Story = {
  args: {
    width: 7,
    height: 7,
    src: Plus,
  },
  render: (args) => <ProfileFrame {...args} />,
};

export const WithErrorHandling: Story = {
  args: {
    width: 7,
    height: 7,
    src: 'invalid-url.jpg', // 오류를 발생시키기 위한 잘못된 URL
  },
  render: (args) => <ProfileFrame {...args} />,
};
