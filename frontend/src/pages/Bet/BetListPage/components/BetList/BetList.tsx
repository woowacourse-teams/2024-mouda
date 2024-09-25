import MissingFallback from '@_components/MissingFallback/MissingFallback';
import BetCard from './BetCard/BetCard';

const useBets = () => {
  return [];
};

export default function BetList() {
  const bets = useBets();

  return bets && bets.length > 0 ? (
    bets.map((bet) => <BetCard key={bet} />)
  ) : (
    <MissingFallback text="아무런 글이 없어요..!" />
  );
}
