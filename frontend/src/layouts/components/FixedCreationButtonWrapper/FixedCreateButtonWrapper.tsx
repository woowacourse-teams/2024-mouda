import { PropsWithChildren } from 'react';
import * as S from './FixedCreationButtonWrapper.style';

export default function FixedCreateButtonWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.fixedWrapperStyle}>{children}</div>;
}