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
import { useRef } from 'react';
import { useTheme } from '@emotion/react';

export default function BetDetailPage() {
  const navigate = useNavigate();
  const params = useParams();
  const theme = useTheme();

  const betId = Number(params.betId);

  const { bet, isLoading, isFetching } = useBet(betId);
  const { mutateAsync: completeBet } = useCompleteBet();
  const { mutateAsync: joinBet } = useJoinBet();

  const isButtonActionLoadingRef = useRef(false);

  if (isLoading || !bet) {
    return null;
  }

  const goToCheckResult = () => {
    if (bet.chatroomId === null) {
      return;
    }
    navigate(GET_ROUTES.nowDarakbang.chattingRoom(bet.chatroomId));
  };

  const handleMoimerButtonClick = async () => {
    if (isButtonActionLoadingRef.current) {
      return;
    }
    isButtonActionLoadingRef.current = true;
    if (bet.isAnnounced) {
      goToCheckResult();
    } else {
      await completeBet(betId);
    }
    isButtonActionLoadingRef.current = false;
  };

  const handleMoimeeButtonClick = () => {
    goToCheckResult();
  };

  const handleJoinButtonClick = async () => {
    if (isButtonActionLoadingRef.current) {
      return;
    }
    isButtonActionLoadingRef.current = true;
    if (!bet.isAnnounced) {
      await joinBet(betId);
    }
    isButtonActionLoadingRef.current = false;
  };

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
          <Roulette isFast={false} participants={bet.participants} />
        )}
      </InformationLayout.ContentContainer>

      <InformationLayout.BottomButtonWrapper>
        {bet.myRole === 'MOIMER' ? (
          <Button
            shape="bar"
            disabled={
              isButtonActionLoadingRef.current ||
              isFetching ||
              bet.participants.length < 2
            }
            onClick={handleMoimerButtonClick}
          >
            {bet.isAnnounced ? '결과 보러가기' : '모집 마감하기'}
          </Button>
        ) : bet.myRole === 'MOIMEE' ? (
          <Button
            shape="bar"
            disabled={!bet.isAnnounced}
            onClick={handleMoimeeButtonClick}
          >
            {bet.isAnnounced ? '결과 보러가기' : '이미 참여했어요'}
          </Button>
        ) : (
          <Button
            shape="bar"
            disabled={
              isButtonActionLoadingRef.current || isFetching || bet.isAnnounced
            }
            onClick={handleJoinButtonClick}
          >
            {bet.isAnnounced ? '마감되었어요' : '참여하기'}
          </Button>
        )}
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
