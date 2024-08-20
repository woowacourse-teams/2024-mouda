import BackArrowButton from '@_components/BackArrowButton/BackArrowButton';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import ROUTES from '@_constants/routes';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { useLocation, useNavigate, useNavigationType } from 'react-router-dom';
import TitleStep from './Steps/TitleStep';
import PlaceStep from './Steps/PlaceStep';
import DateAndTimeStep from './Steps/DateAndTimeStep';
import MaxPeopleStep from './Steps/MaxPeopleStep';
import DescriptionStep from './Steps/DescriptionStep';
import useMoimCreationForm from './MoimCreationPage.hook';
import useAddMoim from '@_hooks/mutaions/useAddMoim';
import { isApprochedByUrl } from './MoimCreatePage.util';

export type MoimCreationStep =
  | '이름입력'
  // | '오프라인/온라인선택'
  | '장소선택'
  | '날짜/시간설정'
  | '최대인원설정'
  | '설명입력';

export const steps: MoimCreationStep[] = [
  '이름입력',
  // '오프라인/온라인선택',
  '장소선택',
  '날짜/시간설정',
  '최대인원설정',
  '설명입력',
];

const inputKeyMapper = {
  title: '이름입력',
  // offlineOrOnline: '오프라인/온라인선택',
  place: '장소선택',
  date: '날짜설정',
  time: '시간설정',
  maxPeople: '최대인원설정',
  description: '설명입력',
};

export default function MoimCreationPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const navigationType = useNavigationType();

  const currentStep = location.state?.step || steps[0];

  const isNewMoimCreation =
    currentStep === '이름입력' &&
    (navigationType === 'PUSH' || isApprochedByUrl());

  const {
    formData,
    formValidation,
    updateTitle,
    updatePlace,
    updateDate,
    updateTime,
    updateMaxPeople,
    updateDescription,
  } = useMoimCreationForm(isNewMoimCreation);

  const { mutate: addMoim } = useAddMoim((moimId: number) => {
    navigate(`/moim/${moimId}`);
  });

  const getCurrentComponent = () => {
    if (currentStep === '이름입력') {
      return (
        <TitleStep
          title={formData.title}
          isValid={formValidation.title}
          onTitleChange={updateTitle}
          onButtonClick={() => {
            navigate(ROUTES.addMoim, {
              state: { step: '장소선택' },
            });
          }}
        />
      );
    } else if (currentStep === '장소선택') {
      return (
        <PlaceStep
          place={formData.place}
          isValid={formValidation.place}
          onPlaceChange={updatePlace}
          onButtonClick={() => {
            navigate(ROUTES.addMoim, {
              state: { step: '날짜/시간설정' },
            });
          }}
        />
      );
    } else if (currentStep === '날짜/시간설정') {
      return (
        <DateAndTimeStep
          date={formData.date}
          time={formData.time}
          isValidDate={formValidation.date}
          isValidTime={formValidation.time}
          onDateChange={updateDate}
          onTimeChange={updateTime}
          onButtonClick={() => {
            navigate(ROUTES.addMoim, {
              state: { step: '최대인원설정' },
            });
          }}
        />
      );
    } else if (currentStep === '최대인원설정') {
      return (
        <MaxPeopleStep
          maxPeople={formData.maxPeople}
          isValid={formValidation.maxPeople}
          onMaxPeopleChange={updateMaxPeople}
          onButtonClick={() => {
            navigate(ROUTES.addMoim, {
              state: { step: '설명입력' },
            });
          }}
        />
      );
    } else if (currentStep === '설명입력') {
      return (
        <DescriptionStep
          description={formData.description}
          onDescriptionChange={updateDescription}
          onButtonClick={() => {
            const invalidInputKeys = Object.entries(formValidation)
              .filter(([, value]) => !value)
              .map(([key]) => key);
            if (invalidInputKeys.length > 0) {
              const invalidKeys = invalidInputKeys
                .map(
                  (key) => inputKeyMapper[key as keyof typeof inputKeyMapper],
                )
                .join(', ');
              alert(`${invalidKeys}이 올바르지 않습니다.`);
              return;
            }
            addMoim(formData);
          }}
        />
      );
    }
  };

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

      {getCurrentComponent()}
    </FunnelLayout>
  );
}
