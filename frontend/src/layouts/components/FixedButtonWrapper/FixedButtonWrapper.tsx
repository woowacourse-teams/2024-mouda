import { PropsWithChildren } from 'react';
import * as S from './FixedButtonWrapper.style';

export default function FixedButtonWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.fixedWrapperStyle}>{children}</div>;
}
