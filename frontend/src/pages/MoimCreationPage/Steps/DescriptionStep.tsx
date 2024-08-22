import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import FunnelTextArea from '@_components/Funnel/FunnelTextArea/FunnelTextArea';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { useEffect, useRef } from 'react';

interface DescriptionStepProps {
  description: string;
  onDescriptionChange: (description: string) => void;
  onButtonClick: () => void;
}

export default function DescriptionStep(props: DescriptionStepProps) {
  const { description, onDescriptionChange, onButtonClick } = props;

  const inputRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);

  return (
    <>
      <FunnelLayout.Main>
        <FunnelQuestion>
          <FunnelQuestion.Text>모임에 대해</FunnelQuestion.Text>
          <br />
          <FunnelQuestion.Highlight>
            설명하고 싶은 내용
          </FunnelQuestion.Highlight>
          <FunnelQuestion.Text>이</FunnelQuestion.Text>
          <br />
          <FunnelQuestion.Text>있다면 적어주세요!</FunnelQuestion.Text>
        </FunnelQuestion>
        <FunnelTextArea
          ref={inputRef}
          placeholder="칼바람 한 판 하쉴?"
          value={description}
          onChange={(e) => onDescriptionChange(e.target.value)}
        />
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelButton onClick={onButtonClick}>모임 만들기 완료!</FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
