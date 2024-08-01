import { css, Theme } from '@emotion/react';

export const tag = (props: { theme: Theme; isSelected: boolean }) => {
  const { theme, isSelected } = props;

  return css`
    ${theme.typography.tag}
    display: inline-flex;
    align-items: center;
    justify-content: center;

    padding: 0.2rem 0.6rem;

    color: ${isSelected
      ? theme.colorPalette.white[100]
      : theme.semantic.primary};

    background: ${isSelected
      ? theme.semantic.primary
      : theme.colorPalette.white[100]};
    border: 1px solid ${theme.semantic.primary};
    border-radius: 10px;
  `;
};
