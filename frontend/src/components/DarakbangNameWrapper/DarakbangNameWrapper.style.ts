import { SerializedStyles, css } from '@emotion/react';

export const name = ({ font }: { font: string | SerializedStyles }) => css`
  ${font}

  max-width: 40vw;
  overflow-x: hidden;
  text-overflow: ellipsis;

  white-space: nowrap;
`;
