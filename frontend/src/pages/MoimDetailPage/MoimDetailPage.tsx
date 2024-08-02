import { useNavigate, useParams } from 'react-router-dom';

import BackLogo from '@_common/assets/back.svg';
import Button from '@_components/Button/Button';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import MoimDescription from '@_components/MoimDescription/MoimDescription';
import MoimInformation from '@_components/MoimInformation/MoimInformation';
import MoimSummary from '@_components/MoimSummary/MoimSummary';
import ROUTES from '@_constants/routes';
import useJoinMoim from '@_hooks/mutaions/useJoinMoim';
import useMoim from '@_hooks/queries/useMoim';
import ProfileList from '@_components/ProfileList/ProfileList';
import CommentList from '@_components/CommentList/CommentList';
import KebabMenu from '@_components/KebabMenu/KebabMenu';
import ZzimButton from '@_components/Zzim/ZzimButton';
import useChamyoMine from '@_hooks/queries/useChamyoMine';
import useZzimMine from '@_hooks/queries/useZzimMine';
import useChangeZzim from '@_hooks/mutaions/useChangeZzim';
import useWriteComment from '@_hooks/mutaions/useWriteComment';
import useCancelMoim from '@_hooks/mutaions/useCancelMoim';
import useReopenMoim from '@_hooks/mutaions/useReopenMoim';
import useCompleteMoin from '@_hooks/mutaions/useCompleteMoin';
import useChamyoAll from '@_hooks/queries/useChamyoAll';
import useCancelChamyo from '@_hooks/mutaions/useCancelChamyo';
export default function MoimDetailPage() {
  const navigate = useNavigate();
  const params = useParams();

  const moimId = Number(params.moimId);

  const { moim, isLoading } = useMoim(moimId);
  const { role, isChamyoMineLoading } = useChamyoMine(moimId);
  const { isZzimed, isZzimMineLoading } = useZzimMine(moimId);
  const { participants, chamyoAllIsLoading } = useChamyoAll(moimId);
  const { mutate: changZzim } = useChangeZzim();
  const { mutate } = useJoinMoim(() => {
    navigate(ROUTES.participationComplete);
  });
  const { mutate: writeComment } = useWriteComment();
  const { mutate: cancelMoim } = useCancelMoim();

  const { mutate: ReopenMoim } = useReopenMoim();
  const { mutate: completeMoim } = useCompleteMoin();
  const { mutate: cancelChamyo } = useCancelChamyo();

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
          <div onClick={() => navigate(ROUTES.main)}>
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Right>
          <ZzimButton isChecked={isZzimed} onClick={() => changZzim(moimId)} />
          {role === 'MOIMER' ? (
            <KebabMenu
              options={[
                {
                  name: '모임 수정하기',
                  onClick: () =>
                    navigate(`/modify/${moimId}`, {
                      state: {
                        ...moim,
                      },
                    }),
                },
                { name: '모임 삭제하기', onClick: () => cancelMoim(moimId) },
                { name: '모임 다시 열기', onClick: () => ReopenMoim(moimId) },
              ]}
            />
          ) : (
            <KebabMenu
              options={[
                { name: '참여 취소하기', onClick: () => cancelChamyo(moimId) },
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
            <CommentList
              comments={moim.comments}
              onWriteClick={() => writeComment(moimId)}
            />
          </MoimDescription>
        )}
      </InformationLayout.ContentContainer>
      <InformationLayout.BottomButtonWrapper>
        {role === 'MOIMER' ? (
          moim.status === 'MOIMING' ? (
            <Button
              shape="bar"
              disabled={false}
              onClick={() => completeMoim(moimId)}
            >
              모집 완료하기
            </Button>
          ) : (
            <Button
              shape="bar"
              disabled={false}
              onClick={() => navigate(ROUTES.chat)}
            >
              채팅방 열기(이동하기)
            </Button>
          )
        ) : role === 'NON_MOIMEE' ? (
          moim.status === 'MOIMING' ? (
            <Button shape="bar" disabled={false} onClick={() => mutate(moimId)}>
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
            기다려 임마
          </Button>
        ) : (
          <Button
            shape="bar"
            disabled={false}
            onClick={() => navigate(ROUTES.chat)}
          >
            채팅방으로 가기
          </Button>
        )}
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
