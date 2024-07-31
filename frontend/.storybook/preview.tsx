import type { Preview } from '@storybook/react';
import React from 'react';
import reset from '../src/common/reset.style';
import { Global } from '@emotion/react';

const preview: Preview = {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
  },
  decorators: [
    (Story) => (
      <div style={{ margin: '3em' }}>
        <Global styles={reset} />
        <Story />
      </div>
    ),
  ],
};

export default preview;
