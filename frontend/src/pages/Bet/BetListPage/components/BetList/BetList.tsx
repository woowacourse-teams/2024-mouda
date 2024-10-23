import MissingFallback from '@_components/MissingFallback/MissingFallback';
import useBetsInterval from '@_hooks/queries/useBetsInterval';
import BetCardList from '../BetCardList/BetCardList';

export default function BetList() {
  const { bets, isLoading } = useBetsInterval(30_000);

  if (isLoading) {
    return <div>Loading</div>;
  }

  return bets && bets.length > 0 ? (
    <BetCardList bets={bets} />
  ) : (
    <MissingFallback text="아무런 글이 없어요..!" />
  );
}
