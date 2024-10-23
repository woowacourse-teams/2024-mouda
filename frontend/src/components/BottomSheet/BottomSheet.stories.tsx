import type { Meta, StoryObj } from '@storybook/react';
import BottomSheet from './BottomSheet';
import Button from '@_components/Button/Button';

const meta = {
  component: BottomSheet,
  title: 'Components/BottomSheet',
} satisfies Meta<typeof BottomSheet>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    isOpen: true,
    onDimmerClick: () => {},
    header: <BottomSheet.Header>Header</BottomSheet.Header>,
    cta: (
      <BottomSheet.CTA>
        <Button shape="bar">CTA</Button>
      </BottomSheet.CTA>
    ),
  },
  render: (args) => (
    <BottomSheet {...args}>
      <BottomSheet.Content>Content</BottomSheet.Content>
    </BottomSheet>
  ),
};
