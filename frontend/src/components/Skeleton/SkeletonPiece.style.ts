import { css, keyframes, Theme } from '@emotion/react';

const shimmer = keyframes`
  0% {
    background-position: -200% 0;
  }
  100% {
    background-position: 200% 0;
  }
`;

export const skeletonPiece = ({
  theme,
  width,
  height,
}: {
  theme: Theme;
  width: string;
  height: string;
}) => css`
  width: ${width};
  height: ${height};

  background: ${theme.colorPalette.grey[200]};
  background-image: linear-gradient(
    90deg,
    ${theme.colorPalette.grey[200]} 0,
    ${theme.colorPalette.grey[100]} 40px,
    ${theme.colorPalette.grey[200]} 80px
  );
  background-size: 200% 100%;
  border-radius: 4px;

  animation: ${shimmer} 1.5s infinite linear;
`;
