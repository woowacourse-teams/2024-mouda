import { PropsWithChildren } from 'react';
import * as S from './HomeFixedButtonWrapper.style';

export default function HomeFixedButtonWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.fixedWrapperStyle}>{children}</div>;
}
