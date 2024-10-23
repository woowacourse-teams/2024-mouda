import bitbit from './assets/fonts/DNFBitBitTTF.ttf';
import { css } from '@emotion/react';
import pretendardBlackWoff2 from './assets/fonts/woff2-subset/Pretendard-Black.subset.woff2';
import pretendardBoldWoff2 from './assets/fonts/woff2-subset/Pretendard-Bold.subset.woff2';
import pretendardExtraBoldWoff2 from './assets/fonts/woff2-subset/Pretendard-ExtraBold.subset.woff2';
import pretendardExtraLightWoff2 from './assets/fonts/woff2-subset/Pretendard-ExtraLight.subset.woff2';
import pretendardLightWoff2 from './assets/fonts/woff2-subset/Pretendard-Light.subset.woff2';
import pretendardMediumWoff2 from './assets/fonts/woff2-subset/Pretendard-Medium.subset.woff2';
import pretendardRegularWoff2 from './assets/fonts/woff2-subset/Pretendard-Regular.subset.woff2';
import pretendardSemiboldWoff2 from './assets/fonts/woff2-subset/Pretendard-SemiBold.subset.woff2';
import pretendardThinWoff2 from './assets/fonts/woff2-subset/Pretendard-Thin.subset.woff2';
import partialSansKRRegular from './assets/fonts/PartialSansKR/PartialSansKR-Regular.woff2';

const fonts = css`
  @font-face {
    font-family: Pretendard;
    font-weight: 900;
    font-display: swap;
    src: url(${pretendardBlackWoff2}) format('woff2');
  }

  @font-face {
    font-family: Pretendard;
    font-weight: 800;
    font-display: swap;
    src: url(${pretendardExtraBoldWoff2}) format('woff2');
  }

  @font-face {
    font-family: Pretendard;
    font-weight: 700;
    font-display: swap;
    src: url(${pretendardBoldWoff2}) format('woff2');
  }

  @font-face {
    font-family: Pretendard;
    font-weight: 600;
    font-display: swap;
    src: url(${pretendardSemiboldWoff2}) format('woff2');
  }

  @font-face {
    font-family: Pretendard;
    font-weight: 500;
    font-display: swap;
    src:
      local('Pretendard Medium'),
      url(${pretendardMediumWoff2}) format('woff2'),
      url('./woff-subset/Pretendard-Medium.subset.woff') format('woff');
  }

  @font-face {
    font-family: Pretendard;
    font-weight: 400;
    font-display: swap;
    src: url(${pretendardRegularWoff2}) format('woff2');
  }

  @font-face {
    font-family: Pretendard;
    font-weight: 300;
    font-display: swap;
    src: url(${pretendardLightWoff2}) format('woff2');
  }

  @font-face {
    font-family: Pretendard;
    font-weight: 200;
    font-display: swap;
    src: url(${pretendardExtraLightWoff2}) format('woff2');
  }

  @font-face {
    font-family: Pretendard;
    font-weight: 100;
    font-display: swap;
    src: url(${pretendardThinWoff2}) format('woff2');
  }

  @font-face {
    font-family: bitbit;
    src: url(${bitbit});
  }
  
 @font-face {
    font-family: PartialSansKR;
    font-weight: 400;
    font-display: swap;
    src: url(${partialSansKRRegular}) format('woff2');
  }
`;

export default fonts;
