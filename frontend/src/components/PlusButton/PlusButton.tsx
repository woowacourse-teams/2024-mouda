import PlusIcon from '@_components/Icons/PlusIcon';
import * as S from './PlusButton.style';

interface PlusButtonProps {
  onClick: () => void;
}

export default function PlusButton(props: PlusButtonProps) {
  const { onClick } = props;

  return (
    <button onClick={onClick} css={S.plusButton}>
      <PlusIcon />
    </button>
  );
}
