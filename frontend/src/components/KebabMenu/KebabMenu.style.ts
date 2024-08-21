import { css, Theme } from '@emotion/react';

export const kebabContainer = (props: { theme: Theme }) => css`
  position: relative;
  width: fit-content;

  & button {
    white-space: nowrap;
    background: ${props.theme.colorPalette.white[100]};
    border: none;
  }
`;
export const optionBox = (props: { theme: Theme }) => css`
  position: absolute;
  top: 100%;
  right: 0;

  display: flex;
  flex-direction: column;
  gap: 1rem;

  box-shadow: ${props.theme.colorPalette.grey[100]} 0 2px 8px 0;
`;
