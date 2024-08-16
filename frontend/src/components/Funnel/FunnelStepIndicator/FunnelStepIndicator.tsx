import { useTheme } from '@emotion/react';
import * as S from './FunnelStepIndicator.style';
import { MoimCreationStep } from '@_pages/MoimCreationPage/MoimCreationPage';

interface FunnelStepIndicatorProps {
  totalSteps: MoimCreationStep[];
  currentStep: MoimCreationStep;
}

export default function FunnelStepIndicator(props: FunnelStepIndicatorProps) {
  const { totalSteps, currentStep } = props;

  const theme = useTheme();

  const progress =
    ((totalSteps.findIndex((step) => step === currentStep) + 1) /
      totalSteps.length) *
    100;

  return (
    <div css={S.container}>
      <div css={S.progressBar({ theme })}>
        <div css={S.progress({ theme, progress })} />
      </div>
    </div>
  );
}
