import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
import { css, Theme } from '@emotion/react';

export const ModalContent = (props: { theme: Theme }) => css`
  ${props.theme.typography.s1}
  width: 100vw;
  max-width: calc(${DISPLAY_MAX_WIDTH} * 0.8);
  margin-bottom: 4rem;
  padding-right: 60px;
`;
