import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import * as S from './NotificationCardSkeleton.style';
export default function NotificationCardSkeleton() {
  return (
    <div css={S.cardBox}>
      <SkeletonPiece width="5rem" height="40px" />
      <div css={S.TextInfoBox}>
        <SkeletonPiece width="40vw" height="16px" />
        <SkeletonPiece width="40vw" height="16px" />
      </div>
    </div>
  );
}
