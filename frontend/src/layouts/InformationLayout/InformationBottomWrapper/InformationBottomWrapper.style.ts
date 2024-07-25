import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
import { css } from '@emotion/react';

// TODO: 바텀 버튼 UI에 대한 기획 논의 필요
export const bottomFixedStyle = css`
  position: fixed;
  bottom: 26px;

  width: 100%;
  max-width: ${DISPLAY_MAX_WIDTH};
  padding: 0 16px;
`;
