import * as S from '@_components/Tag/Tag.style';

import { HTMLProps } from 'react';
import { useTheme } from '@emotion/react';

interface TagProps extends HTMLProps<HTMLDivElement> {
  status: 'MOIMING' | 'COMPLETE' | 'CANCEL';
}

export default function Tag(props: TagProps) {
  const theme = useTheme();
  const { status } = props;
  return (
    <div css={S.tagBox({ theme, status })}>
      {status === 'CANCEL'
        ? '모임 취소'
        : status === 'MOIMING'
          ? '모임중'
          : '모집완료'}
    </div>
  );
}
