import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
import { css } from '@emotion/react';

export const fixedWrapperStyle = css`
  position: fixed;
  bottom: 26px;

  display: flex;
  justify-content: right;

  width: 100%;
  max-width: ${DISPLAY_MAX_WIDTH};
  padding-right: 4rem;
`;
