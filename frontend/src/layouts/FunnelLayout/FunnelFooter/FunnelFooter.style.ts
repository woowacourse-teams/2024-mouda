import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
import { css } from '@emotion/react';

export const container = (keyboardHeight: number) => css`
  position: fixed;
  bottom: ${26 + keyboardHeight}px;

  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: center;

  width: 100%;
  max-width: ${DISPLAY_MAX_WIDTH};
  padding: 0 22px;
`;
