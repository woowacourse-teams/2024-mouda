import { Theme, css } from '@emotion/react';

export const layoutStyle = ({ theme }: { theme: Theme }) => css`
  overflow-y: scroll;
  display: flex;
  flex-direction: column;

  height: 100vh;

  background-color: ${theme.colorPalette.grey[100]};
`;
