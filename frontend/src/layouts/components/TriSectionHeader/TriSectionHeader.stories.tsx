import type { Meta, StoryObj } from '@storybook/react';

import TriSectionHeader from './TriSectionHeader';

const meta: Meta<typeof TriSectionHeader> = {
  component: TriSectionHeader,
};

export default meta;
type Story = StoryObj<typeof TriSectionHeader>;

export const Primary: Story = {
  render: () => {
    return (
      <TriSectionHeader borderBottomColor="gray">
        <TriSectionHeader.Left>
          <div>left1</div>
          <div>left2</div>
        </TriSectionHeader.Left>

        <TriSectionHeader.Center>
          <div>center</div>
        </TriSectionHeader.Center>

        <TriSectionHeader.Right>{'right'}</TriSectionHeader.Right>
      </TriSectionHeader>
    );
  },
};
