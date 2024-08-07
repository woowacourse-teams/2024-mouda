import { DISPLAY_MAX_WIDTH, NAVIGATION_BAR_HEIGHT } from '@_constants/styles';
import { css } from '@emotion/react';

export const fixedWrapperStyle = css`
  position: fixed;
  bottom: 18px;

  display: flex;
  justify-content: right;

  width: 100%;
  max-width: ${DISPLAY_MAX_WIDTH};
  margin-bottom: ${NAVIGATION_BAR_HEIGHT};
  padding-right: 4rem;
`;
