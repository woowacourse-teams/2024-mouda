import type { Preview } from '@storybook/react';
import React from 'react';
import reset from '../src/common/reset.style';
import { Global, ThemeProvider } from '@emotion/react';
import { theme } from '../src/common/theme/theme.style';
import { initialize, mswDecorator } from 'msw-storybook-addon';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { BrowserRouter } from 'react-router-dom';

initialize();

const queryClient = new QueryClient();
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
