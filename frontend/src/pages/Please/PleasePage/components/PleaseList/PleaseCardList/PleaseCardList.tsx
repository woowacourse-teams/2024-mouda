import { Please } from '@_types/index';
import * as S from './PleaseCardList.style';
import PleaseCard from './PleaseCard/PleaseCard';

interface PleaseCardListProps {
  pleases: Please[];
}

export default function PleaseCardList(props: PleaseCardListProps) {
  const { pleases } = props;

  return (
    <div css={S.cardListSection}>
      {pleases.map((please) => (
        <PleaseCard key={please.pleaseId} please={please} />
      ))}
    </div>
  );
}
