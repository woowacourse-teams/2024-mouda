import * as S from '@_components/Tag/Tag.style';

import { HTMLProps, ReactNode } from 'react';
import { useTheme } from '@emotion/react';

interface TagProps extends HTMLProps<HTMLDivElement> {
  color: 'red' | 'green' | 'grey';
  children: ReactNode;
}

export default function Tag(props: TagProps) {
  const theme = useTheme();
  const { children, color } = props;
  return <div css={S.tagBox({ theme, color })}>{children}</div>;
}
