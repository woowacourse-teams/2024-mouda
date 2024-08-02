import { useTheme } from '@emotion/react';
import * as S from '@_components/Zzim/ZzimButton.style';
import FillZzim from '@_common/assets/zzim_fill_button.svg';
import EmptyZzim from '@_common/assets/zzim_emty_button.svg';

interface ZzimButtonProps {
  isChecked: boolean;
  onClick: () => void;
}
export default function ZzimButton(props: ZzimButtonProps) {
  const theme = useTheme();
  const { isChecked, onClick } = props;
  return (
    <button css={S.Zzimbutton({ theme })} onClick={onClick}>
      {isChecked ? <FillZzim /> : <EmptyZzim />}
    </button>
  );
}
