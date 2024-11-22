import { css, Theme } from '@emotion/react';

export const titleWrapper = () => css`
  width: 90%;
  margin-top: 20%;
`;
export const title = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.h3}
`;

export const subtitle = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.b1}
`;
