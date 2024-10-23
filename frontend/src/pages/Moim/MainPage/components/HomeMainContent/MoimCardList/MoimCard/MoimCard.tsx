import * as S from './MoimCard.style';
import { formatHhmmToKorean, formatYyyymmddToKorean } from '@_utils/formatters';
import { HTMLProps } from 'react';
import HeartIcon from '@_components/Icons/HeartIcon';
import { MoimInfo } from '@_types/index';
import useChangeZzim from '@_hooks/mutaions/useChangeZzim';
import { useTheme } from '@emotion/react';

interface MoimCardProps extends HTMLProps<HTMLLIElement> {
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
  const { onClick, ...restArgs } = args;

  const handleKeyDown = (e: React.KeyboardEvent<HTMLLIElement>) => {
    if (e.currentTarget !== e.target) {
      return;
    }
    if (onClick && (e.key === 'Enter' || e.key === ' ')) {
      e.preventDefault();
      onClick(e as unknown as React.MouseEvent<HTMLLIElement>);
    }
  };
  const handleZzimButtonClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    changeZzim(moimId);
  };

  // 카드 전체 정보를 aria-describedby로 제공
  const cardInfoId = `moim-info-${moimId}`;
  const titleId = `moim-title-${moimId}`;

  return (
    <li
      css={S.cardBox}
      aria-labelledby={titleId}
      aria-describedby={cardInfoId}
      tabIndex={0}
      onClick={onClick}
      onKeyDown={handleKeyDown}
      {...restArgs}
    >
      <div css={S.titleBox}>
        {/* 제목을 스크린 리더에 읽히도록 id를 부여 */}
        <h2 id={titleId} css={S.cardTitle({ theme })}>
          {title}
        </h2>
        <button
          css={S.heartButton}
          onClick={handleZzimButtonClick}
          aria-label={isZzimed ? '찜 취소' : '찜하기'}
          tabIndex={0}
          role="button"
        >
          <HeartIcon isFilled={isZzimed} />
        </button>
      </div>

      <div id={cardInfoId} css={S.detailInfo({ theme })} aria-hidden="true">
        {(date || time) && (
          <div css={S.subjectBox}>
            <span css={S.subjectTag({ theme })}>
              {`${date ? '날짜' : ''}${date && time ? ' 및 ' : ''}${time ? '시간' : ''}`}
            </span>
            <span css={S.subjectInfo({ theme })}>
              {`${date ? formatYyyymmddToKorean(date) + ' ' : ''}${time ? formatHhmmToKorean(time) : ''}`}
            </span>
          </div>
        )}

        {place && (
          <div css={S.subjectItem}>
            <span css={S.subjectSubTag({ theme })}>장소</span>
            <span css={theme.typography.b3}>{place}</span>
          </div>
        )}

        <div css={S.subjectItem}>
          <span css={S.subjectSubTag({ theme })}>인원(현재 인원)</span>
          <span css={theme.typography.b3}>
            최대 {maxPeople}명 (현재 {currentPeople}명)
          </span>
        </div>
      </div>
    </li>
  );
}
