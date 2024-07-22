import { Meta, StoryObj } from '@storybook/react';
import MoimInformation from './MoimInformation';

const meta = {
  title: 'Components/MoimInformation',
  component: MoimInformation,
} satisfies Meta<typeof MoimInformation>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    moimInfo: {
      date: '2021-09-01',
      time: '19:00',
      place: '서울시 강남구',
      maxPeople: 5,
      currentPeople: 3,
    },
  },
  render: (args) => <MoimInformation {...args} />,
};
