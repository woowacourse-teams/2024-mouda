import { setupWorker } from 'msw/browser';
import { moimHandler } from './handlers/moimHandler';

export const worker = setupWorker(...moimHandler);
