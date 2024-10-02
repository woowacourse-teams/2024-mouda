import { BetSummary } from '@_types/index';
import * as S from './BetCard.style';
import { useTheme } from '@emotion/react';
import Tag from '@_pages/Bet/components/Tag/Tag';

interface BetCardProps {
  bet: BetSummary;
  onClick: (betId: number) => void;
}

export default function BetCard(props: BetCardProps) {
  const { bet, onClick } = props;

  const theme = useTheme();

  return (
    <div css={S.cardBox} onClick={() => onClick(bet.id)}>
      <div css={S.leftSection}>
        <div css={S.title({ theme })}>{bet.title}</div>
        <div css={S.count({ theme })}>현재 {bet.currentParticipants}명</div>
      </div>
      <div>
        <Tag deadline={bet.deadline} />
      </div>
    </div>
  );
}
