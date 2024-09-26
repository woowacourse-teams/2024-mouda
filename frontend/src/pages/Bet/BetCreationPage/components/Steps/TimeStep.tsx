import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import Select from '@_components/Select/Select';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { BetInputInfo } from '@_types/index';

interface TimeStepProps {
  when: BetInputInfo['when'];
  isValid: boolean;
  onWhenChange: (when: string) => void;
  onButtonClick: () => void;
}

const options = [
  { value: '5분 후', label: '5분 후' },
  { value: '10분 후', label: '10분 후' },
  { value: '15분 후', label: '15분 후' },
  { value: '20분 후', label: '20분 후' },
  { value: '25분 후', label: '25분 후' },
  { value: '30분 후', label: '30분 후' },
  { value: '35분 후', label: '35분 후' },
  { value: '50분 후', label: '50분 후' },
  { value: '60분 후', label: '60분 후' },
];

export default function TimeStep(props: TimeStepProps) {
  const { when, isValid, onWhenChange, onButtonClick } = props;

  return (
    <>
      <FunnelLayout.Main>
        <FunnelQuestion>
          <FunnelQuestion.Highlight>언제 </FunnelQuestion.Highlight>
          <FunnelQuestion.Text>시작할까요?</FunnelQuestion.Text>
        </FunnelQuestion>

        <Select
          options={options}
          value={when}
          onChange={onWhenChange}
          placeholder="몇 분 후에 시작할까요?"
        />
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelButton disabled={!isValid} onClick={onButtonClick}>
          {!isValid ? '시간을 잘~ 입력해주세요' : '완료'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
