import { css } from '@emotion/react';

export const bottomFixedStyle = css`
  position: fixed;
  bottom: 26px;
  padding: 0 16px;

  width: calc(100% - 32px); // 전체 너비에서 양쪽 패딩(16px * 2)을 뺀 값
`;
