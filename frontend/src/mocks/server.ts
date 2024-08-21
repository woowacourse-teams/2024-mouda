import { setupServer } from 'msw/node';
import { handlers } from './handler/index';

export const server = setupServer(...handlers);
