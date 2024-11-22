import { Meta, StoryObj } from '@storybook/react/*';
import PleasePage from './PleasePage';

const meta = {
  title: 'pages/PleasePage',
  component: PleasePage,
} satisfies Meta<typeof PleasePage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <PleasePage />,
};
