import { theme } from '@_common/theme/theme.style';
import { css, Theme } from '@emotion/react';

export const formBox = (props: { theme: Theme }) => css`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 4rem;
  padding: 1rem;

  background: ${props.theme.colorPalette.white[100]};
  border: 1px solid ${theme.colorPalette.grey[200]};
  border-radius: 50px;
`;

export const input = (props: { theme: Theme }) => css`
  ${props.theme.typography.b4}
  width: 100%;
  border: none;
  outline: none;
`;

export const button = (props: { theme: Theme }) => css`
  width: fit-content;
  background-color: ${props.theme.colorPalette.white[100]};
  border: none;
`;
