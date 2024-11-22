import * as S from './MissingFallback.style';

import { SerializedStyles, useTheme } from '@emotion/react';

import missingLogo from '@_common/assets/missing_logo.svg?url';

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
      <img src={missingLogo} alt="미안해용" css={S.image} />
      <span css={font || theme.typography.h5}>{text}</span>
    </div>
  );
}
