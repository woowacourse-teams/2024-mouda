import { css } from '@emotion/react';

export const canvas = ({
  width,
  height,
}: {
  width: number;
  height: number;
}) => css`
  width: ${width}px;
  height: ${height}px;
  border-radius: 8px;
`;
