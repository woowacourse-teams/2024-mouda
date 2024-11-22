import * as S from './PleaseCardListSkeleton.style';
import PleaseCardSkeleton from './PleaseCardSkeleton/PleaseCardSkeleton';

export default function PleaseCardListSkeleton() {
  return (
    <div css={S.cardListSection}>
      <PleaseCardSkeleton />
      <PleaseCardSkeleton />
      <PleaseCardSkeleton />
      <PleaseCardSkeleton />
      <PleaseCardSkeleton />
      <PleaseCardSkeleton />
    </div>
  );
}
