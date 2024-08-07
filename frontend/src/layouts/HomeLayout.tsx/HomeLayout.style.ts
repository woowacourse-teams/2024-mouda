import { NAVIGATION_BAR_HEIGHT } from '@_constants/styles';
import { css, Theme } from '@emotion/react';

export const containerStyle = ({ theme }: { theme: Theme }) => css`
  min-height: 100vh;
  margin-top: 8.6rem;
  margin-bottom: ${NAVIGATION_BAR_HEIGHT};
  background-color: ${theme.colorPalette.grey[100]};
`;
