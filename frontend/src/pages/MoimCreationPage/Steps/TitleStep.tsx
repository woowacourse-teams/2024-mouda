import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import POLICES from '@_constants/poclies';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { MoimInputInfo } from '@_types/index';

interface TitleProps {
  title: MoimInputInfo['title'];
  isValid: boolean;
  onTitleChange: (title: string) => void;
  onButtonClick: () => void;
}

export default function TitleStep(props: TitleProps) {
  const { title, isValid, onTitleChange, onButtonClick } = props;

  return (
    <>
      <FunnelLayout.Main>
        <FunnelQuestion>
          <FunnelQuestion.Text>모임의 </FunnelQuestion.Text>
          <FunnelQuestion.Highlight>이름</FunnelQuestion.Highlight>
          <FunnelQuestion.Text>은 무엇인가요?</FunnelQuestion.Text>
        </FunnelQuestion>
        <FunnelInput
          placeholder="모임의 이름을 입력해주세요."
          value={title}
          onChange={(e) => onTitleChange(e.target.value)}
        />
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelButton disabled={!isValid} onClick={onButtonClick}>
          {title === ''
            ? '모임 이름을 입력해주세요'
            : !isValid
              ? `${POLICES.minimumTitleLength} ~ ${POLICES.maximumTitleLength}글자만 가능해요`
              : '다음으로'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
