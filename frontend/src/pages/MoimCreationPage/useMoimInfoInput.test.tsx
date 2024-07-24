import { renderHook } from '@testing-library/react';
import useMoimInfoInput from './MoimCreatePage.hook';

jest.mock('./MoimCreatePage.util', () => ({
  validateAuthorNickname: jest.fn(() => true),
  validateDate: jest.fn(() => true),
  validateMaxPeople: jest.fn(() => true),
  validatePlace: jest.fn(() => true),
  validateTime: jest.fn(() => true),
  validateTitle: jest.fn(() => true),
}));
describe('useMoimInfoInput', () => {
  it('모든 validate함수가 true일 경우 isValidMoimInfoInput 값이 true가 된다.', () => {
    const { result } = renderHook(() => useMoimInfoInput());

    expect(result.current.isValidMoimInfoInput).toBe(true);
  });
});
