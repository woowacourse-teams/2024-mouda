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

export const explain = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.b2}
  padding: 1rem;
  color: ${theme.colorPalette.grey[300]};
  text-decoration: underline;
  background-color: ${theme.colorPalette.white[100]};
`;
