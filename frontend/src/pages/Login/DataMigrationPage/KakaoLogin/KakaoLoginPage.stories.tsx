import { Meta, StoryObj } from '@storybook/react/*';
import KakaoLoginPage from './KakaoLoginPage';

const meta = {
  title: 'pages/OAuthSelectPage',
  component: KakaoLoginPage,
} satisfies Meta<typeof KakaoLoginPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <KakaoLoginPage />,
};
