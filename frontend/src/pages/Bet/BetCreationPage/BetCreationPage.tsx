import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import TitleStep from './components/Steps/TitleStep';
import WaitingMinutesStep from './components/Steps/WaitingMinutesStep';
import useBetCreationForm from './hooks/useBetCreationForm';
import useFunnel from '@_hooks/useFunnel';
import { useRef } from 'react';

export type BetCreationStep = '제목' | '추첨시간';

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
  } = useBetCreationForm(currentStep);

  const isSubmitted = useRef(false);

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
                if (isSubmitted.current) return;
                const isValid = finalValidate(form);

                if (!isValid) {
                  alert('모든 항목을 입력해주세요.');
                  return;
                }

                isSubmitted.current = true;
                await createBet(form);
              }}
            />
          ),
        }}
      />
    </FunnelLayout>
  );
}
