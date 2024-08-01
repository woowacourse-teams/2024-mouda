import { Meta, StoryObj } from '@storybook/react/*';
import LoginForm from './LoginForm';
import { BrowserRouter } from 'react-router-dom';

const meta = {
  title: 'components/LoginForm',
  component: LoginForm,
  decorators: [
    (Story) => {
      return (
        <BrowserRouter>
          <Story />
        </BrowserRouter>
      );
    },
  ],
} satisfies Meta<typeof LoginForm>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <LoginForm />,
};
