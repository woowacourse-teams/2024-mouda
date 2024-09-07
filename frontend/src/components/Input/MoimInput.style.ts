import { Theme, css } from '@emotion/react';

export const required = (props: { theme: Theme }) => css`
  color: ${props.theme.colorPalette.red[500]};
`;

export const labelWrapper = () => css`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  width: 100%;
`;

export const title = (props: { theme: Theme }) => css`
  ${props.theme.typography.b1}
`;
// prettier-ignore
export const input = (props: { theme: Theme }) => css`
  ${props.theme.typography.b3}
  flex-shrink: 0;

  box-sizing: border-box;
  width: 100%;
  height: 4rem;
  padding: 0.1rem 0.6rem;

  font-size: 1.6rem;


  background: ${props.theme.colorPalette.grey[100]};
  border: 1px solid ${props.theme.colorPalette.grey[300]};
  border-radius: 0.8rem;

`;

export const errorMessage = (props: { theme: Theme }) => css`
  ${props.theme.typography.c2}
  color: ${props.theme.colorPalette.red[500]};
`;
