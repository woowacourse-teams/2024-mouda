import { Meta, StoryObj } from '@storybook/react/*';
import MyPage from './MyPage';

const meta = {
  title: 'pages/MyPage',
  component: MyPage,
} satisfies Meta<typeof MyPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <MyPage />,
};
