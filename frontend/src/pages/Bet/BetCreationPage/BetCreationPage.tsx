import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import useFunnel from '@_hooks/useFunnel';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import TitleStep from './components/Steps/TitleStep';
import WaitingMinutesStep from './components/Steps/WaitingMinutesStep';
import useBetCreationForm from './hooks/useBetCreationForm';

type BetCreationStep = '제목' | '추첨시간';

const steps: BetCreationStep[] = ['제목', '추첨시간'];

export default function BetCreationPage() {
  const { Funnel, currentStep, goBack, goNextStep } =
    useFunnel<BetCreationStep>('제목');

  const {
    form,
    isValid,
    errorMessage,
    updateTitle,
    updateWaitingMinutes,
    validateTitle,
    validateWaitingMinutes,
    finalValidate,
    createBet,
  } = useBetCreationForm();

  return (
    <FunnelLayout>
      <FunnelLayout.Header>
        <FunnelLayout.Header.Left>
          <BackArrowButton onClick={goBack} />
        </FunnelLayout.Header.Left>
        <FunnelLayout.Header.Center>룰렛 만들기</FunnelLayout.Header.Center>
      </FunnelLayout.Header>

      <FunnelStepIndicator totalSteps={steps} currentStep={currentStep} />

      <Funnel
        step={{
          제목: (
            <TitleStep
              title={form.title}
              isValid={isValid.title}
              errorMessage={errorMessage.title}
              onTitleChange={(title: string) => {
                validateTitle(title);
                updateTitle(title);
              }}
              onButtonClick={() => {
                validateTitle(form.title) && goNextStep('추첨시간');
              }}
            />
          ),
          추첨시간: (
            <WaitingMinutesStep
              waitingMinutes={form.waitingMinutes}
              isValid={isValid.waitingMinutes}
              errorMessage={errorMessage.waitingMinutes}
              onWaitingMinutesChange={(waitingMinutes: number) => {
                validateWaitingMinutes(waitingMinutes);
                updateWaitingMinutes(waitingMinutes);
              }}
              onButtonClick={async () => {
                const isValid = finalValidate(form);

                if (!isValid) {
                  alert('모든 항목을 입력해주세요.');
                  return;
                }

                await createBet(form);
              }}
            />
          ),
        }}
      />
    </FunnelLayout>
  );
}
