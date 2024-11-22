import { css } from '@emotion/react';

export const ProfileContanier = css`
  overflow: auto hidden;
  display: flex;
  gap: 20px;

  width: auto;

  -ms-overflow-style: none;

  &::-webkit-scrollbar {
    display: none;
  }
`;
