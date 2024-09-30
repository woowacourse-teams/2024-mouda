import * as S from './DarakbangNameWrapper.style';

import { SerializedStyles, useTheme } from '@emotion/react';

import { PropsWithChildren } from 'react';
import { common } from '@_common/common.style';

interface DarakbangNameWrapperProps extends PropsWithChildren {
  font?: string | SerializedStyles;
}

export default function DarakbangNameWrapper(props: DarakbangNameWrapperProps) {
  const theme = useTheme();
  const { font = theme.typography.h5, children } = props;

  return <div css={[S.name({ font }), common.nonScroll]}>{children}</div>;
}
