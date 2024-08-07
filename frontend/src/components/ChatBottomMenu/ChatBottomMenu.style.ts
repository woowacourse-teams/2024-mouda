import { Theme, css } from '@emotion/react';

export const menu = ({ theme }: { theme: Theme }) => css`
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;

  width: 100%;

  background-color: ${theme.colorPalette.white[100]};
`;
