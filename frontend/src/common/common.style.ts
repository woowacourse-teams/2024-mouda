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
  iphoneBottom: css`
    ${navigator.userAgent.toLowerCase().includes('iphone')
      ? `/* stylelint-disable */
    padding-bottom: constant(safe-area-inset-bottom);
    /* stylelint-enable */
      'padding-bottom: env(safe-area-inset-bottom);`
      : ''}
  `,
};
