import * as S from './MoimSummary.style';

import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import TagSkeleton from '@_pages/Moim/MoimDetailPage/components/Tag/TagSkeleton';

export default function MoimSummarySkeleton() {
  return (
    <div css={S.containerStyle}>
      <div css={S.titleBox()}>
        <SkeletonPiece width="50%" height="3.5rem" />
        <TagSkeleton />
      </div>
    </div>
  );
}
