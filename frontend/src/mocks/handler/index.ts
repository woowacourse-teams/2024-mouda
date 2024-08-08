import { chatHandler } from './chatHandler';
import { moimHandler } from './moimHandler';

export const handlers = [...moimHandler, ...chatHandler];
