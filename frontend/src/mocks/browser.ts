import { setupWorker } from 'msw/browser';
import { moimHandler } from './handler/moimHandler';
import { interestHandler } from './handler/interestHandler';
import { pleaseHandler } from './handler/pleaseHandler';

export const worker = setupWorker(
  ...moimHandler,
  ...interestHandler,
  ...pleaseHandler,
);
