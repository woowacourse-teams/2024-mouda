import useBetsInterval from '@_hooks/queries/useBetsInterval';
import BetCardList from '../BetCardList/BetCardList';
import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';

export default function BetList() {
  const { bets, isLoading } = useBetsInterval(30_000);

  if (isLoading) {
    return <div>Loading</div>;
  }

  return bets && bets.length > 0 ? (
    <BetCardList bets={bets} />
  ) : (
    <MissingFallback text="아직 만들어진 룰렛이 없어요" />
  );
}
