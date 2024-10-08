import { NAVIGATION_BAR_HEIGHT } from '@_constants/styles';
import { css, Theme } from '@emotion/react';

export const listLayout = ({ theme }: { theme: Theme }) => css`
  min-height: calc(100vh - ${NAVIGATION_BAR_HEIGHT});
  margin-bottom: ${NAVIGATION_BAR_HEIGHT};
  background-color: ${theme.colorPalette.grey[100]};
`;
