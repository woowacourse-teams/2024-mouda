import { css, Theme } from '@emotion/react';

export const CardBox = (props: { theme: Theme }) => css`
  display: flex;
  gap: 1rem;
  align-items: center;

  width: 100%;

  border-radius: 1rem;

  &:active {
    background-color: ${props.theme.colorPalette.grey[100]};
  }
`;

export const Title = (props: { theme: Theme }) => css`
  ${props.theme.typography.b1}
`;
