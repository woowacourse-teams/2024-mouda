import { Meta, StoryObj } from '@storybook/react/*';
import OAuthSelectPage from './OAuthSelectPage';

const meta = {
  title: 'pages/OAuthSelectPage',
  component: OAuthSelectPage,
} satisfies Meta<typeof OAuthSelectPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <OAuthSelectPage />,
};
