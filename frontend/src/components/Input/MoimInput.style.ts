import { css, Theme } from '@emotion/react';

export const required = (props: { theme: Theme }) => css`
  color: ${props.theme.colorPalette.red[500]};
`;

export const labelWrapper = () => css`
  width: 100%;
`;

export const title = (props: { theme: Theme }) => css`
  ${props.theme.typography.b1}
`;

export const input = (props: { theme: Theme }) => css`
  flex-shrink: 0;

  width: 100%;
  height: 4rem;

  font-size: 1.6rem;

  background: ${props.theme.colorPalette.grey[100]};
  border: 1px solid ${props.theme.colorPalette.grey[300]};
  border-radius: 0.8rem;
`;
