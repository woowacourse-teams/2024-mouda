import * as S from './InformationContentContainer.style';

import { PropsWithChildren } from 'react';

export default function InformationContentContainer(props: PropsWithChildren) {
  const { children } = props;

  return <main css={S.contentStyle}>{children}</main>;
}
