import { css, Theme } from '@emotion/react';

export const AccountButton = (props: { theme: Theme }) => css`
  ${props.theme.typography.b2}
  background: none;
  border: 1px solid ${props.theme.colorPalette.grey[200]};
  border-radius: 4px;
`;
