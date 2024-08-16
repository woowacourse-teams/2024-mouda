import BackArrowButton from '@_components/BackArrowButton/BackArrowButton';
import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import ROUTES from '@_constants/routes';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import TitleStep from './Steps/TitleStep';
import OnlineOrOfflineStep from './Steps/OnlineOrOfflineStep';

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

  const [moimInfo, setMoimInfo] = useState({
    title: '',
    date: '',
    time: '',
    maxPeople: 0,
    onlineOrOffline: '',
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

      <FunnelStepIndicator totalSteps={5} currentStep={currentStep} />

      <FunnelLayout.Main>
        {currentStep === 1 ? (
          <TitleStep
            title={moimInfo.title}
            onChange={(title) => setMoimInfo((prev) => ({ ...prev, title }))}
          />
        ) : currentStep === 2 ? (
          <OnlineOrOfflineStep
            onlineOrOffline={moimInfo.onlineOrOffline}
            onChange={(onlineOrOffline) =>
              setMoimInfo((prev) => ({ ...prev, onlineOrOffline }))
            }
          />
        ) : null}
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelButton
          disabled={currentStep === 1 && moimInfo.title === ''}
          onClick={() => {
            navigate(ROUTES.addMoim, { state: { step: currentStep + 1 } });
          }}
        >
          {currentStep === 1 && moimInfo.title === ''
            ? '모임 이름을 입력해주세요'
            : currentStep === 2 && moimInfo.onlineOrOffline === ''
              ? '스킵하고 채팅에서 정할게요!'
              : '다음으로'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </FunnelLayout>
  );
}
