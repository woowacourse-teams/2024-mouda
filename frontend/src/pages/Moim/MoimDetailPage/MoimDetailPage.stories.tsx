import { Meta, StoryObj } from '@storybook/react/*';
import MoimDetailPage from './MoimDetailPage';

const meta = {
  title: 'pages/MoimDetailPage',
  component: MoimDetailPage,
} satisfies Meta<typeof MoimDetailPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <MoimDetailPage />,
};
