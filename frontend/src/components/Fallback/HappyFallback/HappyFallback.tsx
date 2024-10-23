import * as S from './HappyFallback.style';

import { SerializedStyles, useTheme } from '@emotion/react';

import happyLogo from '@_common/assets/happy_logo.svg?url';

interface HappyFallbackProps {
  text: string;
  font?: SerializedStyles;
  backgroundColor?: string | SerializedStyles;
}

export default function HappyFallback(props: HappyFallbackProps) {
  const { text, font, backgroundColor } = props;
  const theme = useTheme();
  return (
    <div css={S.container({ backgroundColor })}>
      <img src={happyLogo} alt="좋아용" css={S.image} />
      <span css={font || theme.typography.h5}>{text}</span>
    </div>
  );
}
