import type { Meta, StoryObj } from '@storybook/react';

import UserPreviewList from './UserPreviewList';

const meta: Meta<typeof UserPreviewList> = {
  component: UserPreviewList,
};

export default meta;
type Story = StoryObj<typeof UserPreviewList>;

const imgSrc =
  'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgNGdH6wUBTLeEpqTb-53rFi3FoaGBJywyEA&s';

export const One: Story = {
  args: {
    imageUrls: [imgSrc],
  },
};

export const Two: Story = {
  args: {
    imageUrls: [imgSrc, ''],
  },
};

export const Three: Story = {
  args: {
    imageUrls: [imgSrc, '', ''],
  },
};

export const MoreThanThree: Story = {
  args: {
    imageUrls: [imgSrc, '', '', ''],
  },
};
