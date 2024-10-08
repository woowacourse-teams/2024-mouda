import FunnelStepIndicator from '@_components/Funnel/FunnelStepIndicator/FunnelStepIndicator';
import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import useFunnel from '@_hooks/useFunnel';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import TitleStep from './components/Steps/TitleStep';
import { useState } from 'react';
import { BetInputInfo } from '@_types/index';
import WaitingMinutesStep from './components/Steps/WaitingMinutesStep';
import useAddBet from '@_hooks/mutaions/useAddBet';
import GET_ROUTES from '@_common/getRoutes';
import { useNavigate } from 'react-router-dom';

type BetCreationStep = '제목' | '추첨시간';

const steps: BetCreationStep[] = ['제목', '추첨시간'];

export default function BetCreationPage() {
  const navigate = useNavigate();

  const { Funnel, currentStep, goBack, goNextStep } =
    useFunnel<BetCreationStep>('제목');

  const [state, setState] = useState<BetInputInfo>({
    title: '',
    waitingMinutes: 0,
  });

  const { mutate: createBet } = useAddBet((betId) => {
    navigate(GET_ROUTES.nowDarakbang.betDetail(betId));
  });

  return (
    <FunnelLayout>
      <FunnelLayout.Header>
        <FunnelLayout.Header.Left>
          <BackArrowButton onClick={goBack} />
        </FunnelLayout.Header.Left>
        <FunnelLayout.Header.Center>
          안내면진다 만들기
        </FunnelLayout.Header.Center>
      </FunnelLayout.Header>

      <FunnelStepIndicator totalSteps={steps} currentStep={currentStep} />

      <Funnel
        step={{
          제목: (
            <TitleStep
              title={state.title}
              isValid={true}
              onTitleChange={(title) => setState({ ...state, title })}
              onButtonClick={() => goNextStep('추첨시간')}
            />
          ),
          추첨시간: (
            <WaitingMinutesStep
              waitingMinutes={state.waitingMinutes}
              isValid={true}
              onWaitingMinutesChange={(waitingMinutes: number) =>
                setState({ ...state, waitingMinutes })
              }
              onButtonClick={() => createBet(state)}
            />
          ),
        }}
      />
    </FunnelLayout>
  );
}
