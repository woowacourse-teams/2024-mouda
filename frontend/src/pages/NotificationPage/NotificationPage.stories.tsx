import { Meta, StoryObj } from '@storybook/react/*';
import NotificationPage from './NotificationPage';

const meta = {
  title: 'pages/NotificationPage',
  component: NotificationPage,
} satisfies Meta<typeof NotificationPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <NotificationPage />,
};
