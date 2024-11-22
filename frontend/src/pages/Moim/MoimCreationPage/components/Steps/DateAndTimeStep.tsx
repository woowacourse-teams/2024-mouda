import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelInputErrorMessage from '@_components/Funnel/FunnelInputErrorMessage/FunnelInputErrorMessage';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';

interface DateAndTimeStepProps {
  date: string;
  time: string;
  isValidDate: boolean;
  isValidTime: boolean;
  onDateChange: (date: string) => void;
  onTimeChange: (time: string) => void;
  onButtonClick: () => void;
}

export default function DateAndTimeStep(props: DateAndTimeStepProps) {
  const {
    date,
    time,
    isValidDate,
    isValidTime,
    onDateChange,
    onTimeChange,
    onButtonClick,
  } = props;

  return (
    <>
      <FunnelLayout.Main>
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
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        {isValidDate && isValidTime ? null : (
          <FunnelInputErrorMessage>
            {!isValidDate && !isValidTime
              ? '날짜와 시간을 다시 확인해주세요'
              : !isValidDate
                ? '날짜를 다시 확인해주세요'
                : !isValidTime
                  ? '시간을 다시 확인해주세요'
                  : null}
          </FunnelInputErrorMessage>
        )}
        <FunnelButton
          disabled={!isValidDate || !isValidTime}
          onClick={onButtonClick}
        >
          {date === '' && time === ''
            ? '스킵하고 채팅에서 정할게요!'
            : date === ''
              ? '날짜는 채팅에서 정할게요!'
              : time === ''
                ? '시간은 채팅에서 정할게요!'
                : '다음으로'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
