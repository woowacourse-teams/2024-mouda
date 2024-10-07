import { css } from '@emotion/react';

export const shadow = css`
  position: fixed;
  z-index: -1;
  top: 0;
  left: 50%;
  transform: translateX(-50%);

  width: 600px;
  height: 100vh;
  margin: 0 auto;

  line-height: 1;

  box-shadow:
    0 10px 20px rgb(0 0 0 / 19%),
    0 6px 6px rgb(0 0 0 / 23%);
`;
