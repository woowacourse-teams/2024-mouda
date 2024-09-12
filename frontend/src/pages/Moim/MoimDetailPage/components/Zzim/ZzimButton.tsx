import { useTheme } from '@emotion/react';
import * as S from '@_pages/Moim/MoimDetailPage/components/Zzim/ZzimButton.style';
import HeartIcon from '@_components/Icons/HeartIcon';

interface ZzimButtonProps {
  isZzimed: boolean;
  onClick: () => void;
}
export default function ZzimButton(props: ZzimButtonProps) {
  const theme = useTheme();
  const { isZzimed, onClick } = props;
  return (
    <button css={S.Zzimbutton({ theme })} onClick={onClick}>
      <HeartIcon isFilled={isZzimed} />
    </button>
  );
}
