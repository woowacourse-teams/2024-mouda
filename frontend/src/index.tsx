import App from './App';
import React from 'react';
import { createRoot } from 'react-dom/client';
import ReactGA from 'react-ga4';
import * as Sentry from '@sentry/react';

import '@_service/forgroundMessage';

if (
  process.env.NODE_ENV === 'production' &&
  process.env.REACT_APP_GOOGLE_ANALYTICS
) {
  ReactGA.initialize(process.env.REACT_APP_GOOGLE_ANALYTICS);
}

if (process.env.NODE_ENV === 'production' && process.env.SENTRY_DSN) {
  Sentry.init({
    dsn: process.env.SENTRY_DSN,
    integrations: [
      Sentry.browserTracingIntegration(),
      Sentry.replayIntegration(),
    ],
    tracesSampleRate: 1.0, // Capture 100% of the transactions
    tracePropagationTargets: ['https://mouda.site/'],
    replaysSessionSampleRate: 0.1, // Set the sample rate at 10%
    replaysOnErrorSampleRate: 1.0, // Sample 100% when errors occur
  });
}

async function enableMocking() {
  if (process.env.MSW !== 'true') {
    return;
  }

  const { worker } = await import('./mocks/browser');

  return worker.start();
}

enableMocking().then(() => {
  const rootElement = document.getElementById('root') as HTMLElement;
  const root = createRoot(rootElement);
  root.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>,
  );
});
