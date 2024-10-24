import * as S from './SelectBar.style';

import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';

interface SelectBarProps extends PropsWithChildren {
  onClick: () => void;
}

export default function SelectBar(props: SelectBarProps) {
  const { children, onClick } = props;
  const theme = useTheme();

  return (
    <div css={S.selectBar({ theme })} onClick={onClick}>
      {children}
    </div>
  );
}
