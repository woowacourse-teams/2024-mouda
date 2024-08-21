import { css } from '@emotion/react';

export const card = css`
  display: flex;
`;

export const preview = css`
  display: flex;
  gap: 0.5rem;
  align-items: center;
  justify-content: flex-start;

  max-width: 40rem;
`;

export const name = css`
  overflow: hidden;
  max-width: 20rem;
  padding: 1rem;
  text-overflow: ellipsis;
`;
