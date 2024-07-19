import type { Meta, StoryObj } from '@storybook/react';

import { Fragment } from 'react';
import TriSectionHeader from './TriSectionHeader';

const meta: Meta<typeof TriSectionHeader> = {
  component: TriSectionHeader,
};

export default meta;
type Story = StoryObj<typeof TriSectionHeader>;

export const Primary: Story = {
  args: {
    left: (
      <Fragment>
        <div>left1</div>
        <div>left2</div>
      </Fragment>
    ),
    center: <div>center</div>,
    right: 'sdf',
    borderBottomColor: 'lightgray',
  },
};
