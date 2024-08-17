import { Theme, css } from '@emotion/react';

export const bottom = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.s1}
  display: flex;
  align-items: center;
  justify-content: space-evenly;

  padding: 2rem;

  color: ${theme.semantic.primary};
  text-decoration: underline;
`;

export const fallbackContainer = css`
  height: 30rem;
`;
