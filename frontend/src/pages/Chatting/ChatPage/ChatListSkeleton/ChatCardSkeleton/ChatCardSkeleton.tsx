import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import * as S from './ChatCardSkeleton.style';

export default function ChatCardSkeleton() {
  return (
    <div css={S.cardBox}>
      <div css={S.messageContainer}>
        <SkeletonPiece width="40%" height="24px" />
      </div>

      <div css={S.detailInfo}>
        <SkeletonPiece width="60%" height="18px" />
      </div>
    </div>
  );
}
