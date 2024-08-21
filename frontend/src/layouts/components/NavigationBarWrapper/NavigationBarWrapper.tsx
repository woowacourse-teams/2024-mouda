import { PropsWithChildren } from 'react';
import * as S from './NavigationBarWrapper.style';

export default function NavigationBarWrapper(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.navigationBarWrapper}>{children}</div>;
}
