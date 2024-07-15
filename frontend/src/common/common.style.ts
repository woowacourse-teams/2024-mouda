import { css } from '@emotion/react';

export const common = {
  fontType: {
    title: css`
      color: var(--00-on-surface-high-emphasis, rgba(0, 0, 0, 0.87));
      font-feature-settings:
        'clig' off,
        'liga' off;

      /* title */
      font-family: Roboto;
      font-size: 2.125rem;
      font-style: normal;
      font-weight: 600;
      line-height: 2.25rem; /* 105.882% */
    `,
    subtitle: css`
      color: var(--00-on-surface-high-emphasis, rgba(0, 0, 0, 0.87));
      font-feature-settings:
        'clig' off,
        'liga' off;

      /* subtitle */
      font-family: Roboto;
      font-size: 1.25rem;
      font-style: normal;
      font-weight: 600;
      line-height: 1.5rem; /* 120% */
      letter-spacing: 0.00938rem;
    `,
    placeholder: css`
      color: var(--placeholder, #8b8b8b);
      font-feature-settings:
        'clig' off,
        'liga' off;
      font-family: Roboto;
      font-size: 0.9375rem;
      font-style: normal;
      font-weight: 400;
      line-height: 1.5rem; /* 160% */
      letter-spacing: 0.03125rem;
    `,
  },
};
