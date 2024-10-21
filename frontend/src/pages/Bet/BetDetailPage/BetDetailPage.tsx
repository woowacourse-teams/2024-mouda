import { useNavigate, useParams } from 'react-router-dom';

import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import { BetDetail } from '@_types/index';
import Button from '@_components/Button/Button';
import GET_ROUTES from '@_common/getRoutes';
import ProfileCardList from './components/ProfileCardList/ProfileCardList';
import Roulette from '../components/Roulette/Roulette';
import RouletteWrapper from '../components/RouletteWrapper/RouletteWrapper';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import useBet from '@_hooks/queries/useBet';
import useCompleteBet from '@_hooks/mutaions/useCompleteBet';
import useJoinBet from '@_hooks/mutaions/useJoinBet';
import { useMemo } from 'react';

const getButtonMessage = (bet?: BetDetail) => {
  if (!bet) return '잠시만 기다려주세요';
  if (bet.isAnnounced && bet.chatroomId) return '채팅방으로 가기';
  if (bet.myRole === 'MOIMER') return '룰렛 돌리기';

  if (bet.myRole === 'MOIMEE') return '추첨까지 잠시만 기다려 주세요';
  if (bet.myRole === 'NON_MOIMEE') return '참여하기';
  return '잠시만 기다려주세요';
};

const getIsButtonDisabled = (bet?: BetDetail) => {
  if (!bet) return true;
  if (bet.isAnnounced && bet.chatroomId) return false;
  if (bet.myRole === 'MOIMER') return false;

  if (bet.myRole === 'MOIMEE') return true;
  if (bet.myRole === 'NON_MOIMEE') return true;
  return false;
};
export default function BetDetailPage() {
  const navigate = useNavigate();
  const params = useParams();

  const betId = Number(params.betId);

  const { bet } = useBet(betId);
  const { mutateAsync: completeBet } = useCompleteBet();
  const { mutateAsync: joinBet } = useJoinBet();

  const announceDate = useMemo(
    () => (bet?.deadline ? new Date(bet?.deadline) : new Date()),
    [bet?.deadline],
  );
  const nameList = useMemo(
    () => bet?.participants.map(({ nickname }) => nickname),
    [bet?.participants],
  );

  const buttonClickHandler = async () => {
    if (!bet) return;
    if (bet.isAnnounced && bet.chatroomId) {
      return navigate(GET_ROUTES.nowDarakbang.chattingRoom(bet.chatroomId));
    }
    if (bet.myRole === 'MOIMER') {
      completeBet(betId);
      return;
    }
    if (bet.myRole === 'MOIMEE') return;
    if (bet.myRole === 'NON_MOIMEE') {
      await joinBet(betId);
    }
  };

  return (
    <SelectLayout>
      <SelectLayout.Header>
        <SelectLayout.Header.Left>
          <div onClick={() => navigate(GET_ROUTES.nowDarakbang.bet())}>
            <BackArrowButton />
          </div>
        </SelectLayout.Header.Left>
      </SelectLayout.Header>

      <SelectLayout.ContentContainer>
        {
          <RouletteWrapper
            title={bet?.title || ''}
            description={`지금 당첨될 확률은 *${((1 / (bet?.participants.length || 1)) * 100).toFixed(1)}*%!`}
            announceDate={announceDate}
          >
            {nameList && (
              <Roulette
                nameList={nameList}
                startSpeed={1}
                minMs={3000}
                itemPercent={120}
                stopSpeed={1}
              />
            )}
          </RouletteWrapper>
        }

        {bet && <ProfileCardList profiles={bet.participants} />}
        <Button
          shape="bar"
          disabled={getIsButtonDisabled(bet)}
          onClick={buttonClickHandler}
        >
          {getButtonMessage(bet)}
        </Button>
      </SelectLayout.ContentContainer>
    </SelectLayout>
  );
}
