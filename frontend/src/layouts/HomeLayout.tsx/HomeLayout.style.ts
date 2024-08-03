import { NAVIGATION_BAR_HEIGHT } from '@_constants/styles';
import { css } from '@emotion/react';

export const containerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  margin-bottom: ${NAVIGATION_BAR_HEIGHT};
  padding-bottom: 2rem;
`;
