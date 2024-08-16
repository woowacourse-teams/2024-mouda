import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';

interface MaxPeopleStepProps {
  maxPeople: number;
  onMaxPeopleChange: (maxPeople: number) => void;
}

export default function MaxPeopleStep(props: MaxPeopleStepProps) {
  const { maxPeople, onMaxPeopleChange } = props;

  return (
    <>
      <FunnelQuestion>
        <FunnelQuestion.Text>최대 참가 인원을</FunnelQuestion.Text>
        <br />
        <FunnelQuestion.Text>설정해주세요!</FunnelQuestion.Text>
      </FunnelQuestion>
      <FunnelInput
        type="number"
        value={maxPeople}
        onChange={(e) => onMaxPeopleChange(parseInt(e.target.value))}
      />
    </>
  );
}
