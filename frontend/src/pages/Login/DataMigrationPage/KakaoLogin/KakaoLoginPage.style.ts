import { css, Theme } from '@emotion/react';

export const titleWrapper = () => css`
  margin-top: 20%;
  width: 90%;
`;
export const title = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.h3}
`;

export const subtitle = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.b1}
`;

export const button = () => css``;
