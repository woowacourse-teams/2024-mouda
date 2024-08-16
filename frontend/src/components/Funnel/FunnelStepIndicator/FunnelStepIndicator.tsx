import { useTheme } from '@emotion/react';
import * as S from './FunnelStepIndicator.style';

interface FunnelStepIndicatorProps {
  totalSteps: number;
  currentStep: number;
}

export default function FunnelStepIndicator(props: FunnelStepIndicatorProps) {
  const { totalSteps, currentStep } = props;

  const theme = useTheme();

  const progress = (currentStep / totalSteps) * 100;

  return (
    <div css={S.container}>
      <div css={S.progressBar({ theme })}>
        <div css={S.progress({ theme, progress })} />
      </div>
    </div>
  );
}
