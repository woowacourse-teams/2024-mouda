import { Theme, css } from '@emotion/react';

export const list = ({ theme }: { theme: Theme }) => css`
  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  gap: 1rem;

  padding: 2rem;

  background-color: ${theme.colorPalette.white[100]};
`;
