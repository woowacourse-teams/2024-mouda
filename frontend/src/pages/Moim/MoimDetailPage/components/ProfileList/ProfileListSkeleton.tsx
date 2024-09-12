import * as S from './ProfileList.style';

import { Fragment } from 'react';
import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import { useTheme } from '@emotion/react';

export default function ProfileListSkeleton() {
  const theme = useTheme();
  return (
    <Fragment>
      <div css={theme.typography.s1}>참여자</div>
      <div css={S.ProfileContanier}>
        <SkeletonPiece height="8rem" width="8rem" />
        <SkeletonPiece height="8rem" width="8rem" />
        <SkeletonPiece height="8rem" width="8rem" />
        <SkeletonPiece height="8rem" width="8rem" />
        <SkeletonPiece height="8rem" width="8rem" />
      </div>
    </Fragment>
  );
}
