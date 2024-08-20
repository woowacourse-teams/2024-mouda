import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import POLICES from '@_constants/poclies';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';

interface MaxPeopleStepProps {
  maxPeople: number;
  isValid: boolean;
  onMaxPeopleChange: (maxPeople: number) => void;
  onButtonClick: () => void;
}

export default function MaxPeopleStep(props: MaxPeopleStepProps) {
  const { maxPeople, isValid, onMaxPeopleChange, onButtonClick } = props;

  return (
    <>
      <FunnelLayout.Main>
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
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelButton disabled={!isValid} onClick={onButtonClick}>
          {!isValid
            ? `${POLICES.minimumMaxPeople} ~ ${POLICES.maximumMaxPeople}명만 가능해요`
            : '다음으로'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
