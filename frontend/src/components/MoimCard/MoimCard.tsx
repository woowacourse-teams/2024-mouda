import * as S from './MoimCard.style';

import { HTMLProps } from 'react';
import { formatHhmmToKorean, formatYyyymmddToKorean } from '@_utils/formatters';
import { MoimInfo } from '@_types/index';
import { useTheme } from '@emotion/react';
import HeartIcon from '@_components/Icons/HeartIcon';

interface MoimCardProps extends HTMLProps<HTMLDivElement> {
  moimInfo: MoimInfo;
}

export default function MoimCard(props: MoimCardProps) {
  const {
    moimInfo: { title, date, time, place, maxPeople, currentPeople, isZzimed },
    ...args
  } = props;

  const theme = useTheme();

  return (
    <div css={S.cardBox} {...args}>
      <div css={S.titleBox}>
        <h2 css={S.cardTitle({ theme })}>{title}</h2>
        <HeartIcon isFilled={isZzimed} />
      </div>

      <div css={S.subjectBox}>
        <span css={S.subjectTag({ theme })}>날짜 및 시간</span>
        <span css={S.subjectInfo({ theme })}>
          {`${formatYyyymmddToKorean(date)} ${formatHhmmToKorean(time)}`}
        </span>
      </div>

      <div css={S.detailInfo({ theme })}>
        <span>{place}</span>

        <span>
          최대 {maxPeople}명 (현재 {currentPeople}명)
        </span>
      </div>
    </div>
  );
}
