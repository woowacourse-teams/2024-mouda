import { renderHook } from '@testing-library/react';
import usePleaseInfoInput from './PleaseCreationPage.hook';

jest.mock('./PleaseCreationPage.util', () => ({
  validateDescription: jest.fn(() => true),
  validateTitle: jest.fn(() => true),
}));
describe('usePleaseInfoInput', () => {
  it('모든 validate함수가 true일 경우 isValidPleaseInfoInput 값이 true가 된다.', () => {
    const { result } = renderHook(() => usePleaseInfoInput());

    expect(result.current.isValidPleaseInfoInput).toBe(true);
  });
});
