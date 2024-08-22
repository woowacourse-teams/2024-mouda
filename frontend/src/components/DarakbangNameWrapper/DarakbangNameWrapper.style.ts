import { SerializedStyles, css } from '@emotion/react';

export const name = ({ font }: { font: string | SerializedStyles }) => css`
  ${font}
  overflow-x: hidden;
  max-width: 30%;
  text-overflow: ellipsis;
  white-space: nowrap;
`;
