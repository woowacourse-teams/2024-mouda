import { css, Theme } from '@emotion/react';

export const tabStyle = (props: { theme: Theme }) => {
  const { theme } = props;

  return css`
    display: flex;
    gap: 12px;

    ${theme.typography.s1}
  `;
};

export const tabItemStyle = (props: { theme: Theme; isTurnedOn: boolean }) => {
  const { theme, isTurnedOn } = props;

  return css({
    color: isTurnedOn ? theme.semantic.primary : theme.semantic.disabled,
    borderBottom: isTurnedOn ? `2px solid ${theme.semantic.primary}` : 'none',
  });
};
