import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
import { css } from '@emotion/react';

export const headerStyle = css`
  position: fixed;
  top: 0;

  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 100%;
  max-width: ${DISPLAY_MAX_WIDTH};

  background-color: white;
`;
