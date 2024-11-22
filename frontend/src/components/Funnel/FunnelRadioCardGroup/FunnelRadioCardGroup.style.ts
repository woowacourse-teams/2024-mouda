import { css } from '@emotion/react';

export const container = ({ columns }: { columns: number }) => css`
  ${columns === 1
    ? `
  display: flex;
  flex-direction: column;  
    `
    : `
  display: grid;
  grid-template-columns: repeat(${columns}, 1fr);
    `}
  gap: 24px;
`;
