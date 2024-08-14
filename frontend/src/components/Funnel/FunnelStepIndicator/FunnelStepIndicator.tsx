import { useTheme } from '@emotion/react';
import * as S from './FunnelStepIndicator.style';

interface FunnelStepIndicatorProps {
  totalSteps: number;
  currentStep: number;
}

export default function FunnelStepIndicator(props: FunnelStepIndicatorProps) {
  const { totalSteps, currentStep } = props;

  const theme = useTheme();

  return (
    <div css={S.container}>
      {Array.from({ length: totalSteps }).map((_, index) =>
        index <= currentStep ? (
          <div key={index} css={[S.step, S.activeStep({ theme })]}></div>
        ) : (
          <div key={index} css={[S.step, S.inactiveStep({ theme })]}></div>
        ),
      )}
    </div>
  );
}
