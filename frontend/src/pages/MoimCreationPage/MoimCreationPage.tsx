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
                const invalidInputKeys = Object.entries(formValidation)
                  .filter(([, value]) => !value)
                  .map(([key]) => key);
                if (invalidInputKeys.length > 0) {
                  const invalidKeys = invalidInputKeys
                    .map(
                      (key) =>
                        inputKeyMapper[key as keyof typeof inputKeyMapper],
                    )
                    .join(', ');
                  alert(`${invalidKeys}이 올바르지 않습니다.`);
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
