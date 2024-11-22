import { Meta, StoryObj } from '@storybook/react/*';
import MineInfoCard from './MineInfoCard';

const meta = {
  title: 'components/MineInfoCard',
  component: MineInfoCard,
} satisfies Meta<typeof MineInfoCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    myInfo: {
      nickname: '치코',
      name: '이재민',
      profile: 'sdf',
    },
    isEditing: false,
    onProfileClick: () => {},
    setNickname: () => {},
  },
  render: (args) => <MineInfoCard {...args} />,
};
