import { css, Theme } from '@emotion/react';

export const mainStyle = ({ theme }: { theme: Theme }) => css`
  padding: 16px 22px;
  background-color: ${theme.colorPalette.grey[100]};
`;
