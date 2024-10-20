import * as S from './BackArrowButton.style';

import BackArrowIcon from '@_components/Icons/BackArrowIcon';

interface BackArrowButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {}

export default function BackArrowButton(props: BackArrowButtonProps) {
  const { ...rest } = props;

  return (
    <button {...rest} css={S.button} aria-label="뒤로가기">
      <BackArrowIcon />
    </button>
  );
}
