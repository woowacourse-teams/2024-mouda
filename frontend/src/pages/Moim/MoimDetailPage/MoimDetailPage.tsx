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

export default function MoimDetailPage() {
  const navigate = useNavigate();
  const params = useParams();
  const theme = useTheme();

  const moimId = Number(params.moimId);

  const { moim, isLoading: isMoimLoading } = useMoim(moimId);
  const { role, isChamyoMineLoading } = useChamyoMine(moimId);
  const { isZzimed, isZzimMineLoading } = useZzimMine(moimId);
  const { participants, chamyoAllIsLoading } = useChamyoAll(moimId);
  const { mutate: changZzim } = useChangeZzim();
  const { mutate, isPending: isPendingJoinMoim } = useJoinMoim(() => {
    navigate(GET_ROUTES.nowDarakbang.moimParticipateComplete());
  });

  const { mutate: cancelMoim, isPending: isPendingCancelMoim } =
    useCancelMoim();

  const { mutate: ReopenMoim, isPending: isPendingReopenMoim } =
    useReopenMoim();
  const { mutate: completeMoim, isPending: isPendingCompleteMoim } =
    useCompleteMoin();
  const { mutate: cancelChamyo, isPending: isPendingCancelChamyo } =
    useCancelChamyo();
  const { mutate: openChat, isPending: isPendingOpenChat } = useOpenChat(
    (chatRoomId: number) =>
      navigate(GET_ROUTES.nowDarakbang.chattingRoom(chatRoomId)),
  );

  const kebabMenu = useMemo(() => {
    return role === 'MOIMER' ? (
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
    ) : (
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

  const button = useMemo(() => {
    return isChamyoMineLoading ? (
      ''
    ) : role === 'MOIMER' ? (
      moim?.status === 'MOIMING' ? (
        <Button
          shape="bar"
          disabled={false || isPendingCompleteMoim}
          onClick={() => completeMoim(moimId)}
        >
          모집 완료하기
        </Button>
      ) : moim?.status === 'CANCELED' ? (
        <Button shape="bar" disabled={true}>
          취소된 모임이예요
        </Button>
      ) : (
        <Button
          shape="bar"
          disabled={false || isPendingOpenChat}
          onClick={() => openChat(moimId)}
        >
          채팅방 열기(이동하기)
        </Button>
      )
    ) : role === 'NON_MOIMEE' ? (
      moim?.status === 'MOIMING' ? (
        <Button
          shape="bar"
          disabled={false || isPendingJoinMoim}
          onClick={() => mutate(moimId)}
        >
          참여하기
        </Button>
      ) : moim?.status === 'COMPLETED' ? (
        <Button shape="bar" disabled={true}>
          모집이 완료되었어요
        </Button>
      ) : (
        <Button shape="bar" disabled={true}>
          취소된 모임이예요
        </Button>
      )
    ) : moim?.status === 'MOIMING' ? (
      <Button shape="bar" disabled={true}>
        방장이 채팅방을 만들지 않았습니다
      </Button>
    ) : (
      <Button
        shape="bar"
        disabled={false}
        onClick={() => navigate(GET_ROUTES.nowDarakbang.chattingRoom(moimId))}
      >
        채팅방으로 가기
      </Button>
    );
  }, [
    completeMoim,
    isPendingCompleteMoim,
    isPendingJoinMoim,
    isPendingOpenChat,
    moim?.status,
    moimId,
    navigate,
    mutate,
    openChat,
    role,
    isChamyoMineLoading,
  ]);
  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(GET_ROUTES.nowDarakbang.main())}>
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Right>
          {isZzimMineLoading && (
            <ZzimButton isZzimed={false} onClick={() => {}} />
          )}
          {!isZzimMineLoading && (
            <ZzimButton
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
        {button}
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
