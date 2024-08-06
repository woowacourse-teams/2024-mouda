import PleaseCard from '@_components/PleaseCard/PleaseCard';
import { Please } from '@_types/index';

interface PleaseCardListProps {
  pleases: Please[];
}

export default function PleaseCardList(props: PleaseCardListProps) {
  const { pleases } = props;

  return (
    <div>
      {pleases.map((please) => (
        <PleaseCard key={please.pleaseId} please={please} />
      ))}
    </div>
  );
}
