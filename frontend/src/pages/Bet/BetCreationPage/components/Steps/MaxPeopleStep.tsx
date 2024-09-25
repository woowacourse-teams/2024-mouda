import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { BetInputInfo } from '@_types/index';
import { useEffect, useRef } from 'react';

interface MaxPeopleStepProps {
  maxPeople: BetInputInfo['maxPeople'];
  isValid: boolean;
  onMaxPeopleChange: (maxPeople: number) => void;
  onButtonClick: () => void;
}

export default function MaxPeopleStep(props: MaxPeopleStepProps) {
  const { maxPeople, isValid, onMaxPeopleChange, onButtonClick } = props;

  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);

  return (
    <>
      <FunnelLayout.Main>
        <FunnelQuestion>
          <FunnelQuestion.Text>최대 </FunnelQuestion.Text>
          <FunnelQuestion.Highlight>참가 인원</FunnelQuestion.Highlight>
          <FunnelQuestion.Text>을</FunnelQuestion.Text>
          <br />
          <FunnelQuestion.Text>설정해주세요!</FunnelQuestion.Text>
        </FunnelQuestion>

        <FunnelInput
          ref={inputRef}
          type="number"
          value={maxPeople}
          onKeyUp={(e) => e.key === 'Enter' && isValid && onButtonClick()}
          onChange={(e) => onMaxPeopleChange(parseInt(e.target.value))}
        />
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelButton disabled={!isValid} onClick={onButtonClick}>
          {!isValid ? '참가 인원을 잘~ 입력해주세요' : '다음으로'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
