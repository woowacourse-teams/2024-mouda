import { Meta, StoryObj } from '@storybook/react/*';
import ProfileList from './ProfileList';

const meta = {
  title: 'components/ProfileList',
  component: ProfileList,
} satisfies Meta<typeof ProfileList>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    participants: [
      {
        nickname: '치코',
        profile: '',
        role: 'MOIMER',
      },
      {
        nickname: '치코',
        profile: '',
        role: 'MOIMEE',
      },
      {
        nickname: '치코',
        profile: '',
        role: 'MOIMER',
      },
      {
        nickname: '치코',
        profile: '',
        role: 'MOIMER',
      },
      {
        nickname: '치코',
        profile: '',
        role: 'MOIMER',
      },
      {
        nickname: '치코',
        profile: '',
        role: 'MOIMER',
      },
      {
        nickname: '치코',
        profile: '',
        role: 'MOIMER',
      },
      {
        nickname: '치코',
        profile: '',
        role: 'MOIMER',
      },
    ],
  },
  render: (args) => <ProfileList {...args} />,
};
