import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';
import useBets from '@_hooks/queries/useBets';
import BetCardList from '../BetCardList/BetCardList';

export default function BetList() {
  const { bets, isLoading } = useBets();

  if (isLoading) {
    return <div>Loading</div>;
  }

  return bets && bets.length > 0 ? (
    <BetCardList bets={bets} />
  ) : (
    <MissingFallback text="아무런 글이 없어요..!" />
  );
}
