import { act, renderHook, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { ReactNode } from 'react';
import { BrowserRouter, useNavigate } from 'react-router-dom';
import useFunnel from './useFunnel';

// react-router-dom 모킹
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: jest.fn(),
}));

const wrapper = ({ children }: { children: ReactNode }) => (
  <BrowserRouter>{children}</BrowserRouter>
);

describe('useFunnel', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('초기 단계를 올바르게 설정한다', () => {
    const { result } = renderHook(
      () => useFunnel<'step1' | 'step2' | 'step3'>('step1'),
      { wrapper },
    );
    expect(result.current.currentStep).toBe('step1');
  });

  it('Funnel 컴포넌트가 현재 단계에 해당하는 컴포넌트를 렌더링한다', () => {
    const { result } = renderHook(
      () => useFunnel<'step1' | 'step2' | 'step3'>('step1'),
      { wrapper },
    );

    const Step1 = () => <div>Step 1</div>;
    const Step2 = () => <div>Step 2</div>;
    const Step3 = () => <div>Step 3</div>;

    const TestComponent = () =>
      result.current.Funnel({
        step: { step1: <Step1 />, step2: <Step2 />, step3: <Step3 /> },
      });

    render(<TestComponent />);

    expect(screen.getByText('Step 1')).toBeInTheDocument();
    expect(screen.queryByText('Step 2')).not.toBeInTheDocument();
    expect(screen.queryByText('Step 3')).not.toBeInTheDocument();
  });

  it('goNextStep을 호출하면 다음 단계로 이동한다', () => {
    const mockNavigate = jest.fn();
    (useNavigate as jest.Mock).mockReturnValue(mockNavigate);

    const { result } = renderHook(
      () => useFunnel<'step1' | 'step2' | 'step3'>('step1'),
      { wrapper },
    );
    act(() => {
      result.current.goNextStep('step2');
    });
    expect(mockNavigate).toHaveBeenCalledWith(expect.any(String), {
      state: { step: 'step2' },
    });
  });

  it('goBack을 호출하면 이전 페이지로 이동한다', () => {
    const mockNavigate = jest.fn();
    (useNavigate as jest.Mock).mockReturnValue(mockNavigate);

    const { result } = renderHook(
      () => useFunnel<'step1' | 'step2' | 'step3'>('step1'),
      { wrapper },
    );
    act(() => {
      result.current.goBack();
    });
    expect(mockNavigate).toHaveBeenCalledWith(-1);
  });
});
