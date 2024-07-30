import type { Preview } from '@storybook/react';
import React from 'react';
import reset from '../src/common/reset.style';
import { Global, ThemeProvider } from '@emotion/react';
import { theme } from '../src/common/theme/theme.style';

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
        <ThemeProvider theme={theme}>
          <Global styles={reset} />
          <Story />
        </ThemeProvider>
      </div>
    ),
  ],
};

export default preview;
