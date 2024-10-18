import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelErrorMessage from '@_components/Funnel/FunnelErrorMessage/FunnelErrorMessage';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import FunnelRadioCardGroup from '@_components/Funnel/FunnelRadioCardGroup/FunnelRadioCardGroup';
import FunnelRadioCardGroupOption from '@_components/Funnel/FunnelRadioCardGroup/FunnelRadioCardGroupOption/FunnelRadioCardGroupOption';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { BetInputInfo } from '@_types/index';
import { useRef } from 'react';

interface TimeStepProps {
  waitingMinutes: BetInputInfo['waitingMinutes'];
  isValid: boolean;
  errorMessage: string;
  onWaitingMinutesChange: (waitingMinutes: number) => void;
  onButtonClick: () => Promise<void>;
}

const options = [
  { value: 5, label: '5분' },
  { value: 10, label: '10분' },
  { value: 15, label: '15분' },
  { value: 20, label: '20분' },
  { value: 25, label: '25분' },
  { value: 30, label: '30분' },
];

export default function WaitingMinutesStep(props: TimeStepProps) {
  const {
    waitingMinutes,
    isValid,
    errorMessage,
    onWaitingMinutesChange,
    onButtonClick,
  } = props;

  const loadingRef = useRef(false);

  const handleButtonClick = async () => {
    loadingRef.current = true;
    await onButtonClick();
    loadingRef.current = false;
  };

  return (
    <>
      <FunnelLayout.Main>
        <FunnelQuestion>
          <FunnelQuestion.Highlight>추첨 시간</FunnelQuestion.Highlight>
          <FunnelQuestion.Text>을 선택해주세요.</FunnelQuestion.Text>
        </FunnelQuestion>

        <FunnelRadioCardGroup columns={2}>
          {options.map((option) => (
            <FunnelRadioCardGroupOption
              key={option.value}
              title={option.label}
              isSelected={waitingMinutes === option.value}
              onSelect={() => onWaitingMinutesChange(option.value)}
            />
          ))}
        </FunnelRadioCardGroup>
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelErrorMessage isError={!isValid} errorMessage={errorMessage} />
        <FunnelButton
          disabled={loadingRef.current || !isValid}
          onClick={handleButtonClick}
        >
          완료
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
