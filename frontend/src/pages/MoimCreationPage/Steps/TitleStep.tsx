import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import { MoimInputInfo } from '@_types/index';

interface TitleProps {
  title: MoimInputInfo['title'];
  onTitleChange: (title: string) => void;
}

export default function TitleStep(props: TitleProps) {
  const { title, onTitleChange } = props;

  return (
    <>
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
    </>
  );
}
