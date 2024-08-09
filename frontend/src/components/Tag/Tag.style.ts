import { css, Theme } from '@emotion/react';

interface TagBoxProps {
  theme: Theme;
  status: 'MOIMING' | 'COMPLETED' | 'CANCELED';
}
export const tagBox = (props: TagBoxProps) => {
  const { theme, status } = props;
  return css`
    ${theme.typography.tag}
    display: inline-flex;
    gap: 1rem;
    align-items: center;
    justify-content: center;

    padding: 0.2rem 0.6rem;

    color: ${theme.colorPalette.white[100]};

    background-color: ${status === 'CANCELED'
      ? theme.colorPalette.red[400]
      : status === 'MOIMING'
        ? theme.colorPalette.green[200]
        : theme.colorPalette.grey[400]};
    border-radius: 1rem;
  `;
};
