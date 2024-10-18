import { BetSummary } from '@_types/index';
import BetCard from './BetCard/BetCard';
import * as S from './BetCardList.style';
import { useNavigate } from 'react-router-dom';
import GET_ROUTES from '@_common/getRoutes';

interface BetCardListProps {
  bets: Array<BetSummary & { leftMinute: number }>;
}

export default function BetCardList(props: BetCardListProps) {
  const { bets } = props;

  const navigate = useNavigate();

  const onCardClick = (betId: number) => {
    navigate(GET_ROUTES.nowDarakbang.betDetail(betId));
  };

  return (
    <div css={S.container}>
      {bets.map((bet) => (
        <BetCard key={bet.id} bet={bet} onClick={onCardClick} />
      ))}
    </div>
  );
}
