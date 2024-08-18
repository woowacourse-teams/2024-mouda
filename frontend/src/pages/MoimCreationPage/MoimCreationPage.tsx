import BackArrowButton from '@_components/BackArrowButton/BackArrowButton';
import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import ROUTES from '@_constants/routes';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { useLocation, useNavigate, useNavigationType } from 'react-router-dom';
import TitleStep from './Steps/TitleStep';
import OfflineOrOnlineStep from './Steps/OfflineOrOnlineStep';
import PlaceStep from './Steps/PlaceStep';
import DateAndTimeStep from './Steps/DateAndTimeStep';
import MaxPeopleStep from './Steps/MaxPeopleStep';
import DescriptionStep from './Steps/DescriptionStep';
import useStatePersist from '@_hooks/useStatePersist';

export type MoimCreationStep =
  | '이름입력'
  | '오프라인/온라인선택'
  | '장소선택'
  | '날짜/시간설정'
  | '최대인원설정'
  | '설명입력';

const steps: MoimCreationStep[] = [
  '이름입력',
  '오프라인/온라인선택',
  '장소선택',
  '날짜/시간설정',
  '최대인원설정',
  '설명입력',
];

type MoimCreationInfo = {
  title: string;
  offlineOrOnline: string;
  place: string;
  date: string;
  time: string;
  maxPeople: number;
  description: string;
};

const useMoimCreationInfo = (isInitializing: boolean) => {
  if (isInitializing) {
    sessionStorage.removeItem('moimCreationInfo');
  }

  const [moimInfo, setMoimInfo] = useStatePersist<MoimCreationInfo>({
    key: 'moimCreationInfo',
    initialState: {
      title: '',
      offlineOrOnline: '',
      place: '',
      date: '',
      time: '',
      maxPeople: 0,
      description: '',
    },
    storage: 'sessionStorage',
    clearStorageOnExit: true,
  });

  return { moimInfo, setMoimInfo };
};

export default function MoimCreationPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const navigationType = useNavigationType();

  const currentStep: MoimCreationStep = location.state?.step || steps[0];

  const { moimInfo, setMoimInfo } = useMoimCreationInfo(
    currentStep === '이름입력' && navigationType === 'PUSH',
  );

  const currentComponents: {
    main: JSX.Element;
    footer: JSX.Element;
  } = { main: <></>, footer: <></> };

  if (currentStep === '이름입력') {
    currentComponents.main = (
      <TitleStep
        title={moimInfo.title}
        onTitleChange={(title) => setMoimInfo((prev) => ({ ...prev, title }))}
      />
    );
    currentComponents.footer = (
      <FunnelButton
        disabled={moimInfo.title === ''}
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: { step: '오프라인/온라인선택' },
          });
        }}
      >
        {moimInfo.title === '' ? '모임 이름을 입력해주세요' : '다음으로'}
      </FunnelButton>
    );
  } else if (currentStep === '오프라인/온라인선택') {
    currentComponents.main = (
      <OfflineOrOnlineStep
        offlineOrOnline={moimInfo.offlineOrOnline}
        onOfflineOrOnlineChange={(offlineOrOnline) =>
          setMoimInfo((prev) => ({
            ...prev,
            offlineOrOnline: offlineOrOnline,
          }))
        }
      />
    );
    currentComponents.footer = (
      <FunnelButton
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: {
              step: moimInfo.offlineOrOnline ? '장소선택' : '날짜/시간설정',
            },
          });
        }}
      >
        {moimInfo.offlineOrOnline === ''
          ? '스킵하고 채팅에서 정할게요!'
          : '다음으로'}
      </FunnelButton>
    );
  } else if (currentStep === '장소선택') {
    currentComponents.main = (
      <PlaceStep
        offlineOrOnline={moimInfo.offlineOrOnline}
        place={moimInfo.place}
        onPlaceChange={(place) => setMoimInfo((prev) => ({ ...prev, place }))}
      />
    );
    currentComponents.footer = (
      <FunnelButton
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: { step: '날짜/시간설정' },
          });
        }}
      >
        {moimInfo.place === '' ? '스킵하고 채팅에서 정할게요!' : '다음으로'}
      </FunnelButton>
    );
  } else if (currentStep === '날짜/시간설정') {
    currentComponents.main = (
      <DateAndTimeStep
        date={moimInfo.date}
        time={moimInfo.time}
        onDateChange={(date) => setMoimInfo((prev) => ({ ...prev, date }))}
        onTimeChange={(time) => setMoimInfo((prev) => ({ ...prev, time }))}
      />
    );
    currentComponents.footer = (
      <FunnelButton
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: { step: '최대인원설정' },
          });
        }}
      >
        {moimInfo.date === '' && moimInfo.time === ''
          ? '스킵하고 채팅에서 정할게요!'
          : moimInfo.date === ''
            ? '날짜는 채팅에서 정할게요!'
            : moimInfo.time === ''
              ? '시간은 채팅에서 정할게요!'
              : '다음으로'}
      </FunnelButton>
    );
  } else if (currentStep === '최대인원설정') {
    currentComponents.main = (
      <MaxPeopleStep
        maxPeople={moimInfo.maxPeople}
        onMaxPeopleChange={(maxPeople) =>
          setMoimInfo((prev) => ({ ...prev, maxPeople }))
        }
      />
    );
    currentComponents.footer = (
      <FunnelButton
        disabled={moimInfo.maxPeople === 0}
        onClick={() => {
          navigate(ROUTES.addMoim, {
            state: { step: '설명입력' },
          });
        }}
      >
        다음으로
      </FunnelButton>
    );
  } else if (currentStep === '설명입력') {
    currentComponents.main = (
      <DescriptionStep
        description={moimInfo.description}
        onDescriptionChange={(description) =>
          setMoimInfo((prev) => ({ ...prev, description }))
        }
      />
    );
    currentComponents.footer = (
      <FunnelButton
        onClick={() => {
          console.log(moimInfo);
        }}
      >
        끗!
      </FunnelButton>
    );
  }

  return (
    <FunnelLayout>
      <FunnelLayout.Header>
        <FunnelLayout.Header.Left>
          <BackArrowButton
            onClick={() => {
              navigate(-1);
            }}
          />
        </FunnelLayout.Header.Left>
        <FunnelLayout.Header.Center>모임 만들기</FunnelLayout.Header.Center>
      </FunnelLayout.Header>

      <FunnelStepIndicator totalSteps={steps} currentStep={currentStep} />

      <FunnelLayout.Main>{currentComponents?.main}</FunnelLayout.Main>

      <FunnelLayout.Footer>{currentComponents?.footer}</FunnelLayout.Footer>
    </FunnelLayout>
  );
}
