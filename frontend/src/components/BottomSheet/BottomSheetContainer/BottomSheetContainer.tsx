import { PropsWithChildren } from 'react';
import * as S from './BottomSheetContainer.style';

export default function BottomSheetContainer(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.container}>{children}</div>;
}
