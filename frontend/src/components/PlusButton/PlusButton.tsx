import PlusIcon from '@_components/Icons/PlusIcon';
import * as S from './PlusButton.style';

interface PlusButtonProps {
  onClick: () => void;
}

export default function PlusButton(props: PlusButtonProps) {
  const { onClick } = props;

  return (
    <button onClick={onClick} css={S.plusButton} aria-label="모임 생성">
      <PlusIcon />
    </button>
  );
}
