import { css } from '@emotion/react';

const font = css`
  color: #fff;
  font-family: Pretendard;
  font-size: 1rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  letter-spacing: -0.02rem;
`;
export const shapes = (shape: 'circle' | 'bar', disabled: boolean) => {
  if (shape === 'circle') {
    return css`
      ${font};
      flex-shrink: 0;
      border: none;
      box-shadow: 0px 0px 3px #444;
      border-radius: 50%;
      background: ${disabled ? '#868e96' : '#ffffff'};
      &:active {
        background-color: #868e96;
      }
    `;
  }
  if (shape === 'bar') {
    return css`
      ${font}
      border: none;
      display: flex;
      width: 100%;
      height: 4rem;
      padding: 1rem 3.6875rem;
      justify-content: center;
      align-items: center;
      gap: 0.625rem;
      flex-shrink: 0;
      border-radius: 1.875rem;
      background: ${disabled ? '#868e96' : '#477bff'};
      &:active {
        background-color: ${disabled ? '#868e96' : '#005bb5'};
      }
    `;
  }
};
