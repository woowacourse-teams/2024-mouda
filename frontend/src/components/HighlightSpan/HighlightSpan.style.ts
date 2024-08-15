import { SerializedStyles, css } from '@emotion/react';

export const text = ({
  color,
  font,
}: {
  color: string | SerializedStyles;
  font: string | SerializedStyles;
}) => css`
  ${font}
  color:${color};
  word-wrap: break-word;
  white-space: break-spaces;
`;
