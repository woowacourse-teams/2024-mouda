import { Theme, css } from '@emotion/react';

export const layout = css`
  height: 100vh;
`;

export const container = css`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 2rem;
`;

export const code = ({ theme }: { theme: Theme }) => css`
  ${theme.coloredTypography.h3(theme.semantic.primary)};
  display: flex;
  align-items: center;
  justify-content: center;

  height: 20vh;

  background-color: ${theme.colorPalette.grey[100]};
  border-radius: 2rem;
`;
