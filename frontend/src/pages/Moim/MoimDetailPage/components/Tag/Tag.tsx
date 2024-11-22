import * as S from '@_pages/Moim/MoimDetailPage/components/Tag/Tag.style';

import { HTMLProps } from 'react';
import { MoimStatus } from '@_types/index';
import { useTheme } from '@emotion/react';

interface TagProps extends HTMLProps<HTMLDivElement> {
  status: MoimStatus;
}

export default function Tag(props: TagProps) {
  const theme = useTheme();
  const { status, ...restProps } = props;
  return (
    <div css={S.tagBox({ theme, status })} {...restProps}>
      {status === 'CANCELED'
        ? '모임 취소'
        : status === 'MOIMING'
          ? '모임중'
          : '모집완료'}
    </div>
  );
}
