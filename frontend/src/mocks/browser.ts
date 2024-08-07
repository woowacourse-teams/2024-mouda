import { setupWorker } from 'msw/browser';
import { moimHandler } from './handlers/moimHandler';
import { interestHandler } from './handlers/interestHandler';
import { pleaseHandler } from './handlers/pleaseHandler';

export const worker = setupWorker(...moimHandler, ...interestHandler, ...pleaseHandler);
