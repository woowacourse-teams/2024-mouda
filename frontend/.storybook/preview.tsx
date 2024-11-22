import { Global, ThemeProvider } from '@emotion/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { initialize, mswDecorator } from 'msw-storybook-addon';

import { BrowserRouter } from 'react-router-dom';
import type { Preview } from '@storybook/react';
import React from 'react';
import reset from '../src/common/reset.style';
import { theme } from '../src/common/theme/theme.style';

initialize();

const queryClient = new QueryClient();
const preview: Preview = {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
      },
    },
  },
  decorators: [
    mswDecorator,
    (Story) => (
      <QueryClientProvider client={queryClient}>
        <ThemeProvider theme={theme}>
          <Global styles={reset} />
          <BrowserRouter>
            <div style={{ margin: '3em' }}>
              <Story />
            </div>
          </BrowserRouter>
        </ThemeProvider>
      </QueryClientProvider>
    ),
  ],
};

export default preview;
