import { SerializedStyles, css } from '@emotion/react';

export const container = ({
  backgroundColor,
}: {
  backgroundColor?: string | SerializedStyles;
}) => css`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;

  ${backgroundColor ? `background-color: ${backgroundColor};` : ''}

  min-width: 30rem;
  min-height: 30rem;

  & > * {
    text-align: center;
    white-space: pre-line;
  }
`;

export const image = css`
  width: 100%;
  max-width: 40rem;
  height: 100%;
  max-height: 40rem;
`;