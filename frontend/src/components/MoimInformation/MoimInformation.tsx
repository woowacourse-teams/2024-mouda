import * as S from './MoimInformation.style';
import { MoimInfo } from '@_types/index';
import { formatYyyymmddToKorean, formatHhmmToKorean } from '@_utils/formatters';
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
    <div css={S.containerStyle()}>
      <h2 css={S.titleStyle({ theme })}>모임 정보</h2>
      <div css={S.cardStyle({ theme })}>
        <div css={S.rowStyle({ theme })}>
          <span>날짜</span>
          <span>{formatYyyymmddToKorean(date)}</span>
        </div>
        <div css={S.rowStyle({ theme })}>
          <span>시간</span>
          <span>{formatHhmmToKorean(time)}</span>
        </div>
        <div css={S.rowStyle({ theme })}>
          <span>장소</span>
          <span>{place}</span>
        </div>
        <div css={S.rowStyle({ theme })}>
          <span>최대 인원</span>
          <span>{maxPeople}명</span>
        </div>
        <div css={S.rowStyle({ theme })}>
          <span>현재 참여 인원</span>
          <span>{currentPeople}명</span>
        </div>
      </div>
    </div>
  );
}
