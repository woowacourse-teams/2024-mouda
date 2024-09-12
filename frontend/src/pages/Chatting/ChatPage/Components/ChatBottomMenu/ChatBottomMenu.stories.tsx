import type { Meta, StoryObj } from '@storybook/react';

import ChatBottomMenu from './ChatBottomMenu';
import ChatMenuItem from '@_pages/Chatting/ChatPage/Components/ChatMenuItem/ChatMenuItem';
import { Fragment } from 'react';
import Plus from '@_common/assets/plus.svg';
import { css } from '@emotion/react';

const meta: Meta<typeof ChatBottomMenu> = {
  component: ChatBottomMenu,
};

export default meta;
type Story = StoryObj<typeof ChatBottomMenu>;
export const Two: Story = {
  args: {
    children: (
      <Fragment>
        <ChatMenuItem icon={<Plus />} description="더하기" />
        <ChatMenuItem icon={<Plus />} description="더하기" />
      </Fragment>
    ),
  },
  decorators: (Story) => (
    <div
      css={css`
        width: 300px;
      `}
    >
      <Story />
    </div>
  ),
};

export const Nine: Story = {
  args: {
    children: (
      <Fragment>
        <ChatMenuItem icon={<Plus />} description="더하기" />
        <ChatMenuItem icon={<Plus />} description="더하기" />
        <ChatMenuItem icon={<Plus />} description="더하기" />
        <ChatMenuItem icon={<Plus />} description="더하기" />
        <ChatMenuItem icon={<Plus />} description="더하기" />
        <ChatMenuItem
          icon={<Plus />}
          description="더하기rrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
        />
        <ChatMenuItem icon={<Plus />} description="더하기" />
        <ChatMenuItem icon={<Plus />} description="더하기" />
        <ChatMenuItem icon={<Plus />} description="더하기" />
      </Fragment>
    ),
  },
  decorators: (Story) => (
    <div
      css={css`
        width: 300px;
      `}
    >
      <Story />
    </div>
  ),
};
