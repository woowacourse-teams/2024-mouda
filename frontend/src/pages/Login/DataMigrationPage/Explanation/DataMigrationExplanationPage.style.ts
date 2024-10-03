import { css, Theme } from '@emotion/react';

export const explanationSection = ({ theme }: { theme: Theme }) => css`
  margin: 0rem 5rem;
  ${theme.typography.s1}
`;
