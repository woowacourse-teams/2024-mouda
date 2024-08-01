import { Theme, css } from '@emotion/react';

export const Header = ({ theme }: { theme: Theme }) => css`
  position: sticky;
  top: 0;
  background-color: ${theme.colorPalette.white[100]};
`;
