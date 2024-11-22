import { NAVIGATION_BAR_HEIGHT } from '@_constants/styles';

import { css } from '@emotion/react';

export const navigationBarWrapper = css`
  position: sticky;
  bottom: 0;
  width: 100%;
  height: ${NAVIGATION_BAR_HEIGHT};
`;
