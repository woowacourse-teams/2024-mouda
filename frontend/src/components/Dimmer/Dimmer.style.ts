import { css } from '@emotion/react';

export const dimmer = css`
  position: fixed;
  inset: 0;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;

  width: 100%;
  height: 100%;

  background-color: rgb(0 0 0 / 20%);
`;
