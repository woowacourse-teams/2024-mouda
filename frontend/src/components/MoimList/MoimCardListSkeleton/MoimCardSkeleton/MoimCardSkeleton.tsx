import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import * as S from './MoimCardSkeleton.style';

export default function MoimCardSkeleton() {
  return (
    <div css={S.cardBox}>
      <div css={S.titleBox}>
        <SkeletonPiece width="50%" height="24px" />
        <SkeletonPiece width="24px" height="24px" />
      </div>

      <div css={S.detailInfo}>
        <SkeletonPiece width="40%" height="18px" />
        <SkeletonPiece width="30%" height="18px" />
        <SkeletonPiece width="60%" height="18px" />
      </div>
    </div>
  );
}
