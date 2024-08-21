import { SerializedStyles, css } from '@emotion/react';

export const text = ({
  color,
  font,
  isCenterAlign,
}: {
  color: string | SerializedStyles;
  font: string | SerializedStyles;
  isCenterAlign?: boolean;
}) => css`
  ${font}
  color:${color};
  word-wrap: break-word;
  white-space: break-spaces;
  ${isCenterAlign && 'text-align: center;'}
`;
