import { css, Theme } from '@emotion/react';

interface TagBoxProps {
  theme: Theme;
  minutesUntilDeadline: number;
}
export const tagBox = (props: TagBoxProps) => {
  const { theme, minutesUntilDeadline } = props;
  return css`
    ${theme.typography.tag}
    display: inline-flex;
    gap: 1rem;
    align-items: center;
    justify-content: center;

    padding: 0.2rem 0.6rem;

    color: ${theme.colorPalette.white[100]};

    background-color: ${minutesUntilDeadline >= 10
      ? theme.colorPalette.green[200]
      : minutesUntilDeadline >= 5
        ? theme.colorPalette.yellow[200]
        : minutesUntilDeadline >= 0
          ? theme.colorPalette.red[400]
          : theme.colorPalette.grey[300]};
    border-radius: 1rem;
  `;
};
