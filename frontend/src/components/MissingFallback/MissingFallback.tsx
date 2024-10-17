import * as S from './MissingFallback.style';

import { SerializedStyles, useTheme } from '@emotion/react';

import regretCat from '@_common/assets/regret_cat.webp';

interface MissingFallbackProps {
  text: string;
  font?: SerializedStyles;
  backgroundColor?: string | SerializedStyles;
}

export default function MissingFallback(props: MissingFallbackProps) {
  const { text, font, backgroundColor } = props;
  const theme = useTheme();
  return (
    <div css={S.container({ backgroundColor })}>
      <img src={regretCat} alt="미안해용" css={S.image} />
      <span css={font || theme.typography.h5}>{text}</span>
    </div>
  );
}
