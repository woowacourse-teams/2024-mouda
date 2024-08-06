import { PropsWithChildren } from 'react';
import * as S from './PleaseHeader.style';
import { useTheme } from '@emotion/react';

export default function PleaseHeader(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();

  return (
    <header css={S.headerStyle}>
      <h1 css={S.logoStyle({ theme })}>{children}</h1>
    </header>
  );
}
