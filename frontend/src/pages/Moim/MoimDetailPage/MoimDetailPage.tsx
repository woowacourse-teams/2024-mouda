import { MoimInfo, Role } from '@_types/index';
import { useNavigate, useParams } from 'react-router-dom';

import BackLogo from '@_common/assets/back.svg';
import Button from '@_components/Button/Button';
import CommentList from './components/CommentList/CommentList';
import CommentListSkeleton from './components/CommentList/CommentListSkeleton';
import GET_ROUTES from '@_common/getRoutes';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import KebabMenu from '@_components/KebabMenu/KebabMenu';
import MoimDescription from './components/MoimDescription/MoimDescription';
import MoimDescriptionSkeleton from '@_pages/Moim/MoimDetailPage/components/MoimDescription/MoimDescriptionSkeleton';
import MoimInformation from './components/MoimInformation/MoimInformation';
import MoimSummary from '@_pages/Moim/MoimDetailPage/components/MoimSummary/MoimSummary';
import MoimSummarySkeleton from '@_pages/Moim/MoimDetailPage/components/MoimSummary/MoimSummarySkeleton';
import ProfileList from './components/ProfileList/ProfileList';
import ProfileListSkeleton from '@_pages/Moim/MoimDetailPage/components/ProfileList/ProfileListSkeleton';
import RefreshButton from '@_components/RefreshButton/RefreshButton';
import SkeletonPiece from '@_components/Skeleton/SkeletonPiece';
import ZzimButton from '@_pages/Moim/MoimDetailPage/components/Zzim/ZzimButton';
import useCancelChamyo from '@_hooks/mutaions/useCancelChamyo';
import useCancelMoim from '@_hooks/mutaions/useCancelMoim';
import useChamyoAll from '@_hooks/queries/useChamyoAll';
import useChamyoMine from '@_hooks/queries/useChamyoMine';
import useChangeZzim from '@_hooks/mutaions/useChangeZzim';
import useCompleteMoin from '@_hooks/mutaions/useCompleteMoin';
import useJoinMoim from '@_hooks/mutaions/useJoinMoim';
import { useMemo } from 'react';
import useMoim from '@_hooks/queries/useMoim';
import useOpenChat from '@_hooks/mutaions/useOpenChat';
import useReopenMoim from '@_hooks/mutaions/useReopenMoim';
import { useTheme } from '@emotion/react';
import useZzimMine from '@_hooks/queries/useZzimMine';

const getButtonMessage = (moim: MoimInfo, role: Role) => {
  if (moim.status === 'CANCELED') return '취소된 모임이에요';

  if (role === 'MOIMER') {
    if (moim.status === 'MOIMING') return '모집 완료하기';
    if (moim.status === 'COMPLETED') return '채팅방 열기(이동하기)';
    return '';
  }
  if (role === 'NON_MOIMEE') {
    if (moim.status === 'MOIMING') return '참여하기';
    if (moim.status === 'COMPLETED') return '모집이 완료되었어요';
    return '';
  }
  if (role === 'MOIMEE') {
    if (moim.status === 'MOIMING') return '모임을 모으고 있습니다';
    if (moim.status === 'COMPLETED') {
      if (moim.chatRoomId === null)
        return '방장이 채팅방을 아직 열지 않았습니다';
      return '채팅방으로 가기';
    }
    return '';
  }
  return '';
};

const getButtonDisabled = (moim: MoimInfo, role: Role) => {
  if (moim.status === 'CANCELED') return true;

  if (role === 'MOIMER') {
    if (moim.status === 'MOIMING') return false;
    if (moim.status === 'COMPLETED') return false;
    return true;
  }
  if (role === 'NON_MOIMEE') {
    if (moim.status === 'MOIMING') return false;
    if (moim.status === 'COMPLETED') return true;
    return true;
  }
  if (role === 'MOIMEE') {
    if (moim.status === 'MOIMING') return true;
    if (moim.status === 'COMPLETED') {
      if (moim.chatRoomId === null) return true;
      return false;
    }
    return true;
  }
  return true;
};

