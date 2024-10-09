import { betHandler } from './handler/betHandler';
import { chatHandler } from './handler/chatHandler';
import { interestHandler } from './handler/interestHandler';
import { moimHandler } from './handler/moimHandler';
import { myInfoHandler } from './handler/myInfoHandler';
import { notificationHandler } from './handler/notificationHandler';
import { pleaseHandler } from './handler/pleaseHandler';
import { setupWorker } from 'msw/browser';

export const worker = setupWorker(
  ...moimHandler,
  ...interestHandler,
  ...pleaseHandler,
  ...chatHandler,
  ...notificationHandler,
  ...betHandler,
  ...myInfoHandler,
);
