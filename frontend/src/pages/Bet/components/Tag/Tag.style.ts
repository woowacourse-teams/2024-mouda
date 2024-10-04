import { css, Theme } from '@emotion/react';

interface TagBoxProps {
  theme: Theme;
  isAnnounced: boolean;
  minutesUntilDeadline: number;
}
export const tagBox = (props: TagBoxProps) => {
  const { theme, isAnnounced, minutesUntilDeadline } = props;
  return css`
    ${theme.typography.tag}
    display: inline-flex;
    gap: 1rem;
    align-items: center;
    justify-content: center;

    padding: 0.2rem 0.6rem;

    color: ${theme.colorPalette.white[100]};

    background-color: ${isAnnounced || minutesUntilDeadline < 0
      ? theme.colorPalette.grey[300]
      : minutesUntilDeadline < 5
        ? theme.colorPalette.red[400]
        : minutesUntilDeadline < 10
          ? theme.colorPalette.yellow[200]
          : theme.colorPalette.green[200]};
    border-radius: 1rem;
  `;
};
