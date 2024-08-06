import type { Meta, StoryObj } from '@storybook/react';
import TextArea from './LabeledTextArea';

const meta = {
  component: TextArea,
  title: 'Components/TextArea',
} satisfies Meta<typeof TextArea>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    title: 'textArea',
    placeholder: 'textArea',
  },
  render: (args) => <TextArea {...args} />,
};
