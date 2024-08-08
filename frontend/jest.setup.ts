import '@testing-library/jest-dom';

import dotenv from 'dotenv';
import { server } from './src/mocks/server';

dotenv.config({ path: './.env' });

beforeAll(() => {
  server.listen({
    onUnhandledRequest: 'error', // 이 옵션을 통해 핸들되지 않은 요청을 경고로 표시
  });
});

afterEach(() => {
  server.resetHandlers();
});
// Clean up after the tests are finished.
afterAll(() => {
  server.close();
});
