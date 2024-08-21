import { Theme, css } from '@emotion/react';

export const container = css`
  display: flex;
  flex-direction: column;
`;

export const input = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.b1}
  padding: 0.5rem;
  background-color: ${theme.colorPalette.grey[100]};
  border: 0;
  border-radius: 0.8rem;
`;

export const errorMessage = ({ theme }: { theme: Theme }) => css`
  ${theme.coloredTypography.b2('#ff0000')}
  padding:0 0 0 1rem;
`;
