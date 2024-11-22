import { PropsWithChildren } from 'react';
import * as S from './FixedBottomButtonWrapper.style';

export default function FixedBottomButtonWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.fixedWrapperStyle}>{children}</div>;
}
