import { css, Theme } from '@emotion/react';

interface TagBoxProps {
  theme: Theme;
  color: 'red' | 'green' | 'grey';
}
export const tagBox = (props: TagBoxProps) => {
  const { theme, color } = props;
  return css`
    ${theme.typography.tag}
    color: ${theme.colorPalette.white[100]};
    background-color: ${color === 'red'
      ? theme.colorPalette.red[400]
      : color === 'green'
        ? theme.colorPalette.green[200]
        : theme.colorPalette.grey[400]};
    display: inline-flex;
    padding: 0.2rem 0.6rem;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    border-radius: 1rem;
  `;
};
