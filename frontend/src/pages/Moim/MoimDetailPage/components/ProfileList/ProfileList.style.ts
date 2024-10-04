import { css } from '@emotion/react';

export const ProfileContanier = css`
  /* scrollbar-width: none;  TODO Firefox 현재 no-unsupported-browser-features 경고 발생 중 추후 확인 필요 */

  overflow: auto hidden;
  display: flex;
  gap: 20px;

  width: auto;

  -ms-overflow-style: none;

  &::-webkit-scrollbar {
    display: none;
  }
`;