export default function MoimDetailPage() {
  const navigate = useNavigate();
  const params = useParams();
  const theme = useTheme();

  const moimId = Number(params.moimId);

  const { moim, isLoading: isMoimLoading } = useMoim(moimId);
  const { role } = useChamyoMine(moimId);
  const { isZzimed, isZzimMineLoading } = useZzimMine(moimId);
  const { participants, chamyoAllIsLoading } = useChamyoAll(moimId);
  const { mutate: changZzim } = useChangeZzim();
  const { mutate: joinMoim } = useJoinMoim(() => {
    navigate(GET_ROUTES.nowDarakbang.moimParticipateComplete());
  });

  const { mutate: cancelMoim, isPending: isPendingCancelMoim } =
    useCancelMoim();

  const { mutate: ReopenMoim, isPending: isPendingReopenMoim } =
    useReopenMoim();
  const { mutate: completeMoim } = useCompleteMoin();
  const { mutate: cancelChamyo, isPending: isPendingCancelChamyo } =
    useCancelChamyo();
  const { mutate: openChat } = useOpenChat((chatRoomId: number) =>
    navigate(GET_ROUTES.nowDarakbang.chattingRoom(chatRoomId)),
  );

  const kebabMenu = useMemo(() => {
    if (role === 'MOIMER') {
      return (
        <KebabMenu
          options={[
            {
              name: '모임 수정하기',
              disabled: false,
              onClick: () =>
                navigate(GET_ROUTES.nowDarakbang.modify(moimId), {
                  state: {
                    ...moim,
                    moimId,
                  },
                }),
            },
            {
              name: '모임 취소하기',
              disabled: isPendingCancelMoim,
              onClick: () => cancelMoim(moimId),
            },
            {
              name: '모임 다시 열기',
              disabled: isPendingReopenMoim,
              onClick: () => ReopenMoim(moimId),
            },
          ]}
        />
      );
    }
    if (role === 'MOIMEE') {
      return (
        <KebabMenu
          options={[
            {
              name: '참여 취소하기',
              disabled: isPendingCancelChamyo,
              onClick: () => cancelChamyo(moimId),
            },
          ]}
        />
      );
    }

    return (
      <KebabMenu
        options={[
          {
            name: '사용할 수 있는 메뉴가 없습니다',
            disabled: isPendingCancelChamyo,
            onClick: () => {},
          },
        ]}
      />
    );
  }, [
    ReopenMoim,
    cancelChamyo,
    cancelMoim,
    isPendingCancelChamyo,
    isPendingCancelMoim,
    isPendingReopenMoim,
    moim,
    moimId,
    navigate,
    role,
  ]);

  const buttonClickHandler = (moim: MoimInfo, role: Role) => {
    if (moim.status === 'CANCELED') return;

    if (role === 'MOIMER') {
      if (moim.status === 'MOIMING') return completeMoim(moimId);
      if (moim.status === 'COMPLETED') {
        if (moim.chatRoomId === null) return openChat(moimId);

        return navigate(GET_ROUTES.nowDarakbang.chattingRoom(moim.chatRoomId));
      }
      return;
    }
    if (role === 'NON_MOIMEE') {
      if (moim.status === 'MOIMING') return joinMoim(moimId);
      if (moim.status === 'COMPLETED') return;
      return;
    }
    if (role === 'MOIMEE') {
      if (moim.status === 'MOIMING') return;
      if (moim.status === 'COMPLETED') {
        if (moim.chatRoomId === null) return;

        return navigate(GET_ROUTES.nowDarakbang.chattingRoom(moim.chatRoomId));
      }
      return;
    }
    return;
  };

  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div
            role="button"
            aria-label="뒤로가기"
            onClick={() => navigate(GET_ROUTES.nowDarakbang.main())}
          >
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Right>
          {isZzimMineLoading && (
            <ZzimButton isZzimed={false} onClick={() => {}} />
          )}
          {!isZzimMineLoading && (
            <ZzimButton
              aria-label={`찜 토글 버튼`}
              aria-pressed={!!isZzimed}
              isZzimed={!!isZzimed}
              onClick={() => changZzim(moimId)}
            />
          )}
          {kebabMenu}
          <RefreshButton />
        </InformationLayout.Header.Right>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        {isMoimLoading && <MoimSummarySkeleton />}
        {moim && <MoimSummary moimInfo={moim} />}

        {chamyoAllIsLoading && <ProfileListSkeleton />}
        {participants && <ProfileList participants={participants} />}

        {isMoimLoading && (
          <div>
            <span css={theme.typography.s1}>모임정보</span>
            <SkeletonPiece width="100%" height="10rem" />
          </div>
        )}
        {moim && <MoimInformation moimInfo={moim} />}
        {isMoimLoading && (
          <div>
            <SkeletonPiece width="100%" height="10rem" />
            <SkeletonPiece width="100%" height="10rem" />
          </div>
        )}
        {isMoimLoading && <SkeletonPiece width="100%" height="30rem" />}
        {moim?.description && (
          <MoimDescription title={'상세설명'}>
            {moim.description}
          </MoimDescription>
        )}

        {isMoimLoading && (
          <MoimDescriptionSkeleton title="코멘트" color="grey">
            <CommentListSkeleton />
          </MoimDescriptionSkeleton>
        )}
        {moim && (
          <MoimDescription title="코멘트" color="grey">
            <CommentList moimId={moimId} comments={moim.comments} />
          </MoimDescription>
        )}
      </InformationLayout.ContentContainer>
      <InformationLayout.BottomButtonWrapper>
        {moim && role && (
          <Button
            shape="bar"
            disabled={getButtonDisabled(moim, role)}
            onClick={() => buttonClickHandler(moim, role)}
            aria-label={getButtonMessage(moim, role)}
          >
            {getButtonMessage(moim, role)}
          </Button>
        )}
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
