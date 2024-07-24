import { renderHook, act } from '@testing-library/react';
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

    const { handleChange } = result.current;

    act(() => {
      handleChange({
        target: {
          name: 'title',
          value: 'Valid Title',
        },
      } as React.ChangeEvent<HTMLInputElement>);
    });

    act(() => {
      handleChange({
        target: {
          name: 'date',
          value: '2024-12-31',
        },
      } as React.ChangeEvent<HTMLInputElement>);
    });

    act(() => {
      handleChange({
        target: {
          name: 'time',
          value: '12:00',
        },
      } as React.ChangeEvent<HTMLInputElement>);
    });

    act(() => {
      handleChange({
        target: {
          name: 'place',
          value: 'Valid Place',
        },
      } as React.ChangeEvent<HTMLInputElement>);
    });

    act(() => {
      handleChange({
        target: {
          name: 'maxPeople',
          value: '10',
        },
      } as React.ChangeEvent<HTMLInputElement>);
    });

    act(() => {
      handleChange({
        target: {
          name: 'authorNickname',
          value: 'ValidNickname',
        },
      } as React.ChangeEvent<HTMLInputElement>);
    });

    expect(result.current.isValidMoimInfoInput).toBe(true);
  });
});
