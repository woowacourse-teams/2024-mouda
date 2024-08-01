import { css } from '@emotion/react';

export const contentStyle = css`
  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;
  padding: 28px 22px;

  &::-webkit-scrollbar {
    display: none;
  }
`;
