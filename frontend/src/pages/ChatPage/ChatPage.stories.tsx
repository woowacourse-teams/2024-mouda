import { Meta, StoryObj } from '@storybook/react/*';
import ChatPage from './ChatPage';

const meta = {
  title: 'pages/ChatPage',
  component: ChatPage,
} satisfies Meta<typeof ChatPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <ChatPage />,
};
