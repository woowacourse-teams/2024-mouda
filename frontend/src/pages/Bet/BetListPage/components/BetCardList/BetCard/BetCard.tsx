import { BetSummary } from '@_types/index';
import * as S from './BetCard.style';
import { useTheme } from '@emotion/react';
import RouletteIcon from '@_components/Icons/RouletteIcon';
import PeopleIcon from '@_components/Icons/PeopleIcon';

interface BetCardProps {
  bet: BetSummary & { leftMinute: number };
  onClick: (betId: number) => void;
}

export default function BetCard(props: BetCardProps) {
  const { bet, onClick } = props;

  const theme = useTheme();

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
            : `마감 ${bet.leftMinute}분 전`}
        </div>
        <div css={S.participantCount({ theme })}>
          <PeopleIcon /> {bet.currentParticipants}명
        </div>
        <span css={S.banner({ theme })}>지금 들어오면 25%!</span>
      </div>
      <div css={S.rightSection}>
        <RouletteIcon />
      </div>
    </div>
  );
}
