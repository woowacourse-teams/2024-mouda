import { Meta, StoryObj } from '@storybook/react/*';
import MineInfoCard from './MineInfoCard';
import { BrowserRouter } from 'react-router-dom';

const meta = {
  title: 'components/MineInfoCard',
  component: MineInfoCard,
  decorators: [
    (Story) => {
      return (
        <BrowserRouter>
          <Story />
        </BrowserRouter>
      );
    },
  ],
} satisfies Meta<typeof MineInfoCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    nickname: '뽀로로',
    profile: '',
  },
  render: (args) => <MineInfoCard {...args} />,
};
