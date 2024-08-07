import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
import { css } from '@emotion/react';

export const headerStyle = css`
  position: fixed;
  top: 0;

  width: 100%;
  max-width: ${DISPLAY_MAX_WIDTH};
  padding: 14px 22px 0;

  background-color: white;
`;
