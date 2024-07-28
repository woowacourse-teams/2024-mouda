import { css, Theme } from '@emotion/react';

export const headerStyle = css`
  padding: 14px 22px;
`;

export const logoStyle = (props: { theme: Theme }) => css`
  ${props.theme.typography.h5}
`;
