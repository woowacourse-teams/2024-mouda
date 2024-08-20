import BackArrowIcon from '@_components/Icons/BackArrowIcon';
import * as S from './BackArrowButton.style';

interface BackArrowButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {}

export default function BackArrowButton(props: BackArrowButtonProps) {
  const { ...rest } = props;

  return (
    <button {...rest} css={S.button}>
      <BackArrowIcon />
    </button>
  );
}
