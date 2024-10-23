import { css, Theme } from '@emotion/react';

export const explanationSection = ({ theme }: { theme: Theme }) => css`
  margin: 0 5rem;
  ${theme.typography.s1}
`;
