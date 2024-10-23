import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
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
  ${props.theme.typography.s1}
  width: 100vw;
  max-width: calc(${DISPLAY_MAX_WIDTH} * 0.8);
  margin-bottom: 4rem;
  padding-right: 60px;
`;

export const skipLink = () => css`
  position: absolute;
  z-index: 100;
  top: -40px;
  left: 0;

  padding: 8px;

  color: #fff;

  background: #000;

  &:focus {
    top: 0;
  }
`;
