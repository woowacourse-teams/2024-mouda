import { css, Theme } from '@emotion/react';

export const Zzimbutton = (props: { theme: Theme }) => css`
  white-space: nowrap;
  background: ${props.theme.colorPalette.white[100]};
  border: none;
`;
