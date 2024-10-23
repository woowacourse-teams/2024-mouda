import * as S from '@_pages/Moim/MoimDetailPage/components/Zzim/ZzimButton.style';

import { HTMLAttributes } from 'react';
import HeartIcon from '@_components/Icons/HeartIcon';
import { useTheme } from '@emotion/react';

interface ZzimButtonProps extends HTMLAttributes<HTMLButtonElement> {
  isZzimed: boolean;
  onClick: () => void;
}
export default function ZzimButton(props: ZzimButtonProps) {
  const theme = useTheme();
  const { isZzimed, onClick, ...restProps } = props;
  return (
    <button css={S.Zzimbutton({ theme })} onClick={onClick} {...restProps}>
      <HeartIcon isFilled={isZzimed} />
    </button>
  );
}
