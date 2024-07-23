import { css } from '@emotion/react';

const font = css`
  font-size: 1.6rem;
  font-weight: 700;
  font-style: normal;
  line-height: normal;
  color: #fff;
  letter-spacing: -0.032rem;
`;
export const shapes = (shape: 'circle' | 'bar', disabled: boolean) => {
  if (shape === 'circle') {
    return css`
      ${font};
      flex-shrink: 0;

      background: ${disabled ? '#868e96' : '#ffffff'};
      border: none;
      border-radius: 50%;
      box-shadow: 0 0 3px #444;

      &:active {
        background-color: #868e96;
      }
    `;
  }
  if (shape === 'bar') {
    return css`
      ${font}
      display: flex;
      flex-shrink: 0;
      gap: 1rem;
      align-items: center;
      justify-content: center;

      width: 100%;
      height: 6.4rem;
      padding: 1.6rem 5.9rem;

      background: ${disabled ? '#868e96' : '#477bff'};
      border: none;
      border-radius: 3rem;

      &:active {
        background-color: ${disabled ? '#868e96' : '#005bb5'};
      }
    `;
  }
};
