import * as S from '@_components/Card/MoimCard.style';

import {
  formatHhmmToKorean,
  formatYyyymmddToKorean,
} from '../../utils/formatters';

import { MoimInfo } from '../../types';
import { HTMLProps } from 'react';

interface MoimCardProps extends HTMLProps<HTMLDivElement> {
  moimInfo: MoimInfo;
}

export default function MoimCard(props: MoimCardProps) {
  const {
    moimInfo: { title, date, time, place, maxPeople, currentPeople },
    ...args
  } = props;

  return (
    <div css={S.cardBox} {...args}>
      <h2 css={S.cardTitle}>{title}</h2>
      <div css={S.subjectBox}>
        <span css={S.subjectTag}>날짜 및 시간</span>
        <span css={S.subjectInfo}>
          {`${formatYyyymmddToKorean(date)} ${formatHhmmToKorean(time)}`}
        </span>
      </div>
      <div css={S.subjectBox}>
        <span css={S.subjectTag}>장소</span>
        <span css={S.subjectInfo}>{place}</span>
      </div>
      <div css={S.subjectBox}>
        <span css={S.subjectTag}>인원수</span>
        <span css={S.subjectInfo}>
          최대{maxPeople}명 / 현재 {currentPeople}명
        </span>
      </div>
    </div>
  );
}
