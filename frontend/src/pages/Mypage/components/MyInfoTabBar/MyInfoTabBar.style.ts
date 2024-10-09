import { css, Theme } from '@emotion/react';

export const tabStyle = (props: { theme: Theme }) => css`
  display: flex;
  gap: 12px;
  width: 100%;
  border-bottom: 1px solid ${props.theme.colorPalette.grey[200]};
`;

export const tabItemStyle = (props: { theme: Theme; isTurnedOn: boolean }) => {
  const { theme, isTurnedOn } = props;

  return css`
    ${theme.typography.s1}
    margin: 0 0 0 1rem;
    color: ${isTurnedOn ? theme.semantic.primary : theme.semantic.disabled};
    border-bottom: ${isTurnedOn
      ? `2px solid ${theme.semantic.primary}`
      : 'none'};
  `;
};
