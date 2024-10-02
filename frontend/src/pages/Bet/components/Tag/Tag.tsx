import * as S from './Tag.style';
import { HTMLProps } from 'react';
import { useTheme } from '@emotion/react';
import { BetDetail } from '@_types/index';
import calculateMinutesUntilDeadline from '@_utils/calculateMinutesUntilDeadline';

interface TagProps extends HTMLProps<HTMLDivElement> {
  deadline: BetDetail['deadline'];
}

export default function Tag(props: TagProps) {
  const { deadline } = props;
  const theme = useTheme();

  const minutesUntilDeadline = calculateMinutesUntilDeadline(deadline);

  return (
    <div css={S.tagBox({ theme, minutesUntilDeadline })}>
      {minutesUntilDeadline < 0
        ? '마감됨'
        : `마감 ${minutesUntilDeadline}분 전`}
    </div>
  );
}
