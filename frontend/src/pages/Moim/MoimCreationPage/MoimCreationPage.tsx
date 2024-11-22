import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import DateAndTimeStep from './components/Steps/DateAndTimeStep';
import DescriptionStep from './components/Steps/DescriptionStep';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import MaxPeopleStep from './components/Steps/MaxPeopleStep';
import PlaceStep from './components/Steps/PlaceStep';
import TitleStep from './components/Steps/TitleStep';
import useFunnel from '@_hooks/useFunnel';
import useMoimCreationForm from './MoimCreationPage.hook';
import { useRef } from 'react';

export type MoimCreationStep =
  | '이름입력'
  // | '오프라인/온라인선택'
  | '장소선택'
  | '날짜/시간설정'
  | '최대인원설정'
  | '설명입력';

const steps: MoimCreationStep[] = [
  '이름입력',
  // '오프라인/온라인선택',
  '장소선택',
  '날짜/시간설정',
  '최대인원설정',
  '설명입력',
];

export default function MoimCreationPage() {
  const { Funnel, currentStep, goBack, goNextStep } =
    useFunnel<MoimCreationStep>('이름입력');

  const {
    formData,
    formValidation,
    updateTitle,
    updatePlace,
    updateDate,
    updateTime,
    updateMaxPeople,
    updateDescription,
    finalValidate,
    createMoim,
    isPending,
  } = useMoimCreationForm(currentStep);

  const isSubmitted = useRef(false);

  return (
    <FunnelLayout>
      <FunnelLayout.Header>
        <FunnelLayout.Header.Left>
          <BackArrowButton onClick={goBack} />
        </FunnelLayout.Header.Left>
        <FunnelLayout.Header.Center>모임 만들기</FunnelLayout.Header.Center>
      </FunnelLayout.Header>

      <FunnelStepIndicator totalSteps={steps} currentStep={currentStep} />

      <Funnel
        step={{
          이름입력: (
            <TitleStep
              title={formData.title}
              isValid={formValidation.title}
              onTitleChange={updateTitle}
              onButtonClick={() => goNextStep('장소선택')}
            />
          ),
          장소선택: (
            <PlaceStep
              place={formData.place}
              isValid={formValidation.place}
              onPlaceChange={updatePlace}
              onButtonClick={() => goNextStep('날짜/시간설정')}
            />
          ),
          '날짜/시간설정': (
            <DateAndTimeStep
              date={formData.date}
              time={formData.time}
              isValidDate={formValidation.date}
              isValidTime={formValidation.time}
              onDateChange={updateDate}
              onTimeChange={updateTime}
              onButtonClick={() => goNextStep('최대인원설정')}
            />
          ),
          최대인원설정: (
            <MaxPeopleStep
              maxPeople={formData.maxPeople}
              isValid={formValidation.maxPeople}
              onMaxPeopleChange={updateMaxPeople}
              onButtonClick={() => goNextStep('설명입력')}
            />
          ),
          설명입력: (
            <DescriptionStep
              description={formData.description}
              onDescriptionChange={updateDescription}
              isValid={!isPending}
              onButtonClick={() => {
                if (isSubmitted.current) return;
                const { isValid, errorMessage } = finalValidate();
                if (!isValid) {
                  alert(errorMessage);
                  return;
                }
                isSubmitted.current = true;
                createMoim(formData);
              }}
            />
          ),
        }}
      />
    </FunnelLayout>
  );
}
