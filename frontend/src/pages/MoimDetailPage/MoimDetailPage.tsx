import { useNavigate, useParams } from 'react-router-dom';

import BackLogo from '@_common/assets/back.svg';
import Button from '@_components/Button/Button';
import CommentList from '@_components/CommentList/CommentList';
import GET_ROUTES from '@_common/getRoutes';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import KebabMenu from '@_components/KebabMenu/KebabMenu';
import MoimDescription from '@_components/MoimDescription/MoimDescription';
import MoimInformation from '@_components/MoimInformation/MoimInformation';
import MoimSummary from '@_components/MoimSummary/MoimSummary';
import ProfileList from '@_components/ProfileList/ProfileList';
import ZzimButton from '@_components/Zzim/ZzimButton';
import useCancelChamyo from '@_hooks/mutaions/useCancelChamyo';
import useCancelMoim from '@_hooks/mutaions/useCancelMoim';
import useChamyoAll from '@_hooks/queries/useChamyoAll';
import useChamyoMine from '@_hooks/queries/useChamyoMine';
import useChangeZzim from '@_hooks/mutaions/useChangeZzim';
import useCompleteMoin from '@_hooks/mutaions/useCompleteMoin';
import useJoinMoim from '@_hooks/mutaions/useJoinMoim';
import useMoim from '@_hooks/queries/useMoim';
import useOpenChat from '@_hooks/mutaions/useOpenChat';
import useReopenMoim from '@_hooks/mutaions/useReopenMoim';
import useZzimMine from '@_hooks/queries/useZzimMine';

export default function MoimDetailPage() {
  const navigate = useNavigate();
  const params = useParams();

  const moimId = Number(params.moimId);

  const { moim, isLoading } = useMoim(moimId);
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
  const { mutate: openChat, isPending: isPendingOpenChat } = useOpenChat(() =>
    navigate(GET_ROUTES.nowDarakbang.chattingRoom(moimId)),
  );

  if (
    isLoading ||
    isChamyoMineLoading ||
    isZzimMineLoading ||
    chamyoAllIsLoading
  ) {
    return <div>Loading...</div>;
  }
  if (!moim || isZzimed === undefined || !participants) {
    return <div>No data found</div>;
  }
  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(GET_ROUTES.nowDarakbang.main())}>
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Right>
          <ZzimButton isZzimed={isZzimed} onClick={() => changZzim(moimId)} />
          {role === 'MOIMER' ? (
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
          )}
        </InformationLayout.Header.Right>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <MoimSummary moimInfo={moim} />
        <ProfileList participants={participants} />
        <MoimInformation moimInfo={moim} />
        {moim.description && (
          <MoimDescription title={'상세설명'}>
            {moim.description}
          </MoimDescription>
        )}
        {moim.comments && (
          <MoimDescription title="코멘트" color="grey">
            <CommentList moimId={moimId} comments={moim.comments} />
          </MoimDescription>
        )}
      </InformationLayout.ContentContainer>
      <InformationLayout.BottomButtonWrapper>
        {role === 'MOIMER' ? (
          moim.status === 'MOIMING' ? (
            <Button
              shape="bar"
              disabled={false || isPendingCompleteMoim}
              onClick={() => completeMoim(moimId)}
            >
              모집 완료하기
            </Button>
          ) : moim.status === 'CANCELED' ? (
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
          moim.status === 'MOIMING' ? (
            <Button
              shape="bar"
              disabled={false || isPendingJoinMoim}
              onClick={() => mutate(moimId)}
            >
              참여하기
            </Button>
          ) : moim.status === 'COMPLETED' ? (
            <Button shape="bar" disabled={true}>
              모집이 완료되었어요
            </Button>
          ) : (
            <Button shape="bar" disabled={true}>
              취소된 모임이예요
            </Button>
          )
        ) : moim.status === 'MOIMING' ? (
          <Button shape="bar" disabled={true}>
            방장이 채팅방을 만들지 않았습니다
          </Button>
        ) : (
          <Button
            shape="bar"
            disabled={false}
            onClick={() =>
              navigate(GET_ROUTES.nowDarakbang.chattingRoom(moimId))
            }
          >
            채팅방으로 가기
          </Button>
        )}
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
