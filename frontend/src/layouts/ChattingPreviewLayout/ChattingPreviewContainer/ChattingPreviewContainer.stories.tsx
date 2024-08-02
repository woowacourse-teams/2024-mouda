import type { Meta, StoryObj } from '@storybook/react';

import ChattingPreview from '@_components/ChattingPreview/ChattingPreview';
import ChattingPreviewContainer from './ChattingPreviewContainer';
import { Fragment } from 'react';

const meta: Meta<typeof ChattingPreviewContainer> = {
  component: ChattingPreviewContainer,
};

export default meta;
type Story = StoryObj<typeof ChattingPreviewContainer>;

export const Default: Story = {
  args: {
    children: (
      <Fragment>
        <ChattingPreview
          title="축구하실사람"
          participants={[{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }]}
          lastChat="마지막 메세지"
        />
        <ChattingPreview
          title="밥먹으실 사람"
          participants={[{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }]}
        />
        <ChattingPreview
          title="밥먹으실 사람"
          participants={[{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }]}
        />
        <ChattingPreview
          title="밥먹으실 사람"
          participants={[{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }]}
        />
        <ChattingPreview
          title="밥먹으실 사람"
          participants={[{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }]}
        />
        <ChattingPreview
          title="밥먹으실 사람"
          participants={[{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }]}
        />
        <ChattingPreview
          title="밥먹으실 사람"
          participants={[{ imageUrl: '' }, { imageUrl: '' }, { imageUrl: '' }]}
        />
      </Fragment>
    ),
  },
};
