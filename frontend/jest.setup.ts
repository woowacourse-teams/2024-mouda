import { server } from './src/mocks/server';
import '@testing-library/jest-dom';
import dotenv from 'dotenv';
dotenv.config({ path: './.env' });

beforeAll(() => {
  console.log('befor all server listen');
  server.listen({
    onUnhandledRequest: 'error', // 이 옵션을 통해 핸들되지 않은 요청을 경고로 표시
  });
});

afterEach(() => {
  console.log('after each server reset handlers ');
  server.resetHandlers();
});
// Clean up after the tests are finished.
afterAll(() => {
  console.log('after all  server closed');
  server.close();
});
