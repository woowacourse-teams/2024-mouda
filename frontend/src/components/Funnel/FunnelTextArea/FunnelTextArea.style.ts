import { css, Theme } from '@emotion/react';

export const textArea = (props: { theme: Theme }) => css`
  ${props.theme.typography.b3}
  resize: none;

  flex-shrink: 0;

  box-sizing: border-box;
  padding: 0.6rem;
  width: 100%;
  height: 24rem;

  font-size: 1.6rem;

  background: ${props.theme.colorPalette.grey[100]};
  border: 1px solid ${props.theme.colorPalette.grey[300]};
  border-radius: 0.8rem;
`;
