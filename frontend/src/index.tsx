import App from './App';
import React from 'react';
import { createRoot } from 'react-dom/client';

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
