import { css, Theme } from '@emotion/react';

export const navigationBarItem = (props: {
  theme: Theme;
  isActive: boolean;
}) => {
  const { theme, isActive } = props;

  return css`
    ${theme.typography.c2}
    display: flex;
    flex: 1;
    flex-direction: column;
    gap: 4px;
    align-items: center;
    justify-content: center;

    color: ${isActive
      ? theme.colorPalette.grey[500]
      : theme.colorPalette.grey[300]};
  `;
};
