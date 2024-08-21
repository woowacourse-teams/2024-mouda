import { Meta, StoryObj } from '@storybook/react/*';
import LoginForm from './LoginForm';

const meta = {
  title: 'components/LoginForm',
  component: LoginForm,
} satisfies Meta<typeof LoginForm>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <LoginForm />,
};
