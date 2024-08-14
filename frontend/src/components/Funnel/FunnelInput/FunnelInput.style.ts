import { Theme, css } from '@emotion/react';

export const input = (props: { theme: Theme }) => css`
  ${props.theme.typography.b3}
  box-sizing: border-box;
  width: 100%;
  height: 4.4rem;
  padding: 1.2rem;

  background: ${props.theme.colorPalette.grey[100]};
  border: none;
  border-radius: 10px;
`;
