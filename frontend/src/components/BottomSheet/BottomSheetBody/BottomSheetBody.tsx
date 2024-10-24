import { useTheme } from '@emotion/react';
import { PropsWithChildren } from 'react';
import * as S from './BottomSheetBody.style';

interface BottomSheetBodyProps {
  currentY: number;
  size?: 'small' | 'medium' | 'large' | 'full';
  isDragging: boolean;
}

export default function BottomSheetBody(
  props: PropsWithChildren<BottomSheetBodyProps>,
) {
  const { currentY, size, isDragging, children } = props;

  const theme = useTheme();

  return (
    <div css={S.body({ theme, currentY, size, isDragging })}>{children}</div>
  );
}
