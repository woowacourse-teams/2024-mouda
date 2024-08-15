import BackArrowButton from '@_components/BackArrowButton/BackArrowButton';
import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import ROUTES from '@_constants/routes';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { MoimInputInfo } from '@_types/index';
import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import TitleStep from './Steps/TitleStep';
import PlaceStep from './Steps/PlaceStep';

// const steps = [
//   '이름입력',
//   '장소선택',
//   '날짜와시간선택',
//   '최대인원설정',
//   '설명입력',
// ];

export default function MoimCreationPage() {
  const navigate = useNavigate();
  const location = useLocation();

  const [moimInfo, setMoimInfo] = useState<MoimInputInfo>({
    title: '',
    date: '',
    time: '',
    maxPeople: 0,
    place: '',
    description: '',
  });

  const currentStep = location.state?.step || 1;

  return (
    <FunnelLayout>
      <FunnelLayout.Header>
        <FunnelLayout.Header.Left>
          <BackArrowButton onClick={() => navigate(-1)} />
        </FunnelLayout.Header.Left>
        <FunnelLayout.Header.Center>모임 만들기</FunnelLayout.Header.Center>
      </FunnelLayout.Header>

      <FunnelLayout.Main>
        {currentStep === 1 ? (
          <TitleStep
            title={moimInfo.title}
            onChange={(e) =>
              setMoimInfo({ ...moimInfo, title: e.target.value })
            }
          />
        ) : currentStep === 2 ? (
          <PlaceStep />
        ) : null}
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelStepIndicator totalSteps={5} currentStep={currentStep} />
        <FunnelButton
          onClick={() => {
            navigate(ROUTES.addMoim, { state: { step: currentStep + 1 } });
          }}
        >
          다음으로
        </FunnelButton>
      </FunnelLayout.Footer>
    </FunnelLayout>
  );
}
