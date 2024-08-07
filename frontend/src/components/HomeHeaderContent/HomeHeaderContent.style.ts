import { css, Theme } from '@emotion/react';

export const logoStyle = (props: { theme: Theme }) => css`
  ${props.theme.typography.h5}
`;
