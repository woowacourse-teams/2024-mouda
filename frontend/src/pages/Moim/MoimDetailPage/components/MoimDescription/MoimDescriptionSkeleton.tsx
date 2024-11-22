import * as S from './MoimDescription.style';

import { PropsWithChildren } from 'react';
import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import { useTheme } from '@emotion/react';

interface MoimDescriptionProps extends PropsWithChildren {
  title?: string;
  color?: string;
}

export default function MoimDescriptionSkeleton(props: MoimDescriptionProps) {
  const theme = useTheme();
  const { title, color = '', children } = props;

  if (title === '') {
    return;
  }

  return (
    <div css={S.containerStyle({ theme, color })}>
      {title && <h2 css={S.titleStyle({ theme })}>{title}</h2>}
      {!title && <SkeletonPiece height="2.4rem" width="10rem" />}
      {!children && (
        <div css={S.descriptionStyle({ theme })}>
          <SkeletonPiece height="2.4rem" width="100%" />
          <SkeletonPiece height="2.4rem" width="100%" />
          <SkeletonPiece height="2.4rem" width="100%" />
        </div>
      )}
      {children && children}
    </div>
  );
}
