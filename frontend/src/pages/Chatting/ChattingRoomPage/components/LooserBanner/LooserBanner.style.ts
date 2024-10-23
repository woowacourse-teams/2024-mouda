import { css, Theme } from '@emotion/react';

export const bannerContainer = ({ theme }: { theme: Theme }) => css`
  overflow: hidden; /* 텍스트가 벗어나면 숨김 */
  display: flex;
  align-items: center;

  width: 100%;
  height: 4.4rem;
  padding-bottom: 0.4rem;

  background-color: ${theme.colorPalette.black[100]};
`;

export const bannerTextContainer = css`
  display: flex; /* 텍스트가 가로로 나란히 배치되도록 설정 */
  white-space: nowrap; /* 텍스트가 한 줄로 유지되도록 설정 */
`;

export const bannerText = ({ theme }: { theme: Theme }) => css`
  display: inline-block;
  font-size: 2rem;
  color: ${theme.colorPalette.white[100]};
  ${theme.typography.partialSansKR}
  animation: marquee 3s linear infinite; /* 애니메이션 설정 */

  @keyframes marquee {
    0% {
      transform: translateX(0); /* 처음 상태 */
    }

    100% {
      transform: translateX(-100%); /* 왼쪽 끝으로 이동 */
    }
  }
`;
