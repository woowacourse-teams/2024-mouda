import type { Meta, StoryObj } from '@storybook/react';
import MessageInput from './MessageInput';

const meta = {
  component: MessageInput,
  title: 'Components/MessageInput',
} satisfies Meta<typeof MessageInput>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    placeHolder: '댓글을 입력해주세요',
    disabled: false,
    onSubmit: () => {},
  },
  render: (args) => <MessageInput {...args} />,
};
