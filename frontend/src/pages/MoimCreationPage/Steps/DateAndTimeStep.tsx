import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';

interface DateAndTimeStepProps {
  date: string;
  time: string;
  onDateChange: (date: string) => void;
  onTimeChange: (time: string) => void;
}

export default function DateAndTimeStep(props: DateAndTimeStepProps) {
  const { date, time, onDateChange, onTimeChange } = props;

  return (
    <>
      <FunnelQuestion>
        <FunnelQuestion.Highlight>날짜</FunnelQuestion.Highlight>
        <FunnelQuestion.Text>와 </FunnelQuestion.Text>
        <FunnelQuestion.Highlight>시간</FunnelQuestion.Highlight>
        <FunnelQuestion.Text>을</FunnelQuestion.Text>
        <br />
        <FunnelQuestion.Text>설정해주세요!</FunnelQuestion.Text>
      </FunnelQuestion>
      <FunnelInput
        type="date"
        value={date}
        onChange={(e) => onDateChange(e.target.value)}
      />
      <FunnelInput
        type="time"
        value={time}
        onChange={(e) => onTimeChange(e.target.value)}
      />
    </>
  );
}
