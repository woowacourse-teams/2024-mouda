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
        src: '',
        role: 'moimer',
      },
      {
        nickname: '치코',
        src: '',
        role: 'moimee',
      },
      {
        nickname: '치코',
        src: '',
        role: 'moimer',
      },
      {
        nickname: '치코',
        src: '',
        role: 'moimer',
      },
      {
        nickname: '치코',
        src: '',
        role: 'moimer',
      },
      {
        nickname: '치코',
        src: '',
        role: 'moimer',
      },
      {
        nickname: '치코',
        src: '',
        role: 'moimer',
      },
      {
        nickname: '치코',
        src: '',
        role: 'moimer',
      },
    ],
  },
  render: (args) => <ProfileList {...args} />,
};
