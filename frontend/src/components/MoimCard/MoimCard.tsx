import * as S from './MoimCard.style';

import { formatHhmmToKorean, formatYyyymmddToKorean } from '@_utils/formatters';

import { HTMLProps } from 'react';
import HeartIcon from '@_components/Icons/HeartIcon';
import { MoimInfo } from '@_types/index';
import useChangeZzim from '@_hooks/mutaions/useChangeZzim';
import { useTheme } from '@emotion/react';

interface MoimCardProps extends HTMLProps<HTMLDivElement> {
  moimInfo: MoimInfo;
}

export default function MoimCard(props: MoimCardProps) {
  const {
    moimInfo: {
      moimId,
      title,
      date,
      time,
      place,
      maxPeople,
      currentPeople,
      isZzimed,
    },
    ...args
  } = props;

  const theme = useTheme();

  const { mutate: changeZzim } = useChangeZzim();

  const handleHeartButtonClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    changeZzim(moimId);
  };

  return (
    <div css={S.cardBox} {...args}>
      <div css={S.titleBox}>
        <h2 css={S.cardTitle({ theme })}>{title}</h2>
        <button css={S.heartButton} onClick={handleHeartButtonClick}>
          <HeartIcon isFilled={isZzimed} />
        </button>
      </div>

      {(date || time) && (
        <div css={S.subjectBox}>
          <span
            css={S.subjectTag({ theme })}
          >{`${date ? '날짜' : ''}${date && time ? ' 및 ' : ''}${time ? '시간' : ''}`}</span>
          <span css={S.subjectInfo({ theme })}>
            {`${date ? formatYyyymmddToKorean(date) + ' ' : ''}${time ? formatHhmmToKorean(time) : ''}`}
          </span>
        </div>
      )}

      <div css={S.detailInfo({ theme })}>
        {place && <span css={theme.typography.b3}>{place}</span>}

        <span css={theme.typography.b3}>
          최대 {maxPeople}명 (현재 {currentPeople}명)
        </span>
      </div>
    </div>
  );
}
