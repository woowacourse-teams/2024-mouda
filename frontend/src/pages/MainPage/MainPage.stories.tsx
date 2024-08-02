import { Meta, StoryObj } from '@storybook/react/*';
import MainPage from './MainPage';
import { BrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

const meta = {
  title: 'pages/MainPage',
  component: MainPage,
  decorators: [
    (Story) => {
      return (
        <QueryClientProvider client={queryClient}>
          <BrowserRouter>
            <Story />
          </BrowserRouter>
        </QueryClientProvider>
      );
    },
  ],
} satisfies Meta<typeof MainPage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <MainPage />,
};
