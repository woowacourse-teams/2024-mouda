import * as S from './BetDetailPage.style';

import { useNavigate, useParams } from 'react-router-dom';

import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import Button from '@_components/Button/Button';
import GET_ROUTES from '@_common/getRoutes';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import ProfileList from './components/ProfileList/ProfileList';
import Roulette from './components/Roulette/Roulette';
import Tag from '../components/Tag/Tag';
import useBet from '@_hooks/queries/useBet';
import useCompleteBet from '@_hooks/mutaions/useCompleteBet';
import useJoinBet from '@_hooks/mutaions/useJoinBet';
import { useState } from 'react';
import { useTheme } from '@emotion/react';

export default function BetDetailPage() {
  const navigate = useNavigate();
  const params = useParams();
  const theme = useTheme();

  const betId = Number(params.betId);

  const { bet, isLoading } = useBet(betId);

  const { mutate: completeBet } = useCompleteBet();

  const { mutate: joinBet } = useJoinBet();

  const [isRouletteOpen, setIsRouletteOpen] = useState(false);

  if (isLoading || !bet) {
    return null;
  }

  const bottomButton = (() => {
    if (bet.myRole === 'MOIMER') {
      return (
        <Button
          shape="bar"
          disabled={bet.participants.length < 2}
          onClick={() => {
            if (bet.isAnnounced) {
              setIsRouletteOpen(true);
              if (bet.chatroomId === null) return;
              navigate(GET_ROUTES.nowDarakbang.chattingRoom(bet.chatroomId));
              // setTimeout(() => {
              //   navigate(GET_ROUTES.nowDarakbang.betResult(betId));
              // }, 2500);
            } else {
              completeBet(betId);
            }
          }}
        >
          {bet.isAnnounced ? '결과 보러가기' : '모집 마감하기'}
        </Button>
      );
    }
    if (bet.myRole === 'MOIMEE') {
      return (
        <Button
          shape="bar"
          disabled={!bet.isAnnounced}
          onClick={() => {
            if (bet.isAnnounced) {
              setIsRouletteOpen(true);
              if (bet.chatroomId === null) return;
              navigate(GET_ROUTES.nowDarakbang.chattingRoom(bet.chatroomId));
              // setTimeout(() => {
              //   navigate(GET_ROUTES.nowDarakbang.betResult(betId));
              // }, 2500);
            }
          }}
        >
          {bet.isAnnounced ? '결과 보러가기' : '이미 참여했어요'}
        </Button>
      );
    }
    if (bet.myRole === 'NON_MOIMEE') {
      return (
        <Button
          shape="bar"
          disabled={bet.isAnnounced}
          onClick={() => {
            if (!bet.isAnnounced) {
              joinBet(betId);
            }
          }}
        >
          {bet.isAnnounced ? '마감되었어요' : '참여하기'}
        </Button>
      );
    }
  })();

  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(GET_ROUTES.nowDarakbang.bet())}>
            <BackArrowButton />
          </div>
        </InformationLayout.Header.Left>
      </InformationLayout.Header>

      <InformationLayout.ContentContainer>
        <div css={S.containerStyle}>
          <div css={S.titleBox()}>
            <h1 css={S.title({ theme })}>{bet.title}</h1>
            <Tag isAnnounced={bet.isAnnounced} deadline={bet.deadline}></Tag>
          </div>
        </div>

        <ProfileList participants={bet.participants} />

        {bet.participants.length > 1 && (
          <Roulette isFast={isRouletteOpen} participants={bet.participants} />
        )}
      </InformationLayout.ContentContainer>

      <InformationLayout.BottomButtonWrapper>
        {bottomButton}
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
