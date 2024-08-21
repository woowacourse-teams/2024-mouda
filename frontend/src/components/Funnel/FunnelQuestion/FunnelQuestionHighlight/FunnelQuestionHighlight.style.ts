import { css, Theme } from '@emotion/react';

export const text = ({ theme }: { theme: Theme }) => css`
  color: ${theme.semantic.primary};
`;
