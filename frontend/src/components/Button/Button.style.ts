import { css } from '@emotion/react';

export const shapes = {
  circle: css`
    flex-shrink: 0;
    border-radius: 1.5625rem;
    background: #ededed;
  `,
  bar: css`
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
  `,
};
