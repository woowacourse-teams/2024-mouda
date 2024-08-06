import { css, Theme } from '@emotion/react';

export const headerStyle = css`
  position: fixed;
  top: 0;

  width: 100%;
  max-width: 600px;
  padding: 14px 22px;

  background-color: white;
`;

export const logoStyle = (props: { theme: Theme }) => css`
  ${props.theme.typography.h5}
`;
