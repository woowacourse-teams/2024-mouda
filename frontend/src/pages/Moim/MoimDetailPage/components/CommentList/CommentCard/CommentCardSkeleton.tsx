import * as S from './CommentCard.style';

import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import { useTheme } from '@emotion/react';

export default function CommentCardSkeleton() {
  const theme = useTheme();

  return (
    <div css={S.commentContainer()}>
      <div css={S.commentWrapper({ theme, isChecked: false })}>
        <SkeletonPiece width="3rem" height="3rem" />
        <div css={S.commnetBox()}>
          <div css={S.commentLeft}>
            <div css={S.commentLeftHeader}>
              <SkeletonPiece width="4.1rem" height="1.6rem" />
              <SkeletonPiece width="8.3rem" height="1.4rem" />
            </div>
          </div>
          <SkeletonPiece width="19rem" height="1.4rem" />
        </div>
      </div>
    </div>
  );
}
