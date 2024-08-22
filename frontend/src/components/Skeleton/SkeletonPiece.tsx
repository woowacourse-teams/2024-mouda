import { useTheme } from '@emotion/react';
import * as S from './SkeletonPiece.style';

interface SkeletonPieceProps {
  width: string;
  height: string;
}

export default function SkeletonPiece(props: SkeletonPieceProps) {
  const { width, height } = props;

  const theme = useTheme();

  return <div css={S.skeletonPiece({ theme, width, height })}></div>;
}
