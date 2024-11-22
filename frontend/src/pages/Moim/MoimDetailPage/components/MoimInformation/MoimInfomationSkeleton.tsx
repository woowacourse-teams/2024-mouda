import * as S from './MoimInformation.style';

import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import { useTheme } from '@emotion/react';

export default function MoimInformationSkeleton() {
  const theme = useTheme();

  return (
    <div css={S.containerStyle()}>
      <h2 css={S.titleStyle({ theme })}>모임 정보</h2>
      <div css={S.cardStyle({ theme })}>
        <div css={S.rowStyle({ theme })}>
          <span>날짜</span>
          <SkeletonPiece width="10rem" height="4rem" />
        </div>

        <div css={S.rowStyle({ theme })}>
          <span>시간</span>
          <SkeletonPiece width="5rem" height="4rem" />
        </div>

        <div css={S.rowStyle({ theme })}>
          <span>장소</span>
          <SkeletonPiece width="20rem" height="4rem" />
        </div>

        <div css={S.rowStyle({ theme })}>
          <span>최대 인원</span>
          <SkeletonPiece width="3.3rem" height="4rem" />
        </div>
        <div css={S.rowStyle({ theme })}>
          <span>현재 참여 인원</span>
          <SkeletonPiece width="3.3rem" height="4rem" />
        </div>
      </div>
    </div>
  );
}
