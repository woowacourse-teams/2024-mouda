import { css, Theme } from '@emotion/react';

interface TagBoxProps {
  theme: Theme;
  color: 'red' | 'green' | 'grey';
}
export const tagBox = (props: TagBoxProps) => {
  const { theme, color } = props;
  return css`
    ${theme.typography.tag}
    display: inline-flex;
    gap: 1rem;
    align-items: center;
    justify-content: center;

    padding: 0.2rem 0.6rem;

    color: ${theme.colorPalette.white[100]};

    background-color: ${color === 'red'
      ? theme.colorPalette.red[400]
      : color === 'green'
        ? theme.colorPalette.green[200]
        : theme.colorPalette.grey[400]};
    border-radius: 1rem;
  `;
};
