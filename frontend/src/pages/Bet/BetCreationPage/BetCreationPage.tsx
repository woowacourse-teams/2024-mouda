import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import BackArrowButton from '@_components/Icons/BackArrowButton/BackArrowButton';
import useFunnel from '@_hooks/useFunnel';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import TitleStep from './components/Steps/TitleStep';
import { useState } from 'react';
import { BetInputInfo } from '@_types/index';
import MaxPeopleStep from './components/Steps/MaxPeopleStep';

type BetCreationStep = '제목' | '몇명' | '언제';

const steps: BetCreationStep[] = ['제목', '몇명', '언제'];

export default function BetCreationPage() {
  const { Funnel, currentStep, goBack, goNextStep } =
    useFunnel<BetCreationStep>('제목');

  const [state, setState] = useState<BetInputInfo>({
    title: '',
    maxPeople: 2,
    when: '',
  });

  return (
    <FunnelLayout>
      <FunnelLayout.Header>
        <FunnelLayout.Header.Left>
          <BackArrowButton onClick={goBack} />
        </FunnelLayout.Header.Left>
        <FunnelLayout.Header.Center>내기 만들기</FunnelLayout.Header.Center>
      </FunnelLayout.Header>

      <FunnelStepIndicator totalSteps={steps} currentStep={currentStep} />

      <Funnel
        step={{
          제목: (
            <TitleStep
              title={state.title}
              isValid={true}
              onTitleChange={(title) => setState({ ...state, title })}
              onButtonClick={() => goNextStep('몇명')}
            />
          ),
          몇명: (
            <MaxPeopleStep
              maxPeople={state.maxPeople}
              isValid={true}
              onMaxPeopleChange={(maxPeople) =>
                setState({ ...state, maxPeople })
              }
              onButtonClick={() => goNextStep('언제')}
            />
          ),
          언제: <div>언제</div>,
        }}
      />
    </FunnelLayout>
  );
}
