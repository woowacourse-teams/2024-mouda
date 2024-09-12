import { css } from '@emotion/react';

export const cardBox = css`
  display: flex;
  gap: 1.6rem;
  justify-content: space-between;

  width: 100%;
  padding: 2rem 2.4rem;

  background: white;
  border-radius: 2.5rem;
  box-shadow: rgb(0 0 0 / 24%) 0 3px 8px;
`;

export const contentWrapper = css`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const headerWrapper = css`
  display: flex;
  gap: 0.6rem;
  align-items: center;
`;

export const actionWrapper = css`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
`;
