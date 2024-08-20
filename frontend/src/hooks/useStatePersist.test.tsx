import { act, renderHook } from '@testing-library/react';
import useStatePersist from './useStatePersist';
import { BrowserRouter } from 'react-router-dom';
import { ReactNode } from 'react';

const mockStorage = (() => {
  const store: { [key: string]: string } = {};
  return {
    getItem: jest.fn((key: string) => store[key] || null),
    setItem: jest.fn((key: string, value: string) => {
      store[key] = value.toString();
    }),
    removeItem: jest.fn((key: string) => {
      delete store[key];
    }),
  };
})();

Object.defineProperty(window, 'sessionStorage', { value: mockStorage });
Object.defineProperty(window, 'localStorage', { value: mockStorage });

const wrapper = ({ children }: { children: ReactNode }) => (
  <BrowserRouter>{children}</BrowserRouter>
);

describe('useStatePersist', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('주어진 initialState으로 초기화된다.', () => {
    const { result } = renderHook(
      () =>
        useStatePersist({
          key: 'testKey',
          initialState: 'initial',
        }),
      { wrapper },
    );

    expect(result.current[0]).toBe('initial');
  });

  it('setState 호출 시 상태가 업데이트된다.', async () => {
    const { result } = renderHook(
      () =>
        useStatePersist({
          key: 'testKey',
          initialState: 'initial',
        }),
      { wrapper },
    );

    act(() => {
      result.current[1]('updated');
    });

    expect(result.current[0]).toBe('updated');
  });

  it('새로고침 후에도 상태가 유지된다.', () => {
    const { result } = renderHook(
      () =>
        useStatePersist({
          key: 'testKey',
          initialState: 'initial',
        }),
      { wrapper },
    );

    act(() => {
      result.current[1]('updated');
    });

    act(() => {
      window.dispatchEvent(new Event('beforeunload'));
    });

    expect(mockStorage.setItem).toHaveBeenCalledWith(
      'testKey',
      JSON.stringify('updated'),
    );

    const { result: newResult } = renderHook(
      () =>
        useStatePersist({
          key: 'testKey',
          initialState: 'initial',
        }),
      { wrapper },
    );

    expect(newResult.current[0]).toBe('updated');
  });

  it('localStorage 옵션이 제대로 동작한다.', () => {
    const { result } = renderHook(
      () =>
        useStatePersist({
          key: 'testKey',
          initialState: 'initial',
          storage: 'localStorage',
        }),
      { wrapper },
    );

    act(() => {
      result.current[1]('updated');
    });

    act(() => {
      window.dispatchEvent(new Event('beforeunload'));
    });

    expect(mockStorage.setItem).toHaveBeenCalledWith(
      'testKey',
      JSON.stringify('updated'),
    );

    const { result: newResult } = renderHook(
      () =>
        useStatePersist({
          key: 'testKey',
          initialState: 'initial',
          storage: 'localStorage',
        }),
      { wrapper },
    );

    expect(newResult.current[0]).toBe('updated');
  });
});
