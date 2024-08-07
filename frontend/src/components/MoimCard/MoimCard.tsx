import * as S from './MoimCard.style';

import { HTMLProps } from 'react';
import { formatHhmmToKorean, formatYyyymmddToKorean } from '@_utils/formatters';
import { MoimInfo } from '@_types/index';
import { useTheme } from '@emotion/react';
import HeartIcon from '@_components/Icons/HeartIcon';
import useChangeZzim from '@_hooks/mutaions/useChangeZzim';

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
