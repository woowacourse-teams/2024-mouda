import { css, Theme } from '@emotion/react';

export const errorMessage = ({ theme }: { theme: Theme }) => css`
  font-size: 1.4rem;
  color: ${theme.colorPalette.red[500]};
  text-align: center;
`;
