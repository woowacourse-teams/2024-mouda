import * as S from './BottomSheetHandle.style';

interface BottomSheetHandleProps {
  onTouchStart: (event: React.TouchEvent) => void;
  onTouchMove: (event: React.TouchEvent) => void;
  onTouchEnd: () => void;
}

export default function BottomSheetHandle(props: BottomSheetHandleProps) {
  const { onTouchStart, onTouchMove, onTouchEnd } = props;

  return (
    <div
      css={S.handleWrapper}
      onTouchStart={onTouchStart}
      onTouchMove={onTouchMove}
      onTouchEnd={onTouchEnd}
    >
      <div css={S.handleBar} />
    </div>
  );
}
