import { css } from '@emotion/react';

export const common = {
  fontType: {
    title: css`

      /* title */
      font-family: Roboto;
      font-size: 2.125rem;
      font-feature-settings:
        'clig' off,
        'liga' off;
      font-weight: 600;
      font-style: normal;
      line-height: 2.25rem; /* 105.882% */
      color: var(--00-on-surface-high-emphasis, rgb(0 0 0 / 87%));
    `,
    subtitle: css`

      /* subtitle */
      font-family: Roboto;
      font-size: 1.25rem;
      font-feature-settings:
        'clig' off,
        'liga' off;
      font-weight: 600;
      font-style: normal;
      line-height: 1.5rem; /* 120% */
      color: var(--00-on-surface-high-emphasis, rgb(0 0 0 / 87%));
      letter-spacing: 0.00938rem;
    `,
    placeholder: css`
      font-family: Roboto;
      font-size: 0.9375rem;
      font-feature-settings:
        'clig' off,
        'liga' off;
      font-weight: 400;
      font-style: normal;
      line-height: 1.5rem; /* 160% */
      color: var(--placeholder, #8b8b8b);
      letter-spacing: 0.03125rem;
    `,
  },
};
