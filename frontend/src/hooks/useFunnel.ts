import { ReactNode, useCallback } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

export default function useFunnel<Step extends string | number | symbol>(
  firstStep: Step,
) {
  const location = useLocation();
  const navigate = useNavigate();

  const currentStep: Step = location.state?.step || firstStep;

  const goBack = () => {
    navigate(-1);
  };

  const goNextStep = (nextStep: Step) => {
    navigate(location.pathname, {
      state: { step: nextStep },
    });
  };

  interface FunnelProps {
    step: Record<Step, ReactNode>;
  }

  const Funnel = useCallback(
    (props: FunnelProps) => {
      const { step } = props;
      return step[currentStep];
    },
    [currentStep],
  );

  return { currentStep, goBack, goNextStep, Funnel };
}
