import { css, Theme } from '@emotion/react';

export const kakaoButton = () => css`
  border: none;
  background: none;
`;
export const explain = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.b3}
  padding: 1rem;

  color: ${theme.colorPalette.grey[300]};

  background-color: ${theme.colorPalette.white[100]};
`;

export const boundary = ({ theme }: { theme: Theme }) => css`
  height: 4rem;
  border-right: 1px solid ${theme.colorPalette.grey[300]};
`;
