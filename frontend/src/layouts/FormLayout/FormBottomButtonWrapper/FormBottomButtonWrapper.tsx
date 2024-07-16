import { PropsWithChildren } from 'react';
import * as S from './FormBottomButtonWrapper.style';

export default function FormBottomButtonWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.bottomFixedStyle}>{children}</div>;
}
