import { css } from '@emotion/react';

export const list = ({ size, length }: { size: string; length: number }) => css`
  position: relative;

  display: flex;
  flex-direction: row;
  align-items: center;

  width: calc(${size}*${0.5 + 0.5 * Math.min(length, 3)});

  & > div:first-child {
    z-index: 3;
  }

  & > div:nth-child(2) {
    position: absolute;
    z-index: 2;
    left: calc(${size}*0.5);
  }

  & > div:nth-child(3) {
    position: absolute;
    z-index: 1;
    left: calc(${size}*1);
  }

  & > svg {
    position: absolute;
    left: calc(${size}*2);
  }
`;
