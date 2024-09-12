import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import * as S from './PleaseCardSkeleton.style';

export default function PleaseCardSkeleton() {
  return (
    <div css={S.cardBox}>
      <div css={S.contentWrapper}>
        <div css={S.headerWrapper}>
          <SkeletonPiece width="30%" height="24px" />
          <SkeletonPiece width="140px" height="24px" />
        </div>
        <SkeletonPiece width="100%" height="24px" />
      </div>

      <div css={S.actionWrapper}>
        <SkeletonPiece width="40px" height="40px" />
        <SkeletonPiece width="50px" height="16px" />
      </div>
    </div>
  );
}
