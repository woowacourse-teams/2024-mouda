import { Theme, css } from '@emotion/react';

export const grey400C2 = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.c2}
  color:${theme.colorPalette.grey[400]}
`;

export const basicChat = css`
  white-space: pre;
`;
