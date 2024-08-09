import { css, Theme } from '@emotion/react';

export const tabStyle = () => {
  return css`
    display: flex;
    gap: 12px;
  `;
};

export const tabItemStyle = (props: { theme: Theme; isTurnedOn: boolean }) => {
  const { theme, isTurnedOn } = props;

  return css`
    ${theme.typography.s1}
    color: ${isTurnedOn ? theme.semantic.primary : theme.semantic.disabled};
    border-bottom: ${isTurnedOn
      ? `2px solid ${theme.semantic.primary}`
      : 'none'};
  `;
};
