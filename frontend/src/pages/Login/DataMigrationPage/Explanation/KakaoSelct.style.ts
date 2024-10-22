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

export const explain = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.b2}
  padding: 1rem;
  text-decoration: underline;
  color: ${theme.colorPalette.grey[300]};

  background-color: ${theme.colorPalette.white[100]};
`;
