
import BackArrowButton from '@_components/BackArrowButton/BackArrowButton';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import TitleStep from './Steps/TitleStep';
import PlaceStep from './Steps/PlaceStep';
import DateAndTimeStep from './Steps/DateAndTimeStep';
import MaxPeopleStep from './Steps/MaxPeopleStep';
import DescriptionStep from './Steps/DescriptionStep';
import useMoimCreationForm from './MoimCreationPage.hook';
import useFunnel from '@_hooks/useFunnel';

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
  } = useMoimCreationForm(currentStep);

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
              onButtonClick={() => {
                const { isValid, errorMessage } = finalValidate();
                if (!isValid) {
                  alert(errorMessage);
                  return;
                }
                createMoim(formData);
              }}
            />
          ),
        }}
      />
    </FunnelLayout>
  );
}
