import { Meta, StoryObj } from '@storybook/react/*';
import ProfileList from './ProfileList';

const meta = {
  title: 'components/ProfileList',
  component: ProfileList,
} satisfies Meta<typeof ProfileList>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {},
  render: (args) => <ProfileList {...args} />,
};
