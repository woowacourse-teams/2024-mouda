import { css, Theme } from '@emotion/react';

export const ModalContent = (props: { theme: Theme }) => css`
  ${props.theme.typography.s1}
  margin: 5rem;
`;
