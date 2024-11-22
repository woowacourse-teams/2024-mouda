import * as S from './MoimInformation.style';

import { formatHhmmToKorean, formatYyyymmddToKorean } from '@_utils/formatters';

import { MoimInfo } from '@_types/index';
import { useTheme } from '@emotion/react';

interface MoimInformationProps {
  moimInfo: Pick<
    MoimInfo,
    'date' | 'time' | 'place' | 'maxPeople' | 'currentPeople'
  >;
}

export default function MoimInformation(props: MoimInformationProps) {
  const { date, time, place, maxPeople, currentPeople } = props.moimInfo;
  const theme = useTheme();

  return (
    <section css={S.containerStyle()}>
      <h2 css={S.titleStyle({ theme })} aria-label="모임 정보">
        모임 정보
      </h2>
      <div css={S.cardStyle({ theme })}>
        {date && (
          <div css={S.rowStyle({ theme })}>
            <span aria-label="날짜">날짜</span>
            <span aria-label={formatYyyymmddToKorean(date)}>
              {formatYyyymmddToKorean(date)}
            </span>
          </div>
        )}

        {time && (
          <div css={S.rowStyle({ theme })}>
            <span aria-label="시간">시간</span>
            <span aria-label={formatHhmmToKorean(time)}>
              {formatHhmmToKorean(time)}
            </span>
          </div>
        )}

        {place && (
          <div css={S.rowStyle({ theme })}>
            <span aria-label="장소">장소</span>
            <span aria-label={place}>{place}</span>
          </div>
        )}

        <div css={S.rowStyle({ theme })}>
          <span aria-label="최대 인원">최대 인원</span>
          <span aria-label={`${maxPeople}명`}>{maxPeople}명</span>
        </div>
        <div css={S.rowStyle({ theme })}>
          <span aria-label="현재 참여 인원">현재 참여 인원</span>
          <span aria-label={`${currentPeople}명`}>{currentPeople}명</span>
        </div>
      </div>
    </section>
  );
}
