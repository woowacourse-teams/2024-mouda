import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
import { css } from '@emotion/react';

export const container = css`
  position: fixed;
  bottom: 26px;

  display: flex;
  flex-direction: column;
  gap: 20px;

  width: 100%;
  max-width: ${DISPLAY_MAX_WIDTH};
  padding: 0 22px;
`;
