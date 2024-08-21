import { useTheme } from '@emotion/react';
import { PropsWithChildren } from 'react';
import * as S from './HomeHeaderContent.style';

export default function HomeHeaderContent(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <h1 css={S.logoStyle({ theme })}>{children}</h1>;
}
