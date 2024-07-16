import { css } from '@emotion/react';

const font = css`
  border: none;
  color: #fff;
  font-family: Pretendard;
  font-size: 1rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  letter-spacing: -0.02rem;
`;
export const shapes = {
  default: css``,
  circle: css`
    ${font};
    flex-shrink: 0;
    border-radius: 50%;
    background: #ffffff;
    &:active {
      background-color: #e9ecef;
    }
  `,
  bar: css`
    ${font}
    display: flex;
    width: 100%;
    height: 4rem;
    padding: 1rem 3.6875rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    flex-shrink: 0;
    border-radius: 1.875rem;
    background: #477bff;
    &:active {
      background-color: #005bb5;
    }
  `,
};
