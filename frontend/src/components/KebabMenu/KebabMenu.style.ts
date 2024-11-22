import { css, Theme } from '@emotion/react';

export const kebabContainer = (props: { theme: Theme }) => css`
  position: relative;
  width: fit-content;

  & button {
    white-space: nowrap;
    background: ${props.theme.colorPalette.white[100]};
    ${props.theme.typography.ButtonFont}
    border: none;
    border-bottom: 1px;
  }
`;

export const kebabItem = (props: { theme: Theme }) => css`
  white-space: nowrap;
  background: ${props.theme.colorPalette.white[100]};
  ${props.theme.typography.ButtonFont}
  border: none;
  border-bottom: 1px;
`;

export const optionBox = (props: { theme: Theme }) => css`
  position: absolute;
  top: 5rem;
  right: 0;

  display: flex;
  flex-direction: column;
  gap: 1.5rem;

  padding: 1rem;

  background-color: ${props.theme.colorPalette.white[100]};
  box-shadow: ${props.theme.colorPalette.black[10]} 0 2px 8px 0;

  & button {
    padding: 1rem;
  }
`;
