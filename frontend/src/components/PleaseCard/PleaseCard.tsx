import { Please } from '@_types/index';

interface PleaseCardProps {
  please: Please;
}

export default function PleaseCard(props: PleaseCardProps) {
  const { please } = props;

  return (
    <div>
      {JSON.stringify(please)}
      <div>asdfasdf</div>
    </div>
  );
}
