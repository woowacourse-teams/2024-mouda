import { NAVIGATION_BAR_HEIGHT } from '@_constants/styles';
import { css, Theme } from '@emotion/react';

export const containerStyle = ({ theme }: { theme: Theme }) => css`
  min-height: calc(100vh - ${NAVIGATION_BAR_HEIGHT} - 5.6rem);
  margin-top: 5.6rem;
  margin-bottom: ${NAVIGATION_BAR_HEIGHT};
  background-color: ${theme.colorPalette.grey[100]};
`;
