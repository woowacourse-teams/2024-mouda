import useJoinMoim from '@_hooks/mutaions/useJoinMoim';
import useMoim from '@_hooks/queries/useMoim';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import { useParams, useNavigate } from 'react-router-dom';

import BackLogo from '@_common/assets/back.svg';
import MoimSummary from '@_components/MoimSummary/MoimSummary';
import MoimInformation from '@_components/MoimInformation/MoimInformation';
import MoimDescription from '@_components/MoimDescription/MoimDescription';
import Button from '@_components/Button/Button';
import ROUTES from '@_constants/routes';

export default function MoimDetailPage() {
  const navigate = useNavigate();
  const params = useParams();
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
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <MoimSummary moimInfo={moim} />
        <MoimInformation moimInfo={moim} />

        {moim.description && <MoimDescription description={moim.description} />}
      </InformationLayout.ContentContainer>
      <InformationLayout.BottomButtonWrapper>
        <Button shape="bar" disabled={false} onClick={() => mutate(moimId)}>
          참여하기
        </Button>
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
