import { ChangeEvent, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import BackLogo from '@_common/assets/back.svg';
import Button from '@_components/Button/Button';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import LabeledInput from '@_components/Input/MoimInput';
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

export default function MoimDetailPage() {
  const navigate = useNavigate();
  const params = useParams();
  const [nickname, setNickname] = useState('');

  const moimId = Number(params.moimId);

  const { moim, isLoading } = useMoim(moimId);
  const { mutate } = useJoinMoim(() => {
    navigate(ROUTES.participationComplete);
  });

  if (isLoading) {
    return <div>Loading...</div>;
  }
  if (!moim) {
    return <div>No data found</div>;
  }
  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(-1)}>
            <BackLogo />
          </div>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Right>
          <ZzimButton isChecked={true} onClick={() => {}} />
          <KebabMenu
            options={[
              { name: '모임 수정하기', onClick: () => {} },
              { name: '모임 삭제하기', onClick: () => {} },
            ]}
          />
        </InformationLayout.Header.Right>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <MoimSummary moimInfo={moim} />
        <ProfileList participants={moim.participants} />
        <MoimInformation moimInfo={moim} />
        {moim.description && (
          <MoimDescription title={'상세설명'}>
            {moim.description}
          </MoimDescription>
        )}
        {moim.comments && (
          <MoimDescription title="코멘트" color="grey">
            <CommentList comments={moim.comments}></CommentList>
          </MoimDescription>
        )}
        <LabeledInput
          title="참가자 이름"
          required
          onChange={(e: ChangeEvent<HTMLInputElement>) =>
            setNickname(e.target.value)
          }
        />
      </InformationLayout.ContentContainer>
      <InformationLayout.BottomButtonWrapper>
        <Button
          shape="bar"
          disabled={nickname === ''}
          onClick={() => mutate({ moimId, nickname })}
        >
          참여하기
        </Button>
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
