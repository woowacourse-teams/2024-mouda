import { css } from '@emotion/react';

export const cardBox = css`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 100%;
  padding: 2rem 2.4rem;

  background: white;
  border-radius: 2.5rem;
  box-shadow: rgb(0 0 0 / 24%) 0 3px 8px;
`;

export const messageContainer = css`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  justify-content: space-evenly;
`;

export const detailInfo = css`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
`;
