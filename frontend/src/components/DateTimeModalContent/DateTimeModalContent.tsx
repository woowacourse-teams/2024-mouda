import * as S from './DateTimeModalContent.style';

import { ChangeEvent, useMemo, useState } from 'react';

import Button from '@_components/Button/Button';
import LabeledInput from '@_components/Input/MoimInput';
import POLICES from '@_constants/poclies';
import { useTheme } from '@emotion/react';

interface PlaceModalContentProps {
  onCancel: () => void;
  onConfirm: ({ date, time }: { date: string; time: string }) => void;
}

const validateDate = (date: string) => {
  if (date === '') {
    return true;
  }
  const nowDate = new Date();
  const nowDateYyyymmdd = `${nowDate.getFullYear()}-${(nowDate.getMonth() + 1).toString().padStart(2, '00')}-${nowDate.getDate().toString().padStart(2, '00')}`;
  return date >= nowDateYyyymmdd && POLICES.yyyymmddDashRegex.test(date);
};

const validateTime = (time: string) => {
  if (time === '') {
    return false;
  }
  return POLICES.hhmmRegex.test(time);
};

export default function DateTimeModalContent(props: PlaceModalContentProps) {
  const { onCancel, onConfirm } = props;
  const [date, setDate] = useState('');
  const [time, setTime] = useState('');

  const theme = useTheme();

  const isValidDate = useMemo(() => validateDate(date), [date]);
  const isValidTime = useMemo(() => validateTime(time), [time]);
  return (
    <div css={S.content({ theme })}>
      <LabeledInput
        title="날짜"
        onChange={(e: ChangeEvent<HTMLInputElement>) => {
          setDate(e.target.value);
        }}
        type="date"
      />
      <LabeledInput
        title="시간"
        onChange={(e: ChangeEvent<HTMLInputElement>) => {
          setTime(e.target.value);
        }}
        type="time"
      />
      <div css={S.buttonContainer}>
        <div css={S.buttonWrapper}>
          <Button
            shape="bar"
            reversePrimary
            onClick={onCancel}
            font={theme.typography.Medium}
          >
            취소
          </Button>
        </div>
        <div css={S.buttonWrapper}>
          <Button
            shape="bar"
            primary
            disabled={!isValidDate || !isValidTime}
            onClick={() => {
              if (!isValidDate || !isValidTime) return;
              onConfirm({ date, time });
              setDate('');
              setTime('');
            }}
            font={theme.typography.Medium}
          >
            확정하기
          </Button>
        </div>
      </div>
    </div>
  );
}
