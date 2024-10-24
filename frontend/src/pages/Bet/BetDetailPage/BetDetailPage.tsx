import * as S from './BetDetailPage.style';

import { useEffect, useMemo, useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import { BetDetail } from '@_types/index';
import Button from '@_components/Button/Button';
import GET_ROUTES from '@_common/getRoutes';
import ProfileCardList from './components/ProfileCardList/ProfileCardList';
import Roulette from '../components/Roulette/Roulette';
import RouletteWrapper from '../components/RouletteWrapper/RouletteWrapper';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import { css } from '@emotion/react';
import useBetRefetch from '@_hooks/queries/useBetRefetch';
import useCompleteBet from '@_hooks/mutaions/useCompleteBet';
import useJoinBet from '@_hooks/mutaions/useJoinBet';

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
  if (bet.isAnnounced && bet.chatroomId) return true;
  if (bet.myRole === 'MOIMER') return false;

  if (bet.myRole === 'MOIMEE') return true;
  if (bet.myRole === 'NON_MOIMEE') return false;
  return true;
};

const bitbit = 'bitbit';

const getPercentString = (bet: BetDetail) => {
  const prefix =
    bet.myRole === 'NON_MOIMEE' ? '지금 오면 당첨 확률' : '지금 당첨 확률은';
  const motherNumber =
    bet.participants.length + (bet.myRole === 'NON_MOIMEE' ? 1 : 0);
  const percentNumber = (1 / motherNumber) * 100;
  const percentString =
    percentNumber % 1 === 0
      ? percentNumber.toString()
      : percentNumber.toFixed(1);
  return `${prefix} *${percentString}*%`;
};

export default function BetDetailPage() {
  const navigate = useNavigate();
  const params = useParams();

  const betId = Number(params.betId);

  const { bet } = useBetRefetch(betId);
  const { mutateAsync: completeBet } = useCompleteBet();
  const { mutateAsync: joinBet } = useJoinBet();
  const [mainDescription, setMainDescription] = useState(' ');
  const leftSecond = useRef<number>(Infinity);

  const isJoined = useRef(false);
  const isCompleted = useRef(false);

  if (bet?.isAnnounced) {
    navigate(GET_ROUTES.nowDarakbang.betResult(betId), { replace: true });
  }

  useEffect(() => {
    if (!bet?.deadline) return;
    const deadlineDate = new Date(bet.deadline);
    //@ts-expect-error Date 객체 뺄셈
    leftSecond.current = Math.floor((deadlineDate - new Date()) / 1000);
    const intervalId = setInterval(() => {
      leftSecond.current--;
      if (leftSecond.current < 0) {
        setMainDescription('GO GO!!');
        return;
      }

      setMainDescription(
        `${Math.floor(leftSecond.current / 60)
          .toString()
          .padStart(2, '00')}:${(leftSecond.current % 60)
          .toString()
          .padStart(2, '00')}`,
      );
    }, 1000);

    return () => clearInterval(intervalId);
  }, [bet?.deadline]);

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
      if (isCompleted.current) return;
      isCompleted.current = true;
      completeBet(betId);
      return;
    }
    if (bet.myRole === 'MOIMEE') return;
    if (bet.myRole === 'NON_MOIMEE') {
      if (isJoined.current) return;
      isJoined.current = true;
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
        <section css={S.containerStyle}>
          {
            <RouletteWrapper
              title={bet?.title || ''}
              description={bet ? getPercentString(bet) : '지금 오면 %'}
              mainDescription={mainDescription}
            >
              {nameList && (
                <Roulette
                  nameList={nameList}
                  startSpeed={5}
                  minMs={3000}
                  itemPercent={120}
                />
              )}
            </RouletteWrapper>
          }

          {bet && <ProfileCardList profiles={bet.participants} />}
          <Button
            shape="bar"
            disabled={getIsButtonDisabled(bet)}
            onClick={buttonClickHandler}
            font={css`
              font: 400 normal 2rem ${bitbit};
            `}
          >
            {getButtonMessage(bet)}
          </Button>
        </section>
      </SelectLayout.ContentContainer>
    </SelectLayout>
  );
}
