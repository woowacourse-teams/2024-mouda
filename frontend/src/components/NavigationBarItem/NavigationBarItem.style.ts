import { Theme, css } from '@emotion/react';

export const navigationBarItem = (props: {
  theme: Theme;
  isActive: boolean;
}) => {
  const { theme, isActive } = props;

  return css`
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

export const navigationBarSpan = css`
  user-select: none;
`;
