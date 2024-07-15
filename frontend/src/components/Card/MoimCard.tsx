import * as S from './MoimCard.style';

export default function MoimCard() {
  return (
    <div css={S.cardBox}>
      <h2 css={S.cardTitle}>축구 하실 사람?</h2>
      <div css={S.subjectBox}>
        <span css={S.subjectTag}>날짜 및 시간</span>
        <span css={S.subjectInfo}>7월 15일 오후 2시 </span>
      </div>
      <div css={S.subjectBox}>
        <span css={S.subjectTag}>장소</span>
        <span css={S.subjectInfo}>서울 마포구 독막로 96 1층</span>
      </div>
      <div css={S.subjectBox}>
        <span css={S.subjectTag}>최대인원수</span>
        <span css={S.subjectInfo}>최대 4명 (현재 2명)</span>
      </div>
    </div>
  );
}
