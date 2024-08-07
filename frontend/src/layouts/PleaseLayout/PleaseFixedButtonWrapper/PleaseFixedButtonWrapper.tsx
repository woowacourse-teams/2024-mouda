import { PropsWithChildren } from 'react';
import * as S from './PleaseFixedButtonWrapper.style';

export default function PleaseFixedButtonWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.fixedWrapperStyle}>{children}</div>;
}
