import * as S from './ChatFilterTag.styles';

import { HTMLAttributes } from 'react';
import { useTheme } from '@emotion/react';

interface ChatFilterTagProps extends HTMLAttributes<HTMLDivElement> {
  isChecked: boolean;
  value: string;
}

export default function ChatFilterTag(props: ChatFilterTagProps) {
  const theme = useTheme();
  const { isChecked, value, ...otherProps } = props;

  return (
    <div css={S.tag({ theme, isChecked })} {...otherProps}>
      {value}
    </div>
  );
}
