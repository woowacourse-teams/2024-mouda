import { Theme, css } from '@emotion/react';

export const selectBar = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.s1}
  display:flex;
  align-items: center;

  min-height: 8rem;
  padding: 0 3rem;

  background-color: ${theme.colorPalette.orange[50]};
  border-radius: 2.5rem;
`;
