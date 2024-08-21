import type { Meta, StoryObj } from '@storybook/react';

import MenuItemList from './MenuItemList';

const meta: Meta<typeof MenuItemList> = {
  component: MenuItemList,
};

export default meta;
type Story = StoryObj<typeof MenuItemList>;

export const Default: Story = {
  args: {
    menus: [
      {
        description: '멤버목록',
        onClick: () => {
          alert('멤버목록');
        },
      },
      { description: '수정하기', onClick: () => {} },
    ],
  },
};
