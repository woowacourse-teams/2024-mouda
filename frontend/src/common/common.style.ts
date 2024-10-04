import { css } from '@emotion/react';

export const common = {
  nonDrag: css`
    user-select: none;
  `,
  cursorPointer: css`
    cursor: pointer;
  `,
  nonScroll: css`
    &::-webkit-scrollbar {
      display: none;
    }
  `,
};
