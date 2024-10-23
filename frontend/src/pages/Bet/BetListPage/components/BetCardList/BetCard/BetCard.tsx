import { BetSummary } from '@_types/index';
import * as S from './BetCard.style';
import { useTheme } from '@emotion/react';
import RouletteIcon from '@_components/Icons/RouletteIcon';
import PeopleIcon from '@_components/Icons/PeopleIcon';
import { useMemo } from 'react';

interface BetCardProps {
  bet: BetSummary & { leftMinute: number };
  onClick: (betId: number) => void;
}

export default function BetCard(props: BetCardProps) {
  const { bet, onClick } = props;

  const theme = useTheme();

  const probabilityPercentage = useMemo(() => {
    const percentage = (1 / (bet.currentParticipants + 1)) * 100;
    return percentage % 1 === 0 ? percentage : percentage.toFixed(1);
  }, [bet.currentParticipants]);

  return (
    <div css={S.cardBox} onClick={() => onClick(bet.id)}>
      <div css={S.leftSection}>
        <div css={S.title({ theme })}>{bet.title}</div>
        <div
          css={S.deadline({
            theme,
            isAnnounced: bet.isAnnounced,
            leftMinute: bet.leftMinute,
          })}
        >
          {bet.isAnnounced || bet.leftMinute < 0
            ? '마감됨'
            : bet.leftMinute === 0
              ? '마감 임박'
              : `마감 ${bet.leftMinute}분 전`}
        </div>
        <div css={S.participantCount({ theme })}>
          <PeopleIcon /> {bet.currentParticipants}명
        </div>
        <div css={S.banner({ theme })}>
          지금 당첨 확률 {probabilityPercentage}%
        </div>
      </div>
      <div css={S.rightSection}>
        <RouletteIcon />
      </div>
    </div>
  );
}
