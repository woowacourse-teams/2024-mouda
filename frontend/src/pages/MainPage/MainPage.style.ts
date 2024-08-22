import { css, Theme } from '@emotion/react';

export const headerButton = css`
  background: none;
  border: none;
`;

export const headerLeft = css`
  display: flex;
  gap: 0.5rem;
  align-items: center;
  font-size: 100%;
`;

export const ModalContent = (props: { theme: Theme }) => css`
  ${props.theme.typography.b1}
  margin: 5rem;
`;
