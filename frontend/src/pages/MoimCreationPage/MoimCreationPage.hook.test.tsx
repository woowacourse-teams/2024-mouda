import { act, renderHook } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import useMoimCreationForm from './MoimCreationPage.hook';
import { ReactNode } from 'react';
import * as utils from './MoimCreationPage.util';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

// 세션 스토리지 모킹
const mockSessionStorage = (() => {
  let store: { [key: string]: string } = {};
  return {
    getItem: jest.fn((key: string) => store[key] || null),
    setItem: jest.fn((key: string, value: string) => {
      store[key] = value.toString();
    }),
    removeItem: jest.fn((key: string) => {
      delete store[key];
    }),
    clear: jest.fn(() => {
      store = {};
    }),
  };
})();

Object.defineProperty(window, 'sessionStorage', {
  value: mockSessionStorage,
});

const queryClient = new QueryClient();
const wrapper = ({ children }: { children: ReactNode }) => (
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>{children}</BrowserRouter>
  </QueryClientProvider>
);

describe('useMoimCreationForm', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('URL로 페이지 접근 시, 새로운 모임 생성으로 판단해 초기 상태로 시작한다', () => {
    jest.spyOn(utils, 'isApprochedByUrl').mockReturnValue(true);

    const { result } = renderHook(() => useMoimCreationForm('이름입력'), {
      wrapper,
    });

    expect(result.current.formData).toEqual({
      title: '',
      place: '',
      date: '',
      time: '',
      maxPeople: 0,
      description: '',
    });
    expect(sessionStorage.removeItem).toHaveBeenCalledWith('moimCreationInfo');
  });

  it('폼 데이터 업데이트 시 유효성 검사가 올바르게 수행된다', () => {
    const { result } = renderHook(() => useMoimCreationForm('이름입력'), {
      wrapper,
    });

    act(() => {
      result.current.updateTitle('유효한 모임 제목');
      result.current.updateMaxPeople(10);
    });

    expect(result.current.formValidation).toEqual({
      title: true,
      place: true,
      date: true, // 빈 문자열도 유효하다고 가정
      time: true, // 빈 문자열도 유효하다고 가정
      maxPeople: true,
    });

    act(() => {
      result.current.updateTitle('');
      result.current.updateMaxPeople(0);
    });

    expect(result.current.formValidation).toEqual({
      title: false,
      place: true,
      date: true,
      time: true,
      maxPeople: false,
    });
  });
});
