import { NAVIGATION_BAR_HEIGHT } from '@_constants/styles';
import POLICES from '@_constants/poclies';
import { css } from '@emotion/react';

export const navigationBarWrapper = css`
  position: fixed;
  z-index: ${POLICES.footerZIndex};
  bottom: 0;

  width: 100%;
  height: ${NAVIGATION_BAR_HEIGHT};
`;
