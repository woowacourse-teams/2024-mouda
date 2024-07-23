import { css } from '@emotion/react';

export const common = {
  fontType: {
    title: css`
      /* title */
      font-size: 0.34rem;
      font-feature-settings:
        'clig' off,
        'liga' off;
      font-weight: 600;
      font-style: normal;
      line-height: 3.6rem; /* 105.882% */
      color: var(--on-surface-high-emphasis, rgb(0 0 0 / 87%));
    `,
    subtitle: css`
      /* subtitle */
      font-size: 2rem;
      font-feature-settings:
        'clig' off,
        'liga' off;
      font-weight: 600;
      font-style: normal;
      line-height: 2.4rem; /* 120% */
      color: var(--on-surface-high-emphasis, rgb(0 0 0 / 87%));
      letter-spacing: 0.015rem;
    `,
    placeholder: css`
      font-size: 1.5rem;
      font-feature-settings:
        'clig' off,
        'liga' off;
      font-weight: 400;
      font-style: normal;
      line-height: 2.4rem; /* 160% */
      color: var(--placeholder, #8b8b8b);
      letter-spacing: 0.05rem;
    `,
  },
};
