import * as S from './MoimCardListSkeleton.style';
import MoimCardSkeleton from './MoimCardSkeleton/MoimCardSkeleton';

export default function MoimCardListSkeleton() {
  return (
    <div css={S.cardListSection}>
      <MoimCardSkeleton />
      <MoimCardSkeleton />
      <MoimCardSkeleton />
      <MoimCardSkeleton />
      <MoimCardSkeleton />
      <MoimCardSkeleton />
      <MoimCardSkeleton />
    </div>
  );
}
