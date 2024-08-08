import { chatHandler } from './handler/chatHandler';
import { interestHandler } from './handler/interestHandler';
import { moimHandler } from './handler/moimHandler';
import { pleaseHandler } from './handler/pleaseHandler';
import { setupWorker } from 'msw/browser';

export const worker = setupWorker(
  ...moimHandler,
  ...interestHandler,
  ...pleaseHandler,
  ...chatHandler,
);
