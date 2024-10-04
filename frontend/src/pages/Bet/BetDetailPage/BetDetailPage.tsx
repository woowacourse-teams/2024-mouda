import GET_ROUTES from '@_common/getRoutes';
import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import { useNavigate, useParams } from 'react-router-dom';

import { useTheme } from '@emotion/react';
import useBet from '@_hooks/queries/useBet';
import * as S from './BetDetailPage.style';
import Tag from '../components/Tag/Tag';
import ProfileList from './components/ProfileList/ProfileList';
import Button from '@_components/Button/Button';
import useCompleteBet from '@_hooks/mutaions/useCompleteBet';
import useJoinBet from '@_hooks/mutaions/useJoinBet';
import Roulette from './components/Roulette/Roulette';

export default function BetDetailPage() {
  const navigate = useNavigate();
  const params = useParams();
  const theme = useTheme();

  const betId = Number(params.betId);

  const { bet, isLoading } = useBet(betId);

  const { mutate: completeBet } = useCompleteBet();

  const { mutate: joinBet } = useJoinBet();

  if (isLoading || !bet) {
    return null;
  }

  const bottomButton = (() => {
    if (bet.myRole === 'MOIMER') {
      return (
        <Button
          shape="bar"
          onClick={() => {
            if (bet.isAnnounced) {
              navigate(GET_ROUTES.nowDarakbang.betResult(betId));
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
              navigate(GET_ROUTES.nowDarakbang.betResult(betId));
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

        <Roulette
          participants={[
            ...bet.participants,
            {
              id: 0,
              nickname: '테니',
              profileUrl: '',
            },
            {
              id: 1,
              nickname: '테바',
              profileUrl: '',
            },
            {
              id: 123,
              nickname: '소파',
              profileUrl: '',
            },
            {
              id: 1234,
              nickname: '테니',
              profileUrl: '',
            },
          ]}
        />
      </InformationLayout.ContentContainer>

      <InformationLayout.BottomButtonWrapper>
        {bottomButton}
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
